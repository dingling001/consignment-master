package com.bootdo.common.controller;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	public UserDO getUser() {
        return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}

	public Long getUserDeptId(){
		return getUser().getDeptId();
	}

	@Autowired
	protected BootdoConfig bootdoConfig;

    @Value("${option.app.prefix}")
    protected String appPrefix;


	@Value("${option.file.prefix}")
	protected String filePrefix;
}