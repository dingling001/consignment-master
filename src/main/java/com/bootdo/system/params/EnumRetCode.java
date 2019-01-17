package com.bootdo.system.params;

/**
 * Created by Andy on 2016/11/9.
 * 全局返回Code枚举类
 */
public enum EnumRetCode {

    SUCCESS("0000", "请求成功"),

    PARAM_EMPTY("0001", "参数为空"),

    PARAM_ERROR("0002","参数错误"),

    CODE_ERROR("0003","code不合法"),

    NONE_DATA("0004", "暂无数据"),

    SYSTEM_ERROR("9999","系统错误"),
    ;

    EnumRetCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 异常编码
     */
    private String code;
    /**
     * 异常信息
     */
    private String info;

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}