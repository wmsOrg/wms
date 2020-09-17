package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.AlarmSchemeEquipmentRelaModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: AlarmSchemeEquipmentRelaDao
 * @Description: 警情方案设备关联表持久层
 * @Date: 2020/9/11 15:57
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface AlarmSchemeEquipmentRelaDao extends BaseMapper<AlarmSchemeEquipmentRelaModel> {
}
