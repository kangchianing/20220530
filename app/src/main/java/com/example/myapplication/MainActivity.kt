package com.example.myapplication

import android.graphics.Canvas
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity(), View.OnTouchListener , GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    lateinit var binding: ActivityMainBinding
    lateinit var gDetector: GestureDetector
    lateinit var mper: MediaPlayer
    lateinit var job: Job
    var flag:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gDetector = GestureDetector(this, this)
        binding.imgfly.setOnTouchListener(this)

        mper = MediaPlayer.create(this, R.raw.shoot)

        val img:ImageView = findViewById(R.id.img)
        GlideApp.with(this)
            .load(R.drawable.pu0)
            .circleCrop()
            .override(800, 600)
            .into(img)


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
        gDetector.onTouchEvent(event)
        return true
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        mper.start()
        job = GlobalScope.launch(Dispatchers.Main) {
            delay(25)
            binding.imgfly.setImageResource(R.drawable.shoot1)
            delay(25)
            binding.imgfly.setImageResource(R.drawable.shoot2)
            delay(25)
            binding.imgfly.setImageResource(R.drawable.shoot3)
            delay(25)
            binding.imgfly.setImageResource(R.drawable.shoot4)
            delay(25)
            binding.imgfly.setImageResource(R.drawable.shoot5)
        }

        return true
    }

    override fun onScroll(event: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {

    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true
    }

    override fun onSingleTapConfirmed(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTap(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onDoubleTapEvent(p0: MotionEvent?): Boolean {
        return true
    }


}


