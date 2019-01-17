package com.bootdo.system.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.bootdo.common.config.Constant;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.redis.shiro.RedisCacheManager;
import com.bootdo.common.redis.shiro.RedisManager;
import com.bootdo.common.redis.shiro.RedisSessionDAO;
import com.bootdo.system.shiro.UserRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

//import org.apache.shiro.cache.CacheManager;

/**
 * @author bootdo 1992lcg@163.com
 */
@Configuration
public class ShiroConfig extends BaseController {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.cache.type}")
    private String cacheType;

    @Value("${server.session-timeout}")
    private int tomcatTimeout;

//    @Autowired
//    CacheManager cacheManager;

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(appPrefix + "/login");
        shiroFilterFactoryBean.setSuccessUrl(appPrefix + "/index");
        shiroFilterFactoryBean.setUnauthorizedUrl(appPrefix + "/403");
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

       /* swagger-start */
        filterChainDefinitionMap.put(appPrefix + "/swagger-ui.html", "anon");
        filterChainDefinitionMap.put(appPrefix + "/webjars/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/v2/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/swagger-resources/**", "anon");
        /* swagger-end */


        filterChainDefinitionMap.put(appPrefix + "/css/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/js/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/fonts/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/img/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/docs/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/druid/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/upload/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/files/**", "anon");
        //filterChainDefinitionMap.put(appPrefix + "/logout", "logout");
        filterChainDefinitionMap.put(appPrefix + "/", "anon");
        filterChainDefinitionMap.put(appPrefix + "/blog", "anon");
        filterChainDefinitionMap.put(appPrefix + "/blog/open/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/system/onlinePreferential/app/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/app/api/**", "anon");
        filterChainDefinitionMap.put(appPrefix + "/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(userRealm());
        // 自定义缓存实现 使用redis
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            securityManager.setCacheManager(cacheManager());
        } else {
            securityManager.setCacheManager(ehCacheManager());
        }
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        return userRealm;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     *
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);// 配置缓存过期时间
        //redisManager.setTimeout(1800);
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionDAO sessionDAO() {
        if (Constant.CACHE_TYPE_REDIS.equals(cacheType)) {
            return redisSessionDAO();
        } else {
            return new MemorySessionDAO();
        }
    }

    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
        sessionManager.setSessionDAO(sessionDAO());
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        listeners.add(new BDSessionListener());
        sessionManager.setSessionListeners(listeners);
        return sessionManager;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManager(CacheManager.create());
        return em;
    }


}
