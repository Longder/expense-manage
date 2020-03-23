package com.xwork.expense.entity.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 带主键标识的实体父类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class BaseIdEntity extends BaseEntity{

    @Id
    @Column(name = "id_")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
