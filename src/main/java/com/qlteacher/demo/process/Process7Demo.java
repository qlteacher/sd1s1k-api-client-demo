package com.qlteacher.demo.process;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.lesson.ConfirmLessonDemo;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;

/**
 * 第七步 请在预览课例后，确认课例可以正常显示，所有文件和信息没有缺失的情况下，进行此确认步骤。
 * 确认后，课例将正式入库并发布成为市级优课
 *
 * @author 江立国 2024/8/16 18:36
 */
public class Process7Demo {
    @SneakyThrows
    public static void main(String[] args) {
        File file = Constant.getFile(String.format("test-%s-%s-result.json", Constant.activityId, Constant.catalogId));
        FileInputStream in = new FileInputStream(file);
        byte[] buf = new byte[in.available()];
        in.read(buf);
        in.close();
        String lessonBody = new String(buf, "utf-8");
        UploadLessonCaseInfoVO infoVO = JSON.parseObject(lessonBody, UploadLessonCaseInfoVO.class);
        Boolean confirmLesson = ConfirmLessonDemo.confirmLesson(infoVO.getId(), "我确认课例信息准确无误，可以参与省级优课评选");
        if(confirmLesson) {
            System.out.println("课例确认完成");
        }else{
            System.out.println("课例确认失败");
        }
    }
}
