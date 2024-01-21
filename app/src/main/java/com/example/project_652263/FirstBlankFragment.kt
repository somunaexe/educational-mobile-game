package com.example.project_652263

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.project_652263.databinding.FragmentFirstBlankBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstBlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstBlankFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFirstBlankBinding //Binds the UI elements to this class
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
        //return inflater.inflate(R.layout.fragment_first_blank, container, false)
        binding = FragmentFirstBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController() //Retrieve navigation controller

        val audioAttributes: AudioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(
		AudioAttributes.CONTENT_TYPE_SONIFICATION).build() //Audio attributes of the sound pool player
        val soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build() //Sound pool player

        val buttonClick = soundPool.load(requireActivity(),R.raw.button_click,1) //Button click sound
        val sheepSound = soundPool.load(requireActivity(), R.raw.sheep_sound, 1) //Sheep bleat sound

		//Runs when the animal sound button is clicked
        binding.soundButton.setOnClickListener {
            soundPool.play(sheepSound, 1.0f, 1.0f, 0, 0, 1.0f) //Plays the sheep sound
        }

		//Runs when the next button is clicked
        binding.nextButton.setOnClickListener {
            soundPool.play(buttonClick, 1.0f, 1.0f, 0, 0, 1.0f) //Button click sound plays
            navController.navigate(R.id.action_firstBlankFragment_to_secondBlankFragment) //Loads the second blank fragment
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstBlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FirstBlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}