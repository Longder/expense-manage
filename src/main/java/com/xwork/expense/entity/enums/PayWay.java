package com.xwork.expense.entity.enums;


import lombok.Getter;

/**
 * 支付方式枚举
 */
@Getter
public enum PayWay {
    BORROW_MONEY("BORROW_MONEY","冲借款"),
    PROJECT_TRANSFER("PROJECT_TRANSFER","项目转账"),
    PUBLIC_PAY("PUBLIC_PAY","对公支付"),
    PRIVATE_PAY("PRIVATE_PAY","对私支付");


    /**
     * 名称
     */
    private String name;
    /**
     * 展示名
     */
    private String displayName;

    PayWay(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
