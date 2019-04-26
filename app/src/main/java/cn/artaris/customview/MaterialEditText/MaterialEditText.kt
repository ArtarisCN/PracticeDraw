package cn.artaris.customview.MaterialEditText

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import cn.artaris.customview.R
import cn.artaris.customview.dp2px

/**
 * cn.artaris.customview.MaterialEditText
 * CustomView
 * 2019.04.26  10:13
 * @author : artairs
 */
class MaterialEditText : EditText {

    private val TEXT_SIZE = 4f.dp2px()
    private val VERTICAL_OFFSET = 18f.dp2px()
    private val HORIZONTAL_OFFSET = 7f.dp2px()

    private var mHint: String? = null
    private var mHintHeadShown = false
    private var mAnimateValue: Float = 1f
    private var mBackgroundPadding = Rect()
    private var mAnimate: ObjectAnimator? = null
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        val array = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        mHint = array.getString(R.styleable.MaterialEditText_hint_text)
        array.recycle()

        background.getPadding(mBackgroundPadding)
        init()
    }


    private fun init() {
        background.getPadding(mBackgroundPadding)
        setPadding(
            mBackgroundPadding.left,
            (mBackgroundPadding.top + 12f.dp2px()).toInt(),
            mBackgroundPadding.right,
            mBackgroundPadding.bottom
        )

        mPaint.textSize = TEXT_SIZE.dp2px()
        mPaint.color = Color.GRAY
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (mHintHeadShown && s.toString().isEmpty()) {
                    getAnimate()?.start()
                    mHintHeadShown = false
                } else if (!mHintHeadShown && s.toString().isNotEmpty()) {
                    getAnimate()?.reverse()
                    mHintHeadShown = true
                }
            }
        })
    }

    public fun setAnimateValue(value: Float) {
        mAnimateValue = value
        invalidate()
    }

    private fun getAnimate(): ObjectAnimator? {
        if (mAnimate == null) {
            mAnimate = ObjectAnimator.ofFloat(this, "AnimateValue", 0f, 1f)
        }
        return mAnimate
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.textSize = (TEXT_SIZE + 10f * mAnimateValue).dp2px()
        canvas.drawText(mHint!!, HORIZONTAL_OFFSET, VERTICAL_OFFSET + 22f.dp2px() * mAnimateValue, mPaint)
    }


}