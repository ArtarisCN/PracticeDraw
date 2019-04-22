package cn.artaris.omview.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.hencodepractice.R

/**
 * cn.artaris.omview.dialog
 * OMAndroid
 * 2019.04.11  18:41
 * @author : artairs
 */
class OMProgressMarkView : View {

    enum class StatusEnum {
        Loading,
        LoadSuccess,
        LoadFailure
    }

    private var mProgressColor = 0
    private var mLoadSuccessColor = 0
    private var mLoadFailureColor = 0
    private var mProgressWidth = 0f
    private var mProgressRadius = 0f

    private val mMinAngle = 15
    private val mMaxAngle = 270

    private var mStartAngle = 0
    private var mCurAngle = 30
    private var mProgressPathIncrease = true

    private var mState = StatusEnum.Loading

    private lateinit var mCirclePath: Path
    private lateinit var mSuccessPath: Path

    private lateinit var mFailureLeftPath: Path
    private lateinit var mFailureRightPath: Path


    private lateinit var mAnimationMeasure: PathMeasure

    private lateinit var mSuccessDstPath: Path
    private lateinit var mFailureDstPath: Path

    private lateinit var mPaint: Paint

    private lateinit var mCircleAnimator: ValueAnimator
    private lateinit var mSuccessAnimator: ValueAnimator
    private lateinit var mFailureLeftAnimator: ValueAnimator
    private lateinit var mFailureRightAnimator: ValueAnimator

    private var mSuccessAnimationValue = 0f
    private var mFailureLeftAnimationValue = 0f
    private var mFailureRightAnimationValue = 0f
    private var mCircleAnimationValue = 0f

