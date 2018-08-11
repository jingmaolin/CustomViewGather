package mao.com.customviewgather.slide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Description:简单的使用Scroller模拟下拉滑動。此处使用的是setTranslationY来设置新的位置，
 * ①不好的地方是当滑动一定到位置松开然后再立刻继续滑动时，若此时currentY-startY 与当前view的translationY不同，则会有一瞬间的错位效果，
 * 解决该问题的大致思路是 使用scrollBy来递增滑动的位移
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
                startY = (int) e.getRawY();
                Log.d(TAG, "onTouchEvent: getRawY= " + e.getRawY());
                Log.d(TAG, "onTouchEvent: " + getHeight());
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = (int) e.getRawY();
                int distance = currentY - startY;
                Log.d(TAG, "onTouchEvent: distance = " + distance);
                if (Math.abs(distance) <= getHeight()) {
                    setTranslationY(distance);
                } else {
                    int offset = distance >= 0 ? getHeight() : -getHeight() + 1;
                    Log.d(TAG, "onTouchEvent: result height=" + getHeight());
                    Log.d(TAG, "onTouchEvent: result offset=" + offset);
                    setTranslationY(offset);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                int dy = (int) getTranslationY();
                scroller.startScroll(0, dy, 0, -dy, computerDuration(dy));
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
