package com.qlteacher.demo.lesson;

import com.qlteacher.demo.utils.UpdateFileBySignature;
import com.qlteacher.demo.pojo.vo.StructureSignatureVO;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * @author 江立国 2024/8/16 15:45
 */
public class UploadLessonStructureResourceDemo {

    private static final String STRUCTURE_ID_1 = "#structureId_1#"; //课例结构项目1 文档类
    private static final String STRUCTURE_ID_2 = "#structureId_2#"; //课例结构项目1 视频类

    private static final String LESSON_ID = "#lessonId#";//课程编号


    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("file/upload-document.docx");
        Boolean result = uploadLessonStructureResource(LESSON_ID, STRUCTURE_ID_1, new File(resource.getFile()));
        System.out.println(result?"上传文档成功":"上传文档失败");
        resource = classLoader.getResource("file/upload-video.mp4");
        result = uploadLessonStructureResource(LESSON_ID, STRUCTURE_ID_2, new File(resource.getFile()));
        System.out.println(result?"上传视频成功":"上传视频失败");
    }

    public static Boolean uploadLessonStructureResource(String lessonId, String structureId, File file) {
        List<StructureSignatureVO> signatureVOS = ApplyLessonStructureDemo.applyLessonStructure(lessonId, structureId, file.getName(), 0);
        Boolean result = UpdateFileBySignature.updateFileSignature(signatureVOS.get(0).getSignature(), file);
        if(result) {
            signatureVOS.get(0).setFile(file);
            return CommitLessonStructureDemo.commitLessonStructureDemo(lessonId, signatureVOS.get(0));
        }
        return false;
    }
}
