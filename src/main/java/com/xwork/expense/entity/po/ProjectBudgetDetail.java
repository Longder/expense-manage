package com.xwork.expense.entity.po;

import com.xwork.expense.entity.enums.SpendingType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 项目预算详情，项目中每一项的预算
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "PROJECT_BUDGET_DETAIL")
public class ProjectBudgetDetail extends BaseIdEntity {

    /**
     * 关联项目
     */
    @ManyToOne
    @JoinColumn(name = "project_id_")
    private Project project;

    /**
     * 开支类型
     */
    @Column(name = "spending_type_")
    private SpendingType spendingType;

    /**
     * 预算值
     */
    @Column(name = "budget_")
    private BigDecimal budget;

}
