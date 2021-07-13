package com.hamidulloh.nasheedplayer.utills

import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.model.Nasheed

val listNasheed = arrayListOf<Nasheed>(
    Nasheed(
        id = 0,
        name = "Kuntu Maitan",
        author = "Unknown",
        duration = "3:15",
        path    = R.raw.kuntu_maitan,
        image   = R.drawable.nasheed_player,
        filename = "kuntu_maitan"),

    Nasheed(
        id = 0,
        name = "Mahum bi Ummati Ahmad",
        author = "Unknown",
        duration = "3:12",
        path    = R.raw.mahum_bi_ummati_ahmadin,
        image   = R.drawable.nasheed_player,
        filename = "mahum_bi_ummati_ahmadin"),

    Nasheed(
        id = 0,
        name = "Shukran ya robbi",
        author = "Unknown",
        duration = "4:49",
        path    = R.raw.shukran_ya_robbi,
        image   = R.drawable.nasheed_player,
        filename = "shukran_ya_robbi"),
)