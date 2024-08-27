package com.qlteacher.demo.pojo.dto.lessoncase;

import lombok.Data;

import java.io.Serializable;

/**
 * 课例结构限制DTO
 *
 * @author kevin
 * @date 2024/7/19 15:14
 */
@Data
public class LessonCaseStructureLimitDTO implements Serializable {
    private static final long serialVersionUID = -4951575633173742095L;
    /**
     * 文件格式
     */
    private String fileFormat;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件最大数
     */
    private Long maxCount;
}
