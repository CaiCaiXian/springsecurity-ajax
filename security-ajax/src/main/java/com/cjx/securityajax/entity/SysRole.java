package com.cjx.securityajax.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 4395377670162987328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rid")
    private Long rid;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<SysUser> users;

    /*
    角色和url也是多对多的关系
     */
    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "rid",referencedColumnName = "rid")
    ,inverseJoinColumns = @JoinColumn(name = "mid",referencedColumnName = "mid"))
    private List<SysMenu> menus;


    @Override
    public String getAuthority() {
        return roleName;
    }

}
