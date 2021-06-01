package com.e.parentalcontrol

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val password = loadPassword()

        if (password == null || password == ""){
            startMain()
        }

        loginBtn.setOnClickListener {
            if(passwordEdit.text.toString() == password){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "Wrong password!", Toast.LENGTH_SHORT)
            }
        }
    }

    fun startMain(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun loadPassword(): String?{
        val sharedPreferences = getSharedPreferences("Password", Context.MODE_PRIVATE)

        return sharedPreferences.getString("password", null)
    }
}