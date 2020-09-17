package com.hczx.wms.service;

import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.entity.schemeentities.SchemeEditEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SchemeService
 * @Description: 方案编辑接口
 * @Date: 2020/9/9 10:43
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface SchemeOperateService {
    /**
     * 新增方案
     * @param schemeIncreaseEntity
     * @return
     */
    WmsOperateResponseEntity increaseScheme(SchemeIncreaseEntity schemeIncreaseEntity);

    /**
     * 查询方案
     *
     * @param schemeIncreaseEntity
     * @return
     */
    WmsOperateResponseEntity listScheme(SchemeIncreaseEntity schemeIncreaseEntity);

    /**
     * 方案绑定设备
     *
     * @param schemeEditEntity
     * @return
     */
    WmsOperateResponseEntity schemeBindEquipments(SchemeEditEntity schemeEditEntity);

    /**
     * 查询具体方案所绑定的设备
     *
     * @param schemeId
     * @return
     */
    WmsOperateResponseEntity listEquipments(String schemeId);

    /**
     * 展示图片
     *
     * @param fileName
     * @param request
     * @param response
     * @throws IOException
     */
    void getImage(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 预案查询
     *
     * @param planQueryEntity
     * @return
     */
    WmsOperateResponseEntity planList(PlanQueryEntity planQueryEntity);
}
