package com.hamidulloh.nasheedplayer.ui.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.viewmodel.ViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    private lateinit var mediaPlayer: MediaPlayer

    private var isSeekBarHoldByUser = false
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.nasheedLiveData.observe(this, { nasheed ->
            mediaPlayer = MediaPlayer.create(this, nasheed.path)
            mediaPlayer.start()

            viewModel.durationText.value = mediaPlayer.duration

            object : Thread() {
                override fun run() {
                    super.run()

                    while (mediaPlayer.currentPosition <= mediaPlayer.duration) {
                        viewModel.mediaCurrentPosition.postValue(mediaPlayer.currentPosition)
                        sleep(1_000)

                        Log.d(TAG, "run: ${mediaPlayer.currentPosition}")
                    }
                }
            }.start()

            Log.d(TAG, "mediaCurrentPosition (value): ${mediaPlayer.currentPosition}")

        })

        viewModel.progress.observe(this, { progress ->
            mediaPlayer.seekTo(progress)
        })

        viewModel.playerPauseClickEvent.observe(this, { clickEvent ->
            if (clickEvent) {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()

                    viewModel.isPlaying.value = false
                } else {
                    mediaPlayer.start()

                    viewModel.isPlaying.value = true
                }

                viewModel.playerPauseClickEvent.value = false
            }

        })

        viewModel.isSeekBarHoldByUser.observe(this, {
            isSeekBarHoldByUser = it
        })

    }
}