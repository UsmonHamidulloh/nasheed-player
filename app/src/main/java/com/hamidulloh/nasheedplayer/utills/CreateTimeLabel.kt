package com.hamidulloh.nasheedplayer.utills

fun createTimeLabel(time: Int): String {
    var timeLabel = ""
    val min = time / 1000 / 60
    val sec = time / 1000 % 60

    timeLabel = "$min:"
    if (sec < 10) timeLabel += "0"
    timeLabel += sec

    return timeLabel
}