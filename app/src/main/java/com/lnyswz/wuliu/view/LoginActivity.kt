package com.lnyswz.wuliu.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity() {

    private var context: Context?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_login)
        et_password.setInputType(InputType.TYPE_NULL) //密码始终不弹出软件键盘
        readAccount()//填写登录过的用户名

        et_password.onFocusChangeListener = onFocusChange()  //对密码获取焦点后隐藏键盘进行处理
        btn_login.setOnClickListener {
            login()
            ActivityManager.getInstance().addActivity(this)
        }
    }

    fun readAccount() {
        //创建SharedPreferences对象
        val sp = getSharedPreferences("info", Context.MODE_PRIVATE)
        //获得保存在SharedPredPreferences中的用户名和密码
        val username = sp.getString("username", "")
        //在用户名和密码的输入框中显示用户名和密码
        et_name.setText(username)
        et_password.requestFocus()//密码获取焦点
    }

    private fun login() {
        var msg = checkInput()
        if (msg.isEmpty()) {
            checkUser()
        } else {
            Utils.toast(this, msg)
        }
    }

    private fun checkInput(): String {
        var message = StringBuilder()
        if (et_name.text.isNullOrEmpty()) {
            message.append("用户名不能为空！")
        }
        if (et_password.text.isNullOrEmpty()) {
            if (message.length > 0) {
                message.append("\n")
            }
            message.append("密码不能为空！")
        }
        return message.toString()
    }

    private fun checkUser() {
        val address = Utils.APP_URL + "/admin/loginMAction!login.action"
        val params = mapOf("userName=" to et_name.text.toString(),
                "password=" to et_password.text.toString(),
                "source=" to "android")
        SqlData(address, params).execute()
    }

    private fun enterMain(json: JsonBean) {
        var intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userId", json.obj.id)
        intent.putExtra("userName", json.obj.userName)
        intent.putExtra("did", json.obj.did)
        startActivity(intent)
    }

    internal inner class SqlData(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val json = Utils.getObjectFromJson(s, JsonBean::class.java)
            if (json.success) {
                saveUserName()//保存用户名
                enterMain(json)
            }
            Utils.toast(applicationContext, json.msg)
        }
    }

    fun saveUserName() {
        //创建sharedPreference对象，info表示文件名，MODE_PRIVATE表示访问权限为私有的
        val sp = getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        //获得sp的编辑器
        val ed = sp.edit()
        //以键值对的显示将用户名和密码保存到sp中
        ed.putString("username", et_name.text.toString())
        //提交用户名和密码
        ed.commit()
    }

    inner class onFocusChange :View.OnFocusChangeListener{
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            Log.i("dd","ddddddd+${hasFocus}")
            if (hasFocus) {
                //隐藏软键盘
                val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(et_password.getWindowToken(), 0)

            }
        }
    }


}