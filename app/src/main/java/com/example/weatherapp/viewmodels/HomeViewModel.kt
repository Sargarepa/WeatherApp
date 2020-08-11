package com.example.weatherapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.source.DefaultLocationRepository
import com.example.weatherapp.data.source.DefaultWeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val defaultWeatherRepository: DefaultWeatherRepository
) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var _buttonClicked = MutableLiveData<Boolean>()

    val buttonClicked: LiveData<Boolean>
        get() = _buttonClicked

    val weather = defaultWeatherRepository.observeWeather(viewModelScope)

    fun doneClicking() {
        _buttonClicked.value = null
    }

    fun setButtonClicked() {
        _buttonClicked.value = true
    }

    fun refreshCurrentCoordinates(defaultLocationRepository: DefaultLocationRepository) {
        defaultLocationRepository.refreshCurrentCoordinates()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}