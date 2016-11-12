package com.zsy.roate;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/*
 *以下为自定义的属性,如需使用需在attrs.xml文件中添加以下代码
 *<declare-styleable name="a_zhon">
 *      <!--每条线的粗细-->
 *      <attr name="paintBold" format="dimension" />
 *      <!--每条线的长度-->
 *      <attr name="lineLength" format="dimension" />
 *      <!--线条个数-->
 *      <attr name="lines" format="integer" />
 *      <!--最大进度-->
 *      <attr name="max" format="integer" />
 *      <!--当前进度-->
 *      <attr name="progress" format="integer" />
 *      <!--背景的颜色-->
 *      <attr name="backgroundColor" format="reference|color" />
 *      <!--前景的颜色-->
 *      <attr name="beforeColor" format="reference|color" />
 *      <!--进度文字颜色-->
 *      <attr name="textColor" format="reference|color" />
 *  </declare-styleable>
 */
/*
 * 文件名:     LoadingView
 * 创建者:     阿钟
 * 创建时间:   2016/11/9 22:05
 * 描述:       自定义菊花Loading旋转
 */
public class LoadingView extends View {

    /*view的默认宽度*/
    private int mWidth;
    /*view的默认高度*/
    private int mHeight;
    /*线条粗细*/
    private int paintBold;
    /*线条长度*/
    private int lineLength;
    /*背景线条颜色*/
    private int bgPaintColor;
    /*上层线条颜色*/
    private int beforePaintColor;
    /*进度文字颜色*/
    private int textColor;
    /*线条个数*/
    private int lines;
    /*背景画笔*/
    private Paint bgPaint;
    /*前景画笔*/
    private Paint bfPaint;
    /*进度文字画笔*/
    private Paint textPaint;
    /*当前下载进度*/
    private int progress;
    /*最大进度*/
    private int max;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(context, attrs);
        initPaint();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 加载我们在attrs.xml文件的自定义的属性
     */
    private void loadAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.a_zhon);
        paintBold = array.getDimensionPixelSize(R.styleable.a_zhon_paintBold, 10);
        lineLength = array.getDimensionPixelSize(R.styleable.a_zhon_lineLength, 25);
        bgPaintColor = array.getColor(R.styleable.a_zhon_backgroundColor, Color.GRAY);
        beforePaintColor = array.getColor(R.styleable.a_zhon_beforeColor, Color.YELLOW);
        lines = array.getInt(R.styleable.a_zhon_lines, 20);
        max = array.getInt(R.styleable.a_zhon_max, 100);
        progress = array.getInt(R.styleable.a_zhon_progress, 0);
        textColor = array.getColor(R.styleable.a_zhon_textColor, Color.BLACK);
        array.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setColor(bgPaintColor);
        bgPaint.setAntiAlias(true);
        bgPaint.setStrokeWidth(paintBold);
        //使得画笔更加圆滑
        bgPaint.setStrokeJoin(Paint.Join.ROUND);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        bfPaint = new Paint();
        bfPaint.setColor(beforePaintColor);
        bfPaint.setAntiAlias(true);
        bfPaint.setStrokeWidth(paintBold);
        bfPaint.setStrokeJoin(Paint.Join.ROUND);
        bfPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取view的宽度
        mWidth = getViewSize(100, widthMeasureSpec);
        //获取view的高度
        mHeight = getViewSize(100, heightMeasureSpec);
    }

    /**
     * 测量模式                 表示意思
     * UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
     * EXACTLY	    当前的尺寸就是当前View应该取的尺寸
     * AT_MOST	    当前尺寸是当前View能取的最大尺寸
     *
     * @param defaultSize 默认大小
     * @param measureSpec 包含测量模式和宽高信息
     * @return 返回View的宽高大小
     */
    private int getViewSize(int defaultSize, int measureSpec) {
        int viewSize = defaultSize;
        //获取测量模式
        int mode = MeasureSpec.getMode(measureSpec);
        //获取大小
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED: //如果没有指定大小，就设置为默认大小
                viewSize = defaultSize;
                break;
            case MeasureSpec.AT_MOST: //如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                viewSize = size;
                break;
            case MeasureSpec.EXACTLY: //如果是固定的大小，那就不要去改变它
                viewSize = size;
                break;
        }
        return viewSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = mWidth / 2;
        int y = mHeight / 2;
        int r = x - 5;
        for (int i = 0; i < lines; i++) {
            //绘制下层菊花
            canvas.drawLine(x, y - r, x, y - r + lineLength, bgPaint);
            canvas.rotate(360 / lines, x, y);
        }
        //获取需要绘制多少个刻度
        int count = (progress * lines) / max;
        //绘制中间的文字进度
        canvas.drawText(progress + "%", x, y + 5, textPaint);
        //绘制上层菊花,也就是进度
        canvas.rotate(360 / lines, x, y);
        for (; count > 0; count--) {
            canvas.drawLine(x, y - r, x, y - r + lineLength, bfPaint);
            canvas.rotate(360 / lines, x, y);
        }
    }

    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param start    开始值
     * @param current  结束值
     * @param duration 动画时长
     */
    public void startAnimation(int start, int current, int duration) {
        ValueAnimator progressAnimator = ValueAnimator.ofInt(start, current);
        progressAnimator.setDuration(duration);
        progressAnimator.setTarget(progress);
        progressAnimator.setInterpolator(new BounceInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.start();
    }
    
    /*设置进度最大值*/
    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    /*设置当前进度*/
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}
