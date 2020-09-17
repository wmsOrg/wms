package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: PlanModel
 * @Description: 预案实体映射表
 * @Date: 2020/9/15 15:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "plan")
public class PlanModel extends Model<PlanModel> {

    /**预案唯一标识*/
    @TableId("id")
    private String id;

    /**预案名*/
    @TableField("planName")
    private String planName;

    /**警情唯一标识*/
    @TableField("alarmingId")
    private String alarmingId;

    /**方案唯一标识*/
    @TableField("schemeId")
    private String schemeId;

    /**启用标识*/
    @TableField("useState")
    private String useState;

    /**预案图片地址*/
    @TableField("imageUrl")
    private String imageUrl;

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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getAlarmingId() {
        return alarmingId;
    }

    public void setAlarmingId(String alarmingId) {
        this.alarmingId = alarmingId;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
