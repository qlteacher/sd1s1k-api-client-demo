package com.qlteacher.demo.process;

import com.qlteacher.demo.Constant;
import com.qlteacher.demo.utils.TestMarkerTemplate;
import lombok.SneakyThrows;

import java.io.*;
import java.util.Map;

/**
 * 第二步通过SpringEL处理引擎将模板处理成可提交的JSON数据
 *
 * @author 江立国 2024/8/16 11:09
 */
public class Process2Demo {

    private static final String TITLE = "#课例标题#";

    @SneakyThrows
    public static void main(String[] args) {
        //加载添加课例数据模板
        File file = Constant.getFile(String.format("test-%s-%s.template", Constant.activityId, Constant.catalogId));
        //判断文件是否存在
        if(file.exists()){
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            String template = new String(bytes, "utf-8");
            //取出模板中所有需要填写的属性
            String[] properties = TestMarkerTemplate.loadProperties(template);
            //赋值人员和学校信息 这些信息可以通过基础信息查询
            TestMarkerTemplate.schoolId = Constant.schoolId;
            TestMarkerTemplate.masterId = Constant.userId;
            TestMarkerTemplate.masterName = Constant.userName;
            TestMarkerTemplate.lectureId = "#核心成员编号#";
            TestMarkerTemplate.lectureName = "#核心成员姓名#";
            //需要给生成映射表方法提供一个课例的标题 //可以重写这段生成MAP的代码生成你需要的MAP
            Map<String, String> content = TestMarkerTemplate.assemblePropertiesToMap(TITLE, properties);
            //通过模板和数据映射生成提交用Json数据
            String submitJson = TestMarkerTemplate.constructTemplate(template, content);
            //指定生成提交用json数据的保存位置
            File outFile = Constant.getFile(String.format("test-%s-%s-submit.json", Constant.activityId, Constant.catalogId));
            OutputStream out = new FileOutputStream(outFile);
            out.write(submitJson.getBytes("utf-8"));
            out.flush();
            out.close();
        }
    }

}
