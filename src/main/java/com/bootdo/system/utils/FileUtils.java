package com.bootdo.system.utils;

import com.bootdo.common.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by ziteng016 on 2019/1/14.
 */
@Component
public class FileUtils extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public String saveImg(String base64){
        String fileName = null;
        try {
            fileName = convertBase64DataToImage(base64, bootdoConfig.getUploadPath(), "jpg");
        } catch (Exception e) {
            logger.error("保存照片报错了", e);
            return null;
        }
        return filePrefix + fileName;
    }


    private  String convertBase64DataToImage(String base64ImgData, String filePath, String suffix){

        File logosavedir = new File(filePath);// path1为存放的路径
        if (!logosavedir.exists()) {// 如果不存在文件夹，则自动生成
            logosavedir.mkdirs();
        }
        String fileOldName = null;
        try {
            fileOldName = RandomUtils.generateString(10) + DateUtils.getDateStr(new Date()) + "." + suffix;
            String fileName = filePath + fileOldName;
            BASE64Decoder d = new BASE64Decoder();
            byte[] bs = d.decodeBuffer(base64ImgData);
            FileOutputStream os = new FileOutputStream(fileName);
            os.write(bs);
            os.close();
        } catch (IOException e) {
            System.out.println("base64保存图片报错！");
        }
        return fileOldName;
    }


    public  boolean deleteFile(String url) {
        String fileName =bootdoConfig.getUploadPath() + url.substring(filePrefix.length());
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
