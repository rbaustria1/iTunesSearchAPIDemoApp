package com.example.itunessearchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.itunessearchapp.data.Track
import com.example.itunessearchapp.data.Tracks
import com.example.itunessearchapp.retrofit.RetroInstance
import com.example.itunessearchapp.retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel() {
    lateinit var liveDataList: MutableLiveData<List<Track>>
    init {
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<List<Track>>{
        return liveDataList
    }


/*makeAPICall() function initiates the http request*/
    fun makeAPICall(){
        val retroInstance = RetroInstance.getRetroInstance()
        val retroServiceInterface = retroInstance.create(RetroServiceInterface::class.java)
        val call =retroServiceInterface.getData()
        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {

//                Log.i("Mytag", response.body().toString())
                val trackList: List<Track> = response.body()!!.results

                liveDataList.postValue(trackList)

            }

            override fun onFailure(call: Call<Tracks>, t: Throwable) {
 //               Log.i("Mytag", t.message.toString())
                liveDataList.postValue(null)
            }
        })
    }
}