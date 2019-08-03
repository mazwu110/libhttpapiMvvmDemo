package com.qlib.libadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

// add by mzw 2018.3.30
public class SwipeToLoadHelper extends RecyclerView.OnScrollListener {

    private RecyclerView.LayoutManager mLayoutManager;
    private LoadMoreAdapterWrapper mAdapterWrapper;
    private LoadQMoreListener mListener;
    /** 是否正在加载中 */
    private boolean mLoading = false;
    /** 上拉刷新功能是否开启 */
    private boolean mIsSwipeToLoadEnabled = true;

    public SwipeToLoadHelper(RecyclerView recyclerView, LoadMoreAdapterWrapper adapterWrapper) {
        mLayoutManager = recyclerView.getLayoutManager();
        mAdapterWrapper = adapterWrapper;

        if (mLayoutManager instanceof GridLayoutManager) {
            mAdapterWrapper.setAdapterType(LoadMoreAdapterWrapper.ADAPTER_TYPE_GRID);
            mAdapterWrapper.setSpanCount(((GridLayoutManager) mLayoutManager).getSpanCount());
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            mAdapterWrapper.setAdapterType(LoadMoreAdapterWrapper.ADAPTER_TYPE_LINEAR);
        }

        // 将OnScrollListener设置RecyclerView
        recyclerView.addOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (mIsSwipeToLoadEnabled && SCROLL_STATE_IDLE == newState && !mLoading) {
            if (mLayoutManager instanceof GridLayoutManager) {
                final GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (mIsSwipeToLoadEnabled) {
                            // 功能开启, 根据位置判断, 最后一个item时返回整个宽度, 其他位置返回1
                            // AdapterWrapper会保证最后一个item会从新的一行开始
                            if (position == mLayoutManager.getItemCount() - 1) {
                                return gridLayoutManager.getSpanCount();
                            } else {
                                return 1;
                            }
                        } else {
                            return 1;
                        }
                    }
                });
            }

            if (mLayoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mLayoutManager;
                int lastCompletePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // only when the complete visible item is second last
                if (lastCompletePosition == mLayoutManager.getItemCount() - 2) {
                    int firstCompletePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    View child = linearLayoutManager.findViewByPosition(lastCompletePosition);
                    if (child == null)
                        return;
                    int deltaY = recyclerView.getBottom() - recyclerView.getPaddingBottom() - child.getBottom();
                    if (deltaY > 0 && firstCompletePosition != 0) {
                        recyclerView.smoothScrollBy(0, -deltaY);
                    }
                } else if (lastCompletePosition == mLayoutManager.getItemCount() - 1) {
                    // 最后一项完全显示, 触发操作, 执行加载更多操作 禁用回弹判断
                    mLoading = true;
                    mAdapterWrapper.setLoadItemState(0);
                    if (mListener != null) {
                        mListener.onLoadMore();
                    }
                }
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /** 设置下拉刷新功能是否开启 */
    public void setSwipeToLoadEnabled(boolean isSwipeToLoadEnabled) {
        if (mIsSwipeToLoadEnabled != isSwipeToLoadEnabled) {
            mIsSwipeToLoadEnabled = isSwipeToLoadEnabled;
            mAdapterWrapper.setLoadItemVisibility(isSwipeToLoadEnabled);
        }
    }

    /** 设置LoadMore Item为加载完成状态, 上拉加载更多完成时调用 */
    public void setLoadMoreFinish() {
        mLoading = false;
        mAdapterWrapper.setLoadItemState(1);
    }

    // 设置底部显示没有更多数据了
    public void setNoMoreData() {
        mLoading = false;
        mAdapterWrapper.setLoadItemState(2);
    }

    // 设置底部加载更多是否显示
    public void setLoadMoreViewVisibility(boolean show) {
        mAdapterWrapper.setLoadMoreViewVisibility(show);
    }

    //上拉操作触发时调用的接口
    public void setLoadMoreListener(LoadQMoreListener loadMoreListener) {
        mListener = loadMoreListener;
    }

    public interface LoadQMoreListener {
        void onLoadMore();
    }
}
