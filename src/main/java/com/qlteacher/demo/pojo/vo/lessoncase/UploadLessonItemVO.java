package com.qlteacher.demo.pojo.vo.lessoncase;

import com.qlteacher.demo.pojo.dto.lessoncase.LessonCaseStructureLimitDTO;
import lombok.Data;

import java.util.List;

/**
 * 上传课例子项
 *
 * @author
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
public class UploadLessonItemVO {

    /**
     * 编号
     **/
    private String id;
    /**
     * 活动编号
     **/
    private String activityId;
    /**
     * 课例编号
     **/
    private String lessonCaseId;
    /**
     * 结构项编号
     **/
    private String lessonCaseStructureId;
    /**
     * 名称
     **/
    private String name;
    /**
     * 类型
     **/
    private String type;
    /**
     * 排序
     **/
    private Integer sort;
    /**
     * 是否必须，类型type不等于directory时才有
     **/
    private Boolean require;
    /**
     * 文件数，类型type不等于directory时才有
     **/
    private Integer maxFileCount;
    /**
     * 限制，类型type等于data时才有
     **/
    private List<LessonCaseStructureLimitDTO> limit = null;
    /**
     * 内容，类型type等于data时才有
     **/
    private List<StructureResourceItem> content = null;
    /**
     * 标题，类型type等于info时才有
     **/
    private String title;
    /**
     * 简介，类型type等于info时才有
     **/
    private String summary;

    /**
     * 标签
     */
    private List<String> tags;
    /**
     * 讲师编号，类型type等于info时才有
     **/
    private String lecturerId;
    /**
     * 讲师姓名，类型type等于info时才有
     **/
    private String lecturerName;
    /**
     *
     **/
    private List<UploadLessonItemVO> children = null;

}

