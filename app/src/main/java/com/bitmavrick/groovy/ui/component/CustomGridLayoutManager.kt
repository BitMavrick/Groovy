package com.bitmavrick.groovy.ui.component

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CustomGridLayoutManager(
    context: Context,
    spanCount: Int,
) : GridLayoutManager(context, spanCount) {
    init {
        spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    if (position == 0) {
                        spanCount
                    } else {
                        1
                    }
            }
    }
}
