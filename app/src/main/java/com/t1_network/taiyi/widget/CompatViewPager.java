package com.t1_network.taiyi.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by David on 2015/12/10.
 *
 * @author David
 * @version $Rev$
 * @time ${Time}
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updataDate $Date$
 * @updateDes ${TODO}
 */
public class CompatViewPager extends ViewPager {

    private float mDownX;
    private float mDownY;

    public CompatViewPager(Context context) {
        this(context, null);
    }


    public CompatViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //请求不要拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                    mDownX = ev.getX();
                    mDownY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveX = ev.getX();
                    float moveY = ev.getY();

                    float diffX = moveX - mDownX;
                    float diffY = moveY - mDownY;

                    if (Math.abs(diffX) > Math.abs(diffY)){
                        //左右移动
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }else{
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }

                    //如果是左右移动,请求父容器不要拦截
                    //如果是上下移动,请求父容器拦截
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }

        return super.dispatchTouchEvent(ev);
    }
}
