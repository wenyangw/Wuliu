package com.lnyswz.wuliu.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.SqlUtils
import com.lnyswz.wuliu.common.Utils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var btnLogin: Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener{
            var msg = check()
            if(msg.isEmpty()) {
                login()
                val getData = GetData1()
                getData.execute("http://192.168.0.8/lnyswz/admin/departmentAction!listYws.action", "id=12")
//            Utils.toast(this, et_name.text)
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

    }

    internal inner class GetData1 : SqlUtils() {
        //        GetData1(Context ctx){
        //            super(ctx);
        //        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            //show.setText(s)
        }
//
//        override fun doInBackground(vararg params: String): String {
//            return super.doInBackground(params)
//        }
    }

}
