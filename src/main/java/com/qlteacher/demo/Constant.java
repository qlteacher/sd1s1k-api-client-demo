package com.qlteacher.demo;

import java.io.File;
import java.net.URL;

/**
 * 这个流程中作为常量使用的参数都放这里
 *
 * @author 江立国 2024/8/16 10:08
 */
public class Constant {

    public static final String activityId = "sd2024";

    //catalogId可以通过查阅文档获取
    public static final String catalogId = "#catalogId#";

    public static final String userId = "#用户编号#";

    public static final String userName = "#用户姓名#";

    public static final String schoolId = "#学校编号#";

    //临时跟目录
    public static final String baseOutPath = "/vars/otoel/";

    public static File getFile(String name){
        File basedir = new File(baseOutPath);
        //如果目录不存在就创建目录
        if(!basedir.exists()) {
            basedir.mkdirs();
        }
        return new File(basedir, name);
    }

}
