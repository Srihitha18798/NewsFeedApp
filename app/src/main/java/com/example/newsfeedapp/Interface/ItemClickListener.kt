package com.example.newsfeedapp.Interface

import android.view.View


interface ItemClickListener {
    fun onClick(view: View, position: Int)
}