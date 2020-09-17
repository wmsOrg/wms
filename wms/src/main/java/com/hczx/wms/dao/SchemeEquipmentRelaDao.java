package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.SchemeEquipmentRelaModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: SchemeEquipmentRelaDao
 * @Description: 方案设备绑定实体映射持久层
 * @Date: 2020/9/9 15:23
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SchemeEquipmentRelaDao extends BaseMapper<SchemeEquipmentRelaModel> {

    /**
     * 根据方案唯一标识解绑设备
     *
     * @param schemeId
     * @return
     */
    boolean removeBySchemeId(@Param("schemeId") String schemeId) throws Exception;

    /**
     * 查询具体方案所绑定的设备
     *
     * @param schemeId
     * @throws Exception
     */
    List<EquipmentsInSchemeEntity> listEquipmentsBySchemeId(@Param("schemeId") String schemeId) throws Exception;
}
