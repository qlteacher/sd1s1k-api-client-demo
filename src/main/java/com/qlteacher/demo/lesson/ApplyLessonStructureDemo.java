package com.qlteacher.demo.lesson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qlteacher.demo.AccessTokenDemo;
import com.qlteacher.demo.Constant;
import com.qlteacher.demo.pojo.conf.ConfigUtil;
import com.qlteacher.demo.pojo.vo.SignatureVO;
import com.qlteacher.demo.pojo.vo.StructureSignatureVO;
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
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/**
 * @author 江立国 2024/8/15 15:07
 */
public class ApplyLessonStructureDemo {

    private static final String STRUCTURE_ID = "#structureId#";

    @SneakyThrows
    public static void main(String[] args) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("file/upload-document.docx");
        File file = Constant.getFile("test-update.json");
        if(file.exists()) {
            FileInputStream in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            in.close();
            String body = new String(bytes, "utf-8");
            UploadLessonCaseInfoVO infoVO = JSON.parseObject(body, UploadLessonCaseInfoVO.class);
            file =  new File(resource.getPath());
            List<StructureSignatureVO> list = applyLessonStructure(infoVO.getId(), STRUCTURE_ID, file.getName(), 0);
            System.out.println(JSON.toJSONString(list));
        }
    }

    @SneakyThrows
    public static List<StructureSignatureVO> applyLessonStructure(String lessonId, String structureId, String fileName, int index){
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        //获取token
        String accessToken = AccessTokenDemo.getAccessToken();
        OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest(
                ConfigUtil.getConfig().getLesson().getApplyLessonStructureUrl().concat(String.format("%s/%s", lessonId,structureId)))
                .setAccessToken(accessToken).buildHeaderMessage();
        bearerClientRequest.setHeader("Content-Type","application/json");
        //添加提交数据
        JSONArray array = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("index", 0);
        object.put("fileName", fileName);
        array.add(object);
        bearerClientRequest.setBody(array.toJSONString());

        OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.POST, OAuthResourceResponse.class);
        String body = resourceResponse.getBody();
        StructureSignatureVO[] structureSignatureVOS = JSON.parseObject(body, StructureSignatureVO[].class);
        return Arrays.asList(structureSignatureVOS);
    }
}
