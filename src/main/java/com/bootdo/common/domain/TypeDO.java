package com.bootdo.common.domain;

public class TypeDO {
    private Integer business_type_id;

    private String business_type;

    private String business_type_name;

    private String business_type_superior;

    private String business_type_desc;

    private String business_type_is_root;

    private Integer business_type_power;

    private Integer business_type_status;

    private String business_type_img;

    private String business_type_dev;

    private String is_popular;

    public Integer getBusiness_type_id() {
        return business_type_id;
    }

    public void setBusiness_type_id(Integer business_type_id) {
        this.business_type_id = business_type_id;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type == null ? null : business_type.trim();
    }

    public String getBusiness_type_name() {
        return business_type_name;
    }

    public void setBusiness_type_name(String business_type_name) {
        this.business_type_name = business_type_name == null ? null : business_type_name.trim();
    }

    public String getBusiness_type_superior() {
        return business_type_superior;
    }

    public void setBusiness_type_superior(String business_type_superior) {
        this.business_type_superior = business_type_superior == null ? null : business_type_superior.trim();
    }

    public String getBusiness_type_desc() {
        return business_type_desc;
    }

    public void setBusiness_type_desc(String business_type_desc) {
        this.business_type_desc = business_type_desc == null ? null : business_type_desc.trim();
    }

    public String getBusiness_type_is_root() {
        return business_type_is_root;
    }

    public void setBusiness_type_is_root(String business_type_is_root) {
        this.business_type_is_root = business_type_is_root == null ? null : business_type_is_root.trim();
    }

    public Integer getBusiness_type_power() {
        return business_type_power;
    }

    public void setBusiness_type_power(Integer business_type_power) {
        this.business_type_power = business_type_power;
    }

    public Integer getBusiness_type_status() {
        return business_type_status;
    }

    public void setBusiness_type_status(Integer business_type_status) {
        this.business_type_status = business_type_status;
    }

    public String getBusiness_type_img() {
        return business_type_img;
    }

    public void setBusiness_type_img(String business_type_img) {
        this.business_type_img = business_type_img == null ? null : business_type_img.trim();
    }

    public String getBusiness_type_dev() {
        return business_type_dev;
    }

    public void setBusiness_type_dev(String business_type_dev) {
        this.business_type_dev = business_type_dev == null ? null : business_type_dev.trim();
    }

    public String getIs_popular() {
        return is_popular;
    }

    public void setIs_popular(String is_popular) {
        this.is_popular = is_popular == null ? null : is_popular.trim();
    }
}