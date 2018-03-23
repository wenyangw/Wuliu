package com.lnyswz.wuliu.control

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ObjBean
import com.lnyswz.wuliu.common.Utils

class CkfhListRecyclerViewAdpter(var context: Context, var datas: List<ObjBean>) : RecyclerView.Adapter<CkfhListRecyclerViewAdpter.ViewHodler>(){
    override fun onBindViewHolder(holder: ViewHodler?, position: Int) {
        var data = datas.get(position)
        holder!!.ckfh_kh.setOnClickListener{Utils.toast(context,"ddd") }

        holder!!.ckfh_lsh.text = data.xsthlsh
        holder!!.ckfh_kh.text = data.khmc

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHodler {
        var itemView = View.inflate(context, R.layout.item_recyclerview_ckfh_list,null)
        return ViewHodler(itemView)
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    inner class ViewHodler(itemView: View): RecyclerView.ViewHolder(itemView){

        var ckfh_lsh: TextView = itemView.findViewById(R.id.tv_ckfh_list_lsh)
        var ckfh_kh: TextView = itemView.findViewById(R.id.tv_ckfh_list_kh)
    }

}


