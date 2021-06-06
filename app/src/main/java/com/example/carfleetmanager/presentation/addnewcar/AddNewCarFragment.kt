package com.example.carfleetmanager.presentation.addnewcar

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentAddNewCarBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.example.carfleetmanager.presentation.cardetails.CarDetailsActivity
import com.example.carfleetmanager.presentation.util.ConnectionUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*

class AddNewCarFragment : Fragment() {

    private lateinit var binding: FragmentAddNewCarBinding
    private lateinit var carFleetViewModel: CarFleetViewModel
    private lateinit var addCarViewModel: AddCarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewCarBinding.bind(view)
        carFleetViewModel = (activity as AddNewCarActivity).carFleetViewModel
        addCarViewModel = (activity as AddNewCarActivity).addCarViewModel

        initData()
        initButtons()
    }

    private fun initData() {
        binding.registrationNumberTextInput.editText!!.setText(addCarViewModel.registrationNumber)
        binding.brandTextInput.editText!!.setText(addCarViewModel.brand)
        binding.modelTextInput.editText!!.setText(addCarViewModel.model)
        binding.productionDateValueTextView.text = addCarViewModel.productionDate

        if (addCarViewModel.color == "#ffffff") {
            binding.carColorImageView.setImageResource(R.drawable.ic_car2)
        } else {
            ImageViewCompat.setImageTintList(
                binding.carColorImageView,
                ColorStateList.valueOf(Color.parseColor(addCarViewModel.color))
            )
        }

        binding.ownerValueTextView.text = addCarViewModel.ownerName
        binding.latitudeValueTextView.text = addCarViewModel.latitude
        binding.longitudeValueTextView.text = addCarViewModel.longitude
    }

    private fun initButtons() {
        initInputData()
        initProductionDatePicker()
        initCarColorPicker()
        initOwnerPicker()
        initLocationPicker()

        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.saveCarButton.setOnClickListener {
           if (!ConnectionUtils.isNetworkAvailable(activity as AddNewCarActivity)) {
                Snackbar.make(it, resources.getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG)
                        .show()
            } else {
               saveCar(it)
           }
        }
    }

    private fun initInputData() {
        binding.registrationNumberTextInput.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                binding.registrationNumberTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.registrationNumberTextInput.error = null
            }

            addCarViewModel.registrationNumber = text.toString()
        }
        binding.brandTextInput.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                binding.brandTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.brandTextInput.error = null
                addCarViewModel
            }

            addCarViewModel.brand = text.toString()
        }
        binding.modelTextInput.editText!!.doOnTextChanged { text, start, before, count ->
            if (text!!.isEmpty()) {
                binding.modelTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.modelTextInput.error = null
            }

            addCarViewModel.model = text.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initProductionDatePicker() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        binding.productionDateCardView.setOnClickListener {
            val dialog = DatePickerDialog(requireActivity(), { _, mYear, mMonth, mDay ->
                var dayValue = mDay.toString()
                var monthValue = mMonth.toString()

                if (mDay < 10)
                    dayValue = "0$dayValue"

                if (mMonth < 10)
                    monthValue = "0$monthValue"

                binding.productionDateValueTextView.text = "$dayValue/$monthValue/$mYear"
                addCarViewModel.productionDate = "$dayValue/$monthValue/$mYear"
            }, year, month, day)

            dialog.show()
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
        }
    }

    private fun initCarColorPicker() {
        binding.colorCardView.setOnClickListener {
            val initialColor = Color.parseColor(addCarViewModel.color)

            val dialog = AmbilWarnaDialog(
                requireActivity(),
                initialColor,
                object : AmbilWarnaDialog.OnAmbilWarnaListener {
                    override fun onCancel(dialog: AmbilWarnaDialog?) {
                        if (initialColor == -1) {
                            binding.carColorImageView.setBackgroundResource(R.drawable.ic_car2)
                            binding.carColorImageView.imageTintList = null
                        } else {
                            ImageViewCompat.setImageTintList(
                                binding.carColorImageView,
                                ColorStateList.valueOf(initialColor)
                            )
                        }
                    }

                    override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                        if (color == -1) {
                            binding.carColorImageView.setBackgroundResource(R.drawable.ic_car2)
                            binding.carColorImageView.imageTintList = null
                        } else {
                            ImageViewCompat.setImageTintList(
                                binding.carColorImageView,
                                ColorStateList.valueOf(color)
                            )
                        }

                        addCarViewModel.color = "#${Integer.toHexString(color).substring(2)}"
                    }
                }
            )

            dialog.show()
            dialog.dialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
            dialog.dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorAccent))
        }
    }

    private fun initOwnerPicker() {
        binding.ownerCardView.setOnClickListener {
            findNavController().navigate(
                R.id.action_addNewCarFragment_to_selectOwnerFragment
            )
        }
    }

    private fun initLocationPicker() {
        binding.coordinatesCardView.setOnClickListener { view ->
            if (!ConnectionUtils.isNetworkAvailable(activity as AddNewCarActivity)) {
                Snackbar.make(view, resources.getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG)
                    .show()
            } else if (addCarViewModel.latitude == "-" || addCarViewModel.longitude == "-") {
                findNavController().navigate(
                    R.id.action_addNewCarFragment_to_selectCarLocationFragment
                )
            } else {
                val bundle = Bundle().apply {
                    putDouble("latitude", addCarViewModel.latitude.toDouble())
                    putDouble("longitude", addCarViewModel.longitude.toDouble())
                }

                findNavController().navigate(
                    R.id.action_addNewCarFragment_to_selectCarLocationFragment,
                    bundle
                )
            }
        }
    }

    private fun saveCar(view: View) {
        if (isDataCorrectToSave()) {

        } else {
            Snackbar.make(view, resources.getString(R.string.empty_data_saving_error), Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private fun isDataCorrectToSave(): Boolean {
        var isDataCorrect = true

        if (addCarViewModel.registrationNumber == "") {
            isDataCorrect = false
            binding.registrationNumberTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (addCarViewModel.brand == "") {
            isDataCorrect = false
            binding.brandTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (addCarViewModel.model == "") {
            isDataCorrect = false
            binding.modelTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (addCarViewModel.productionDate == "__ / __ / ____") {
            isDataCorrect = false
        }
        if (addCarViewModel.ownerName == "-") {
            isDataCorrect = false
        }
        if(addCarViewModel.latitude == "-" || addCarViewModel.longitude == "-") {
            isDataCorrect = false
        }

        return isDataCorrect
    }

}