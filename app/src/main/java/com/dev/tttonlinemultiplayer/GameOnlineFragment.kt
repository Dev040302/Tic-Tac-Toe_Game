package com.dev.tttonlinemultiplayer

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dev.tttonlinemultiplayer.databinding.FragmentGameOnlineBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class GameOnlineFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentGameOnlineBinding? = null

    private val binding get() = _binding!!



    lateinit var imgdata: ImageView
    var number = 5

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
                val action = GameOnlineFragmentDirections.actionGameOnlineFragmentToMenuFragment()
                findNavController().navigate(action)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        _binding = FragmentGameOnlineBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).frag=4

        for(i in 1..9)
        {

            var img : ImageView
            img = when(i){
                1 -> view.findViewById<ImageView>(R.id.imageView0)
                2 -> view.findViewById<ImageView>(R.id.imageView1)
                3 -> view.findViewById<ImageView>(R.id.imageView2)
                4 -> view.findViewById<ImageView>(R.id.imageView3)
                5 -> view.findViewById<ImageView>(R.id.imageView4)
                6 -> view.findViewById<ImageView>(R.id.imageView5)
                7 -> view.findViewById<ImageView>(R.id.imageView6)
                8 -> view.findViewById<ImageView>(R.id.imageView7)
                9 -> view.findViewById<ImageView>(R.id.imageView8)
                else -> {view.findViewById<ImageView>(R.id.imageView0)}
            }
            img.isEnabled = true
            img.setImageResource(0)

            //startActivity(Intent(this,ThirdPage::class.java))
            if((activity as MainActivity).maker){
                FirebaseDatabase.getInstance().reference.child("data").child((activity as MainActivity).playcode).removeValue()
            }


        }

        var status: TextView =view.findViewById<TextView>(R.id.status)
        status.text = "Creator's Turn"

        FirebaseDatabase.getInstance().reference.child("data").child((activity as MainActivity).playcode)
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {



                    var data = snapshot.value
                    if ((activity as MainActivity).maker) {
                        (activity as MainActivity).maker = false
                        moveonline(data.toString(), (activity as MainActivity).maker)
                    } else {
                        (activity as MainActivity).maker = true
                        moveonline(data.toString(),(activity as MainActivity).maker)
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    number=7
                }

            })

        binding.imageView0.setOnClickListener(this)
        binding.imageView1.setOnClickListener(this)
        binding.imageView2.setOnClickListener(this)
        binding.imageView3.setOnClickListener(this)
        binding.imageView4.setOnClickListener(this)
        binding.imageView5.setOnClickListener(this)
        binding.imageView6.setOnClickListener(this)
        binding.imageView7.setOnClickListener(this)
        binding.imageView8.setOnClickListener(this)

    }

    override fun onClick(view: View?) {

        if ((activity as MainActivity).maker) {

            val img = view as ImageView
            var cellOnline = 0
            when (img.id) {
                R.id.imageView0 -> cellOnline = 1
                R.id.imageView1 -> cellOnline = 2
                R.id.imageView2 -> cellOnline = 3
                R.id.imageView3 -> cellOnline = 4
                R.id.imageView4 -> cellOnline = 5
                R.id.imageView5 -> cellOnline = 6
                R.id.imageView6 -> cellOnline = 7
                R.id.imageView7 -> cellOnline = 8
                R.id.imageView8 -> cellOnline = 9
                else -> {
                    cellOnline = 0
                }

            }
            var playerTurn = false;
            Handler().postDelayed(Runnable { playerTurn = true }, 600)
            playnow(img, cellOnline)
            updateDatabase(cellOnline);



        } else {
            Toast.makeText(requireActivity(), "Wait for your turn", Toast.LENGTH_LONG).show()
        }
    }

    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    fun playnow(img: ImageView, currCell: Int) {

        img.setImageResource(R.drawable.x)
        emptyCells.remove(currCell)
        var status: TextView =view?.findViewById<TextView>(R.id.status)!!
        status.text = "Opponent's Turn"
        player1.add(currCell)
        emptyCells.add(currCell)
        //Handler().postDelayed(Runnable { audio.pause() } , 500)
        img.isEnabled = false
        checkwinner()
    }

    fun moveonline(data: String, move: Boolean) {



        if (move) {
            imgdata = when (data.toInt()) {
                1 -> view?.findViewById<ImageView>(R.id.imageView0)!!
                2 -> view?.findViewById<ImageView>(R.id.imageView1)!!
                3 -> view?.findViewById<ImageView>(R.id.imageView2)!!
                4 -> view?.findViewById<ImageView>(R.id.imageView3)!!
                5 -> view?.findViewById<ImageView>(R.id.imageView4)!!
                6 -> view?.findViewById<ImageView>(R.id.imageView5)!!
                7 -> view?.findViewById<ImageView>(R.id.imageView6)!!
                8 -> view?.findViewById<ImageView>(R.id.imageView7)!!
                9 -> view?.findViewById<ImageView>(R.id.imageView8)!!
                else -> {
                    view?.findViewById<ImageView>(R.id.imageView0)!!
                }
            }
            imgdata.setImageResource(R.drawable.o)
            var status: TextView =view?.findViewById<TextView>(R.id.status)!!
            status.text = "Your Turn"
            player2.add(data.toInt())
            emptyCells.add(data.toInt())
            imgdata.isEnabled = false
            checkwinner()
        }
    }

    fun updateDatabase(cellId: Int) {

        FirebaseDatabase.getInstance().reference.child("data").child((activity as MainActivity).playcode).push().setValue(cellId);
    }

    fun checkwinner() {





        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) || (player1.contains(
                1
            ) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) || (player1.contains(
                7
            ) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) || (player1.contains(
                1
            ) && player1.contains(5) && player1.contains(9)) ||
            player1.contains(3) && player1.contains(5) && player1.contains(7) || (player1.contains(2) && player1.contains(
                5
            ) && player1.contains(8))
        ) {

            /*val intent = Intent(this@Gameonline,com.dev.tttonlinemultiplayer.Result::class.java)
            val result = "x"
            intent.putExtra("Result", result)
            intent.putExtra("Type","4")
            startActivity(intent)
            reset()*/

            val action = GameOnlineFragmentDirections.actionGameOnlineFragmentToResultFragment("4","x")
            this.findNavController().navigate(action)


        } else if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) || (player2.contains(
                1
            ) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) || (player2.contains(
                7
            ) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) || (player2.contains(
                1
            ) && player2.contains(5) && player2.contains(9)) ||
            player2.contains(3) && player2.contains(5) && player2.contains(7) || (player2.contains(2) && player2.contains(
                5
            ) && player2.contains(8))
        ) {
            /* val intent = Intent(this@Gameonline,com.dev.tttonlinemultiplayer.Result::class.java)
             val result = "o"
             intent.putExtra("Result", result)
             intent.putExtra("Type","4")
             startActivity(intent)
             reset()*/

            val action = GameOnlineFragmentDirections.actionGameOnlineFragmentToResultFragment("4","o")
            this.findNavController().navigate(action)

        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4
            ) && emptyCells.contains(5) && emptyCells.contains(6) && emptyCells.contains(7) &&
            emptyCells.contains(8) && emptyCells.contains(9)
        ) {
            /*val intent = Intent(this@Gameonline,com.dev.tttonlinemultiplayer.Result::class.java)
            val result = "draw"
            intent.putExtra("Result", result)
            intent.putExtra("Type","4")
            startActivity(intent)
            reset()*/

            val action = GameOnlineFragmentDirections.actionGameOnlineFragmentToResultFragment("4","draw")
            this.findNavController().navigate(action)
        }

    }

    fun reset()
    {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1;
        for(i in 1..9)
        {
            var img : ImageView
            img = when(i){
                1 -> view?.findViewById<ImageView>(R.id.imageView0)!!
                2 -> view?.findViewById<ImageView>(R.id.imageView1)!!
                3 -> view?.findViewById<ImageView>(R.id.imageView2)!!
                4 -> view?.findViewById<ImageView>(R.id.imageView3)!!
                5 -> view?.findViewById<ImageView>(R.id.imageView4)!!
                6 -> view?.findViewById<ImageView>(R.id.imageView5)!!
                7 -> view?.findViewById<ImageView>(R.id.imageView6)!!
                8 -> view?.findViewById<ImageView>(R.id.imageView7)!!
                9 -> view?.findViewById<ImageView>(R.id.imageView8)!!
                else -> {view?.findViewById<ImageView>(R.id.imageView0)!!}
            }
            img.isEnabled = true
            img.setImageResource(0)

            //startActivity(Intent(this,ThirdPage::class.java))
            if((activity as MainActivity).maker){
                FirebaseDatabase.getInstance().reference.child("data").child((activity as MainActivity).playcode).removeValue()
            }


        }
    }

    fun removeCode(){
        if((activity as MainActivity).maker){
            FirebaseDatabase.getInstance().reference.child("codes").child((activity as MainActivity).keyvalue).removeValue()
        }
    }


}