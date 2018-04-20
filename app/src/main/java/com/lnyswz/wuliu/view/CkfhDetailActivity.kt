package com.lnyswz.wuliu.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import com.lnyswz.wuliu.control.CkfhDetailRecycleViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_detail.*


class CkfhDetailActivity : AppCompatActivity() {

    private var context: Context?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ckfh_detail)
        context = this
        getCkfhDetail()
    }

    fun getCkfhDetail(){
        val address = Utils.sqlUrl(SPUtils.get(context, "serverUrl", "").toString(), "/jxc/xsthAction!getXsthOutDetail.action")
        val params = mapOf("xsthlsh=" to intent.getStringExtra("lsh"),
                                   "type=" to intent.getStringExtra("type"))
        SqlData4XsthDetail(address, params).execute()
    }

    internal inner class SqlData4XsthDetail(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val xsthDetails = Utils.getObjectFromJson(s, DatagridBean::class.java)
            when(xsthDetails.msg){
                ""  ->{

                    var adapter = CkfhDetailRecycleViewAdpter(context!!, xsthDetails.rows)
                    recy_ckfh_detail_show.adapter = adapter
                    recy_ckfh_detail_show.layoutManager = LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false )
                    recy_ckfh_detail_show.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                }
                else ->{
                  Utils.toast(applicationContext,xsthDetails.msg)
                }
            }
        }
    }

}
