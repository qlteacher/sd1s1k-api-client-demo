package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.PageList;
import com.qlteacher.demo.pojo.vo.activity.UploadLessonCaseInfoVO;
import com.qlteacher.demo.pojo.vo.lessoncase.ClassBaseInfoVO;
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
 * @author 江立国 2024/8/15 9:58
 */
public class QueryLessonsPageDemo {

    private static String TEST_ACTIVITY_ID = Constant.activityId;

    public static void main(String[] args) {
        queryLessonsPageDemo(TEST_ACTIVITY_ID);
    }

    @SneakyThrows
    public static PageList<ClassBaseInfoVO> queryLessonsPageDemo(String actId){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            //先获取token
            String accessToken = AccessTokenDemo.getAccessToken();

            OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                    ConfigUtil.getConfig().getLesson().getQueryLessonsPageUrl().concat(String.format("?activityId=%s&regionId=%s&state=%s&pageSize=%d&pageIndex=%d&count=%s", actId, "", "", 20,1,"true")))
                    .setAccessToken(accessToken)
                    .buildHeaderMessage();

            OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
            String body = resourceResponse.getBody();
            PageList pageList = JSON.parseObject(body, PageList.class);
            pageList.setList((List) pageList.getList().stream().map(obj -> ((JSONObject) obj).toJavaObject(ClassBaseInfoVO.class)).collect(Collectors.toList()));
            return pageList;
        } catch (Exception e) {
            throw e;
        }
    }
}
