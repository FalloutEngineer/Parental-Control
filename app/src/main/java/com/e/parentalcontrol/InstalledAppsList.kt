package com.e.parentalcontrol

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class InstalledAppsList : AppCompatActivity(), OnAppListener {
    var appList:List<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_installed_apps_list)

        initView()
    }

    private fun initView() {
        var recyclerView:RecyclerView = findViewById(R.id.recycleView)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        appList = getApplications()
        var adapter: Adapter = Adapter(this, getApplications(), this)

        recyclerView.adapter = adapter
    }

    private fun getApplications(): List<Item> {
        var items:MutableList<Item> = ArrayList<Item>()
        var manager:PackageManager = packageManager

        var intent:Intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        var infos:List<ResolveInfo> = manager.queryIntentActivities(intent, 0)

        for(resolveInfo in infos) {
            var activityInfo: ActivityInfo = resolveInfo.activityInfo
            var isBlocked = false;
            if(activityInfo.packageName in BlockedList.list){
                isBlocked = true
            }
            items.add(Item(activityInfo.loadIcon(manager), activityInfo.loadLabel(manager).toString(), activityInfo.packageName, isBlocked))
        }
        return items
    }

    override fun onOptionsItemSelected(item:MenuItem): Boolean {
        var id = item.itemId

        if(id == android.R.id.home){
            finish()
        }

        return true
    }

    override fun onAppClick(position: Int) {
        var n = appList!!.get(position)?.name
        if(n in BlockedList.list){
            BlockedList.list.remove(n)
        }else{
            BlockedList.list.add(n)
        }
        initView()
        saveArray()
    }

    fun saveArray(): Boolean {
        val prefs = getSharedPreferences("blockedapps", 0)
        val editor = prefs.edit()
        editor.putInt("list" + "_size", BlockedList.list.size)
        for (i in BlockedList.list.indices) editor.putString("list" + "_" + i, BlockedList.list[i])
        return editor.commit()
    }

}