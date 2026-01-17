package com.termux.view.textselection;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.PopupWindow;
import androidx.core.view.PointerIconCompat;
import com.termux.view.R;
import com.termux.view.TerminalView;

/* loaded from: classes.dex */
public class TextSelectionHandleView extends View {
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    private final CursorController mCursorController;
    private PopupWindow mHandle;
    private Drawable mHandleDrawable;
    private int mHandleHeight;
    private final Drawable mHandleLeftDrawable;
    private final Drawable mHandleRightDrawable;
    private int mHandleWidth;
    private float mHotspotX;
    private float mHotspotY;
    private final int mInitialOrientation;
    private boolean mIsDragging;
    private int mLastParentX;
    private int mLastParentY;
    private long mLastTime;
    private int mOrientation;
    private int mPointX;
    private int mPointY;
    final int[] mTempCoords;
    Rect mTempRect;
    private float mTouchOffsetY;
    private float mTouchToWindowOffsetX;
    private float mTouchToWindowOffsetY;
    private final TerminalView terminalView;

    public TextSelectionHandleView(TerminalView terminalView, CursorController cursorController, int initialOrientation) {
        super(terminalView.getContext());
        this.mTempCoords = new int[2];
        this.terminalView = terminalView;
        this.mCursorController = cursorController;
        this.mInitialOrientation = initialOrientation;
        this.mHandleLeftDrawable = getContext().getDrawable(R.drawable.text_select_handle_left_material);
        this.mHandleRightDrawable = getContext().getDrawable(R.drawable.text_select_handle_right_material);
        setOrientation(initialOrientation);
    }

    private void initHandle() {
        PopupWindow popupWindow = new PopupWindow(this.terminalView.getContext(), (AttributeSet) null, android.R.attr.textSelectHandleWindowStyle);
        this.mHandle = popupWindow;
        popupWindow.setSplitTouchEnabled(true);
        this.mHandle.setClippingEnabled(false);
        this.mHandle.setWindowLayoutType(PointerIconCompat.TYPE_HAND);
        this.mHandle.setWidth(-2);
        this.mHandle.setHeight(-2);
        this.mHandle.setBackgroundDrawable(null);
        this.mHandle.setAnimationStyle(0);
        this.mHandle.setEnterTransition(null);
        this.mHandle.setExitTransition(null);
        this.mHandle.setContentView(this);
    }

    public void setOrientation(int orientation) {
        this.mOrientation = orientation;
        int handleWidth = 0;
        switch (orientation) {
            case 0:
                Drawable drawable = this.mHandleLeftDrawable;
                this.mHandleDrawable = drawable;
                handleWidth = drawable.getIntrinsicWidth();
                this.mHotspotX = (handleWidth * 3) / 4.0f;
                break;
            case 2:
                Drawable drawable2 = this.mHandleRightDrawable;
                this.mHandleDrawable = drawable2;
                handleWidth = drawable2.getIntrinsicWidth();
                this.mHotspotX = handleWidth / 4.0f;
                break;
        }
        this.mHandleHeight = this.mHandleDrawable.getIntrinsicHeight();
        this.mHandleWidth = handleWidth;
        this.mTouchOffsetY = (-r1) * 0.3f;
        this.mHotspotY = 0.0f;
        invalidate();
    }

    public void show() {
        if (!isPositionVisible()) {
            hide();
            return;
        }
        removeFromParent();
        initHandle();
        invalidate();
        int[] coords = this.mTempCoords;
        this.terminalView.getLocationInWindow(coords);
        coords[0] = coords[0] + this.mPointX;
        coords[1] = coords[1] + this.mPointY;
        PopupWindow popupWindow = this.mHandle;
        if (popupWindow != null) {
            popupWindow.showAtLocation(this.terminalView, 0, coords[0], coords[1]);
        }
    }

    public void hide() {
        this.mIsDragging = false;
        PopupWindow popupWindow = this.mHandle;
        if (popupWindow != null) {
            popupWindow.dismiss();
            removeFromParent();
            this.mHandle = null;
        }
        invalidate();
    }

    public void removeFromParent() {
        if (!isParentNull()) {
            ((ViewGroup) getParent()).removeView(this);
        }
    }

    public void positionAtCursor(final int cx, final int cy, boolean forceOrientationCheck) {
        int x = this.terminalView.getPointX(cx);
        int y = this.terminalView.getPointY(cy + 1);
        moveTo(x, y, forceOrientationCheck);
    }

