package com.example.songrecommenderapp



import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVRecord
import java.io.File
import java.io.FileReader

data class Song (
    val id: Int,
    val artist: String,
    val name: String,
    val year: Int,
    val cluster: Int,
    val link: String,
    val popularity: Int
    )

val reader = FileReader("/assets/songs_w_links.csv")
val csvFormat = CSVFormat.Builder.create()
    .setHeader("ID", "artists", "name", "year", "cluster", "link", "popularity")
    .setDelimiter(',')
    .build()

private fun recordToSong(record: CSVRecord): Song {
    return Song(
        record.get("#").toInt(),
        record.get("artists"),
        record.get("name"),
        record.get("year").toInt(),
        record.get("cluster").toInt(),
        record.get("link"),
        record.get("popularity").toInt()
    )
}
class Recommendations(val filename: String) {
    private val songs: List<Song> = File(filename).bufferedReader().use { reader ->
        val parser = CSVParser(reader, csvFormat)
        parser.records.map { recordToSong(it) }
    }

    fun findRecommendations(songTitle: String, artist: String): Pair<Int, List<Song>>? {
        val faves = songs.filter {
            it.name.lowercase().contains(songTitle.lowercase()) && it.artist.lowercase().contains(artist.lowercase())
        }
        if (faves.isEmpty()) {
            return null
        }
        val clust = faves.map { it.cluster }
        val clustered = clust.groupingBy { it }.eachCount()
        val faveCluster = clustered.toList().sortedByDescending { (_, value) -> value }[0].first
        println("Favorite cluster: $faveCluster")
        val recommendation = songs.filter {
            it.cluster == faveCluster && !it.name.lowercase().contains(songTitle.lowercase())
        }.sortedBy { it.popularity }.shuffled().take(30).sortedByDescending { it.popularity }
        return faveCluster to recommendation.take(10)
    }
}

