package com.ldxx.tv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ldxx.tv.R
import com.ldxx.tv.listener.OnRecyclerViewItemClickListener

class ActivitiesAdapter(val data: MutableList<String>):RecyclerView.Adapter<ActivitiesAdapter.ActivityViewHolder>() {

    private var listener: OnRecyclerViewItemClickListener<String>?=null

    fun setOnRecyclerViewItemClickListener(ls:OnRecyclerViewItemClickListener<String>){
        listener = ls
    }


    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        return ActivityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_activities,parent,false))
    }

    /**
     *
     */
    override fun getItemCount(): Int = data.size

    /**
     *
     */
    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        holder.tvName.text = data[position]
        holder.itemView.setOnClickListener { listener?.onItemClick(data[position]) }
    }



    inner class ActivityViewHolder(viewItem:View):RecyclerView.ViewHolder(viewItem){
        val tvName:TextView = viewItem.findViewById(R.id.tv_name)
    }
}