package com.lnyswz.wuliu.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var btnLogin: Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener{
            Utils.toast(this, et_name.text)
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", et_name.text)
            intent.putExtra("password", et_password.text)
            startActivity(intent)
        }
    }

}
