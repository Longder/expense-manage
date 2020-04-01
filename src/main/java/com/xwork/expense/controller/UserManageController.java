package com.xwork.expense.controller;

import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;
import com.xwork.expense.service.UserMangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户管理的控制器
 */
@Controller
@RequestMapping("/admin/user")
public class UserManageController {

    @Resource
    private UserMangeService userMangeService;

    /**
     * 用户列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list",userMangeService.listAllUser());
        return "user/list";
    }

    /**
     * 去添加用户
     * @return
     */
    @GetMapping("/toAdd")
    public String toAdd(Model model){
        model.addAttribute("roles", SysRole.values());
        return "user/add-user-modal";
    }

    /**
     * 检查登录名
     * @return
     */
    @ResponseBody
    @PostMapping("/checkLoginName")
    public Boolean checkLoginName(String loginName){
        return userMangeService.checkLoginName(loginName);
    }

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @PostMapping("/add")
    public String add(SysUser sysUser){
        userMangeService.saveOneUser(sysUser,sysUser.getRole());
        return "redirect:/admin/user/list";
    }

    /**
     * 删除用户
     * @param sysUserId
     * @return
     */
    @GetMapping("/delete/{sysUserId}")
    public String delete(@PathVariable("sysUserId") Long sysUserId){
        userMangeService.deleteOneUser(sysUserId);
        return "redirect:/admin/user/list";
    }
}

