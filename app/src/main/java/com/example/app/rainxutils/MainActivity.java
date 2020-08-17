package com.example.app.rainxutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.app.rainxutils.observer.Hero;
import com.example.app.rainxutils.observer.model.Monster;
import com.example.app.rainxutils.observer.model.Trap;
import com.example.app.rainxutils.observer.model.Treasure;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private IWXAPI msgApi;

    @ViewInject(R.id.content)
    private TextView content;
    @ViewInject(R.id.orderId)
    private EditText orderId;
    @ViewInject(R.id.money)
    private EditText moneyEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        msgApi = WXAPIFactory.createWXAPI(this, null);
        msgApi.registerApp(Constants.APPID);
        String orderID = "202006290956490031";
        String money = "11";
        orderId.setText(orderID);
        orderId.setSelection(orderID.length());
        moneyEdit.setText(money);
        moneyEdit.setSelection(money.length());
        content.setMovementMethod(ScrollingMovementMethod.getInstance());

        Hero hero = new Hero();
        Monster monster = new Monster();
        Trap trap = new Trap();
        Treasure treasure = new Treasure();

        hero.attachObserver(monster);
        hero.attachObserver(trap);
        hero.attachObserver(treasure);
        hero.move();

    }

    @Event(value = {R.id.wxPay, R.id.aliPay})
    private void onClick(View v) {
        Map<String, Object> map = new HashMap<>();
        switch (v.getId()) {
            case R.id.wxPay:
                map.clear();
                map.put("cmd", "getVersion");
                pay(map, 1);
                break;
            case R.id.aliPay:
                map.clear();
                map.put("cmd", "getVersion");
                pay(map, 2);
                break;
        }
    }

    private void pay(Map<String, Object> map, final int type) {

//        RequestParams params = new RequestParams("http://192.168.0.106:9001/helpsalesll/alipay/appPayOrder");
        RequestParams params = new RequestParams("https://sdzxiangmu.com/alipay/appPayOrder");
        params.addQueryStringParameter("orderNo", orderId.getText().toString());
        params.addQueryStringParameter("money", moneyEdit.getText().toString());
//        params.addQueryStringParameter("goodsName", "w9");
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String s) {
                content.setText(s);
                if (type == 1) {
                    wx(s);
                } else {
                    aliPay(s);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String s) {
                return false;
            }
        });

    }

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == SDK_PAY_FLAG) {//对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    showAlert("支付成功" + payResult);
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    showAlert("支付失败" + payResult);
                }
            }
        }
    };

    private void aliPay(String payInfo) {
        final AliPayBean aliPayBean = gson.fromJson(payInfo, AliPayBean.class);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask aliPay = new PayTask(MainActivity.this);
                Map<String, String> result = aliPay.payV2(aliPayBean.message, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private void wx(String payInfo) {
        PayBean payBean = gson.fromJson(payInfo, PayBean.class);
//        BaseBean payBean = gson.fromJson(payInfo, BaseBean.class);
        PayReq request = new PayReq();
        request.appId = Constants.APPID;
        request.partnerId = Constants.PARTNERID;
        request.packageValue = Constants.PACKAGEVALUE;
        request.prepayId = payBean.prepayid;
        request.nonceStr = payBean.noncestr;
        request.timeStamp = payBean.timestamp;
        request.sign = payBean.sign;

//        request.prepayId = payBean.data.prepayid;
//        request.nonceStr = payBean.data.noncestr;
//        request.timeStamp = payBean.data.timestamp;
//        request.sign = payBean.data.sign;

        msgApi.sendReq(request);
    }

    private void showAlert(String info) {
        Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
    }


}
