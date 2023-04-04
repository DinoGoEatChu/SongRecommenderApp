package com.example.songrecommenderapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(private var songs: List<Song>) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitleTextView: TextView = itemView.findViewById(R.id.songTitleTextView)
        val artistTextView: TextView = itemView.findViewById(R.id.artistTextView)
        val faveClusterTextView: TextView = itemView.findViewById(R.id.faveClusterTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.song_item_layout, parent, false)
        return SongViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songTitleTextView.text = song.name
        holder.artistTextView.text = song.artist
        holder.faveClusterTextView.text = song.cluster.toString()
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    fun updateSongs(songs: List<Song>) {
        this.songs = songs
        notifyDataSetChanged()
    }
}