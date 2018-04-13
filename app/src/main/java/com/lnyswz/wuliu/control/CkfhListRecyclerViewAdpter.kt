package com.lnyswz.wuliu.control

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lnyswz.wuliu.R
import com.lnyswz.wuliu.common.ObjBean
import com.lnyswz.wuliu.view.CkfhDetailActivity

class CkfhListRecyclerViewAdpter(var context: Context, var datas: List<ObjBean>,var intent: Intent) : RecyclerView.Adapter<CkfhListRecyclerViewAdpter.ViewHodler>() {
    override fun onBindViewHolder(holder: ViewHodler?, position: Int) {
        var data = datas.get(position)
        holder!!.ckfh_lsh.text =Html.fromHtml("<b>${context.getString(R.string.label_lsh)}:</b>  ${data.xsthlsh}")
        holder!!.ckfh_kh.text = Html.fromHtml("<b>${context.getString(R.string.label_kh)}:</b>  ${data.khmc}")
        holder!!.ckfh_createTime.text =  Html.fromHtml("<b>${context.getString(R.string.label_time)}:</b>  ${data.createTime}")

        holder!!.itemView.setOnClickListener{
            var inet = Intent(context,CkfhDetailActivity::class.java)
            inet!!.putExtra("lsh", data.xsthlsh)
            inet!!.putExtra("type",intent.getStringExtra("type"))
            inet.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(inet)
        }
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
        var ckfh_createTime: TextView = itemView.findViewById(R.id.tv_ckfh_list_createTime)
    }

}




