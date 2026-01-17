package com.termux.view;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Scroller;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import com.termux.terminal.KeyHandler;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSession;
import com.termux.view.GestureAndScaleRecognizer;
import com.termux.view.textselection.TextSelectionCursorController;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* loaded from: classes.dex */
public final class TerminalView extends View {
    private static final String LOG_TAG = "TerminalView";
    public static final int TERMINAL_CURSOR_BLINK_RATE_MAX = 2000;
    public static final int TERMINAL_CURSOR_BLINK_RATE_MIN = 100;
    private static boolean TERMINAL_VIEW_KEY_LOGGING_ENABLED = false;
    private final boolean mAccessibilityEnabled;
    public TerminalViewClient mClient;
    int mCombiningAccent;
    private boolean mCursorInvisibleIgnoreOnce;
    int[] mDefaultSelectors;
    public TerminalEmulator mEmulator;
    final GestureAndScaleRecognizer mGestureRecognizer;
    private int mMouseScrollStartX;
    private int mMouseScrollStartY;
    private long mMouseStartDownTime;
    public TerminalRenderer mRenderer;
    float mScaleFactor;
    float mScrollRemainder;
    final Scroller mScroller;
    private final Runnable mShowFloatingToolbar;
    public TerminalSession mTermSession;
    private Handler mTerminalCursorBlinkerHandler;
    private int mTerminalCursorBlinkerRate;
    private TerminalCursorBlinkerRunnable mTerminalCursorBlinkerRunnable;
    private TextSelectionCursorController mTextSelectionCursorController;
    int mTopRow;

