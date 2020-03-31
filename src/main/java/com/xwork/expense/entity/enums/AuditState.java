package com.xwork.expense.entity.enums;

import lombok.Getter;

/**
 * 审核状态
 */
@Getter
public enum AuditState {
    PRE_AUDIT("PRE_AUDIT", "待审核"),
    FINANCE_AUDIT("FINANCE_AUDIT","已关联项目"),
    AUDIT_L1("AUDIT_L1", "一级审核通过"),
    AUDIT_L2("AUDIT_L2", "二级审核通过"),
    AUDIT_L3("AUDIT_L3", "三级审核通过");
    /**
     * 名称
     */
    private String name;
    /**
     * 展示名
     */
    private String displayName;

    AuditState(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }
}
