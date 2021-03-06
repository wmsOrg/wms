package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.AlarmRecordModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: AlarmRecordDao
 * @Description: 警情记录持久层
 * @Date: 2020/9/23 17:25
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface AlarmRecordDao extends BaseMapper<AlarmRecordModel> {

    /**
     *
     * 获取当前预案最新记录
     *
     * @param planId
     * @return
     */
    AlarmRecordModel queryCurrentlarmRecordByPlanId(@Param("planId") String planId);
}
