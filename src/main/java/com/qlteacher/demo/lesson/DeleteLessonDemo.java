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

/**
 * @author 江立国 2024/8/15 9:31
 */
public class DeleteLessonDemo {

    @SneakyThrows
    public static void main(String[] args) {
        File file = Constant.getFile("test-result.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            if(deleteLessonDemo(infoVO.getId())) {
                System.out.println("删除成功");
            }
        }
    }

    @SneakyThrows
    public static Boolean deleteLessonDemo(String id){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            //先获取token
            String accessToken = AccessTokenDemo.getAccessToken();

            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                    ConfigUtil.getConfig().getLesson().getDeleteLessonUrl().concat(id))
                    .setAccessToken(accessToken)
                    .buildHeaderMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.DELETE, OAuthResourceResponse.class);
            String body = resourceResponse.getBody();
            return Boolean.valueOf(body);
        } catch (Exception e) {
            throw e;
        }
    }
}
