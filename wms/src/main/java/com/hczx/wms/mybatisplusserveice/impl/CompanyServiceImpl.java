package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.CompanyDao;
import com.hczx.wms.dao.EquipmentDao;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.model.CompanyModel;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.mybatisplusserveice.CompanyService;
import com.hczx.wms.mybatisplusserveice.EquipmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: CompanyServiceImpl
 * @Description: 公司服务层实现
 * @Date: 2020/9/7 17:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyDao, CompanyModel> implements CompanyService {


}
