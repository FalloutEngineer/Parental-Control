package com.e.parentalcontrol


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class Adapter constructor(context: Context, apps: List<Item>, onAppListener: OnAppListener) : RecyclerView.Adapter<ViewHolder>(){
    private val context: Context = context
    private val apps: List<Item> = apps
    private val onAppListener: OnAppListener = onAppListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.app_item, parent, false)

        return ViewHolder(view, onAppListener)
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.appName.setText(apps.get(position).name)
        if(apps.get(position).isBlocked){
            holder.lockApp.setImageResource(R.drawable.ic_baseline_lock_24)
        }
        holder.appIcon.setImageDrawable(apps.get(position).icon)
    }
}