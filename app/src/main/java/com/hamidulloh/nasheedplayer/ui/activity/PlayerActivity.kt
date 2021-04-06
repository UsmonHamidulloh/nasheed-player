package com.hamidulloh.nasheedplayer.ui.activity

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
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

        nasheed = Nasheed(
            id = bundle!!.getInt("id"),
            name = bundle!!.getString("name")!!,
            path = bundle!!.getInt("path"),
            cover = bundle!!.getInt("cover")
        )

        mediaPlayer = MediaPlayer.create(this, nasheed.path)

        calculateTime(mediaPlayer.duration)

        mediaPlayer.start()

        if (hours > 0) {
            binding.duration.text = "$hours:$minut:$second"
        } else {
            binding.duration.text = "$minut:$second"
        }

        binding.resumePause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                binding.resumePause.setImageResource(R.drawable.ic_play)

                mediaPlayer.pause()
                length = mediaPlayer.currentPosition
            } else {
                binding.resumePause.setImageResource(R.drawable.ic_pause)

                mediaPlayer.seekTo(length)
                mediaPlayer.start()
            }

        }

    }

    private fun calculateTime(duration: Int) {
        minut = (duration / 1_000) / 60
        second = (duration / 1_000) - (minut * 60)
        hours = minut / 60
    }

}