package com.lnyswz.wuliu.control

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ObjBean


class CkfhRecyclerViewAdpter(var context: Context, var datas: ArrayList<ObjBean>  ) : RecyclerView.Adapter<CkfhRecyclerViewAdpter.ViewHodler>() {
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHodler?, position: Int) {
            var data = datas.get(position)
            holder!!.ckfh_spbh.text =  "${context.getString(R.string.label_spbh)}:${data.spbh}"
            holder!!.ckfh_spmc.text =  "${context.getString(R.string.label_spmc)}:${data.spmc}"
            holder!!.ckfh_sppp.text =  "${context.getString(R.string.label_sppp)}:${data.sppp}"
            holder!!.ckfh_spcd.text =  "${context.getString(R.string.label_spcd)}:${data.spcd}"
            holder!!.ckfh_zdwsl.text =  "${context.getString(R.string.label_zjldsl)}:${data.zdwsl}(${data.zjldwmc.trim()})"
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
       var itemView = View.inflate(context, R.layout.item_recyclerview_ckfh_detial,null)
        return ViewHodler(itemView)
    }

    inner class ViewHodler(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ckfh_spbh: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spbh)
        var ckfh_spmc: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spmc)
        var ckfh_sppp: TextView = itemView.findViewById(R.id.tv_ckfh_detail_sppp)
        var ckfh_spcd: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spcd)
        var ckfh_zdwsl: TextView = itemView.findViewById(R.id.tv_ckfh_detail_zjldwsl)
    }

}

