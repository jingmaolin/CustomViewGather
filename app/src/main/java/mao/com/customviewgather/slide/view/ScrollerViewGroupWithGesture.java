package mao.com.customviewgather.slide.view;

import android.content.Context;
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
    public boolean onTouchEvent(MotionEvent e) {
        if (!gestureDetector.onTouchEvent(e) && e.getAction() == MotionEvent.ACTION_UP) {
            lastY = 0;
            int scrollY = ((View) getParent()).getScrollY();
            scroller.startScroll(0, scrollY, 0, -scrollY, computerDuration(scrollY));
            invalidate();
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            Log.d(TAG, "computeScroll: ");
            int dy = scroller.getCurrY();
            ((View) getParent()).scrollTo(0, dy);
            invalidate();
        }
    }

    /**
     * 注意 dy * 1f 的必要性
     */
    private int computerDuration(int dy) {
        Log.d(TAG, "computerDuration: " + Math.abs(dy * 1f) / getHeight() * ANIMATION_DURATION);
        return dy == 0 ? 0 : (int) (Math.abs(dy * 1f) / getHeight() * ANIMATION_DURATION);
    }

    private void moveByDistance(int dy) {
        int scrollY = ((View) getParent()).getScrollY();
        int distance = dy + scrollY;
        if (Math.abs(distance) < getHeight()) {
            ((View) getParent()).scrollBy(0, dy);
        } else {
            //加1或者减是防止视图完全滑出，进而导致computeScroll不会被调用
            int offset = distance >= 0 ? getHeight() - 1 : -getHeight() + 1;
            ((View) getParent()).scrollTo(0, offset);
        }
    }

    private int lastY = 0;


    /**
     * onScroll scrollBy scrollTo 中的屏幕滑动的值与x、y轴上对应的差值互为相反数
     */
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { //TODO 向下滑动 distanceY 的值一会为正一会为负
            Log.d(TAG, "onScroll: e2 = " + e2.getRawY());
            Log.d(TAG, "onScroll: " + (int) distanceY);

            //计算前后两个move事件的差值
            if (lastY == 0) {
                moveByDistance((int) -(e2.getRawY() - e1.getRawY()));
            } else {
                moveByDistance((int) -(e2.getRawY() - lastY));
            }
            lastY = (int) e2.getRawY();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown: ");
            if (scroller.computeScrollOffset()) {
                scroller.forceFinished(true);
            }
            return true;
        }
    };
}
