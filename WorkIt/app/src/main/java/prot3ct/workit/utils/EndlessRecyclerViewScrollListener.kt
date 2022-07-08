package prot3ct.workit.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private val visibleThreshold = 6
    private var currentPage = 1
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 1
    private var mLayoutManager: RecyclerView.LayoutManager

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val currentFirstVisible =
            (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount
        lastVisibleItemPosition =
            (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (totalItemCount < previousTotalItemCount) {
            currentPage = startingPageIndex
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }
        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }
        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

    init {
        mLayoutManager = layoutManager
    }
}