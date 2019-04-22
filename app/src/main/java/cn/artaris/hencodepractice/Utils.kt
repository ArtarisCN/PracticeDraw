package cn.artaris.hencodepractice

import android.content.res.Resources
import android.util.TypedValue

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.19  22:16
 * @author : artairs
 */

fun Float.dp2px() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)