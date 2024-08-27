package com.qlteacher.demo.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

/**
 * 上传许可对象
 *
 * @author
 * @date 2024-07-19T10:16:34.371+08:00[Asia/Shanghai]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignatureVO {

    /**
     * 签名 /上传视频时会返回这个属性
     **/
    private String signature;

    /**
     * 预签名地址 /上传非视频时会返回这个属性
     */
    private URL signatureUrl;

    /**
     * 文件key/appId(视频时)
     **/
    private String key;

    /**
     * 域名 上传非视频时这个属性有效。
     **/
    private String domain;

    /**
     * 区域名
     */
    private String region;

}

