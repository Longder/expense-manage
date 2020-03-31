package com.xwork.expense.entity.po;

import com.xwork.expense.entity.enums.AuditState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 报销申请
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "EXPENSE_APPLY")
public class ExpenseApply extends BaseIdEntity {


    /**
     * 关联项目的开支预算详情
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "spending_detail_id_")
    private SpendingDetail spendingDetail;

    /**
     * 报销申请名称
     */
    @Column(name = "name_")
    private String name;

    /**
     * 报销原因
     */
    @Column(name = "reason_")
    private String reason;

    /**
     * 报销金额
     */
    @Column(name = "money_")
    private BigDecimal money;

    /**
     * 审核状态
     */
    @Column(name = "audit_state_")
    private AuditState auditState;

    /**
     * 上传文档的路径
     */
    @Column(name = "apply_file_path_")
    private String applyFilePath;

    /**
     * 上传过来的文件
     */
    @Transient
    private MultipartFile file;
}
