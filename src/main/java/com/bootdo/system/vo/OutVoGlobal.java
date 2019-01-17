package com.bootdo.system.vo;


import com.bootdo.system.params.EnumRetCode;

/**
 * Created by Andy on 2016/11/9.
 * 全局返回VO
 */
public class OutVoGlobal {
    /**
     * 系统状态
     */
    private String code;
    /**
     * 系统返回信息
     */
    private String info;
    /**
     * 返回数据
     */
    private Object data;

    public String getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 设置枚举信息
     */
    public void setEnum(EnumRetCode enumRetCode) {
        this.code = enumRetCode.getCode();
        this.info = enumRetCode.getInfo();
    }
}
