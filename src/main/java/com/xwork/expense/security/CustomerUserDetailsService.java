package com.xwork.expense.security;

import com.xwork.expense.entity.po.SysUser;
import com.xwork.expense.entity.po.SysUserRole;
import com.xwork.expense.repository.SysUserRepository;
import com.xwork.expense.repository.SysUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;


/**
 * 自定义的SpringSecurity用来做用户认证的service
 */
@Component
public class CustomerUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomerUserDetailsService.class);
    @Resource
    private SysUserRepository sysUserRepository;
    @Resource
    private SysUserRoleRepository sysUserRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        logger.debug("进入自定义的UserDetails");
        logger.debug(s);
        SysUser sysUser = sysUserRepository.getByLoginName(s);
        if(ObjectUtils.isEmpty(sysUser)){
            throw new BadCredentialsException("用户名不正确");
        }
        //设置权限进去
        List<SysUserRole> roleList = sysUserRoleRepository.listRolesByUserId(sysUser.getId());
        sysUser.setRoles(roleList);
        return sysUser;
    }
}
