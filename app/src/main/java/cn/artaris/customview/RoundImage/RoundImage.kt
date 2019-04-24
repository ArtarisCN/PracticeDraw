package cn.artaris.customview.RoundImage

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.R
import cn.artaris.customview.dp2px
import cn.artaris.customview.getAvatar

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.21  14:54
 * @author : artairs
 */
class RoundImage : View {

    private val DEFAULT_BOARD_COLOR: Int = Color.BLACK
    private val DEFAULT_BOARD_WIDTH: Float = 12f.dp2px()

    private var mBoardColor: Int
    private var mBoarderWidth: Float

    private lateinit var mAvatar: Bitmap

    private var mRadius: Float = 0f
    private var mCut: RectF = RectF()
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs,
            R.styleable.RoundImageAttrs, defStyleAttr, 0)
        mBoarderWidth = array.getDimension(R.styleable.RoundImageAttrs_board_width, DEFAULT_BOARD_WIDTH)
        mBoardColor = array.getColor(R.styleable.RoundImageAttrs_board_color, DEFAULT_BOARD_COLOR)
        println("mBoardColor: $mBoardColor")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2f - mBoarderWidth
        mAvatar = getAvatar(resources, (mRadius * 2).toInt())
        mCut.set(
            -mRadius,
            -mRadius,
            mRadius,
            mRadius
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())

        //画底色
        canvas.drawCircle(0f, 0f, mRadius + mBoarderWidth, mPaint)

        val saved = canvas.saveLayer(mCut, null)
        canvas.drawCircle(0f, 0f, mRadius, mPaint)
        mPaint.xfermode = mXfermode
        canvas.drawBitmap(mAvatar, -mRadius, -mRadius, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(saved)
    }
}