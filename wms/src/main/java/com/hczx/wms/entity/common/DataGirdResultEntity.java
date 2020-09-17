package com.hczx.wms.entity.common;

/**
 * @ClassName: EquipmentListResultEntity
 * @Description: 数据表格清单实体
 * @Date: 2020/9/1 17:10
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class DataGirdResultEntity {

    private static final String SUCCESS = "成功";
    private static final String ERROR = "失败";

    /**响应状态code，因为前台layui默认0为响应成功，所以此处默认为0*/
    private Integer code = 0;

    /**数据总条数*/
    private Long count = 0L;

    /**返回是否成功*/
    private Boolean result = false;

    /**返回提示信息*/
    private String msg = "";

    /**返回数据信息*/
    private Object data;

    private DataGirdResultEntity() {
    }

    /**
     * 成功的响应
     *
     * @return
     */
    public static DataGirdResultEntity success() {
        return result(true, SUCCESS, null,null);
    }

    public static DataGirdResultEntity success(String msg) {
        return result(true, msg, null,null);
    }

    public static DataGirdResultEntity success(Object data) {
        return result(true, SUCCESS, data,null);
    }
    public static DataGirdResultEntity success(Object data,Long count) {
        return result(true, SUCCESS, data,count);
    }


    public static DataGirdResultEntity success(String msg, Object data) {
        return result(true, msg, data,null);
    }

    public static DataGirdResultEntity success(String msg, Object data,Long count) {
        return result(true, msg, data,count);
    }

    /**
     * 失败的响应
     *
     * @return
     */
    public static DataGirdResultEntity error() {
        return result(false, ERROR, null,null);
    }

    public static DataGirdResultEntity error(String msg) {
        return result(false, msg, null,null);
    }

    public static DataGirdResultEntity error(Object data) {
        return result(false, ERROR, data,null);
    }

    public static DataGirdResultEntity error(Object data,Long count) {
        return result(false, ERROR, data,count);
    }

    public static DataGirdResultEntity error(String msg, Object data) {
        return result(false, msg, data,null);
    }

    public static DataGirdResultEntity error(String msg, Object data,Long count) {
        return result(false, msg, data,count);
    }

    public static DataGirdResultEntity result(Boolean result, String msg, Object data,Long count) {
        DataGirdResultEntity jsonResult = new DataGirdResultEntity();
        jsonResult.setResult(result);
        jsonResult.setMsg(msg);
        jsonResult.setData(data);
        jsonResult.setCount(count);
        return jsonResult;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static String getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
