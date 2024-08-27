package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSONObject;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

/**
 * @author 江立国 2024/8/15 13:54
 */
public class CommitLessonCoverDemo {

    @SneakyThrows
    public static Boolean commitLessonCoverDemo(String id, String key, String domain) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getLessonCoverUrl().concat(id))
                .setAccessToken(accessToken).buildHeaderMessage();
        bearerClientRequest.setHeader("Content-Type","application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("domain", domain);
        bearerClientRequest.setBody(jsonObject.toJSONString());
        try {
            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.PUT, OAuthResourceResponse.class);
            String body = resourceResponse.getBody();
            //输出访问结果
            System.out.println(body);
            return Boolean.valueOf(body);
        }catch (Exception e) {
            System.err.printf("id:[%s], key:[%s], domain:[%s]", id, key, domain);
            throw e;
        }
    }
}
