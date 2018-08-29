package com.ldxx.tv

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ldxx.tv.adapter.PackageInfoAdapter
import com.ldxx.tv.entity.PackageInfoBean
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_package_info.*
import android.widget.ProgressBar
import android.view.ViewGroup
import android.widget.Toast


class PackageInfoActivity : AppCompatActivity() {

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_info)

        snackbar = Snackbar.make(rv_app_list, "应用列表获取中...", Snackbar.LENGTH_INDEFINITE)
        val contentLay: ViewGroup = snackbar.view.findViewById<View>(android.support.design.R.id.snackbar_text).parent as ViewGroup
        val item = ProgressBar(this,null,android.R.attr.progressBarStyleInverse)

        contentLay.addView(item, 0)
        snackbar.show()

        val observable = Observable.create(ObservableOnSubscribe<MutableList<PackageInfoBean>> { emitter ->
            emitter.onNext(getInstalledApps(true))
            emitter.onComplete()
        })


        val disposableObserver = object : DisposableObserver<MutableList<PackageInfoBean>>() {

            override fun onComplete() {
                snackbar.dismiss()
            }


            override fun onNext(t: MutableList<PackageInfoBean>) {
                t.sortBy { it.appName?.toLowerCase() }
                setUpData(t)
            }


            override fun onError(e: Throwable) {
                snackbar.dismiss()
            }


        }
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        //mCompositeDisposable.add(disposableObserver);


    }

    private fun setUpData(t: MutableList<PackageInfoBean>) {
        val adapter = PackageInfoAdapter(t)
        adapter.setOnPackageItemClickListener(object :PackageInfoAdapter.OnPackageItemClickListener{
            override fun onItemClick(packageName: String?) {
                if (packageName!=null){
                    startActivity(Intent(this@PackageInfoActivity,PackageDetailActivity::class.java).apply {
                        putExtra("packageName",packageName)
                    })
                }else{
                    Toast.makeText(this@PackageInfoActivity,"package name 为空",Toast.LENGTH_SHORT).show()
                }
            }

        })
        rv_app_list.layoutManager = LinearLayoutManager(this)
        rv_app_list.adapter = adapter
    }

    private fun getInstalledApps(getSysPackages: Boolean): MutableList<PackageInfoBean> {
        val res: MutableList<PackageInfoBean> = mutableListOf()
        val packs = packageManager.getInstalledPackages(0)
        for (i in packs.indices) {
            val p = packs[i]
            if (!getSysPackages && p.versionName == null) {
                continue
            }
            val newInfo = PackageInfoBean()
            newInfo.appName = p.applicationInfo.loadLabel(packageManager).toString()
            newInfo.packageName = p.packageName
            newInfo.versionName = p.versionName
            newInfo.versionCode = p.versionCode
            newInfo.icon = p.applicationInfo.loadIcon(packageManager)
            res.add(newInfo)
        }
        return res
    }
}
