package com.azhon.lightmode.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.azhon.lightmode.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 文件名:    ActionBarToast
 * 创建时间:  2018/1/4 on 21:09
 * 描述:     TODO 仿QQ提示信息弹框
 * <p>
 * 使用方法(在Activity中)：
 * <p>
 * ActionBarToast barToast = new ActionBarToast(this);
 * barToast.showToast(ActionBarToast.LENGTH_SHORT);
 * <p/>
 *
 * @author 阿钟
 */

public class ActionBarToast {

    /**
     * activity 根视图
     */
    private ViewGroup decorView;
    /**
     * activity 根视图
     */
    private View view;
    /**
     * 状态栏加上actionBar的高度
     */
    private int totalHeight;
    /**
     * 是否正在显示
     */
    private boolean showing;
    /**
     * 显示／退出 动画的执行时间
     */
    private int animationDuration = 150;
    /**
     * 短时间显示 ms
     */
    public static final int LENGTH_SHORT = 1500;
    /**
     * 长时间显示 ms
     */
    public static final int LENGTH_LONG = 2000;
    /**
     * 不消失，需要手动调用{@link #cancel()}
     */
    public static final int LENGTH_SHOW = -1;

    /**
     * @ IntDef 代替枚举
     */
    @IntDef({LENGTH_SHORT, LENGTH_LONG, LENGTH_SHOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public ActionBarToast(AppCompatActivity activity) {
        decorView = (ViewGroup) activity.getWindow().getDecorView();
        view = LayoutInflater.from(activity).inflate(R.layout.item_toast, null);
        //设置View的高度
        totalHeight = getHeight(activity, view.findViewById(R.id.status_bar));
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, totalHeight));
    }

    /**
     * 显示
     *
     * @param duration 显示时长
     */
    public void showToast(@Duration int duration) {
        if (!showing) {
            showing = true;
            decorView.addView(view);
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -totalHeight, 0f);
            animator.setDuration(animationDuration);
            animator.start();
            if (duration != LENGTH_SHOW) {
                //一段时间后隐藏
                view.postDelayed(runnable, duration);
            }
        }
    }

    /**
     * 延时执行取消操作
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            cancel();
        }
    };

    /**
     * 取消展示
     */
    public void cancel() {
        if (!showing) {
            return;
        }
        //手动移除异步计时器
        view.removeCallbacks(runnable);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0f, -totalHeight);
        animator.setDuration(animationDuration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (view.getParent() != null) {
                    ((ViewGroup) view.getParent()).removeView(view);
                    showing = false;
                }
            }
        });
        animator.start();
    }

    /**
     * 是否正在展示
     *
     * @return {@link #showing}
     */
    public boolean isShowing() {
        return showing;
    }

    /**
     * 获取状态栏 + 标题栏 的高度
     */
    private int getHeight(Context context, View v) {
        //标题栏
        TypedArray values = context.getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarHeight = values.getDimensionPixelSize(0, 0);
        values.recycle();
        //状态栏
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //设置布局 占位视图的高度
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

            v.setLayoutParams(new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, statusBarHeight));
        }
        return actionBarHeight + statusBarHeight;
    }
}
