package com.hczx.wms.entity.planentities;

/**
 * @ClassName: PlanQueryEntity
 * @Description:
 * @Date: 2020/9/15 16:46
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class PlanQueryEntity {

    private Integer page;

    private Integer size;

    private String id;

    private String planName;

    private String alarmingId;

    private String schemeId;

    private String useState;

    private String imageUrl;

    private String createTime;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getAlarmingId() {
        return alarmingId;
    }

    public void setAlarmingId(String alarmingId) {
        this.alarmingId = alarmingId;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }
}
