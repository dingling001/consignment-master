package com.bootdo.app.domain;

import java.util.List;

/**
 * Created by ziteng016 on 2019/1/10.
 */
public class WxGoodsType {

    private Integer typeId;
    private Integer typeParent;
    private String typeName;
    private String typeImg;

    private List<WxGoods> list;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getTypeParent() {
        return typeParent;
    }

    public void setTypeParent(Integer typeParent) {
        this.typeParent = typeParent;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeImg() {
        return typeImg;
    }

    public void setTypeImg(String typeImg) {
        this.typeImg = typeImg;
    }

    public List<WxGoods> getList() {
        return list;
    }

    public void setList(List<WxGoods> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "WxGoodsType{" +
                "typeId=" + typeId +
                ", typeParent=" + typeParent +
                ", typeName='" + typeName + '\'' +
                ", typeImg='" + typeImg + '\'' +
                ", list=" + list +
                '}';
    }
}
