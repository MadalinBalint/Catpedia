package com.mendelin.catpedia.breeds_list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpedia.ItemBreedListBinding
import com.mendelin.catpedia.breeds_list.models.BreedInfoResponse
import com.mendelin.catpedia.breeds_list.ui.BreedsListFragmentDirections

internal typealias OnImageLoaderListener = (holder: BreedsAdapter.BreedInfoResponseViewHolder, breed: BreedInfoResponse) -> Unit

class BreedsAdapter : ListAdapter<BreedInfoResponse, BreedsAdapter.BreedInfoResponseViewHolder>(DiffCallbackBreedsAdapter) {

    private var listener: OnImageLoaderListener? = null
    private val breedsList: ArrayList<BreedInfoResponse> = arrayListOf()
    lateinit var context: Context

    class BreedInfoResponseViewHolder(var binding: ItemBreedListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(property: BreedInfoResponse) {
            binding.property = property
            binding.executePendingBindings()
        }
    }

    companion object DiffCallbackBreedsAdapter : DiffUtil.ItemCallback<BreedInfoResponse>() {
        override fun areItemsTheSame(oldItem: BreedInfoResponse, newItem: BreedInfoResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BreedInfoResponse, newItem: BreedInfoResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedInfoResponseViewHolder {
        context = parent.context
        return BreedInfoResponseViewHolder(ItemBreedListBinding.inflate(LayoutInflater.from(context)))
    }

    override fun onBindViewHolder(holder: BreedInfoResponseViewHolder, position: Int) {
        val breed = breedsList[position]
        holder.bind(breed)

        if (breed.image == null) {
            listener?.invoke(holder, breed)
        }

        holder.binding.breedCard.setOnClickListener {
            val action = BreedsListFragmentDirections.actionBreedInfo(
                breed.image?.url ?: "",
                breed.name,
                breed.description ?: "",
                String.format("%s (%s)", breed.origin, breed.country_code),
                breed.temperament ?: "",
                breed.wikipedia_url ?: "")

            val extras = FragmentNavigatorExtras(
                holder.binding.imgBreed to "catImage"
            )

            holder.binding.breedCard.findNavController().navigate(action, extras)
        }
    }

    fun setList(list: List<BreedInfoResponse>) {
        list.sortedBy { it.name }

        breedsList.apply {
            clear()
            addAll(list)
        }

        submitList(breedsList)
        notifyDataSetChanged()
    }

    fun addListener(listener: OnImageLoaderListener?) {
        this.listener = listener
    }
}