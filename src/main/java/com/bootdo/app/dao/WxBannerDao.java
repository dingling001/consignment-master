package com.bootdo.app.dao;

import com.bootdo.app.domain.WxBanner;

import java.util.List;
import java.util.Map;

/**
 * Created by ziteng016 on 2019/1/10.
 */

public interface WxBannerDao {

    int remove(Integer banId);

    int update(WxBanner wxBanner);

    int save(WxBanner wxBanner);

    WxBanner get(Integer banId);

    List<WxBanner> list(Map<String,Object> map);

    Integer count(Map<String,Object> map);
}
