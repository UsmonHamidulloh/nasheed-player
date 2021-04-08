package com.hamidulloh.nasheedplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamidulloh.nasheedplayer.model.Nasheed

class ViewModel : ViewModel() {
    var nasheedLiveData = MutableLiveData<Nasheed>()
    var durationText = MutableLiveData<Int>()

    var isNasheedPlaying = MutableLiveData<Boolean>()

    var isPlaying = MutableLiveData<Boolean>()  //done
    var playerPauseClickEvent = MutableLiveData<Boolean>()  //done

    var isSeekBarHoldByUser = MutableLiveData<Boolean>()
    var mediaCurrentPosition = MutableLiveData<Int>()
    var progress = MutableLiveData<Int>()
}