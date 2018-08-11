package mao.com.customviewgather.slide.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

    private int startY = 0;
    private int currentY = 0;

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
    public boolean onTouchEvent(MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
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

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll: ");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling: ");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress: ");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: ");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp: ");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress: ");
            super.onLongPress(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: ");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d(TAG, "onDoubleTapEvent: ");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d(TAG, "onSingleTapConfirmed: ");
            return true;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.d(TAG, "onContextClick: ");
            return true;
        }
    };
}
