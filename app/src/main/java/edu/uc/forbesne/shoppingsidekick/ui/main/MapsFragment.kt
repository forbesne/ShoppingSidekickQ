package edu.uc.forbesne.shoppingsidekick.ui.main

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import edu.uc.forbesne.shoppingsidekick.R
import edu.uc.forbesne.shoppingsidekick.dto.Market

class MapsFragment(store: Market) : Fragment() {

    var store = store
    var latitude = store.latitude
    var longitude = store.longitude

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
        var bottomBoundary = latitude.toDouble() - .005
        var leftBoundary = longitude.toDouble() - .005
        var topBoundary = latitude.toDouble() + .005
        var rightBoundary = longitude.toDouble() + .005

        var mapBoundary = LatLngBounds(LatLng(bottomBoundary, leftBoundary), LatLng(topBoundary, rightBoundary))

        val marker = LatLng(latitude.toDouble(), longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(marker).title(store.name))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mapBoundary, 0))

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
        mapFragment?.getMapAsync(callback)
    }
}