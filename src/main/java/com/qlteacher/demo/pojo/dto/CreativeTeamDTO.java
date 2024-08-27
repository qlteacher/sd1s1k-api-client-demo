package com.qlteacher.demo.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创作团队成员
 * @author 
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreativeTeamDTO {

  /**
   * 用户编号
   **/
  private String  userId;

  /**
   * 类型
   **/
  private String  type;

}

