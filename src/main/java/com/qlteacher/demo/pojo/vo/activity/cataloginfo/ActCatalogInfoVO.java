package com.qlteacher.demo.pojo.vo.activity.cataloginfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 活动目录信息
 *
 * @author DXH
 */
@Data
public class ActCatalogInfoVO implements Serializable {

    private static final long serialVersionUID = 8581707520893016863L;
    /**
     * 活动信息id，ID 编号
     **/
    private String id;
    /**
     * 活动Id
     **/
    private String activityId;
    /**
     * 目录结构Id
     **/
    private String catalogId;
    /**
     * 目录信息名称
     **/
    private String name;
    /**
     * 父级id
     **/
    private String parentId;
    /**
     * 级别
     **/
    private Integer level;
    /**
     * 排序
     **/
    private Integer sort;
    /**
     * 简介
     **/
    private String summary;
    /**
     * 字典项id
     **/
    private String dictItemId;
    /**
     * 路径
     **/
    private String path;
    /**
     * 名称路径
     **/
    private String namePath;

}

