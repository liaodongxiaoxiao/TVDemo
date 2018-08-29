package com.ldxx.tv

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.ldxx.tv.adapter.ActivitiesAdapter
import com.ldxx.tv.listener.OnRecyclerViewItemClickListener
import kotlinx.android.synthetic.main.activity_package_detail.*

class PackageDetailActivity : AppCompatActivity() {

    private val tag: String = "PackageDetailActivity"

    private lateinit var pName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_detail)

        if (intent.hasExtra("packageName")) {
            pName = intent.getStringExtra("packageName")

            tv_package_name.text = pName

            try {
                val pi = packageManager.getPackageInfo(pName, PackageManager.GET_ACTIVITIES)
                val ac = pi.activities

                ac?.let {
                    val msg = StringBuffer()
                    msg.append("package name:\n")
                            .append(pName)
                            .append("\n")
                            .append("all activities:")
                    val data: MutableList<String> = mutableListOf()

                    for (a in it) {

                        //Log.e(tag, "${a.name}")
                        msg.append("\n").append(a.name)
                        if (a.exported && a.name != null && !a.name.contains("$")) {
                            data.add(a.name)
                        }
                    }
                    //tv_all.text = msg.toString()
                    Log.e(tag, msg.toString())
                    //rv_activities.adapter = Aci

                    val adapter = ActivitiesAdapter(data)

                    //rv_activities.addItemDecoration(RecyclerView.ItemDecoration())
                    rv_activities.layoutManager = LinearLayoutManager(this)
                    rv_activities.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                    rv_activities.adapter = adapter
                    adapter.setOnRecyclerViewItemClickListener(object : OnRecyclerViewItemClickListener<String> {
                        override fun onItemClick(t: String) {
                            try {
                                val intent = Intent()
                                //this, Class.forName("com.android.settings.applications.StorageUse")
                                intent.component = ComponentName(pName, t)
                                startActivity(intent)
                            } catch (e: Exception) {
                                //Log.e(tag,e.toString(),e)
                                Toast.makeText(this@PackageDetailActivity, "无法打开：$t", Toast.LENGTH_SHORT).show()
                                //tv_exception.text = "异常：${e.message}"
                            }
                        }

                    })
                }

            } catch (e: PackageManager.NameNotFoundException) {
                Log.e(tag, "error:${e.message}", e)
            }
        }
    }
}
