package com.example.project_652263

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

/**
* This class handles the surface view displayed in the third blank fragment.
* It extends/subclasses the Surface View class and handles multiple threads
*/
class ThirdFragmentSurfaceView(context: Context?, attrs: AttributeSet?) : MySurfaceView(context, attrs), Runnable {

	private var canMate = true //The male and female frog can mate and lay an egg
    private var myGameObjects = ConcurrentHashMap<Int, GameObject>() //Stores the game objects before they have grown into female frogs
	private var femaleFrogObjects = ConcurrentHashMap<Int, GameObject>() //Stores the game object that grown into female frogs
    private var maleFrogObjects = ConcurrentHashMap<Int, GameObject>() //Stores the game object that grown into female frogs
    private val frogEggImage = context!!.resources.getDrawable(R.drawable.frog_egg, null) //Stores the image of frog eggs
    private val tadpoleImage = context!!.resources.getDrawable(R.drawable.tadpole, null) //Stores the image of tadpoles
    private val femaleFrogImage = context!!.resources.getDrawable(R.drawable.female_frog, null) //Stores the image of female frogs
	private val maleFrogImage = context!!.resources.getDrawable(R.drawable.male_frog, null) //Stores the image of male frogs
    private val maleFrogSprite = ThirdFragmentGameObject(400, 500, 7, 7, maleFrogImage) //Stores the game object of the male frogs

	/**
	* Runs before the first frame and initializes the field variables
	* @param none
	* @return none
	*/
    init {
        myThread = Thread(this) //Points the thread to this class
        myThread.start() //Starts the thread
        myHolder = holder //Initializes the holder
        val femaleFrogSprite = GameObject(100, 180, 5, 5, femaleFrogImage) //Stores the game object of the first female frog
        paint.color = Color.BLUE //Sets the paint color to white
        Log.d("femaleFrogObjects.size", "size") //Logs the number of female frogs in the concurrent hash map
        femaleFrogObjects[femaleFrogObjects.size] = femaleFrogSprite //Adds the female frog to the hash map for female frogs
        maleFrogObjects[maleFrogObjects.size] = maleFrogSprite //Adds the female frog to the hash map for female frogs
    }

	/**
	* Runs when the screen is touched
	* @param MotionEvent Touch event
	* return Boolean true/false
	*/
    override fun onFilterTouchEventForSecurity(event: MotionEvent?): Boolean {
        maleFrogSprite.px = event!!.x.toInt() //Makes the male frog move to the position touched on the x-axis
        maleFrogSprite.py = event!!.y.toInt() //Makes the male frog move to the position touched on the y-axis
        return true
    }
	
	/**
	* Gives birth to baby frogs and adds them to the surface view
	* @param Int birthX: The position where the frogs mated on the x-axis
	* @param Int birthY: The position where the frogs mated on the y-axis
	* @return none
     */
    private suspend fun startBirthCycle(birthX: Int, birthY: Int){
        withContext(Dispatchers.Main) {
            val babyFrog = GameObject(birthX, birthY, 5, 5, frogEggImage) //Adds a frog egg game object to the surface view
            myGameObjects[myGameObjects.size] = babyFrog //Adds the frog egg game object to the hash map to enable it to move every frame so it can be drawn and seen
            
			//The egg hatches into a tadpole after 5 seconds/5000 milliseconds
			delay(5000)
            babyFrog.image = tadpoleImage


            //The tadpole grows into a female frog after 5 seconds/5000 milliseconds
            delay(5000)
            var randomGender = Random.nextInt(0, 2)

            if (randomGender == 0) {
                babyFrog.image =  maleFrogImage
                maleFrogObjects[maleFrogObjects.size] = babyFrog //Adds the female frog to the female frog hash map so it can mate upon intersection with male frogs
            } else {
                babyFrog.image = femaleFrogImage
                femaleFrogObjects[femaleFrogObjects.size] = babyFrog //Adds the female frog to the female frog hash map so it can mate upon intersection with male frogs
            }

            myGameObjects.remove(myGameObjects.size) //Removes the female frog from the my game objects hash map since its move function is already called in the female frog hash map
            canMate = true //The male frog can mate now
        }
    }

	/**
	* Runs every frame
	* @param none
	* @return none
	*/
    override fun run() {
		while (isRunning) {
            if(!myHolder.surface.isValid) continue
            val canvas: Canvas = myHolder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat(), paint) //Places a canvas using the passed parameters

            maleFrogSprite.move(canvas) //Moves the male frog
            myGameObjects.forEach{(keys, gameObject) -> gameObject.move(canvas)} //Moves the baby frogs
            maleFrogObjects.forEach{(keys, gameObject) -> gameObject.move(canvas)}
            for ((key, femaleFrog) in femaleFrogObjects) {
                femaleFrog.move(canvas) //Moves the female frogs

                for ((key, maleFrog) in maleFrogObjects) {
                    //Runs if the male frog is trying to mate with a female frog and is allowed to
                    if (Rect.intersects(maleFrog.rectangle, femaleFrog.rectangle) && canMate) {
                        canMate = false //Male frog can not mate

                        Log.d("as", "intersected$canMate")

                        //Retrieve the best birth position on the x-axis based off the intersection position
                        var birthXParam = if(canvas.width - femaleFrog.x >=  400) {
                            femaleFrog.x + femaleFrog.width
                        } else {
                            femaleFrog.x - femaleFrog.width;
                        }

                        //Retrieve the best birth position on the y-axis based off the intersection position
                        var birthYParam = if(canvas.height - femaleFrog.y >=  400) {
                            femaleFrog.y + femaleFrog.width
                        } else {
                            femaleFrog.y - femaleFrog.width
                        }
                        CoroutineScope(Dispatchers.Main).launch {
                            startBirthCycle(birthX = birthXParam, birthY = birthYParam) //Gives birth to a new frog
                        }
                    }
                }
            }
            myHolder.unlockCanvasAndPost(canvas)
        }
    }
}