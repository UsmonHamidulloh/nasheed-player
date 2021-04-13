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
import com.hamidulloh.nasheedplayer.utills.listNasheed
import com.hamidulloh.nasheedplayer.viewmodel.ViewModel

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel
    private lateinit var nasheed: Nasheed
    private var index = 0

    private var duration = 0
    private var isHoldByUser = false
    private var isNasheedPlaying = true

    private var isNext = false
    private var isPrevious = false

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

        viewModel.nasheedLiveData.observe(requireActivity(), { nasheedLiveData ->
            binding.name.text = nasheedLiveData.name
            binding.image.setImageResource(nasheed.cover)

            nasheed = nasheedLiveData

            Log.d(TAG, "nextBtn: liveData $index")
            index = listNasheed.indexOf(nasheed)
        })

        binding.resumePause.setOnClickListener {
            if (_binding != null) {
                viewModel.playerPauseClickEvent.value = true
            }
        }

        viewModel.isPlaying.observe(requireActivity(), { mediaIsPlaying ->
            if (_binding != null) {
                if (mediaIsPlaying) {
                    binding.resumePause.setImageResource(R.drawable.ic_pause)
                } else {
                    binding.resumePause.setImageResource(R.drawable.ic_play)
                }
                isNasheedPlaying = mediaIsPlaying
            }


            Log.d(TAG, "recieve mediaIsPlaying: $mediaIsPlaying")
            Log.d(TAG, "recieve isNasheedPlaying: $isNasheedPlaying")
        })

        viewModel.durationText.observe(requireActivity(), {
            duration = it

            if (_binding != null) {
                binding.duration.text = calculateTime(duration)
                binding.positionBar.max = duration
            }
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
            if (_binding != null) {
                binding.positionBar.progress = currentPosition
                binding.firstTime.text = createTimeLabel(currentPosition)

                Log.d(TAG, "mediaCurrentPosition: $currentPosition")
            }
        })

        binding.next.setOnClickListener {
            if (index < listNasheed.size - 1) {
                Log.d(TAG, "nextBtn: old.id: ${index}, new.id: ${index + 1}")
                Log.d(TAG, "nextBtn:  nextNasheedIs: ${listNasheed[index + 1].name}")

                viewModel.nasheedLiveData.value = listNasheed[index + 1]
            } else {
                viewModel.nasheedLiveData.value = listNasheed[0]

                Log.d(TAG, "nextBtn: goToFirstNasheed")
            }
        }

        binding.previous.setOnClickListener {
            if (index < listNasheed[0].id) {
                viewModel.nasheedLiveData.value = listNasheed[index - 1]

                Log.d(TAG, "previous: previousNasheedIs: ${listNasheed[nasheed.id - 1].name}")
            } else {
                viewModel.nasheedLiveData.value = listNasheed[0]

                Log.d(TAG, "nextBtn: goToFirstNasheed")
            }
        }

        viewModel.progress.value = binding.positionBar.progress

        return binding.root
    }

    private fun receiveArgsData() {
        nasheed = Nasheed(
            id = args.id,
            name = args.name,
            path = args.path,
            cover = args.cover,
            filename = args.filename
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}