package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic

import androidx.recyclerview.widget.RecyclerView
import com.mendelin.catpedia.ItemBreedInfoBinding
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse

class BreedInfoResponseViewHolder(private var binding: ItemBreedInfoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(property: BreedInfoResponse) {
        binding.property = property
        binding.executePendingBindings()
    }
}