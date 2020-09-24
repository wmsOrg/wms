package com.hczx.wms.entity.planentities;

/**
 * @ClassName: PlanContentQueryEntity
 * @Description: 预案内容查询
 * @Date: 2020/9/15 17:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class PlanContentQueryEntity {

    private String alarmingId;

    private String planId;

    private String schemeId;

    private String operation;

    public String getAlarmingId() {
        return alarmingId;
    }

    public void setAlarmingId(String alarmingId) {
        this.alarmingId = alarmingId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
