package com.xwork.expense.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

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
}
