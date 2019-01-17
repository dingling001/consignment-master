package com.bootdo.app.service.impl;

import com.bootdo.app.dao.WxGoodsDao;
import com.bootdo.app.domain.WxGoods;
import com.bootdo.app.domain.WxGoodsType;
import com.bootdo.app.service.WxGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */
@Service("wxGoodsService")
public class WxGoodsServiceImpl implements WxGoodsService {

    @Autowired
    private WxGoodsDao wxGoodsDao;

    @Override
    public List<String> getGoodsBrand(Integer typeId) {
        return wxGoodsDao.getGoodsBrand(typeId);
    }

    @Override
    public Integer countTypes(Map<String, Object> map) {
        return wxGoodsDao.countTypes(map);
    }

    @Override
    public List<WxGoodsType> getTypes(Map<String, Object> map) {
        return wxGoodsDao.getTypes(map);
    }

    @Override
    public Integer countGoods(Map<String, Object> map) {
        return wxGoodsDao.countGoods(map);
    }

    @Override
    public List<WxGoods> getGoodsList(Map<String, Object> map) {
        return wxGoodsDao.getGoodsList(map);
    }

    @Override
    public WxGoods getWxGoods(Integer goodsId) {
        return wxGoodsDao.getWxGoods(goodsId);
    }

    @Override
    public List<WxGoods> getTopGoods() {
        return wxGoodsDao.getTopGoods();
    }

    @Override
    public List<WxGoodsType> getTypeList() {
        return wxGoodsDao.getTypeList();
    }

    @Override
    public Integer saveGoods(WxGoods wxGoods) {
        return wxGoodsDao.saveGoods(wxGoods);
    }

    @Override
    public Integer updateGoods(WxGoods wxGoods) {
        return wxGoodsDao.updateGoods(wxGoods);
    }

    @Override
    public Integer removeGoods(Integer goodsId) {
        return wxGoodsDao.removeGoods(goodsId);
    }

    @Override
    public Integer saveType(WxGoodsType wxGoodsType) {
        return wxGoodsDao.saveType(wxGoodsType);
    }

    @Override
    public Integer updateType(WxGoodsType wxGoodsType) {
        return wxGoodsDao.updateType(wxGoodsType);
    }

    @Override
    public Integer removeType(Integer typeId) {
        return wxGoodsDao.removeType(typeId);
    }

    @Override
    public WxGoodsType getType(Integer typeId) {
        return wxGoodsDao.getType(typeId);
    }
}
