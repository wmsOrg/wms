package com.hczx.wms.entity.schemeequipmentrelaentities;

import com.hczx.wms.entity.schemeentities.SchemeBindEquipmentEntity;

import java.util.List;

/**
 * @ClassName: SchemeEditEntity
 * @Description: 方案新增实体
 * @Date: 2020/9/9 10:34
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class SchemeRelaEquipmentsEntity {

    /**方案id*/
    private String schemeId;

    /**最大层数*/
    private Integer maxRows;

    /**最大列数*/
    private Integer maxColumns;

    /**绑定单元数据*/
    private List<EquipmentsInSchemeEntity> datas;

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }

    public Integer getMaxColumns() {
        return maxColumns;
    }

    public void setMaxColumns(Integer maxColumns) {
        this.maxColumns = maxColumns;
    }

    public List<EquipmentsInSchemeEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<EquipmentsInSchemeEntity> datas) {
        this.datas = datas;
    }
}
