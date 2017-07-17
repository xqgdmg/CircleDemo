package com.yiw.circledemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by yiw on 2016/6/6.
 */
public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context){
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform); // 位图池，位图
    }

    private Bitmap circleCrop(BitmapPool bitmapPool, Bitmap bitmapSource) {
        if (bitmapSource == null) return null;

         // 获取宽高最小值
        int size = Math.min(bitmapSource.getWidth(), bitmapSource.getHeight());

         // 假设宽 12 ；高 6 -- 》那么 x = 3 ； y = 0； size = 6
         // 大值减小值的一半，另一个一定是0
        int x = (bitmapSource.getWidth() - size) / 2;
        int y = (bitmapSource.getHeight() - size) / 2;

         // 这里的 x， y表示起始的 x，y 坐标值
         // 这里的x，y参数可以互换，因为我们知道两个确定点的坐标，随便用一个就好
         // 1 --》 正方形
        Bitmap squaredBitmap = Bitmap.createBitmap(bitmapSource, x, y, size, size); // 3；0；6；6

         // 用到了 bitmapPool
        Bitmap resultBitmap = bitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (resultBitmap == null) {
            resultBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }
         // 2 --》正方形放到画布
        Canvas canvas = new Canvas(resultBitmap);
        Paint paint = new Paint();
         // 这个不设置也是可以的
        paint.setShader(new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP)); // CLAMP 外部颜色复制边沿颜色
        paint.setAntiAlias(true);
        float r = size / 2f;
         // 3 --》 画圆
        canvas.drawCircle(r, r, r, paint); // 圆心x，圆心y，半径，画笔
        return resultBitmap;

    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
