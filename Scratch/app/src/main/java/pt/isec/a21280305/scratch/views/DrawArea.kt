package pt.isec.a21280305.scratch.views

import android.content.Context
import android.graphics.*

import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.drawToBitmap
import pt.isec.a21280305.scratch.model.Line
import pt.isec.a21280305.scratch.model.Scratch
import pt.isec.a21280305.scratch.utils.ImageUtils

const val TAG = "DrawArea"

class DrawArea(context: Context) : View(context), GestureDetector.OnGestureListener {
    companion object {
        const val TAG_DA = "Draw Area"
    }

    private var scratch : Scratch = Scratch("No name", arrayListOf(Line(Path(), Color.BLACK)))

    private val draw : ArrayList<Line>
        get() = scratch.lines

    private val atualPath: Path
        get() = draw.last().path

    constructor(context: Context, scratch: Scratch) : this(context){
        this.scratch = scratch
        setBackgroundColor(scratch.bkgColor)
        scratch.bkgImage?.run {
            ImageUtils.setPic(this@DrawArea, this)
        }
    }

    private val paint = Paint(Paint.DITHER_FLAG or Paint.ANTI_ALIAS_FLAG).apply{
        color = Color.BLACK
        strokeWidth = 4f
        style = Paint.Style.FILL_AND_STROKE
    }

    var lineColor : Int = Color.BLACK
        set(value){
            field = value
            scratch.addLine(value)
        }

    val preview : Bitmap
        get() = this.drawToBitmap()

    private val gestureDetector : GestureDetector by lazy { GestureDetector(context, this) }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas?.drawLine(50f, 50f, 300f, 300f, paint)
        //canvas?.drawPath(path, paint)
        for(line in draw)
            canvas?.drawPath(line.path, paint.apply{color = line.color})
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (gestureDetector.onTouchEvent(event)) {
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.i(TAG,"onDown: ${e!!.x} ${e!!.y}")
        atualPath.moveTo(e.x, e.y)
        return true
    }

    override fun onShowPress(e: MotionEvent?) {
        Log.i(TAG,"onShowPress: ${e!!.x} ${e!!.y}")
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.i(TAG,"onSingleTapUp: ${e!!.x} ${e!!.y}")
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        atualPath.lineTo(e2!!.x, e2.y)
        atualPath.moveTo(e2.x, e2.y)
        invalidate()
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.i(TAG,"onLongPress: ${e!!.x} ${e!!.y}")
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.i(TAG,"onFling: ${e2!!.x} ${e2!!.y}")
        return false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        scratch.bkgImage?.run {
            ImageUtils.setPic(this@DrawArea, this)
        }
    }
}