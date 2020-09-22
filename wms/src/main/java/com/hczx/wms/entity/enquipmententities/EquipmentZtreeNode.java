package com.hczx.wms.entity.enquipmententities;

/**
 * @ClassName: EquipmentZtreeNode
 * @Description:
 * @Date: 2020/9/7 15:28
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentZtreeNode {

    private String id;

    private String name;

    private String pId;

    private String linkingNo;

    private boolean open;

    private boolean nocheck;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isNocheck() {
        return nocheck;
    }

    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getLinkingNo() {
        return linkingNo;
    }

    public void setLinkingNo(String linkingNo) {
        this.linkingNo = linkingNo;
    }
}
