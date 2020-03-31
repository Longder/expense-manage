package com.xwork.expense.service;

import com.xwork.expense.entity.dto.ExpensePayDto;
import com.xwork.expense.entity.dto.JoinProjectDto;
import com.xwork.expense.entity.po.ExpenseApply;

import java.util.List;

/**
 * 报销管理的service
 */
public interface ExpenseManageService {

    /**
     * 查询所有报销申请
     * @return
     */
    List<ExpenseApply> listAllExpenseApply();

    /**
     * 添加一个报销申请
     */
    void addOneExpenseApply(ExpenseApply expenseApply);

    /**
     * 财务查询待关联的报销申请
     * @return
     */
    List<ExpenseApply> listForFinance();

    /**
     * 获取一个报销申请
     * @param expenseApplyId
     * @return
     */
    ExpenseApply getOneExpenseApply(Long expenseApplyId);

    /**
     * 报销申请关联项目
     * @param joinProjectDto
     */
    void joinProject(JoinProjectDto joinProjectDto);

    /**
     * 审核用的报销申请列表
     * @return
     */
    List<ExpenseApply> listForAudit();

    /**
     * 审核报销申请
     * @param expenseApplyId
     */
    void auditExpenseApply(Long expenseApplyId);

    /**
     * 出纳查看的待支付的申请列表
     * @return
     */
    List<ExpenseApply> listForCashier();

    /**
     * 支付报销
     * @param expensePayDto
     */
    void payExpense(ExpensePayDto expensePayDto);
}
