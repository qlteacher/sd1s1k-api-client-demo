package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.utils.UpdateFileBySignature;
import com.qlteacher.demo.pojo.vo.SignatureVO;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/**
 * @author 江立国 2024/8/15 14:04
 */
public class UploadLessonCoverDemo {

    @SneakyThrows
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("file/big-cover.jpg");
        File imageFile = new File(resource.getFile());
        File file = Constant.getFile("test-update.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            if(uploadLessonCover(imageFile, infoVO.getId())){
                System.out.println("上传封面成功!");
            }
        }
    }

    public static Boolean uploadLessonCover(File file, String lessonId) {
        //申请上传许可
        SignatureVO signature = ApplyLessonCoverDemo.applyLessonCoverDemo(lessonId, file.getName());
        //上传文件
        Boolean update = UpdateFileBySignature.updateFileSignature(signature, file);
        if(update) {
            //如果文件上传成功，提交上传结果，这里需要把许可证里的key和domain一起提交
            return CommitLessonCoverDemo.commitLessonCoverDemo(lessonId, signature.getKey(), signature.getDomain());
        }
        return false;
    }
}
