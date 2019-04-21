package cn.artaris.hencodepractice.JIkeLike

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import android.widget.LinearLayout
import cn.artaris.hencodepractice.R

/**
 * cn.artaris.hencodepractice.JIkeLike
 * HenCodeLayoutPractice
 * 2019.04.18  16:45
 * @author : artairs
 */
class JikeLikeView:LinearLayout {

    private lateinit var mLikeUnelected:Bitmap
    private lateinit var mLikeSelected:Bitmap
    private lateinit var mLikeShineing:Bitmap




    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

        initBitmap()
    }

    private fun initBitmap() {
        mLikeUnelected = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_unselected)
        mLikeSelected = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_selected)
        mLikeSelected = BitmapFactory.decodeResource(resources, R.mipmap.ic_messages_like_selected_shining)

    }
}