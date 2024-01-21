package com.example.project_652263

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.project_652263.databinding.FragmentFourthBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FourthBlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FourthBlankFragment : Fragment(), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
	private lateinit var binding: FragmentFourthBlankBinding //Binds the UI elements to this class
    private lateinit var navController: NavController //Navigator controller for moving between fragments
    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_fourth_blank, container, false)
        binding = FragmentFourthBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Retrieve navigation controller

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)

        for (s in deviceSensors) {
            Log.d("MyTAG", s.name)
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_STATUS_ACCURACY_LOW)

        val audioAttributes: AudioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(
            AudioAttributes.CONTENT_TYPE_SONIFICATION).build() //Audio attributes of sound pool player
        val soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build() //Set sound pool player
        val buttonClick = soundPool.load(requireActivity(),R.raw.button_click,1) //Button click sound

        binding.mercuryButton.setOnClickListener {
            soundPool.play(buttonClick, 1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            binding.FourthFragmentSurfaceView.changeGravity(0)
            binding.textView.text = "The asteroids are moving at mercury's gravity. Click one of the below planets to learn about their gravity."
        }

        binding.earthButton.setOnClickListener {
            soundPool.play(buttonClick, 1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            binding.FourthFragmentSurfaceView.changeGravity(1)
            binding.textView.text = "The asteroids are moving at earth's gravity. Click one of the below planets to learn about their gravity."
        }

        binding.marsButton.setOnClickListener {
            soundPool.play(buttonClick, 1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            binding.FourthFragmentSurfaceView.changeGravity(2)
            binding.textView.text = "The asteroids are moving at mars' gravity. Click one of the below planets to learn about their gravity."
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FourthBlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FourthBlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            var x = event.values[0]
            var y = event.values[1]
            var z = event.values[2]

            var mySpaceship = "%.2f".format(x) + "%.2f".format(y) + "%.2f".format(z)
            Log.d("MyTAG", mySpaceship)

            var w = 0
            var h = 0
            binding.FourthFragmentSurfaceView.spaceshipSprite.move(x.toInt(),z.toInt())
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}