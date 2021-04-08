package com.hamidulloh.nasheedplayer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.databinding.FragmentPlayerBinding
import com.hamidulloh.nasheedplayer.model.Nasheed
import com.hamidulloh.nasheedplayer.utills.calculateTime
import com.hamidulloh.nasheedplayer.utills.createTimeLabel
import com.hamidulloh.nasheedplayer.viewmodel.ViewModel

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel
    private lateinit var nasheed: Nasheed

    private var duration = 0
    private var isHoldByUser = false
    private var isNasheedPlaying = true

    private var TAG = "PlayerFragment"

    private val args: PlayerFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)

        receiveArgsData()

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        viewModel.nasheedLiveData.value = nasheed

        binding.resumePause.setOnClickListener {
            viewModel.playerPauseClickEvent.value = true
        }

        viewModel.isPlaying.observe(requireActivity(), { mediaIsPlaying ->
            if (mediaIsPlaying) {
                binding.resumePause.setImageResource(R.drawable.ic_pause)
            } else {
                binding.resumePause.setImageResource(R.drawable.ic_play)
            }
            isNasheedPlaying = mediaIsPlaying

            Log.d(TAG, "recieve mediaIsPlaying: $mediaIsPlaying")
            Log.d(TAG, "recieve isNasheedPlaying: $isNasheedPlaying")
        })

        viewModel.durationText.observe(requireActivity(), {
            duration = it

            binding.duration.text = calculateTime(duration)
            binding.positionBar.max = duration
        })

        binding.positionBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isHoldByUser = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                isHoldByUser = false

                if (seekBar != null) {
                    viewModel.progress.value = seekBar.progress
                }
            }
        })

        viewModel.mediaCurrentPosition.observe(requireActivity(), { currentPosition ->
            binding.positionBar.progress = currentPosition
            binding.firstTime.text = createTimeLabel(currentPosition)

            Log.d(TAG, "mediaCurrentPosition: $currentPosition")
        })

        viewModel.progress.value = binding.positionBar.progress

        return binding.root
    }

    private fun receiveArgsData() {
        nasheed = Nasheed(
            id = args.id,
            name = args.name,
            path = args.path,
            cover = args.cover
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}