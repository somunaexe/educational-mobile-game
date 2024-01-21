package com.example.project_652263

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable

open class GameObject (var x:Int, var y:Int, var dx:Int, var dy:Int, var image:Drawable) {
    var width: Int = 200 //Width of the game object
    var height: Int = 200 //Height of the game object
    val rectangle = Rect(x, y, x + width, y + height) //Rectangle/outline of the game object

	/**
	* Moves the game object on the canvas
	* @param Canvas Canvas being drawn on
	* @return none
	*/
    open fun move(canvas: Canvas) {
        x += dx //Moves the game object on the x-axis
        y += dy //Moves the game object on the y-axis

        if(x > (canvas.width - width) || x < 0) dx = -dx //Changes the game object movement algorithm on the x-axis
        if (y > (canvas.height - height) || y < 0) dy = -dy //Changes the game object movement algorithm on the y-axis
		
        rectangle.set(x, y, x + width, y + height) //Sets the rectangle based on the position and size
        image.setBounds(x, y, x + width, y + height) //Sets the image based on the position and size
        image.draw(canvas) //Draws the image on the canvas
    }
}