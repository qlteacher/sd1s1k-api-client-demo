package com.qlteacher.demo.pojo.vo.lessoncase;

import com.qlteacher.demo.pojo.dto.lessoncase.LessonCaseStructureLimitDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 课例结构VO
 *
 *
 * @author kevin
 * @date 2024/7/19 14:37
 */
@Data
public class ActLessonCaseStructureVO implements Serializable {

    private static final long serialVersionUID = 4811818385149896325L;

    /**
     * id主键
     */
    private String id;

    /**
     * 活动id
     */
    private String activityId;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 结构简介
     */
    private String summary;

    /**
     * 类型：目录  数据  信息
     */
    private String type;

    /**
     * 限制
     */
    private List<LessonCaseStructureLimitDTO> limit;

    /**
     * 最大文件数
     */
    private Integer maxFileCount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否必填
     */
    private Boolean require;

    /**
     * 是否显示
     */
    private Boolean show;



    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

}
