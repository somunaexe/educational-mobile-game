package com.example.project_652263

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

class FourthFragmentSurfaceView(context: Context?, attrs: AttributeSet?) : MySurfaceView(context, attrs), Runnable {
    private var myGameObjects = ConcurrentHashMap<Int, GameObject>() //Stores the game objects/asteroids
    private var asteroidImage = context!!.resources.getDrawable(R.drawable.asteroid, null)//Drawable/Image of the asteroid to be avoided by the player
    private var gameOverImage = context!!.resources.getDrawable(R.drawable.game_over, null) //Drawable/Image of the game over UI displayed at the end of the game
    private var addingAsteroid = false //Can add an asteroid to the canvas when false vice-versa
    private var spaceshipImage = context!!.resources.getDrawable(R.drawable.spaceship, null) //Drawable/Image of the spaceship the player controls
    var spaceshipSprite = FourthFragmentGameObject(400, 1200, 10, 10, spaceshipImage) //Game object of the spaceship

    //Approximate gravities of various celestial bodies, planets, and/or moons
	private val gravities = listOf(
    listOf("mercury", 3),
    listOf("earth", 9),
    listOf("mars", 4))

    var asteroidDy: Int = gravities[0][1] as Int //Gravity/speed of the asteroids. The gravity is set to 9 to represent earths gravity

    /**
     * Runs before the first frame and initializes the field variables
     * @param none
     * @return none
     */
    init {
		myThread = Thread(this) //Points the thread to this class
        myThread.start()
        myHolder = holder
		paint.color = Color.WHITE
    }

    fun changeGravity(planetPosition: Int) {
        asteroidDy = gravities[planetPosition][1] as Int
    }

    /**
     * Adds new asteroids to the surface view
     * @param Int spawnX: The position where the asteroid spawns on the x-axis
     * @return none
     */
    private suspend fun startAsteroidCycle(spawnX: Int){
        withContext(Dispatchers.Main) {
            var asteroid = FourthFragmentObstacle(spawnX, 1, 5, asteroidDy, asteroidImage) //Obstacles the spaceship must avoid
            myGameObjects[myGameObjects.size] = asteroid //Adds the asteroid game object to the hash map to enable it to move every frame so it can be drawn and seen

            delay(5000)//Wait for 5 seconds/5000 milliseconds
            addingAsteroid = false//Asteroids can be added to the canvas again
        }
    }

    /**
     * Runs every frame
     * @param none
     * @return none
     */
    override fun run() {
        while (isRunning) {
            if (!myHolder.surface.isValid) continue
            val canvas: Canvas = myHolder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint)

            for ((key, asteroid) in myGameObjects) {
                asteroid.dy = asteroidDy
                asteroid.move(canvas)
                if (canvas.height - asteroid.y <= 0) {
                    myGameObjects.remove(key)
                }
                if (Rect.intersects(spaceshipSprite.rectangle, asteroid.rectangle)) {
                    stop()
                    spaceshipSprite.image = gameOverImage
                }
            }
            if(!addingAsteroid) {
                addingAsteroid = true
                CoroutineScope(Dispatchers.Main).launch {
                    var randomX = Random.nextInt(0, canvas.width - 201)
                    startAsteroidCycle(randomX) //Adds new asteroids to the canvas
                }
            }
            myHolder.unlockCanvasAndPost(canvas)
        }
    }
}