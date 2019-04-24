package cn.artaris.customview.CameraView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.getAvatar
import cn.artaris.customview.getZForCamera

/**
 * cn.artaris.customview.CameraView
 * CustomView
 * 2019.04.23  17:38
 * @author : artairs
 */
class CameraView : View {

    private var mCamera = Camera()
    private var mRadius: Float = 0f
    private lateinit var mAvatar: Bitmap
    private lateinit var mCutRect: RectF
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mCamera.rotateX(45f)
        mCamera.setLocation(0f, 0f, getZForCamera())
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

        canvas.save()
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas.rotate(-20f)
        mCamera.applyToCanvas(canvas)
        canvas.clipRect(mCutRect)
        canvas.rotate(20f)
        canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
        canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
        canvas.restore()

        mCutRect.set(-mRadius * 1.5f,
            -mRadius * 1.5f,
            mRadius * 1.5f,
            0f)
        canvas.save()
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        canvas.rotate(-20f)
        canvas.clipRect(mCutRect)
        canvas.rotate(20f)
        canvas.translate(-(width / 2).toFloat(), -(height / 2).toFloat())
        canvas.drawBitmap(mAvatar, paddingLeft.toFloat(), paddingTop.toFloat(), mPaint)
        canvas.restore()
    }

}