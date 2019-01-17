package com.bootdo.system.controller.content;


import com.bootdo.app.domain.WxGoods;
import com.bootdo.app.domain.WxGoodsType;
import com.bootdo.app.service.WxGoodsService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.FileUtil;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.system.utils.FileUtils;
import com.bootdo.system.utils.JsonUtils;
import com.bootdo.system.vo.OutVoGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/goods")
public class SysGoodsController extends BaseController {
    private String prefix= "system/goods";

    @Autowired
    private WxGoodsService wxGoodsService;

    @Autowired
    private FileService sysFileService;

    @Autowired
    private FileUtils fileUtils;

    @GetMapping("/list")
    @ResponseBody
    PageUtils getList(@RequestParam Map<String, Object> params) {
        // 查询列表数据

        Query query = new Query(params);
        List<WxGoods> list = wxGoodsService.getGoodsList(query);
        Integer count = wxGoodsService.countGoods(query);


        return new PageUtils(list, count);
    }




    @GetMapping("/types")
    @ResponseBody
    public List<WxGoodsType> getTypeList(){
        return wxGoodsService.getTypes(new HashMap<>());
    }

    @GetMapping("/type/list")
    @ResponseBody
    PageUtils getTypeList(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);

        List<WxGoodsType> types = wxGoodsService.getTypes(query);
        Integer countTypes = wxGoodsService.countTypes(query);


        return new PageUtils(types, countTypes);
    }



    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/type/save")
    public R save(WxGoodsType wxGoodsType) {
        if (wxGoodsService.saveType(wxGoodsType)> 0) {
            return R.ok();
        }
        return R.error();
    }


    @GetMapping("/type/add")
    public String getAddTypeIndex(Model model){
        model.addAttribute("prefix",appPrefix);
        return prefix + "/type/add";
    }

    /**
     * 删除
     */
    @PostMapping("/type/remove")
    @ResponseBody
    public R remove(Integer id) {

        System.out.println(id);


        WxGoodsType type = wxGoodsService.getType(id);


        Long fileId = Long.valueOf(type.getTypeParent());
        String fileName = bootdoConfig.getUploadPath() + sysFileService.get(fileId).getUrl().replace("/files/", "");
        if (sysFileService.remove(fileId) > 0) {
            boolean b = FileUtil.deleteFile(fileName);
            if (!b) {
                return R.error("数据库记录删除成功，文件删除失败");
            }

            if(wxGoodsService.removeType(id) > 0){
                return R.ok();
            }

            return R.error();
        } else {
            return R.error();
        }
    }

    /**
     * 更新
     */
    @ResponseBody
    @PostMapping("/type/update")
    public R update(WxGoodsType wxGoodsType) {
        try{
            if (wxGoodsService.updateType(wxGoodsType) > 0) {
                return R.ok();
            }
            return R.error();
        }catch (Exception e){
            return R.ok();
        }
    }

    @GetMapping("/type/edit/{id}")
    String toEdit(@PathVariable String id, Model model) {
        model.addAttribute("prefix",appPrefix);
        Integer typeId = Integer.valueOf(id);
        WxGoodsType goodsType = wxGoodsService.getType(typeId);
        model.addAttribute("types",goodsType);

        return prefix + "/type/edit";
    }

    @GetMapping("/type/index")
    public String getTypeIndex(Model model){
        model.addAttribute("prefix",appPrefix);
        return prefix + "/type/index";
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("prefix",appPrefix);
        return prefix + "/index";
    }

    @PostMapping("/removeImg")
    @ResponseBody
    public OutVoGlobal removeImg(String url){
        OutVoGlobal outVoGlobal = new OutVoGlobal();
        boolean deleteFile = fileUtils.deleteFile(url);
        return outVoGlobal;
    }

    @PostMapping("/upImg")
    @ResponseBody
    public OutVoGlobal upImg(String base64){
        OutVoGlobal outVoGlobal = new OutVoGlobal();
        String saveImg = fileUtils.saveImg(base64);
        System.out.println(saveImg);
        return outVoGlobal;
    }

    private WxGoods getGoods(Map<String,Object> params) throws Exception{
        String url1 = getImgUrl(params.get("base_1"));
        String url2 = getImgUrl(params.get("base_2"));
        String url3 = getImgUrl(params.get("base_3"));
        String url4 = getImgUrl(params.get("base_4"));
        String url5 = getImgUrl(params.get("base_5"));
        WxGoods goods = JsonUtils.map2obj((Map<String, Object>) params.get("goods"), WxGoods.class);
        List<String> list = Arrays.asList(url3,url4,url5);
        String innerUrl = getInnerUrl(list);
        goods.setGoodsImg(url1);
        goods.setGoodsDetail(url2);
        goods.setGoodsCarousel(innerUrl);
        return  goods;
    }

    @PostMapping("/updateGoods")
    @ResponseBody
    public R updateGoods(@RequestBody Map<String,Object> params) throws Exception{
        WxGoods goods = getGoods(params);

        if(wxGoodsService.updateGoods(goods) > 0){
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/remove")
    @ResponseBody
    public R removeGoods(Integer id) {
        WxGoods wxGoods = wxGoodsService.getWxGoods(id);

        String url1 = wxGoods.getGoodsImg();
        String url2 = wxGoods.getGoodsDetail();
        String carousel = wxGoods.getGoodsCarousel();
        String[] strings = carousel.split(",");
        fileUtils.deleteFile(url1);
        fileUtils.deleteFile(url2);
        for(String s : strings){
            fileUtils.deleteFile(s);
        }

        if(wxGoodsService.removeGoods(id) > 0){
            return R.ok();
        }

        return R.error();


    }

    @PostMapping("/saveGoods")
    @ResponseBody
    public R saveGoods(@RequestBody Map<String,Object> params) throws Exception{
        WxGoods goods = getGoods(params);
        if(wxGoodsService.saveGoods(goods) > 0){
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/edit/{id}")
    String toEditGoods(@PathVariable String id, Model model) {
        model.addAttribute("prefix",appPrefix);
        Integer goodsId = Integer.valueOf(id);
        WxGoods wxGoods = wxGoodsService.getWxGoods(goodsId);
        model.addAttribute("goods",wxGoods);

        return prefix + "/edit";
    }

    @GetMapping("/add")
    public String getAddIndex(Model model){
        model.addAttribute("prefix",appPrefix);
        return prefix + "/add";
    }

    private  String getImgUrl(Object imgStr){
        if(!StringUtils.isEmpty(imgStr)){
            if (imgStr.toString().indexOf("http") != -1) {
                return imgStr.toString();
            }

            try{
                return fileUtils.saveImg(imgStr.toString());
            }catch (Exception e){
                return "";
            }

        }


        return "";
    }

    private static String getInnerUrl(List<String> list){
        String url = "";
        for(String s : list){
            if(!StringUtils.isEmpty(s) && !"".equals(s)){
                url += s + ",";
            }
        }
        if(url.length() > 0){
            url = url.substring(0,url.length() - 1);
        }

        return url;
    }

}
