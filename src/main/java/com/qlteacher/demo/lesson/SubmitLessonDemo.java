package com.qlteacher.demo.lesson;

import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

import java.io.File;
import java.io.FileInputStream;

/**
 * 提交课例信息
 * @author 江立国 2024/8/14 17:47
 */
public class SubmitLessonDemo {

    @SneakyThrows
    public static void main(String[] args) {
        File file = Constant.getFile("test-construct.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            submitLessonDemo(body);
        }
    }

    @SneakyThrows
    public static String submitLessonDemo(String body){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getSubmitLessonUrl())
                .setAccessToken(accessToken).buildHeaderMessage();
        bearerClientRequest.setHeader("Content-Type","application/json");
        bearerClientRequest.setBody(body);
        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.POST, OAuthResourceResponse.class);
        String context = resourceResponse.getBody();
        //输出访问结果
        System.out.println(context);
        return context;
    }
}
