package com.azhon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 文件名:    CustomTouchView
 * 创建时间:  2019-04-26 on 15:51
 * 描述:     TODO 自定义View，触摸交互
 *
 * @author 阿钟
 */

public class CustomTouchView extends View {
    /**
     * 上下文
     */
    private Context context;
    /**
     * 每一个矩形的宽度
     */
    private int rectWidth = 120;
    /**
     * 绘制多少个矩形
     */
    private int rectCount = 23;
    /**
     * 宽度
     */
    private int width;
    /**
     * 高度
     */
    private int height;
    /**
     * 内容区域的宽度
     */
    private int contentWidth;
    /**
     * 矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 速度获取
     */
    private VelocityTracker velocityTracker;
    /**
     * 惯性最大 最小速度
     */
    private int maximumVelocity, minimumVelocity;
    /**
     * 控制滑动
     */
    private OverScroller scroller;
    /**
     * 非法触控id
     */
    private final static int INVALID_ID = -1;
    /**
     * 记录首个触控点的id 避免多点触控引起的滚动
     */
    private int activePointerId = INVALID_ID;
    /**
     * 最小可滑动值、最大可滑动值
     */
    private int minScrollX = 0, maxScrollX = 0;
    /**
     * 手指上次滑动的点
     */
    private float lastX;

    /**
     * 保存随机生成的颜色
     */
    private Map<Integer, Integer> colors = new HashMap<>();


    public CustomTouchView(Context context) {
        this(context, null);
    }

    public CustomTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);

        //最小的惯性滚动速度
        maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minimumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        scroller = new OverScroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defaultWidth = DensityUtil.dip2px(context, 300);
        int defaultHeight = DensityUtil.dip2px(context, 200);
        width = getViewSize(defaultWidth, widthMeasureSpec);
        height = getViewSize(defaultHeight, heightMeasureSpec);
        //设置View的大小
        setMeasuredDimension(width, height);

        //最大的滚动距离为内容的宽度 - View的宽度 - 最右边多算的一个空白区域
        contentWidth = rectCount * rectWidth * 2 - rectWidth;
        maxScrollX = contentWidth - width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < rectCount; i++) {
            int right = i * 2 * rectWidth + rectWidth;
            Rect rect = new Rect(i * 2 * rectWidth, height / 4, right, 3 * height / 4);
            paint.setColor(randomColor(i));
            canvas.drawRect(rect, paint);
            drawText(canvas, rect, String.valueOf(i + 1), textPaint);
        }
    }

    /**
     * 将文字绘制在矩形中间
     */
    private void drawText(Canvas canvas, Rect rect, String text, Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //为基线到字体上边框的距离,即上图中的top
        float top = fontMetrics.top;
        //为基线到字体下边框的距离,即上图中的bottom
        float bottom = fontMetrics.bottom;
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);
        //基线中间点的y轴计算公式
        canvas.drawText(text, rect.centerX(), baseLineY, paint);
    }

    /**
     * 获取View的宽高
     */
    private int getViewSize(int defaultSize, int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            //wrap_content
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                return defaultSize;
            //match_parent、固定dp大小
            case MeasureSpec.EXACTLY:
                return size;
        }
        return defaultSize;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //内容区域的宽度是否大于View的宽度，如果没有大于View的宽度则不需要滚动
        if (contentWidth <= width) return super.onTouchEvent(event);
        //开始速度检测
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录首个触控点的id
                activePointerId = event.findPointerIndex(event.getActionIndex());
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (activePointerId == INVALID_ID || event.findPointerIndex(activePointerId) == INVALID_ID) {
                    break;
                }
                //计算首个触控点移动后的坐标
                float moveX = lastX - event.getX(activePointerId);
                lastX = event.getX(activePointerId);
                damping((int) moveX);
                break;
            case MotionEvent.ACTION_UP:
                activePointerId = INVALID_ID;
                //处理松手后的Fling
                velocityTracker.computeCurrentVelocity(1000, maximumVelocity);
                int velocityX = (int) velocityTracker.getXVelocity();
                //滑动速度大于默认速度，则开始惯性滑动
                if (Math.abs(velocityX) > minimumVelocity) {
                    startFling(velocityX);
                }
                restoreLeft();
                restoreRight();
                recycleVelocity();
                break;
            case MotionEvent.ACTION_CANCEL:
                activePointerId = INVALID_ID;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                recycleVelocity();
                break;
        }
        return true;
    }


    /**
     * 当持续向右滑动的时候，放手回到起点
     */
    private void restoreLeft() {
        //不能继续往右边滑动了
        if (getScrollX() < 0) {
            startFling(-50);
            invalidate();
        }
    }

    /**
     * 当持续向左滑动的时候，放手定位到最右边
     */
    private void restoreRight() {
        //不能继续往右边滑动了
        if (getScrollX() > maxScrollX) {
            startFling(50);
            invalidate();
        }
    }

    /**
     * 阻尼效果
     */
    private void damping(int moveX) {
        //持续在向左滑动 和 持续向右滑动
        if (getScrollX() > maxScrollX || getScrollX() < minScrollX) {
            scrollBy(moveX / 3, 0);
        } else {
            scrollBy(moveX, 0);
        }
    }

    /**
     * VelocityTracker回收
     */
    private void recycleVelocity() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }


    /**
     * 开始的、惯性滚动
     *
     * @param velocityX x轴速度
     */
    private void startFling(int velocityX) {
        scroller.fling(getScrollX(), 0, -velocityX / 2, 0, minScrollX, maxScrollX, 0, 0);
        invalidate();
    }


    /**
     * 由fling方法发起的invalidate，会最终调用view的computeScroll方法
     * 而在computeScroll方法中又发起了postInvalidate，最终又会调用view的computeScroll，如此循环绘制形成了惯性滚动
     */
    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 随机生成颜色
     */
    private int randomColor(int position) {
        Integer color = colors.get(position);
        if (color != null) {
            return color;
        }
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        color = Color.rgb(r, g, b);
        colors.put(position, color);
        return color;
    }
}
