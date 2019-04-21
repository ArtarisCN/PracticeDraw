package cn.artaris.hencodepractice.PieChart

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import cn.artaris.hencodepractice.dp2px

/**
 * cn.artaris.hencodepractice
 * HenCodeLayoutPractice
 * 2019.04.20  19:55
 * @author : artairs
 */
class PieChart : View {

    private val RADIUS = dp2px(150f)
    private val mDataList = ArrayList<ChartData>()

    private  var mPaint:Paint

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


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate((width /2).toFloat(), (height /2).toFloat())
        var startAngle = 0f
        for (chartData in mDataList) {
            if(mDataList.indexOf(chartData) == 2){
                canvas.save()
                canvas.translate(-dp2px(10f), -dp2px(10f))
            }
            mPaint.color = ContextCompat.getColor(context,chartData.color)
            canvas.drawArc(-RADIUS,-RADIUS,RADIUS,RADIUS,startAngle,chartData.degree,true,mPaint)
            startAngle += chartData.degree
            if(mDataList.indexOf(chartData) == 2){
                canvas.restore()
            }
        }
    }


    private class ChartData(@ColorRes val color:Int,val degree: Float)
}