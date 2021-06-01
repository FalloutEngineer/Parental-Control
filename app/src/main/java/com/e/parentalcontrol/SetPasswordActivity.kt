package com.e.parentalcontrol

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_set_password.*
import kotlinx.android.synthetic.main.activity_test.*

class SetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_password)

        savePassword.setOnClickListener {
            if(passwordEdit.text.toString().isNotEmpty()){
                save()
            }else{
                Toast.makeText(this, "Field is empty!", Toast.LENGTH_SHORT)
            }

        }
    }

    fun save(){
        val sharedPreferences = getSharedPreferences("Password", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        val password = passwordEdit.text.toString()

        edit.apply{
            putString("password", password)
        }.apply()
    }
}