package com.xwork.expense.repository;

import com.xwork.expense.entity.po.SpendingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpendingDetailRepository extends JpaRepository<SpendingDetail,Long> {

    @Query("SELECT S FROM SpendingDetail S WHERE S.project.id = :projectId")
    List<SpendingDetail> listByProjectId(@Param("projectId") Long projectId);
}
