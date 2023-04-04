package com.example.songrecommenderapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var recommendations: Recommendations
    private lateinit var recyclerView: RecyclerView
    private lateinit var faveClusterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recommendations = Recommendations(this, "songs_w_links.csv")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter(listOf()) //song adapter class to display recommendations



        val newRecsButton: Button = findViewById(R.id.newRecs)
        newRecsButton.setOnClickListener {
            val songTitle: String = findViewById<EditText>(R.id.song_entry).text.toString()
            val artist: String = findViewById<EditText>(R.id.artist_entry).text.toString()


            val recommendationsResult = recommendations.findRecommendations(songTitle, artist)
            if (recommendationsResult != null) {
                val (faveCluster, songs) = recommendationsResult
                // update the adapter with new recommendations..
                (recyclerView.adapter as SongAdapter).updateSongs(songs)
                //set the value of fave cluster textView
            } else {
                Toast.makeText(this, "No recommendations found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}