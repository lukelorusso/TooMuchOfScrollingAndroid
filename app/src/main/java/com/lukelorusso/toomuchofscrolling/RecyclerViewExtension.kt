package com.lukelorusso.toomuchofscrolling

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.bindScrollTo(vararg destinations: RecyclerView): RecyclerView.OnScrollListener {
    object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            // each time you grab a RecyclerView, you should STOP other destinations' scrolling
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING)
                destinations.forEach { destinationRecyclerView ->
                    destinationRecyclerView.stopScroll()
                }
            // then you can scroll the grabbed RecyclerView
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
