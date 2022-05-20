package com.example.myapplication

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    lateinit var binding: ActivityMainBinding
    var flag:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img:ImageView = findViewById(R.id.img)
        GlideApp.with(this)
            .load(R.drawable.pu0)
            .circleCrop()
            .override(800, 600)
            .into(img)

        lateinit var job: Job
        //binding.txv.text = secondsLeft.toString()

        binding.imgfly.setOnTouchListener(this)

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
                            binding.imgfly.setImageResource(R.drawable.fly2)
                            delay(25)
                            binding.imgfly.setImageResource(R.drawable.fly1)

                            val canvas: Canvas = binding.mysv.holder.lockCanvas()
                            binding.mysv.drawSomething(canvas)
                            binding.mysv.holder.unlockCanvasAndPost(canvas)
                        }
                    }
                }
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_MOVE){
            v?.x = (115 - v!!.width/2).toFloat()
            v?.y = event.rawY- v!!.height/2
        }
        return true
    }
}