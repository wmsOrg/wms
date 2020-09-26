package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.SysUseRoleModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: SysUseRoleDao
 * @Description: 用户角色持久层
 * @Date: 2020/9/25 14:29
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SysUseRoleDao extends BaseMapper<SysUseRoleModel> {
}
