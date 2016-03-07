package com.example.hiyoriaya.weistodays;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements ViewFlipper.OnTouchListener{

    ViewFlipper flipper;
    // X軸最低スワイプ距離
    private static final int SWIPE_MIN_DISTANCE = 50;
    // X軸最低スワイプスピード
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    // タッチイベントを処理するためのインタフェース
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    //起動後の準備
    protected void setViews(){
        Uri.Builder builder = new Uri.Builder();
        AsyncHttpRequest task = new AsyncHttpRequest(this);
        task.execute(builder);
        flipper = (ViewFlipper)findViewById(R.id.flipper);
        mGestureDetector = new GestureDetector(this, mOnGestureListener);
        flipper.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    // タッチイベントのリスナー
    private final GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        // フリックイベント
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            try {
                if  (event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    flipper.showPrevious();
                } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    flipper.showNext();
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        //シングルタップイベント
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            flipper.showNext();
            return true;
        }
    };
}
