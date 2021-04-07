package com.hamidulloh.nasheedplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.hamidulloh.nasheedplayer.databinding.FragmentPlayerBinding
import com.hamidulloh.nasheedplayer.model.Nasheed
import com.hamidulloh.nasheedplayer.viewmodel.ViewModel

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel
    private lateinit var nasheed: Nasheed

    private lateinit var runnable: Runnable

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
        viewModel.resumePause.value = binding.resumePause
        viewModel.seekBar.value = binding.positionBar
        viewModel.durationText.value = binding.duration

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