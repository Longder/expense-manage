package com.xwork.expense.entity.enums;

import lombok.Getter;

/**
 * 开支类型的枚举
 */
@Getter
public enum SpendingType {
    BUSINESS_TRIP("BUSINESS_TRIP","出差费用"),
    TRANSPORTATION("TRANSPORTATION","交通费"),
    COMMUNICATION("COMMUNICATION","通信费"),
    OFFICE_SUPPLIES("OFFICE_SUPPLIES","办公用品费");

    /**
     * 名称
     */
    private String name;
    /**
     * 展示名
     */
    private String displayName;

    SpendingType(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
