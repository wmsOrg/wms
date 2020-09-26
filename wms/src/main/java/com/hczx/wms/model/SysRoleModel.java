package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysRoleModel
 * @Description: 角色映射实体
 * @Date: 2020/9/25 14:12
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("sys_role")
public class SysRoleModel extends Model<SysRoleModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**角色名称*/
    @TableField("roleName")
    private String role_name;

    /**创建时间*/
    @TableField("create_time")
    private Date createTime;

    /**更新时间*/
    @TableField("update_time")
    private Date updateTime;

    /**生效标识 1：生效 0：失效*/
    @TableField("valid_status")
    private String validStatus;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
}
