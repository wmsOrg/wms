package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.model.SchemeModel;

/**
 * @ClassName: SchemeService
 * @Description: 方案服务层接口
 * @Date: 2020/9/9 10:53
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface SchemeService extends IService<SchemeModel> {

    /**
     * 新增方案
     *
     * @param schemeIncreaseEntity
     * @return
     */
    boolean saveScheme(SchemeIncreaseEntity schemeIncreaseEntity);

}
