package com.ndriqa.rootapp.adapters

import android.annotation.SuppressLint
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ndriqa.rootapp.R
import com.ndriqa.rootapp.databinding.ListItemSongBinding
import com.ndriqa.rootapp.models.Song
import java.util.zip.Inflater

class MusicAdapter(val listener: Listener) : RecyclerView.Adapter<MusicAdapter.MusicItemViewHolder>() {
    var data: List<Song> = mutableListOf()
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

                    root.setOnClickListener { listener.songClicked(song) }
                    playButton.setOnClickListener { listener.songClicked(song) }
                }
            }
    }

    interface Listener {
        fun songClicked(song: Song)
    }
}