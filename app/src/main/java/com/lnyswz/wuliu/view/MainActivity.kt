package com.lnyswz.wuliu.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMain()
    }

    private fun showMain() {
        val address = Utils.APP_URL + "/admin/menuAction!menuTree.action"
        val params = mapOf("userId=" to intent.getStringExtra("userId"),
                "userName=" to intent.getStringExtra("userName"),
                "cid=" to Utils.CATALOG_ID)
        SqlData4Menus(address, params).execute()
    }

    private fun getButtons(menuId: String) {
        val address = Utils.APP_URL + "/admin/buttonAction!buttons.action"
        val params = mapOf("userId=" to intent.getStringExtra("userId"),
                "userName=" to intent.getStringExtra("userName"),
                "mid=" to menuId,
                "tabId=" to "0",
                "did=" to intent.getStringExtra("did"))
        SqlData4Buttons(address, params).execute()
    }


    internal inner class SqlData4Menus(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val menus = Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)

            for (menu in menus) if(menu.pid != null) {
                Log.i("MainActivity", menu.id)
                getButtons(menu.id)
            }
        }
    }

    internal inner class SqlData4Buttons(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            Log.i("TAG", s)

            val buttons = Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)

            for (button in buttons) {
                Log.i("MainActivity", button.orderNum)
                Log.i("MainActivity", button.text)
                Log.i("MainActivity", button.handler)
            }
        }
    }



}