package com.example.carfleetmanager.presentation.addnewcar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carfleetmanager.R
import com.example.carfleetmanager.data.model.Owner
import com.example.carfleetmanager.databinding.FragmentSelectOwnerBinding
import com.example.carfleetmanager.presentation.CarFleetViewModel
import com.example.carfleetmanager.presentation.util.ConnectionUtils
import com.google.android.material.snackbar.Snackbar

class SelectOwnerFragment : Fragment() {

    private lateinit var binding: FragmentSelectOwnerBinding
    private lateinit var ownersAdapter: OwnersAdapter
    private lateinit var viewModel: CarFleetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_owner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSelectOwnerBinding.bind(view)
        viewModel = (activity as AddNewCarActivity).viewModel
        ownersAdapter = OwnersAdapter()
        ownersAdapter.setOnItemClickListener { owner ->
            onItemClickListener(owner)
        }

        initRecyclerView()
        displayOwnerList()
        initSwipeContainer(view)
        initButtons()
    }

    private fun initRecyclerView() {
        binding.ownerListRecyclerView.apply {
            adapter = ownersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun displayOwnerList() {
        hideNoDataFoundInfo()
        showProgressBar()

        viewModel.getOwnerList()
        viewModel.ownerList.observe(viewLifecycleOwner, { response ->
            if (response != null) {
                hideProgressBar()
                ownersAdapter.differ.submitList(response)
                ownersAdapter.notifyDataSetChanged()
            } else {
                hideProgressBar()
                showNoDataFoundInfo()
                Snackbar.make(requireView(), resources.getString(R.string.no_owner_data_found), Snackbar.LENGTH_LONG)
                        .show()
            }
        })
    }

    private fun onItemClickListener(owner: Owner) {
        viewModel.ownerId = owner.id
        viewModel.ownerName = "${owner.firstName} ${owner.lastName}"

        requireActivity().onBackPressed()
    }

    private fun initSwipeContainer(view: View) {
        binding.swipeRefreshContainer.let {
            it.setOnRefreshListener {
                updateData(view)
                it.isRefreshing = false
            }
        }
    }

    private fun initButtons() {
        binding.backImageButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.updateImageButton.setOnClickListener {
            updateData(it)
        }
    }

    private fun updateData(view: View) {
        if (!ConnectionUtils.isNetworkAvailable(activity as AddNewCarActivity)) {
            Snackbar.make(view, resources.getString(R.string.no_owner_data_found), Snackbar.LENGTH_LONG)
                    .show()
        } else {
            ownersAdapter.differ.submitList(emptyList())
            showProgressBar()
            viewModel.updateOwnerList()
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showNoDataFoundInfo() {
        binding.noOwnerDataFoundTextView.visibility = View.VISIBLE
    }

    private fun hideNoDataFoundInfo() {
        binding.noOwnerDataFoundTextView.visibility = View.INVISIBLE
    }

}