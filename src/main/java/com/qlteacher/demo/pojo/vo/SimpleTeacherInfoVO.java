package com.qlteacher.demo.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 简单教师信息，教师信息
 * @author 
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
@AllArgsConstructor
public class SimpleTeacherInfoVO {

  /**
   * 用户编号
   **/
  private String  userId;
  /**
   * 用户姓名
   **/
  private String  userName;
  /**
   * 学校编号
   **/
  private String  schoolId;
  /**
   * 学校名称
   **/
  private String  schoolName;

}

