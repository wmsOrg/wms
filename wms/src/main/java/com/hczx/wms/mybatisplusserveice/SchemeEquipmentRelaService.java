package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.model.SchemeEquipmentRelaModel;
import com.hczx.wms.model.SchemeModel;

import java.util.List;

/**
 * @ClassName: SchemeEquipmentRelaService
 * @Description: 方案设备绑定实体映射接口层
 * @Date: 2020/9/9 15:24
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface SchemeEquipmentRelaService extends IService<SchemeEquipmentRelaModel> {

    /**
     * 绑定设备操作
     *
     * @param schemeModel
     * @param schemeEquipmentRelaModels
     * @return
     */
    boolean bandEquipments(SchemeModel schemeModel, List<SchemeEquipmentRelaModel> schemeEquipmentRelaModels);

}
