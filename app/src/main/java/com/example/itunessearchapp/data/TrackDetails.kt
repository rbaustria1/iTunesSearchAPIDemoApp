package com.example.itunessearchapp.data

import android.os.Parcel
import android.os.Parcelable

data class TrackDetails(
    var trackName: String?,
    var trackPrice:String?, var currency:String?, var genre: String?, var longDescription: String?, var artWorkUrl: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(trackName)
        parcel.writeString(trackPrice)
        parcel.writeString(currency)
        parcel.writeString(genre)
        parcel.writeString(longDescription)
        parcel.writeString(artWorkUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrackDetails> {
        override fun createFromParcel(parcel: Parcel): TrackDetails {
            return TrackDetails(parcel)
        }

        override fun newArray(size: Int): Array<TrackDetails?> {
            return arrayOfNulls(size)
        }
    }
}
