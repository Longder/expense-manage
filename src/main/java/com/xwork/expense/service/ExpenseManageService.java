package com.xwork.expense.service;

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
}
