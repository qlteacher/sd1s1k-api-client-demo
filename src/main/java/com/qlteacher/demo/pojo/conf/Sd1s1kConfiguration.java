package com.qlteacher.demo.pojo.conf;

import lombok.Data;
import lombok.ToString;

/**
 * @author 江立国 2024/8/12 11:21
 */
@Data
@ToString
public class Sd1s1kConfiguration {

    private String oauthUrl;

    private ClientConf client;

    private BaseInfoConf baseInfo;

    private LessonConf lesson;

}
