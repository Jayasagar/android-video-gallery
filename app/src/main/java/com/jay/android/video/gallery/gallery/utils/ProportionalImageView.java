package com.jay.android.video.gallery.gallery.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Scale the image view proportionally :)
 */
public class ProportionalImageView extends ImageView {
 
    public ProportionalImageView(Context context) {
        super(context);
    }
 
    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}