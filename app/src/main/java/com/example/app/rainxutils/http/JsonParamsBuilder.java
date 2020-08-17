package com.example.app.rainxutils.http;

import android.text.TextUtils;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.http.app.ParamsBuilder;
import org.xutils.x;

import java.util.HashMap;

import javax.net.ssl.SSLSocketFactory;

/**
 * @ClassName JsonParamsBuilder
 * @Author DYJ
 * @Date 2020/6/30 14:46
 * @Version 1.0
 * @Description TODO
 */

public class JsonParamsBuilder implements ParamsBuilder {

    static final String SERVER_A = "a";
    static final String SERVER_B = "b";

    private static final HashMap<String, String> SERVER_MAP = new HashMap<String, String>();
    private static final HashMap<String, String> DEBUG_SERVER_MAP = new HashMap<String, String>();

    static {
        SERVER_MAP.put(SERVER_A, "https://www.baidu.com");
        SERVER_MAP.put(SERVER_B, "https://www.baidu.com");
        DEBUG_SERVER_MAP.put(SERVER_A, "https://www.baidu.com");
        DEBUG_SERVER_MAP.put(SERVER_B, "https://www.baidu.com");
    }

    @Override
    public String buildUri(RequestParams requestParams, HttpRequest httpRequest) {
        return getHost(httpRequest.host());
    }

    @Override
    public String buildCacheKey(RequestParams requestParams, String[] strings) {
        return null;
    }

    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return null;
    }

    @Override
    public void buildParams(RequestParams params) {
        // 添加额外公共参数
        params.addParameter("common_a", "xxxx");
        params.addParameter("common_b", "xxxx");
        // 将post请求的body参数以json形式提交
        // params.setAsJsonContent(true);

        // 或者query参数和body参数都json形式
        String json = params.toJSONString();
        params.clearParams();// 清空参数
        if (params.getMethod() == HttpMethod.GET) {
            params.addQueryStringParameter("wd", json);
        } else {
            params.setBodyContent(json);
        }

        // 也可以将参数对象转为pb
        //byte[] pbData = convertPbData(pbFiled);
        //params.setMultipart(false);
        // 非multipart表单，key被忽略，只上传pbData
        //params.addBodyParameter("data", pbData, "application/octet-stream");
    }

    @Override
    public void buildSign(RequestParams requestParams, String[] strings) {
        // params.addHeader("xxx_sign", "xxxx");
    }


    private String getHost(String host) {
        String result = null;
        if (x.isDebug()) {
            result = DEBUG_SERVER_MAP.get(host);
        } else {
            result = SERVER_MAP.get(host);
        }
        return TextUtils.isEmpty(result) ? host : result;
    }
}
