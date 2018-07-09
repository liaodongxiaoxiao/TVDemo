package com.ldxx.tv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ldxx.tv.R
import com.ldxx.tv.entity.PackageInfoBean

class PackageInfoAdapter(val data: MutableList<PackageInfoBean>) : RecyclerView.Adapter<PackageInfoAdapter.PackageInfoViewHolder>() {

    //private val data: MutableList<PackageInfoBean> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageInfoViewHolder {
        return PackageInfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_package_info, parent, false))
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: PackageInfoViewHolder, position: Int) {
        val entity = data[position]
        holder.apply {
            ivIcon.setImageDrawable(entity.icon)
            tvName.text = entity.appName
            tvPackageName.text = entity.packageName
            tvVersion.text = entity.versionName
        }
    }

    inner class PackageInfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvPackageName: TextView = view.findViewById(R.id.tv_package_name)
        val tvVersion: TextView = view.findViewById(R.id.tv_version)
        val ivIcon: ImageView = view.findViewById(R.id.iv_icon)
    }
}