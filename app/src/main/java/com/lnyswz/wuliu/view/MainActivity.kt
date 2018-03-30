package com.lnyswz.wuliu.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import android.support.constraint.ConstraintSet
import android.util.Log
import android.widget.ImageButton
import com.lnyswz.wuliu.common.SqlUtils

class MainActivity : AppCompatActivity() {

    private var layout: ConstraintLayout? = null
    private var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.layout_main)
        context=this
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

            menus.filter {it.pid != null}.map {getButtons(it.id)}
        }
    }

    internal inner class SqlData4Buttons(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)

            val objs = Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)

            var imgBtn: ImageButton?
            var lastId = 0
            for ((index, obj) in objs.withIndex()) {

                imgBtn = ImageButton(applicationContext)

                when(obj.handler){
                    "ckfh_scan_code" -> {
                        imgBtn.setBackgroundResource(R.drawable.ckfh_scan_icon)
                        imgBtn.id = R.id.ckfh_scan_code
                        imgBtn.setOnClickListener { ckfh_scan_code() }
                    }
                    "ckfh_list_record" -> {
                        imgBtn.setBackgroundResource(R.drawable.ckfh_list_icon)
                        imgBtn.id = R.id.ckfh_list_record
                        imgBtn.setOnClickListener { ckfh_list_record() }
                    }
                }
                layout?.addView(imgBtn)

                val set = ConstraintSet()
                //复制原有的约束关系
                set.clone(layout)
                //清空该控件的约束关系
                //set.clear(btn.id)
                //设置该控件的约束宽度
                set.constrainWidth(imgBtn.id, 0)
                //设置该控件的约束高度
                set.constrainHeight(imgBtn.id, ConstraintLayout.LayoutParams.WRAP_CONTENT)

                if(index == 0){
                    set.connect(imgBtn.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                    set.connect(imgBtn.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                    set.connect(imgBtn.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                    set.connect(imgBtn.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                    set.setVerticalBias(imgBtn.id, 0.2f)
                    set.setMargin(imgBtn.id, ConstraintSet.START, 80)
                    set.setMargin(imgBtn.id, ConstraintSet.END, 80)
                }else {
                    set.connect(imgBtn.id, ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM)
                    set.connect(imgBtn.id, ConstraintSet.START, lastId, ConstraintSet.START)
                    set.connect(imgBtn.id, ConstraintSet.END, lastId, ConstraintSet.END)
                    set.setMargin(imgBtn.id, ConstraintSet.TOP, 20)
                }
                //启用新的约束关系
                set.applyTo(layout)
                lastId = imgBtn.id
            }
        }
    }

    private fun ckfh_scan_code(){
        var inet = Intent(this, CkfhScanShowActivity::class.java)
        inet.putExtra("type","out")
        inet.putExtra("createId",intent.getStringExtra("userId"))
        startActivity(inet)
    }

    private fun ckfh_list_record(){
        var inet = Intent(this,CkfhListActivity::class.java)
        inet.putExtra("type","out")
        inet.putExtra("createId",intent.getStringExtra("userId"))
        startActivity(inet)

    }

}