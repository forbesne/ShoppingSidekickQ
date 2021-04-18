package edu.uc.forbesne.shoppingsidekick.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class AppViewModel(application: Application): AndroidViewModel(application) {
    private val locationLiveData = LocationLiveData(application)
    internal fun getLocationLiveData() = locationLiveData
}