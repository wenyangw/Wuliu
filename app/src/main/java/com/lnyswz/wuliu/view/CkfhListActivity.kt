package com.lnyswz.wuliu.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.DatagridBean
import com.lnyswz.wuliu.common.ObjBean
import com.lnyswz.wuliu.common.SqlUtils
import com.lnyswz.wuliu.common.Utils
import com.lnyswz.wuliu.control.CkfhListRecyclerViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_list.*

class CkfhListActivity : AppCompatActivity() {

    private var context: Context?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ckfh_list)
        context = this
        getCkfhData()

    }

    fun getCkfhData(){
        val address = Utils.APP_URL + "/jxc/xsthAction!getXsth.action"
        val params = mapOf("xsthlsh=" to intent.getStringExtra("lsh"))
        SqlData4XsthList(address, params).execute()
    }

    internal inner class SqlData4XsthList(url: String, param: Map<String, String>) : SqlUtils(url, param) {

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val xsth = Utils.getObjectFromJson(s, DatagridBean::class.java)

            val list= listOf<ObjBean>(xsth.obj,xsth.obj,xsth.obj,xsth.obj,xsth.obj)
            var adapter = CkfhListRecyclerViewAdpter(context!!, list!!)
            recy_ckfh_list.adapter = adapter
            recy_ckfh_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)



        }

    }



}
