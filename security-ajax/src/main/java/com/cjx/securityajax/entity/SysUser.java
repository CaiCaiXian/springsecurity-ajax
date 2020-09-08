package com.cjx.securityajax.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "uid")
    private String uid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    /*
    角色和用户是多对多的关系
     */
    @ManyToMany(cascade=CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",
    joinColumns = @JoinColumn(name = "uid",referencedColumnName = "uid"),
    inverseJoinColumns = @JoinColumn(name = "rid",referencedColumnName = "rid"))
    private List<SysRole> roles;

    /*
    继承了UserDetails类后会默认继承一些方法
    下面是获取用户的角色的方法，返回的角色（权限会在登陆成功后存在令牌中）
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(SysRole role : roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
