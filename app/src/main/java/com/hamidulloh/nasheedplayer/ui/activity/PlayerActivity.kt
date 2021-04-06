package com.hamidulloh.nasheedplayer.ui.activity

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.databinding.ActivityPlayerBinding
import com.hamidulloh.nasheedplayer.model.Nasheed

class PlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var nasheed: Nasheed

    private var second: Int = 0
    private var minut: Int = 0
    private var hours: Int = 0

    private var length = 0

    private var bundle: Bundle? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras

        receiveBundleData()

        mediaPlayer = MediaPlayer.create(this, nasheed.path)
        mediaPlayer.start()

        calculateTime(mediaPlayer.duration)

        resumePauseClickListener()
        setDurationText()
        seekBarChangeListener()

        Thread(Runnable {
            while (mediaPlayer != null) {
                try {
                    val msg = Message()
                    msg.what = mediaPlayer.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1_000)
                } catch (e: InterruptedException) {

                }
            }
        }).start()
    }

    private fun receiveBundleData() {
        nasheed = Nasheed(
            id = bundle!!.getInt("id"),
            name = bundle!!.getString("name")!!,
            path = bundle!!.getInt("path"),
            cover = bundle!!.getInt("cover")
        )
    }

    private fun setDurationText() {
        if (hours > 0) {
            binding.duration.text = "$hours:$minut:$second"
        } else {
            binding.duration.text = "$minut:$second"
        }
    }

    private fun seekBarChangeListener() {
        binding.positionBar.max = mediaPlayer.duration
        binding.positionBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    mediaPlayer.start()

                    changeResumePauseImage(image = R.drawable.ic_pause)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun resumePauseClickListener() {
        binding.resumePause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                changeResumePauseImage(image = R.drawable.ic_play)

                mediaPlayer.pause()
                length = mediaPlayer.currentPosition
            } else {
                changeResumePauseImage(image = R.drawable.ic_pause)

                mediaPlayer.seekTo(length)
                mediaPlayer.start()
            }

        }
    }

    private fun changeResumePauseImage(image: Int) {
        binding.resumePause.setImageResource(image)
    }

    var handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            val elapsedTime = createTimeLabel(currentPosition)

            binding.positionBar.progress = currentPosition
            binding.firstTime.text = elapsedTime
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    private fun calculateTime(duration: Int) {
        minut = (duration / 1_000) / 60
        second = (duration / 1_000) - (minut * 60)
        hours = minut / 60
    }

}