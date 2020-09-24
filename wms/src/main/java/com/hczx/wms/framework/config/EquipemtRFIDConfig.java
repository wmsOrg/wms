package com.hczx.wms.framework.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gg.reader.api.dal.*;
import com.gg.reader.api.protocol.gx.*;
import com.hczx.wms.dao.EquipmentDao;
import com.hczx.wms.dao.EquipmentInOutRecordDao;
import com.hczx.wms.entity.cacheentities.EquipmentCacheEntity;
import com.hczx.wms.entity.common.ConnectedConstant;
import com.hczx.wms.entity.planentities.PlanEquipmentsEntity;
import com.hczx.wms.framework.cache.EquipmentCache;
import com.hczx.wms.model.EquipmentInOutRecordModel;
import com.hczx.wms.model.EquipmentModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.gg.reader.api.MainClass.subscribeHandler;

/**
 * @ClassName: EquipemtRFIDConfig
 * @Description:
 * @Date: 2020/9/17 17:48
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Configuration
@ComponentScan({"com.hczx.wms.dao"})
public class EquipemtRFIDConfig {

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private EquipmentInOutRecordDao equipmentInOutRecordDao;

    static GClient client = new GClient();

    static List<LogBaseEpcInfo> logBaseEpcInfos = new ArrayList<>();

    @Bean
    public void read() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        threadPool.execute(new Runnable(){
            @Override
            public void run() {
                GServer server = new GServer();

                if (server.open(8160)) {
                    subscribeServerHandler(server);
                    System.out.println("开始监听");
                } else {
                    System.out.println("监听失败");
                }
//                for(int j=1;j<=4;j++){
//                    try {
//                        Thread.sleep(20);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for  task of " + task);
//                }
            }
        });


    }

    //订阅监听上报
    private void subscribeServerHandler(GServer server) {
        server.onGClientConnected = new HandlerGClientConnected() {
            @Override
            public void log(GClient gClient) {
                System.out.println(gClient.getName() + "---监听成功");
                client = gClient;//切换连接对象
                client.setSendHeartBeat(true);//开启心跳检测Tcp连接状态
                subscribeTcpHandler();//订阅Tcp断连上报

                subscribeHandler(client);
                MsgBaseInventoryEpc msgBaseInventoryEpc = new MsgBaseInventoryEpc();
                msgBaseInventoryEpc.setAntennaEnable(EnumG.AntennaNo_1 | EnumG.AntennaNo_2);
                msgBaseInventoryEpc.setInventoryMode(EnumG.InventoryMode_Inventory);
                client.sendSynMsg(msgBaseInventoryEpc);
                if (0x00 == msgBaseInventoryEpc.getRtCode()) {
                    System.out.println("MsgBaseInventoryEpc[OK].");
                } else {
                    System.out.println(msgBaseInventoryEpc.getRtMsg());
                }

//                testStop();//测试监听成功的连接是否通信正常
            }
        };
    }

    //订阅TCP断开连接上报
    private void subscribeTcpHandler() {
        client.onDisconnected = new HandlerTcpDisconnected() {
            @Override
            public void log(String s) {
                System.out.println("连接" + s + "已断开");
                client.close();//释放当前连接资源
            }
        };
    }

    private void testStop() {
        MsgBaseStop msg = new MsgBaseStop();
        client.sendSynMsg(msg);
        if (0x00 == msg.getRtCode()) {
            System.out.println("Stop success");
        } else {
            System.out.println(msg.getRtMsg());
        }
    }

    //订阅6c标签信息上报
    private void subscribeHandler(GClient client) {
        client.onTagEpcLog = new HandlerTagEpcLog() {
            @Override
            public void log(String s, LogBaseEpcInfo logBaseEpcInfo) {
                if (null != logBaseEpcInfo && logBaseEpcInfo.getResult() == 0) {
                    EquipmentInOutRecordModel equipmentInOutRecordModel = new EquipmentInOutRecordModel();
                    System.out.println(logBaseEpcInfo);
                    String epc = logBaseEpcInfo.getEpc();
                    if (EquipmentCache.getCacheSize()>0){
                        List<String> keys = EquipmentCache.getCacheAllkey();
                        int size = keys.size();
                        for (int i = 0; i < size; i++) {
                            EquipmentCacheEntity equipmentCacheEntity = EquipmentCache.getCacheInfo(keys.get(i));
                            if (equipmentCacheEntity.getKey().equals("TreatTheCases")) {
                                List<PlanEquipmentsEntity> planEquipmentsEntities = (List<PlanEquipmentsEntity>) equipmentCacheEntity.getValue();
                                String planId = planEquipmentsEntities.get(0).getPlanId();
                                String alarmRecordId = planEquipmentsEntities.get(0).getAlarmRecordId();
                                List<PlanEquipmentsEntity> tempPlanEquipmentsEntities = planEquipmentsEntities.stream().filter(o -> o.getEquipmentRfid().equals(epc)).collect(Collectors.toList());
                                if (tempPlanEquipmentsEntities == null || tempPlanEquipmentsEntities.isEmpty()) {

                                    System.out.println("非法出库，报警");

//                                    equipmentInOutRecordModel = packageEquipmentInOutRecordModel(tempPlanEquipmentsEntities.get(0).getEquipmentId(),tempPlanEquipmentsEntities.get(0).getEquipmentRfid(),alarmRecordId,"10",planId);
//                                    equipmentInOutRecordDao.insert(equipmentInOutRecordModel);
                                    equipmentInOutRecordModel.setId(UUID.randomUUID().toString());
                                    equipmentInOutRecordModel.setEquipmentId(tempPlanEquipmentsEntities.get(0).getEquipmentId());
                                    equipmentInOutRecordModel.setEquipmentRfid(tempPlanEquipmentsEntities.get(0).getEquipmentRfid());
                                    equipmentInOutRecordModel.setAlarmRecordId(alarmRecordId);
                                    equipmentInOutRecordModel.setDirection("10");
                                    equipmentInOutRecordModel.setSchemeId(planId);
                                    equipmentInOutRecordModel.setCreatedDate(new Date());
                                    equipmentInOutRecordDao.insert(equipmentInOutRecordModel);

                                } else {
                                    if (tempPlanEquipmentsEntities.get(0).getWaitOperatingFlag()) {
//                                        equipmentInOutRecordModel = packageEquipmentInOutRecordModel(tempPlanEquipmentsEntities.get(0).getEquipmentId(),tempPlanEquipmentsEntities.get(0).getEquipmentRfid(),alarmRecordId,"10",planId);
//                                        equipmentInOutRecordDao.insert(equipmentInOutRecordModel);

                                        equipmentInOutRecordModel.setId(UUID.randomUUID().toString());
                                        equipmentInOutRecordModel.setEquipmentId(tempPlanEquipmentsEntities.get(0).getEquipmentId());
                                        equipmentInOutRecordModel.setEquipmentRfid(tempPlanEquipmentsEntities.get(0).getEquipmentRfid());
                                        equipmentInOutRecordModel.setAlarmRecordId(alarmRecordId);
                                        equipmentInOutRecordModel.setDirection("10");
                                        equipmentInOutRecordModel.setSchemeId(planId);
                                        equipmentInOutRecordModel.setCreatedDate(new Date());
                                        equipmentInOutRecordDao.insert(equipmentInOutRecordModel);

                                        tempPlanEquipmentsEntities.get(0).setWaitOperatingFlag(false);
                                    }

                                }

                                System.out.println("合法出库");

                                List<String> rfids = new ArrayList<>();
                                rfids.add(epc);
                                equipmentDao.updateInboundStateBatchByRfids("0",rfids);


                            }else{
                                List<PlanEquipmentsEntity> planEquipmentsEntities = (List<PlanEquipmentsEntity>) equipmentCacheEntity.getValue();
                                String planId = planEquipmentsEntities.get(0).getPlanId();

                                String alarmRecordId = planEquipmentsEntities.get(0).getAlarmRecordId();
                                List<PlanEquipmentsEntity> tempPlanEquipmentsEntities = planEquipmentsEntities.stream().filter(o -> o.getEquipmentRfid().equals(epc)).collect(Collectors.toList());
                                if (tempPlanEquipmentsEntities == null || tempPlanEquipmentsEntities.isEmpty()) {

                                    System.out.println("非法入库，报警");

//                                    equipmentInOutRecordModel = packageEquipmentInOutRecordModel(tempPlanEquipmentsEntities.get(0).getEquipmentId(),tempPlanEquipmentsEntities.get(0).getEquipmentRfid(),alarmRecordId,"20",planId);
//                                    equipmentInOutRecordDao.insert(equipmentInOutRecordModel);
                                    equipmentInOutRecordModel.setId(UUID.randomUUID().toString());
                                    equipmentInOutRecordModel.setEquipmentId(tempPlanEquipmentsEntities.get(0).getEquipmentId());
                                    equipmentInOutRecordModel.setEquipmentRfid(tempPlanEquipmentsEntities.get(0).getEquipmentRfid());
                                    equipmentInOutRecordModel.setAlarmRecordId(alarmRecordId);
                                    equipmentInOutRecordModel.setDirection("20");
                                    equipmentInOutRecordModel.setSchemeId(planId);
                                    equipmentInOutRecordModel.setCreatedDate(new Date());
                                    equipmentInOutRecordDao.insert(equipmentInOutRecordModel);
                                } else {


                                    if (tempPlanEquipmentsEntities.get(0).getWaitOperatingFlag()) {
//                                        equipmentInOutRecordModel = packageEquipmentInOutRecordModel(tempPlanEquipmentsEntities.get(0).getEquipmentId(),tempPlanEquipmentsEntities.get(0).getEquipmentRfid(),alarmRecordId,"20",planId);
//                                        equipmentInOutRecordDao.insert(equipmentInOutRecordModel);
                                        equipmentInOutRecordModel.setId(UUID.randomUUID().toString());
                                        equipmentInOutRecordModel.setEquipmentId(tempPlanEquipmentsEntities.get(0).getEquipmentId());
                                        equipmentInOutRecordModel.setEquipmentRfid(tempPlanEquipmentsEntities.get(0).getEquipmentRfid());
                                        equipmentInOutRecordModel.setAlarmRecordId(alarmRecordId);
                                        equipmentInOutRecordModel.setDirection("20");
                                        equipmentInOutRecordModel.setSchemeId(planId);
                                        equipmentInOutRecordModel.setCreatedDate(new Date());
                                        equipmentInOutRecordDao.insert(equipmentInOutRecordModel);

                                        System.out.println("合法入库");

                                        tempPlanEquipmentsEntities.get(0).setWaitOperatingFlag(false);
                                    }
                                }
                                List<String> rfids = new ArrayList<>();
                                rfids.add(epc);
                                equipmentDao.updateInboundStateBatchByRfids("1",rfids);


                            }
                        }
                    }else{
                        System.out.println("非法出入库，报警");
                        QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("equipmentRfid", epc);
                        String flag = "";
                        EquipmentModel equipmentModel = new EquipmentModel().selectOne(queryWrapper);
                        if (equipmentModel != null && !StringUtils.isBlank(equipmentModel.getId())){
                            equipmentModel.setInboundState(StringUtils.isBlank(equipmentModel.getInboundState())||equipmentModel.getInboundState().equals("0")?"1":"0");
                            flag = StringUtils.isBlank(equipmentModel.getInboundState())||equipmentModel.getInboundState().equals("0")?"10":"20";
                            equipmentModel.updateById();
                        }

                        equipmentInOutRecordModel = packageEquipmentInOutRecordModel(equipmentModel.getId(),equipmentModel.getEquipmentRfid(),null,flag,null);
                        equipmentInOutRecordModel.insert();
                    }
                }
            }
        };

        client.onTagEpcOver = new HandlerTagEpcOver() {
            @Override
            public void log(String s, LogBaseEpcOver logBaseEpcOver) {
                System.out.println("HandlerTagEpcOver");
            }
        };
    }

    /**
     * 封装设备出入库记录
     * @param equipmentId
     * @param equipmentRfid
     * @param alarmRecordId
     * @param direction
     * @param schemeId
     * @return
     */
    private EquipmentInOutRecordModel packageEquipmentInOutRecordModel(String equipmentId,String equipmentRfid,String alarmRecordId,String direction,String schemeId){
        EquipmentInOutRecordModel equipmentInOutRecordModel = new EquipmentInOutRecordModel();
        equipmentInOutRecordModel.setId(UUID.randomUUID().toString());
        equipmentInOutRecordModel.setEquipmentId(equipmentId);
        equipmentInOutRecordModel.setEquipmentRfid(equipmentRfid);
        equipmentInOutRecordModel.setAlarmRecordId(alarmRecordId);
        equipmentInOutRecordModel.setDirection(direction);
        equipmentInOutRecordModel.setSchemeId(schemeId);
        equipmentInOutRecordModel.setCreatedDate(new Date());

        return equipmentInOutRecordModel;
    }
}
