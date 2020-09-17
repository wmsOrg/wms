package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: UserModel
 * @Description: 用户映射表
 * @Date: 2020/9/1 09:59
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "user")
public class UserModel extends Model<UserModel> {

    /**主键，UUID*/
    @TableId(value = "id")
    private String id;

    /**姓名*/
    @TableField(value = "name")
    private String name;

    /**性别*/
    @TableField(value = "sex")
    private String sex;

    /**电话*/
    @TableField(value = "phone")
    private String phone;

    /**电话*/
    @TableField(value = "mobile")
    private String mobile;

    /**邮箱号码*/
    @TableField(value = "emailNum")
    private String emailNum;

    /**组织号码，UUID*/
    @TableField(value = "organizationId")
    private String organizationId;

    /**登录账号*/
    @TableField(value = "loginName")
    private String loginName;

    /**密码*/
    @TableField(value = "password")
    private String password;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

    /**登录时间*/
    @TableField(value = "loginTime")
    private Date loginTime;

    /**上次登录时间*/
    @TableField(value = "lastLoginTime")
    private Date lastLoginTime;

    /**登录次数*/
    @TableField(value = "loginCount")
    private Integer loginCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmailNum() {
        return emailNum;
    }

    public void setEmailNum(String emailNum) {
        this.emailNum = emailNum;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
