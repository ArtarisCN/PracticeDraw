package cn.artaris.customview.DashBoard

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.R
import cn.artaris.customview.dp2px

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.19  17:15
 * @author : artairs
 */
class DashBoardView : View {

    private val DEFAULT_RADIUS = 400f
    private val DEFAULT_MAX_INDEX = 20
    private val DEFAULT_STOKEN_WIDTH = 4f

    private val START_ANGLE = 150
    private val SWEEP_ANGLE = 240


    var mIndex: Int = 12
    private var mMaxScale: Int
    private var mRadius: Float
    private var mStrokeWidth: Float

    private lateinit var mPaint: Paint
    private lateinit var mBoardPath: Path
    private lateinit var mPointerPath: Path
    private lateinit var mPathMeasure: PathMeasure
    private lateinit var mPathEffect: PathDashPathEffect

    private var mMoveAnimatorValue = 1f
    private var mChangeIndex: Int = mIndex
    private lateinit var mMoveAnimator: ValueAnimator


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.DashBoardViewAttrs, defStyleAttr, 0)
        mMaxScale = array.getInt(R.styleable.DashBoardViewAttrs_scale, DEFAULT_MAX_INDEX)
        mRadius = array.getFloat(R.styleable.DashBoardViewAttrs_radius, DEFAULT_RADIUS)
        mStrokeWidth = array.getFloat(R.styleable.DashBoardViewAttrs_stroke_width, DEFAULT_STOKEN_WIDTH)

        initPaint()
        intiPath()
        initAnimate()
    }

    private fun intiPath() {
        mBoardPath = Path()
        mPointerPath = Path()
        val boardRectF =
            RectF(width - mRadius, -mRadius, mRadius, mRadius)
        mBoardPath.addArc(boardRectF, START_ANGLE.toFloat(), SWEEP_ANGLE.toFloat())

        mPathMeasure = PathMeasure(mBoardPath, false)

        val scalePath = Path()
        scalePath.addRect(
            0f,
            0f,
            1f.dp2px(),
            10f.dp2px(),
            Path.Direction.CCW
        )

        mPathEffect =
            PathDashPathEffect(
                scalePath,
                (mPathMeasure.length - 1f.dp2px()) / (mMaxScale),
                0f,
                PathDashPathEffect.Style.ROTATE
            )
    }

    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStrokeWidth
    }

    private fun initAnimate() {
        mMoveAnimator = ValueAnimator.ofFloat(0f, 1f)
        mMoveAnimator.addUpdateListener {
            mMoveAnimatorValue = it.animatedValue as Float
            invalidate()
        }
    }

    public fun setIndex(index: Int) {
        mChangeIndex = index
        mMoveAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                mIndex = mChangeIndex
            }
        })
        mMoveAnimator.start()
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
        mPaint.strokeWidth = 1f.dp2px()
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawPath(mBoardPath, mPaint)
        mPaint.pathEffect = null

        //仪表盘角度
        val angle: Double =
            (START_ANGLE.toFloat() + (mIndex + (mChangeIndex - mIndex) * mMoveAnimatorValue) / mMaxScale.toFloat() * SWEEP_ANGLE.toFloat()).toDouble()

        mPointerPath.reset()
        //从原点画至指针端点
        mPointerPath.lineTo(
            (Math.cos(Math.toRadians(angle)) * mRadius * 0.6).toFloat(),
            (Math.sin(Math.toRadians(angle)) * mRadius * 0.6).toFloat()
        )

        //计算 指针端点左右两个短线 相对于 指针端点 的相对坐标
        val leftX = (Math.cos(Math.toRadians(angle-150)) * 10f.dp2px()).toFloat()
        val leftY = (Math.sin(Math.toRadians(angle-150)) * 10f.dp2px()).toFloat()

        val rightX = (Math.cos(Math.toRadians(angle-210)) * 10f.dp2px()).toFloat()
        val rightY = (Math.sin(Math.toRadians(angle-210)) * 10f.dp2px()).toFloat()

        //以指针端点先向左画短线
        mPointerPath.rLineTo(leftX,leftY)
        //以回到指针端点
        mPointerPath.rMoveTo(-leftX,-leftY)
        //以指针端点先向右画短线
        mPointerPath.rLineTo(rightX,rightY)

        mPaint.strokeWidth = 2f.dp2px()
        mPaint.strokeJoin = Paint.Join.ROUND
        canvas.drawPath(mPointerPath,mPaint)
    }


}