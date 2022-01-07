package com.dev.tttonlinemultiplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dev.tttonlinemultiplayer.databinding.FragmentCodeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CodeFragment : Fragment() {
    private var _binding : FragmentCodeBinding? = null

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
                val action = CodeFragmentDirections.actionCodeFragmentToMenuFragment()
                findNavController().navigate(action)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        _binding = FragmentCodeBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).frag=3

        binding.progressbar.visibility= View.GONE

        binding.btncreate.setOnClickListener {
            (activity as MainActivity).maker = true

            (activity as MainActivity).playcode = binding.code.getText().toString()

            binding.btncreate.visibility = View.GONE
            binding.btnjoin.visibility = View.GONE
            binding.code.visibility = View.GONE
            //binding.title.visibility = View.GONE
            binding.progressbar.visibility = View.VISIBLE

            FirebaseDatabase.getInstance().reference.child("Code")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        var check = isValueAvailable(snapshot, (activity as MainActivity).playcode)
                        /*val post = snapshot.getValue()
                        var sample = post.toString()
                        Toast.makeText(this@Code, sample, Toast.LENGTH_LONG).show()*/

                        if (check == true) {

                            binding.btncreate.visibility = View.VISIBLE
                            binding.btnjoin.visibility = View.VISIBLE
                            binding.code.visibility = View.VISIBLE
                            //binding.title.visibility = View.VISIBLE
                            binding.progressbar.visibility = View.GONE

                            if((activity as MainActivity).gotcode == false){
                                Toast.makeText(requireActivity(),"Code Already Available", Toast.LENGTH_LONG).show()
                            }


                        } else {
                            FirebaseDatabase.getInstance().reference.child("Code").push()
                                .setValue((activity as MainActivity).playcode)
                            (activity as MainActivity).checktemp = false
                            isValueAvailable(snapshot, (activity as MainActivity).playcode)
                            (activity as MainActivity).gotcode = true
                            play()

                        }
                    }

                })
        }
        binding.btnjoin.setOnClickListener {
            (activity as MainActivity).maker = false
            (activity as MainActivity).playcode = binding.code.text.toString()

            binding.btncreate.visibility = View.GONE
            binding.btnjoin.visibility = View.GONE
            binding.code.visibility = View.GONE
            //binding.title.visibility = View.GONE
            binding.progressbar.visibility = View.VISIBLE

            FirebaseDatabase.getInstance().getReference("Code")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {

                        var data: Boolean = isValueAvailable(snapshot, (activity as MainActivity).playcode)
                        if (data == true) {
                            play()
                        } else {

                            binding.btncreate.visibility = View.VISIBLE
                            binding.btnjoin.visibility = View.VISIBLE
                            binding.code.visibility = View.VISIBLE
                            //binding.title.visibility = View.VISIBLE
                            binding.progressbar.visibility = View.GONE

                            Toast.makeText(requireActivity(),"Invalid Code", Toast.LENGTH_LONG).show()
                        }
                    }

                })
        }

    }

    private fun play() {
        Toast.makeText(requireActivity(),(activity as MainActivity).maker.toString(), Toast.LENGTH_LONG).show()
        val action = CodeFragmentDirections.actionCodeFragmentToGameOnlineFragment()
        this.findNavController().navigate(action)
    }

    private fun isValueAvailable(snapshot: DataSnapshot, code: String): Boolean {
        var data = snapshot.children
        data.forEach {
            var value = it.value

            if (value == code) {
                (activity as MainActivity).keyvalue = it.key!!
                return true
            }
        }
        return false
    }





}