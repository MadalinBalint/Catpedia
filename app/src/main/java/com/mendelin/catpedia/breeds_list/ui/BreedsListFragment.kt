package com.mendelin.catpedia.breeds_list.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpedia.R
import com.mendelin.catpedia.breeds_list.adapter.BreedsAdapter
import com.mendelin.catpedia.breeds_list.adapter.MarginItemDecorationVertical
import com.mendelin.catpedia.breeds_list.adapter.OnImageLoaderListener
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import com.mendelin.catpedia.breeds_list.viewmodel.BreedsViewModel
import com.mendelin.catpedia.constants.Status
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.utils.ResourceUtils
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_breeds_list.*
import javax.inject.Inject

class BreedsListFragment : DaggerFragment(R.layout.fragment_breeds_list) {

    private var internectBroadcastReceiver: BroadcastReceiver? = null

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var breedsAdapter: BreedsAdapter

    private lateinit var viewModel: BreedsViewModel
    private val searchView: SearchView? by lazy { activity?.findViewById(R.id.searchView) }

    override fun onResume() {
        super.onResume()

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, providerFactory).get(BreedsViewModel::class.java)

        searchView?.visibility = View.GONE

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filter(query?.trim() ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.filter("")
                }
                return false
            }
        })

        /* Setup UI */
        breedsAdapter.addListener(object : OnImageLoaderListener {
            override fun invoke(holder: BreedsAdapter.BreedInfoResponseViewHolder, breed: BreedInfoResponse) {
                viewModel.readBreedInfoData(breed.id)
                    .observe(viewLifecycleOwner, {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    resource.data?.let { images ->
                                        if (images.size == 1) {
                                            breed.image = images[0]
                                            holder.bind(breed)
                                        }
                                    }
                                }
                                Status.ERROR -> {
                                }
                                Status.LOADING -> {
                                }
                            }
                        }
                    })
            }
        })

        recyclerBreeds.apply {
            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = true

            addItemDecoration(MarginItemDecorationVertical(resources.getDimension(R.dimen.recyclerview_padding).toInt(), resources.getDimension(R.dimen.recyclerview_padding).toInt()))
        }

        /* Broadcast receiver to fetch data when the internet connection is back */
        if (internectBroadcastReceiver == null) {
            internectBroadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent) {
                    observeViewModel()
                }
            }
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        activity?.registerReceiver(internectBroadcastReceiver, intentFilter)

        /* Setup observers */
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()

        internectBroadcastReceiver?.let {
            activity?.unregisterReceiver(it)
        }
    }

    private fun observeViewModel() {
        if (viewModel.getOriginalBreedList().isEmpty()) {
            viewModel.readBreedsData()
                .observe(viewLifecycleOwner, { list ->
                    list?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                recyclerBreeds.visibility = View.VISIBLE
                                progressBreedsList.visibility = View.GONE
                                searchView?.visibility = View.VISIBLE
                                resource.data?.let { breeds ->
                                    viewModel.setOriginalBreedList(breeds)
                                }
                            }
                            Status.ERROR -> {
                                recyclerBreeds.visibility = View.VISIBLE
                                progressBreedsList.visibility = View.GONE
                                searchView?.visibility = View.GONE
                                ResourceUtils.showErrorAlert(requireContext(), list.message
                                    ?: getString(R.string.alert_error_unknown))
                            }
                            Status.LOADING -> {
                                progressBreedsList.visibility = View.VISIBLE
                                recyclerBreeds.visibility = View.GONE
                                searchView?.visibility = View.GONE
                            }
                        }
                    }
                })
        } else {
            searchView?.visibility = View.VISIBLE
        }

        viewModel.getBreedsList()
            .observe(viewLifecycleOwner, { list ->
                list?.let {
                    recyclerBreeds.visibility = View.GONE
                    breedsAdapter.setList(list)
                    breedsAdapter.notifyDataSetChanged()
                    recyclerBreeds.visibility = View.VISIBLE
                }
            })

        viewModel.getErrorFilter()
            .observe(viewLifecycleOwner, { error ->
                if (error.isNotEmpty()) {
                    ResourceUtils.showErrorAlert(requireContext(), error)
                    viewModel.getErrorFilter().postValue("")
                }
            })
    }
}