package com.example.project_652263

import android.graphics.Canvas
import android.graphics.drawable.Drawable

class ThirdFragmentGameObject (x:Int, y:Int, dx:Int, dy:Int, image: Drawable): GameObject(x, y, dx, dy, image){
    var px: Int = 400 //Initial position the frog should move to on the x-axis
    var py: Int = 500 //Initial position the frog should move to on the y-axis

	/**
	* Moves the frog to the px,py coordinate
	* @param Canvas Canvas being drawn on
	* @return none
	*/
    override fun move(canvas: Canvas) {
        if(px > x) x += 5 else x -= 5 //Moves the frog on the x-axis towards px
        if(py > y) y += 5 else y -= 5 //Moves the frog on the y-axis towards py
        
        rectangle.set(x, y, x + width, y + height) //Sets the rectangle using the frogs position and size
        image.setBounds(x, y, x + width, y + height) //Sets the image bounds using the frogs position and size
        image.draw(canvas) //Draws the image on the canvas
    }
}