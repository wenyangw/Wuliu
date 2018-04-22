package com.lnyswz.wuliu.control

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ObjBean

class CkfhRecyclerViewAdpter(var context: Context, var datas: ArrayList<ObjBean>) : RecyclerView.Adapter<CkfhRecyclerViewAdpter.ViewHodler>() {
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHodler?, position: Int) {
        var data = datas[position]
        holder!!.ckfh_spbh.text =  Html.fromHtml("<b>${context.getString(R.string.label_spbh)}:</b>  ${data.spbh}")
        holder.ckfh_spmc.text =  Html.fromHtml("<b>${context.getString(R.string.label_spmc)}:</b>  ${data.spmc}")
        holder.ckfh_spcd.text =  Html.fromHtml("<b>${context.getString(R.string.label_spcd)}:</b>  ${data.spcd}")
        holder.ckfh_zdwsl.text =  Html.fromHtml("<b>${context.getString(R.string.label_zjldwsl)}:</b>  ${data.zdwsl}(${data.zjldwmc.trim()})")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
       var itemView = View.inflate(context, R.layout.item_recyclerview_ckfh_detial,null)
        return ViewHodler(itemView)
    }

    inner class ViewHodler(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ckfh_spbh: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spbh)
        var ckfh_spmc: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spmc)
        var ckfh_spcd: TextView = itemView.findViewById(R.id.tv_ckfh_detail_spcd)
        var ckfh_zdwsl: TextView = itemView.findViewById(R.id.tv_ckfh_detail_zjldwsl)
    }

}

