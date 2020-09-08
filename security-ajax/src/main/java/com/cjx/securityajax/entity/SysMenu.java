package com.cjx.securityajax.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -1808565992831740300L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mid")
    private Long mid;

    @Column(name = "url")
    private String url;

    @ManyToMany(mappedBy = "menus",fetch = FetchType.EAGER)
    private List<SysRole> roles;

}
