package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeNode;
import com.hczx.wms.model.CompanyModel;
import com.hczx.wms.model.EquipmentModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: CompanyDao
 * @Description: 公司持久层接口
 * @Date: 2020/9/7 17:12
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface CompanyDao extends BaseMapper<CompanyModel> {

    /**
     * 查找公司树形信息
     *
     * @return
     */
    List<EquipmentZtreeNode> listComZtreeInfo();

}
