package com.hczx.wms.entity.common;

/**
 * @ClassName: AuthenticationResponseEntity
 * @Description: 响应实体
 * @Date: 2020/8/31 11:31
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class WmsOperateResponseEntity {

    /**响应编码*/
    private String Code;

    /**响应标识*/
    private boolean Flag;

    /**响应信息*/
    private String Msg;

    /**响应数据*/
    private Object Data;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
