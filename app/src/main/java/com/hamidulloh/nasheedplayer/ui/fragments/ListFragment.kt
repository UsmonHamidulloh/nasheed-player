package com.hamidulloh.nasheedplayer.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamidulloh.nasheedplayer.databinding.FragmentListBinding
import com.hamidulloh.nasheedplayer.ui.adapter.NasheedAdapter
import com.hamidulloh.nasheedplayer.utills.listNasheed

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var nasheedAdapter: NasheedAdapter
    private val TAG = "ListFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        nasheedAdapter = NasheedAdapter(NasheedAdapter.NasheedItemCallback {
            val navDirections = ListFragmentDirections.actionListFragmentToPlayerFragment(
                id = it.id,
                name = it.name,
                author = it.author,
                duration = it.duration,
                path = it.path,
                cover = it.image,
                filename = it.filename
            )
            findNavController().navigate(navDirections)

            Log.d(TAG, "NasheedItemCallback: $it")
        })

        nasheedAdapter.submitList(listNasheed)

        binding.nasheedList.apply {
            adapter = nasheedAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}