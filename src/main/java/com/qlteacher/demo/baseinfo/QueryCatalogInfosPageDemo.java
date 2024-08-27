package com.qlteacher.demo.baseinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.activity.cataloginfo.ActCatalogInfoVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;

import java.util.List;

/**
 * 分页获取 活动课程目录信息
 *
 * @author 江立国 2024/8/14 16:21
 */
public class QueryCatalogInfosPageDemo {
    private static String TEST_ACTIVITY_ID = Constant.activityId;

    private static String TEST_PARENT_ID = "#parentId#";

    public static void main(String[] args) {
        List<ActCatalogInfoVO> actCatalogInfoVOS = queryCatalogInfosPage();
        System.out.println(JSON.toJSONString(actCatalogInfoVOS, SerializerFeature.PrettyFormat, SerializerFeature.WriteDateUseDateFormat));
    }

    @SneakyThrows
    public static List<ActCatalogInfoVO> queryCatalogInfosPage(){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getBaseInfo().getQueryCatalogInfosPageUrl().concat(String.format("?activityId=%s&parentId=%s", TEST_ACTIVITY_ID, TEST_PARENT_ID)))
                .setAccessToken(accessToken).buildHeaderMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        String body = resourceResponse.getBody();
        //输出访问结果
        System.out.println(body);
        List<ActCatalogInfoVO> actCatalogInfoVOS = JSON.parseArray(body, ActCatalogInfoVO.class);
        return actCatalogInfoVOS;
    }

}
