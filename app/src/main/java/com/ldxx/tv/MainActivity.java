package com.ldxx.tv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView tvOem;
    private TextView tvVersion;
    private TextView tvModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });
        tvOem = (TextView) findViewById(R.id.tv_oem);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvModel = (TextView) findViewById(R.id.tv_model);

        tvVersion.setText(Build.VERSION.RELEASE);
        tvOem.setText(Build.BRAND);
        tvModel.setText(android.os.Build.MODEL);
    }
}
