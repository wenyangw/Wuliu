package com.lnyswz.wuliu.common

import android.content.Context
import android.widget.Toast

/**
 * Created by Wenyang on 2018/2/18.
 */
object Utils {
    fun toast(context: Context, message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}