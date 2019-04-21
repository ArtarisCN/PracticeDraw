package cn.artaris.hencodepractice.RoundImage

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.hencodepractice.R
import cn.artaris.hencodepractice.dp2px

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.21  14:54
 * @author : artairs
 */
class RoundImage : View {

    private val DEFAULT_BOARD_COLOR: Int = Color.BLACK
    private val DEFAULT_BOARD_WIDTH: Float = dp2px(12f)

    private var mBoarderWidth: Float
    private var mBoardColor: Int

    private lateinit var mAvatar: Bitmap

    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mRadius: Float = 0f
    private var mXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private var mCut: RectF = RectF()

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
        mAvatar = getAvatar()
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
        canvas.drawCircle(0f, 0f, mRadius + mBoarderWidth, mPaint)

        val saved = canvas.saveLayer(mCut, null)
        mPaint.color = mBoardColor
        canvas.drawCircle(0f, 0f, mRadius, mPaint)
        mPaint.xfermode = mXfermode
        canvas.drawBitmap(mAvatar, -mRadius, -mRadius, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(saved)
    }


    private fun getAvatar(): Bitmap {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = (mRadius * 2).toInt()
        println("onSizeChanged $mRadius")

        return BitmapFactory.decodeResource(resources, R.drawable.avatar, option)
    }


}