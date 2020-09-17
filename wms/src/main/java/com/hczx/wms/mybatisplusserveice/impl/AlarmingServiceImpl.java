package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.AlarmingDao;
import com.hczx.wms.model.AlarmingModel;
import com.hczx.wms.mybatisplusserveice.AlarmingService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AlarmingService
 * @Description: 警情服务层实现
 * @Date: 2020/9/10 10:46
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AlarmingServiceImpl extends ServiceImpl<AlarmingDao, AlarmingModel> implements AlarmingService {
}
