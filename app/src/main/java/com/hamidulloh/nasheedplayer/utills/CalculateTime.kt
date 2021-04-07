package com.hamidulloh.nasheedplayer.utills

fun calculateTime(duration: Int): String {
    val minut = (duration / 1_000) / 60
    val second = (duration / 1_000) - (minut * 60)
    val hours = minut / 60

    if (hours > 0) {
        return "$hours:$minut:$second"
    } else {
        return "$minut:$second"
    }
}