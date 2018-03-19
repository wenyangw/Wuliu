package com.lnyswz.wuliu.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import com.lnyswz.wuliu.control.CkfhRecyclerViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_scan_show.*
import java.util.ArrayList

class CkfhScanShowActivity : AppCompatActivity() {

    private var layout: ConstraintLayout ? = null

    private var context: Context ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ckfh_scan_show)

        layout = findViewById(R.id.layout_scan_show)
        context = this
        getData()
    }

    fun getData(){
        val address = Utils.APP_URL + "/jxc/xsthAction!getXsth.action"
        val params = mapOf("xsthlsh=" to intent.getStringExtra("lsh"))
        SqlData4Xsth(address, params).execute()
    }



    internal inner class SqlData4Xsth(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)

            val xsth = Utils.getObjectFromJson(s, DatagridBean::class.java)

            ckfh_title.text = getString(R.string.label_title_ckfh)
            ckfh_bm.text = ("${getString(R.string.label_bmmc)}: ${xsth.obj.bmmc}")
            ckfh_lsh.text = ("${getString(R.string.label_lsh)}: ${xsth.obj.xsthlsh}")
            ckfh_date.text = ("${getString(R.string.label_date)}: ${xsth.obj.createTime}")
            ckfh_kh.text = ("${getString(R.string.label_kh)}: ${xsth.obj.khmc}")
            ckfh_ck.text = ("${getString(R.string.label_ck)}: ${xsth.obj.ckmc}")

            var adapter = CkfhRecyclerViewAdpter(context!!, xsth.rows as ArrayList<ObjBean>)
            ckfh_recyler.adapter = adapter
            ckfh_recyler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}

