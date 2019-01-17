package com.bootdo.system.controller;

import com.bootdo.common.annotation.Log;
import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.FileService;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.domain.MenuDO;
import com.bootdo.system.service.MenuService;
import com.bootdo.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class LoginController extends BaseController {


    @Autowired
    MenuService menuService;
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

/*    @GetMapping({"/", ""})
    String welcome(Model model) {
        model.addAttribute("prefix",appPrefix);
        return "redirect:/login";
    }*/

    @GetMapping({"/index"})
    String index(Model model) {
        model.addAttribute("prefix",appPrefix);
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        FileDO fileDO = fileService.get(getUser().getPicId());
        if (fileDO != null && fileDO.getUrl() != null) {
            if (fileService.isExist(fileDO.getUrl())) {
                model.addAttribute("picUrl", fileDO.getUrl());
            } else {
                model.addAttribute("picUrl", appPrefix + "/img/photo_s.jpg");
            }
        } else {
            model.addAttribute("picUrl", appPrefix + "/img/photo_s.jpg");
        }
        model.addAttribute("username", getUser().getUsername());
        return "index_v1";
    }

    @GetMapping("/login")
    String login(Model model) {
        model.addAttribute("prefix",appPrefix);
        return "login";
    }

    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(String username, String password) {
        //// TODO: 2018/8/1 密码加密方法
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            //UserDO user = userService.getByAccountPassword(username, password);
            subject.login(token);
            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout(Model model) {
        ShiroUtils.logout();
        model.addAttribute("prefix",appPrefix);
        return "login";
    }

    @GetMapping("/main")
    String main(Model model) {
        model.addAttribute("prefix",appPrefix);
        return "main";
    }

}
