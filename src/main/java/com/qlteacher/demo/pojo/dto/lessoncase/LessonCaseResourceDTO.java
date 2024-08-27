package com.qlteacher.demo.pojo.dto.lessoncase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 江立国 2024/8/5 10:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonCaseResourceDTO {

    private String url;

    private String showUrl;

    private String domain;

    private String fileName;

    private String status;
}
