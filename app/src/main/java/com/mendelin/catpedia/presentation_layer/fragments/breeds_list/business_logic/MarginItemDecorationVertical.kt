/*
 *
 * MarginItemDecoration.kt on ShopMyCloset
 *
 * Created by Madalin Balint on 24/6/2020 19:17.
 * Copyright (c) 2020 Kodiak Mobile SRL. All rights reserved.
 */

package com.mendelin.catpedia.presentation_layer.fragments.breeds_list.business_logic

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecorationVertical(private val horizontal: Int, private val vertical: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = vertical
            }

            left = horizontal
            right = horizontal

            bottom = vertical
        }
    }
}