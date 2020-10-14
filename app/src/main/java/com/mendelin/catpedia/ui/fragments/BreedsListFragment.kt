package com.mendelin.catpedia.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mendelin.catpedia.R
import com.mendelin.catpedia.adapter.BreedsAdapter
import com.mendelin.catpedia.databinding.FragmentBreedsListBinding
import com.mendelin.catpedia.di.viewmodels.ViewModelProviderFactory
import com.mendelin.catpedia.ui.activity.ActivityCallback
import com.mendelin.catpedia.ui.custom_views.AlertBox
import com.mendelin.catpedia.ui.custom_views.MarginItemDecorationVertical
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBreedsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        activityCallback?.showToolbar(true)
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
        viewModel.getLoadingObservable().observe(viewLifecycleOwner, ::setLoadingProgress)

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

    private fun setLoadingProgress(status: Boolean) {
        binding.progressBreedsList.visibility = if (status) View.VISIBLE else View.GONE
        binding.recyclerBreeds.visibility = if (status) View.GONE else View.VISIBLE
        activityCallback?.showSearchBar(status)
    }

    private fun showErrorAlert(context: Context, msg: String) {
        AlertBox().apply {
            setPositiveButtonListener { dialog, _ ->
                dialog.dismiss()
            }

            showAlert(
                context,
                context.getString(R.string.alert_error),
                msg,
                context.getString(R.string.alert_ok),
                null
            )
        }
    }
}