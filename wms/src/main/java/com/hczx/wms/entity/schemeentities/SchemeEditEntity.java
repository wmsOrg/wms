package com.hczx.wms.entity.schemeentities;

import java.util.List;

/**
 * @ClassName: SchemeEditEntity
 * @Description: 方案新增实体
 * @Date: 2020/9/9 10:34
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class SchemeEditEntity {

    /**方案id*/
    private String schemeId;

    /**最大层数*/
    private Integer maxRows;

    /**最大列数*/
    private Integer maxColumns;

    /**方案展示图片*/
    private String image;

    /**绑定单元数据*/
    private List<SchemeBindEquipmentEntity> datas;

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

    public List<SchemeBindEquipmentEntity> getDatas() {
        return datas;
    }

    public void setDatas(List<SchemeBindEquipmentEntity> datas) {
        this.datas = datas;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
