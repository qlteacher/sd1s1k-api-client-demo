package com.qlteacher.demo.utils;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.dto.CreativeTeamDTO;
import lombok.SneakyThrows;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 模板处理工具类
 *
 * @author 江立国 2024/7/31 14:00
 */
public class TestMarkerTemplate {

    public static String TITLE = "#课例标题#";

    @SneakyThrows
    public static void main(String[] args) {
        File infile = Constant.getFile("test.json");
        if(infile.exists()) {
            FileInputStream in = new FileInputStream(infile);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            String outBody = assemble(body, TITLE);
            File outFile = Constant.getFile("test-construct.json");
            FileOutputStream out = new FileOutputStream(outFile);
            out.write(outBody.getBytes("utf-8"));
            out.flush();
            out.close();
        }
    }

    public static String assemble(String body, String title) {
        String[] properties = loadProperties(body);
        Map<String, String> content = assemblePropertiesToMap(title, properties);
        String outBody = constructTemplate(body, content);
        return outBody;
    }

    /**
     * 转换配课例模板填空属性为映射表
     *
     * @param title
     * @param properties
     * @return
     */
    public static Map<String, String> assemblePropertiesToMap(String title, String[] properties) {
        Map<String, String> content = new HashMap<>();
        //声明tags信息 实际应用中每一个tags相关属性需要生成个自对应的tags的JSON字符串
        String tags = JSON.toJSON(new String[]{"#tags1#","#tags2#"}).toString();
        Random random = new Random(System.currentTimeMillis());
        List<CreativeTeamDTO> teams = ConfigUtil.getConfig().getLesson().getTeams().stream().filter(team ->!"member".equals(team.getType())).collect(Collectors.toList());
        for (String property : properties) {
            //结构环节属性赋值
            if(property.startsWith("structure_")) {
                int randomIndex = teams.isEmpty()? 0 : random.nextInt(teams.size());
                if (property.endsWith("_summary")) {
                    content.put(property, "");
                    continue;
                }
                if (property.endsWith("_title")) {
                    content.put(property, "");
                    continue;
                }
                if (property.endsWith("_lecturer_id")) {
                    content.put(property, teams.isEmpty()?"":teams.get(randomIndex).getUserId());
                    continue;
                }
                if (property.endsWith("_lecturer_name")) {
                    content.put(property, "");
                    continue;
                }
                if (property.endsWith("_tags")) {
                    content.put(property, "null");//这里需要自己组织数据 JSON.toJSON(new String[]{"#tags1#","#tags2#"}).toString()
                    continue;
                }
            }
            //课例基础属性中固有填空属性赋值
            if(property.equals("lesson_summary")) {
                content.put(property, "");
                continue;
            }
            if(property.equals("lesson_title")) {
                content.put(property, title);
                continue;
            }
            if(property.equals("lesson_school_id")){
                content.put(property, ConfigUtil.getConfig().getLesson().getSchoolId());
                continue;
            }
            if(property.equals("lesson_ranking")) {
                content.put(property, "1");
                continue;
            }
            if(property.equals("lesson_tags")) {
                content.put(property, "null");
                continue;
            }
            if(property.equals("lesson_authors")) {
                if(!ObjectUtils.isEmpty(teams)) {
                    content.put(property, JSON.toJSON(ConfigUtil.getConfig().getLesson().getTeams()).toString());
                }else{
                    content.put(property, "null");
                }
                continue;
            }
        }
        return content;
    }

    public static String constructTemplate(String template, Map<String, String> content) {
        ExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new TemplateParserContext();
        Expression expression = parser.parseExpression(template, parserContext);
        //组织需要填报的信息
        EvaluationContext context = new StandardEvaluationContext();

        content.entrySet().forEach(entry -> context.setVariable(entry.getKey(), entry.getValue()));

        return expression.getValue(context).toString();
    }

    public static String[] loadProperties(String template) {
        Pattern compile = Pattern.compile("#\\{#([^\\}]+)\\}");
        Matcher matcher = compile.matcher(template);
        List<String> properties = new ArrayList<>();
        while (matcher.find()){
            properties.add(matcher.group(1));
        }
        return properties.toArray(new String[0]);
    }
}
