package com.example.songrecommenderapp


import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader


class Recommendations(private val context: Context){
    val inputStream = context.assets.open("songs_w_links.csv")
    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
    val csvString = bufferedReader.use {it.readText()}

}