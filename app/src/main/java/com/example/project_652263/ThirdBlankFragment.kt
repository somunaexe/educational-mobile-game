package com.example.project_652263

import android.Manifest
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.media.AudioAttributes
import android.media.SoundPool
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project_652263.databinding.FragmentThirdBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdBlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdBlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentThirdBlankBinding//Binds the UI elements to this class
    private lateinit var navController: NavController //Navigator controller for moving between fragments

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
        //return inflater.inflate(R.layout.fragment_third_blank, container, false)
        binding = FragmentThirdBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Retrieve navigation controller
        var frogSound = MediaPlayer.create(requireActivity(), R.raw.frog_sound) //Media player to play frog sounds

        frogSound.isLooping = true //media player loops sound
        frogSound.start() // Start playing the sound
		
        val audioAttributes: AudioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(
		AudioAttributes.CONTENT_TYPE_SONIFICATION).build() //Audio attributes of sound pool player
        val soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build() //Set sound pool player
        val buttonClick = soundPool.load(requireActivity(),R.raw.button_click,1) //Button click sound

		//Runs if the next button is clicked
        binding.nextButton3.setOnClickListener {
            soundPool.play(buttonClick, 1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            navController.navigate(R.id.action_thirdBlankFragment_to_fourthBlankFragment) //Loads the fourth blank fragment
            frogSound.stop()
            frogSound.release()
        }
    }
	
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdBlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdBlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}