package com.xwork.expense.service;

import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;

import java.util.List;

public interface UserMangeService {


    /**
     * 检查登录名
     * @param loginName
     * @return true:可以添加  false：不能添加
     */
    Boolean checkLoginName(String loginName);

    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> listAllUser();

    /**
     * 保存一个用户
     * @param sysUser
     * @param sysRole
     */
    void saveOneUser(SysUser sysUser, SysRole sysRole);

    /**
     * 删除一个用户
     * @param sysUserId
     */
    void deleteOneUser(Long sysUserId);
}
