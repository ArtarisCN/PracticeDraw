package cn.artaris.customview.CameraView

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.getAvatar
import cn.artaris.customview.getZForCamera

/**
 * cn.artaris.customview.CameraView
 * CustomView
 * 2019.04.24  10:29
 * @author : artairs
 */
class CameraAnimateView : View {

    private var mCamera = Camera()
    private var mRadius: Float = 0f
    private lateinit var mAvatar: Bitmap
    private lateinit var mCutRect: RectF
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mMidAnimatorValue = 0f
    private var mEndAnimatorValue = 0f
    private var mStartAnimatorValue = 0f
    private lateinit var mEndAnimator: ValueAnimator
    private lateinit var mMidAnimator: ValueAnimator
    private lateinit var mStartAnimator: ValueAnimator
    private lateinit var mAnimateSet: AnimatorSet

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mCamera.setLocation(0f, 0f, getZForCamera())

        initAnimate()
    }

    private fun initAnimate() {
        mStartAnimator = ValueAnimator.ofFloat(0f, 1f)
        mStartAnimator.duration = 2000
        mMidAnimator = ValueAnimator.ofFloat(0f, 1f)
        mMidAnimator.duration = 2000
        mEndAnimator = ValueAnimator.ofFloat(0f, 1f)
        mEndAnimator.duration = 4000

        mStartAnimator.addUpdateListener {
            mStartAnimatorValue = it.animatedValue as Float
            invalidate()
        }

        mMidAnimator.addUpdateListener {
            mMidAnimatorValue = it.animatedValue as Float
            invalidate()
        }

        mEndAnimator.addUpdateListener {
            mEndAnimatorValue = it.animatedValue as Float
            invalidate()
        }

        mAnimateSet = AnimatorSet()
        mAnimateSet.play(mMidAnimator).after(mStartAnimator).before(mEndAnimator)
        mAnimateSet.startDelay = 300
        mAnimateSet.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRadius = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2f
        println("mRadius $mRadius")
        mAvatar = getAvatar(resources, (mRadius * 2).toInt())
        mCutRect = RectF(
            -mRadius * 1.5f,
            0f,
            mRadius * 1.5f,
            mRadius * 1.5f
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)
        if (mStartAnimatorValue < 1f) {

            mCutRect.set(
                -mRadius * 1.5f,
                0f,
                mRadius * 1.5f,
                mRadius * 1.5f
            )
            canvas.save()
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            mCamera.save()
            mCamera.rotateX(45f * mStartAnimatorValue)
            mCamera.applyToCanvas(canvas)
            mCamera.restore()
            canvas.clipRect(mCutRect)
            canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
            canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
            canvas.restore()

            mCutRect.set(
                -mRadius * 1.5f,
                -mRadius * 1.5f,
                mRadius * 1.5f,
                0f
            )
            canvas.save()
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            canvas.clipRect(mCutRect)
            canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
            canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
            canvas.restore()

        } else if (mStartAnimatorValue == 1f && mMidAnimatorValue < 1f) {

            mCutRect.set(
                -mRadius * 1.5f,
                0f,
                mRadius * 1.5f,
                mRadius * 1.5f
            )
            canvas.save()
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            canvas.rotate(-270f * mMidAnimatorValue)
            mCamera.save()
            mCamera.rotateX(45f)
            mCamera.applyToCanvas(canvas)
            mCamera.restore()
            canvas.clipRect(mCutRect)
            canvas.rotate(270f * mMidAnimatorValue)
            canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
            canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
            canvas.restore()

            mCutRect.set(
                -mRadius * 1.5f,
                -mRadius * 1.5f,
                mRadius * 1.5f,
                0f
            )
            canvas.save()
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            canvas.rotate(-270f * mMidAnimatorValue)
            canvas.clipRect(mCutRect)
            canvas.rotate(270f * mMidAnimatorValue)
            canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
            canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
            canvas.restore()
        } else if(mStartAnimatorValue == 1f && mMidAnimatorValue == 1f && mEndAnimatorValue < 1f){
            if(mEndAnimatorValue < 0.5f){
                mCutRect.set(
                    -mRadius * 1.5f,
                    0f,
                    mRadius * 1.5f,
                    mRadius * 1.5f
                )
                canvas.save()
                canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
                canvas.rotate(-270f)
                mCamera.save()
                mCamera.rotateX(45f)
                mCamera.applyToCanvas(canvas)
                mCamera.restore()
                canvas.clipRect(mCutRect)
                canvas.rotate(270f)
                canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
                canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
                canvas.restore()

                mCutRect.set(
                    -mRadius * 1.5f,
                    -mRadius * 1.5f,
                    mRadius * 1.5f,
                    0f
                )
                canvas.save()
                canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
                canvas.rotate(-270f)
                mCamera.save()
                mCamera.rotateX(-45f * mEndAnimatorValue * 2)
                mCamera.applyToCanvas(canvas)
                mCamera.restore()
                canvas.clipRect(mCutRect)
                canvas.rotate(270f)
                canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
                canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
                canvas.restore()
            } else{
                mCutRect.set(
                    -mRadius * 1.5f,
                    0f,
                    mRadius * 1.5f,
                    mRadius * 1.5f
                )
                canvas.save()
                canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
                canvas.rotate(-270f)
                mCamera.save()
                mCamera.rotateX(45f * (1 - (mEndAnimatorValue - 0.5f) * 2))
                mCamera.applyToCanvas(canvas)
                mCamera.restore()
                canvas.clipRect(mCutRect)
                canvas.rotate(270f)
                canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
                canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
                canvas.restore()

                mCutRect.set(
                    -mRadius * 1.5f,
                    -mRadius * 1.5f,
                    mRadius * 1.5f,
                    0f
                )
                canvas.save()
                canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
                canvas.rotate(-270f)
                mCamera.save()
                mCamera.rotateX(-45f * (1 - (mEndAnimatorValue - 0.5f) * 2))
                mCamera.applyToCanvas(canvas)
                mCamera.restore()
                canvas.clipRect(mCutRect)
                canvas.rotate(270f)
                canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
                canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
                canvas.restore()
            }
        } else if(mStartAnimatorValue == 1f && mMidAnimatorValue == 1f && mEndAnimatorValue == 1f){
            canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
        }

    }

}