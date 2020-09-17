package com.hczx.wms.entity.enquipmententities;

import java.util.List;

/**
 * @ClassName: EquipmentZtreeEntity
 * @Description:
 * @Date: 2020/9/7 15:26
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentZtreeEntity {

    private boolean errorFlag;

    private String errMsg;

    private List<EquipmentZtreeNode> data;

    public boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(boolean errorFlag) {
        this.errorFlag = errorFlag;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<EquipmentZtreeNode> getData() {
        return data;
    }

    public void setData(List<EquipmentZtreeNode> data) {
        this.data = data;
    }
}
