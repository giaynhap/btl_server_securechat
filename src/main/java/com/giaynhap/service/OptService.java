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
public class OptService {
    private static Random rnd = new Random();

    public void sendOpt(String msisgn,String code)  throws Exception {

        System.out.println(" call send opt ");
        HttpPost post = new HttpPost("http://124.158.6.219/xosolive/gnapi.php");
        String message = "Secure Chat Application \nYour opt Code"+code;
        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("msisdn", msisgn));
        urlParameters.add(new BasicNameValuePair("message", message));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public String randomCode(){
        StringBuilder sb = new StringBuilder(5);
        for(int i=0; i < 5; i++)
            sb.append((char)('0' + rnd.nextInt(10)));
        return sb.toString();
    }
}
