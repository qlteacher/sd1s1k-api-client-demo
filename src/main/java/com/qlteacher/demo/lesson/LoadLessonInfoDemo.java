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
 * 读取课例信息
 *
 * @author 江立国 2024/8/15 9:03
 */
public class LoadLessonInfoDemo {

    @SneakyThrows
    public static void main(String[] args) {
        File file = Constant.getFile(String.format("test-%s-%s-result.json", Constant.activityId, Constant.catalogId));
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            UploadLessonCaseInfoVO caseInfoVO = loadLessonInfoDemo(infoVO.getId());
            System.out.println(JSON.toJSONString(caseInfoVO));
        }
    }

    @SneakyThrows
    public static UploadLessonCaseInfoVO loadLessonInfoDemo(String id) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            //先获取token
            String accessToken = AccessTokenDemo.getAccessToken();

            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                    ConfigUtil.getConfig().getLesson().getLoadLessonUrl().concat(id))
                    .setAccessToken(accessToken)
                    .buildHeaderMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String body = resourceResponse.getBody();
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            return infoVO;
        } catch (Exception e) {
            throw e;
        }
    }
}
