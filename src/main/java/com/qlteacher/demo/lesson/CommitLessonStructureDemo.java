package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.param.StructureItemParam;
import com.qlteacher.demo.pojo.vo.SignatureVO;
import com.qlteacher.demo.pojo.vo.StructureSignatureVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 江立国 2024/8/15 13:54
 */
public class CommitLessonStructureDemo {

    @SneakyThrows
    public static Boolean commitLessonStructureDemo(String id, StructureSignatureVO... signatures) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getCommitLessonStructuresUrl().concat(id))
                .setAccessToken(accessToken).buildHeaderMessage();
        bearerClientRequest.setHeader("Content-Type","application/json");
        List<StructureItemParam> list = Arrays.stream(signatures).map(signature -> {
            StructureItemParam item = new StructureItemParam();
            item.setStructureId(signature.getStructureId());
            item.setDomain(signature.getSignature().getDomain());
            item.setRegion(signature.getSignature().getRegion());
            item.setKey(signature.getSignature().getKey());
            item.setIndex(signature.getIndex());
            item.setFileName(signature.getFile().getName());
            item.setFileSize(signature.getFile().length());
            return item;
        }).collect(Collectors.toList());

        bearerClientRequest.setBody(JSON.toJSONString(list));
        try {
            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.PUT, OAuthResourceResponse.class);
            String body = resourceResponse.getBody();
            //输出访问结果
            System.out.println(body);
            return Boolean.valueOf(body);
        }catch (Exception e) {
            System.err.printf("id:[%s], %s", id, JSON.toJSONString(list));
            throw e;
        }
    }
}
