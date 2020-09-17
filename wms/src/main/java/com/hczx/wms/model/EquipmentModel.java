package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: EquipmentModel
 * @Description: 设备映射表
 * @Date: 2020/9/1 09:59
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "equipment")
public class EquipmentModel extends Model<EquipmentModel> {

    /**主键，UUID*/
    @TableId(value = "id")
    private String id;

    /**设备id*/
    @TableField(value = "equipmentRfid")
    private String equipmentRfid;

    /**设备名称*/
    @TableField(value = "equipmentName")
    private String equipmentName;

    /**设备等级*/
    @TableField(value = "equipmentGrade")
    private String equipmentGrade;

    /**设备种类*/
    @TableField(value = "equipmentClass")
    private String equipmentClass;

    /**设备种类名称*/
    @TableField(value = "equipmentClassName")
    private String equipmentClassName;

    /**设备上级id*/
    @TableField(value = "equipmentPreId")
    private String equipmentPreId;

    /**设备所属公司id*/
    @TableField(value = "equipmentCompanyId")
    private String equipmentCompanyId;

    /**设备所属公司名称*/
    @TableField(value = "equipmentCompanyName")
    private String equipmentCompanyName;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

    /**修改时间*/
    @TableField(value = "editTime")
    private Date editTime;

    /**设备所属方案id*/
    @TableField(value = "schemeId")
    private String schemeId;

    /**设备所属方案名称*/
    @TableField(value = "schemeName")
    private String schemeName;

    /**生效状态  0：失效   1：生效*/
    @TableField(value = "validState")
    private String validState;

    /**占用状态 0：不占用   1：占用*/
    @TableField(value = "occupyState")
    private String occupyState;

    /**入库状态 0：未定义 1：入库  -1：出库*/
    @TableField(value = "inboundState")
    private String inboundState;

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

    public String getEquipmentRfid() {
        return equipmentRfid;
    }

    public void setEquipmentRfid(String equipmentRfid) {
        this.equipmentRfid = equipmentRfid;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentGrade() {
        return equipmentGrade;
    }

    public void setEquipmentGrade(String equipmentGrade) {
        this.equipmentGrade = equipmentGrade;
    }

    public String getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(String equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public String getEquipmentPreId() {
        return equipmentPreId;
    }

    public void setEquipmentPreId(String equipmentPreId) {
        this.equipmentPreId = equipmentPreId;
    }

    public String getEquipmentCompanyId() {
        return equipmentCompanyId;
    }

    public void setEquipmentCompanyId(String equipmentCompanyId) {
        this.equipmentCompanyId = equipmentCompanyId;
    }

    public String getEquipmentCompanyName() {
        return equipmentCompanyName;
    }

    public void setEquipmentCompanyName(String equipmentCompanyName) {
        this.equipmentCompanyName = equipmentCompanyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public String getOccupyState() {
        return occupyState;
    }

    public void setOccupyState(String occupyState) {
        this.occupyState = occupyState;
    }

    public String getEquipmentClassName() {
        return equipmentClassName;
    }

    public void setEquipmentClassName(String equipmentClassName) {
        this.equipmentClassName = equipmentClassName;
    }

    public String getInboundState() {
        return inboundState;
    }

    public void setInboundState(String inboundState) {
        this.inboundState = inboundState;
    }
}
