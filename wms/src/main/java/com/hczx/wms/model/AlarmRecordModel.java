package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: AlarmRecordModel
 * @Description: 警情实体映射表
 * @Date: 2020/9/23 16:41
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("alarm_record")
public class AlarmRecordModel extends Model<AlarmRecordModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**启用的方案ID*/
    @TableField(value = "scheme_id")
    private String schemeId;

    /**警情发生时间，默认当前时间*/
    @TableField(value = "occur_date")
    private Date occurDate;

    /**出库器具数量*/
    @TableField(value = "out_count")
    private Integer outCount;

    /**入库器具数量*/
    @TableField(value = "in_count")
    private Integer inCount;

    /**方案涉及的器具数量*/
    @TableField(value = "scheme_count")
    private Integer schemeCount;

    /**警情描述*/
    @TableField(value = "remark")
    private String remark;

    /**创建者*/
    @TableField(value = "created_by")
    private String created_by;

    /**创建时间，也即出入库时间*/
    @TableField(value = "created_date")
    private Date createdDate;

    /**创建者*/
    @TableField(value = "last_modified_by")
    private String lastModifiedBy;

    /**修改时间*/
    @TableField(value = "last_modified_date")
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

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Date getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(Date occurDate) {
        this.occurDate = occurDate;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public Integer getSchemeCount() {
        return schemeCount;
    }

    public void setSchemeCount(Integer schemeCount) {
        this.schemeCount = schemeCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
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
}
