package com.hczx.wms.entity.cacheentities;

/**
 * @ClassName: EquipmentCacheEntity
 * @Description:  出警设备缓存
 * @Date: 2020/9/23 10:07
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentCacheEntity {

    private String key;//缓存ID

    private Object value;//缓存数据

    private long timeOut;//更新时间

    private boolean expired; //是否终止

    public EquipmentCacheEntity() {

        super();

    }



    public EquipmentCacheEntity(String key, Object value, long timeOut, boolean expired) {

        this.key = key;

        this.value = value;

        this.timeOut = timeOut;

        this.expired = expired;

    }



    public String getKey() {

        return key;

    }



    public long getTimeOut() {

        return timeOut;

    }



    public Object getValue() {

        return value;

    }



    public void setKey(String string) {

        key = string;

    }



    public void setTimeOut(long l) {

        timeOut = l;

    }



    public void setValue(Object object) {

        value = object;

    }



    public boolean isExpired() {

        return expired;

    }



    public void setExpired(boolean b) {

        expired = b;

    }

}
