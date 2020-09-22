package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.enquipmententities.EquipmentLv1inPlanEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.model.SchemeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: SchemeDao
 * @Description: 方案持久层接口
 * @Date: 2020/9/9 10:52
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SchemeDao extends BaseMapper<SchemeModel> {

    /**
     * 查询方案列表
     *
     * @param schemeIncreaseEntity
     * @return
     */
    List<SchemeIncreaseEntity> listScheme(SchemeIncreaseEntity schemeIncreaseEntity) throws Exception;

    /**
     * 绑定设备后更新方案
     *
     * @param schemeModel
     * @return
     * @throws Exception
     */
    boolean updateBindedScheme(SchemeModel schemeModel) throws Exception;

    /**
     * 查询预案中一级设备
     *
     * @param planQueryEntity
     * @return
     * @throws Exception
     */
    List<EquipmentLv1inPlanEntity> queryEquipmentLv1InPlan(PlanContentQueryEntity planQueryEntity) throws Exception;

    /**
     * 查询方案中一级设备
     *
     * @param planQueryEntity
     * @return
     * @throws Exception
     */
    List<EquipmentLv1inPlanEntity> queryEquipmentLv1InScheme(@Param("schemeId") String planQueryEntity) throws Exception;

    /**
     * 查询方案中一级设备内部设备
     *
     * @param planQueryEntity
     * @return
     * @throws Exception
     */
    List<EquipmentLv1inPlanEntity> queryEquipmentLv1ContentsInScheme(@Param("schemeId") String planQueryEntity) throws Exception;

    /**
     * 查询预案中一级设备详情
     *
     * @param planQueryEntity
     * @return
     * @throws Exception
     */
    List<EquipmentLv1inPlanEntity> queryEquipmentLv1ContentsInPlan(PlanContentQueryEntity planQueryEntity) throws Exception;

    /**
     * 作废方案
     *
     * @param schemeIds
     */
    boolean removeSchemeByIds(@Param("list") List<String> schemeIds) throws Exception;
}
