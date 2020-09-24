package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: EquipmentInOutRecordModel
 * @Description: 设备出入库实体映射表
 * @Date: 2020/9/23 16:32
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("equipment_in_out_record")
public class EquipmentInOutRecordModel extends Model<EquipmentInOutRecordModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**设备唯一标识*/
    @TableField("equipment_id")
    private String equipmentId;

    @TableField("equipment_rfid")
    private String equipmentRfid;

    /**警情记录唯一标识*/
    @TableField("alarm_record_id")
    private String alarmRecordId;

    /**方向，10-出库，20-入库*/
    @TableField("direction")
    private String direction;

    /**方案ID：目前关联planId*/
    @TableField("scheme_id")
    private String schemeId;

    /**创建者*/
    @TableField("created_by")
    private String createdBy;

    /**创建时间，也即出入库时间*/
    @TableField("created_date")
    private Date createdDate;

    /**修改者*/
    @TableField("last_modified_by")
    private String lastModifiedBy;

    /**修改时间*/
    @TableField("last_modified_date")
    private Date lastModifiedDate;


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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getAlarmRecordId() {
        return alarmRecordId;
    }

    public void setAlarmRecordId(String alarmRecordId) {
        this.alarmRecordId = alarmRecordId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getEquipmentRfid() {
        return equipmentRfid;
    }

    public void setEquipmentRfid(String equipmentRfid) {
        this.equipmentRfid = equipmentRfid;
    }
}
