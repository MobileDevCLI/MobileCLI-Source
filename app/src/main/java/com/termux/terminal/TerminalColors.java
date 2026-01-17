package com.termux.terminal;

import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public final class TerminalColors {
    public static final TerminalColorScheme COLOR_SCHEME = new TerminalColorScheme();
    public final int[] mCurrentColors = new int[TextStyle.NUM_INDEXED_COLORS];

    public TerminalColors() {
        reset();
    }

    public void reset(int index) {
        this.mCurrentColors[index] = COLOR_SCHEME.mDefaultColors[index];
    }

    public void reset() {
        System.arraycopy(COLOR_SCHEME.mDefaultColors, 0, this.mCurrentColors, 0, TextStyle.NUM_INDEXED_COLORS);
    }

    static int parse(String c) {
        int skipInitial;
        int skipBetween;
        try {
            if (c.charAt(0) != '#') {
                if (!c.startsWith("rgb:")) {
                    return 0;
                }
                skipInitial = 4;
                skipBetween = 1;
            } else {
                skipInitial = 1;
                skipBetween = 0;
            }
            int charsForColors = (c.length() - skipInitial) - (skipBetween * 2);
            if (charsForColors % 3 != 0) {
                return 0;
            }
            int componentLength = charsForColors / 3;
            double mult = 255.0d / (Math.pow(2.0d, componentLength * 4) - 1.0d);
            int currentPosition = skipInitial;
            String rString = c.substring(currentPosition, currentPosition + componentLength);
            int currentPosition2 = currentPosition + componentLength + skipBetween;
            String gString = c.substring(currentPosition2, currentPosition2 + componentLength);
            int currentPosition3 = currentPosition2 + componentLength + skipBetween;
            String bString = c.substring(currentPosition3, currentPosition3 + componentLength);
            int r = (int) (Integer.parseInt(rString, 16) * mult);
            int g = (int) (Integer.parseInt(gString, 16) * mult);
            int b = (int) (Integer.parseInt(bString, 16) * mult);
            return (r << 16) | ViewCompat.MEASURED_STATE_MASK | (g << 8) | b;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return 0;
        }
    }

    public void tryParseColor(int intoIndex, String textParameter) {
        int c = parse(textParameter);
        if (c != 0) {
            this.mCurrentColors[intoIndex] = c;
        }
    }
}
