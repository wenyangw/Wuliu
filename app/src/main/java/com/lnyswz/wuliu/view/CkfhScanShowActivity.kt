package com.lnyswz.wuliu.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager

import android.view.View
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.*
import com.lnyswz.wuliu.control.CkfhRecyclerViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_scan_show.*
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.text.method.NumberKeyListener
import android.view.KeyEvent


class CkfhScanShowActivity : AppCompatActivity() {

    private var context: Context ?= null
    private var xsthlsh: String ?= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ckfh_scan_show)
        context = this
        listenerEditViewVal()
    }

    fun listenerEditViewVal(){
        et_scan_input.addTextChangedListener(EditTextChangeListener())
    }

    fun getData(lsh: String){

        val address = Utils.APP_URL + "/jxc/xsthAction!getXsth.action"
        val params = mapOf("xsthlsh=" to lsh,
                                   "type=" to intent.getStringExtra("type"),
                                   "createId=" to intent.getStringExtra("createId"))
        SqlData4Xsth(address, params).execute()
    }

    internal inner class SqlData4Xsth(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val xsth = Utils.getObjectFromJson(s, DatagridBean::class.java)
            when(xsth.msg){
                "" ->{
                    showCkfhDate(true)
                    putTVText(xsth)
                    btn_ckfh_submit.setOnClickListener{ckfhUpdata() }
                    adapterManager(xsth)
                }
                else -> {
                    showCkfhDate(false)
                    tv_ckfh_scan_msg.text = xsth.msg

                }
            }
        }
    }
    private fun ckfhUpdata() {
        val address = Utils.APP_URL + "/jxc/xsthAction!updateXsthOut.action"
        val params = mapOf("xsthlsh=" to xsthlsh.toString(),
                "type=" to intent.getStringExtra("type"),
                "createId=" to intent.getStringExtra("createId").toString())
        UpdateData4Xsth(address, params).execute()
    }



    internal inner class UpdateData4Xsth(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val json = Utils.getObjectFromJson(s, JsonBean::class.java)
            when(json.success){
                true -> finish()
            }
            Utils.toast(context!!,json.msg)
        }
    }



    private fun showCkfhDate (bol: Boolean ){
        when(bol){
            true -> {

                // 隐藏软键盘
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)

                et_scan_input.visibility=View.GONE
                tv_ckfh_scan_msg.visibility = View.GONE

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
                tv_ckfh_scan_msg.visibility = View.VISIBLE
            }

        }

    }




    private fun adapterManager(xsth: DatagridBean) {
        var adapter = CkfhRecyclerViewAdpter(context!!, xsth.rows as ArrayList<ObjBean>)
        recy_ckfh_recyler.adapter = adapter
        recy_ckfh_recyler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun putTVText(xsth: DatagridBean) {
        tv_ckfh_title.text = getString(R.string.label_title_ckfh)
        tv_ckfh_bm.text = "${getString(R.string.label_bmmc)}: ${xsth.obj.bmmc} "
        tv_ckfh_lsh.text = "${getString(R.string.label_lsh)}: ${xsth.obj.xsthlsh} "
        tv_ckfh_date.text = "${getString(R.string.label_date)}: ${xsth.obj.createTime} "
        tv_ckfh_kh.text = "${getString(R.string.label_kh)}: ${xsth.obj.khmc} "
        tv_ckfh_ck.text = "${getString(R.string.label_ck)}: ${xsth.obj.ckmc} "
        btn_ckfh_submit.text = ("${getString(R.string.btn_submit)}")
    }




    inner class EditTextChangeListener : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun afterTextChanged(editable: Editable) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int){
            when( charSequence!!.length ){
                13 -> judgeLsh(charSequence!!.substring(0, 12))
                12 -> judgeLsh(charSequence!!.toString())
                in 0 .. 11 ->  tv_ckfh_scan_msg.text = ""
            }

        }
    }

    private fun judgeLsh(lsh: String){
        when( lsh!!.substring(6,8) ){
            "05","11" -> {
                            getData(lsh)
                            xsthlsh = lsh
                         }
            else -> Utils.toast(this,"${getString(R.string.scan_no_lsh)}")
            }


    }

}



