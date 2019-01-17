package com.bootdo.system.controller.content;


import com.bootdo.app.domain.WxBanner;
import com.bootdo.app.service.WxBannerService;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.FileType;
import com.bootdo.common.utils.FileUtil;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.R;
import com.bootdo.system.params.EnumRetCode;
import com.bootdo.system.vo.OutVoGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/banner")
public class SysBannerController extends BaseController {

    private String prefix= "system/banner";

    @Autowired
    private FileService sysFileService;

    @Autowired
    private WxBannerService wxBannerService;

    @GetMapping("/list")
    @ResponseBody
    PageUtils getList(Map<String,Object> params) {
        // 查询列表数据
        List<WxBanner> list = wxBannerService.list(params);
        Integer count = wxBannerService.count(params);
        return new PageUtils(list, count);
    }

    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("prefix",appPrefix);
        return prefix + "/index";
    }

    /**
     * 更新
     */
    @ResponseBody
    @PostMapping("/update")
    public R update(WxBanner wxBanner) {
        System.out.println("wxBanner ===> " + wxBanner);
        try{
            if (wxBannerService.update(wxBanner) > 0) {
                return R.ok();
            }
            return R.error();
        }catch (Exception e){
            return R.ok();
        }
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id, HttpServletRequest request) {

        WxBanner wxBanner = wxBannerService.get(id);
        Long fileId = Long.valueOf(wxBanner.getFileId());
        String fileName = bootdoConfig.getUploadPath() + sysFileService.get(fileId).getUrl().replace("/files/", "");
        if (sysFileService.remove(fileId) > 0) {
            boolean b = FileUtil.deleteFile(fileName);
            if (!b) {
                return R.error("数据库记录删除成功，文件删除失败");
            }

            if(wxBannerService.remove(id) > 0){
                return R.ok();
            }

            return R.error();
        } else {
            return R.error();
        }
    }





    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(WxBanner wxBanner) {
        if (wxBannerService.save(wxBanner) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable String id,Model model) {
        model.addAttribute("prefix",appPrefix);
        Integer banId = Integer.valueOf(id);
        WxBanner wxBanner = wxBannerService.get(banId);
        model.addAttribute("ban",wxBanner);
        return prefix + "/edit";
    }

    @GetMapping("/add")
    String add(Model model) {
        model.addAttribute("prefix",appPrefix);
        return prefix + "/add";
    }

    @ResponseBody
    @PostMapping("/upload")
    OutVoGlobal upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        OutVoGlobal outVoGlobal = new OutVoGlobal();

        String fileName = file.getOriginalFilename();
        fileName = FileUtil.renameToUUID(fileName);


        FileDO sysFile = new FileDO(FileType.fileType(fileName),appPrefix + "/files/" + fileName, new Date());
        try {
            FileUtil.uploadFile(file.getBytes(), bootdoConfig.getUploadPath(), fileName);
        } catch (Exception e) {
            outVoGlobal.setEnum(EnumRetCode.SYSTEM_ERROR);
            return outVoGlobal;
        }

        if (sysFileService.save(sysFile) > 0) {
            outVoGlobal.setEnum(EnumRetCode.SUCCESS);
            outVoGlobal.setData(sysFile);
            return outVoGlobal;
        }
        outVoGlobal.setEnum(EnumRetCode.SYSTEM_ERROR);
        return outVoGlobal;
    }
}
