package com.edu.nju.hotel.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * Created by luoxuechun on 2017/6/5.
 */
public class RecyclerDivider extends RecyclerView.ItemDecoration {

    private int dividerHeight;
    private int dividerWidth;
    private Paint mPaint;
    private Drawable middleOfDrawable;

    public RecyclerDivider(int color, int dividerHeight, int dividerWidth) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
        this.dividerHeight = dividerHeight;
        this.dividerWidth = dividerWidth;
    }

//    public RecyclerDivider(int color, int dividerHeight, int dividerWidth, Drawable middleOfDrawable) {
//        this(color, dividerHeight, dividerWidth);
//        this.middleOfDrawable = middleOfDrawable;
//    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft();
            int right = left + dividerWidth;
            int top = child.getBottom();
            int bottom = top+dividerHeight;
//            if (middleOfDrawable != null) {
//                top = middleOfDrawable.getIntrinsicHeight() / 2 - dividerHeight;
//                bottom = middleOfDrawable.getIntrinsicWidth() / 2 + dividerHeight;
//            } else {
//                top = child.getHeight() / 2 - dividerHeight;
//                bottom = child.getHeight() / 2 + dividerHeight;
//            }
            canvas.drawRect(left, top, right, bottom, mPaint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, dividerWidth, dividerHeight);
    }
}