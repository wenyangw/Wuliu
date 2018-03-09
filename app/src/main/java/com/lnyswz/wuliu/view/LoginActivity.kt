package com.lnyswz.wuliu.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Reader
import java.lang.reflect.Type

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btn_login.setOnClickListener{
            login()
        }
    }

    private fun login(){
        var msg = checkInput()
        if(msg.isEmpty()) {
            checkUser()
        }else{
            Utils.toast(this, msg)
        }
    }

    private fun checkInput(): String{
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

    private fun checkUser(){
        val address = Utils.APP_URL + "/admin/loginMAction!login.action"
        val params = mapOf("userName=" to et_name.text.toString(), "password=" to et_password.text.toString(), "source=" to "android")
        SqlData(address, params).execute()
    }

    private fun enterMain(json: JsonBean){
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userName", json.obj.userName)
        intent.putExtra("userId", json.obj.id)
        startActivity(intent)
    }

    internal inner class SqlData(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)

            //val json = Gson().fromJson(s, JsonBean::class.java)
            val json: JsonBean = Gson().fromJson(s)
            if(json.success){
                enterMain(json)
            }
            Utils.toast(applicationContext, json.msg)
        }
    }

    // 在 kotlin中扩展 Java 类的 Gson.fromJson 方法
    // 不在传入 T 的class，inline 的作用就是将函数插入到被调用处，配合 reified 就可以获取到 T 真正的类型
    inline fun <reified T : Any> Gson.fromJson(json: String): T{
        return fromJson(json, T::class.java)
    }

}




