package com.bootdo.app.service.impl;

import com.bootdo.app.dao.WxBannerDao;
import com.bootdo.app.domain.WxBanner;
import com.bootdo.app.service.WxBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */
@Service("wxBannerService")
public class WxBannerServiceImpl implements WxBannerService {

    @Autowired
    private WxBannerDao wxBannerDao;

    @Override
    public int remove(Integer banId) {
        return wxBannerDao.remove(banId);
    }

    @Override
    public WxBanner get(Integer banId) {
        return wxBannerDao.get(banId);
    }

    @Override
    public int update(WxBanner wxBanner) {
        return wxBannerDao.update(wxBanner);
    }

    @Override
    public int save(WxBanner wxBanner) {
        return wxBannerDao.save(wxBanner);
    }

    @Override
    public List<WxBanner> list(Map<String,Object> map) {
        return wxBannerDao.list(map);
    }

    @Override
    public Integer count(Map<String,Object> map) {
        return wxBannerDao.count(map);
    }
}
