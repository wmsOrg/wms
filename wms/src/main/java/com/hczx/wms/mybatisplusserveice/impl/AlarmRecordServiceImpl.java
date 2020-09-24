package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.AlarmRecordDao;
import com.hczx.wms.dao.AlarmingDao;
import com.hczx.wms.model.AlarmRecordModel;
import com.hczx.wms.model.AlarmingModel;
import com.hczx.wms.mybatisplusserveice.AlarmRecordService;
import com.hczx.wms.mybatisplusserveice.AlarmingService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: AlarmRecordServiceImpl
 * @Description: 警情记录服务实现
 * @Date: 2020/9/23 17:28
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AlarmRecordServiceImpl extends ServiceImpl<AlarmRecordDao, AlarmRecordModel> implements AlarmRecordService {
}
