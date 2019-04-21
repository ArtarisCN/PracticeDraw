package cn.artaris.hencodepractice.DashBoard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.hencodepractice.R
import cn.artaris.hencodepractice.dp2px

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.19  17:15
 * @author : artairs
 */
class DashBoardView : View {

    private val DEFAULT_MAX_INDEX = 20
    private val DEFAULT_RADIUS = 400f
    private val DEFAULT_STOKEN_WIDTH = 4f

    private val START_ANGLE = 150
    private val SWEEP_ANGLE = 240


    private var mIndex: Int = 12
    private var mMaxScale: Int
    private var mRadius: Float
    private var mStrokeWidth: Float

    private lateinit var mPaint: Paint
    private lateinit var mBoardPath: Path
    private lateinit var mPathMeasure: PathMeasure
    private lateinit var mPathEffect: PathDashPathEffect

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DashBoardViewAttrs, defStyleAttr, 0)
        mMaxScale = array.getInt(R.styleable.DashBoardViewAttrs_scale, DEFAULT_MAX_INDEX)
        mRadius = array.getFloat(R.styleable.DashBoardViewAttrs_radius, DEFAULT_RADIUS)
        mStrokeWidth = array.getFloat(R.styleable.DashBoardViewAttrs_stroke_width, DEFAULT_STOKEN_WIDTH)

        initPaint()
        intiPath()
    }


    private fun intiPath() {
        mBoardPath = Path()
        val boardRectF =
            RectF(width - mRadius, -mRadius, mRadius, mRadius)
        mBoardPath.addArc(boardRectF, START_ANGLE.toFloat(), SWEEP_ANGLE.toFloat())

        mPathMeasure = PathMeasure(mBoardPath, false)

        val scalePath = Path()
        scalePath.addRect(
            0f,
            0f,
            dp2px(1f),
            dp2px(10f),
            Path.Direction.CCW
        )

        mPathEffect =
            PathDashPathEffect(
                scalePath,
                (mPathMeasure.length - dp2px(2f)) / (mMaxScale),
                0f,
                PathDashPathEffect.Style.ROTATE
            )
    }

    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStrokeWidth
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            MeasureSpec.makeMeasureSpec(
                ((mRadius + mStrokeWidth) * 2 + paddingRight + paddingRight).toInt(),
                MeasureSpec.getMode(widthMeasureSpec)
            ),
            MeasureSpec.makeMeasureSpec(
                ((mRadius + mStrokeWidth) * (1 + Math.sin(Math.toRadians(START_ANGLE.toDouble()))) + paddingTop + paddingBottom).toInt(),
                MeasureSpec.getMode(heightMeasureSpec)
            )
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate(paddingLeft + mRadius, paddingTop + mRadius)
        canvas.drawPoint(0f, 0f, mPaint)
        canvas.drawPath(mBoardPath, mPaint)
        mPaint.pathEffect = mPathEffect
        mPaint.strokeWidth = dp2px(1f)
        canvas.drawPath(mBoardPath, mPaint)
        mPaint.pathEffect = null

        val angle:Double = (START_ANGLE.toFloat() + mIndex.toFloat() / mMaxScale.toFloat() * SWEEP_ANGLE.toFloat()).toDouble()
        canvas.drawLine(
            0f,
            0f,
            (Math.cos(Math.toRadians(angle)) * mRadius * 0.6).toFloat(),
            (Math.sin(Math.toRadians(angle)) * mRadius * 0.6).toFloat(),
            mPaint
        )
    }


}