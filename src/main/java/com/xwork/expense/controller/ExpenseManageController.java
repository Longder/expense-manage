package com.xwork.expense.controller;

import com.xwork.expense.entity.dto.JoinProjectDto;
import com.xwork.expense.entity.po.ExpenseApply;
import com.xwork.expense.service.ExpenseManageService;
import com.xwork.expense.service.ProjectManageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 报销管理的控制器
 */
@Controller
@RequestMapping("/admin/expense")
public class ExpenseManageController {
    @Resource
    private ExpenseManageService expenseManageService;

    @Resource
    private ProjectManageService projectManageService;

    /**
     * 员工查看的申请列表
     *
     * @return
     */
    @GetMapping("/listForEmployee")
    public String listExpenseApplyForEmployee(Model model) {
        model.addAttribute("list", expenseManageService.listAllExpenseApply());
        return "expense/list-for-employee";
    }

    /**
     * 初始化报销申请
     *
     * @return
     */
    @GetMapping("/initApply")
    public String initExpenseApply() {
        return "expense/apply-expense-modal";
    }

    /**
     * 申请报销
     *
     * @param expenseApply
     * @return
     */
    @PostMapping("/apply")
    public String applyExpense(ExpenseApply expenseApply) {
        expenseManageService.addOneExpenseApply(expenseApply);
        return "redirect:/admin/expense/listForEmployee";
    }

    @GetMapping("/listForFinance")
    public String listExpenseApplyForFinance(Model model) {
        model.addAttribute("list", expenseManageService.listForFinance());
        return "expense/list-for-finance";
    }

    /**
     * 去关联项目
     *
     * @param model
     * @param expenseApplyId
     * @return
     */
    @GetMapping("/toJoinProject")
    public String toJoinProject(Model model, Long expenseApplyId) {
        model.addAttribute("apply", expenseManageService.getOneExpenseApply(expenseApplyId));
        model.addAttribute("projects",projectManageService.listAllAudited());
        return "expense/join-project-modal";
    }

    /**
     * 报销申请关联项目
     * @param joinProjectDto
     * @return
     */
    @PostMapping("/joinProject")
    public String joinProject(JoinProjectDto joinProjectDto){
        return "redirect:/admin/expense/listForFinance";
    }

    /**
     * 下载上传的文件
     *
     * @param response
     */
    @GetMapping("/download")
    public void downloadDoc(HttpServletResponse response, Long expenseApplyId) {
        ExpenseApply apply = expenseManageService.getOneExpenseApply(expenseApplyId);
        Path file = Paths.get(apply.getApplyFilePath());
        if (Files.exists(file)) {
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;fileName=申请材料");
            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


}
