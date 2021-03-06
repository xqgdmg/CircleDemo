package com.yiw.circledemo.holder;

import android.view.View;
import android.view.ViewStub;

import com.yiw.circledemo.R;
import com.yiw.circledemo.widgets.MultiImageView;

/**
 * Created by suneee on 2016/8/16.
 * 九宫格图片
 */
public class ImageViewHolder extends CircleViewHolder {
    /** 图片*/
    public MultiImageView multiImageView;

    public ImageViewHolder(View itemView){
        super(itemView, TYPE_IMAGE);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {

         // 手动抛异常
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }

        // 传说中这个只能加载一次，它是指一个布局
        viewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View inflateView = viewStub.inflate();

        MultiImageView multiImageView = (MultiImageView) inflateView.findViewById(R.id.multiImagView);
        if(multiImageView != null){
            this.multiImageView = multiImageView;
        }
    }
}
