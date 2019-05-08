package cn.artaris.customview.TagLayout;

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import cn.artaris.customview.dp2px
import java.util.*

/**
 * cn.artaris.customview.TagLayout
 * CustomView
 * 2019.05.08  14:11
 *
 * @author : artairs
 */
class ColorTextView : TextView {
    private val mName = arrayOf(
        "钢铁侠", "绿巨人", "黑豹", "美国队长", "蜘蛛侠", "黑寡妇", "战争机器"
        , "猎鹰", "雷神", "奇异博士", "惊奇队长", "冬日战士", "小辣椒", "洛基", "蚁人", "鹰眼", "猩红女巫", "快银", "幻视", "奥创", "灭霸"
    )

    private val COLORS = intArrayOf(
        Color.parseColor("#E91E63"),
        Color.parseColor("#673AB7"),
        Color.parseColor("#3F51B5"),
        Color.parseColor("#2196F3"),
        Color.parseColor("#009688"),
        Color.parseColor("#FF9800"),
        Color.parseColor("#FF5722"),
        Color.parseColor("#795548")
    )
    private val TEXT_SIZES = floatArrayOf(18f, 24f, 28f)
    private val random = Random()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setTextColor(Color.WHITE)
        mPaint.color = COLORS[random.nextInt(COLORS.size)]
        textSize = TEXT_SIZES[random.nextInt(TEXT_SIZES.size)]
        setPadding(16f.dp2px().toInt(), 8f.dp2px().toInt(), 16f.dp2px().toInt(), 8f.dp2px().toInt())
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        val lp = layoutParams as ViewGroup.MarginLayoutParams
//        canvas.drawRoundRect(
//            0f, 0f, (width).toFloat(),
//            (height).toFloat(), 4f.dp2px(), 4f.dp2px(), mPaint
//        )

        canvas.drawRoundRect(
            lp.leftMargin.toFloat(), lp.topMargin.toFloat(), (width - lp.rightMargin).toFloat(),
            (height - lp.bottomMargin).toFloat(), 4f.dp2px(), 4f.dp2px(), mPaint
        )
//        println("""Width = $width
//            |Height = $height
//        """.trimMargin())
        super.onDraw(canvas)
    }
}
