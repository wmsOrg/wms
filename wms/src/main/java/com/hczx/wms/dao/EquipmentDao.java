package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeNode;
import com.hczx.wms.model.EquipmentModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: EquipmentDao
 * @Description: 设备持久层接口
 * @Date: 2020/9/1 10:12
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface EquipmentDao extends BaseMapper<EquipmentModel> {

    /**
     * 分页查询设备信息
     *
     * @param page
     * @param size
     * @param equipmentInfoEntity
     * @return
     */
    List<EquipmentInfoEntity> listEquipmentInfo(@Param("page") Integer page, @Param("size") Integer size, @Param("equipmentInfoEntity") EquipmentInfoEntity equipmentInfoEntity) throws Exception;

    /**
     * 查询设备信息数量
     *
     * @param equipmentInfoEntity
     * @return
     */
    Long countEquipmentInfo(@Param("equipmentInfoEntity") EquipmentInfoEntity equipmentInfoEntity) throws Exception;

    /**
     * 作废设备
     *
     * @param ids
     * @return
     */
    boolean nullifyByIds(@Param("list") List<String> ids);

    /**
     * 查找树形设备信息
     *
     * @return
     */
    List<EquipmentZtreeNode> zTreeEquipmentInfo();

    /**
     * 根据RFID批量更新设备入库状态
     *
     * @param inboundState
     * @param equipmenmtRfids
     * @return
     */
    boolean updateInboundStateBatchByRfids(@Param("inboundState") String inboundState, @Param("list") List<String> equipmenmtRfids);

    /**
     * 更新设备关联号
     * @param ids
     * @return
     */
    boolean linkEquipmentByIds(@Param("list")List<String> ids, @Param("linkNo") String linkNo);
}
