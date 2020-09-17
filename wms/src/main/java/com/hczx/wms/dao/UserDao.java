package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: EquipmentDao
 * @Description: 用户持久层接口
 * @Date: 2020/9/1 10:12
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface UserDao extends BaseMapper<UserModel> {
}
