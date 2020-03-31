package com.xwork.expense.repository;

import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.ExpenseApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseApplyRepository extends JpaRepository<ExpenseApply,Long> {

    @Query("SELECT E FROM ExpenseApply E WHERE E.auditState = :auditState")
    List<ExpenseApply> listByAuditState(@Param("auditState")AuditState auditState);
}
