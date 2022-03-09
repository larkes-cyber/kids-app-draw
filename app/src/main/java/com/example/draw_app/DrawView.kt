package com.example.draw_app

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawView(context:Context, attr:AttributeSet ):View(context, attr) {

    private var mDrawPath:CustomPath? = null;
    private var mCanvasBitmap: Bitmap? = null
    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null
    private val mPaths = ArrayList<CustomPath>();

    private var mBrushSize: Float = 0.toFloat()
    private var color = Color.BLACK

    private var canvas: Canvas? = null

    init{
        setUpWholeComponents();
    }

    private fun setUpWholeComponents(){
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas);

        canvas!!.drawBitmap(mCanvasBitmap!!, 0f, 0f, mCanvasPaint)

        for(path in mPaths){
            mDrawPaint!!.strokeWidth = path.Size
            mDrawPaint!!.color = path.Color
            canvas.drawPath(path, mDrawPaint!!)
        }

        if (!mDrawPath!!.isEmpty) {
            mDrawPaint!!.strokeWidth = mDrawPath!!.Size
            mDrawPaint!!.color = mDrawPath!!.Color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDrawPath!!.Color = color
                mDrawPath!!.Size = mBrushSize

                mDrawPath!!.reset()
                mDrawPath!!.moveTo(
                    touchX,
                    touchY
                )
            }

            MotionEvent.ACTION_MOVE -> {
                mDrawPath!!.lineTo(
                    touchX,
                    touchY
                )
            }

            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!);
                mDrawPath = CustomPath(color, mBrushSize)
            }
            else -> return false
        }

        invalidate()
        return true
    }
      fun onChangeBrushSize(newSize:Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            newSize,
            resources.displayMetrics);
        mDrawPaint!!.strokeWidth = mBrushSize;
     }



    interface inner class CustomPath(var Color:Int, var Size:Float): Path() {

    }
}