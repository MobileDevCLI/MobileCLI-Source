package com.termux.terminal;

import androidx.core.app.FrameMetricsAggregator;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public final class TextStyle {
    public static final int CHARACTER_ATTRIBUTE_BLINK = 8;
    public static final int CHARACTER_ATTRIBUTE_BOLD = 1;
    public static final int CHARACTER_ATTRIBUTE_DIM = 256;
    public static final int CHARACTER_ATTRIBUTE_INVERSE = 16;
    public static final int CHARACTER_ATTRIBUTE_INVISIBLE = 32;
    public static final int CHARACTER_ATTRIBUTE_ITALIC = 2;
    public static final int CHARACTER_ATTRIBUTE_PROTECTED = 128;
    public static final int CHARACTER_ATTRIBUTE_STRIKETHROUGH = 64;
    private static final int CHARACTER_ATTRIBUTE_TRUECOLOR_BACKGROUND = 1024;
    private static final int CHARACTER_ATTRIBUTE_TRUECOLOR_FOREGROUND = 512;
    public static final int CHARACTER_ATTRIBUTE_UNDERLINE = 4;
    public static final int COLOR_INDEX_BACKGROUND = 257;
    public static final int COLOR_INDEX_CURSOR = 258;
    public static final int COLOR_INDEX_FOREGROUND = 256;
    static final long NORMAL = encode(256, 257, 0);
    public static final int NUM_INDEXED_COLORS = 259;

    static long encode(int foreColor, int backColor, int effect) {
        long result;
        long result2 = effect & FrameMetricsAggregator.EVERY_DURATION;
        if ((foreColor & ViewCompat.MEASURED_STATE_MASK) == -16777216) {
            result = result2 | ((foreColor & 16777215) << 40) | 512;
        } else {
            result = result2 | ((foreColor & 511) << 40);
        }
        if ((backColor & ViewCompat.MEASURED_STATE_MASK) == -16777216) {
            return result | ((backColor & 16777215) << 16) | 1024;
        }
        return result | ((backColor & 511) << 16);
    }

    public static int decodeForeColor(long style) {
        if ((512 & style) == 0) {
            return (int) ((style >>> 40) & 511);
        }
        return ((int) ((style >>> 40) & 16777215)) | ViewCompat.MEASURED_STATE_MASK;
    }

    public static int decodeBackColor(long style) {
        if ((1024 & style) == 0) {
            return (int) ((style >>> 16) & 511);
        }
        return ((int) ((style >>> 16) & 16777215)) | ViewCompat.MEASURED_STATE_MASK;
    }

    public static int decodeEffect(long style) {
        return (int) (2047 & style);
    }
}
