package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.model.PlanModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName: PlanDao
 * @Description: 预案持久层
 * @Date: 2020/9/15 15:18
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface PlanDao extends BaseMapper<PlanModel> {

    /**
     * 根据警情唯一标识查询预案
     *
     * @param planQueryEntity
     * @return
     */
    List<PlanQueryEntity> listPlan(PlanQueryEntity planQueryEntity);
}
