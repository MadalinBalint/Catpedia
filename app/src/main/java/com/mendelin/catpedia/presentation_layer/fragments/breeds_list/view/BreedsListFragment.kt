package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.OnImageLoaderListener
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel.BreedsViewModel
import kotlinx.android.synthetic.main.fragment_breeds_list.*

class BreedsListFragment : BaseFragment(R.layout.fragment_breeds_list) {

    var internectBroadcastReceiver: BroadcastReceiver? = null

    private lateinit var viewModel: BreedsViewModel
    private lateinit var breedsAdapter: BreedsAdapter
    private lateinit var searchView: SearchView

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

        /* Setup view model */
        viewModel = ViewModelProvider(this).get(BreedsViewModel::class.java)

        /* Setup UI */
        breedsAdapter = BreedsAdapter(object : OnImageLoaderListener {
            override fun invoke(holder: BreedsAdapter.BreedInfoResponseViewHolder, breed: BreedInfoResponse) {
                viewModel.getBreedImage(breed.id).observe(viewLifecycleOwner, {
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
            viewModel.getBreedsList().observe(viewLifecycleOwner, { list ->
                list?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerBreeds.visibility = View.VISIBLE
                            progressBreedsList.visibility = View.GONE
                            searchView.visibility = View.VISIBLE
                            resource.data?.let { breeds ->
                                viewModel.setOriginalBreedList(breeds)
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
        }

        viewModel.breedsList.observe(viewLifecycleOwner, { list ->
            list?.let {
                recyclerBreeds.visibility = View.GONE
                breedsAdapter.setList(list)
                breedsAdapter.notifyDataSetChanged()
                recyclerBreeds.visibility = View.VISIBLE
            }
        })

        viewModel.errorFilter.observe(viewLifecycleOwner, { error ->
            if (error.isNotEmpty()) {
                ResourceUtils.showErrorAlert(requireContext(), error)
                viewModel.errorFilter.value = ""
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (connectivityManager != null) {
            val activeNetworkInfo = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return false
    }
}