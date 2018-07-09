package com.ldxx.tv

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        button.setOnClickListener {
            val intent = Intent(Settings.ACTION_SETTINGS)
            startActivity(intent)
        }

        btn_app_list.setOnClickListener {
            startActivity(Intent(this, PackageInfoActivity::class.java))
        }

        tv_version.text = Build.VERSION.RELEASE
        tv_oem.text = Build.BRAND
        tv_model.text = android.os.Build.MODEL
    }
}
