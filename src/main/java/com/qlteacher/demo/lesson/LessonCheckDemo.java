package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 检查课程
 *
 * @author 江立国 2024/8/16 18:25
 */
public class LessonCheckDemo {

    @SneakyThrows
    public static void main(String[] args) {
        File file = Constant.getFile("test-update.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            List<String> error = lessonCheck(infoVO.getId());
            if(error.isEmpty()){
                System.out.println("课例检查完成，课例已经补充完善！");
            } else {
                System.out.println("课例检查完成,还有内容需要完善："+error.toString());
            }
        }
    }


    @SneakyThrows
    public static List<String> lessonCheck(String lessonId) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //先获取token
        String accessToken = AccessTokenDemo.getAccessToken();

        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getLessonCheckUrl().concat(lessonId))
                .setAccessToken(accessToken)
                .buildHeaderMessage();

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        String body = resourceResponse.getBody();
        System.out.println(body);
        ArrayList errorList = JSON.parseObject(body, ArrayList.class);
        return errorList;
    }
}
