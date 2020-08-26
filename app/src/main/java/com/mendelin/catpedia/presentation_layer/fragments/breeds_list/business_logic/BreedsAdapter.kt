package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ListAdapter
import com.mendelin.catpedia.ItemBreedInfoBinding
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.adapter.DiffCallback
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel.BreedsViewModel


class BreedsAdapter(val breeds: ArrayList<BreedInfoResponse>,
                    val viewModel: BreedsViewModel,
                    val owner: LifecycleOwner) : ListAdapter<BreedInfoResponse, BreedInfoResponseViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedInfoResponseViewHolder {
        return BreedInfoResponseViewHolder(ItemBreedInfoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: BreedInfoResponseViewHolder, position: Int) {
        val breed = breeds[position]
        holder.bind(breed)

        if (breed.image == null) {
            viewModel.getBreedImage(breed.id).observe(owner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            /*recyclerBreeds.visibility = View.VISIBLE
                        progressBreedsList.visibility = View.GONE*/
                            resource.data?.let { images ->
                                if (images.size == 1) {
                                    breed.image = images[0]
                                    holder.bind(breed)
                                }
                            }
                        }
                        Status.ERROR -> {
                            /*recyclerBreeds.visibility = View.VISIBLE
                        progressBreedsList.visibility = View.GONE
                        showErrorAlert(requireContext(), it.message
                            ?: getString(R.string.alert_error_unknown))*/
                        }
                        Status.LOADING -> {
                            /*progressBreedsList.visibility = View.VISIBLE
                        recyclerBreeds.visibility = View.GONE*/
                        }
                    }
                }
            })
        }
    }

    fun addBreeds(breeds: List<BreedInfoResponse>) {
        this.breeds.apply {
            clear()
            addAll(breeds)
            submitList(breeds)
        }
    }
}