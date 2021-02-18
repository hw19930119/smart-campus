package com.sunsharing.smartcampus.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

@Log4j2
public class HttpUtils {
    public static String postJson(String url, JSONObject params){
        String result =null;
        try {
            CloseableHttpClient client = null;
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
                String paramsStr=params.toJSONString();// URLEncoder.encode(params.toJSONString(),"UTF-8");
                httpPost.setEntity(new StringEntity(paramsStr,
                        ContentType.create("application/json", "UTF-8")));
                log.info("ServerHelper.postJson params:", JSONObject.toJSONString(params));
                client = HttpClients.createDefault();
                response = client.execute(httpPost);
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity);
                log.info("ServerHelper.postJson return:",result);
            } finally {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            }
        } catch (Exception e) {
            log.error("ServerHelper.postJson:",e);
        }
        return result;
    }
}
