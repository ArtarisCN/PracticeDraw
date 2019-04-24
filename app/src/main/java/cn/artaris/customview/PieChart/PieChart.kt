package cn.artaris.customview.PieChart

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.dp2px

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.20  19:55
 * @author : artairs
 */
class PieChart : View {

    private val RADIUS = 150f.dp2px()
    private val mDataList = ArrayList<ChartData>()

    private var mPaint: Paint
    private var mRadius = RADIUS

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mDataList.add(ChartData(R.color.darker_gray, 60f))
        mDataList.add(ChartData(R.color.holo_orange_dark, 110f))
        mDataList.add(ChartData(R.color.holo_red_light, 120f))
        mDataList.add(ChartData(R.color.holo_purple, 70f))

        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.WHITE)
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        var startAngle = 0f
        mDataList.forEach {
            if (mDataList.indexOf(it) == 2) {
                canvas.save()
                canvas.translate((-10f).dp2px(), (-10f).dp2px())
            }
            mPaint.color = ContextCompat.getColor(context, it.color)
            canvas.drawArc(-mRadius, -mRadius, mRadius, mRadius, startAngle, it.degree, true, mPaint)
            startAngle += it.degree
            if (mDataList.indexOf(it) == 2) {
                canvas.restore()
            }
        }
    }


    private class ChartData(@ColorRes val color: Int, val degree: Float)
}