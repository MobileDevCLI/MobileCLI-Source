package com.termux.terminal;

import java.nio.charset.StandardCharsets;

/* loaded from: classes.dex */
public abstract class TerminalOutput {
    public abstract void onBell();

    public abstract void onColorsChanged();

    public abstract void onCopyTextToClipboard(String text);

    public abstract void onPasteTextFromClipboard();

    public abstract void titleChanged(String oldTitle, String newTitle);

    public abstract void write(byte[] data, int offset, int count);

    public final void write(String data) {
        if (data == null) {
            return;
        }
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        write(bytes, 0, bytes.length);
    }
}
