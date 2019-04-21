package cn.artaris.hencodepractice

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.21  14:54
 * @author : artairs
 */
class RoundImage : View {

    private val DEFAULT_BOARD_COLOR: Int = Color.BLACK
    private val DEFAULT_BOARD_WIDTH: Int = dp2px(10f).toInt()

    private var mBoardWidth: Int
    @ColorRes
    private var mBoardColor: Int
    private var mAvatarRes: Int

    private lateinit var mAvatar :Bitmap

    private lateinit var mPaint: Paint
    private var mRadius: Float = 0f
    private var mXfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.RoundImageAttrs, defStyleAttr, 0)
        mBoardWidth = array.getInt(R.styleable.RoundImageAttrs_board_width, DEFAULT_BOARD_WIDTH)
        mBoardColor = array.getInt(R.styleable.RoundImageAttrs_board_color, DEFAULT_BOARD_COLOR)
        mAvatarRes = array.getInt(R.styleable.RoundImageAttrs_avatar_res, 0)


        initPaint()
        initBitmap()
    }

    private fun initBitmap() {
        mAvatar = BitmapFactory.decodeResource(Resources.getSystem(),mAvatarRes)
    }

    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2f - mBoardWidth
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        val saved = canvas.saveLayer(null, null)
        mPaint.color = ContextCompat.getColor(context,mBoardColor)
        canvas.drawCircle(0f, 0f,mRadius + mBoardWidth,mPaint)
        mPaint.xfermode = mXfermode
        canvas.drawBitmap(mAvatar,-mRadius,-mRadius,mPaint);
        mPaint.xfermode = null
        canvas.restoreToCount(saved)
    }


}