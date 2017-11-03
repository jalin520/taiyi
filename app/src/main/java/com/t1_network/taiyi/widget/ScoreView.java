package com.t1_network.taiyi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.t1_network.taiyi.R;

/**
 * Created by David on 15/11/20.
 */
public class ScoreView extends View {

    private String pastName = "http://schemas.android.com/apk/res/com.t1_network.taiyi.widget";

    public ScoreView(Context context) {
        super(context);
        init(context, null);
    }

    public ScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private float width = 0;
    private float height = 0;
    private float textSize = 0;
    private int score = 0;

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ScoreView);

        if (ta.hasValue(R.styleable.ScoreView_circle_width)) {
            width = ta.getDimension(R.styleable.ScoreView_circle_width, 0);
        }

        if (ta.hasValue(R.styleable.ScoreView_circle_height)) {
            height = ta.getDimension(R.styleable.ScoreView_circle_height, 0);
        }
        if (ta.hasValue(R.styleable.ScoreView_circle_text_size)) {
            textSize = ta.getDimension(R.styleable.ScoreView_circle_text_size, 0);
        }
        if (ta.hasValue(R.styleable.ScoreView_circle_score)) {
            score = ta.getInt(R.styleable.ScoreView_circle_score, 0);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //计算角度
        float temp = (float) (score / 100.0) * 360;

        Paint paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(5);
        paint1.setColor(getResources().getColor(R.color.bgDeepRoot));
        canvas.drawArc(new RectF(10, 10, width - 10, height - 10), 0, 360, false, paint1);

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        canvas.drawArc(new RectF(10, 10, width - 10, height - 10), -90, temp, false, paint);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        invalidate();
    }
}
