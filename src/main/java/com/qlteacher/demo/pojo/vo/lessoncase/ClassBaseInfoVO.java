package com.qlteacher.demo.pojo.vo.lessoncase;

import com.qlteacher.demo.pojo.dto.CreativeTeamDTO;
import com.qlteacher.demo.pojo.dto.FileStatusStateDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 课例基础信息
 * @author 
 * @date 2024-07-24T09:19:36.329+08:00[Asia/Shanghai]
 */
@Data
public class ClassBaseInfoVO {

  /**
   * ID 编号
   **/
  private String  id;
  /**
   * 标题
   **/
  private String  title;
  /**
   * 标签
   **/
  private List<String> tags = new ArrayList<>();
    /**
   * 概要
   **/
  private String  summary;
  /**
   * 学校编号
   **/
  private String  schoolId;
  /**
   * 课程编号
   **/
  private String  activityId;
  /**
   * 作者编号
   **/
  private List<CreativeTeamDTO> authors = new ArrayList<>();
    /**
   * 排序号，也就是排名
   **/
  private BigDecimal  ranking;
  /**
   * 预览地址
   **/
  private String  viewUrl;
  /**
   * 课程状态
   **/
  private String status;

  /**
   * 课程文件上传统计
   **/
  private FileStatusStateDTO statusStat;

}

