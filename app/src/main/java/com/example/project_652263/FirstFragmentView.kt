package com.example.project_652263

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class FirstFragmentView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var paint = Paint() //Paint to be used on the canvas
    var myX: Float = 100f 
    var myY: Float = 100f
    lateinit var myBitmap: Bitmap
    lateinit var myBitmapCanvas: Canvas //Canvas to be draw on
    var myWidth: Int = 800 //Width of the canvas 
    var myHeight: Int = 800 //Height of the canvas

	//Runs before the first frame and initializes field variables
    init {
        paint.color = Color.RED //Sets the paint color to red
        paint.textSize = 80f //Sets the text size of the paint to 80f
    }

    override fun onDrawForeground(canvas: Canvas) {
        super.onDrawForeground(canvas)
        canvas!!.drawText("Hello from canvas", myX, myY, paint) 
        myBitmapCanvas.drawCircle(myX, myY, 10f, paint) //Draws circle on the bitmap canvas
        canvas!!.drawBitmap(myBitmap, 0f, 0f, null) //Draws on the canvas
    }

	/**
	* Runs if the screen is touched
	* @param MotionEvent Touch event
	* @return Boolean true/false
	*/
    override fun onFilterTouchEventForSecurity(event: MotionEvent?): Boolean {
        //return super.onFilterTouchEventForSecurity(event)
        myX = event!!.x //Point that was touched on the x-axis
        myY = event!!.y //Point that was touched on the y-axis
        invalidate()
        return true //Returns true
    }

	/**
	* Measures size of the canvas
	* @param Int widthMeasureSpec Width of canvas
	* @param Int heightMeasureSpec Height of canvas
	*/
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        myWidth = measuredWidth
        myHeight = measuredHeight
        myBitmap = Bitmap.createBitmap(myWidth, myHeight, Bitmap.Config.RGB_565)
        myBitmapCanvas = Canvas(myBitmap)
    }
}