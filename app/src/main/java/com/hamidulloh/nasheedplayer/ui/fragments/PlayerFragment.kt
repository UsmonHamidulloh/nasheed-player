package com.hamidulloh.nasheedplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    private var isHoldByUser = false
    private var duration = 0
    private var isNasheedPlaying = true
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
            if (_binding != null) {
                binding.nName.text = nasheedLiveData.name
                binding.nImage.setImageResource(nasheed.image)
                binding.nAuthor.text = nasheedLiveData.author
                nasheed = nasheedLiveData
                index = listNasheed.indexOf(nasheed)
            }
        })

        //back to nasheed_list
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        //remote play and pause
        binding.playPause.setOnClickListener {
            if (_binding != null) {
                viewModel.playerPauseClickEvent.value = true
            }
        }

        viewModel.isPlaying.observe(requireActivity(), { mediaIsPlaying ->
            if (_binding != null) {
                if (mediaIsPlaying) {
                    binding.playPause.setImageResource(R.drawable.ic_pause)
                } else {
                    binding.playPause.setImageResource(R.drawable.ic_play)
                }
                isNasheedPlaying = mediaIsPlaying
            }
        })

        viewModel.durationText.observe(requireActivity(), {
            duration = it

            if (_binding != null) {
                binding.dEnd.text = calculateTime(duration)
                binding.seekBar.max = duration
            }
        })

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                if (!isHoldByUser) {
                    binding.seekBar.progress = currentPosition
                }
                binding.dStart.text = createTimeLabel(currentPosition)
            }
        })

        viewModel.isNasheedEnded.observe(requireActivity(), {
            if (it) {
                checkNextNasheed()
                viewModel.isNasheedEnded.value = false
            }
        })

        binding.nNext.setOnClickListener {
            checkNextNasheed()
        }

        binding.nPrevious.setOnClickListener {
            if (index != listNasheed[0].id) {
                viewModel.nasheedLiveData.value = listNasheed[index - 1]
            } else {
                viewModel.nasheedLiveData.value = listNasheed[0]
            }
        }

        viewModel.progress.value = binding.seekBar.progress

        return binding.root
    }

    private fun checkNextNasheed() {
        if (index < listNasheed.size - 1) {
            viewModel.nasheedLiveData.value = listNasheed[index + 1]
        } else {
            viewModel.nasheedLiveData.value = listNasheed[0]
        }
    }

    private fun receiveArgsData() {
        nasheed = Nasheed(
            id = args.id,
            name = args.name,
            author = args.author,
            duration = args.duration,
            path = args.path,
            image = args.cover,
            filename = args.filename
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}