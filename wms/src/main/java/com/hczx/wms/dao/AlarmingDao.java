package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.model.AlarmingModel;
import org.apache.ibatis.annotations.Mapper;

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
    Long countAlarmingInfo(AlarmingInfoEntity alarmingInfoEntity) throws Exception;

    /**
     * 根据条件查询警情列表
     *
     * @param page
     * @param size
     * @param alarmingInfoEntity
     * @return
     */
    List<AlarmingInfoEntity> listAlarmingInfo(Integer page, Integer size, AlarmingInfoEntity alarmingInfoEntity) throws Exception;
}
