package com.hamidulloh.nasheedplayer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.databinding.ActivityMainBinding
import com.hamidulloh.nasheedplayer.model.Nasheed
import com.hamidulloh.nasheedplayer.ui.adapter.NasheedAdapter
import com.hamidulloh.nasheedplayer.utills.nasheedList

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nasheedAdapter: NasheedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nasheedAdapter = NasheedAdapter()

        nasheedAdapter.submitList(nasheedList())

        binding.nasheedList.apply {
            adapter = nasheedAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}