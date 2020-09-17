package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SchemeEquipmentRelaModel
 * @Description:  方案设备绑定实体映射表
 * @Date: 2020/9/9 15:20
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "scheme_equipment_rela")
public class SchemeEquipmentRelaModel extends Model<SchemeEquipmentRelaModel> {

    @TableId(value = "id")
    private String id;

    /**方案Id*/
    @TableField(value = "schemeId")
    private String schemeId;

    /**设备Id*/
    @TableField(value = "equipmentId")
    private String equipmentId;

    /**行数*/
    @TableField(value = "rowNum")
    private Integer rowNum;

    /**列数*/
    @TableField(value = "columnNum")
    private Integer columnNum;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
