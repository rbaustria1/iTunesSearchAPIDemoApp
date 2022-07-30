package com.example.itunessearchapp.adapter

import android.app.Activity
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.itunessearchapp.R
import com.example.itunessearchapp.data.Track
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import org.w3c.dom.Text
import java.util.*


class TrackListAdapter(val activity:Activity):RecyclerView.Adapter<TrackListAdapter.MyViewHolder>() {
    private var trackList: List<Track>? = null

    var onItemClick: ((Track)->Unit)? = null

    fun setTrackList(trackList: List<Track>?){
        this.trackList = trackList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(trackList?.get(position)!!, activity)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(trackList!![position])
        }
    }

    override fun getItemCount(): Int {
        if(trackList == null){
            return 0
        }
        else {
            return trackList?.size!!
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val artWorkImageView:ImageView = view.findViewById(R.id.artWorkImageView)
        val trackNameTextView:TextView = view.findViewById(R.id.trackNameTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
        val genreTextView: TextView = view.findViewById(R.id.genreTextView)

        fun bind(data: Track, activity: Activity){
            val currencySymbol: String? = Currency.getInstance(data.currency.toString()).symbol
            trackNameTextView.text = data.trackName
            priceTextView.text = "$currencySymbol${data.trackPrice.toString()}"
            genreTextView.text = data.primaryGenreName.toString()

            Glide.with(activity)
                .load(data.artworkUrl100)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .placeholder(R.drawable.ic_baseline_local_movies_24)
                .override(500,500).into(artWorkImageView)
        }

    }
}