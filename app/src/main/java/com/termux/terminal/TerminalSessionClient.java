package com.termux.terminal;

/* loaded from: classes.dex */
public interface TerminalSessionClient {
    Integer getTerminalCursorStyle();

    void logDebug(String tag, String message);

    void logError(String tag, String message);

    void logInfo(String tag, String message);

    void logStackTrace(String tag, Exception e);

    void logStackTraceWithMessage(String tag, String message, Exception e);

    void logVerbose(String tag, String message);

    void logWarn(String tag, String message);

    void onBell(TerminalSession session);

    void onColorsChanged(TerminalSession session);

    void onCopyTextToClipboard(TerminalSession session, String text);

    void onPasteTextFromClipboard(TerminalSession session);

    void onSessionFinished(TerminalSession finishedSession);

    void onTerminalCursorStateChange(boolean state);

    void onTextChanged(TerminalSession changedSession);

    void onTitleChanged(TerminalSession changedSession);
}
