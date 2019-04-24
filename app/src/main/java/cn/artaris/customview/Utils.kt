package cn.artaris.customview

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.19  22:16
 * @author : artairs
 */

fun Float.dp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)


fun getAvatar(resources:Resources,radius:Int):Bitmap{
    val option = BitmapFactory.Options()
    option.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, R.drawable.avatar, option)
    option.inJustDecodeBounds = false
    option.inDensity = option.outWidth
    option.inTargetDensity = radius

    return BitmapFactory.decodeResource(resources, R.drawable.avatar, option)
}


fun getZForCamera() = -6 * Resources.getSystem().displayMetrics.density