package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.AlarmRecordDao;
import com.hczx.wms.dao.EquipmentInOutRecordDao;
import com.hczx.wms.model.AlarmRecordModel;
import com.hczx.wms.model.EquipmentInOutRecordModel;
import com.hczx.wms.mybatisplusserveice.AlarmRecordService;
import com.hczx.wms.mybatisplusserveice.EquipmentInOutRecordService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: EquipmentInOutRecordServiceImpl
 * @Description: 设备出入库记录服务实现
 * @Date: 2020/9/23 17:29
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class EquipmentInOutRecordServiceImpl extends ServiceImpl<EquipmentInOutRecordDao, EquipmentInOutRecordModel> implements EquipmentInOutRecordService {
}
