package com.example.carfleetmanager.presentation.carlist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfleetmanager.R
import com.example.carfleetmanager.data.model.Car
import com.example.carfleetmanager.databinding.FragmentCarListBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.example.carfleetmanager.presentation.cardetails.CarDetailsActivity
import com.example.carfleetmanager.presentation.util.ConnectionUtils
import com.google.android.material.snackbar.Snackbar

class CarListFragment : Fragment() {

    private lateinit var binding: FragmentCarListBinding
    private lateinit var viewModel: CarFleetViewModel
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
        viewModel = (activity as CarsActivity).viewModel
        carsAdapter = CarsAdapter()
        carsAdapter.setOnItemClickListener {
            onItemClickListener(it)
        }

        initRecyclerView()
        displayCarList()
        initSwipeContainer(view)
    }

    private fun initRecyclerView() {
        binding.carListRecyclerView.apply {
            adapter = carsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onItemClickListener(car: Car) {
        val bundle = Bundle().apply {
            putSerializable("car", car)
        }
        val intent = Intent(requireActivity(), CarDetailsActivity::class.java).apply {
            putExtras(bundle)
        }

        startActivity(intent)
    }

    private fun displayCarList() {
        hideNoDataFoundInfo()
        showProgressBar()

        viewModel.getOwnerList()
        viewModel.getCarList()
        viewModel.carList.observe(viewLifecycleOwner, { response ->
            if (response.isNotEmpty()) {
                hideProgressBar()
                hideNoDataFoundInfo()
                carsAdapter.differ.submitList(response)
                carsAdapter.notifyDataSetChanged()
            } else {
                hideProgressBar()
                showNoDataFoundInfo()
                Snackbar.make(requireView(), resources.getString(R.string.check_internet_connection_no_car_data), Snackbar.LENGTH_LONG)
                        .show()
            }
        })
    }

    private fun initSwipeContainer(view: View) {
        binding.swipeRefreshContainer.let {
            it.setOnRefreshListener {
                updateData(view)
                it.isRefreshing = false
            }
        }
    }

    private fun updateData(view: View) {
        if (!ConnectionUtils.isNetworkAvailable(activity as CarsActivity)) {
            Snackbar.make(view, resources.getString(R.string.check_internet_connection), Snackbar.LENGTH_LONG)
                    .show()
        } else {
            carsAdapter.differ.submitList(emptyList())
            showProgressBar()
            viewModel.updateOwnerList()
            viewModel.updateCarList()
        }
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