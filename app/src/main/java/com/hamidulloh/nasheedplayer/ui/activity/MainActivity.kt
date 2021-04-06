package com.hamidulloh.nasheedplayer.ui.activity

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.databinding.ActivityMainBinding
import com.hamidulloh.nasheedplayer.model.Nasheed
import com.hamidulloh.nasheedplayer.ui.adapter.NasheedAdapter
import com.hamidulloh.nasheedplayer.utills.nasheedList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nasheedAdapter: NasheedAdapter
    private lateinit var mediaPlayer: MediaPlayer

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()

        nasheedAdapter = NasheedAdapter(NasheedAdapter.NasheedItemCallback {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer.create(this, it.path)
            mediaPlayer.start()

            Log.d(TAG, "NasheedItemCallback: $it")
        })

        nasheedAdapter.submitList(nasheedList())

        binding.nasheedList.apply {
            adapter = nasheedAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}