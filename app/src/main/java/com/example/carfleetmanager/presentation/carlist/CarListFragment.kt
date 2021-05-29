package com.example.carfleetmanager.presentation.carlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfleetmanager.R
import com.example.carfleetmanager.databinding.FragmentCarListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CarListFragment : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var viewModel: CarListViewModel
    private lateinit var carsAdapter: CarsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_car_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCarListBinding.bind(view)
        viewModel = (activity as CarListActivity).viewModel
        carsAdapter = CarsAdapter()

        initRecyclerView()
        displayCarList()
    }

    private fun initRecyclerView() {
        binding.carListRecyclerView.apply {
            adapter = carsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displayCarList() {
        hideNoDataFoundInfo()
        showProgressBar()

        viewModel.getCarList()
        viewModel.carList.observe(viewLifecycleOwner, { response ->
            if (response != null) {
                hideProgressBar()
                carsAdapter.differ.submitList(response)
                carsAdapter.notifyDataSetChanged()
            } else {
                hideProgressBar()
                showNoDataFoundInfo()
                Snackbar.make(requireView(), resources.getString(R.string.no_car_data_found), Snackbar.LENGTH_LONG)
                        .show()
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showNoDataFoundInfo() {
        binding.noCarDataFoundTextView.visibility = View.VISIBLE
    }

    private fun hideNoDataFoundInfo() {
        binding.noCarDataFoundTextView.visibility = View.INVISIBLE
    }

}