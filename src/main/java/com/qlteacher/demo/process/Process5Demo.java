package com.qlteacher.demo.process;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.lesson.UploadLessonStructureResourceDemo;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;
import com.qlteacher.demo.pojo.vo.lessoncase.UploadLessonItemVO;
import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * 第五步循环上传课例资源 需要解析保存下来的课例信息，上传所有环节节点下类型为data类型节点指定的资源
 * 这个例子中使用循环调用方式在每个节点下上传一个资源，资源选择优先级 视频 > pdf文档 > doc,docx文档 > zip,rar文件
 *
 * @author 江立国 2024/8/16 17:19
 */
public class Process5Demo {

    public static void main(String[] args) {
        File file = Constant.getFile(String.format("test-%s-%s-result.json", Constant.activityId, Constant.catalogId));
        List<UploadLessonItemVO> itemVOS = loadLessonStructures(file);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File video = new File(classLoader.getResource("file/upload-video.mp4").getFile());
        File pdf = new File(classLoader.getResource("file/upload-document.pdf").getFile());
        File document = new File(classLoader.getResource("file/upload-document.docx").getFile());
        File other = new File(classLoader.getResource("file/upload-file.zip").getFile());

        for (UploadLessonItemVO item : itemVOS) {
            if(item.getLimit().stream().anyMatch(limit -> limit.getFileFormat().contains("mp4"))){
                UploadLessonStructureResourceDemo.uploadLessonStructureResource(item.getLessonCaseId(), item.getLessonCaseStructureId(), video);
            }else if(item.getLimit().stream().anyMatch(limit -> limit.getFileFormat().contains("pdf"))){
                UploadLessonStructureResourceDemo.uploadLessonStructureResource(item.getLessonCaseId(), item.getLessonCaseStructureId(), pdf);
            }else if(item.getLimit().stream().anyMatch(limit -> limit.getFileFormat().contains("docx"))){
                UploadLessonStructureResourceDemo.uploadLessonStructureResource(item.getLessonCaseId(), item.getLessonCaseStructureId(), document);
            }else{
                UploadLessonStructureResourceDemo.uploadLessonStructureResource(item.getLessonCaseId(), item.getLessonCaseStructureId(), other);
            }
            System.out.println(String.format("环节[%s]的资源上传成功！", item.getName()));
        }

    }

    /**
     * 从指定的 课例数据文件中解析出所有需要上传资源的节点
     *
     * @param file
     * @return
     */
    @SneakyThrows
    public static List<UploadLessonItemVO> loadLessonStructures(File file) {
        FileInputStream in = new FileInputStream(file);
        byte[] buf = new byte[in.available()];
        in.read(buf);
        in.close();
        String lessonBody = new String(buf, "utf-8");
        UploadLessonCaseInfoVO infoVO = JSON.parseObject(lessonBody, UploadLessonCaseInfoVO.class);
        List<UploadLessonItemVO> list = new ArrayList();
        List<UploadLessonItemVO> children = infoVO.getStructures();
        List<UploadLessonItemVO> subchildren;
        do {
            subchildren = new ArrayList<>();
            for (UploadLessonItemVO child : children) {
                if("directory".equals(child.getType())){
                    subchildren.addAll(child.getChildren());
                    continue;
                }
                if("data".equals(child.getType())){
                    list.add(child);
                    continue;
                }
            }
            children = subchildren;
        } while (!ObjectUtils.isEmpty(children));
        return list;
    }
}
