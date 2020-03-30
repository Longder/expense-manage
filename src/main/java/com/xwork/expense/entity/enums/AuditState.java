package com.xwork.expense.entity.enums;

import lombok.Getter;

/**
 * 审核状态
 */
@Getter
public enum AuditState {
    PRE_AUDIT("PRE_AUDIT", "待审核"),
    AUDIT_L1("AUDIT_L1", "初审通过"),
    AUDIT_L2("AUDIT_L2", "复审通过"),
    AUDIT_L3("AUDIT_L3", "核定通过");
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
