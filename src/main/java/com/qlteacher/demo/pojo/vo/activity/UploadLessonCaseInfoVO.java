package com.qlteacher.demo.pojo.vo.activity;

import com.qlteacher.demo.pojo.dto.CreativeTeamDTO;
import com.qlteacher.demo.pojo.vo.lessoncase.UploadLessonItemVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传课程结构主体
 *
 * @author
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
public class UploadLessonCaseInfoVO {

    /**
     * ID 编号
     **/
    private String id;
    /**
     * 标题
     **/
    private String title;
    /**
     * 标签
     **/
    private List<String> tags = new ArrayList<>();
    /**
     * 概要
     **/
    private String summary;
    /**
     * 学校编号
     **/
    private String schoolId;
    /**
     * 课程编号
     **/
    private String activityId;

    /**
     * 活动目录编号
     **/
    private String catalogId;
    /**
     * 作者编号
     **/
    private List<CreativeTeamDTO> authors = new ArrayList<>();
    /**
     * 排序号，也就是排名
     **/
    private Integer ranking;
    /**
     * 预览地址
     **/
    private String viewUrl;
    /**
     * 课程状态
     **/
    private String status;
    /**
     *
     **/
    private Object statusStat;
    /**
     *
     **/
    private List<UploadLessonItemVO> structures = new ArrayList<>();

}

