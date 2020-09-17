package com.hczx.wms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Base64;

/**
 * @ClassName: ImageUtils
 * @Description:
 * @Date: 2020/9/12 11:01
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class ImageUtils {

    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * base64转图片，并存储到本地
     * @param base64 base64字符串
     *
     */
    public static void base64ToImage(String base64, String savePath) throws Exception {


        byte[] b = null;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            if (base64.indexOf("data:image/jpeg;base64,") != -1) {
                b = decoder.decode(base64.replaceAll("data:image/jpeg;base64,", ""));
            } else {
                if (base64.indexOf("data:image/png;base64,") != -1) {
                    b = decoder.decode(base64.replaceAll("data:image/png;base64,", ""));
                } else {
                    b = decoder.decode(base64.replaceAll("data:image/jpg;base64,", ""));
                }
            }
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("base64转换图片异常：",e);
            throw new Exception(e);
        }

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        file = new File(savePath);

        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(b);
        } catch (FileNotFoundException e) {
            logger.error("打开文件夹异常：",e);
            throw new Exception(e);
        } catch (IOException e) {
            logger.error("写文件异常：",e);
            throw new Exception(e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("缓冲区关闭异常：",e);
                    throw new Exception(e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    logger.error("文件输出流关闭异常：",e);
                    throw new Exception(e);
                }
            }
        }
        return;
    }

}
