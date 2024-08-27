package com.qlteacher.demo.process;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.lesson.UploadLessonCoverDemo;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * 第四步上传课例封面 如果没有封面可以跳过
 *
 * @author 江立国 2024/8/16 14:42
 */
public class Process4Demo {

    public static void main(String[] args) {
        //加载课例信息 通过第四步获取到的课例信息进行保存后的文件
        try {
            File infoFile = Constant.getFile(String.format("test-%s-%s-result.json", Constant.activityId, Constant.catalogId));
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            URL resource = classLoader.getResource("file/big-cover.jpg");
            //加载要上传的封面文件
            File imageFile = new File(resource.getFile());
            if (infoFile.exists() && imageFile.exists()) {
                FileInputStream in = new FileInputStream(infoFile);
                byte[] bytes = new byte[in.available()];
                in.read(bytes);
                in.close();
                String body = new String(bytes, "utf-8");
                UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
                UploadLessonCoverDemo.uploadLessonCover(imageFile, infoVO.getId());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

}
