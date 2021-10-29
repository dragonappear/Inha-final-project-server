package com.dragonappear.inha.api.service.buying.iamport;

import com.dragonappear.inha.api.service.buying.iamport.dto.CancelDto;
import com.dragonappear.inha.exception.buying.IamportException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IamportService {
    public static final String IMPORT_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    public static final String IMPORT_CANCEL_URL = "https://api.iamport.kr/payments/cancel";
    public static final String KEY = "5132626560989602";
    public static final String SECRET = "76b263a820b4d8645755eb3f51f7afb500027545cbe027a24bdf8751e53ba34a9190b36a12508e46";

    // 아임포트에서 인증(토큰)을 받아오는 메서드
    public static String getImportToken()  {
        String result = "";
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_TOKEN_URL);
        Map<String,String> m =new HashMap<String,String>();
        m.put("imp_key", KEY);
        m.put("imp_secret", SECRET);
        try {
            post.setEntity(new UrlEncodedFormEntity(convertParameter(m)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String body = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(body);
            JsonNode resNode = rootNode.get("response");
            result = resNode.get("access_token").asText();
        } catch (Exception e){
            throw new IamportException("아임포트 토큰을 받아올 수 없습니다.");
        } return result;
    }

    // 아임포트로 취소요청 메서드
    public static void cancelPayment(CancelDto dto)  {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(IMPORT_CANCEL_URL);
        Map<String, String> map = new HashMap<String, String>();
        post.setHeader("Authorization", dto.getToken());
        map.put("imp_uid", dto.getImpId());
        map.put("merchant_uid", dto.getMerchantId());
        map.put("amount", dto.getAmount());
        map.put("checksum", dto.getChecksum());
        String asd = "";
        try { post.setEntity(new UrlEncodedFormEntity(convertParameter(map)));
            HttpResponse res = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            String enty = EntityUtils.toString(res.getEntity());
            JsonNode rootNode = mapper.readTree(enty);
            asd = rootNode.get("response").asText();
        } catch (Exception e) {
            throw new IamportException(e.getMessage());
        }
        if (asd.equals("null")) {
            throw new IamportException("환불실패");
        }
    }

    //  Http요청 파라미터를 만들어 주는 메서드
    public static List<NameValuePair> convertParameter(Map<String,String> paramMap){
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Set<Map.Entry<String,String>> entries = paramMap.entrySet();
        for(Map.Entry<String,String> entry : entries) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        } return paramList;
    }

}
