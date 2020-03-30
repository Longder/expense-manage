package com.xwork.expense.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/project")
public class ProjectManageController {

    /**
     * 会计用的项目管理列表
     * @return
     */
    @GetMapping("/listForFinance")
    public String listForFinance(Model model){

        return "project/listForFinance";
    }
}
