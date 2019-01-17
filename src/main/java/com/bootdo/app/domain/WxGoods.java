package com.bootdo.app.domain;

/**
 * Created by ziteng016 on 2019/1/10.
 */
public class WxGoods {
    private Integer goodsId;
    private String goodsName;
    private Integer goodsType;
    private String goodsImg;
    private String goodsDetail;
    private String goodsDesc;
    private String goodsText;
    private String goodsCode;
    private String goodsBrand;
    private String goodsTop;
    private String goodsCost;
    private String goodsPrice;
    private String goodsStatus;
    private String goodsCarousel;


    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodsText() {
        return goodsText;
    }

    public void setGoodsText(String goodsText) {
        this.goodsText = goodsText;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getGoodsTop() {
        return goodsTop;
    }

    public void setGoodsTop(String goodsTop) {
        this.goodsTop = goodsTop;
    }

    public String getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(String goodsCost) {
        this.goodsCost = goodsCost;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsCarousel() {
        return goodsCarousel;
    }

    public void setGoodsCarousel(String goodsCarousel) {
        this.goodsCarousel = goodsCarousel;
    }

    @Override
    public String toString() {
        return "WxGoods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsType=" + goodsType +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsDetail='" + goodsDetail + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodsText='" + goodsText + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsBrand='" + goodsBrand + '\'' +
                ", goodsTop='" + goodsTop + '\'' +
                ", goodsCost='" + goodsCost + '\'' +
                ", goodsPrice='" + goodsPrice + '\'' +
                ", goodsStatus='" + goodsStatus + '\'' +
                ", goodsCarousel='" + goodsCarousel + '\'' +
                '}';
    }
}
