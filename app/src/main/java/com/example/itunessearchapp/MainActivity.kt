package com.example.itunessearchapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.itunessearchapp.adapter.TrackListAdapter
import com.example.itunessearchapp.data.TrackDetails
import com.example.itunessearchapp.viewmodel.MainActivityViewModel
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var trackListRecyclerView: RecyclerView
    private lateinit var lastVisitTextView: TextView
    private lateinit var recyclerAdapter: TrackListAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trackListRecyclerView = findViewById(R.id.trackListRecyclerView)
        lastVisitTextView = findViewById(R.id.lastVisitTextView)

        sharedPreferences = getSharedPreferences("my_sp", MODE_PRIVATE)
        sharedPreferencesEditor =sharedPreferences.edit()



        initRecyclerView()
        initViewModel()
        showLastVisit()

    }

    override fun onPause() {
        super.onPause()
        setLastVisit()
        Log.i("mytag:","setLastVisit")
    }


    /*showLastVisit() retrieves the last_visit_date value from the shared preferences in order
      display it on the lastVisitTextView
    */
    private fun showLastVisit(){
        val lastVisit: String? = sharedPreferences.getString("last_visit_date",null)
        if(lastVisit!=null){
            lastVisitTextView.text = "Your last visit was $lastVisit"
            lastVisitTextView.visibility = VISIBLE
        }
    }

    /*setLastVisit gets an instance of the current date and saves it as a string to be retrieved from shared
       preferences once the user reopens the app
    */
    private fun setLastVisit() {
        val c: Calendar = Calendar.getInstance()
        val dateFormat:DateFormat = DateFormat.getDateInstance(DateFormat.FULL)
        val lastVisit: String = dateFormat.format(c.time)

        sharedPreferencesEditor.apply{
            putString("last_visit_date", lastVisit)
            commit()
        }
        //Log.i("mytag",lastVisit)
    }


    //Initializes the RecyclerView with its layout manager, adapter, and onItemCLick listener
    private fun initRecyclerView(){
        trackListRecyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = TrackListAdapter(this)
        trackListRecyclerView.adapter = recyclerAdapter
        recyclerAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            val trackDetails: TrackDetails = TrackDetails(it.trackName,
                it.trackPrice.toString(),
                it.currency,
                it.primaryGenreName,
                it.longDescription,
                it.artworkUrl100)

            intent.putExtra("track_details",trackDetails)
            startActivity(intent)
        }
    }

    //Initialize the ViewModel
    private fun initViewModel(){
        val viewModel:MainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this) {
            if (it != null) {
                recyclerAdapter.setTrackList(it)
                recyclerAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error on getting data.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.makeAPICall()
    }








}