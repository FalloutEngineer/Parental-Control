package com.e.parentalcontrol

import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class ViewHolder(itemView: View, onAppListener: OnAppListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    val appName : TextView = itemView.findViewById(R.id.App_name)
    val appIcon : ImageView = itemView.findViewById(R.id.App_icon)
    val lockApp: ImageView = itemView.findViewById(R.id.Lock_icon)

    var onAppListener: OnAppListener? = null;

    init{
        itemView.setOnClickListener(this);
        this.onAppListener = onAppListener;
    }

    override fun onClick(v: View?) {
        onAppListener?.onAppClick(adapterPosition)
    }
}