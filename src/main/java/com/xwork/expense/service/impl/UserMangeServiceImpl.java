package com.xwork.expense.service.impl;

import com.xwork.expense.entity.po.SysRole;
import com.xwork.expense.entity.po.SysUser;
import com.xwork.expense.entity.po.SysUserRole;
import com.xwork.expense.repository.SysUserRepository;
import com.xwork.expense.repository.SysUserRoleRepository;
import com.xwork.expense.service.UserMangeService;
import com.xwork.expense.util.EncryptionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户管理相关业务
 */
@Service
public class UserMangeServiceImpl implements UserMangeService {

    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysUserRoleRepository sysUserRoleRepository;

    /**
     * 检查登录名
     *
     * @param loginName 登录名
     * @return true:可以添加  false：不能添加
     */
    @Override
    public Boolean checkLoginName(String loginName) {
        SysUser sysUser = sysUserRepository.getByLoginName(loginName);
        return ObjectUtils.isEmpty(sysUser);
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<SysUser> listAllUser() {
        List<SysUser> sysUserList = sysUserRepository.findAll();
        //封装角色
        sysUserList.forEach(sysUser -> sysUser.setRole(sysUser.getRoles().get(0).getRole()));
        return sysUserList;
    }

    /**
     * 保存一个用户
     *
     * @param sysUser
     * @param sysRole
     */
    @Override
    @Transactional
    public void saveOneUser(SysUser sysUser, SysRole sysRole) {
        if(ObjectUtils.isEmpty(sysUser.getId())){
            //处理下密码
            sysUser.setPassword(EncryptionUtil.encrypt(sysUser.getPassword().trim()));
            //默认是有效的
            sysUserRepository.save(sysUser);
            SysUserRole userRole = new SysUserRole(sysUser,sysRole);
            sysUserRoleRepository.save(userRole);
        }
    }
}
