package com.qlteacher.demo;

import com.qlteacher.demo.pojo.conf.ConfigUtil;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

/**
 * 获取获取登录token demo
 */
public class AccessTokenDemo {


    public static void main(String[] args) {
        System.out.println(getAccessToken());
    }

    public static String accessToken = null;

    public static String getAccessToken() {
        if(null != accessToken){
            return accessToken;
        }
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        try {
            OAuthClientRequest request = OAuthClientRequest
                    .tokenLocation(ConfigUtil.getConfig().getOauthUrl())
                    .setGrantType(GrantType.PASSWORD).setClientId(ConfigUtil.getConfig().getClient().getClientId())
                    .setClientSecret(ConfigUtil.getConfig().getClient().getClientSecret())
                    .setUsername(ConfigUtil.getConfig().getClient().getUserName())
                    .setPassword(ConfigUtil.getConfig().getClient().getUserPassword()).buildBodyMessage();
            OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(request, OAuth.HttpMethod.POST);
            String accessToken = oAuthResponse.getAccessToken();

            return accessToken;
        } catch (OAuthSystemException | OAuthProblemException e) {
            e.printStackTrace();
        }
        return "";
    }
}
