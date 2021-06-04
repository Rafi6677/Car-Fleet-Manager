package com.example.carfleetmanager.presentation.cars.cardetails

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.carfleetmanager.R
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.databinding.FragmentCarDetailsBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel

class CarDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCarDetailsBinding
    private lateinit var car: Car
    private lateinit var viewModel: CarFleetViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarDetailsBinding.bind(view)
        car = (activity as CarDetailsActivity).car
        viewModel = (activity as CarDetailsActivity).carFleetViewModel

        displayData()
        initButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun displayData() {
        binding.registrationNumberTextView.text = car.registration
        binding.brandValueTextView.text = car.brand
        binding.modelValueTextView.text = car.model
        binding.productionDateValueTextView.text = car.year.substringBefore("T")


        if (car.color == "#ffffff" || car.color == "#fff") {
            binding.carColorImageView.setBackgroundResource(R.drawable.ic_car2)
            binding.carColorImageView.imageTintList = null
        } else {
            ImageViewCompat.setImageTintList(
                binding.carColorImageView,
                ColorStateList.valueOf(Color.parseColor(car.color))
            )
        }

        viewModel.getOwnerById(car.ownerId)
        viewModel.owner.observe(viewLifecycleOwner, { response ->
            if (response != null) {
                binding.ownerValueTextView.text = "${response.firstName} ${response.lastName}"
            } else {
                binding.ownerValueTextView.text = resources.getString(R.string.blank)
            }
        })

        binding.latitudeValueTextView.text = car.lat
        binding.longitudeValueTextView.text = car.lng
    }

    private fun initButtons() {
        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}