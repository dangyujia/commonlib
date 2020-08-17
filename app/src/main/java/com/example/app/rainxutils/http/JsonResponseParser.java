package com.example.app.rainxutils.http;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * @ClassName JsonResponseParser
 * @Author DYJ
 * @Date 2020/6/30 15:03
 * @Version 1.0
 * @Description TODO
 */

public class JsonResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest uriRequest) throws Throwable {

    }

    @Override
    public Object parse(Type type, Class<?> aClass, String s) throws Throwable {
        return null;
    }

}
