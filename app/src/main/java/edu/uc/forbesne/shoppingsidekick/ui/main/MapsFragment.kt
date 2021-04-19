package edu.uc.forbesne.shoppingsidekick.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.LocationDetails
import edu.uc.forbesne.shoppingsidekick.dto.Market
import kotlinx.android.synthetic.main.fragment_maps.*
import java.math.RoundingMode

class MapsFragment(store: Market) : Fragment() {

    var store = store
    var latitude = store.latitude
    var longitude = store.longitude
    private val LOCATION_PERMISSION_REQUEST_CODE = 1702

    private lateinit var locationDetails: LocationDetails
    private lateinit var appViewModel: AppViewModel

    private lateinit var mMap : GoogleMap
    private var mapReady = false

    companion object {
        fun newInstance(store: Market) = MapsFragment(store)
    }
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        mMap = googleMap
        mapReady = true

        var bottomBoundary = latitude.toDouble() - .005
        var leftBoundary = longitude.toDouble() - .005
        var topBoundary = latitude.toDouble() + .005
        var rightBoundary = longitude.toDouble() + .005

        var mapBoundary = LatLngBounds(LatLng(bottomBoundary, leftBoundary), LatLng(topBoundary, rightBoundary))

        val marker = LatLng(latitude.toDouble(), longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(marker).title(store.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBoundary, 0))
        googleMap.uiSettings.setZoomControlsEnabled(true)

    }

    internal fun requestLocationUpdates() {
        appViewModel = ViewModelProviders.of(this).get(AppViewModel::class.java)

        appViewModel.getLocationLiveData().observeForever{
            locationDetails = it
            updateMap()
        }
    }

    private fun updateMap() {
        if (mapReady && locationDetails != null) {
                if (!locationDetails.longitude.isEmpty() && !locationDetails.latitude.isEmpty()) {
                    var currLatitude = locationDetails.latitude.toDouble()
                    var currLongitude = locationDetails.longitude.toDouble()
                    val marker = LatLng(currLatitude, currLongitude)
                    var bottomBoundary = latitude.toDouble() - .005
                    var leftBoundary = longitude.toDouble() - .005
                    var topBoundary = latitude.toDouble() + .005
                    var rightBoundary = longitude.toDouble() + .005
                    if (currLongitude > longitude.toDouble()) {
                        rightBoundary = currLongitude + .005
                    }
                    else {
                        leftBoundary = currLongitude - .005
                    }
                    if (currLatitude > latitude.toDouble()) {
                        topBoundary = currLatitude + .005
                    }
                    else {
                        bottomBoundary = currLatitude - .005
                    }


                    var mapBoundary = LatLngBounds(LatLng(bottomBoundary, leftBoundary), LatLng(topBoundary, rightBoundary))
                    mMap.addMarker(MarkerOptions().position(marker).title("Your location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBoundary, 0))

                    val milesDistance = getDistanceInMiles(latitude.toDouble(), longitude.toDouble(), currLatitude, currLongitude)
                    txtDistance.text = "Distance: " + milesDistance.toString() + " miles"

                }

            }
    }

    private fun getDistanceInMiles(firstLatitude: Double, firstLongitude: Double,
                                   secondLatitude: Double, secondLongitude: Double): Double {
        val resultMeters = FloatArray(1)
        Location.distanceBetween(firstLatitude, firstLongitude,
                secondLatitude, secondLongitude, resultMeters)
        val resultMiles = resultMeters[0]*0.000621371192
        val distanceMiles = (resultMiles).toBigDecimal().setScale(1, RoundingMode.UP).toDouble()
        return distanceMiles
    }

    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(context!!, "Unable to update location without permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        prepRequestLocationUpdates()
        mapFragment?.getMapAsync(callback)
    }
}