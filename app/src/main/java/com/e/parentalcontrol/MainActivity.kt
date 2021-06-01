package com.e.parentalcontrol

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, ParentalControlService::class.java).also {
            startService(it)
        }

        installedAppsBtn.setOnClickListener {
            startActivity(Intent(this, InstalledAppsList::class.java))
        }

        setPasswordBtn.setOnClickListener {
            startActivity(Intent(this, SetPasswordActivity::class.java))
        }

        permissionsBtn.setOnClickListener {
            startActivity(Intent(this, PermissionsActivity::class.java))
        }

        stopBtn.setOnClickListener {
            applicationContext.stopService(Intent(this, ParentalControlService::class.java))
        }
    }
}