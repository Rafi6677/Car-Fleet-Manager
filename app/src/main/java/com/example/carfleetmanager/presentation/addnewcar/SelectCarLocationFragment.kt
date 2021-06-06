package com.example.carfleetmanager.presentation.addnewcar

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentSelectCarLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SelectCarLocationFragment : Fragment(), OnMapReadyCallback{

    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var permissionDenied = false

    private lateinit var binding: FragmentSelectCarLocationBinding
    private lateinit var addCarViewModel: AddCarViewModel
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_map_location, container, false)
        initMapFragment()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectCarLocationBinding.bind(view)

        initButtons()
    }

    private fun initMapFragment() {
        addCarViewModel = (activity as AddNewCarActivity).addCarViewModel

        val supportMapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment

        supportMapFragment.getMapAsync(this)
    }

    private fun initButtons() {
        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        enableMyLocation()
        setOnMapClickListener()

        val latitude = arguments?.getDouble("latitude")
        val longitude = arguments?.getDouble("longitude")

        if (latitude != null && longitude != null) {
            displayCarLocation(LatLng(latitude, longitude))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
            setOnMapClickListener()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                activity as AddNewCarActivity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun setOnMapClickListener() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
                activity as AddNewCarActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.setOnMapClickListener { latLng ->
                map.clear()

                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    latLng, 16f
                )

                map.animateCamera(cameraUpdate)
                map.addMarker(MarkerOptions().position(latLng))
                map.setOnMarkerClickListener { marker ->
                    showSaveLocationDialog(marker.position)
                    true
                }
                showSaveLocationDialog(latLng)
            }
        } else {
            ActivityCompat.requestPermissions(
                activity as AddNewCarActivity,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun displayCarLocation(carPosition: LatLng) {
        val markerOptions = MarkerOptions().apply {
            position(carPosition)
        }
        map.apply {
            clear()
            animateCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 16f))
            addMarker(markerOptions)
        }
    }

    private fun showSaveLocationDialog(latLng: LatLng) {
        val dialog = AlertDialog.Builder(activity as AddNewCarActivity)
            .setTitle("Chosen car location")
            .setMessage("Latitude: ${latLng.latitude}\nLongitude: ${latLng.longitude}")
            .setPositiveButton("Save") { _, _ ->
                addCarViewModel.latitude = latLng.latitude.toString()
                addCarViewModel.longitude = latLng.longitude.toString()
                requireActivity().onBackPressed()
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
        }
        dialog.show()

        val window: Window = dialog.window!!
        val wlp: WindowManager.LayoutParams = window.attributes

        wlp.gravity = Gravity.BOTTOM
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

}