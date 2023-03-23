package com.example.pictake_preview;

import android.content.Context;
import android.view.TextureView;

import android.util.AttributeSet;

public class SquareTextureView extends TextureView {
    public SquareTextureView(Context context){
        this(context,null);
    }

    public SquareTextureView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public SquareTextureView(Context context,AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(width<height){
            setMeasuredDimension(width,width);
        }else {
            setMeasuredDimension(height,height);
        }


    }

}
