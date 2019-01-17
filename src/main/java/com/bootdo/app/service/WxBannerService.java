package com.bootdo.app.service;

import com.bootdo.app.domain.WxBanner;

import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */
public interface WxBannerService {
    int remove(Integer banId);

    WxBanner get(Integer banId);

    int update(WxBanner wxBanner);

    int save(WxBanner wxBanner);

    List<WxBanner> list(Map<String,Object> map);

    Integer count(Map<String,Object> map);
}
