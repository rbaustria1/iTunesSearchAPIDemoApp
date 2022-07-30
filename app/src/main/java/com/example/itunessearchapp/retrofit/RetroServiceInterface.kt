package com.example.itunessearchapp.retrofit

import com.example.itunessearchapp.data.Track
import com.example.itunessearchapp.data.Tracks
import retrofit2.http.GET
import retrofit2.Call as Call1

interface RetroServiceInterface {
    @GET("/search?term=star&amp;country=au&amp;media=movie&amp;all")
    fun getData(): Call1<Tracks>
}