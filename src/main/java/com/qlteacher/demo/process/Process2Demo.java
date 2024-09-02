package com.qlteacher.demo.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.dto.CreativeTeamDTO;
import com.qlteacher.demo.utils.TestMarkerTemplate;
import lombok.SneakyThrows;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 第二步通过SpringEL处理引擎将模板处理成可提交的JSON数据
 *
 * @author 江立国 2024/8/16 11:09
 */
public class Process2Demo {

    private static final String TITLE = "文学阅读与创意表达测试优课";

    @SneakyThrows
    public static void main(String[] args) {
        //加载添加课例数据模板
        File file = Constant.getFile(String.format("test-%s-%s.template", Constant.activityId, Constant.catalogId));
        //判断文件是否存在
        if(file.exists()){
            InputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            String template = new String(bytes, "utf-8");
            //取出模板中所有需要填写的属性
            String[] properties = TestMarkerTemplate.loadProperties(template);
            System.out.println(String.join(",", properties));
            //赋值人员和学校信息 这些信息可以通过基础信息查询
            //需要给生成映射表方法提供一个课例的标题 //可以重写这段生成MAP的代码生成你需要的MAP
            Map<String, String> content = TestMarkerTemplate.assemblePropertiesToMap(TITLE, properties);
            System.out.println(JSON.toJSONString(content, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
            Map<String, List<CreativeTeamDTO>> teamMap = ConfigUtil.getConfig().getLesson().getTeams().stream().collect(Collectors.groupingBy(CreativeTeamDTO::getType));
            String teamMaster = teamMap.get("master").get(0).getUserId();//一个团队中只有一个主持人
            String teamLecturer1 = teamMap.get("lecturer").get(0).getUserId(); //这里的例子里配置了两个核心成员
            String teamLecturer2 = teamMap.get("lecturer").get(1).getUserId(); //这里的例子里配置了两个核心成员
            content.put("lesson_title", "烟台市小学语文课例1");
            content.put("lesson_summary", "三年级小学生有一定的古诗积累，课前需要了解古代乞巧节的风俗，唐代人们的生活状况，再学习《乞巧》才能较为容易，真切体会《嫦娥》的孤独寂寞，有些难度。三年级小学生有一定的古诗积累，课前需要了解古代乞巧节的风俗，唐代人们的生活状况，再学习《乞巧》才能较为容易，真切体会《嫦娥》的孤独寂寞，有些难度。三年级小学生有一定的古诗积累，课前需要了解古代乞巧节的风俗，唐代人们的生活状况，再学习《乞巧》才能较为容易，真切体会《嫦娥》的孤独寂寞，有些难度。");
            content.put("lesson_tags", JSON.toJSONString(new String[]{"古诗","乞巧", "嫦娥"}));
            content.put("lesson_ranking", "2");
            content.put("structure_1_0_0_title", "古诗两首");
            content.put("structure_1_0_0_lecturer_id", teamMaster); //指定主讲
            content.put("structure_1_0_0_summary", "《古诗两首》是人教版第六册第八组的起头课文。这组教材围绕民间故事和神话传说这一主题来编排。再一次把学生带回儿时倾听大人们讲故事的快乐中去，感受古人的想象是多么丰富。");
            content.put("structure_1_0_0_tags", JSON.toJSONString(new String[]{"小学","古诗", "三年级"}));
            content.put("structure_1_1_0_title", "乞巧");
            content.put("structure_1_1_0_lecturer_id", teamLecturer1); //指定主讲
            content.put("structure_1_1_0_summary", "《乞巧》是唐代诗人林杰描写民间七夕乞巧盛况的古诗。也是一首想象丰富，流传很广的古诗，浅显易懂，并且涉及到家喻户晓的神话传说故事《牛郎织女》。如果学生理解古代生活背景，了解古代乞巧节的风俗，便较容易体会诗歌内容，体会诗歌情感");
            content.put("structure_1_1_0_tags", JSON.toJSONString(new String[]{"乞巧","唐朝", "七夕", "牛郎织女"}));
            content.put("structure_1_2_0_title", "嫦娥");
            content.put("structure_1_2_0_lecturer_id", teamLecturer2); //指定主讲
            content.put("structure_1_2_0_summary", "《嫦娥》这首古诗情感绵长幽怨，学生在情感上很难跟进，共鸣。因此，本课目标定位为感受而非感悟，力图借助本诗和嫦娥奔月的传说让学生感受到古文化的瑰丽和神奇");
            content.put("structure_1_2_0_tags", JSON.toJSONString(new String[]{"嫦娥","奔月", "神话"}));
            content.put("lesson_authors", JSON.toJSONString(ConfigUtil.getConfig().getLesson().getTeams()));

            //通过模板和数据映射生成提交用Json数据
            String submitJson = TestMarkerTemplate.constructTemplate(template, content);
            //指定生成提交用json数据的保存位置
            File outFile = Constant.getFile(String.format("test-%s-%s-submit.json", Constant.activityId, Constant.catalogId));
            OutputStream out = new FileOutputStream(outFile);
            out.write(submitJson.getBytes("utf-8"));
            out.flush();
            out.close();
        }
    }

}
