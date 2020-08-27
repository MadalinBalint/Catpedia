package com.mendelin.catpedia.presentation_layer.fragments.breed_info

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mendelin.catpedia.R
import com.mendelin.catpedia.presentation_layer.fragments.BaseFragment
import kotlinx.android.synthetic.main.fragment_breed_info.*

class BreedInfoFragment : BaseFragment(R.layout.fragment_breed_info) {

    private val args: BreedInfoFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()

        toolbarOff()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /* Determine how shared elements are handled */
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 750
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 6f
        circularProgressDrawable.centerRadius = 50f
        circularProgressDrawable.start()

        /* Load cat image */
        Glide.with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .disallowHardwareConfig())
            .load(args.imageUrl)
            .optionalCenterCrop()
            .placeholder(circularProgressDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgCat)

        /* Setup the rest of the data */
        txtBreedName.text = args.name
        txtBreedDescription.text = args.description
        txtBreedCountry.text = args.country
        txtBreedTemperament.text = args.temperament
        txtBreedWikipediaLink.text = args.link

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}