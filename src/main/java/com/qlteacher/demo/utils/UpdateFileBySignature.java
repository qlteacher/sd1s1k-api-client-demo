package com.qlteacher.demo.utils;

import com.qlteacher.demo.pojo.vo.SignatureVO;
import lombok.SneakyThrows;
import org.apache.oltu.oauth2.common.OAuth;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * 通过许可上传文件工具类
 *
 * @author 江立国 2024/8/15 11:04
 */
public class UpdateFileBySignature {

    @SneakyThrows
    public static Boolean updateFileSignature(SignatureVO signature, File file) {
        HttpURLConnection conn = (HttpURLConnection) signature.getSignatureUrl().openConnection();
        conn.setRequestMethod(OAuth.HttpMethod.PUT);
        // 设置允许输出
        conn.setDoOutput(true);

        FileInputStream fis = new FileInputStream(file);
        // 写入对象内容
        byte[] contentBytes = new byte[40960];
        int length = 0;
        try (OutputStream os = conn.getOutputStream()) {
            while ((length = fis.read(contentBytes)) > 0) {
                os.write(contentBytes, 0, length);
            }
            os.flush();
            fis.close();
        }
        // 检查响应码
        int responseCode = conn.getResponseCode();
        System.err.println("Response Code : " + responseCode);
        String result;
        if (responseCode != 200) {
            InputStream errorStream = conn.getErrorStream();
            byte[] buffer = new byte[errorStream.available()];
            int read = errorStream.read(buffer);
            errorStream.close();
            result = new String(buffer);
        } else {
            InputStream in = conn.getInputStream();
            byte[] buffer = new byte[in.available()];
            int read = in.read(buffer);
            in.close();
            result = new String(buffer);
        }
        // 关闭连接
        conn.disconnect();

        System.out.println(result);
        return responseCode == 200;
    }
}
