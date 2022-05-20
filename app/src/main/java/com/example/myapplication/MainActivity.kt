package com.example.myapplication

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var flag:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lateinit var job: Job

        //binding.txv.text = secondsLeft.toString()

        binding.imgstart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                if(flag){
                    flag = false
                    binding.imgstart.setImageResource(R.drawable.start)
                    job.cancel()
                }else{
                    flag = true
                    binding.imgstart.setImageResource(R.drawable.stop)
                    job = GlobalScope.launch(Dispatchers.Main) {
                        while(flag){
                            delay(25)
                            val canvas: Canvas = binding.mysv.holder.lockCanvas()
                            binding.mysv.drawSomething(canvas)
                            binding.mysv.holder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        })
    }
}