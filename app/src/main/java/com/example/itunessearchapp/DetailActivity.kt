package com.example.itunessearchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.itunessearchapp.data.Track
import com.example.itunessearchapp.data.TrackDetails
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var trackNameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var genreTextView: TextView
    private lateinit var longDescriptionTextView: TextView
    private lateinit var artWorkImageView: ImageView
    private lateinit var trackDetails: TrackDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        trackNameTextView = findViewById(R.id.trackNameTextView)
        priceTextView = findViewById(R.id.priceTextView)
        genreTextView =findViewById(R.id.genreTextView)
        longDescriptionTextView = findViewById(R.id.longDescriptionTextView)
        artWorkImageView = findViewById(R.id.artWorkImageView)

        trackDetails = intent.getParcelableExtra<TrackDetails>("track_details")!!

        if(trackDetails!=null) {
            cleanData()
            initDetails()
        }

    }

    /*cleanData() simply checks the values for null and replaces them with an empty
    * string so that the TextViews will not display the word 'null'
    */

    private fun cleanData() {
        if(trackDetails.trackName == null){
            trackDetails.trackName = ""
        }
        if(trackDetails.trackPrice == null){
            trackDetails.trackPrice = ""
        }
        if(trackDetails.currency == null){
            trackDetails.currency = ""
        }
        if(trackDetails.genre == null){
            trackDetails.genre = ""
        }
        if(trackDetails.longDescription == null){
            trackDetails.longDescription = "No description available."
        }

    }


    /*initDetails() simply initializes the views with the data that they will be displaying
    *
    * */
    private fun initDetails() {
        val currencySymbol: String? = Currency.getInstance(trackDetails!!.currency.toString()).symbol
        trackNameTextView.text = trackDetails.trackName
        priceTextView.text = "$currencySymbol${trackDetails.trackPrice.toString()}"
        genreTextView.text = trackDetails.genre.toString()
        longDescriptionTextView.text = trackDetails.longDescription.toString()

        Glide.with(this)
            .load(trackDetails.artWorkUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .fitCenter()
            .placeholder(R.drawable.ic_baseline_local_movies_24)
            .override(500,500).into(artWorkImageView)
    }
}