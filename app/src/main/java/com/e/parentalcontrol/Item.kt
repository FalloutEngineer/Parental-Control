package com.e.parentalcontrol

import android.graphics.drawable.Drawable

class Item (icon:Drawable, packageName: String, name: String, isBlocked:Boolean) {
    val icon: Drawable = icon
    private val packageName: String = packageName;
    val name: String = name;
    val isBlocked: Boolean = isBlocked;
}