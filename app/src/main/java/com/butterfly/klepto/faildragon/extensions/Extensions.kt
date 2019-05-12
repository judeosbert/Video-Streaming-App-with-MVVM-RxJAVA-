package com.butterfly.klepto.faildragon.extensions

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import com.butterfly.klepto.faildragon.utils.dpToPx
import kotlinx.android.synthetic.main.custom_tab_item_layout.view.*
import java.io.File


fun TextView.sizeScaleAnimation(endSizeInDP:Float,duration:Long){
    val px:Float  = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        endSizeInDP,Resources.getSystem().displayMetrics)
    val animator = ObjectAnimator.ofFloat(this,
        "textSize", px);
    animator.setDuration(duration)
    animator.start();
}
fun String.path():String{

        Log.d("JUDE", "Path Extension Function$this")
        return Uri.parse(this).lastPathSegment!!

}

