package com.bootdo.system.dao;

import com.bootdo.system.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserDao {

	UserDO getByAccountPassword(@Param("username")String username,@Param("password") String password);

	UserDO get(Long userId);
	
	List<UserDO> list(Map<String, Object> map);

	List<UserDO> devList(Map<String, Object> map);
	int devCount(Map<String, Object> map);

	List<UserDO> listAuthUsers(Map<String, Object> map);
	
	int count(Map<String, Object> map);

	int countAuthUsers(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
	
	Long[] listAllDept();

}
