package com.bootdo.common.exception;


import com.bootdo.common.controller.BaseController;
import com.bootdo.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MainsiteErrorController extends BaseController implements ErrorController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String ERROR_PATH ="/error";

    @Autowired
    ErrorAttributes errorAttributes;

    @RequestMapping(
            value = {ERROR_PATH},
            produces = {"text/html"}
    )
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        int code = response.getStatus();
        ModelAndView model = new ModelAndView();
        if (404 == code) {
            model.addObject("prefix",appPrefix);
            model.setViewName("/error/404");
            return model;
        } else if (403 == code) {
            model.addObject("prefix",appPrefix);
            model.setViewName("/error/403");
            return model;
        } else if (401 == code) {
            model.addObject("prefix",appPrefix);
            model.setViewName("/login");
            return model;
        } else {
            model.addObject("prefix",appPrefix);
            model.setViewName("/error/500");
            return model;
        }

    }

    @RequestMapping(value = ERROR_PATH)
    public R handleError(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(200);
        int code = response.getStatus();
        if (404 == code) {
            return R.error(404, "未找到资源");
        } else if (403 == code) {
            return R.error(403, "没有访问权限");
        } else if (401 == code) {
            return R.error(403, "登录过期");
        } else {
            return R.error(500, "服务器错误");
        }
    }

    @Override
    public String getErrorPath() {
        // TODO Auto-generated method stub
        return ERROR_PATH;
    }
}