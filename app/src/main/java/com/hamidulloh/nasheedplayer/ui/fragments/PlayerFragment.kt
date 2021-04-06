package com.hamidulloh.nasheedplayer.ui.fragments

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.databinding.FragmentPlayerBinding
import com.hamidulloh.nasheedplayer.model.Nasheed

class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var nasheed: Nasheed

    private var second: Int = 0
    private var minut: Int = 0
    private var hours: Int = 0

    private var length = 0

    private val args: PlayerFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)

        receiveArgsData()

        mediaPlayer = MediaPlayer.create(requireActivity(), nasheed.path)
        mediaPlayer.start()

        calculateTime(mediaPlayer.duration)

        resumePauseClickListener()
        setDurationText()
        seekBarChangeListener()

        Thread(Runnable {
            while (mediaPlayer != null) {
                try {
                    val msg = Message()
                    msg.what = mediaPlayer.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1_000)
                } catch (e: InterruptedException) {

                }
            }
        }).start()

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

    private fun setDurationText() {
        if (hours > 0) {
            binding.duration.text = "$hours:$minut:$second"
        } else {
            binding.duration.text = "$minut:$second"
        }
    }

    private fun seekBarChangeListener() {
        binding.positionBar.max = mediaPlayer.duration
        binding.positionBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                    mediaPlayer.start()

                    changeResumePauseImage(image = R.drawable.ic_pause)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun resumePauseClickListener() {
        binding.resumePause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                changeResumePauseImage(image = R.drawable.ic_play)

                mediaPlayer.pause()
                length = mediaPlayer.currentPosition
            } else {
                changeResumePauseImage(image = R.drawable.ic_pause)

                mediaPlayer.seekTo(length)
                mediaPlayer.start()
            }

        }
    }

    private fun changeResumePauseImage(image: Int) {
        binding.resumePause.setImageResource(image)
    }

    var handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what
            val elapsedTime = createTimeLabel(currentPosition)

            if (_binding != null) {
                binding.positionBar.progress = currentPosition
                binding.firstTime.text = elapsedTime
            }
        }
    }

    private fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        val min = time / 1000 / 60
        val sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    private fun calculateTime(duration: Int) {
        minut = (duration / 1_000) / 60
        second = (duration / 1_000) - (minut * 60)
        hours = minut / 60
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}