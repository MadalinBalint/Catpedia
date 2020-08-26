package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.mendelin.catpedia.ItemBreedInfoBinding
import com.mendelin.catpedia.common.ResourceUtils
import com.mendelin.catpedia.common.Status
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.adapter.DiffCallback
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.view.BreedsListFragmentDirections
import com.mendelin.catpedia.presentation_layer.fragments.breeds_list.viewmodel.BreedsViewModel


class BreedsAdapter(val viewModel: BreedsViewModel,
                    val owner: LifecycleOwner,
                    val navController: NavController) : ListAdapter<BreedInfoResponse, BreedInfoResponseViewHolder>(DiffCallback) {

    val originalList: ArrayList<BreedInfoResponse> = arrayListOf()
    val breedsList: ArrayList<BreedInfoResponse> = arrayListOf()
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedInfoResponseViewHolder {
        context = parent.context
        return BreedInfoResponseViewHolder(ItemBreedInfoBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: BreedInfoResponseViewHolder, position: Int) {
        val breed = breedsList[position]
        holder.bind(breed)

        if (breed.image == null) {
            viewModel.getBreedImage(breed.id).observe(owner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { images ->
                                if (images.size == 1) {
                                    breed.image = images[0]
                                    holder.bind(breed)
                                    holder.binding.progressBreedImage.visibility = View.GONE
                                }
                            }
                        }
                        Status.ERROR -> {
                            holder.binding.progressBreedImage.visibility = View.GONE
                        }
                        Status.LOADING -> {
                            holder.binding.progressBreedImage.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }

        holder.binding.breedCard.setOnClickListener {
            val action = BreedsListFragmentDirections.actionBreedInfo(
                breed.image?.url ?: "",
                breed.name,
                breed.description ?: "",
                String.format("%s (%s)", breed.country_code, breed.origin),
                breed.temperament ?: "",
                breed.wikipedia_url ?: "")

            val extras = FragmentNavigatorExtras(
                holder.binding.imgBreed to "catImage"
            )

            navController.navigate(action, extras)
        }
    }

    fun setOriginalList(list: List<BreedInfoResponse>) {
        originalList.clear()
        originalList.addAll(list)

        breedsList.apply {
            clear()
            addAll(list)
        }

        submitList(originalList)
    }

    fun setFilteredList(breeds: List<BreedInfoResponse>) {
        breedsList.apply {
            clear()
            addAll(breeds)
        }

        submitList(breedsList)
    }

    fun filterBreedsList(query: String) {
        if (query.isEmpty()) {
            breedsList.apply {
                clear()
                addAll(originalList)
            }
            submitList(originalList)
        } else {
            val filteredList = originalList.filter { it.origin?.toLowerCase() == query.toLowerCase() || it.origin?.toLowerCase()?.startsWith(query.toLowerCase()) == true }

            if (filteredList.isNotEmpty()) {
                breedsList.clear()
                breedsList.addAll(filteredList)
                submitList(filteredList)
            } else
                ResourceUtils.showErrorAlert(context, "The country ${query} doesn't exist in our list.")
        }
    }
}