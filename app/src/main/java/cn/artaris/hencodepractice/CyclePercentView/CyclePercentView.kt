package cn.artaris.hencodepractice.CyclePercentView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * cn.artaris.hencodepractice.CyclePercentView
 * CustomView
 * 2019.04.22  15:48
 * @author : artairs
 */
class CyclePercentView: View {

    private lateinit var mPaint: Paint

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        initPath()
        initPaint()
    }

    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun initPath() {

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }

}