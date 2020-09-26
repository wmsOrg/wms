package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysUseRoleModel
 * @Description:  用户角色映射实体表
 * @Date: 2020/9/25 14:04
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("sys_use_role")
public class SysUseRoleModel extends Model<SysUseRoleModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**角色唯一标识*/
    @TableField("role_id")
    private String roleId;

    /**用户唯一标识*/
    @TableField("user_id")
    private String userId;

    /**创建时间*/
    @TableField("create_time")
    private Date createTime;

    /**更新时间*/
    @TableField("update_time")
    private Date updateTime;

    /**生效标识 1：生效 0：失效*/
    @TableField("valid_state")
    private String validState;

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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }
}
