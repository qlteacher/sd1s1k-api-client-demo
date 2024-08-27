package com.qlteacher.demo.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.File;

/**
 * 结构项实例签名
 *
 * @author 江立国 2024/8/6 8:40
 */
@Data
public class StructureSignatureVO {

    /**
     * 结构项实例编号
     */
    private String id;
    /**
     * 结构项编号
     */
    private String structureId;
    /**
     * 序号/下标
     */
    private Integer index;
    /**
     * 上传签名
     */
    private SignatureVO signature;

    @JsonIgnore
    private File file;
}
