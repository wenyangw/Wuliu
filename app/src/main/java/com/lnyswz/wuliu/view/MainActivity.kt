package com.lnyswz.wuliu.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.Utils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("intent:", intent.toString())
        Utils.toast(this, intent.getStringExtra("name"))
        tv_info.text = intent.getStringExtra("name")
    }
}
