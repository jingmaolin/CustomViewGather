package mao.com.customviewgather.slide.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

/**
 * Description:简单的使用Scroller + Gesture模拟下拉滑動
 * author:jingmaolin
 * email:1271799407@qq.com.
 * phone:13342446520.
 * date: 2018/8/11.
 */
public class ScrollerViewGroupWithGesture extends RelativeLayout {
    private static final String TAG = "maoTest";

    private Scroller scroller;
    private GestureDetector gestureDetector;

    private final int ANIMATION_DURATION = 800;

    public ScrollerViewGroupWithGesture(Context context) {
        this(context, null);
    }

    public ScrollerViewGroupWithGesture(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerViewGroupWithGesture(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scroller = new Scroller(context);
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!gestureDetector.onTouchEvent(e) && e.getAction() == MotionEvent.ACTION_UP) {
            int translationY = (int) getTranslationY();
            scroller.startScroll(0, translationY, 0, -translationY, computerDuration(translationY));
            invalidate();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG, "computeScroll: ");
        if (scroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll 2: ");
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

    private void moveByDistance(int distance) {
        int translationY = (int) getTranslationY();
        if (Math.abs(translationY - distance) <= getHeight()) {
            ((View) getParent()).scrollBy(0, distance);
        } else {
            int offset = (translationY + distance) >= 0 ? getHeight() : -getHeight() + 1;
            setTranslationY(offset);
        }
        invalidate();
    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll: " + (int) distanceY);
            moveByDistance((int) distanceY);
            return true;
        }

//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            Log.d(TAG, "onFling: " + (int) velocityY);
//            moveByDistance((int) velocityY);
//            return true;
//        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: ");
            if (!scroller.computeScrollOffset()) {
                scroller.forceFinished(true);
            }
            return true;
        }
    };
}
