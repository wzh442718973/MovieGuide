package com.firecat.video.player.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HPosterView extends androidx.appcompat.widget.AppCompatImageView {

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Drawable drawable = getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int heigh = MeasureSpec.getSize(heightMeasureSpec);

            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if(width <= 0){
                width = displayMetrics.widthPixels;
            }
            if(heigh <= 0){
                heigh = displayMetrics.heightPixels;
            }

            int bmp_height = ((BitmapDrawable) drawable).getBitmap().getHeight();
            int bmp_width = ((BitmapDrawable) drawable).getBitmap().getWidth();

            int ss_width = width;
            int ss_height = bmp_height * ss_width / bmp_width;
            if (ss_height >= heigh) {
                ss_height = heigh;
                ss_width = bmp_width * ss_height / bmp_height;
            }
            super.setMeasuredDimension(ss_width, ss_height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public HPosterView(@NonNull Context context) {
        super(context);
    }

    public HPosterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HPosterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}