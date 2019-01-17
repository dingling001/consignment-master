package com.bootdo.app.dao;

import com.bootdo.app.domain.WxGoods;
import com.bootdo.app.domain.WxGoodsType;

import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */
public interface WxGoodsDao {
    public List<String> getGoodsBrand(Integer typeId);

    public Integer countTypes(Map<String,Object> map);

    public List<WxGoodsType> getTypes(Map<String,Object> map);


    public WxGoods getWxGoods(Integer goodsId);

    public Integer countGoods(Map<String,Object> map);

    public List<WxGoods> getGoodsList(Map<String,Object> map);

    public List<WxGoods> getTopGoods();

    public List<WxGoodsType> getTypeList();

    public Integer saveGoods(WxGoods wxGoods);

    public Integer updateGoods(WxGoods wxGoods);

    public Integer  removeGoods(Integer goodsId);

    public Integer saveType(WxGoodsType wxGoodsType);

    public Integer updateType(WxGoodsType wxGoodsType);

    public Integer removeType(Integer typeId);

    public WxGoodsType getType(Integer typeId);
}
