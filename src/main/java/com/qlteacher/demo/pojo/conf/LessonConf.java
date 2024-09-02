package com.qlteacher.demo.pojo.conf;

import com.qlteacher.demo.pojo.dto.CreativeTeamDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 江立国 2024/8/14 16:50
 */
@Data
public class LessonConf {

    private String schoolId = "";

    private List<CreativeTeamDTO> teams = new ArrayList<>();

    private String applyLessonUrl;

    private String submitLessonUrl;

    private String loadLessonUrl;

    private String updateLessonUrl;

    private String deleteLessonUrl;

    private String queryLessonsPageUrl;

    private String lessonCoverUrl;

    private String applyLessonStructureUrl;

    private String commitLessonStructuresUrl;

    private String lessonCheckUrl;

    private String lessonConfirmUrl;
}
