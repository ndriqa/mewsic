package com.ndriqa.rootapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ndriqa.rootapp.R
import com.ndriqa.rootapp.databinding.ListItemSongBinding
import com.ndriqa.rootapp.models.Song

class MusicAdapter(private val listener: Listener) : RecyclerView.Adapter<MusicAdapter.MusicItemViewHolder>() {
    var data: List<Song> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var nowPlayingSong: Song? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MusicItemViewHolder(DataBindingUtil.inflate(inflater, R.layout.list_item_song, parent, false))
    }

    override fun onBindViewHolder(holder: MusicItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class MusicItemViewHolder(private val itemBinding: ListItemSongBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(song: Song){
                with(itemBinding){
                    title.text = song.audioTitle
                    artist.text = song.audioArtist

                    root.setOnClickListener { songClicked(song) }

                    root.setBackgroundResource(
                        if (song.audioId == nowPlayingSong?.audioId) R.drawable.song_background_selected
                        else R.drawable.song_background
                    )

                    isPlayingIcon.visibility = if (song.audioId == nowPlayingSong?.audioId) View.VISIBLE else View.GONE
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun songClicked(song: Song){
        nowPlayingSong = song
        listener.playSong(song)
    }

    interface Listener {
        fun playSong(song: Song)
    }
}