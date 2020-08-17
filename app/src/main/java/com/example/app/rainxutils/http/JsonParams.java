package com.example.app.rainxutils.http;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JsonParams
 * @Author DYJ
 * @Date 2020/6/30 14:44
 * @Version 1.0
 * @Description TODO
 */
@HttpRequest(
        host = JsonParamsBuilder.SERVER_A,
        path = "query/test",
        builder = JsonParamsBuilder.class
)
public class JsonParams extends RequestParams {

    public String paramStr;

    public int paramInt;

    public List<String> paramList;

    // 可以在这里定义pb属性，在ParamsBuilder中将pb数据设置为请求内容.
    // private CLASS_PB pbFiled;


    // 发送请求的示例
    // 参数被JsonDemoParamsBuilder重新加工成json的形式发送.
    // 示例项目的混淆配置会使这个类的字段不被混淆, 字段名作为参数名.
    public static Callback.Cancelable send(Callback.CommonCallback<JsonResponse> callback) {
        JsonParams params = new JsonParams();
        params.paramStr = "test";
        params.paramInt = 10;
        params.paramList = new ArrayList<String>();
        params.paramList.add("test");
        return x.http().post(params, callback);
    }
}
