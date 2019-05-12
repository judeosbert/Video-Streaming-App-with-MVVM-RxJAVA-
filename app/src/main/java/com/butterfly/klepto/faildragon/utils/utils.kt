package com.butterfly.klepto.faildragon.utils

import android.content.Context
import android.content.res.Resources
import com.butterfly.klepto.faildragon.extensions.path
import java.io.File

fun dpToPx(dp: Int): Float {
    return (dp * Resources.getSystem().getDisplayMetrics().density)
}

fun getVideoName(url:String):String{
    return url.replace("https://clips.twitch.tv/","")

}
fun isFileInCache(context: Context, fileName: String):Boolean{
    if(File(context.cacheDir,fileName.path()).exists()){
        return true
    }
    return false
}
fun getCacheFile(context: Context,fileName: String):File{
    return File(context.cacheDir,fileName.path())
}
