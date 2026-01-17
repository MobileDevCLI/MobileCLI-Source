package com.termux.view;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.termux.terminal.TerminalSession;

/* loaded from: classes.dex */
public interface TerminalViewClient {
    void copyModeChanged(boolean copyMode);

    boolean isTerminalViewSelected();

    void logDebug(String tag, String message);

    void logError(String tag, String message);

    void logInfo(String tag, String message);

    void logStackTrace(String tag, Exception e);

    void logStackTraceWithMessage(String tag, String message, Exception e);

    void logVerbose(String tag, String message);

    void logWarn(String tag, String message);

    boolean onCodePoint(int codePoint, boolean ctrlDown, TerminalSession session);

    void onEmulatorSet();

    boolean onKeyDown(int keyCode, KeyEvent e, TerminalSession session);

    boolean onKeyUp(int keyCode, KeyEvent e);

    boolean onLongPress(MotionEvent event);

    float onScale(float scale);

    void onSingleTapUp(MotionEvent e);

    boolean readAltKey();

    boolean readControlKey();

    boolean readFnKey();

    boolean readShiftKey();

    boolean shouldBackButtonBeMappedToEscape();

    boolean shouldEnforceCharBasedInput();

    boolean shouldUseCtrlSpaceWorkaround();
}
