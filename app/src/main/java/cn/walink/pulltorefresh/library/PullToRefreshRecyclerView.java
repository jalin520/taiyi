package cn.walink.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Potato on 15/11/24.
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

    private RecyclerView recyclerView;
//
//    public PullToRefreshRecyclerView(Context context) {
//        super(context);
//    }
//
//    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public Orientation getPullToRefreshScrollDirection() {
//        return Orientation.VERTICAL;
//    }
//
//    @Override
//    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
//        recyclerView = new RecyclerView(context, attrs);
//
//        return recyclerView;
//    }
//
//    @Override
//    protected boolean isReadyForPullEnd() {
//        View view = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
//        if (null != view) {
//            return getRefreshableView().getBottom() >= view.getBottom();
//        }
//        return false;
//    }
//
//    @Override
//    protected boolean isReadyForPullStart() {
//        View view = getRefreshableView().getChildAt(0);
//
//        if (view != null) {
//            return view.getTop() >= getRefreshableView().getTop();
//        }
//        return false;
//    }


    public PullToRefreshRecyclerView(Context context) {
        super(context);
    }

    public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
    }

    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        recyclerView = new RecyclerView(context, attrs);
        return recyclerView;
    }

    @Override
    public Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    @Override
    protected boolean isReadyForPullEnd() {
        return isEndItemVisible();
    }

    private boolean isEndItemVisible() {

        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        if (adapter != null) {
            View view = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
            if (null != view) {
                return getRefreshableView().getBottom() >= view.getBottom();
            }
        }

        return false;
    }


    @Override

    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }


    private boolean isFirstItemVisible() {
        final RecyclerView.Adapter<?> adapter = getRefreshableView().getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以下拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {
            if (DEBUG) {
                Log.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return mRefreshableView.getChildAt(0).getTop() >= mRefreshableView.getTop();
            }
        }

        return false;
    }

    private int getFirstVisiblePosition() {
        View firstVisibleChild = mRefreshableView.getChildAt(0);
        return firstVisibleChild != null ? mRefreshableView.getChildAdapterPosition(firstVisibleChild) : -1;
    }
}
