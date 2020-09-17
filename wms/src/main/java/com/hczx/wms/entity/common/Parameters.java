package com.hczx.wms.entity.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: Parameters
 * @Description: 配置参数
 * @Date: 2020/9/12 10:55
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Component
@ConfigurationProperties(prefix = "common.params")
public class Parameters {

    /**可视化界面截图保存地址*/
    private String visibleEditLayoutCaptureUrl;

    public String getVisibleEditLayoutCaptureUrl() {
        return visibleEditLayoutCaptureUrl;
    }

    public void setVisibleEditLayoutCaptureUrl(String visibleEditLayoutCaptureUrl) {
        this.visibleEditLayoutCaptureUrl = visibleEditLayoutCaptureUrl;
    }
}
