package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.PlanDao;
import com.hczx.wms.model.PlanModel;
import com.hczx.wms.mybatisplusserveice.PlanService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: PlanServiceImpl
 * @Description: 预案服务实现
 * @Date: 2020/9/15 15:20
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanDao, PlanModel> implements PlanService {
}
