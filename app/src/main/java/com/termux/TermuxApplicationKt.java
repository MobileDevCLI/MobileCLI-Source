package com.termux;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;

/* compiled from: TermuxApplication.kt */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000e\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0002"}, d2 = {"DEFAULT_TERMUX_PROPERTIES", "", "app_devDebug"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class TermuxApplicationKt {
    private static final String DEFAULT_TERMUX_PROPERTIES = "\n# MobileCLI Terminal Properties\n# Documentation: https://wiki.termux.com/wiki/Terminal_Settings\n\n### Keyboard Settings ###\n\n# Extra keys configuration (JSON format)\n# Default keys: ESC, CTRL, ALT, TAB, -, /, |, HOME, UP, END, PGUP\n# extra-keys = [['ESC','/','-','HOME','UP','END','PGUP'],['TAB','CTRL','ALT','LEFT','DOWN','RIGHT','PGDN']]\n\n# Back key behavior: \"back\" (default) or \"escape\"\n# back-key = back\n\n### Appearance ###\n\n# Use fullscreen mode\n# fullscreen = false\n\n# Hide soft keyboard on startup\n# hide-soft-keyboard-on-startup = false\n\n### Terminal Settings ###\n\n# Terminal transcript rows (scrollback buffer)\n# terminal-transcript-rows = 2000\n\n# Cursor blink rate in ms (0 = no blink)\n# terminal-cursor-blink-rate = 500\n\n# Cursor style: \"block\", \"underline\", or \"bar\"\n# terminal-cursor-style = block\n\n### Bell Settings ###\n\n# Bell character behavior: \"vibrate\", \"beep\", \"ignore\"\n# bell-character = vibrate\n\n### URL/External App Settings ###\n\n# Allow external apps to open URLs and files\n# This must be true for Claude Code OAuth to work!\nallow-external-apps = true\n\n### Session Settings ###\n\n# Default working directory\n# default-working-directory = /data/data/com.termux/files/home\n\n";
}
