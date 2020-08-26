package com.mendelin.catpedia.presentation_layer.fragments.breed_info.view

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mendelin.catpedia.R
import com.mendelin.catpedia.presentation_layer.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_breed_info.*

class BreedInfoFragment : BaseFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .disallowHardwareConfig())
            .load(args.imageUrl)
            .optionalCenterCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgCat)

        txtBreedName.text = args.name
        txtBreedDescription.text = args.description
        txtBreedCountry.text = args.country
        txtBreedTemperament.text = args.temperament
        txtBreedWikipediaLink.text = args.link
    }
}