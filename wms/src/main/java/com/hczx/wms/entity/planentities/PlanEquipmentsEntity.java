package com.hczx.wms.entity.planentities;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Objects;

/**
 * @ClassName: PlanQueryEntity
 * @Description:
 * @Date: 2020/9/15 16:46
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class PlanEquipmentsEntity {

    private String planId;

    private String alarmRecordId;

    private String alarmingId;

    private String schemeId;

    private String equipmentId;

    private String equipmentRfid;

    private String equipmentInBoundState;

    private String inoutState;

    private Boolean waitOperatingFlag;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentRfid() {
        return equipmentRfid;
    }

    public void setEquipmentRfid(String equipmentRfid) {
        this.equipmentRfid = equipmentRfid;
    }

    public String getEquipmentInBoundState() {
        return equipmentInBoundState;
    }

    public void setEquipmentInBoundState(String equipmentInBoundState) {
        this.equipmentInBoundState = equipmentInBoundState;
    }

    public String getAlarmRecordId() {
        return alarmRecordId;
    }

    public void setAlarmRecordId(String alarmRecordId) {
        this.alarmRecordId = alarmRecordId;
    }

    public Boolean getWaitOperatingFlag() {
        return waitOperatingFlag;
    }

    public void setWaitOperatingFlag(Boolean waitOperatingFlag) {
        this.waitOperatingFlag = waitOperatingFlag;
    }

    public String getInoutState() {
        return inoutState;
    }

    public void setInoutState(String inoutState) {
        this.inoutState = inoutState;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanEquipmentsEntity that = (PlanEquipmentsEntity) o;
        return Objects.equals(planId, that.planId) &&
                Objects.equals(alarmRecordId, that.alarmRecordId) &&
                Objects.equals(alarmingId, that.alarmingId) &&
                Objects.equals(schemeId, that.schemeId) &&
                Objects.equals(equipmentId, that.equipmentId) &&
                Objects.equals(equipmentRfid, that.equipmentRfid) &&
                Objects.equals(equipmentInBoundState, that.equipmentInBoundState) &&
                Objects.equals(inoutState, that.inoutState) &&
                Objects.equals(waitOperatingFlag, that.waitOperatingFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, alarmRecordId, alarmingId, schemeId, equipmentId, equipmentRfid, equipmentInBoundState, inoutState, waitOperatingFlag);
    }
}
