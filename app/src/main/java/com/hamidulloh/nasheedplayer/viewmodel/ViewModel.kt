package com.hamidulloh.nasheedplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidulloh.nasheedplayer.model.Nasheed

class ViewModel : ViewModel() {
    var nasheedLiveData = MutableLiveData<Nasheed>()
    var durationText = MutableLiveData<Int>()
    var isPlaying = MutableLiveData<Boolean>()
    var playerPauseClickEvent = MutableLiveData<Boolean>()
    var isSeekBarHoldByUser = MutableLiveData<Boolean>()
    var mediaCurrentPosition = MutableLiveData<Int>()
    var progress = MutableLiveData<Int>()
}