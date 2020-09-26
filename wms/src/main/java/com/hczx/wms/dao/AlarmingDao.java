package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.planentities.PlanEquipmentsEntity;
import com.hczx.wms.model.AlarmingModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: AlarmingDao
 * @Description: 警情持久层
 * @Date: 2020/9/10 10:41
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface AlarmingDao extends BaseMapper<AlarmingModel> {

    /**
     * 根据条件查询警情列表数量
     *
     * @param alarmingInfoEntity
     * @return
     */
    Long countAlarmingInfo(@Param("alarmingInfoEntity") AlarmingInfoEntity alarmingInfoEntity) throws Exception;

    /**
     * 根据条件查询警情列表
     *
     * @param page
     * @param size
     * @param alarmingInfoEntity
     * @return
     */
    List<AlarmingInfoEntity> listAlarmingInfo(@Param("page") Integer page, @Param("size") Integer size, @Param("alarmingInfoEntity") AlarmingInfoEntity alarmingInfoEntity) throws Exception;

    /**
     * 作废警情
     *
     * @param ids
     * @return
     */
    boolean updateValidStateByIds(List<String> ids) throws Exception;

    /**
     * 查询某个方案下一级设备出入库情况
     *
     * @param planId
     * @param inoutState
     */
    List<PlanEquipmentsEntity> queryInOutEquipmentLv1(@Param("planId") String planId, @Param("inoutState") String inoutState) throws Exception;
}
