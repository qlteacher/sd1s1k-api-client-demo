package com.qlteacher.demo.pojo.vo.lessoncase;

import lombok.Data;

/**
 * UpdataLessonItemVoLimit
 *
 * @author
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
public class UploadLessonItemLimitVO {

    /**
     *
     **/
    private String type;
    /**
     * 最大限制
     **/
    private Integer maxSize;
    /**
     * 最大数量
     **/
    private Integer maxCount;

}

