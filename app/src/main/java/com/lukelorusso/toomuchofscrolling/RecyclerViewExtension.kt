package com.lukelorusso.toomuchofscrolling

import androidx.recyclerview.widget.RecyclerView

/**
 * With this you can and "bind" a [RecyclerView] to others so that, when you scroll one,
 * you actually scroll the others too of the same change (or difference, or Δ),
 * including the final "settling" or "inertial" effect.
 *
 * The resulting [RecyclerView.OnScrollListener] is applied to the [RecyclerView] and returned.
 *
 * @param destinations all the [RecyclerView]s that you want to "bind" together, except [this]
 * @return the applied [RecyclerView.OnScrollListener]
 * @see <a href="https://github.com/lukelorusso/TooMuchOfScrollingAndroid">TooMuchOfScrollingAndroid</a>
 * @author Luke Lorusso
 *
 * Copyright (C) 2020 Luke Lorusso - lukelorusso.com
 * Licensed under the Apache License Version 2.0
 */
fun RecyclerView.bindScrollTo(vararg destinations: RecyclerView): RecyclerView.OnScrollListener {
    object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            // each time you drag a RecyclerView, you should STOP other destinations' scrolling
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                destinations.forEach { destinationRecyclerView ->
                    destinationRecyclerView.stopScroll()
                }
            // then you can scroll the RecyclerView, dragged or not
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            // when you scroll a RecyclerView of some [dx, dy] you can do it
            super.onScrolled(recyclerView, dx, dy)

            // and then, ONLY if this RecyclerView is NOT idle, you can propagate that scroll
            // (this is to avoid infinite multiplications of [dx, dy] when calling scrollBy)
            if (recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE)
                destinations.forEach { destinationRecyclerView ->
                    destinationRecyclerView.scrollBy(dx, dy)
                }
        }
    }.also { listener ->
        this.addOnScrollListener(listener)
        return listener
    }

}
