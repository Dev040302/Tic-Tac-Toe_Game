package com.dev.tttonlinemultiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dev.tttonlinemultiplayer.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding : FragmentResultBinding? = null

    private val binding get() = _binding!!

    lateinit var result:String
    lateinit var type:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)



        arguments?.let{
            type = it.getString("TYPE").toString()
            result = it.getString("RESULT").toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                (activity as MainActivity).showad()
                returnback()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        _binding = FragmentResultBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).frag=5

        if(type == "1" || type == "2" || type == "3"){

            binding.multiplayer.visibility = View.GONE
            showresult(type)


        }

        if(type == "4"|| type=="5"){

            binding.singleplayer.visibility = View.GONE
            showresult(type)

        }
        binding.btnrestart.setOnClickListener {
            (activity as MainActivity).showad()
            returnback()

        }

    }

    fun returnback() {



        (activity as MainActivity).reset()

        if(type=="4"){
            val action = ResultFragmentDirections.actionResultFragmentToCodeFragment()
            this.findNavController().navigate(action)
        }
        else {
            val action = ResultFragmentDirections.actionResultFragmentToGameFragment(type)
            this.findNavController().navigate(action)
        }
    }


    private fun showresult(type: String) {
        if(type == "1" || type == "2" || type == "3"){

            if(result=="x"){
                (activity as MainActivity).result(1)
                binding.result.setText("You")
            }
            if(result == "o"){
                (activity as MainActivity).result(0)
                binding.result.setText("Computer")
            }
            if(result == "draw"){
                draw()
            }

        }
        if(type == "4"|| type=="5"){

            if(result=="x"){
                (activity as MainActivity).result(1)
                binding.imgresult.setImageResource(R.drawable.x)
            }
            if(result == "o"){
                (activity as MainActivity).result(1)
                binding.imgresult.setImageResource(R.drawable.o)
            }
            if(result == "draw"){
                draw()
            }

        }
    }

    private fun draw() {

        (activity as MainActivity).result(0)
        binding.draw.visibility = View.VISIBLE
        binding.multiplayer.visibility = View.GONE
        binding.singleplayer.visibility = View.GONE

    }


}