package com.termux.view;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/* loaded from: classes.dex */
final class GestureAndScaleRecognizer {
    boolean isAfterLongPress;
    private final GestureDetector mGestureDetector;
    final Listener mListener;
    private final ScaleGestureDetector mScaleDetector;

    public interface Listener {
        boolean onDoubleTap(MotionEvent e);

        boolean onDown(float x, float y);

        boolean onFling(MotionEvent e, float velocityX, float velocityY);

        void onLongPress(MotionEvent e);

        boolean onScale(float focusX, float focusY, float scale);

        boolean onScroll(MotionEvent e2, float dx, float dy);

        boolean onSingleTapUp(MotionEvent e);

        boolean onUp(MotionEvent e);
    }

    public GestureAndScaleRecognizer(Context context, Listener listener) {
        this.mListener = listener;
        GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.termux.view.GestureAndScaleRecognizer.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
                return GestureAndScaleRecognizer.this.mListener.onScroll(e2, dx, dy);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return GestureAndScaleRecognizer.this.mListener.onFling(e2, velocityX, velocityY);
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onDown(MotionEvent e) {
                return GestureAndScaleRecognizer.this.mListener.onDown(e.getX(), e.getY());
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public void onLongPress(MotionEvent e) {
                GestureAndScaleRecognizer.this.mListener.onLongPress(e);
                GestureAndScaleRecognizer.this.isAfterLongPress = true;
            }
        }, null, true);
        this.mGestureDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.termux.view.GestureAndScaleRecognizer.2
            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return GestureAndScaleRecognizer.this.mListener.onSingleTapUp(e);
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent e) {
                return GestureAndScaleRecognizer.this.mListener.onDoubleTap(e);
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent e) {
                return true;
            }
        });
        ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.termux.view.GestureAndScaleRecognizer.3
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return true;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                return GestureAndScaleRecognizer.this.mListener.onScale(detector.getFocusX(), detector.getFocusY(), detector.getScaleFactor());
            }
        });
        this.mScaleDetector = scaleGestureDetector;
        scaleGestureDetector.setQuickScaleEnabled(false);
    }

    public void onTouchEvent(MotionEvent event) {
        this.mGestureDetector.onTouchEvent(event);
        this.mScaleDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case 0:
                this.isAfterLongPress = false;
                break;
            case 1:
                if (!this.isAfterLongPress) {
                    this.mListener.onUp(event);
                    break;
                }
                break;
        }
    }

    public boolean isInProgress() {
        return this.mScaleDetector.isInProgress();
    }
}
