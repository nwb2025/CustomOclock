package com.example.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class OclockView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {
    private var h: Int = 0
    private var w: Int = 0
    private var padding: Int = 0
    private var handTruncation = 0
    private var hourTrancation = 0
    private var radius = 0
    private lateinit var paint: Paint
    private var isInit = false
    private val rect: Rect = Rect()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(!isInit) initOClock()
        canvas?.drawColor(Color.WHITE)
        drawCircle(canvas)
        drawNumeral(canvas)
        drawHands(canvas)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = min(measuredHeight, measuredWidth )
        setMeasuredDimension(size, size)
    }

    private fun drawHourHand(canvas: Canvas?, loc: Double) {
        var angle = Math.PI * loc / 30 - Math.PI / 2
        val handRadius = radius - hourTrancation
        paint.reset()
        paint.apply {
            color = resources.getColor(android.R.color.holo_blue_dark)
            strokeWidth = 15f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        canvas?.drawLine(
                (w / 2f + cos(angle) * -handRadius / 3).toFloat(),
                (h / 2f + sin(angle) * -handRadius / 3).toFloat(),
                (w / 2f + cos(angle) * handRadius).toFloat(),
                (h / 2f + sin(angle) * handRadius).toFloat(),
                paint
        )
    }

    private fun drawMinHand(canvas: Canvas?, loc: Double) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        paint.reset()
        paint.apply {
            color = resources.getColor(android.R.color.holo_red_dark)
            strokeWidth = 22f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        var handRadius = radius - handTruncation - 27
        canvas?.drawLine(
                (w / 2 + cos(angle) * -handRadius / 3).toFloat(),
                (h / 2 + sin(angle) * -handRadius / 3).toFloat(),
                (w / 2 + cos(angle) * handRadius).toFloat(),
                (h / 2 + sin(angle) * handRadius).toFloat(),
                paint
        )
    }

    private fun drawSecHand(canvas: Canvas?, loc: Double) {
        var angle = Math.PI * loc / 30 - Math.PI / 2
        paint.reset()
        paint.apply {
            color = resources.getColor(android.R.color.black)
            strokeWidth = 30f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        var handRadius = radius - handTruncation
        canvas?.drawLine(
                (w / 2 + cos(angle) * -handRadius / 3).toFloat(),
                (h / 2 + sin(angle) * -handRadius / 3).toFloat(),
                (w / 2 + cos(angle) * handRadius).toFloat(),
                (h / 2 + sin(angle) * handRadius).toFloat(),
                paint
        )
    }

    private fun drawHands(canvas: Canvas?) {
        var calendar: Calendar = Calendar.getInstance()
        drawHourHand(canvas, (calendar.get(Calendar.HOUR).toDouble()))
        drawMinHand(canvas, calendar.get(Calendar.MINUTE).toDouble())
        drawSecHand(canvas, calendar.get(Calendar.SECOND).toDouble())
    }

    private fun drawNumeral(canvas: Canvas?) {
        for (num in 1..12) {
            var angle = Math.PI / 6 * (num - 3)
            var x = (w / 2 + cos(angle) * radius - rect.width() / 2).toFloat()
            var y = (h / 2 + sin(angle) * radius - rect.height() / 2).toFloat()
            paint.apply{
                strokeWidth = 25f
                style = Paint.Style.FILL
                isAntiAlias = true
            }

            canvas?.drawRect(x, y, x + 20, y + 20, paint)
        }
    }

    private fun drawCircle(canvas: Canvas?) {
        paint.reset()
        paint.apply {
            color = resources.getColor(android.R.color.black)
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }
        canvas?.drawCircle(
                width / 2f,
                height / 2f,
                radius + padding - 10f,
                paint)
    }

    private fun initOClock(){
        h = height
        w = width
        padding = 50
        var min = min(h, w)
        radius = min / 2 - padding
        handTruncation = min / 20
        hourTrancation = min / 6
        paint = Paint()
        isInit = true
    }
}