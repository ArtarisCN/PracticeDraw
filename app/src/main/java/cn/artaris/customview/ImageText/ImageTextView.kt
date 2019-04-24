package cn.artaris.customview.ImageText

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import cn.artaris.customview.dp2px
import cn.artaris.customview.getAvatar

/**
 * cn.artaris.hencodepractice.ImageText
 * CustomView
 * 2019.04.23  10:49
 * @author : artairs
 */
class ImageTextView : View {

    private val DEFAULT_IMAGE_WIDTH = 160f.dp2px()
    private val IMAGE_OFFSET = 100f.dp2px()

    private val mText = "漫步在又一年的春天，回望人生的旅程，珍藏着时光中的美丽与绚烂，纯静与光明，仰望阳光明媚，轻盈风起的早晨，那些泛黄的记忆穿越时光的隧道，感动着自己，时光里那些难忘的瞬间将人生刻画成最美好的曾经。初春的大街，天气已经变暖，匆匆赶路的人们，己褪去厚厚的棉衣，感受着阳光的温暖。空气清新，阳光明媚，风和日丽，白云在蔚蓝天空的怀中尽情的撒着娇，它们如久别从重逢的恋人，相互缠绵，尽情的拥抱，倾诉着离别后相思的语言。阳光悄悄跑进我的房间，温柔的抚摸着我沉睡的脸，让我如婴儿般静静的躺在她的怀中，享受着阳光带给我的温暖，在阳光沐浴中慢慢醒来，倾听鸟儿在窗前尽情欢快的歌唱，碧蓝的天空如平静无波的湖水般清澈明朗，轻柔的白云在蓝天怀抱中漂浮游动。带着柔媚的风情，涂满了整个蓝天。春天伸着温暖的双臂，拥吻着行人的脸颊，那么激烈，那么深情，那么亲切。阵阵微风吹来，携带着春天的芳香气息，聆听着春天的声音，感受着春天的阳光，她的笑容，明媚灿烂，暖暖的格外温柔，照在身上，温暖醉人。春季的阳光洒满大地的每一个角落，为大地点燃勃勃生机，处处散发着清新的气息。春天万物复苏，可四周依旧，空空荡荡，显的那么荒凉，花草树木，依然没有恢复往日的拥挤与喧嚣，也许树木，花草还沉浸在节日的喜悦中，它们还没收到春天发出的信息，还在痴痴的等待……不知道今年的春天，会有多少感动的相逢，会有多少个动人故事上演，会有多少的相遇，心动，会收获多少惊喜的邂逅与相拥。时光如梭，岁月如歌，希望所有的相遇都不会走散，相遇时如姹紫嫣红的烟火，万紫千红，即使转身后的瞬间，一切变成了烟花燃尽后的一缕青烟，也能深深把美好，收藏在记忆的心间。让平淡有了美丽的色彩，心灵不再这么孤单。"

    private val mRadius = DEFAULT_IMAGE_WIDTH /2
    private var mImage: Bitmap
    private var mCutRect = RectF()
    private lateinit var mPaint: Paint
    private var mTextLength = floatArrayOf(0f)
    private lateinit var mFontMetrics: Paint.FontMetrics
    private val xFermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mImage = getAvatar(resources, (DEFAULT_IMAGE_WIDTH).toInt())
        initPaint()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCutRect.set(
            IMAGE_OFFSET,
            IMAGE_OFFSET,
            IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH,
            IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH
        )
    }

    private fun initPaint() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.FILL

        mPaint.textSize = 12f.dp2px()

        mFontMetrics = mPaint.fontMetrics

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)

        val saved = canvas.saveLayer(mCutRect, null)
        canvas.drawCircle(
            IMAGE_OFFSET + mRadius,
            IMAGE_OFFSET + mRadius,
            mRadius,
            mPaint
        )
        mPaint.xfermode = xFermode
        canvas.drawBitmap(mImage, IMAGE_OFFSET, IMAGE_OFFSET, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(saved)

        var startTextIndex = 0
        var yOffset = mPaint.fontSpacing
        while (startTextIndex < mText.length) {

            val textTop = yOffset + mFontMetrics.ascent
            val textBottom = yOffset + mFontMetrics.descent

            var usableWidth: Float
            if (textBottom < IMAGE_OFFSET || textTop > (IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH)) {
                usableWidth = width.toFloat()
                val length = mPaint.breakText(mText, startTextIndex, mText.length, true, usableWidth, mTextLength)
                canvas.drawText(mText, startTextIndex, startTextIndex + length, 0f, yOffset, mPaint)
                startTextIndex += length
            } else {
                val usableWidthLeft:Double
                val usableWidthRight:Double
                val chord = if (textTop > (IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH / 2)) {
                    Math.sqrt((2 * mRadius * (IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH -  textTop) - (IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH -  textTop) *(IMAGE_OFFSET + DEFAULT_IMAGE_WIDTH -  textTop)).toDouble())
                } else {
                    Math.sqrt((2 * mRadius * (textBottom - IMAGE_OFFSET) - (textBottom - IMAGE_OFFSET) * (textBottom - IMAGE_OFFSET)).toDouble())
                }

                usableWidthLeft = IMAGE_OFFSET + mRadius - chord
                usableWidthRight = width - (IMAGE_OFFSET + mRadius + chord)

                val lengthLeft = mPaint.breakText(mText, startTextIndex, mText.length, true, usableWidthLeft.toFloat(), mTextLength)
                canvas.drawText(mText, startTextIndex, startTextIndex + lengthLeft, 0f, yOffset, mPaint)
                startTextIndex += lengthLeft

                val lengthRight = mPaint.breakText(mText, startTextIndex, mText.length, true, usableWidthRight.toFloat(), mTextLength)
                canvas.drawText(mText,startTextIndex,startTextIndex + lengthRight,(IMAGE_OFFSET + mRadius + chord).toFloat(),yOffset,mPaint)
                startTextIndex += lengthRight
            }

            yOffset += mPaint.fontSpacing
        }
    }


}