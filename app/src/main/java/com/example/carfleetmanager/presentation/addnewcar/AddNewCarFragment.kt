package com.example.carfleetmanager.presentation.addnewcar

import android.annotation.SuppressLint
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
import com.example.carfleetmanager.presentation.carlist.CarsActivity
import com.example.carfleetmanager.presentation.util.ConnectionUtils
import com.google.android.material.snackbar.Snackbar
import yuku.ambilwarna.AmbilWarnaDialog
import java.util.*

class AddNewCarFragment : Fragment() {

    private lateinit var binding: FragmentAddNewCarBinding
    private lateinit var viewModel: CarFleetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddNewCarBinding.bind(view)
        viewModel = (activity as AddNewCarActivity).viewModel

        initData()
        initButtons()
    }

    private fun initData() {
        binding.registrationNumberTextInput.editText!!.setText(viewModel.registrationNumber)
        binding.brandTextInput.editText!!.setText(viewModel.brand)
        binding.modelTextInput.editText!!.setText(viewModel.model)
        binding.productionDateValueTextView.text = viewModel.productionDate

        if (viewModel.color == "#ffffff") {
            binding.carColorImageView.setImageResource(R.drawable.ic_car2)
        } else {
            ImageViewCompat.setImageTintList(
                binding.carColorImageView,
                ColorStateList.valueOf(Color.parseColor(viewModel.color))
            )
        }

        binding.ownerValueTextView.text = viewModel.ownerName
        binding.latitudeValueTextView.text = viewModel.latitude
        binding.longitudeValueTextView.text = viewModel.longitude
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
        binding.registrationNumberTextInput.editText!!.doOnTextChanged { text, _, _, _ ->
            if (text!!.isEmpty()) {
                binding.registrationNumberTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.registrationNumberTextInput.error = null
            }

            viewModel.registrationNumber = text.toString()
        }
        binding.brandTextInput.editText!!.doOnTextChanged { text, _, _, _ ->
            if (text!!.isEmpty()) {
                binding.brandTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.brandTextInput.error = null
            }

            viewModel.brand = text.toString()
        }
        binding.modelTextInput.editText!!.doOnTextChanged { text, _, _, _ ->
            if (text!!.isEmpty()) {
                binding.modelTextInput.error = resources.getString(R.string.empty_field_error)
            } else {
                binding.modelTextInput.error = null
            }

            viewModel.model = text.toString()
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
                var monthValue = (mMonth + 1).toString()

                if (mDay < 10)
                    dayValue = "0$dayValue"

                if (mMonth < 10)
                    monthValue = "0$monthValue"

                binding.productionDateValueTextView.text = "$dayValue/$monthValue/$mYear"
                viewModel.productionDate = "$dayValue/$monthValue/$mYear"
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
            val initialColor = Color.parseColor(viewModel.color)

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

                        viewModel.color = "#${Integer.toHexString(color).substring(2)}"
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
            } else if (viewModel.latitude == "-" || viewModel.longitude == "-") {
                findNavController().navigate(
                    R.id.action_addNewCarFragment_to_selectCarLocationFragment
                )
            } else {
                val bundle = Bundle().apply {
                    putDouble("latitude", viewModel.latitude.toDouble())
                    putDouble("longitude", viewModel.longitude.toDouble())
                }

                findNavController().navigate(
                    R.id.action_addNewCarFragment_to_selectCarLocationFragment,
                    bundle
                )
            }
        }
    }

    private fun saveCar(view: View) {
        if (isDataCorrectToSave(view)) {
            showProgressBar()
            viewModel.savedCarResponse.postValue(null)
            viewModel.saveNewCar()
            viewModel.savedCarResponse.observe(viewLifecycleOwner, { response ->
                hideProgressBar()
                if (response != null) {
                    val bundle = Bundle().apply {
                        putBoolean("update_order", true)
                    }
                    val intent = Intent(requireActivity(), CarsActivity::class.java).apply {
                        putExtras(bundle)
                    }

                    (activity as AddNewCarActivity).finishAffinity()
                    startActivity(intent)
                } else {
                    Snackbar.make(view, resources.getString(R.string.saving_error), Snackbar.LENGTH_LONG)
                        .show()
                }
            })
        } else {
            Snackbar.make(view, resources.getString(R.string.empty_data_saving_error), Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    private fun isDataCorrectToSave(view: View): Boolean {
        var isDataCorrect = true

        if (viewModel.registrationNumber == "") {
            isDataCorrect = false
            binding.registrationNumberTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (viewModel.brand == "") {
            isDataCorrect = false
            binding.brandTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (viewModel.model == "") {
            isDataCorrect = false
            binding.modelTextInput.error = resources.getString(R.string.empty_field_error)
        }
        if (viewModel.productionDate == "__ / __ / ____") {
            isDataCorrect = false
        }
        if (viewModel.ownerName == "-") {
            isDataCorrect = false
        }
        if(viewModel.latitude == "-" || viewModel.longitude == "-") {
            isDataCorrect = false
        }
        if (!ConnectionUtils.isNetworkAvailable(activity as AddNewCarActivity)) {
            Snackbar.make(view, resources.getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG)
                    .show()
            isDataCorrect = false
        }

        return isDataCorrect
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}