    public TerminalView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.mDefaultSelectors = new int[]{-1, -1, -1, -1};
        this.mScaleFactor = 1.0f;
        this.mMouseScrollStartX = -1;
        this.mMouseScrollStartY = -1;
        this.mMouseStartDownTime = -1L;
        this.mShowFloatingToolbar = new Runnable() { // from class: com.termux.view.TerminalView.3
            @Override // java.lang.Runnable
            public void run() {
                if (TerminalView.this.getTextSelectionActionMode() != null) {
                    TerminalView.this.getTextSelectionActionMode().hide(0L);
                }
            }
        };
        this.mGestureRecognizer = new GestureAndScaleRecognizer(context, new GestureAndScaleRecognizer.Listener() { // from class: com.termux.view.TerminalView.1
            boolean scrolledWithFinger;

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onUp(MotionEvent event) {
                TerminalView.this.mScrollRemainder = 0.0f;
                if (TerminalView.this.mEmulator != null && TerminalView.this.mEmulator.isMouseTrackingActive() && !event.isFromSource(8194) && !TerminalView.this.isSelectingText() && !this.scrolledWithFinger) {
                    TerminalView.this.sendMouseEventCode(event, 0, true);
                    TerminalView.this.sendMouseEventCode(event, 0, false);
                    return true;
                }
                this.scrolledWithFinger = false;
                return false;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onSingleTapUp(MotionEvent event) {
                if (TerminalView.this.mEmulator == null) {
                    return true;
                }
                if (TerminalView.this.isSelectingText()) {
                    TerminalView.this.stopTextSelectionMode();
                    return true;
                }
                TerminalView.this.requestFocus();
                TerminalView.this.mClient.onSingleTapUp(event);
                return true;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onScroll(MotionEvent e, float distanceX, float distanceY) {
                if (TerminalView.this.mEmulator == null) {
                    return true;
                }
                if (TerminalView.this.mEmulator.isMouseTrackingActive() && e.isFromSource(8194)) {
                    TerminalView.this.sendMouseEventCode(e, 32, true);
                } else {
                    this.scrolledWithFinger = true;
                    float distanceY2 = distanceY + TerminalView.this.mScrollRemainder;
                    int deltaRows = (int) (distanceY2 / TerminalView.this.mRenderer.mFontLineSpacing);
                    TerminalView.this.mScrollRemainder = distanceY2 - (r2.mRenderer.mFontLineSpacing * deltaRows);
                    TerminalView.this.doScroll(e, deltaRows);
                }
                return true;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onScale(float focusX, float focusY, float scale) {
                if (TerminalView.this.mEmulator == null || TerminalView.this.isSelectingText()) {
                    return true;
                }
                TerminalView.this.mScaleFactor *= scale;
                TerminalView terminalView = TerminalView.this;
                terminalView.mScaleFactor = terminalView.mClient.onScale(TerminalView.this.mScaleFactor);
                return true;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onFling(final MotionEvent e2, float velocityX, float velocityY) {
                if (TerminalView.this.mEmulator == null || !TerminalView.this.mScroller.isFinished()) {
                    return true;
                }
                final boolean mouseTrackingAtStartOfFling = TerminalView.this.mEmulator.isMouseTrackingActive();
                if (mouseTrackingAtStartOfFling) {
                    TerminalView.this.mScroller.fling(0, 0, 0, -((int) (velocityY * 0.25f)), 0, 0, (-TerminalView.this.mEmulator.mRows) / 2, TerminalView.this.mEmulator.mRows / 2);
                } else {
                    TerminalView.this.mScroller.fling(0, TerminalView.this.mTopRow, 0, -((int) (velocityY * 0.25f)), 0, 0, -TerminalView.this.mEmulator.getScreen().getActiveTranscriptRows(), 0);
                }
                TerminalView.this.post(new Runnable() { // from class: com.termux.view.TerminalView.1.1
                    private int mLastY = 0;

                    @Override // java.lang.Runnable
                    public void run() {
                        if (mouseTrackingAtStartOfFling != TerminalView.this.mEmulator.isMouseTrackingActive()) {
                            TerminalView.this.mScroller.abortAnimation();
                            return;
                        }
                        if (TerminalView.this.mScroller.isFinished()) {
                            return;
                        }
                        boolean more = TerminalView.this.mScroller.computeScrollOffset();
                        int newY = TerminalView.this.mScroller.getCurrY();
                        int diff = newY - (mouseTrackingAtStartOfFling ? this.mLastY : TerminalView.this.mTopRow);
                        TerminalView.this.doScroll(e2, diff);
                        this.mLastY = newY;
                        if (more) {
                            TerminalView.this.post(this);
                        }
                    }
                });
                return true;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onDown(float x, float y) {
                return false;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public boolean onDoubleTap(MotionEvent event) {
                return false;
            }

            @Override // com.termux.view.GestureAndScaleRecognizer.Listener
            public void onLongPress(MotionEvent event) {
                if (!TerminalView.this.mGestureRecognizer.isInProgress() && !TerminalView.this.mClient.onLongPress(event) && !TerminalView.this.isSelectingText()) {
                    TerminalView.this.performHapticFeedback(0);
                    TerminalView.this.startTextSelectionMode(event);
                }
            }
        });
        this.mScroller = new Scroller(context);
        AccessibilityManager am = (AccessibilityManager) context.getSystemService("accessibility");
        this.mAccessibilityEnabled = am.isEnabled();
    }

    public void setTerminalViewClient(TerminalViewClient client) {
        this.mClient = client;
    }

    public void setIsTerminalViewKeyLoggingEnabled(boolean value) {
        TERMINAL_VIEW_KEY_LOGGING_ENABLED = value;
    }

    public boolean attachSession(TerminalSession session) {
        if (session == this.mTermSession) {
            return false;
        }
        this.mTopRow = 0;
        this.mTermSession = session;
        this.mEmulator = null;
        this.mCombiningAccent = 0;
        updateSize();
        setVerticalScrollBarEnabled(true);
        return true;
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        boolean z = true;
        if (this.mClient.isTerminalViewSelected()) {
            if (this.mClient.shouldEnforceCharBasedInput()) {
                outAttrs.inputType = 524432;
            } else {
                outAttrs.inputType = 0;
            }
        } else {
            outAttrs.inputType = 1;
        }
        outAttrs.imeOptions = 33554432;
        return new BaseInputConnection(this, z) { // from class: com.termux.view.TerminalView.2
            @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
            public boolean finishComposingText() {
                if (TerminalView.TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                    TerminalView.this.mClient.logInfo(TerminalView.LOG_TAG, "IME: finishComposingText()");
                }
                super.finishComposingText();
                sendTextToTerminal(getEditable());
                getEditable().clear();
                return true;
            }

            @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
            public boolean commitText(CharSequence text, int newCursorPosition) {
                if (TerminalView.TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                    TerminalView.this.mClient.logInfo(TerminalView.LOG_TAG, "IME: commitText(\"" + ((Object) text) + "\", " + newCursorPosition + ")");
                }
                super.commitText(text, newCursorPosition);
                if (TerminalView.this.mEmulator == null) {
                    return true;
                }
                Editable content = getEditable();
                sendTextToTerminal(content);
                content.clear();
                return true;
            }

            @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
            public boolean deleteSurroundingText(int leftLength, int rightLength) {
                if (TerminalView.TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                    TerminalView.this.mClient.logInfo(TerminalView.LOG_TAG, "IME: deleteSurroundingText(" + leftLength + ", " + rightLength + ")");
                }
                KeyEvent deleteKey = new KeyEvent(0, 67);
                for (int i = 0; i < leftLength; i++) {
                    sendKeyEvent(deleteKey);
                }
                return super.deleteSurroundingText(leftLength, rightLength);
            }

            void sendTextToTerminal(CharSequence text) {
                int codePoint;
                TerminalView.this.stopTextSelectionMode();
                int textLengthInChars = text.length();
                int i = 0;
                while (i < textLengthInChars) {
                    char firstChar = text.charAt(i);
                    if (Character.isHighSurrogate(firstChar)) {
                        i++;
                        if (i < textLengthInChars) {
                            codePoint = Character.toCodePoint(firstChar, text.charAt(i));
                        } else {
                            codePoint = TerminalEmulator.UNICODE_REPLACEMENT_CHAR;
                        }
                    } else {
                        codePoint = firstChar;
                    }
                    if (TerminalView.this.mClient.readShiftKey()) {
                        codePoint = Character.toUpperCase(codePoint);
                    }
                    boolean ctrlHeld = false;
                    if (codePoint <= 31 && codePoint != 27) {
                        if (codePoint == 10) {
                            codePoint = 13;
                        }
                        ctrlHeld = true;
                        switch (codePoint) {
                            case 28:
                                codePoint = 92;
                                break;
                            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_HORIZONTAL_BIAS /* 29 */:
                                codePoint = 93;
                                break;
                            case 30:
                                codePoint = 94;
                                break;
                            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_WIDTH_DEFAULT /* 31 */:
                                codePoint = 95;
                                break;
                            default:
                                codePoint += 96;
                                break;
                        }
                    }
                    TerminalView.this.inputCodePoint(codePoint, ctrlHeld, false);
                    i++;
                }
            }
        };
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return 1;
        }
        return terminalEmulator.getScreen().getActiveRows();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return 1;
        }
        return terminalEmulator.mRows;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return 1;
        }
        return (terminalEmulator.getScreen().getActiveRows() + this.mTopRow) - this.mEmulator.mRows;
    }

    public void onScreenUpdated() {
        int i;
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return;
        }
        int rowsInHistory = terminalEmulator.getScreen().getActiveTranscriptRows();
        if (this.mTopRow < (-rowsInHistory)) {
            this.mTopRow = -rowsInHistory;
        }
        boolean skipScrolling = false;
        if (isSelectingText()) {
            int rowShift = this.mEmulator.getScrollCounter();
            int i2 = this.mTopRow;
            if ((-i2) + rowShift > rowsInHistory) {
                stopTextSelectionMode();
            } else {
                skipScrolling = true;
                this.mTopRow = i2 - rowShift;
                decrementYTextSelectionCursors(rowShift);
            }
        }
        if (!skipScrolling && (i = this.mTopRow) != 0) {
            if (i < -3) {
                awakenScrollBars();
            }
            this.mTopRow = 0;
        }
        this.mEmulator.clearScrollCounter();
        invalidate();
        if (this.mAccessibilityEnabled) {
            setContentDescription(getText());
        }
    }

    public void setTextSize(int textSize) {
        TerminalRenderer terminalRenderer = this.mRenderer;
        this.mRenderer = new TerminalRenderer(textSize, terminalRenderer == null ? Typeface.MONOSPACE : terminalRenderer.mTypeface);
        updateSize();
    }

    public void setTypeface(Typeface newTypeface) {
        this.mRenderer = new TerminalRenderer(this.mRenderer.mTextSize, newTypeface);
        updateSize();
        invalidate();
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return true;
    }

    @Override // android.view.View
    public boolean isOpaque() {
        return true;
    }

    public int[] getColumnAndRow(MotionEvent event, boolean relativeToScroll) {
        int column = (int) (event.getX() / this.mRenderer.mFontWidth);
        int row = (int) ((event.getY() - this.mRenderer.mFontLineSpacingAndAscent) / this.mRenderer.mFontLineSpacing);
        if (relativeToScroll) {
            row += this.mTopRow;
        }
        return new int[]{column, row};
    }

    void sendMouseEventCode(MotionEvent e, int button, boolean pressed) {
        int[] columnAndRow = getColumnAndRow(e, false);
        int x = columnAndRow[0] + 1;
        int y = columnAndRow[1] + 1;
        if (pressed && (button == 65 || button == 64)) {
            if (this.mMouseStartDownTime == e.getDownTime()) {
                x = this.mMouseScrollStartX;
                y = this.mMouseScrollStartY;
            } else {
                this.mMouseStartDownTime = e.getDownTime();
                this.mMouseScrollStartX = x;
                this.mMouseScrollStartY = y;
            }
        }
        this.mEmulator.sendMouseEvent(button, x, y, pressed);
    }

    void doScroll(MotionEvent event, int rowsDown) {
        boolean up = rowsDown < 0;
        int amount = Math.abs(rowsDown);
        for (int i = 0; i < amount; i++) {
            if (this.mEmulator.isMouseTrackingActive()) {
                sendMouseEventCode(event, up ? 64 : 65, true);
            } else if (this.mEmulator.isAlternateBufferActive()) {
                handleKeyCode(up ? 19 : 20, 0);
            } else {
                this.mTopRow = Math.min(0, Math.max(-this.mEmulator.getScreen().getActiveTranscriptRows(), this.mTopRow + (up ? -1 : 1)));
                if (!awakenScrollBars()) {
                    invalidate();
                }
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent event) {
        if (this.mEmulator == null || !event.isFromSource(8194) || event.getAction() != 8) {
            return false;
        }
        boolean up = event.getAxisValue(9) > 0.0f;
        doScroll(event, up ? -3 : 3);
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mEmulator == null) {
            return true;
        }
        int action = event.getAction();
        if (isSelectingText()) {
            updateFloatingToolbarVisibility(event);
            this.mGestureRecognizer.onTouchEvent(event);
            return true;
        }
        if (event.isFromSource(8194)) {
            if (event.isButtonPressed(2)) {
                if (action == 0) {
                    showContextMenu();
                }
                return true;
            }
            if (event.isButtonPressed(4)) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService("clipboard");
                ClipData clipData = clipboard.getPrimaryClip();
                if (clipData != null) {
                    CharSequence paste = clipData.getItemAt(0).coerceToText(getContext());
                    if (!TextUtils.isEmpty(paste)) {
                        this.mEmulator.paste(paste.toString());
                    }
                }
            } else if (this.mEmulator.isMouseTrackingActive()) {
                switch (event.getAction()) {
                    case 0:
                    case 1:
                        sendMouseEventCode(event, 0, event.getAction() == 0);
                        break;
                    case 2:
                        sendMouseEventCode(event, 32, true);
                        break;
                }
            }
        }
        this.mGestureRecognizer.onTouchEvent(event);
        return true;
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
            this.mClient.logInfo(LOG_TAG, "onKeyPreIme(keyCode=" + keyCode + ", event=" + event + ")");
        }
        if (keyCode == 4) {
            if (isSelectingText()) {
                stopTextSelectionMode();
                return true;
            }
            if (this.mClient.shouldBackButtonBeMappedToEscape()) {
                switch (event.getAction()) {
                    case 0:
                        return onKeyDown(keyCode, event);
                    case 1:
                        return onKeyUp(keyCode, event);
                }
            }
        } else if (this.mClient.shouldUseCtrlSpaceWorkaround() && keyCode == 62 && event.isCtrlPressed()) {
            return onKeyDown(keyCode, event);
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
            this.mClient.logInfo(LOG_TAG, "onKeyDown(keyCode=" + keyCode + ", isSystem()=" + event.isSystem() + ", event=" + event + ")");
        }
        if (this.mEmulator == null) {
            return true;
        }
        if (isSelectingText()) {
            stopTextSelectionMode();
        }
        if (this.mClient.onKeyDown(keyCode, event, this.mTermSession)) {
            invalidate();
            return true;
        }
        if (event.isSystem() && (!this.mClient.shouldBackButtonBeMappedToEscape() || keyCode != 4)) {
            return super.onKeyDown(keyCode, event);
        }
        if (event.getAction() == 2 && keyCode == 0) {
            this.mTermSession.write(event.getCharacters());
            return true;
        }
        int metaState = event.getMetaState();
        boolean controlDown = event.isCtrlPressed() || this.mClient.readControlKey();
        boolean leftAltDown = (metaState & 16) != 0 || this.mClient.readAltKey();
        boolean shiftDown = event.isShiftPressed() || this.mClient.readShiftKey();
        boolean rightAltDownFromEvent = (metaState & 32) != 0;
        int keyMod = controlDown ? 0 | 1073741824 : 0;
        if (event.isAltPressed() || leftAltDown) {
            keyMod |= Integer.MIN_VALUE;
        }
        if (shiftDown) {
            keyMod |= KeyHandler.KEYMOD_SHIFT;
        }
        if (event.isNumLockOn()) {
            keyMod |= KeyHandler.KEYMOD_NUM_LOCK;
        }
        if (!event.isFunctionPressed() && handleKeyCode(keyCode, keyMod)) {
            if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                this.mClient.logInfo(LOG_TAG, "handleKeyCode() took key event");
            }
            return true;
        }
        int bitsToClear = 28672;
        if (!rightAltDownFromEvent) {
            bitsToClear = 28672 | 18;
        }
        int effectiveMetaState = event.getMetaState() & (~bitsToClear);
        if (shiftDown) {
            effectiveMetaState |= 65;
        }
        if (this.mClient.readFnKey()) {
            effectiveMetaState |= 8;
        }
        int result = event.getUnicodeChar(effectiveMetaState);
        if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
            this.mClient.logInfo(LOG_TAG, "KeyEvent#getUnicodeChar(" + effectiveMetaState + ") returned: " + result);
        }
        if (result == 0) {
            return false;
        }
        int oldCombiningAccent = this.mCombiningAccent;
        if ((Integer.MIN_VALUE & result) != 0) {
            int i = this.mCombiningAccent;
            if (i != 0) {
                inputCodePoint(i, controlDown, leftAltDown);
            }
            this.mCombiningAccent = Integer.MAX_VALUE & result;
        } else {
            int i2 = this.mCombiningAccent;
            if (i2 != 0) {
                int combinedChar = KeyCharacterMap.getDeadChar(i2, result);
                if (combinedChar > 0) {
                    result = combinedChar;
                }
                this.mCombiningAccent = 0;
            }
            inputCodePoint(result, controlDown, leftAltDown);
        }
        if (this.mCombiningAccent != oldCombiningAccent) {
            invalidate();
            return true;
        }
        return true;
    }

    public void inputCodePoint(int codePoint, boolean controlDownFromEvent, boolean leftAltDownFromEvent) {
        if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
            this.mClient.logInfo(LOG_TAG, "inputCodePoint(codePoint=" + codePoint + ", controlDownFromEvent=" + controlDownFromEvent + ", leftAltDownFromEvent=" + leftAltDownFromEvent + ")");
        }
        if (this.mTermSession == null) {
            return;
        }
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator != null) {
            terminalEmulator.setCursorBlinkState(true);
        }
        boolean controlDown = controlDownFromEvent || this.mClient.readControlKey();
        boolean altDown = leftAltDownFromEvent || this.mClient.readAltKey();
        if (this.mClient.onCodePoint(codePoint, controlDown, this.mTermSession)) {
            return;
        }
        if (controlDown) {
            if (codePoint >= 97 && codePoint <= 122) {
                codePoint = (codePoint - 97) + 1;
            } else if (codePoint >= 65 && codePoint <= 90) {
                codePoint = (codePoint - 65) + 1;
            } else if (codePoint == 32 || codePoint == 50) {
                codePoint = 0;
            } else if (codePoint == 91 || codePoint == 51) {
                codePoint = 27;
            } else if (codePoint == 92 || codePoint == 52) {
                codePoint = 28;
            } else if (codePoint == 93 || codePoint == 53) {
                codePoint = 29;
            } else if (codePoint == 94 || codePoint == 54) {
                codePoint = 30;
            } else if (codePoint == 95 || codePoint == 55 || codePoint == 47) {
                codePoint = 31;
            } else if (codePoint == 56) {
                codePoint = WorkQueueKt.MASK;
            }
        }
        if (codePoint > -1) {
            switch (codePoint) {
                case 710:
                    codePoint = 94;
                    break;
                case 715:
                    codePoint = 96;
                    break;
                case 732:
                    codePoint = 126;
                    break;
            }
            this.mTermSession.writeCodePoint(altDown, codePoint);
        }
    }

    public boolean handleKeyCode(int keyCode, int keyMod) {
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator != null) {
            terminalEmulator.setCursorBlinkState(true);
        }
        TerminalEmulator term = this.mTermSession.getEmulator();
        String code = KeyHandler.getCode(keyCode, keyMod, term.isCursorKeysApplicationMode(), term.isKeypadApplicationMode());
        if (code == null) {
            return false;
        }
        this.mTermSession.write(code);
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
            this.mClient.logInfo(LOG_TAG, "onKeyUp(keyCode=" + keyCode + ", event=" + event + ")");
        }
        if (this.mEmulator == null && keyCode != 4) {
            return true;
        }
        if (this.mClient.onKeyUp(keyCode, event)) {
            invalidate();
            return true;
        }
        if (event.isSystem()) {
            return super.onKeyUp(keyCode, event);
        }
        return true;
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateSize();
    }

    public void updateSize() {
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        if (viewWidth == 0 || viewHeight == 0 || this.mTermSession == null) {
            return;
        }
        int newColumns = Math.max(4, (int) (viewWidth / this.mRenderer.mFontWidth));
        int newRows = Math.max(4, (viewHeight - this.mRenderer.mFontLineSpacingAndAscent) / this.mRenderer.mFontLineSpacing);
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null || newColumns != terminalEmulator.mColumns || newRows != this.mEmulator.mRows) {
            this.mTermSession.updateSize(newColumns, newRows);
            this.mEmulator = this.mTermSession.getEmulator();
            this.mClient.onEmulatorSet();
            TerminalCursorBlinkerRunnable terminalCursorBlinkerRunnable = this.mTerminalCursorBlinkerRunnable;
            if (terminalCursorBlinkerRunnable != null) {
                terminalCursorBlinkerRunnable.setEmulator(this.mEmulator);
            }
            this.mTopRow = 0;
            scrollTo(0, 0);
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.mEmulator == null) {
            canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
            return;
        }
        int[] sel = this.mDefaultSelectors;
        TextSelectionCursorController textSelectionCursorController = this.mTextSelectionCursorController;
        if (textSelectionCursorController != null) {
            textSelectionCursorController.getSelectors(sel);
        }
        this.mRenderer.render(this.mEmulator, canvas, this.mTopRow, sel[0], sel[1], sel[2], sel[3]);
        renderTextSelection();
    }

    public TerminalSession getCurrentSession() {
        return this.mTermSession;
    }

    private CharSequence getText() {
        return this.mEmulator.getScreen().getSelectedText(0, this.mTopRow, this.mEmulator.mColumns, this.mTopRow + this.mEmulator.mRows);
    }

    public int getCursorX(float x) {
        return (int) (x / this.mRenderer.mFontWidth);
    }

    public int getCursorY(float y) {
        return (int) (((y - 40.0f) / this.mRenderer.mFontLineSpacing) + this.mTopRow);
    }

    public int getPointX(int cx) {
        if (cx > this.mEmulator.mColumns) {
            cx = this.mEmulator.mColumns;
        }
        return Math.round(cx * this.mRenderer.mFontWidth);
    }

    public int getPointY(int cy) {
        return Math.round((cy - this.mTopRow) * this.mRenderer.mFontLineSpacing);
    }

    public int getTopRow() {
        return this.mTopRow;
    }

    public void setTopRow(int mTopRow) {
        this.mTopRow = mTopRow;
    }

    @Override // android.view.View
    public void autofill(AutofillValue value) {
        if (value.isText()) {
            this.mTermSession.write(value.getTextValue().toString());
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return 1;
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        return AutofillValue.forText("");
    }

    public synchronized boolean setTerminalCursorBlinkerRate(int blinkRate) {
        boolean result;
        if (blinkRate != 0 && (blinkRate < 100 || blinkRate > 2000)) {
            this.mClient.logError(LOG_TAG, "The cursor blink rate must be in between 100-2000: " + blinkRate);
            this.mTerminalCursorBlinkerRate = 0;
            result = false;
        } else {
            this.mClient.logVerbose(LOG_TAG, "Setting cursor blinker rate to " + blinkRate);
            this.mTerminalCursorBlinkerRate = blinkRate;
            result = true;
        }
        if (this.mTerminalCursorBlinkerRate == 0) {
            this.mClient.logVerbose(LOG_TAG, "Cursor blinker disabled");
            stopTerminalCursorBlinker();
        }
        return result;
    }

    public synchronized void setTerminalCursorBlinkerState(boolean start, boolean startOnlyIfCursorEnabled) {
        stopTerminalCursorBlinker();
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return;
        }
        terminalEmulator.setCursorBlinkingEnabled(false);
        if (start) {
            int i = this.mTerminalCursorBlinkerRate;
            if (i >= 100 && i <= 2000) {
                if (startOnlyIfCursorEnabled && !this.mEmulator.isCursorEnabled()) {
                    if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                        this.mClient.logVerbose(LOG_TAG, "Ignoring call to start cursor blinker since cursor is not enabled");
                    }
                    return;
                }
                if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                    this.mClient.logVerbose(LOG_TAG, "Starting cursor blinker with the blink rate " + this.mTerminalCursorBlinkerRate);
                }
                if (this.mTerminalCursorBlinkerHandler == null) {
                    this.mTerminalCursorBlinkerHandler = new Handler(Looper.getMainLooper());
                }
                this.mTerminalCursorBlinkerRunnable = new TerminalCursorBlinkerRunnable(this.mEmulator, this.mTerminalCursorBlinkerRate);
                this.mEmulator.setCursorBlinkingEnabled(true);
                this.mTerminalCursorBlinkerRunnable.run();
            }
        }
    }

    private void stopTerminalCursorBlinker() {
        if (this.mTerminalCursorBlinkerHandler != null && this.mTerminalCursorBlinkerRunnable != null) {
            if (TERMINAL_VIEW_KEY_LOGGING_ENABLED) {
                this.mClient.logVerbose(LOG_TAG, "Stopping cursor blinker");
            }
            this.mTerminalCursorBlinkerHandler.removeCallbacks(this.mTerminalCursorBlinkerRunnable);
        }
    }

    private class TerminalCursorBlinkerRunnable implements Runnable {
        private final int mBlinkRate;
        boolean mCursorVisible = false;
        private TerminalEmulator mEmulator;

        public TerminalCursorBlinkerRunnable(TerminalEmulator emulator, int blinkRate) {
            this.mEmulator = emulator;
            this.mBlinkRate = blinkRate;
        }

        public void setEmulator(TerminalEmulator emulator) {
            this.mEmulator = emulator;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                TerminalEmulator terminalEmulator = this.mEmulator;
                if (terminalEmulator != null) {
                    boolean z = !this.mCursorVisible;
                    this.mCursorVisible = z;
                    terminalEmulator.setCursorBlinkState(z);
                    TerminalView.this.invalidate();
                }
            } finally {
                TerminalView.this.mTerminalCursorBlinkerHandler.postDelayed(this, this.mBlinkRate);
            }
        }
    }

    TextSelectionCursorController getTextSelectionCursorController() {
        if (this.mTextSelectionCursorController == null) {
            this.mTextSelectionCursorController = new TextSelectionCursorController(this);
            ViewTreeObserver observer = getViewTreeObserver();
            if (observer != null) {
                observer.addOnTouchModeChangeListener(this.mTextSelectionCursorController);
            }
        }
        return this.mTextSelectionCursorController;
    }

    private void showTextSelectionCursors(MotionEvent event) {
        getTextSelectionCursorController().show(event);
    }

    private boolean hideTextSelectionCursors() {
        return getTextSelectionCursorController().hide();
    }

    private void renderTextSelection() {
        TextSelectionCursorController textSelectionCursorController = this.mTextSelectionCursorController;
        if (textSelectionCursorController != null) {
            textSelectionCursorController.render();
        }
    }

    public boolean isSelectingText() {
        TextSelectionCursorController textSelectionCursorController = this.mTextSelectionCursorController;
        if (textSelectionCursorController != null) {
            return textSelectionCursorController.isActive();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ActionMode getTextSelectionActionMode() {
        TextSelectionCursorController textSelectionCursorController = this.mTextSelectionCursorController;
        if (textSelectionCursorController != null) {
            return textSelectionCursorController.getActionMode();
        }
        return null;
    }

    public void startTextSelectionMode(MotionEvent event) {
        if (!requestFocus()) {
            return;
        }
        showTextSelectionCursors(event);
        this.mClient.copyModeChanged(isSelectingText());
        invalidate();
    }

    public void stopTextSelectionMode() {
        if (hideTextSelectionCursors()) {
            this.mClient.copyModeChanged(isSelectingText());
            invalidate();
        }
    }

    private void decrementYTextSelectionCursors(int decrement) {
        TextSelectionCursorController textSelectionCursorController = this.mTextSelectionCursorController;
        if (textSelectionCursorController != null) {
            textSelectionCursorController.decrementYTextSelectionCursors(decrement);
        }
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mTextSelectionCursorController != null) {
            getViewTreeObserver().addOnTouchModeChangeListener(this.mTextSelectionCursorController);
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mTextSelectionCursorController != null) {
            stopTextSelectionMode();
            getViewTreeObserver().removeOnTouchModeChangeListener(this.mTextSelectionCursorController);
            this.mTextSelectionCursorController.onDetached();
        }
    }

    private void showFloatingToolbar() {
        if (getTextSelectionActionMode() != null) {
            int delay = ViewConfiguration.getDoubleTapTimeout();
            postDelayed(this.mShowFloatingToolbar, delay);
        }
    }

    void hideFloatingToolbar() {
        if (getTextSelectionActionMode() != null) {
            removeCallbacks(this.mShowFloatingToolbar);
            getTextSelectionActionMode().hide(-1L);
        }
    }

    public void updateFloatingToolbarVisibility(MotionEvent event) {
        if (getTextSelectionActionMode() != null) {
            switch (event.getActionMasked()) {
                case 1:
                case 3:
                    showFloatingToolbar();
                    break;
                case 2:
                    hideFloatingToolbar();
                    break;
            }
        }
    }
}
