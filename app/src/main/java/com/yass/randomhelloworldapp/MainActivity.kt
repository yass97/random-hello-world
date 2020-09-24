package com.yass.randomhelloworldapp

import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.yass.randomhelloworldapp.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val helloWorldTextLayout: ConstraintLayout
        get() = binding.helloWorldTextLayout

    private val helloWorldText: TextView
        get() = binding.helloWorldText

    private var bgm: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setClickListener()

        setUpMediaPlayer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        setUpHelloWorldPosition()
    }

    override fun onPause() {
        super.onPause()

        bgm?.apply {
            stop()
            reset()
            release()
        }

        bgm = null
    }

    private fun setUpMediaPlayer() {

        bgm = MediaPlayer.create(this, R.raw.battle).apply {
            setOnCompletionListener {
                stopBgm()
            }
        }

        bgm?.start()
    }

    private fun setUpHelloWorldPosition() {

        val viewSize = getViewSize()

        val lp = (helloWorldTextLayout.layoutParams as ConstraintLayout.LayoutParams)

        val leftPosition = getHelloWorldRandomPosition(viewSize.x)
        val topPosition = getHelloWorldRandomPosition(viewSize.y)

        lp.apply {
            leftMargin = leftPosition
            topMargin = topPosition
        }

        helloWorldTextLayout.layoutParams = lp

        helloWorldText.alpha = HIDE
    }

    private fun setClickListener() {

        helloWorldText.setOnClickListener {
            clickHelloWorldText()
        }
    }

    private fun stopBgm() {

        bgm?.apply {
            stop()
            reset()
            release()
        }

        bgm = null
    }

    private fun clickHelloWorldText() {

        stopBgm()

        helloWorldText.alpha = DISPLAY

        val mediaPlayer = MediaPlayer.create(this, R.raw.hello_world).apply {
            setOnCompletionListener {
                // 再生
                it.apply {
                    stop()
                    reset()
                    release()
                }
            }
        }

        mediaPlayer.start()
    }

    private fun getHelloWorldRandomPosition(viewSize: Int): Int = Random.nextInt(viewSize)

    private fun getViewSize(): Point {

        val point = Point(0, 0)

        val view = binding.root

        point.set((view.width - helloWorldText.width), (view.height - helloWorldText.height))

        return point
    }

    companion object {
        private const val DISPLAY = 1f
        private const val HIDE = 0f
    }
}
