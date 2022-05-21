package com.ndriqa.rootapp.utility.data

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.ndriqa.rootapp.models.Song

fun Song.uri(): Uri {
    Log.i("mytag", this.absolutePath.toString())
    return "${this.absolutePath}".toUri()
}