package com.lnyswz.wuliu.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ButtonBean
import com.lnyswz.wuliu.common.SqlUtils
import com.lnyswz.wuliu.common.UtilBean
import com.lnyswz.wuliu.common.Utils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMain()
    }

    private fun showMain(){
        val address = Utils.APP_URL + "/admin/menuAction!menuTreeM.action"
        val params = mapOf("userId=" to intent.getStringExtra("userId"), "userName=" to intent.getStringExtra("userName"), "cid=" to Utils.CATALOG_ID)
        SqlData(address, params).execute()
    }

    private fun getButtons(){
        val address = Utils.APP_URL + "/admin/buttonAction!buttons.action"
        val params = mapOf("userId=" to intent.getStringExtra("userId"),
                "menuId=" to intent.getStringExtra("userName"),
                "tabId=" to Utils.CATALOG_ID,
                "did=" to "14")
        SqlData(address, params).execute()
    }



    internal inner class SqlData(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)

            Log.i("MainActivity", s)

            //getMenu(s)


            //val type = object : TypeToken<List<ButtonBean>>(){}.type
            val strings = Gson().fromJson(s, Array<String>::class.java)

            for (string in strings) {
                Log.i("MainActivity", string)
            }

            //val login = Gson().fromJson(s, UtilBean::class.java)
        }
    }

    /**
     * 将“{，，}，{，，}”这种格式的数据转换成字符串数组
     * 目前传入的数据使用[]包围
     */
    private fun getButtons(str: String): Array<ButtonBean>?{
        return null
    }

    private fun getMenu(str: String): Array<ButtonBean>?{
        Log.i("MainActivity", tt(str))

        return null
    }

    private fun tt(str: String): String{
        var s = str.substring(1, str.length - 1)
        val login = Gson().fromJson(s, ButtonBean::class.java)

        Log.i("MainActivity", login.text)

        return s
    }


}
