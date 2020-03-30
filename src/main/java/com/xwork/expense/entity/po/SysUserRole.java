package com.xwork.expense.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * 用户角色关联
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "SYS_USER_ROLE")
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole extends BaseIdEntity implements GrantedAuthority {

    /**
     * 用户id
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "sys_user_id_",referencedColumnName = "id_")
    private SysUser sysUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_")
    private SysRole role;

    @Override
    public String getAuthority() {
        return this.role.getName();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysUserRole{");
        sb.append("role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
