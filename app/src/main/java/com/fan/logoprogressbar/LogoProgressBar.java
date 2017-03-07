package com.fan.logoprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by admin on 2016/12/25.
 */
public class LogoProgressBar extends View {
    private int bWidth;
    private int bHeight;
    private MODE mMode = MODE.ROUND;
    private int mProgress;
    private int mMax;

    private Bitmap mNormalBitmap;
    private Bitmap mFullBitmap;

    private int mDelta;



    public LogoProgressBar(Context context) {
        super(context);
        init();
    }

    public LogoProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LogoProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.LogoProgressBar);
        mMax = a.getInteger(R.styleable.LogoProgressBar_max,100);
        mProgress = a.getInteger(R.styleable.LogoProgressBar_progress,0);
        mNormalBitmap = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.LogoProgressBar_normal_background,0));
        mFullBitmap = BitmapFactory.decodeResource(getResources(),a.getResourceId(R.styleable.LogoProgressBar_full_background,0));
        int mode = a.getInteger(R.styleable.LogoProgressBar_orientation, 1);
        if (mode == 1){
            mMode = MODE.ROUND;
        }else if (mode == 2){
            mMode = MODE.HORIZONTAL;
        }else {
            mMode = MODE.VERTICAL;
        }
        a.recycle();
        init();
    }

    public enum MODE {
        ROUND,
        HORIZONTAL,
        VERTICAL
    }



    private void init() {
        mDelta = 6;
        bWidth = mNormalBitmap.getWidth();
        bHeight = mNormalBitmap.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(bWidth,bHeight);
        }else if (widthSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(bWidth,heightSpecSize);
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,bHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(mFullBitmap,null,new RectF(mDelta/2,mDelta/2,getWidth() - mDelta ,getHeight() - mDelta),paint);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.drawBitmap(mNormalBitmap ,null,new RectF(mDelta/2,mDelta/2,getWidth() - mDelta ,getHeight()-mDelta),paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setStrokeWidth(getWidth()/2);
        if (mMode == MODE.ROUND) {
            RectF rectf = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawArc(rectf, -90, 360 * mProgress / mMax, true, paint);
        } else if (mMode == MODE.HORIZONTAL) {
            RectF rectf = new RectF(0, 0, getWidth() * mProgress / mMax, getHeight());
            canvas.drawRect(rectf, paint);
        } else {
            RectF rectf = new RectF(0, 0, getWidth(), getHeight() * mProgress/ mMax);
            canvas.drawRect(rectf, paint);
        }
        canvas.restoreToCount(sc);
    }

    public void setProgress(int p) {
        if (p > mMax){
            mProgress = mMax;
        }else {
            mProgress = p;
        }
        invalidate();
    }

    public void setMode(MODE mode) {
        mMode = mode;
    }
}
