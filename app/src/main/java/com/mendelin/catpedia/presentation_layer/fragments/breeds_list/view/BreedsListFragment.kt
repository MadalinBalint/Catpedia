package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpedia.R
import com.mendelin.catpedia.common.ResourceUtils
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.networking.models.BreedInfoResponse
import com.mendelin.catpedia.presentation_layer.fragments.BaseFragment
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.BreedsAdapter
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.MarginItemDecorationVertical
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel.BreedsViewModel
import kotlinx.android.synthetic.main.fragment_breeds_list.*

class BreedsListFragment : BaseFragment(R.layout.fragment_breeds_list) {

    private lateinit var viewModel: BreedsViewModel
    private lateinit var breedsAdapter: BreedsAdapter
    private lateinit var searchView: SearchView

    private val originalBreedList: ArrayList<BreedInfoResponse> = arrayListOf()

    override fun onResume() {
        super.onResume()

        toolbarOn()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView = activity?.findViewById(R.id.searchView)!!

        searchView.visibility = View.GONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.query.value = query?.trim() ?: ""
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.query.value = ""
                }
                return false
            }
        })

        /* Setup view model */
        viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        /* Setup UI */
        breedsAdapter = BreedsAdapter(viewModel, viewLifecycleOwner)
        recyclerBreeds.apply {
            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = true

            addItemDecoration(MarginItemDecorationVertical(resources.getDimension(R.dimen.recyclerview_padding).toInt(), resources.getDimension(R.dimen.recyclerview_padding).toInt()))
        }

        /* Setup observers */
        observeViewModel()
    }

    private fun observeViewModel() {
        if (originalBreedList.isEmpty()) {
            viewModel.getBreedsList().observe(viewLifecycleOwner, { list ->
                list?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerBreeds.visibility = View.VISIBLE
                            progressBreedsList.visibility = View.GONE
                            searchView.visibility = View.VISIBLE
                            resource.data?.let { breeds ->
                                originalBreedList.clear()
                                originalBreedList.addAll(breeds)

                                viewModel.breedsList.value = originalBreedList
                            }
                        }
                        Status.ERROR -> {
                            recyclerBreeds.visibility = View.VISIBLE
                            progressBreedsList.visibility = View.GONE
                            searchView.visibility = View.GONE
                            ResourceUtils.showErrorAlert(requireContext(), list.message
                                ?: getString(R.string.alert_error_unknown))
                        }
                        Status.LOADING -> {
                            progressBreedsList.visibility = View.VISIBLE
                            recyclerBreeds.visibility = View.GONE
                            searchView.visibility = View.GONE
                        }
                    }
                }
            })
        } else {
            searchView.visibility = View.VISIBLE
            viewModel.breedsList.value = originalBreedList
        }

        viewModel.breedsList.observe(viewLifecycleOwner, { list ->
            list?.let {
                recyclerBreeds.visibility = View.GONE
                breedsAdapter.setList(list)
                breedsAdapter.notifyDataSetChanged()
                recyclerBreeds.visibility = View.VISIBLE
            }
        })

        viewModel.query.observe(viewLifecycleOwner, { query ->
            query?.let {
                if (query.isNotEmpty()) {
                    val filteredList = originalBreedList.filter { it.origin?.toLowerCase() == query.toLowerCase() || it.origin?.toLowerCase()?.startsWith(query.toLowerCase()) == true }

                    if (filteredList.isEmpty()) {
                        ResourceUtils.showErrorAlert(requireContext(), "The country ${query} doesn't exist in our list.")
                    } else
                        viewModel.breedsList.value = ArrayList(filteredList)
                } else
                    viewModel.breedsList.value = originalBreedList
            }
        })
    }
}