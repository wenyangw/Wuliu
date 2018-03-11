package com.lnyswz.wuliu.view

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import android.support.constraint.ConstraintSet
import com.lnyswz.wuliu.common.SqlUtils

class MainActivity : AppCompatActivity() {

    private var layout: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.layout_main)
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
                getButtons(menu.id)
            }
        }
    }

    internal inner class SqlData4Buttons(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)

            val objs = Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)

            var btn: Button?
            var lastId = 0
            for ((index, obj) in objs.withIndex()) {
                btn = Button(applicationContext)
                when(obj.handler){
                    "ckfh_scan_code" -> btn.id = R.id.ckfh_scan_code
                    "ckfh_list_record" -> btn.id = R.id.ckfh_list_record

                }
                btn.setOnClickListener{
                    view ->  when(view.id){
                        R.id.ckfh_scan_code -> ckfh_scan_code()
                        R.id.ckfh_list_record -> ckfh_list_record()
                    }
                }
                btn.text = obj.text
                layout?.addView(btn)
                val set = ConstraintSet()
                //复制原有的约束关系
                set.clone(layout)
                //清空该控件的约束关系
                //set.clear(btn.id)
                //设置该控件的约束宽度
                set.constrainWidth(btn.id, 0)
                //设置该控件的约束高度
                set.constrainHeight(btn.id, ConstraintLayout.LayoutParams.WRAP_CONTENT)

                if(index == 0){
                    set.connect(btn.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.connect(btn.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    set.connect(btn.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(btn.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                    set.setMargin(btn.id, ConstraintSet.START, 80)
                    set.setMargin(btn.id, ConstraintSet.END, 80)
                }else {
                    set.connect(btn.id, ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM)
                    set.connect(btn.id, ConstraintSet.START, lastId, ConstraintSet.START)
                    set.connect(btn.id, ConstraintSet.END, lastId, ConstraintSet.END)
                    set.setMargin(btn.id, ConstraintSet.TOP, 20)
                }
                //启用新的约束关系
                set.applyTo(layout)
                lastId = btn.id
            }
        }
    }

    private fun ckfh_scan_code(){
        Log.i("Tag", "ckfh_scan_code()")
    }
    private fun ckfh_list_record(){
        Log.i("Tag", "ckfh_list_record()")
    }

}