package com.e.parentalcontrol

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test.*

class Test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        load()

        saveBtn.setOnClickListener {
            save()
        }
    }

    fun save(){
        val insertedText:String = editText.text.toString()
        textView2.text = insertedText

        val sharedPreferences = getSharedPreferences("shar", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.apply{
            putString("str",insertedText)
        }.apply()

        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
    }

    fun load(){
        val sharedPreferences = getSharedPreferences("shar", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("str", null)

        textView2.text = savedString
    }
}