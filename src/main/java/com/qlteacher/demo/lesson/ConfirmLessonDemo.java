package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.SignatureVO;
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
import java.net.URLEncoder;

/**
 * 确认课程完善，并正式提交课例
 *
 * @author 江立国 2024/8/17 9:25
 */
public class ConfirmLessonDemo {

    @SneakyThrows
    public static void main(String[] args) {
        File file = new File("/vars/test-update.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            if(confirmLesson(infoVO.getId(), "我确认课程已经完善，可以提交到一师一课正式课例库里。")){
                System.out.println("确认课例成功！");
            }
        }
    }

    /**
     * 确认课程完善，并正式提交
     *
     * @param lessonId
     * @param signature
     * @return
     */
    @SneakyThrows
    public static Boolean confirmLesson(String lessonId, String signature) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getLessonConfirmUrl().concat(lessonId))
                .setAccessToken(accessToken).buildHeaderMessage();
        bearerClientRequest.setHeader("Content-Type","application/x-www-form-urlencoded");
        bearerClientRequest.setBody("signature=" + signature);
        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.PUT, OAuthResourceResponse.class);
        String body = resourceResponse.getBody();
        //输出访问结果
        System.out.println(body);
        return Boolean.valueOf(body);
    }
}
