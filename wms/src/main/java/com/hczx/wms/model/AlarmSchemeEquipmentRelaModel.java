package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: AlarmSchemeEquipmentRelaModel
 * @Description: 警情方案设备关联表
 * @Date: 2020/9/11 15:52
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("alarm_scheme_equipment_rela")
public class AlarmSchemeEquipmentRelaModel extends Model<AlarmSchemeEquipmentRelaModel> {

    /**唯一标识*/
    @TableId(value = "id")
    private String id;

    /**警情唯一标识*/
    @TableField(value = "alarmingId")
    private String alarmingId;

    /**方案唯一标识*/
    @TableField(value = "schemeId")
    private String schemeId;

    /**方案设备关联表唯一标识*/
    @TableField(value = "seRelaId")
    private String seRelaId;

//    /**警情方案设备关联表批次号*/
//    @TableField(value = "batchNo")
//    private String batchNo;

    /**预案号*/
    @TableField(value = "planId")
    private String planId;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

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

    public String getAlarmingId() {
        return alarmingId;
    }

    public void setAlarmingId(String alarmingId) {
        this.alarmingId = alarmingId;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSeRelaId() {
        return seRelaId;
    }

    public void setSeRelaId(String seRelaId) {
        this.seRelaId = seRelaId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
