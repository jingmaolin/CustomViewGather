package mao.com.customviewgather.slide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Description:简单的使用Scroller模拟下拉滑動。此处使用的是setTranslationY来设置新的位置，
 * ①之前是用currentY-startY直接作为偏移量，不好的地方是当滑动一定到位置松开然后再立刻继续滑动时，
 * 若此时currentY-startY 与当前view的translationY不同，则会有一瞬间的错位效果，
 * 解决该问题的大致思路是 在每次move事件之后startY都更新，偏移量 =（currentY-startY）+ 当前的偏移量
 * ②视图滑动至view之外后，computeScroll方法不会被调用
 * author:jingmaolin
 * email:1271799407@qq.com.
 * phone:13342446520.
 * date: 2018/8/11.
 */
public class ScrollerViewGroup extends RelativeLayout {
    private static final String TAG = "maoTest";

    private int startY = 0;
    private int currentY = 0;
    private Scroller scroller;

    private final int ANIMATION_DURATION = 800;

    public ScrollerViewGroup(Context context) {
        this(context, null);
    }

    public ScrollerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (scroller.computeScrollOffset()) {
                    scroller.forceFinished(true);
                }
                startY = (int) e.getRawY();
                Log.d(TAG, "onTouchEvent: getRawY= " + e.getRawY());
                Log.d(TAG, "onTouchEvent: " + getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = (int) e.getRawY();
                int dy = currentY - startY;
                int distance = (int) (getTranslationY() + dy);
                //当滑动的距离超过屏幕的高度时，computeScroll不会被调用
                if (Math.abs(distance) < getHeight()) {
                    setTranslationY(distance);
                } else {
                    //当底部下滑位移完全为屏幕高度时仍正常恢复原样，但顶部需要略小于屏幕高度，否则滑动到顶部时向下拖动无反应
                    int offset = distance >= 0 ? getHeight() : -getHeight() + 1;
                    Log.d(TAG, "onTouchEvent: result height=" + getHeight());
                    Log.d(TAG, "onTouchEvent: result offset=" + offset);
                    setTranslationY(offset);
                }
                startY = currentY;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int translationY = (int) getTranslationY();
                scroller.startScroll(0, translationY, 0, -translationY, computerDuration(translationY));
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG, "computeScroll: ");
        if (scroller.computeScrollOffset()) {
            int dy = scroller.getCurrY();
            moveViewToPosition(dy);
        }
    }

    private void moveViewToPosition(int dy) {
        setTranslationY(dy);
        invalidate();
    }

    /**
     * 注意 dy * 1f 的必要性
     */
    private int computerDuration(int dy) {
        Log.d(TAG, "computerDuration: " + Math.abs(dy * 1f) / getHeight() * ANIMATION_DURATION);
        return dy == 0 ? 0 : (int) (Math.abs(dy * 1f) / getHeight() * ANIMATION_DURATION);
    }
}
