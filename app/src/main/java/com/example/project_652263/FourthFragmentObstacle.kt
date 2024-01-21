package com.example.project_652263

import android.graphics.Canvas
import android.graphics.drawable.Drawable

class FourthFragmentObstacle (x:Int, y:Int, dx:Int, dy:Int, image: Drawable): GameObject(x, y, dx, dy, image) {

    /**
     * Moves the game object on the canvas
     * @param Canvas Canvas being drawn on
     * @return none
     */
    override fun move(canvas: Canvas) {
        y += dy //Moves the game object on the y-axis
        rectangle.set(x, y, x + width, y + height) //Sets the rectangle based on the position and size
        image.setBounds(x, y, x + width, y + height) //Sets the image based on the position and size
        image.draw(canvas) //Draws the image on the canvas
    }
}