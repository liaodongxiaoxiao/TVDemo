package com.ldxx.tv

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        button.setOnClickListener {
            try {
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivity(intent)
            } catch (e: Exception) {
                tv_exception.text = "异常：${e.message}"
            }

        }

        btn_tts.setOnClickListener {
            try {
                val intent = Intent("com.android.settings.TTS_SETTINGS")
                startActivity(intent)
            } catch (e: Exception) {
                tv_exception.text = "异常：${e.message}"
            }

        }

        /*btn_set.setOnClickListener {
            try {
                val intent = Intent()
                //this, Class.forName("com.android.settings.applications.StorageUse")
                intent.component = ComponentName("com.google.android.apps.maps","com.google.android.maps.PlacesActivity")
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(tag,e.toString(),e)
                tv_exception.text = "异常：${e.message}"
            }
        }*/

        btn_app_list.setOnClickListener {
            startActivity(Intent(this, PackageInfoActivity::class.java))
        }

        btn_test.setOnClickListener {
            tv_exception.text = ""
            val intent = Intent().apply {
                action = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA
                //action = TextToSpeech.Engine.ACTION_GET_SAMPLE_TEXT
                //putExtra(TextToSpeech.Engine.EXTRA_SAMPLE_TEXT,"这是一个测试用例！哈哈哈！")
            }

            //startActivity(intent)
            startActivityForResult(intent, 1001)
        }



        tv_version.text = Build.VERSION.RELEASE
        tv_oem.text = Build.BRAND
        tv_model.text = android.os.Build.MODEL
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {

        }
        when (resultCode) {

            TextToSpeech.Engine.CHECK_VOICE_DATA_PASS -> tv_exception.text = "$resultCode :语音引擎校验通过"
            TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL -> tv_exception.text = "$resultCode :检验失败"
        //TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME -> tv_exception.text = "$resultCode :缺少发音文件"

        }

        data?.let {
            val sb = StringBuilder()
            val bundle = it.extras
            val keys = bundle.keySet()
            for (key in keys) {
                sb.append("\n")
                sb.append("$key : ${bundle.get(key)}")
                Log.e(tag, "$key : ${bundle.get(key)}")
            }

            tv_exception.append(sb.toString())
        }
    }
}
