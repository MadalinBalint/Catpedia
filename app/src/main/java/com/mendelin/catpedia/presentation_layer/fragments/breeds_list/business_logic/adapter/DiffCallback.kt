package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic.adapter

import androidx.recyclerview.widget.DiffUtil
import com.mendelin.catpedia.data_access_layer.networking.models.responses.BreedInfoResponse

object DiffCallback : DiffUtil.ItemCallback<BreedInfoResponse>() {
    override fun areItemsTheSame(oldItem: BreedInfoResponse, newItem: BreedInfoResponse): Boolean {
        return oldItem.image == newItem.image
    }

    override fun areContentsTheSame(oldItem: BreedInfoResponse, newItem: BreedInfoResponse): Boolean {
        return oldItem == newItem
    }
}