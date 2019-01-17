package com.bootdo.app.controller;

import com.bootdo.app.domain.WxBanner;
import com.bootdo.app.domain.WxGoods;
import com.bootdo.app.domain.WxGoodsType;
import com.bootdo.app.service.WxBannerService;
import com.bootdo.app.service.WxGoodsService;
import com.bootdo.system.params.EnumRetCode;
import com.bootdo.system.vo.OutVoGlobal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */
@RequestMapping("/app/weChat")
@Controller
@Api(value = "商品接口")
public class ConsignmentController {

    @Autowired
    private WxGoodsService wxGoodsService;


    @Autowired
    private WxBannerService wxBannerService;


    @PostMapping(value = "/getGoodsList")
    @ResponseBody
    @ApiOperation(value = "获取分类下商品列表",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal getGoodsList(@RequestParam(value = "token", required = false) String token,
                                    @RequestParam(value = "typeId", required = false) String typeId,
                                    @RequestParam(value = "priceType", required = false) String priceType,
                                    @RequestParam(value = "brand", required = false) String brand){
        OutVoGlobal outVoGlobal = new OutVoGlobal();
        Map<String,Object> map = new HashMap<>();
        map.put("brand",brand);
        map.put("priceType",priceType);

        Map<String,Object> data = new HashMap<>();
        List<WxGoods> goodsList = wxGoodsService.getGoodsList(map);
        List<String> goodsBrand = wxGoodsService.getGoodsBrand(Integer.valueOf(typeId));

        data.put("goodsList",goodsList);
        data.put("goodsBrand",goodsBrand);
        outVoGlobal.setEnum(EnumRetCode.SUCCESS);
        outVoGlobal.setData(data);
        return outVoGlobal;
    }

    @PostMapping(value = "/getTypeList")
    @ResponseBody
    @ApiOperation(value = "获取分类列表",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal getTypeList(@RequestParam(value = "token", required = false) String token){

        List<WxGoodsType> types = wxGoodsService.getTypes(new HashMap<>());
        return checkValue(types);
    }

    @PostMapping(value = "/getBannerList")
    @ResponseBody
    @ApiOperation(value = "获取首页Banner列表",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal getBannerList(@RequestParam(value = "token", required = false) String token){
        List<WxBanner> list = wxBannerService.list(new HashMap<>());

        return checkValue(list);
    }

    private OutVoGlobal checkValue(Object obj){
        OutVoGlobal outVoGlobal = new OutVoGlobal();

        if(StringUtils.isEmpty(obj)){
            outVoGlobal.setEnum(EnumRetCode.NONE_DATA);
            return outVoGlobal;
        }

        outVoGlobal.setEnum(EnumRetCode.SUCCESS);
        outVoGlobal.setData(obj);
        return outVoGlobal;
    }


    @PostMapping(value = "/getGoodsInfo")
    @ResponseBody
    @ApiOperation(value = "获取商品详情",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal getGoodsInfo(@RequestParam(value = "goodsId", required = false) Integer goodsId,
                                    @RequestParam(value = "token", required = false) String token){
        WxGoods wxGoods = wxGoodsService.getWxGoods(goodsId);

        return checkValue(wxGoods);
    }


    @PostMapping(value = "/homeList")
    @ResponseBody
    @ApiOperation(value = "首页商品列表",notes = "正常返回数据:{\"code\": \"0000\",\"info\": \"请求成功\",\"data\": \"相应数据\"}")
    public OutVoGlobal getHomeList(@RequestParam(value = "token", required = false) String token){
        OutVoGlobal outVoGlobal = new OutVoGlobal();

        WxGoodsType wxGoodsType = new WxGoodsType();
        wxGoodsType.setTypeName("精品推荐");
        List<WxGoods> topGoods = wxGoodsService.getTopGoods();
        wxGoodsType.setList(topGoods);

        List<WxGoodsType> data = new ArrayList<>();

        data.add(wxGoodsType);

        List<WxGoodsType> list = wxGoodsService.getTypeList();
        data.addAll(list);

        outVoGlobal.setEnum(EnumRetCode.SUCCESS);
        outVoGlobal.setData(data);
        return  outVoGlobal;
    }

}
