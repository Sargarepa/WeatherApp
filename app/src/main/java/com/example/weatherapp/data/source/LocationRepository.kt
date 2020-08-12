package com.example.weatherapp.data.source

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng

interface LocationRepository {

    fun refreshLocationData()

    fun getLastLocation() : LiveData<LatLng>
}