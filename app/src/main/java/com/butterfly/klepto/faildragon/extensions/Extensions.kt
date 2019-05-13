package com.butterfly.klepto.faildragon.extensions

import android.net.Uri
import android.util.Log


fun String.path():String{
    Log.d("JUDE", "Path Extension Function$this")
    return Uri.parse(this).lastPathSegment!!

}

