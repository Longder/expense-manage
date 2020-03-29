package com.xwork.expense.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主控制器
 */
@Controller
@Slf4j
public class MainController {

    /**
     * 主页默认跳转到登陆页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "redirect:toLogin";
    }

    /**
     * 去登陆页
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 后台主页,登陆后访问的首页
     * @return
     */
    @GetMapping("/admin/index")
    public String adminIndex(){
        log.debug("去后台主页");
        return "index";
    }

    /**
     * 仪表盘，欢迎页面
     * @return
     */
    @GetMapping("/admin/dashboard")
    public String dashboard(){
        return "dashboard";
    }

}
