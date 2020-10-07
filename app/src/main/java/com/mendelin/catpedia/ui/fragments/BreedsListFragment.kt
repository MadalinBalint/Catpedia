package com.mendelin.catpedia.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpedia.R
import com.mendelin.catpedia.adapter.breeds_list.BreedsAdapter
import com.mendelin.catpedia.adapter.breeds_list.MarginItemDecorationVertical
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.databinding.FragmentBreedsListBinding
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.ui.activity.ActivityCallback
import com.mendelin.catpedia.viewmodels.BreedsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class BreedsListFragment : DaggerFragment(R.layout.fragment_breeds_list) {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var breedsAdapter: BreedsAdapter

    private val viewModel: BreedsViewModel by activityViewModels { providerFactory }
    private lateinit var binding: FragmentBreedsListBinding
    private var activityCallback: ActivityCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActivityCallback) {
            activityCallback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreedsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        toolbar.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityCallback?.showSearchBar(false)

        binding.recyclerBreeds.apply {
            adapter = breedsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            isNestedScrollingEnabled = true

            addItemDecoration(
                MarginItemDecorationVertical(
                    resources.getDimension(R.dimen.recyclerview_padding).toInt(),
                    resources.getDimension(R.dimen.recyclerview_padding).toInt()
                )
            )
        }

        observeViewModel()

        /* Fetch data only on first load */
        if (viewModel.getBreedsList().value == null) {
            viewModel.fetchBreeds()
        }
    }

    private fun observeViewModel() {
        viewModel.getLoadingObservable().observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBreedsList.visibility = View.VISIBLE
                binding.recyclerBreeds.visibility = View.GONE
                activityCallback?.showSearchBar(false)
            } else {
                binding.progressBreedsList.visibility = View.GONE
                binding.recyclerBreeds.visibility = View.VISIBLE
                activityCallback?.showSearchBar(true)
            }
        })

        viewModel.getBreedsList()
            .observe(viewLifecycleOwner, { list ->
                breedsAdapter.setList(list ?: emptyList())
            })

        viewModel.getErrorFilter()
            .observe(viewLifecycleOwner, { error ->
                if (error.isNotEmpty()) {
                    showErrorAlert(requireContext(), error)
                    viewModel.onErrorHandled()
                }
            })
    }

    private fun showErrorAlert(context: Context, msg: String) {
        val alert = AlertBox()

        alert.setPositiveButtonListener { dialog, _ ->
            dialog.dismiss()
        }

        alert.showAlert(
            context,
            context.getString(R.string.alert_error),
            msg,
            context.getString(R.string.alert_ok),
            null
        )
    }
}