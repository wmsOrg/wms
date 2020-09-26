package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.SysPermissionDao;
import com.hczx.wms.model.SysPermissionModel;
import com.hczx.wms.mybatisplusserveice.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SysPermissionServiceImpl
 * @Description: 权限服务实现
 * @Date: 2020/9/25 14:24
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermissionModel> implements SysPermissionService {
}
