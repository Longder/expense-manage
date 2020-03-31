package com.xwork.expense.repository;


import com.xwork.expense.entity.enums.AuditState;
import com.xwork.expense.entity.po.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    /**
     * 根据审核级别查询
     * @param auditState
     * @return
     */
    @Query("SELECT P FROM Project P WHERE P.auditState =:auditState")
    List<Project> listByAuditState(@Param("auditState") AuditState auditState);
}
