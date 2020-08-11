package com.example.weatherapp.data.source

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultLocationRepository @Inject constructor(private val context: Context) :
    LocationRepository {

    private var _location = MutableLiveData<LatLng>()
    val location: LiveData<LatLng>
        get() = _location

    @SuppressLint("MissingPermission")
    override fun refreshCurrentCoordinates() {
        val locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                LocationServices.getFusedLocationProviderClient(context)
                    .removeLocationUpdates(this)
                if (locationResult != null && locationResult.locations.size > 0) {
                    val latestLocationIndex = locationResult.locations.size - 1
                    val latitude = locationResult.locations[latestLocationIndex].latitude
                    val longitude = locationResult.locations[latestLocationIndex].longitude
                    _location.value = LatLng(latitude, longitude)
                }
            }
        }

        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

}