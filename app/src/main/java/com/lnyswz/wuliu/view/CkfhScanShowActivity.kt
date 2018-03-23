package com.lnyswz.wuliu.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import com.lnyswz.wuliu.control.CkfhRecyclerViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_scan_show.*


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
            Log.i("dd",s)
            Log.i("dd",xsth.total.toString())

            when(xsth.total > 0){
                true -> {
                    showCkfhDate(true)

                    tv_ckfh_title.text = getString(R.string.label_title_ckfh)
                    tv_ckfh_bm.text = ("${getString(R.string.label_bmmc)}: ${xsth.obj.bmmc} ")
                    tv_ckfh_lsh.text = ("${getString(R.string.label_lsh)}: ${xsth.obj.xsthlsh} ")
                    tv_ckfh_date.text = ("${getString(R.string.label_date)}: ${xsth.obj.createTime} ")
                    tv_ckfh_kh.text = ("${getString(R.string.label_kh)}: ${xsth.obj.khmc} ")
                    tv_ckfh_ck.text = ("${getString(R.string.label_ck)}: ${xsth.obj.ckmc} ")
                    btn_ckfh_submit.text = ("${getString(R.string.btn_submit)}")

//                    btn_ckfh_submit.setOnClickListener( ckfh_submit() )

                    var adapter = CkfhRecyclerViewAdpter(context!!, xsth.rows as ArrayList<ObjBean>)
                    recy_ckfh_recyler.adapter = adapter
                    recy_ckfh_recyler.layoutManager = LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false )
                }
                else -> {
                    showCkfhDate(false)
                    tv_ckfh_lsh_no_data.text = ("${getString(R.string.label_lsh)}: ${intent.getStringExtra("lsh")} ")
                    tv_ckfh_hint_no_data.text = ("${getString(R.string.scan_hint_no_data)}")
                }
             }



        }

//        fun ckfh_submit(): View.OnClickListener? {
//            val address = Utils.APP_URL + "/jxc/xsthAction!getXsth.action"
//            val params = mapOf("xsthlsh=" to intent.getStringExtra("lsh"))
//        }

        fun showCkfhDate (bol: Boolean ){

            when(bol){
                true -> {
                    tv_ckfh_title.visibility = View.VISIBLE
                    tv_ckfh_lsh.visibility = View.VISIBLE
                    tv_ckfh_bm.visibility = View.VISIBLE
                    tv_ckfh_date.visibility = View.VISIBLE
                    tv_ckfh_kh.visibility = View.VISIBLE
                    tv_ckfh_ck.visibility = View.VISIBLE
                    recy_ckfh_recyler.visibility = View.VISIBLE
                    btn_ckfh_submit.visibility = View.VISIBLE
                }
                else ->{
                    tv_ckfh_lsh_no_data.visibility = View.VISIBLE
                    tv_ckfh_hint_no_data.visibility = View.VISIBLE
                }

            }

        }

    }





}

