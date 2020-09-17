package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.SchemeDao;
import com.hczx.wms.dao.SchemeEquipmentRelaDao;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.SchemeEquipmentRelaModel;
import com.hczx.wms.model.SchemeModel;
import com.hczx.wms.mybatisplusserveice.SchemeEquipmentRelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * @ClassName: SchemeEquipmentRelaServiceImpl
 * @Description: 方案设备绑定实体映射接口实现层
 * @Date: 2020/9/9 15:25
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class SchemeEquipmentRelaServiceImpl extends ServiceImpl<SchemeEquipmentRelaDao, SchemeEquipmentRelaModel> implements SchemeEquipmentRelaService {

    private Logger logger = LoggerFactory.getLogger(SchemeEquipmentRelaServiceImpl.class);

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private SchemeEquipmentRelaDao schemeEquipmentRelaDao;

    @Autowired
    private SchemeEquipmentRelaService schemeEquipmentRelaService;

    /**
     * 绑定设备操作
     *
     * @param schemeModel
     * @param schemeEquipmentRelaModels
     * @return
     */
    @Transactional
    @Override
    public boolean bandEquipments(SchemeModel schemeModel, List<SchemeEquipmentRelaModel> schemeEquipmentRelaModels) {

        boolean flag = true;

        try {

            schemeDao.updateBindedScheme(schemeModel);
            schemeEquipmentRelaDao.removeBySchemeId(schemeModel.getId());
            schemeEquipmentRelaService.saveBatch(schemeEquipmentRelaModels);

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("绑定设备操作异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flag = false;
        }

        return flag;
    }
}
