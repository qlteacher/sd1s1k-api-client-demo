package com.qlteacher.demo.baseinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.SimpleTeacherInfoVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

/**
 * @author 江立国 2024/8/14 15:50
 */
public class LoadOtoelBaseInfoTeacherDemo {
    private static String TEST_ACTIVITY_ID = Constant.activityId;
    private static String TEST_CARDNUM = "#身份证号#";
    private static String TEST_NAME = "#人员姓名#";

    public static void main(String[] args) {
        SimpleTeacherInfoVO simpleTeacherInfoVO = loadOtoelBaseInfoTeacher(TEST_ACTIVITY_ID, TEST_CARDNUM, TEST_NAME);
        System.out.println(JSON.toJSONString(simpleTeacherInfoVO, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
    }

    @SneakyThrows
    public static SimpleTeacherInfoVO loadOtoelBaseInfoTeacher(String actId, String cardnum, String name){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getBaseInfo().getLoadTeacherUrl().concat(actId).concat(String.format("?cardnum=%s&name=%s", cardnum, name)))
                .setAccessToken(accessToken).buildHeaderMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        String body = resourceResponse.getBody();
        //输出访问结果
        System.out.println(body);
        SimpleTeacherInfoVO simpleTeacherInfoVO = JSON.parseObject(body, SimpleTeacherInfoVO.class);
        return simpleTeacherInfoVO;
    }
}
