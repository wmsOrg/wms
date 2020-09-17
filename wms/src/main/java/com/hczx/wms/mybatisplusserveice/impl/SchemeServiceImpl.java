package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.EquipmentDao;
import com.hczx.wms.dao.SchemeDao;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.model.SchemeModel;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.mybatisplusserveice.EquipmentService;
import com.hczx.wms.mybatisplusserveice.SchemeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: SchemeServiceImpl
 * @Description: 方案服务层实现
 * @Date: 2020/9/9 10:54
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class SchemeServiceImpl extends ServiceImpl<SchemeDao, SchemeModel> implements SchemeService {

    private Logger logger = LoggerFactory.getLogger(SchemeServiceImpl.class);

    /**
     * 新增方案
     *
     * @param schemeIncreaseEntity
     * @return
     */
    @Transactional
    @Override
    public boolean saveScheme(SchemeIncreaseEntity schemeIncreaseEntity) {

        boolean flag = true;
        SchemeModel schemeModel = new SchemeModel();
        BeanUtils.copyProperties(schemeIncreaseEntity, schemeModel);
        if (schemeModel!= null && !StringUtils.isBlank(schemeModel.getSchemeName())){
            schemeModel.setId(UUID.randomUUID().toString());
            schemeModel.setCreateTime(new Date());
            schemeModel.setImageUrl("");

            //新增用户
            try{
                flag = schemeModel.insert();
            }catch (Exception e){
                logger.error("新增方案异常：",e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            return flag;
        }

        return false;
    }

}
