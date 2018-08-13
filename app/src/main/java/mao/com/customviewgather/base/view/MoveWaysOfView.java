package mao.com.customviewgather.base.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Description ：用于scrollTo,scrollBy,getTranslations,getX,getRawX,getScrollY等测试使用
 * Created by jingmaolin on 2018/8/13.
 */

public class MoveWaysOfView extends RelativeLayout {
    private static final String TAG = "maoTest";

    public MoveWaysOfView(Context context) {
        this(context, null);
    }

    public MoveWaysOfView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MoveWaysOfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //one
                getXAndRawX(event);
                getTranslations();

                setTranslationY(getTranslationY() + 10 * getResources().getDisplayMetrics().density);

                getXAndRawX(event);
                getTranslations();

                //two
//                scrollBy(0, (int) (10 * getResources().getDisplayMetrics().density));
//                getTranslations();

                //three
//                Log.d(TAG, "parent scrollY: " + ((View) getParent()).getScrollY());
//                ((View) getParent()).scrollBy(0, (int) (5 * getResources().getDisplayMetrics().density));
//                getTranslations();
//                Log.d(TAG, "parent scrollY: " + ((View) getParent()).getScrollY());

                //four
//                Log.d(TAG, "parent scrollY: " + ((View) getParent()).getScrollY());
//                Log.d(TAG, "child scrollY: " + getScrollY());
//                setTranslationY(10 * getResources().getDisplayMetrics().density);
//                Log.d(TAG, "child scrollY: " + getScrollY());
//                Log.d(TAG, "parent scrollY: " + ((View) getParent()).getScrollY());
                break;
        }
        invalidate();
        return true;
    }

    private void getXAndRawX(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: density = " + getResources().getDisplayMetrics().density);
        Log.d(TAG, "onTouchEvent: event.getX() = " + event.getX());
        Log.d(TAG, "onTouchEvent: event.getRawX() = " + event.getRawX());
    }

    private void getTranslations() {
        Log.d(TAG, "onTouchEvent: translation = " + getTranslationY());
    }
}
