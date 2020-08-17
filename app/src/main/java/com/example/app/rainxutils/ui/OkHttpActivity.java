package com.example.app.rainxutils.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.app.rainxutils.R;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("")
                .build();
        Call response = client.newCall(request);
        TypeToken token = new  TypeToken<String>(){};

    }
}
