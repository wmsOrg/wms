package com.hczx.wms.entity.planentities;

import java.util.List;

/**
 * @ClassName: AllEquipmentsInPlanEntuty
 * @Description: 预案中所有设备
 * @Date: 2020/9/16 10:52
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class AllEquipmentsInPlanEntity {

    /**警情唯一标识*/
    private String alarmingId;

    /**方案唯一标识*/
    private String schemeId;

    /**预案唯一标识*/
    private String planId;

    /**总行数*/
    private Integer totalRowNums;

    /**总条数*/
    private Integer totalColumnNums;

    /**警情唯一标识*/
    private List<CategoryEntity> categorys;

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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Integer getTotalRowNums() {
        return totalRowNums;
    }

    public void setTotalRowNums(Integer totalRowNums) {
        this.totalRowNums = totalRowNums;
    }

    public Integer getTotalColumnNums() {
        return totalColumnNums;
    }

    public void setTotalColumnNums(Integer totalColumnNums) {
        this.totalColumnNums = totalColumnNums;
    }

    public List<CategoryEntity> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<CategoryEntity> categorys) {
        this.categorys = categorys;
    }
}
