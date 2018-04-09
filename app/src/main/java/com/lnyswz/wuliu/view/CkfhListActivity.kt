package com.lnyswz.wuliu.view

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.gson.reflect.TypeToken
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ObjBean
import com.lnyswz.wuliu.common.SqlUtils
import com.lnyswz.wuliu.common.Utils
import com.lnyswz.wuliu.control.CkfhListRecyclerViewAdpter
import kotlinx.android.synthetic.main.activity_ckfh_list.*
import java.util.*
import android.support.v7.widget.DividerItemDecoration


class CkfhListActivity : AppCompatActivity(){

    private var context: Context?= null
    private val cal: Calendar = Calendar.getInstance()
    private var haveDates: String = "0"

    private var createYear: Int = 0
    private var createMonth: Int = 0
    private var createDay: Int = 0
    private var endYear: Int = 0
    private var endMonth: Int = 0
    private var endDay: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ckfh_list)
        context = this
        crateTimeInto()
        endTimeInto()
        btn_ckfh_list_select.setOnClickListener {
            getCkfhData()
        }
        getCkfhData()
    }

    private fun crateTimeInto() {
        when(cal.get(Calendar.MONTH)+1){
            1 -> createYear = cal.get(Calendar.YEAR)-1
            else -> createYear = cal.get(Calendar.YEAR)
        }
        createMonth = cal.get(Calendar.MONTH)
        createDay = cal.get(Calendar.DAY_OF_MONTH)
        tv_ckfh_list_createTime.text = "${createYear}-${createMonth}-${createDay}"
        tv_ckfh_list_createTime.setOnClickListener {
            timeManage(tv_ckfh_list_createTime, createYear, createMonth, createDay, "createTime")
        }
    }

    private fun endTimeInto() {
        endYear = cal.get(Calendar.YEAR)
        endMonth = cal.get(Calendar.MONTH) + 1
        endDay = cal.get(Calendar.DAY_OF_MONTH)
        tv_ckfh_list_endTime.text = "${endYear}-${endMonth}-${endDay}"
        tv_ckfh_list_endTime.setOnClickListener {
            timeManage(tv_ckfh_list_endTime, endYear, endMonth, endDay, "endTime")
        }
    }

    private fun timeManage(tv: TextView,year: Int, month: Int, day: Int,par: String) {
        showList("0")
        ckfh_list_datePicker.init(year, month - 1, day)
        { datePicker, y, m, d ->
            datePicker.visibility = View.GONE
            showList(haveDates)
            tv.text = "${y}-${m + 1}-${d}"
            when (par) {
                "createTime" -> {
                    createYear = y
                    createMonth = m + 1
                    createDay = d
                }
                "endTime" -> {
                    endYear = y
                    endMonth = m + 1
                    endDay = d
                }
            }
        }
    }

    fun getCkfhData(){
        //Òþ²ØÈí¼üÅÌ
        val inputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(et_ckfh_list_khmc.getWindowToken(), 0)

        val address = Utils.APP_URL + "/jxc/xsthAction!getXsthOutList.action"
        val params = mapOf("type=" to intent.getStringExtra("type"),
                                    "createId=" to intent.getStringExtra("createId"),
                                    "createTime=" to "${tv_ckfh_list_createTime.text}",
                                    "endTime=" to "${tv_ckfh_list_endTime.text} 23:59:00",
                                    "khmc=" to et_ckfh_list_khmc.text.toString()
                            )
        SqlData4XsthList(address, params).execute()
    }

    internal inner class SqlData4XsthList(url: String, param: Map<String, String>) : SqlUtils(url, param) {
        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val xsths = Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)
            when(xsths.size) {
                0    -> {
                            showList("2")
                            tv_ckfh_list_msg.text = getString(R.string.ckfh_list_no_list)
                        }
                else -> {
                            showList("1")
                            var adapter = CkfhListRecyclerViewAdpter(context!!, xsths,intent)
                            recy_ckfh_list.adapter = adapter
                            recy_ckfh_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            recy_ckfh_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                        }
            }
        }
    }

    fun showList(para: String){
        when (para){
            "0" ->{
                        tv_ckfh_list_msg.visibility = View.GONE
                        recy_ckfh_list.visibility = View.GONE
                        ckfh_list_datePicker.visibility = View.VISIBLE
                        btn_ckfh_list_select.visibility = View.GONE
                    }
            "1" -> {
                        tv_ckfh_list_msg.visibility = View.GONE
                        recy_ckfh_list.visibility = View.VISIBLE
                        btn_ckfh_list_select.visibility = View.VISIBLE
                        haveDates = para
                    }
            "2" -> {
                        tv_ckfh_list_msg.visibility = View.VISIBLE
                        recy_ckfh_list.visibility = View.GONE
                        btn_ckfh_list_select.visibility = View.VISIBLE
                        haveDates = para
                    }
        }
    }

}


