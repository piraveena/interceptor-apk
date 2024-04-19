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
        System.out.println("Request body as JSON: " + jsonObject.toString());

        System.out.println("request path");
        System.out.println(req.getContextPath().toString());

//        {cache-control=no-cache, authorization=Bearer 58516aba-a07b-3000-9b27-0f47d0880bbc, postman-token=4f6572aa-50a0-4299-90ac-2b0a0ab6975a, accept-encoding=gzip, deflate, br, :authority=default.gw.wso2.com:9095, x-wso2-cluster-header=default__default.gw.wso2.com_SCIM2.0EndpointSwaggerDefinition1.0.0_cbe4677a-1085-49b8-9f83-abdf87f53ca70, accept=*/*, x-request-id=006ae16f-5570-48d1-9602-779be9386125, x-forwarded-proto=https, user-agent=PostmanRuntime/7.37.3, :scheme=https, :method=GET, :path=/U0NJTSAyLjAgRW5kcG9pbnQgU3dhZ2dlciBEZWZpbml0aW9uMS4wLjA/1.0.0/t/carbon.super/scim2/Users}
//
        Object requestHeader = jsonObject.get("requestHeaders");
//                Map<String, Object> reqBody = new HashMap<>(); // replace with actual request body
//                System.out.println("printing data:===" + reqBody);
//
//                req
//                String url = (String) reqBody.get("requestHeaders").);
//                System.out.println("printing url:===" + url);

//                String[] free_tier = {"carbon.super", "foo.com"};
//                String[] enterprise_tier = {"abc.com", "xyz.com"};
//                String[] professional_tier = {"bar.com"};
//
//                String[] config_api = {"/api/server/v1/applications", "/api"};
//                String[] run_time_low_traffic = {"/scim2/Users"};
//                String[] run_time_high_traffic = {"/oauth2/token"};
//
//                String tenantDomain = null;
//                String apiPath = null;
//
//                Pattern pattern = Pattern.compile("/t/([^/]*)(/[a-zA-Z0-9/]*)");
//                Matcher matcher = pattern.matcher(url);
//                if (matcher.find()) {
//                    System.out.println(matcher.group(1));
//                    System.out.println(matcher.group(2));
//                    tenantDomain = matcher.group(1);
//                    apiPath = matcher.group(2);
//                }
//
//                String key = tenantDomain + ":" + apiPath; // foo.com:/scim2/Users
//                System.out.println("key: " + key);
//
//                Map<String, String> res = new HashMap<>(); // replace with actual response object
//
//                if (Arrays.asList(free_tier).contains(tenantDomain)) {
//                    System.out.println("hittt free_tier ==: ");
//                    if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
//                        System.out.println("hittt run_time_low_traffic ==: ");
//                        res.put("free_tier_run_time_low_traffic", key);
//                    } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
//                        res.put("free_tier_run_time_high_traffic", key);
//                    } else if (Arrays.asList(config_api).contains(apiPath)) {
//                        res.put("free_tier_config_traffic", key);
//                    }
//                } else if (Arrays.asList(professional_tier).contains(tenantDomain)) {
//                    if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
//                        res.put("professional_tier_run_time_low_traffic", key);
//                    } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
//                        res.put("professional_tier_run_time_high_traffic", key);
//                    } else if (Arrays.asList(config_api).contains(apiPath)) {
//                        res.put("professional_tier_config", key);
//                    }
//                } else if (Arrays.asList(enterprise_tier).contains(tenantDomain)) {
//                    if (Arrays.asList(run_time_low_traffic).contains(apiPath)) {
//                        res.put("enterprise_tier_run_time_low_traffic", key);
//                    } else if (Arrays.asList(run_time_high_traffic).contains(apiPath)) {
//                        res.put("enterprise_tier_run_time_high_traffic", key);
//                    } else if (Arrays.asList(config_api).contains(apiPath)) {
//                        res.put("enterprise_tier_config", key);
//                    }
//                } else {
//                    res.put("rateLimitKeys", "");
//                }
//            }
        // Handle the request here
        // For now, just return a simple message
        System.out.println("Request received: " + req.getPathInfo());
        return ResponseEntity.ok("Success");
    }
}