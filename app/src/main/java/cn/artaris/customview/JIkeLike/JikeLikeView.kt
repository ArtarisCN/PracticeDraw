package cn.artaris.customview.JIkeLike

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout



/**
 * cn.artaris.hencodepractice.JIkeLike
 * HenCodeLayoutPractice
 * 2019.04.18  16:45
 * @author : artairs
 */
class JikeLikeView : LinearLayout {

    private lateinit var mLikeUnelected: Bitmap
    private lateinit var mLikeSelected: Bitmap
    private lateinit var mLikeShineing: Bitmap

    private var mIsLike = false
    private var mLikeCount = 123

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initBitmap()
        initAnimate()
    }

    private fun initAnimate() {

    }

    private fun initBitmap() {
        mLikeUnelected = BitmapFactory.decodeResource(resources, cn.artaris.customview.R.mipmap.ic_messages_like_unselected)
        mLikeSelected = BitmapFactory.decodeResource(resources, cn.artaris.customview.R.mipmap.ic_messages_like_selected)
        mLikeSelected = BitmapFactory.decodeResource(resources, cn.artaris.customview.R.mipmap.ic_messages_like_selected_shining)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                println("ACTION_DOWN")
//                ObjectAnimator.ofPropertyValuesHolder(mJikeLikeLayout, mScaleXZoomAnimate, mScaleYZoomAnimate).start()
            }
            MotionEvent.ACTION_CANCEL -> {
                println("ACTION_CANCEL")
            }
            MotionEvent.ACTION_UP -> {
                println("ACTION_UP")
                invalidate()
            }
            else -> {

            }
        }
        return true
    }


    fun reset(count:Int){
        mLikeCount = count
        mIsLike = false
        invalidate()
    }

    private fun setInitialSelectedState(select:Boolean){

    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


    }

}