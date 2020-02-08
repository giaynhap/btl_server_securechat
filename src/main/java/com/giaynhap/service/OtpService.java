package com.giaynhap.service;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OtpService {
    private static Random rnd = new Random();

    public void sendOtp(String msisgn, String code)  throws Exception {

        HttpPost post = new HttpPost("http://124.158.6.219/xosolive/gnapi.php");
        String message = "Secure Chat Application \nYour OTP code:  "+code;
        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("msisdn", msisgn));
        urlParameters.add(new BasicNameValuePair("message", message));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
        }
    }
    public String randomCode(){
        int codeSize =7;
        StringBuilder sb = new StringBuilder(codeSize);
        for(int i=0; i < codeSize; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }
}
