package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: CompanyModel
 * @Description: 公司实体映射
 * @Date: 2020/9/7 17:10
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "company")
public class CompanyModel extends Model<CompanyModel> {

    /**主键id*/
    @TableId("id")
    private String id;

    /**公司编码*/
    @TableField("companyCode")
    private String companyCode;

    /**公司简称*/
    @TableField("companyAbbreviation")
    private String companyAbbreviation;

    /**公司名称*/
    @TableField("companyName")
    private String companyName;

    /**公司地址*/
    @TableField("companyAddress")
    private String companyAddress;

    /**公司电话*/
    @TableField("companyTel")
    private String companyTel;

    /**公司法人*/
    @TableField("corporation")
    private String corporation;

    /**生效状态*/
    @TableField("validState")
    private String validState;

    /**创建时间*/
    @TableField("createTime")
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

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyAbbreviation() {
        return companyAbbreviation;
    }

    public void setCompanyAbbreviation(String companyAbbreviation) {
        this.companyAbbreviation = companyAbbreviation;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCorporation() {
        return corporation;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
