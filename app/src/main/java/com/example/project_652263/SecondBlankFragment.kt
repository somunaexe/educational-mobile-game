package com.example.project_652263

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.project_652263.databinding.FragmentSecondBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondBlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondBlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentSecondBlankBinding //Binds the UI elements to this class
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
        binding = FragmentSecondBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Retrieve navigation controller

        val audioAttributes: AudioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(
		AudioAttributes.CONTENT_TYPE_SONIFICATION).build() //Audio attributes of the sound player
        val soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build() //The sound player
        val buttonClick = soundPool.load(requireActivity(),R.raw.button_click,1) //Button click sound
        val grassSound = soundPool.load(requireActivity(), R.raw.grass_sound, 1) //Grass moving sound
        val ratSound = soundPool.load(requireActivity(), R.raw.rat_sound, 1) //Rat squeaking sound
        val snakeSound = soundPool.load(requireActivity(),R.raw.snake_sound,1) //Snake hissing sound
        val hawkSound = soundPool.load(requireActivity(),R.raw.hawk_sound,1) //Hawk crying sound

		//Runs when the sun image button is clicked
        binding.sunButton.setOnClickListener {
            binding.imageView2.visibility = View.VISIBLE //Arrow pointing to grass image button becomes visible
            binding.grassButton.visibility = View.VISIBLE //Grass image button becomes visible
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            soundPool.play(grassSound, 1.0f, 1.0f, 0, 0, 1.0f) //Grass moving sound plays
            binding.foodChainTextView.text = "Try and guess what eats the grass before clicking it." //Changes the narrator text to teach the user about the food chain
        }
		
		//Runs when the grass image button is clicked
        binding.grassButton.setOnClickListener {
            binding.imageView3.visibility = View.VISIBLE //Arrow pointing to rat image button becomes visible
            binding.ratButton.visibility = View.VISIBLE //Rat image button becomes visible
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            soundPool.play(ratSound, 1.0f, 1.0f, 0, 0, 1.0f) //Rat sound plays
            binding.foodChainTextView.text = "You're getting the hang of this. Think of the many predators rats have before clicking the rat." //Changes the narrator text to teach the user about the food chain 
        }
		
		//Runs when the rat image button is clicked
        binding.ratButton.setOnClickListener {
            binding.imageView4.visibility = View.VISIBLE //Arrow pointing to snake image button becomes visible
            binding.snakeButton.visibility = View.VISIBLE //Snake image button becomes visible
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            soundPool.play(snakeSound, 1.0f, 1.0f, 0, 0, 1.0f) //Snake sound plays
            binding.foodChainTextView.text = "Good job! Predators are animals that eat other animals. Click the snake to see its predator. Remember to think of all the predators it has." //Changes the narrator text to teach the user about the food chain
        }
		
		//Runs when the snake image button is clicked
        binding.snakeButton.setOnClickListener {
            binding.imageView5.visibility = View.VISIBLE //Arrow pointing to hawk image button becomes visible
            binding.hawkButton.visibility = View.VISIBLE //Hawk image button becomes visible
            binding.nextButton2.visibility = View.VISIBLE //Next button becomes visible. This button takes you to the next game/fragment
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            soundPool.play(hawkSound, 1.0f, 1.0f, 0, 0, 1.0f) //Hawk sound plays
            binding.foodChainTextView.text = "A Hawk! It's at the top of the food chain meaning no animal eats it and it eats any prey it wants." //Changes the narrator text to teach the user about the food chain
        }
		
		//Runs when the hawk image button is clicked
        binding.hawkButton.setOnClickListener {
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            soundPool.play(hawkSound, 1.0f, 1.0f, 0, 0, 1.0f) //Hawk sound plays
        }
		
		//Runs when the next button is clicked
        binding.nextButton2.setOnClickListener {
            soundPool.play(buttonClick,1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            navController.navigate(R.id.action_secondBlankFragment_to_thirdBlankFragment) //Loads the third blank fragment
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondBlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SecondBlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}