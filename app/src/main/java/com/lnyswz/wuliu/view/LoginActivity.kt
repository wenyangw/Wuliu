package com.lnyswz.wuliu.view

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import kotlinx.android.synthetic.main.activity_login.*
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class LoginActivity : AppCompatActivity() {

    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this

        //et_password.setInputType(InputType.TYPE_NULL) //密码始终不弹出软件键盘
        putUserName()//填写登录过的用户名
        et_password.onFocusChangeListener = onFocusChange()  //对密码获取焦点后隐藏键盘进行处理
        btn_login.setOnClickListener {
            login()
            ActivityManager.getInstance().addActivity(this)
        }
    }

    private fun putUserName() {
        et_name.setText(SPUtils.get(context, "username", et_name.text.toString()).toString())
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
        val address = Utils.sqlUrl(SPUtils.get(context, "serverUrl", "").toString(),"/admin/loginMAction!login.action")
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
                //保存用户名
                SPUtils.put(context, "username", et_name.text.toString())
                enterMain(json)
            }
            Utils.toast(applicationContext, json.msg)
        }
    }

    inner class onFocusChange :View.OnFocusChangeListener{
        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            if (hasFocus) {
                //隐藏软键盘
                val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(et_password.windowToken, 0)
            }
        }
    }

    private fun setServerUrl() {
        val edUrl = EditText(this)
        edUrl.setText(SPUtils.get(context, "serverUrl", edUrl.text.toString()).toString())
        edUrl.minLines = 1
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.server_url_hint))
            .setIcon(android.R.drawable.ic_dialog_info)
            .setView(edUrl)
            .setPositiveButton(getString(R.string.btn_ok)) { arg0, arg1 ->
                SPUtils.put(context, "serverUrl", edUrl.text.toString()).toString()
            }
            .setNegativeButton(getString(R.string.btn_cancel), null)
            .show()
    }

    //标题菜单-更新
    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menu.add(Menu.NONE, R.id.login_set, 0, getString(R.string.server_url_menu))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.login_set//监听菜单按钮
            -> {
                setServerUrl()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}