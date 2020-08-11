package com.example.weatherapp.data.source

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

class DefaultLocationRepository constructor(activity: Activity) : LocationRepository {

    var _coordinates = MutableLiveData<LatLng>()
    val coordinates: LiveData<LatLng>
        get() = _coordinates

    fun refreshCurrentCoordinates() {

    }

}