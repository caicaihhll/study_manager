package com.java.managersystem.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.java.managersystem.R;


public class SunRoundImageView extends ImageView {
    private RoundViewDelegate mRoundViewDelegate;
    private float round = 15f;//圆角半径像素值
    Context mContext;

    public SunRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        mContext = context;
        if (mRoundViewDelegate == null) {
            mRoundViewDelegate = new RoundViewDelegate(this, getContext());
        }
    }

    public SunRoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        if (mRoundViewDelegate == null) {
            mRoundViewDelegate = new RoundViewDelegate(this, getContext());
        }
        initAttrs(attrs, defStyle);
    }

    public SunRoundImageView(Context context) {
        this(context,null);
        mContext = context;
        if (mRoundViewDelegate == null) {
            mRoundViewDelegate = new RoundViewDelegate(this, getContext());
        }

    }

    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RoundView, defStyleAttr, 0);

        //边框圆角
        round = array.getDimension(R.styleable.RoundView_corner_size, 15);

        //回收
        array.recycle();
        mRoundViewDelegate.setRectAdius(round);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        mRoundViewDelegate.roundRectSet(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mRoundViewDelegate.canvasSetLayer(canvas);

        super.draw(canvas);
        canvas.restore();
    }

    public void setRound(float mround){
        round = mround;
        mRoundViewDelegate.setRectAdius(round);
        invalidate();
    }
}
