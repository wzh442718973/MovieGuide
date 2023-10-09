package com.firecat.video.player.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class VPosterView extends AppCompatImageView {


    private static final float WIDTH = 180.0f;
    private static final float HEIGH = 250.0f;

    private static final float WH_SCALE = WIDTH / HEIGH;

    public VPosterView(@NonNull Context context) {
        super(context);
    }

    public VPosterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VPosterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);

        if (width <= 0 && heigh <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }


        if(width > 0){
            heigh = (int) (width / WH_SCALE);
        }

        super.setMeasuredDimension(width, heigh);

//        Log.e("wzh", width + "*" + heigh + " | " + getMeasuredWidth() + "*" + getMeasuredHeight());
    }
}
