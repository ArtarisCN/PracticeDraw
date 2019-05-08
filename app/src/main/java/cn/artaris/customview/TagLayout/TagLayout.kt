package cn.artaris.customview.TagLayout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import kotlin.math.max

/**
 * cn.artaris.customview.TagLayout
 * CustomView
 * 2019.04.28  10:27
 * @author : artairs
 */
class TagLayout : ViewGroup {

    var childrenBounds = ArrayList<Rect>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var index = 0
        while (index < childCount) {
            val view = getChildAt(index)
            val childBounds = childrenBounds[index]
            view.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
            index++
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = 0
        var heightUsed = 0
        var index = 0
        val usableWidth = MeasureSpec.getSize(widthMeasureSpec)
        var lastLineHeight = 0
        var maxWidth = 0
        println()
        while (index < childCount) {
            val child = getChildAt(index)

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            val childBounds: Rect
            if (childrenBounds.size <= index) {
                childBounds = Rect()
            } else {
                childBounds = childrenBounds[index]
            }

            println(child.measuredWidth)
            if (widthUsed + child.measuredWidth >= usableWidth) {
                widthUsed = 0
                heightUsed += lastLineHeight
                measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed)
            }

            childBounds.set(widthUsed, heightUsed, widthUsed + child.measuredWidth, heightUsed + child.measuredHeight)
            childrenBounds.add(childBounds)

            lastLineHeight = max(lastLineHeight, child.measuredHeight)

            widthUsed += child.measuredWidth
            maxWidth = max(maxWidth, widthUsed)
            index++
        }
        heightUsed += lastLineHeight
        val widthMeasureSpecNew = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.getMode(widthMeasureSpec))
        val heightMeasureSpecNew = MeasureSpec.makeMeasureSpec(heightUsed, MeasureSpec.getMode(heightMeasureSpec))

        setMeasuredDimension(widthMeasureSpecNew, heightMeasureSpecNew)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}