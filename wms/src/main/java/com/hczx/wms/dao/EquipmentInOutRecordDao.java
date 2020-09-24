package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.EquipmentInOutRecordModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: EquipmentInOutRecordDao
 * @Description: 设备出入库记录持久层
 * @Date: 2020/9/23 17:26
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface EquipmentInOutRecordDao extends BaseMapper<EquipmentInOutRecordModel> {
}
