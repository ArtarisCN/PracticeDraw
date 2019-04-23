package cn.artaris.hencodepractice.CyclePercentView

import android.animation.ValueAnimator
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.hencodepractice.R
import cn.artaris.hencodepractice.dp2px

/**
 * cn.artaris.hencodepractice.CyclePercentView
 * CustomView
 * 2019.04.22  15:48
 * @author : artairs
 */
class CyclePercentView : View {

    private val DEFAULT_BOARD_WIDTH: Int = 20f.dp2px().toInt()
    private val DEFAULT_BOARD_COLOR: Int = Color.GRAY
    private val DEFAULT_BOARD_BG_COLOR: Int = Color.LTGRAY

    private var mBoardColor: Int
    private var mBoardWidth: Int
    private var mBoardBgColor: Int
    private var mDrawDegree = 240

    private lateinit var mPaint: Paint
    private var mFontMetrics = Paint.FontMetrics()
    private val mTextBounds = Rect()

    private var mRadius: Float = 0f

    private var mMoveAnimatorValue = 1f
    private lateinit var mMoveAnimator: ValueAnimator

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.CyclePercentAttrs, defStyleAttr, 0)
        mBoardColor = array.getColor(R.styleable.CyclePercentAttrs_board_color, DEFAULT_BOARD_COLOR)
        mBoardBgColor = array.getColor(R.styleable.CyclePercentAttrs_board_bg_color, DEFAULT_BOARD_BG_COLOR)
        mBoardWidth = array.getInt(R.styleable.CyclePercentAttrs_board_width, DEFAULT_BOARD_WIDTH)
    }

    init {
        initPath()
        initPaint()
        initAnimate()
    }

    private fun initAnimate() {
        mMoveAnimator = ValueAnimator.ofFloat(0f, 1f)
        mMoveAnimator.repeatMode = RESTART
        mMoveAnimator.repeatCount = Short.MAX_VALUE.toInt()
        mMoveAnimator.duration = 6000
        mMoveAnimator.addUpdateListener {
            mMoveAnimatorValue = it.animatedValue as Float
            invalidate()
        }
        mMoveAnimator.start()
    }


    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.style = Paint.Style.STROKE

        mPaint.typeface = Typeface.createFromAsset(context.assets, "spyagency3.ttf")
        mPaint.textSize = 22f.dp2px()

        mFontMetrics = mPaint.fontMetrics
    }

    private fun initPath() {

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = (Math.min(w - paddingLeft - paddingRight, h - paddingTop - paddingBottom) / 2 - mBoardWidth).toFloat()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        val degree = (mDrawDegree + mMoveAnimatorValue * 360) % 360

        mPaint.style = Paint.Style.STROKE
        mPaint.color = mBoardBgColor
        mPaint.strokeWidth = mBoardWidth.toFloat()
        canvas.drawCircle(0f, 0f, mRadius, mPaint)

        mPaint.color = mBoardColor
        canvas.drawArc(-mRadius, -mRadius, mRadius, mRadius, -90f, degree, false, mPaint)

        val text = String.format(context.getString(R.string.cycle_text_format), (degree / 3.6).toInt())

        mPaint.getTextBounds(text, 0, text.length, mTextBounds)
        mPaint.style = Paint.Style.FILL
        println(mFontMetrics.ascent)
        println(mFontMetrics.descent)
        canvas.drawText(
            text,
            (-mTextBounds.right / 2).toFloat(),
//            ((mTextBounds.top + mTextBounds.bottom) / 2).toFloat(),
            -(mFontMetrics.descent + mFontMetrics.ascent) / 2,
            mPaint
        )


    }

}