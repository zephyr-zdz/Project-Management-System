package com.example.projectmanagementsystem.service.user;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service("UserPushService")
public class PushService {
    public void push(String sendKey, String title, String content) {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, String> params = new HashMap<>();
        params.put("pushkey", sendKey);
        params.put("text", title);
        params.put("desp", content);
        ResponseEntity<Map> response;        //  执行HTTP请求
        response = client.exchange(
                "https://api2.pushdeer.com/message/push?pushkey={pushkey}&text={text}&desp={desp}",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                Map.class,
                params);
        System.out.println(response);
    }
}
