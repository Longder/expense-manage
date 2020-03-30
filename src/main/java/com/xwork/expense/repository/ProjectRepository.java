package com.xwork.expense.repository;


import com.xwork.expense.entity.po.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
