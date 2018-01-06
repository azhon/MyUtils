package com.azhon.lightmode.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * 项目名:    LightMode
 * 包名       com.azhon.lightmode
 * 文件名:    MaskView
 * 创建时间:  2018/1/2 on 23:14
 * 描述:     TODO 模仿网页观看视频，关灯效果；需要在完全显示在屏幕上时，添加才有效
 *
 * @author 阿钟
 */

public class MaskView extends View {

    /**
     * activity 根视图
     */
    private ViewGroup decorView;
    /**
     * 不加遮罩的视图
     */
    private View brightView;
    /**
     * 遮罩画笔
     */
    private Paint paint;
    /**
     * 不遮罩的view
     */
    private RectF brightRectF;
    /**
     * 遮罩的路径
     */
    private Path path;

    /**
     * 是否已经添加了
     */
    private boolean showing = false;

    public MaskView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        //获取activity顶层视图
        decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        //初始化蒙版视图，充满全屏幕
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        paint = new Paint();
        //设置为黑色 在加点透明度
        paint.setColor(Color.argb(230, 0, 0, 0));
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (decorView == null || brightView == null) {
            return;
        }
        //路径恢复
        path.reset();
        //A
        path.moveTo(brightRectF.left, brightRectF.top);
        //B
        path.lineTo(brightRectF.right, brightRectF.top);
        //C
        path.lineTo(brightRectF.right, brightRectF.bottom);
        //D
        path.lineTo(brightRectF.left, brightRectF.bottom);
        //A
        path.lineTo(brightRectF.left, brightRectF.top);
        //E
        path.lineTo(0, brightRectF.top);
        //F
        path.lineTo(0, decorView.getHeight());
        //F
        path.lineTo(decorView.getWidth(), decorView.getHeight());
        //H
        path.lineTo(decorView.getWidth(), 0);
        //I
        path.lineTo(0, 0);
        //E
        path.lineTo(0, brightRectF.top);
        //A
        path.lineTo(brightRectF.left, brightRectF.top);
        //闭合曲线
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * 添加不加蒙版的View
     *
     * @param view 视图
     */
    public void attachView(View view) {
        if (!showing) {
            this.brightView = view;
            //没有添加，才可以添加
            initMask();
        }
    }

    /**
     * 移除遮罩
     */
    public void removeMask() {
        if (!showing) {
            //没有添加
            return;
        }
        //使用动画消失
        AlphaAnimation animation = new AlphaAnimation(1f, 0f);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewParent parent = MaskView.this.getParent();
                if (parent != null && parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(MaskView.this);
                    showing = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);

    }

    /**
     * 初始化不加遮罩view的位置
     */
    private void initMask() {
        int[] location = new int[2];
        //获取view在屏幕上的坐标
        brightView.getLocationOnScreen(location);
        brightRectF = new RectF(location[0], location[1], location[0] + brightView.getWidth(),
                location[1] + brightView.getHeight());
        //添加蒙版到Activity之上
        decorView.addView(this);
        invalidate();
        showing = true;
    }

    /**
     * 是否已经添加
     *
     * @return
     */
    public boolean isShowing() {
        return showing;
    }
}
