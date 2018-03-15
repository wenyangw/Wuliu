package com.lnyswz.wuliu.common

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.lnyswz.wuliu.R
import java.io.StringBufferInputStream
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Wenyang on 2018/2/18.
 */
object Utils {
    val APP_URL = "http://192.168.0.8/lnyswz"
    //val APP_URL = "http://218.25.74.6/lnyswz"
    //val APP_URL = "http://192.168.0.2:8080"

    //进销存类别的id
    val CATALOG_ID = "aed966ee-e780-4a46-835f-3c6688ec3fd1"

    fun toast(context: Context, message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    /**
     * json="[{}, {}, {}]"
     * return List<T>
     * Usage: Utils.getListFromJson<List<ObjBean>>(s, object : TypeToken<List<ObjBean>>() {}.type)
     */
    fun <T> getListFromJson(key: String, type: Type): T {
        return Gson().fromJson(key, type)
    }

    /**
     * json={"", "", "obj":{}}
     * return JsonBean -> ObjBean
     * Usage: Utils.getObjectFromJson(s, JsonBean::class.java)
     */
    fun <T> getObjectFromJson(key: String, cls: Class<T>): T {
        return Gson().fromJson<T>(key, cls)
    }

}

data class JsonBean(val success: Boolean, val msg: String, val obj: ObjBean)

data class DatagridBean(val total: Long, val obj: ObjBean, val rows: List<ObjBean>)

data class ObjBean(val id: String,
                    val userName: String,
                    val did: String,
                    val pid: String,
                    val text: String,
                    val handler: String,
                    val orderNum: String,
                   val xsthlsh: String,
                   val bmmc: String,
                   val khmc: String,
                   val ckmc: String,
                   val spbh: String,
                    val spmc: String,
                   val spcd: String,
                   val sppp: String,
                   val zjldwmc: String,
                   val zdwsl: String)