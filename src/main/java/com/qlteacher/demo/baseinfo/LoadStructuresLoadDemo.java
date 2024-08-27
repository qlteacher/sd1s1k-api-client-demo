package com.qlteacher.demo.baseinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.lessoncase.ActLessonCaseStructureVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 读取传课配置例子
 *
 * @author 江立国 2024/8/14 10:28
 */
public class LoadStructuresLoadDemo {

    private static String TEST_ACTIVITY_ID = Constant.activityId;

    private static String CATALOG_ID = "#catalogId#";

    public static void main(String[] args) {
        List<ActLessonCaseStructureVO> actLessonCaseStructureVOS = loadStructures(TEST_ACTIVITY_ID, CATALOG_ID);
        System.out.println(JSON.toJSONString(actLessonCaseStructureVOS, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
    }

    @SneakyThrows
    public static List<ActLessonCaseStructureVO> loadStructures(String actId, String catalogId) {
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            //先获取token
            String accessToken = AccessTokenDemo.getAccessToken();

            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                    ConfigUtil.getConfig().getBaseInfo().getStructuresUrl().concat(actId).concat("?catalogId="+catalogId))
                    .setAccessToken(accessToken)
                    .buildHeaderMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            JSONArray objects = JSON.parseArray(resourceResponse.getBody());
            List<ActLessonCaseStructureVO> result = objects.stream().map(object -> ((JSONObject) object).toJavaObject(ActLessonCaseStructureVO.class)).collect(Collectors.toList());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
