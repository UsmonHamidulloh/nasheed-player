package com.hamidulloh.nasheedplayer.viewmodel

import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidulloh.nasheedplayer.model.Nasheed

class ViewModel : ViewModel() {
    var nasheedLiveData = MutableLiveData<Nasheed>()
    var resumePause = MutableLiveData<ImageView>()
    var durationText = MutableLiveData<TextView>()
    var seekBar = MutableLiveData<SeekBar>()
}