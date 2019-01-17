package com.bootdo.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.bootdo.system.params.EnumRetCode;
import com.bootdo.system.utils.HttpClientUtil;
import com.bootdo.system.vo.OutVoGlobal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ziteng016 on 2019/1/10.
 */
@RequestMapping("/app/weChat")
@Controller
@Api(value = "小程序登录调用接口")
public class WeChatController {

    private String baseUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private String appId = "wxd9b0aabd502ac948";
    private String secret = "808beb01ac26951e7fae2405c6f85ee3";
    private String suffix = "&grant_type=authorization_code";


    @PostMapping(value = "/login")
    @ResponseBody
    @ApiOperation(value = "登录接口",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal login(@RequestParam(value = "code", required = false) String code){
        OutVoGlobal outVoGlobal = new OutVoGlobal();
        String url = baseUrl + "?appid=" + appId + "&secret=" + secret +"&js_code=" + code + suffix;
        String request = HttpClientUtil.httpPostRequest(url);
        System.out.println("request = " + request);
        JSONObject jsonObject = JSONObject.parseObject(request);
        Object errcode = jsonObject.get("errcode");
        if(!StringUtils.isEmpty(errcode)){
            outVoGlobal.setEnum(EnumRetCode.CODE_ERROR);
            return outVoGlobal;
        }else{
            String openid = jsonObject.get("openid").toString();
            String sessionKey = jsonObject.get("session_key").toString();


        }


        return outVoGlobal;
    }
}
