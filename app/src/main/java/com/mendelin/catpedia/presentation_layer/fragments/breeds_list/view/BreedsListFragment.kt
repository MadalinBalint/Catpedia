package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpedia.R
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse
import com.mendelin.catpedia.presentation_layer.fragments.BaseFragment
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.BreedsAdapter
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.MarginItemDecorationVertical
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel.BreedsViewModel
import kotlinx.android.synthetic.main.fragment_breeds_list.*

class BreedsListFragment : BaseFragment(R.layout.fragment_breeds_list) {

    private lateinit var viewModel: BreedsViewModel
    private lateinit var breedAdapter: BreedsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Setup view model */
        viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        /* Setup UI */
        breedAdapter = BreedsAdapter(arrayListOf(), viewModel, viewLifecycleOwner, findNavController())
        recyclerBreeds.apply {
            adapter = breedAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = true

            addItemDecoration(MarginItemDecorationVertical(resources.getDimension(R.dimen.recyclerview_padding).toInt(), resources.getDimension(R.dimen.recyclerview_padding).toInt()))
        }

        /* Setup observers */
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getBreedsList().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerBreeds.visibility = View.VISIBLE
                        progressBreedsList.visibility = View.GONE
                        resource.data?.let { breeds -> retrieveList(breeds) }
                    }
                    Status.ERROR -> {
                        recyclerBreeds.visibility = View.VISIBLE
                        progressBreedsList.visibility = View.GONE
                        showErrorAlert(requireContext(), it.message
                            ?: getString(R.string.alert_error_unknown))
                    }
                    Status.LOADING -> {
                        progressBreedsList.visibility = View.VISIBLE
                        recyclerBreeds.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(breeds: List<BreedInfoResponse>) {
        breedAdapter.apply {
            addBreeds(breeds)
            notifyDataSetChanged()
        }
    }
}