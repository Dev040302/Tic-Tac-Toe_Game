package com.dev.tttonlinemultiplayer

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.dev.tttonlinemultiplayer.databinding.FragmentGameBinding


class GameFragment : Fragment(), View.OnClickListener {

    private var _binding : FragmentGameBinding? = null

    private val binding get() = _binding!!



    var gameActive: Boolean = true
    var cani:Boolean = true
    var activePlayer = 0
    var gameState = arrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    var counter = 0
    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        arguments?.let{
            type = it.getString("TYPE").toString()
        }




    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val callback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val action = GameFragmentDirections.actionGameFragmentToMenuFragment()
                findNavController().navigate(action)
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        _binding = FragmentGameBinding.inflate(inflater,container,false)
        val view = binding.root
        return view



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as MainActivity).frag=2

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


        if(type == "5"){
            val img: ImageView = view as ImageView
            val tappedImage = Integer.parseInt(img.tag.toString())

            if (!gameActive) {

            }

            if (gameState[tappedImage] == 2) {
                counter++

                if (counter == 9) {
                    gameActive = false
                }

                gameState[tappedImage] = activePlayer

                if (activePlayer == 0) {
                    img.setImageResource(R.drawable.x)
                    activePlayer = 1
                    //var status: TextView = findViewById(R.id.status)
                    binding.status.text = "O's TURN"
                } else {
                    img.setImageResource(R.drawable.o)
                    activePlayer = 0
                    //var status: TextView = findViewById(R.id.status)
                    binding.status.text = "X's TURN"
                }

            }
            var flag = 0
            var winPosition = winPositions
            for (winPosition in winPositions) {
                if (
                    gameState[winPosition[0]] == gameState[winPosition[1]]
                    && gameState[winPosition[1]] == gameState[winPosition[2]]
                    && gameState[winPosition[0]] != 2
                ) {
                    flag = 1
                    var winner: String
                    gameActive = false
                    var message = "0"
                    if (gameState[winPosition[0]] == 0) {
                        winner = "X has won"
                        message = "x"
                    } else {
                        winner = "O has won"
                        message = "o"
                    }
                    //var status: TextView = findViewById(R.id.status)
                    binding.status.text = winner
                    /*val intent = Intent(this@Game, Result::class.java)
                    val result = message
                    intent.putExtra("Result", result)
                    intent.putExtra("Type", type)
                    startActivity(intent)
                    finish()*/
                    val action = GameFragmentDirections.actionGameFragmentToResultFragment(type,message)
                    this.findNavController().navigate(action)

                }

            }
            if (counter == 9 && flag == 0) {
                //var status: TextView = findViewById(R.id.status)
                binding.status.text = "Match Draw"
                /*val intent = Intent(this@Game, Result::class.java)
                intent.putExtra("Result","draw")
                intent.putExtra("Type", type)
                startActivity(intent)
                finish()*/
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(type,"draw")
                this.findNavController().navigate(action)
            }
        }
        else{

            if(cani){
                cani=false
                val img: ImageView = view as ImageView
                val tappedImage = Integer.parseInt(img.tag.toString())
                activePlayer =1

                if (gameState[tappedImage] == 2) {
                    counter++

                    if (counter == 9) {
                        gameActive = false
                    }

                    gameState[tappedImage] = activePlayer

                    img.setImageResource(R.drawable.x)
                    activePlayer = 0
                    //var status: TextView = findViewById(R.id.status)
                    binding.status.text = "Computers's TURN"
                    gamewin()
                }
            }

            else{
                //Toast.makeText(this, "Wait for Your Turn", Toast.LENGTH_LONG).show()
                Toast.makeText(requireActivity(),"Wait for you Turn", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun gamewin() {
        var end=false
        var flag = 0
        var winPosition = winPositions
        for (winPosition in winPositions) {
            if (
                gameState[winPosition[0]] == gameState[winPosition[1]]
                && gameState[winPosition[1]] == gameState[winPosition[2]]
                && gameState[winPosition[0]] != 2
            ) {
                end=true
                flag = 1
                var winner: String
                gameActive = false
                var message = "0"
                if (gameState[winPosition[0]] == 1) {
                    winner = "X has won"
                    message = "x"
                } else {
                    winner = "O has won"
                    message = "o"
                }
                binding.status.text = winner
                /*val intent = Intent(this@Game, Result::class.java)
                val result = message
                intent.putExtra("Result", result)
                intent.putExtra("Type",type)
                startActivity(intent)
                finish()*/

                val action = GameFragmentDirections.actionGameFragmentToResultFragment(type,message)
                this.findNavController().navigate(action)

            }

        }
        if (counter == 9 && flag == 0) {
            end=true
            binding.status.text = "Match Draw"
            var result = "draw"
            /*val intent = Intent(this@Game,Result::class.java)
            intent.putExtra("Type",type)
            intent.putExtra("Result", result)
            startActivity(intent)
            finish()*/
            val action = GameFragmentDirections.actionGameFragmentToResultFragment(type,result)
            this.findNavController().navigate(action)
        }

        if(type != "5"){

            if(activePlayer == 0 && end == false){
                Handler().postDelayed({
                    robot()
                },500)

            }
        }
        else{
            cani=true
        }
    }

    fun robot() {
        if(type == "1"){
            var list= listOf(1,2,3,4,5,6,7,8,9)
            var ran=list.random()
            var image: ImageView? = null

            when(ran){
                1->{image=binding.imageView0}
                2->{image=binding.imageView1}
                3->{image=binding.imageView2}
                4->{image=binding.imageView3}
                5->{image=binding.imageView4}
                6->{image=binding.imageView5}
                7->{image=binding.imageView6}
                8->{image=binding.imageView7}
                9->{image=binding.imageView8}
            }
            val tappedImage = Integer.parseInt(image?.tag.toString())
            if(gameState[tappedImage]==2){
                play(image)
            }else{
                robot()
            }
        }
        if(type == "2"){
            var d:Int=stophuman()
            if(d==1){
                playtostop()

            }else{
                playnextrandommove()

            }
        }
        if(type =="3"){
            var c:Int=win()
            if(c==1){
                playforwin()

            }else{
                var d:Int=stophuman()
                if(d==1){
                    playtostop()

                }else{
                    playnextrandommove()

                }
            }
        }
    }

    fun play(image: ImageView?) {
        val tappedImage = Integer.parseInt(image!!.tag.toString())
        if (gameState[tappedImage] == 2) {
            counter++

            if (counter == 9) {
                gameActive = false
            }
            gameState[tappedImage] = activePlayer

            image.setImageResource(R.drawable.o)
            activePlayer = 1
            //var status: TextView = findViewById(R.id.status)
            binding.status.text = "Your's TURN"
            gamewin()
            cani = true
        }
    }

    fun playforwin() {
        if (gameState[0] == 0 && gameState[1] == 0 && gameState[2] != 1)
        {play(binding.imageView2)}
        else if (gameState[0] == 0 && gameState[2] == 0 && gameState[1] != 1)
        {   play(binding.imageView1)}
        else if (gameState[0] != 1 && gameState[1] == 0 && gameState[2] == 0)
        {   play(binding.imageView0)}
        else if (gameState[3] == 0 && gameState[4] == 0 && gameState[5] != 1)
        {   play(binding.imageView5)}
        else if (gameState[3] == 0 && gameState[5] == 0 && gameState[4] != 1)
        {   play(binding.imageView4)}
        else if (gameState[3] != 1 && gameState[5] == 0 && gameState[4] == 0)
        {   play(binding.imageView3)}
        else if (gameState[6] == 0 && gameState[7] == 0 && gameState[8] != 1)
        {   play(binding.imageView8)}
        else if (gameState[6] == 0 && gameState[8] == 0 && gameState[7] != 1)
        {   play(binding.imageView7)}
        else if (gameState[6] != 1 && gameState[8] == 0 && gameState[7] == 0)
        {   play(binding.imageView6)}
        else if (gameState[0] == 0 && gameState[4] == 0 && gameState[8] != 1)
        {   play(binding.imageView8)}
        else if (gameState[0] == 0 && gameState[8] == 0 && gameState[4] != 1)
        {   play(binding.imageView4)}
        else if (gameState[0] != 1 && gameState[4] == 0 && gameState[8] == 0)
        {play(binding.imageView0)}
        else if (gameState[2] == 0 && gameState[4] == 0 && gameState[6] != 1)
        {    play(binding.imageView6)}
        else if (gameState[2] == 0 && gameState[6] == 0 && gameState[4] != 1)
        {   play(binding.imageView4)}
        else if (gameState[2] != 1 && gameState[4] == 0 && gameState[6] == 0)
        {   play(binding.imageView2)}
        else if (gameState[0] == 0 && gameState[3] == 0 && gameState[6] != 1)
        {   play(binding.imageView6)}
        else if (gameState[0] == 0 && gameState[6] == 0 && gameState[3] != 1)
        {   play(binding.imageView3)}
        else if (gameState[0] != 1 && gameState[3] == 0 && gameState[6] == 0)
        {   play(binding.imageView0)}
        else if (gameState[1] == 0 && gameState[4] == 0 && gameState[7] != 1)
        {   play(binding.imageView7)}
        else if (gameState[1] == 0 && gameState[7] == 0 && gameState[4] != 1)
        {   play(binding.imageView4)}
        else if (gameState[1] != 1 && gameState[7] == 0 && gameState[4] == 0)
        {   play(binding.imageView1)}
        else if (gameState[2] == 0 && gameState[5] == 0 && gameState[8] != 1)
        {   play(binding.imageView8)}
        else if (gameState[2] == 0 && gameState[8] == 0 && gameState[5] != 1)
        {   play(binding.imageView5)}
        else
        {   play(binding.imageView2)}
    }

    fun win(): Int {
        if (
            (gameState[0] == 0 && gameState[1] == 0 && gameState[2] != 1)
            || (gameState[0] == 0 && gameState[2] == 0 && gameState[1] != 1)
            || (gameState[0] != 1 && gameState[1] == 0 && gameState[2] == 0)
            || (gameState[3] == 0 && gameState[4] == 0 && gameState[5] != 1)
            || (gameState[3] == 0 && gameState[5] == 0 && gameState[4] != 1)
            || (gameState[3] != 1 && gameState[5] == 0 && gameState[4] == 0)
            || (gameState[6] == 0 && gameState[7] == 0 && gameState[8] != 1)
            || (gameState[6] == 0 && gameState[8] == 0 && gameState[7] != 1)
            || (gameState[6] != 1 && gameState[8] == 0 && gameState[7] == 0)
            || (gameState[0] == 0 && gameState[4] == 0 && gameState[8] != 1)
            || (gameState[0] == 0 && gameState[8] == 0 && gameState[4] != 1)
            || (gameState[0] != 1 && gameState[4] == 0 && gameState[8] == 0)
            || (gameState[2] == 0 && gameState[4] == 0 && gameState[6] != 1)
            || (gameState[2] == 0 && gameState[6] == 0 && gameState[4] != 1)
            || (gameState[2] != 1 && gameState[4] == 0 && gameState[6] == 0)
            || (gameState[0] == 0 && gameState[3] == 0 && gameState[6] != 1)
            || (gameState[0] == 0 && gameState[6] == 0 && gameState[3] != 1)
            || (gameState[0] != 1 && gameState[3] == 0 && gameState[6] == 0)
            || (gameState[1] == 0 && gameState[4] == 0 && gameState[7] != 1)
            || (gameState[1] == 0 && gameState[7] == 0 && gameState[4] != 1)
            || (gameState[1] != 1 && gameState[7] == 0 && gameState[4] == 0)
            || (gameState[2] == 0 && gameState[5] == 0 && gameState[8] != 1)
            || (gameState[2] == 0 && gameState[8] == 0 && gameState[5] != 1)
            || (gameState[2] != 1 && gameState[8] == 0 && gameState[5] == 0)
        ) {
            return 1
        } else{ return 0 }
    }

    fun playnextrandommove() {
        if (gameState[0] == 1 && counter == 1)
        {play(binding.imageView4)}
        else if (counter == 1 && gameState[8] == 1)
        {play(binding.imageView4)}
        else if (counter == 1 && gameState[2] == 1)
        {play(binding.imageView4)}
        else if (counter == 1 && gameState[6] == 1)
        {play(binding.imageView4)}
        else if (counter == 3 && (gameState[0] == 1 && gameState[8] == 1 || gameState[2] == 1 && gameState[6] == 1))
        {play(binding.imageView1)}
        else if (gameState[4] != 0 && gameState[4] != 1)
        {    play(binding.imageView4)}
        else if (gameState[0] != 0 && gameState[0] != 1)
        {   play(binding.imageView0)}
        else if (gameState[8] != 0 && gameState[8] != 1)
        {   play(binding.imageView8)}
        else if (gameState[2] != 0 && gameState[2] != 1)
        {  play(binding.imageView2)}
        else if (gameState[6] != 0 && gameState[6] != 1)
        {   play(binding.imageView6)}
        else if (gameState[1] != 0 && gameState[1] != 1)
        {   play(binding.imageView1)}
        else if (gameState[3] != 0 && gameState[3] != 1)
        {   play(binding.imageView3)}
        else if (gameState[5] != 0 && gameState[5] != 1)
        {   play(binding.imageView5)}
        else if(gameState[7] != 0 && gameState[7] != 1)
        {play(binding.imageView7)}
    }

    fun playtostop() {
        if (gameState[0] == 1 && gameState[1] == 1 && gameState[2] != 0)
            play(binding.imageView2)
        else if (gameState[0] == 1 && gameState[2] == 1 && gameState[1] != 0)
            play(binding.imageView1)
        else if (gameState[0] != 0 && gameState[1] == 1 && gameState[2] == 1)
            play(binding.imageView0)
        else if (gameState[3] == 1 && gameState[4] == 1 && gameState[5] != 0)
            play(binding.imageView5)
        else if (gameState[3] == 1 && gameState[5] == 1 && gameState[4] != 0)
            play(binding.imageView4)
        else if (gameState[3] != 0 && gameState[5] == 1 && gameState[4] == 1)
            play(binding.imageView3)
        else if (gameState[6] == 1 && gameState[7] == 1 && gameState[8] != 0)
            play(binding.imageView8)
        else if (gameState[6] == 1 && gameState[8] == 1 && gameState[7] != 0)
            play(binding.imageView7)
        else if (gameState[6] != 0 && gameState[8] == 1 && gameState[7] == 1)
            play(binding.imageView6)
        else if (gameState[0] == 1 && gameState[4] == 1 && gameState[8] != 0)
            play(binding.imageView8)
        else if (gameState[0] == 1 && gameState[8] == 1 && gameState[4] != 0)
            play(binding.imageView4)
        else if (gameState[0] != 0 && gameState[4] == 1 && gameState[8] == 1)
            play(binding.imageView0)
        else if (gameState[2] == 1 && gameState[4] == 1 && gameState[6] != 0)
            play(binding.imageView6)
        else if (gameState[2] == 1 && gameState[6] == 1 && gameState[4] != 0)
            play(binding.imageView4)
        else if (gameState[2] != 0 && gameState[4] == 1 && gameState[6] == 1)
            play(binding.imageView2)
        else if (gameState[0] == 1 && gameState[3] == 1 && gameState[6] != 0)
            play(binding.imageView6)
        else if (gameState[0] == 1 && gameState[6] == 1 && gameState[3] != 0)
            play(binding.imageView3)
        else if (gameState[0] != 0 && gameState[3] == 1 && gameState[6] == 1)
            play(binding.imageView0)
        else if (gameState[1] == 1 && gameState[4] == 1 && gameState[7] != 0)
            play(binding.imageView7)
        else if (gameState[1] == 1 && gameState[7] == 1 && gameState[4] != 0)
            play(binding.imageView4)
        else if (gameState[1] != 0 && gameState[7] == 1 && gameState[4] == 1)
            play(binding.imageView1)
        else if (gameState[2] == 1 && gameState[5] == 1 && gameState[8] != 0)
            play(binding.imageView8)
        else if (gameState[2] == 1 && gameState[8] == 1 && gameState[5] != 0)
            play(binding.imageView5)
        else if (gameState[2] != 0 && gameState[8] == 1 && gameState[5] == 1)
            play(binding.imageView2)
    }

    fun stophuman(): Int {

        if (gameState[0] == 1 && gameState[1] == 1 && gameState[2] != 0
            || gameState[0] == 1 && gameState[2] == 1 && gameState[1] != 0
            || gameState[0] != 0 && gameState[1] == 1 && gameState[2] == 1
            || gameState[3] == 1 && gameState[4] == 1 && gameState[5] != 0
            || gameState[3] == 1 && gameState[5] == 1 && gameState[4] != 0
            || gameState[3] != 0 && gameState[5] == 1 && gameState[4] == 1
            || gameState[6] == 1 && gameState[7] == 1 && gameState[8] != 0
            || gameState[6] == 1 && gameState[8] == 1 && gameState[7] != 0
            || gameState[6] != 0 && gameState[8] == 1 && gameState[7] == 1
            || gameState[0] == 1 && gameState[4] == 1 && gameState[8] != 0
            || gameState[0] == 1 && gameState[8] == 1 && gameState[4] != 0
            || gameState[0] != 0 && gameState[4] == 1 && gameState[8] == 1
            || gameState[2] == 1 && gameState[4] == 1 && gameState[6] != 0
            || gameState[2] == 1 && gameState[6] == 1 && gameState[4] != 0
            || gameState[2] != 0 && gameState[4] == 1 && gameState[6] == 1
            || gameState[0] == 1 && gameState[3] == 1 && gameState[6] != 0
            || gameState[0] == 1 && gameState[6] == 1 && gameState[3] != 0
            || gameState[0] != 0 && gameState[3] == 1 && gameState[6] == 1
            || gameState[1] == 1 && gameState[4] == 1 && gameState[7] != 0
            || gameState[1] == 1 && gameState[7] == 1 && gameState[4] != 0
            || gameState[1] != 0 && gameState[7] == 1 && gameState[4] == 1
            || gameState[2] == 1 && gameState[5] == 1 && gameState[8] != 0
            || gameState[2] == 1 && gameState[8] == 1 && gameState[5] != 0
            || gameState[2] != 0 && gameState[8] == 1 && gameState[5] == 1
        ) {
            return 1
        } else {
            return 0
        }

    }



}