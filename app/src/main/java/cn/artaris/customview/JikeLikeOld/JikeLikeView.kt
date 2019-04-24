package cn.artaris.customview.JikeLikeOld

import android.animation.AnimatorSet
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import cn.artaris.customview.R

/**
 * cn.artaris.hencodepractice.JikeLike
 * HenCodeLayoutPractice
 * 2019.04.16  18:36
 * @author : artairs
 */
class JikeLikeView: FrameLayout {


    private lateinit var mJikeLikeTextView: JIkeLikeTextView

    private var mHasShineImage:Boolean = true

    private val mScaleXZoomAnimate = PropertyValuesHolder.ofFloat("scaleX",1f,0.5f)
    private val mScaleYZoomAnimate = PropertyValuesHolder.ofFloat("scaleY",1f,0.5f)

    private val mScaleXScaleAnimate1 = PropertyValuesHolder.ofFloat("scaleX",0.5f,1.2f)
    private val mScaleYScaleAnimate1 = PropertyValuesHolder.ofFloat("scaleY",0.5f,1.2f)

    private val mScaleXScaleAnimate2 = PropertyValuesHolder.ofFloat("scaleX",1.2f,1f)
    private val mScaleYScaleAnimate2 = PropertyValuesHolder.ofFloat("scaleY",1.2f,1f)

    private val mActionUpAnimationSet = AnimatorSet()

    var mCurrentSelectedState:Boolean = false


    constructor(context: Context) : this(context,null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        val array = context.theme.obtainStyledAttributes(attrs,
            R.styleable.JikeLikeViewAttrs, defStyleAttr, 0)
        mHasShineImage = array.getBoolean(R.styleable.JikeLikeViewAttrs_has_shine, true)

        initAnimate()
    }

    private fun initAnimate() {

    }


    fun reset(count:Int){

    }

    private fun setInitialSelectedState(select:Boolean){

    }


//    override fun onAttachedToWindow() {
////        super.onAttachedToWindow()
////        mJikeLike = findViewById(R.id.iv_like)
////        mJikeSunshine = findViewById(R.id.iv_sunshine)
////
////        mJikeLikeLayout = findViewById(R.id.ll_image)
////
////        if(!mHasShineImage){
////            mJikeSunshine.visibility = View.INVISIBLE
////            invalidate()
////        }
//    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action){
//            MotionEvent.ACTION_DOWN->{
//                println("ACTION_DOWN")
//                ObjectAnimator.ofPropertyValuesHolder(mJikeLikeLayout, mScaleXZoomAnimate, mScaleYZoomAnimate).start()
//            }
//            MotionEvent.ACTION_CANCEL->{
//                println("ACTION_CANCEL")
//            }
//            MotionEvent.ACTION_UP->{
//                println("ACTION_UP")
//                mActionUpAnimationSet.play( ObjectAnimator.ofPropertyValuesHolder(mJikeLikeLayout, mScaleXScaleAnimate1, mScaleYScaleAnimate1))
//                    .before(ObjectAnimator.ofPropertyValuesHolder(mJikeLikeLayout, mScaleXScaleAnimate2, mScaleYScaleAnimate2))
//                mActionUpAnimationSet.start()
//                if(mCurrentSelectedState){
//
//                } else{
//
//                }
//            }
//            else->{
//
//            }
//        }
//        return true
//    }
}