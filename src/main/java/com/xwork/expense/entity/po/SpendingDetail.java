package com.xwork.expense.entity.po;

import com.xwork.expense.entity.enums.SpendingType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 项目开支详情
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "SPENDING_DETAIL")
public class SpendingDetail extends BaseIdEntity{

    /**
     * 所属项目
     */
    @ManyToOne
    @JoinColumn(name = "project_id_")
    private Project project;

    /**
     * 开支类型
     */
    @Column(name = "spending_type_")
    private SpendingType type;

    /**
     * 预算金额
     */
    @Column(name = "budget_")
    private BigDecimal budget;
}
