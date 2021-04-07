package com.hamidulloh.nasheedplayer.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.utills.calculateTime
import com.hamidulloh.nasheedplayer.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var resumePause: ImageView
    private lateinit var seekBar: SeekBar

    private var length = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.nasheedLiveData.observe(this, { nasheed ->
            mediaPlayer = MediaPlayer.create(this, nasheed.path)
            mediaPlayer.start()

        })

        viewModel.resumePause.observe(this, {
            resumePause = it

            resumePause.setOnClickListener {
                if (mediaPlayer.isPlaying) {
                    resumePause.setImageResource(R.drawable.ic_play)

                    mediaPlayer.pause()
                    length = mediaPlayer.currentPosition
                } else {
                    resumePause.setImageResource(R.drawable.ic_pause)

                    mediaPlayer.seekTo(length)
                    mediaPlayer.start()
                }
            }
        })

        viewModel.durationText.observe(this, { durationText ->
            durationText.text = calculateTime(mediaPlayer.duration)
        })

        viewModel.seekBar.observe(this, { positionBar ->
            seekBar = positionBar

            object : Thread() {
                override fun run() {
                    super.run()

                    while (seekBar.progress <= mediaPlayer.duration && mediaPlayer.isPlaying) {
                        seekBar.progress = mediaPlayer.currentPosition
                        sleep(1_000)
                    }
                }
            }.start()

            seekBar.max = mediaPlayer.duration
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                        mediaPlayer.start()

                        resumePause.setImageResource(R.drawable.ic_pause)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        })
    }
}