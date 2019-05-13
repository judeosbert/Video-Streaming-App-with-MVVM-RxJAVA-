package com.butterfly.klepto.faildragon.utils

import android.support.v7.util.DiffUtil
import com.butterfly.klepto.faildragon.BaseApplication
import com.butterfly.klepto.faildragon.extensions.path
import com.butterfly.klepto.faildragon.modal.Feed
import java.io.File


fun getVideoName(url:String):String{
    return url.replace("https://clips.twitch.tv/","")

}
fun isFileInCache(fileName: String):Boolean{
    if(File(BaseApplication.applicationContext().cacheDir,fileName.path()).exists()){
        return true
    }
    return false
}
fun getCacheFile(fileName: String):File{
    return File(BaseApplication.applicationContext().cacheDir,fileName.path())
}

class DiffUtilCallBack:DiffUtil.ItemCallback<Feed>(){
    override fun areItemsTheSame(old: Feed, new: Feed): Boolean {
        return old.id.equals(new.id)
    }

    override fun areContentsTheSame(old: Feed, new: Feed): Boolean {
        return old == new
    }

}
