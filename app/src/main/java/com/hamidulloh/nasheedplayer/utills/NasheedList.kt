package com.hamidulloh.nasheedplayer.utills

import com.hamidulloh.nasheedplayer.R
import com.hamidulloh.nasheedplayer.model.Nasheed

fun nasheedList(): ArrayList<Nasheed> {
    val listNasheed = ArrayList<Nasheed>()

    listNasheed.apply {
        add(Nasheed(0, "Kuntu Maitan", R.raw.kuntu_maitan, R.drawable.nasheed_player, "kuntu_maitan"))
        add(Nasheed(1, "Mahum Bi Ummati Ahmadin", R.raw.mahum_bi_ummati_ahmadin, R.drawable.nasheed_player, "mahum_bi_ummati_ahmadin"))
    }

    return listNasheed
}