package com.e.parentalcontrol

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.e.parentalcontrol.Constants.NOTIFICATION_CHANNEL_ID
import com.e.parentalcontrol.Constants.NOTIFICATION_CHANNEL_NAME
import com.e.parentalcontrol.Constants.NOTIFICATION_ID
import java.util.*


class   ParentalControlService : Service() {

    val TAG = "ParentalControlService"

    var isFirstRun = true

    private lateinit var blockService: BlockService

    val thread = Thread{
        blockService = BlockService(applicationContext)

        var isLocked = false

        val handler = Handler(Looper.getMainLooper())

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                var topPackageName: String
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val mUsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                    val time = System.currentTimeMillis()
                    val stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time)
                    if (stats != null) {
                        val mySortedMap: SortedMap<Long, UsageStats> = TreeMap()
                        for (usageStats in stats) {
                            mySortedMap[usageStats.lastTimeUsed] = usageStats
                        }
                        if (!mySortedMap.isEmpty()) {
                            topPackageName = mySortedMap[mySortedMap.lastKey()]!!.packageName
                                if(topPackageName in BlockedList.list){
                                    if(!isLocked){
                                        handler.post(Runnable {
                                            if(canDrawOverlays){
                                                blockService.show()
                                            }
                                        })
                                        isLocked = true
                                    }
                                }else{
                                    if(blockService != null && isLocked){
                                        blockService.dismiss()
                                        isLocked = false
                                    }
                                }
                        }
                    }

                }

            }
        }, 0, 500)
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if(isFirstRun){
                startForegroundService()
                thread.start()
                isFirstRun = false
                loadArray()
            }
        }


        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Service is stopped")
    }

    private fun startForegroundService(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
            as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_child_care_24)
                .setContentTitle("Parental Control")
                .setContentText("Protecting ")

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    //
    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            FLAG_UPDATE_CURRENT
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    fun loadArray() {
        val prefs = getSharedPreferences("blockedapps", 0)
        val size = prefs.getInt("list" + "_size", 0)
        val array = MutableList<String>(size, init= {i:Int -> ""})
        for (i in 0 until size) array[i] = prefs.getString("list" + "_" + i, null).toString()
        BlockedList.list = array
    }

}