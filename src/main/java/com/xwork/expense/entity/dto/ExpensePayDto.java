package com.xwork.expense.entity.dto;

import com.xwork.expense.entity.enums.PayWay;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExpensePayDto implements Serializable {
    /**
     * 申请id
     */
    private Long expenseApplyId;
    /**
     * 支付方式
     */
    private PayWay payWay;
}
