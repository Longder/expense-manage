package com.xwork.expense.controller;

import com.xwork.expense.entity.enums.SpendingType;
import com.xwork.expense.entity.po.Project;
import com.xwork.expense.entity.po.SpendingDetail;
import com.xwork.expense.service.ProjectManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/project")
@Slf4j
public class ProjectManageController {

    @Resource
    private ProjectManageService projectManageService;

    /**
     * 会计用的项目管理列表
     *
     * @return
     */
    @GetMapping("/listForFinance")
    public String listForFinance(Model model) {
        //会计能看到所有的项目
        model.addAttribute("list", projectManageService.listAllProject());
        return "project/list-for-finance";
    }


    /**
     * 初始化申请项目的页面
     *
     * @return
     */
    @GetMapping("/initApplyProject")
    public String initApplyProject(Model model) {
        Map<String, String> map = new HashMap<>();
        Arrays.stream(SpendingType.values()).forEach(spendingType -> {
            map.put(spendingType.getName(), spendingType.getDisplayName());
        });
        model.addAttribute("spendingTypes", map);
        return "project/init-apply-project-modal";
    }

    /**
     * 申请项目
     *
     * @return
     */
    @PostMapping("/applyProject")
    public String applyProject(Project project) {
        projectManageService.applyProject(project);
        return "redirect:/admin/project/listForFinance";
    }

    /**
     * 审批人查看项目列表
     *
     * @return
     */
    @GetMapping("/listForAudit")
    public String listForAudit(Model model) {
        model.addAttribute("list", projectManageService.listForAudit());
        return "project/list-for-audit";
    }

    /**
     * 去审批项目
     *
     * @param projectId
     * @return
     */
    @GetMapping("/toAudit")
    public String toAuditProject(Long projectId, Model model) {
        model.addAttribute("project", projectManageService.getOneProject(projectId));
        return "project/audit-project-modal";
    }

    /**
     * 审核项目
     *
     * @param projectId
     * @return
     */
    @PostMapping("/audit")
    public String auditProject(Long projectId,String result) {
        log.info("projectId:" + projectId);
        projectManageService.auditProject(projectId,result);
        return "redirect:/admin/project/listForAudit";
    }

    /**
     * 项目开支详情
     *
     * @param projectId
     * @return
     */
    @PostMapping("/listDetail")
    @ResponseBody
    public List<SpendingDetail> listDetail(Long projectId) {
        return projectManageService.listSpendingDetail(projectId);
    }

    /**
     * 项目信息列表，只查询已经审批过的项目，主要是查看用
     *
     * @return
     */
    @GetMapping("/listForInfo")
    public String listForInfo(Model model) {
        model.addAttribute("list", projectManageService.listAllAudited());
        return "project/list-for-info";
    }

    /**
     * 项目报销详情
     * @return
     */
    @GetMapping("/projectDetail")
    public String projectExpenseDetail(Model model,Long projectId){
        model.addAttribute("project", projectManageService.getOneProject(projectId));
        return "project/detail-modal";
    }
}
