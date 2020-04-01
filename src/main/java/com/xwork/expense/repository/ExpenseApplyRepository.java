package com.xwork.expense.repository;

import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.ExpenseApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseApplyRepository extends JpaRepository<ExpenseApply,Long> {

    @Query("SELECT E FROM ExpenseApply E WHERE E.auditState = :auditState")
    List<ExpenseApply> listByAuditState(@Param("auditState")AuditState auditState);

    @Query("SELECT E FROM ExpenseApply E WHERE E.auditState = :auditState and E.payed = :payed")
    List<ExpenseApply> listByAudiStateAndPayed(@Param("auditState")AuditState auditState,@Param("payed") Boolean payed);

    @Query("SELECT SUM(E.money) FROM ExpenseApply E WHERE E.payed = true and E.spendingDetail.id = :spendingDetailId")
    BigDecimal sumBySpendingDetailId(@Param("spendingDetailId") Long spendingDetailId);
}
