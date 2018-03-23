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
            holder!!.ckfh_spbh.text = data.spbh
            holder!!.ckfh_spbmc.text = data.spmc
            holder!!.ckfh_spcd.text = data.spcd
            holder!!.ckfh_sppp.text = data.sppp
            holder!!.ckfh_zdw.text = data.zjldwmc
            holder!!.ckfh_zdwsl.text = data.zdwsl

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
       var itemView = View.inflate(context, R.layout.item_recyclerview,null)
        return ViewHodler(itemView)

    }


    inner class ViewHodler(itemView: View): RecyclerView.ViewHolder(itemView) {

        var ckfh_spbh: TextView = itemView.findViewById(R.id.tv_ckfh_show_spbh)
        var ckfh_spbmc: TextView = itemView.findViewById(R.id.tv_ckfh_show_spmc)
        var ckfh_sppp: TextView = itemView.findViewById(R.id.tv_ckfh_show_sppp)
        var ckfh_spcd: TextView = itemView.findViewById(R.id.tv_ckfh_show_spcd)
        var ckfh_zdw: TextView = itemView.findViewById(R.id.tv_ckfh_show_zjldw)
        var ckfh_zdwsl: TextView = itemView.findViewById(R.id.tv_ckfh_show_zjldwsl)
    }

}

