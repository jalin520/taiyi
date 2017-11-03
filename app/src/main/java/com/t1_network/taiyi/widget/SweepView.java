package com.t1_network.taiyi.widget;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by David on 2016/1/6.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class SweepView extends ViewGroup {

    private View mContentView;
    private View mDeleteView;
    private int mDeletewidth;
    private ViewDragHelper mViewDragHelper;

    private boolean isOpen;


    public SweepView(Context context) {
        this(context, null);
    }

    public SweepView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, new MyCallback());


    }

    @Override
    protected void onFinishInflate() {
        mContentView = getChildAt(0);
        mDeleteView = getChildAt(1);

        mDeletewidth = mDeleteView.getLayoutParams().width;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentView.measure(widthMeasureSpec, heightMeasureSpec);
        mDeleteView.measure(MeasureSpec.makeMeasureSpec(mDeletewidth, MeasureSpec.EXACTLY), heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isOpen) {
            mContentView.layout(-mDeleteView.getMeasuredWidth(), 0, mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth(), mContentView.getMeasuredHeight());

            mDeleteView.layout(mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth(), 0,
                    mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());


        } else {
            mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());

            mDeleteView.layout(mContentView.getMeasuredWidth(), 0,
                    mContentView.getMeasuredWidth() + mDeleteView.getMeasuredWidth(), mContentView.getMeasuredHeight());


        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    class MyCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView || child == mDeleteView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mContentView) {
                if (left > 0) {
                    return 0;
                } else if (-left > mDeleteView.getMeasuredWidth()) {
                    return -mDeleteView.getMeasuredWidth();
                }
            } else {
                if (left < mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth()) {
                    return mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth();
                } else if (left > mContentView.getMeasuredWidth()) {
                    return mContentView.getMeasuredWidth();
                }
            }
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            invalidate();
            if (changedView == mContentView) {
                int changeLeft = mContentView.getMeasuredWidth() + left;
                mDeleteView.layout(changeLeft, 0, changeLeft + mDeleteView.getMeasuredWidth(), mContentView.getMeasuredHeight());
            } else {
                int l = left - mContentView.getMeasuredWidth();
                mContentView.layout(l, 0, left, mContentView.getMeasuredHeight());
            }

        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            int lAbs = Math.abs(mContentView.getLeft());
            int measuredWidth = mDeleteView.getMeasuredWidth() / 2;
            if (lAbs > measuredWidth) {
//                    mContentView.layout(-mDeleteView.getMeasuredWidth(), 0,
//                            mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth(),
//                            mContentView.getMeasuredHeight());
//
//                    mDeleteView.layout(mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth()
//                            , 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
                open();

            } else {
//                    mContentView.layout(0, 0,
//                            mContentView.getMeasuredWidth(),
//                            mContentView.getMeasuredHeight());
//
//                    mDeleteView.layout(mContentView.getMeasuredWidth()
//                            , 0, mContentView.getMeasuredWidth() + mDeleteView.getMeasuredWidth()
//                            , mContentView.getMeasuredHeight());
                close();

            }


        }
    }

    public boolean isOpened() {
        return isOpen;
    }

    public void open() {
        mViewDragHelper.smoothSlideViewTo(mContentView, -mDeleteView.getMeasuredWidth(), 0);
        mViewDragHelper.smoothSlideViewTo(mDeleteView, mContentView.getMeasuredWidth() - mDeleteView.getMeasuredWidth(), 0);
        invalidate();
        isOpen = true;
    }

    public void close() {
        mViewDragHelper.smoothSlideViewTo(mContentView, 0, 0);
        mViewDragHelper.smoothSlideViewTo(mDeleteView, mContentView.getMeasuredWidth(), 0);
        invalidate();
        isOpen = false;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
