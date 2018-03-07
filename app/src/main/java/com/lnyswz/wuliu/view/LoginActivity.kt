package com.lnyswz.wuliu.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.SqlUtils
import com.lnyswz.wuliu.common.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private var result: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener{
            var msg = check()
            if(msg.isEmpty()) {
                login()
                var intent = Intent(this, MainActivity::class.java)
                intent.putExtra("name", et_name.text.toString())
                intent.putExtra("password", et_password.text.toString())
                startActivity(intent)
            }else{
                Utils.toast(this, msg)
            }
        }
    }

    fun check(): String{
        var message = StringBuilder()
        if(et_name.text.isNullOrEmpty()) {
            message.append("用户名不能为空！")
        }
        if(et_password.text.isNullOrEmpty()) {
            if(message.length > 0){
                message.append("\n")
            }
            message.append("密码不能为空！")
        }

        return message.toString()
    }

    fun login(){
        Log.i("LoginActivity", "fun login()")
        val getData = GetData()
        getData.execute("http://192.168.0.8/lnyswz/admin/departmentAction!listYws.action", "id=12")
    }

    internal inner class GetData : SqlUtils() {
        override fun onPostExecute(s: String) {
            Log.i("LoginActivity", "fun onPostExecute")
            super.onPostExecute(s)
            Log.i("LoginActivity", s)
        }
    }

}
