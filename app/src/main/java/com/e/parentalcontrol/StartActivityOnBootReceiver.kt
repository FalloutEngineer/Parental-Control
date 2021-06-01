package com.e.parentalcontrol

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class StartActivityOnBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("BootCompletedReceiver", "starting service...")
        val intent = Intent(context, ParentalControlService::class.java)
        context!!.startService(intent)
    }
}

