package com.dev.tttonlinemultiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dev.tttonlinemultiplayer.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding : FragmentMenuBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = MenuFragmentDirections.actionMenuFragmentSelf()
                findNavController().navigate(action)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)


        _binding = FragmentMenuBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).frag=1

        binding.btnsingle.setOnClickListener {

            binding.mainmenu.visibility = View.GONE
            binding.singleplayer.visibility = View.VISIBLE

        }

        binding.btnmultiplayer.setOnClickListener {

            binding.mainmenu.visibility = View.GONE
            binding.multiplayer.visibility = View.VISIBLE

        }

        binding.btneasy.setOnClickListener { playgame(1) }
        binding.btnmedium.setOnClickListener { playgame(2) }
        binding.btnhard.setOnClickListener { playgame(3) }
        binding.btnonline.setOnClickListener { playgame(4) }
        binding.btnoffline.setOnClickListener { playgame(5) }

    }

    private fun playgame(i: Int) {

        if(i==4){
            val action = MenuFragmentDirections.actionMenuFragmentToCodeFragment()
            this.findNavController().navigate(action)

        }
        else{
            val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(i.toString())
            this.findNavController().navigate(action)


        }



    }





}