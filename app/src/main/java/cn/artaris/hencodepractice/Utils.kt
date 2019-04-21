package cn.artaris.hencodepractice

import android.content.res.Resources
import android.util.TypedValue

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.19  22:16
 * @author : artairs
 */

public fun dp2px(dp:Float):Float{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
}
