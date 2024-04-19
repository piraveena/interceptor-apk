package com.example.interceptorapk;

import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class RequestHandlerService {

    @PostMapping("/api/v1/handle-request")
    public ResponseEntity<?> handleRequest(HttpServletRequest req) throws IOException {

        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        System.out.println("Request body: " + requestBody.toString());

        JSONObject jsonObject = (JSONObject) JSONValue.parse(requestBody.toString());

        System.out.println("request path:"+ req.getContextPath().toString());

        JSONObject requestHeaders = (JSONObject)jsonObject.get("requestHeaders");
        String path = (String) requestHeaders.get(":path");

        String[] free_tier = {"carbon.super", "foo.com"};
        String[] enterprise_tier = {"abc.com", "xyz.com"};
        String[] professional_tier = {"bar.com"};

        String[] config_api = {"/api/server/v1/applications", "/api"};
        String[] run_time_low_traffic = {"/scim2/Users"};
        String[] run_time_high_traffic = {"/oauth2/token"};

        String tenantDomain = null;
        String apiPath = null;

        Pattern pattern = Pattern.compile("/t/([^/]*)(/[a-zA-Z0-9/]*)");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            tenantDomain = matcher.group(1);
            apiPath = matcher.group(2);
        }

        String key = tenantDomain + ":" + apiPath; // foo.com:/scim2/Users
        System.out.println("key: " + key);

        Map<String, String> res = new HashMap<>();
        JSONObject rateLimitKeys = new JSONObject();

        if (Arrays.asList(free_tier).contains(tenantDomain)) {
            if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
                res.put("free_tier_run_time_low_traffic", key);
            } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
                res.put("free_tier_run_time_high_traffic", key);
            } else if (Arrays.asList(config_api).contains(apiPath)) {
                res.put("free_tier_config_traffic", key);
            } else {
                res.put("rateLimitKeys", "");
            }
        } else if (Arrays.asList(professional_tier).contains(tenantDomain)) {
            if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
                res.put("professional_tier_run_time_low_traffic", key);
            } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
                res.put("professional_tier_run_time_high_traffic", key);
            } else if (Arrays.asList(config_api).contains(apiPath)) {
                res.put("professional_tier_config", key);
            }   else {
                res.put("rateLimitKeys", "");
            }
        } else if (Arrays.asList(enterprise_tier).contains(tenantDomain)) {
            if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
                res.put("enterprise_tier_run_time_low_traffic", key);
            } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
                res.put("enterprise_tier_run_time_high_traffic", key);
            } else if (Arrays.asList(config_api).contains(apiPath)) {
                res.put("enterprise_tier_config", key);
            } else {
                res.put("rateLimitKeys", "");
            }
        } else {
            res.put("rateLimitKeys", "");
        }

        rateLimitKeys.put("rateLimitKeys", res);
        System.out.println("rateLimitKeys: " + rateLimitKeys.toString());

        return ResponseEntity.ok(rateLimitKeys);
    }
}