    private val mSuccessAnimatorSet = AnimatorSet()
    private val mFailureAnimatorSet = AnimatorSet()


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.ProgressMarkView, defStyleAttr, 0)
        mProgressColor = array.getColor(R.styleable.ProgressMarkView_progress_color, Color.WHITE)
        mLoadSuccessColor = array.getColor(R.styleable.ProgressMarkView_load_success_color, Color.WHITE)
        mLoadFailureColor = array.getColor(R.styleable.ProgressMarkView_load_failure_color, Color.WHITE)
        mProgressWidth = array.getDimension(R.styleable.ProgressMarkView_progress_width, 12f)
        mProgressRadius = array.getDimension(R.styleable.ProgressMarkView_progress_radius, 80f)

        initPaint()
        initPath()
        initAnimate()
    }


    private fun initAnimate() {
        mCircleAnimator = ValueAnimator.ofFloat(0f, 1f)
        mCircleAnimator.addUpdateListener {
            mCircleAnimationValue = it.animatedValue as Float
            invalidate()
        }

        mSuccessAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        mSuccessAnimator.addUpdateListener {
            mSuccessAnimationValue = it.animatedValue as Float
            invalidate()
        }

        mFailureLeftAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        mFailureLeftAnimator.addUpdateListener {
            mFailureLeftAnimationValue = it.animatedValue as Float
            invalidate()
        }

        mFailureRightAnimator = ValueAnimator.ofFloat(0f, 1.0f)
        mFailureRightAnimator.addUpdateListener {
            mFailureRightAnimationValue = it.animatedValue as Float
            invalidate()
        }

        mFailureAnimatorSet.play(mFailureLeftAnimator).after(mCircleAnimator).before(mFailureRightAnimator)
        mSuccessAnimatorSet.play(mSuccessAnimator).after(mCircleAnimator)
    }

    private fun initPath() {
        mCirclePath = Path()
        mSuccessPath = Path()
        mFailureLeftPath = Path()
        mFailureRightPath = Path()

        mAnimationMeasure = PathMeasure()

        mSuccessDstPath = Path()
        mFailureDstPath = Path()
    }


    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = mProgressColor
        mPaint.style = Paint.Style.STROKE
        mPaint.isDither = true
        mPaint.strokeWidth = mProgressWidth
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    private fun resetPath() {
        mCircleAnimationValue = 0f
        mSuccessAnimationValue = 0f
        mFailureLeftAnimationValue = 0f
        mFailureRightAnimationValue = 0f

        mSuccessPath.reset()
        mSuccessDstPath.reset()

        mFailureDstPath.reset()
        mFailureLeftPath.reset()
        mFailureRightPath.reset()
    }

    fun loadLoading() {
        mState = StatusEnum.Loading
        invalidate()
    }

    fun loadSuccess(listener: AnimatorFinishListener?) {
        resetPath()
        mState = StatusEnum.LoadSuccess
        if (listener != null) {
            mSuccessAnimatorSet.addListener(object :AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    listener.onAnimatorFinish()
                }
            })
        }

        mSuccessAnimatorSet.start()
    }

    fun loadFailure(listener: AnimatorFinishListener?) {
        resetPath()
        mState = StatusEnum.LoadFailure
        if (listener != null) {
            mFailureAnimatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    listener.onAnimatorFinish()
                }
            })
        }

        mFailureAnimatorSet.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        when (mState) {
            StatusEnum.Loading -> {
                //将当前画布的点移到getPaddingLeft,getPaddingTop,后面的操作都以该点作为参照点
                canvas.save()
                canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())

                if (mCurAngle < mMaxAngle && mProgressPathIncrease) {
                    mCurAngle += 3
                } else if (mCurAngle >= mMaxAngle && mProgressPathIncrease) {
                    mProgressPathIncrease = false
                } else if (!mProgressPathIncrease && mCurAngle > mMinAngle) {
                    mCurAngle -= 3
                } else if (!mProgressPathIncrease && mCurAngle <= mMinAngle) {
                    mProgressPathIncrease = true
                }

                mStartAngle += 8
                mStartAngle %= 360

                canvas.drawArc(0f,
                        0f,
                        mProgressRadius * 2 + mProgressWidth,
                        mProgressRadius * 2 + mProgressWidth,
                        mStartAngle.toFloat(),
                        mCurAngle.toFloat(),
                        false,
                        mPaint)
                invalidate()

                canvas.restore()
            }
            StatusEnum.LoadSuccess -> {
                canvas.save()
                canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())

                canvas.drawArc(0f,
                        0f,
                        mProgressRadius * 2 + mProgressWidth,
                        mProgressRadius * 2 + mProgressWidth,
                        mStartAngle + mCircleAnimationValue * (360 - mStartAngle),
                        mCurAngle + mCircleAnimationValue * (270 - mCurAngle),
                        false,
                        mPaint)
                canvas.restore()

                if (mCircleAnimationValue == 1f) {
                    canvas.save()

                    canvas.translate((width / 2).toFloat(), height / 2.toFloat())
                    mSuccessPath.moveTo(-mProgressRadius / 3 - mProgressWidth,
                            -mProgressRadius / 3 + mProgressWidth * 2)
                    mSuccessPath.lineTo(0f,
                            mProgressWidth * 2)
                    mSuccessPath.lineTo((mProgressRadius * (1 / Math.sqrt(2.0))).toFloat(),
                            -(mProgressRadius * (1 / Math.sqrt(2.0))).toFloat() + mProgressWidth)

                    mAnimationMeasure.setPath(mSuccessPath, false)
                    mAnimationMeasure.getSegment(0f,
                            mSuccessAnimationValue * mAnimationMeasure.length,
                            mSuccessDstPath,
                            true)
                    println(mSuccessAnimationValue * mAnimationMeasure.length)
                    canvas.drawPath(mSuccessDstPath, mPaint)
                    canvas.restore()

                }
            }
            StatusEnum.LoadFailure -> {
                canvas.save()
                canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())

                canvas.drawArc(0f,
                        0f,
                        mProgressRadius * 2 + mProgressWidth,
                        mProgressRadius * 2 + mProgressWidth,
                        mStartAngle.toFloat(),
                        mCurAngle + mCircleAnimationValue * (360 - mCurAngle),
                        false,
                        mPaint)
                canvas.restore()

                canvas.save()
                canvas.translate((width / 2).toFloat(), height / 2.toFloat())

                if (mCircleAnimationValue == 1f) {

                    mFailureLeftPath.moveTo(-mProgressRadius / 3,
                            -mProgressRadius  / 3)
                    mFailureLeftPath.lineTo(mProgressRadius  / 3,
                            mProgressRadius / 3)

                    mAnimationMeasure.setPath(mFailureLeftPath, false)
                    mAnimationMeasure.getSegment(0f,
                            mFailureLeftAnimationValue * mAnimationMeasure.length,
                            mFailureDstPath,
                            true)
                    println(mFailureLeftAnimationValue * mAnimationMeasure.length)
                    canvas.drawPath(mFailureDstPath, mPaint)
                }

                if(mFailureLeftAnimationValue == 1f){
                    mFailureRightPath.moveTo(mProgressRadius / 3,
                            -mProgressRadius  / 3)
                    mFailureRightPath.lineTo(-mProgressRadius / 3,
                            mProgressRadius / 3)

                    mAnimationMeasure.setPath(mFailureRightPath, false)
                    mAnimationMeasure.getSegment(0f,
                            mFailureRightAnimationValue * mAnimationMeasure.length,
                            mFailureDstPath,
                            true)
                    println(" " + mFailureRightAnimationValue * mAnimationMeasure.length)

                    canvas.drawPath(mFailureDstPath, mPaint)
                }
                canvas.restore()

            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int
        val height: Int
        var mode = MeasureSpec.getMode(widthMeasureSpec)
        var size = MeasureSpec.getSize(widthMeasureSpec)

        width = if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            (2 * mProgressRadius + mProgressWidth + paddingLeft.toFloat() + paddingRight.toFloat()).toInt()
        }

        mode = MeasureSpec.getMode(heightMeasureSpec)
        size = MeasureSpec.getSize(heightMeasureSpec)
        height = if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            (2 * mProgressRadius + mProgressWidth + paddingTop.toFloat() + paddingBottom.toFloat()).toInt()
        }
        setMeasuredDimension(width, height)
    }


    interface AnimatorFinishListener {
        fun onAnimatorFinish()
    }


}