package com.hczx.wms.entity.schemeequipmentrelaentities;

import java.util.List;

/**
 * @ClassName: PlanIncreaseRequestEntity
 * @Description: 预案新增请求实体
 * @Date: 2020/9/15 16:07
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class PlanIncreaseRequestEntity {

    /**预案名称*/
    private String planName;

    /**警情Id*/
    private String alarmingId;

    /**方案Id*/
    private String schemeId;

    /**预案绑定的设备*/
    private List<EquipmentsInSchemeEntity> datas;

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public List<EquipmentsInSchemeEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<EquipmentsInSchemeEntity> datas) {
        this.datas = datas;
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
}
