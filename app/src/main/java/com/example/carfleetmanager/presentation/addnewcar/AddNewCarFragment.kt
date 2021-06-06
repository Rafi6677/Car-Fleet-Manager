package com.example.carfleetmanager.presentation.addnewcar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentAddNewCarBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.example.carfleetmanager.presentation.util.ConnectionUtils
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
        initProductionDatePicker()
        initCarColorPicker()
        initOwnerPicker()

        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.saveCarButton.setOnClickListener {
           if (!ConnectionUtils.isNetworkAvailable(activity as AddNewCarActivity)) {
                Snackbar.make(it, resources.getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG)
                        .show()
            } else {
               saveCar()
           }
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

    private fun saveCar() {

    }

    private fun isDataCorrectToSave(): Boolean {
        if (addCarViewModel.registrationNumber == ""
                || addCarViewModel.registrationNumber.length < 3) {
            return false
        } else if (addCarViewModel.brand == "" || addCarViewModel.model == "") {
            return false
        } else if (addCarViewModel.productionDate == "__ / __ / ____") {
            return false
        } else if (addCarViewModel.ownerName == "-") {
            return false
        } else if(addCarViewModel.latitude == "-" || addCarViewModel.longitude == "-") {
            return false
        }

        return true
    }

}