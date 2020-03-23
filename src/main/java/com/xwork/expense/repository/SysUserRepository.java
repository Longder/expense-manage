package com.xwork.expense.repository;


import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    @Query("select s from SysUser s where s.loginName = :loginName")
    SysUser getByLoginName(@Param("loginName") String loginName);

    @Query("select distinct s from SysUser s left join s.roles r where r.role = :role ")
    List<SysUser> listByRole(@Param("role") SysRole role);
}
