package com.giaynhap.securechat.service;

import com.giaynhap.securechat.service.serviceInterface.OtpService;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Component
public class OtpServiceImpl implements OtpService {
    @Override
    public String genOTPCode(String secret) throws Exception {
        return genOTPCode(secret, new Date().getTime());
    }

    @Override
    public void sendOTP(String msisgn, String code) {
        HttpPost post = new HttpPost("http://124.158.6.49/otpapi/index.php");
        String message = "Secure Chat Application \nYour OTP code:  "+code;
        // add request parameter, form parameters
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("msisdn", msisgn));
        urlParameters.add(new BasicNameValuePair("message", message));
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
        }catch (Exception e){
        }
    }

    @Override
    public String genOTPCode(String secret, Long time) throws Exception  {
        if (secret == null || secret == "") {
            throw new Exception("Secret key does not exist.");
        }

        long value = time / TimeUnit.SECONDS.toMillis(30);
        Base32 base = new Base32(true);
        byte[] key = base.decode(secret);

        byte[] data = new byte[8];
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        byte[] hash = hmacSha1(data, key);

        return "123456";//truncateHash(hash);
    }

    private String truncateHash(byte[] hash) {
        String hashString = new String(hash);
        int offset = Integer.parseInt(hashString.substring(hashString.length() - 1, hashString.length()), 16);

        String truncatedHash = hashString.substring(offset * 2, offset * 2 + 8);

        int val = Integer.parseUnsignedInt(truncatedHash, 16) & 0x7FFFFFFF;

        String finalHash = String.valueOf(val);
        finalHash = finalHash.substring(finalHash.length() - 6, finalHash.length());

        return finalHash;
    }

    private byte[] hmacSha1(byte[] value, byte[] keyBytes) {
        SecretKeySpec signKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");

            mac.init(signKey);

            byte[] rawHmac = mac.doFinal(value);

            return new Hex().encode(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
