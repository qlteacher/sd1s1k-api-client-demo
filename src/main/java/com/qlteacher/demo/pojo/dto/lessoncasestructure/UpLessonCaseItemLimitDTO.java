package com.qlteacher.demo.pojo.dto.lessoncasestructure;

import lombok.Data;

/**
 * UpLessonCaseItemParamLimit
 * @author 
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
public class UpLessonCaseItemLimitDTO {

  /**
   * 类型
   **/
  private String  type;
  /**
   * 最大限制
   **/
  private Integer  maxSize;

}

