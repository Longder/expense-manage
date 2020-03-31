package com.xwork.expense.entity.po;

import com.xwork.expense.entity.enums.AuditState;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;

/**
 * 财务立项，项目
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "PROJECT")
public class Project extends BaseIdEntity{

    /**
     * 项目名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 项目描述
     */
    @Column(name = "description_")
    private String description;

    /**
     * 总预算
     */
    @Column(name = "total_budget_")
    private BigDecimal totalBudget;

    /**
     * 审核状态
     */
    @Column(name = "audit_state_")
    private AuditState auditState;

    /**
     * 是否激活
     */
    @Column(name = "active_")
    private Boolean active;

    /**
     * 项目预算详情集合，不持久化，dto用
     */
    @Transient
    private List<SpendingDetail> detailList;
}
