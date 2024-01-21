package com.example.project_652263

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.WHITE
import android.graphics.Paint
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

open class MySurfaceView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs), Runnable {
    var isRunning = true
    var myThread: Thread
    var myHolder: SurfaceHolder
    var paint = Paint()

	//Runs before the first frame and initializes field variables
    init {
        myThread = Thread(this)
        myThread.start()
        myHolder = holder
		paint.color = WHITE
    }

	//Starts the thread
    open fun start() {
        isRunning = true
        myThread = Thread(this)
        myThread.start()
    }

	//Pauses the thread
    open fun stop() {
        isRunning = false;
        while (true) {
            try {
                myThread.join()
                break
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            break
        }
    }

    override fun run() {
        while (isRunning) {
            if (!myHolder.surface.isValid) continue
            val canvas: Canvas = myHolder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)
            myHolder.unlockCanvasAndPost(canvas)
        }
    }
}