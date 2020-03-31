package com.xwork.expense.entity.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 报销关联项目表单的dto
 */
@Data
public class JoinProjectDto implements Serializable {

    /**
     * 报销申请id
     */
    private Long expenseApplyId;

    /**
     * 项目开支详情id
     */
    private Long spendingDetailId;
}
