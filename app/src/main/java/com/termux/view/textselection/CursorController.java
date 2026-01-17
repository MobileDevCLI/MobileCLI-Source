package com.termux.view.textselection;

import android.view.MotionEvent;
import android.view.ViewTreeObserver;

/* loaded from: classes.dex */
public interface CursorController extends ViewTreeObserver.OnTouchModeChangeListener {
    boolean hide();

    boolean isActive();

    void onDetached();

    boolean onTouchEvent(MotionEvent event);

    void render();

    void show(MotionEvent event);

    void updatePosition(TextSelectionHandleView handle, int x, int y);
}
