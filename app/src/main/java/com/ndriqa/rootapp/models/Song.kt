package com.ndriqa.rootapp.models

class Song(
    val audioArtist: String?,
    val audioYear: Int?,
    val audioTitle: String?,
    val audioDisplayName: String?,
    val audioDuration: Long?,
    val audioAlbum: String?,
    val audioFolderName: String?,
    val audioId: Long?,
    val audioDateAdded: Int?,
    val absolutePath: String?,
    val albumId: Long?,
    val albumArtPath: String?,
    var isPlaying: Boolean = false
)