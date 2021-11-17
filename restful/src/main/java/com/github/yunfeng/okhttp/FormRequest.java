package com.github.yunfeng.okhttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FormRequest {
    public static void main(String[] args) {
        String url = "https://www.baidu.com/";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
            .add("key1", "value1")
            .add("key2", "value2")
            .build();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("key3", "value3");
        Headers headers = Headers.of(headerMap);

        Request request = new Request.Builder().url(url).headers(headers).post(body).build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