    private void moveTo(int x, int y, boolean forceOrientationCheck) {
        float oldHotspotX = this.mHotspotX;
        checkChangedOrientation(x, forceOrientationCheck);
        this.mPointX = (int) (x - (isShowing() ? oldHotspotX : this.mHotspotX));
        this.mPointY = y;
        if (isPositionVisible()) {
            int[] coords = null;
            if (isShowing()) {
                coords = this.mTempCoords;
                this.terminalView.getLocationInWindow(coords);
                int x1 = coords[0] + this.mPointX;
                int y1 = coords[1] + this.mPointY;
                PopupWindow popupWindow = this.mHandle;
                if (popupWindow != null) {
                    popupWindow.update(x1, y1, getWidth(), getHeight());
                }
            } else {
                show();
            }
            if (this.mIsDragging) {
                if (coords == null) {
                    coords = this.mTempCoords;
                    this.terminalView.getLocationInWindow(coords);
                }
                if (coords[0] != this.mLastParentX || coords[1] != this.mLastParentY) {
                    this.mTouchToWindowOffsetX += coords[0] - r5;
                    this.mTouchToWindowOffsetY += coords[1] - this.mLastParentY;
                    this.mLastParentX = coords[0];
                    this.mLastParentY = coords[1];
                    return;
                }
                return;
            }
            return;
        }
        hide();
    }

    public void changeOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            setOrientation(orientation);
        }
    }

    private void checkChangedOrientation(int posX, boolean force) {
        if (!this.mIsDragging && !force) {
            return;
        }
        long millis = SystemClock.currentThreadTimeMillis();
        if (millis - this.mLastTime < 50 && !force) {
            return;
        }
        this.mLastTime = millis;
        TerminalView hostView = this.terminalView;
        int left = hostView.getLeft();
        int right = hostView.getWidth();
        int top = hostView.getTop();
        int bottom = hostView.getHeight();
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Rect clip = this.mTempRect;
        clip.left = this.terminalView.getPaddingLeft() + left;
        clip.top = this.terminalView.getPaddingTop() + top;
        clip.right = right - this.terminalView.getPaddingRight();
        clip.bottom = bottom - this.terminalView.getPaddingBottom();
        ViewParent parent = hostView.getParent();
        if (parent == null || !parent.getChildVisibleRect(hostView, clip, null)) {
            return;
        }
        if (posX - this.mHandleWidth < clip.left) {
            changeOrientation(2);
        } else if (this.mHandleWidth + posX > clip.right) {
            changeOrientation(0);
        } else {
            changeOrientation(this.mInitialOrientation);
        }
    }

    private boolean isPositionVisible() {
        if (this.mIsDragging) {
            return true;
        }
        TerminalView hostView = this.terminalView;
        int right = hostView.getWidth();
        int bottom = hostView.getHeight();
        if (this.mTempRect == null) {
            this.mTempRect = new Rect();
        }
        Rect clip = this.mTempRect;
        clip.left = this.terminalView.getPaddingLeft() + 0;
        clip.top = this.terminalView.getPaddingTop() + 0;
        clip.right = right - this.terminalView.getPaddingRight();
        clip.bottom = bottom - this.terminalView.getPaddingBottom();
        ViewParent parent = hostView.getParent();
        if (parent == null || !parent.getChildVisibleRect(hostView, clip, null)) {
            return false;
        }
        int[] coords = this.mTempCoords;
        hostView.getLocationInWindow(coords);
        int posX = coords[0] + this.mPointX + ((int) this.mHotspotX);
        int posY = coords[1] + this.mPointY + ((int) this.mHotspotY);
        return posX >= clip.left && posX <= clip.right && posY >= clip.top && posY <= clip.bottom;
    }

    @Override // android.view.View
    public void onDraw(Canvas c) {
        int width = this.mHandleDrawable.getIntrinsicWidth();
        int height = this.mHandleDrawable.getIntrinsicHeight();
        this.mHandleDrawable.setBounds(0, 0, width, height);
        this.mHandleDrawable.draw(c);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        this.terminalView.updateFloatingToolbarVisibility(event);
        switch (event.getActionMasked()) {
            case 0:
                float rawX = event.getRawX();
                float rawY = event.getRawY();
                this.mTouchToWindowOffsetX = rawX - this.mPointX;
                this.mTouchToWindowOffsetY = rawY - this.mPointY;
                int[] coords = this.mTempCoords;
                this.terminalView.getLocationInWindow(coords);
                this.mLastParentX = coords[0];
                this.mLastParentY = coords[1];
                this.mIsDragging = true;
                return true;
            case 1:
            case 3:
                this.mIsDragging = false;
                return true;
            case 2:
                float rawX2 = event.getRawX();
                float rawY2 = event.getRawY();
                float newPosX = (rawX2 - this.mTouchToWindowOffsetX) + this.mHotspotX;
                float newPosY = (rawY2 - this.mTouchToWindowOffsetY) + this.mHotspotY + this.mTouchOffsetY;
                this.mCursorController.updatePosition(this, Math.round(newPosX), Math.round(newPosY));
                return true;
            default:
                return true;
        }
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(this.mHandleDrawable.getIntrinsicWidth(), this.mHandleDrawable.getIntrinsicHeight());
    }

    public int getHandleHeight() {
        return this.mHandleHeight;
    }

    public int getHandleWidth() {
        return this.mHandleWidth;
    }

    public boolean isShowing() {
        PopupWindow popupWindow = this.mHandle;
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    public boolean isParentNull() {
        return getParent() == null;
    }

    public boolean isDragging() {
        return this.mIsDragging;
    }
}
