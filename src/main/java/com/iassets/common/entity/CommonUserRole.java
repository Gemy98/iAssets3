/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Basic
 *  javax.persistence.Cacheable
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.GeneratedValue
 *  javax.persistence.GenerationType
 *  javax.persistence.Id
 *  javax.persistence.NamedQueries
 *  javax.persistence.NamedQuery
 *  javax.persistence.Table
 */
package com.iassets.common.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Cacheable
@Entity
@NamedQueries(value={@NamedQuery(name="CommonUserRole.findAll", query="SELECT u FROM CommonUserRole u"), @NamedQuery(name="CommonUserRole.findByUserName", query="SELECT u FROM CommonUserRole u WHERE u.userName=:userName"), @NamedQuery(name="CommonUserRole.updateUsername", query="UPDATE CommonUserRole SET userName = :newusername WHERE userName = :oldusername")})
@Table(name="common_user_role")
public class CommonUserRole
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional=false)
    private Integer id;
    @Column(name="user_name")
    private String userName;
    @Column(name="role_name")
    private String roleName;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
