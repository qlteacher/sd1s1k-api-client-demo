package com.qlteacher.demo.process;

import com.qlteacher.demo.Constant;
import com.qlteacher.demo.lesson.SubmitLessonDemo;
import lombok.SneakyThrows;

import java.io.*;

/**
 * 第三步 提交课例数据
 * 数据是通过第一步获取模板后由第二步处理后生成可提交的数据
 *
 * @author 江立国 2024/8/16 14:16
 */
public class Process3Demo {

    @SneakyThrows
    public static void main(String[] args) {
        File inFile = Constant.getFile(String.format("test-%s-%s-submit.json", Constant.activityId, Constant.catalogId));
        if(inFile.exists()) {
            InputStream in = new FileInputStream(inFile);
            byte[] buf = new byte[in.available()];
            in.read(buf);
            in.close();
            String json = new String(buf, "utf-8");
            //提交添加课例数据，会获得返回一个完整的课例json数据，建议保存，便于以后上传课例资源的依据
            String result = SubmitLessonDemo.submitLessonDemo(json);
            File saveFile = Constant.getFile(String.format("test-%s-%s-result.json", Constant.activityId, Constant.catalogId));
            OutputStream out = new FileOutputStream(saveFile);
            out.write(result.getBytes("utf-8"));
            out.flush();
            out.close();
        }
    }
}
