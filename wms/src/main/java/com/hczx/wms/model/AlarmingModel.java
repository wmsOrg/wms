package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: AlarmingModel
 * @Description: 警情表映射实体
 * @Date: 2020/9/10 10:41
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("alarming")
public class AlarmingModel extends Model<AlarmingModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**警情名称*/
    @TableField(value = "name")
    private String name;

    /**警情级别   1:一级 2：二级 3：三级...*/
    @TableField(value = "level")
    private String level;

    /**警情类别 0：地震 1：火灾 2：洪灾*/
    @TableField(value = "category")
    private String category;

    /**警情描述*/
    @TableField(value = "discribetion")
    private String discribetion;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

    /**创建人Id*/
    @TableField(value = "createUserId")
    private String createUserId;

    /**创建人姓名*/
    @TableField(value = "createUserName")
    private String createUserName;

    /**生效状态  1：生效  0：失效*/
    @TableField(value = "validState")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscribetion() {
        return discribetion;
    }

    public void setDiscribetion(String discribetion) {
        this.discribetion = discribetion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }
}
