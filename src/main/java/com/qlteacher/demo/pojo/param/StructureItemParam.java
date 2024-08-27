package com.qlteacher.demo.pojo.param;

import lombok.Data;

/**
 * @author 江立国 2024/8/16 15:06
 */
@Data
public class StructureItemParam {
    /**
     * 域名，对于视频类的资源这里可以传appId
     */
    private String domain;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 文件下标
     */
    private long index;
    /**
     * 文件地址，文件上传路径或fileId
     */
    private String key;
    /**
     * 区域名，视频类的资源必须传该属性
     */
    private String region;
    /**
     * 结构项编号
     */
    private String structureId;
}

