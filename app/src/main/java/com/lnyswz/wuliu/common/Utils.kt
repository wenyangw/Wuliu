package com.lnyswz.wuliu.common

import android.content.Context
import android.widget.Toast

/**
 * Created by Wenyang on 2018/2/18.
 */
object Utils {
    //val APP_URL = "http://192.168.0.8/lnyswz"
    //val APP_URL = "http://218.25.74.6/lnyswz"
    val APP_URL = "http://192.168.0.2:8080"

    //进销存类别的id
    val CATALOG_ID = "aed966ee-e780-4a46-835f-3c6688ec3fd1"
    fun toast(context: Context, message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

data class JsonBean(val success: Boolean, val msg: String, val obj: UtilBean)

data class UtilBean(val id: String, val userName: String, val pid: String, val text: String)

data class ButtonBean(val id: String, val pid: String, val text: String)

class Result<T> {
    var success: Boolean = false
    var msg: String? = null
    var obj: T? = null
}