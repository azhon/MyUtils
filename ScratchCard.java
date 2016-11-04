package com.zsy.lambda;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/*
 * 项目名:   AndroidHeros
 * 包名:     com.zsy.androidheros.view
 * 文件名:   ScratchCard 
 * 创建者:   ZSY
 * 创建时间: 2016/11/4 17:15
 * 描述:     刮刮卡效果
 */
public class ScratchCard extends View {

    //手指触摸屏幕，
    private Paint paint;
    private Path path;
    //隐藏的背景图片
    private Bitmap bgBitmap;
    //覆盖在上层的灰色图层
    private Bitmap fgBitmap;
    private Canvas canvas;

    public ScratchCard(Context context) {
        super(context);
        initPaint();
    }

    public ScratchCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public ScratchCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAlpha(0);
        //在已有的图像上绘图将会在其上面添加一层新的图层，如果新图层的paint是不透明的,那么它将遮挡住下面的paint；
        //如果新图层它是部分透明的,那么它不透明的地方将会被染上下面的颜色
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setStyle(Paint.Style.STROKE);
        //设置笔触和连接处能更加圆滑
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paint.setStrokeWidth(60);
        path = new Path();
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a_zhon);
        fgBitmap = Bitmap.createBitmap(bgBitmap.getWidth(), bgBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(fgBitmap);
        //在图层上绘制一层颜色
        canvas.drawColor(Color.parseColor("#1dcdef"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图层
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        //绘制遮罩图层
        canvas.drawBitmap(fgBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                //实现点击檫除
                path.lineTo(event.getX() + 1, event.getY() + 1);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        canvas.drawPath(path, paint);
        invalidate();
        return true;

    }
}
