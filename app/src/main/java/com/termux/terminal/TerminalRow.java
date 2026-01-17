package com.termux.terminal;

import java.util.Arrays;

/* loaded from: classes.dex */
public final class TerminalRow {
    private static final float SPARE_CAPACITY_FACTOR = 1.5f;
    private final int mColumns;
    boolean mHasNonOneWidthOrSurrogateChars;
    boolean mLineWrap;
    private short mSpaceUsed;
    final long[] mStyle;
    public char[] mText;

    public TerminalRow(int columns, long style) {
        this.mColumns = columns;
        this.mText = new char[(int) (columns * SPARE_CAPACITY_FACTOR)];
        this.mStyle = new long[columns];
        clear(style);
    }

    public void copyInterval(TerminalRow line, int sourceX1, int sourceX2, int destinationX) {
        int codePoint;
        this.mHasNonOneWidthOrSurrogateChars |= line.mHasNonOneWidthOrSurrogateChars;
        int x1 = line.findStartOfColumn(sourceX1);
        int x2 = line.findStartOfColumn(sourceX2);
        boolean startingFromSecondHalfOfWideChar = sourceX1 > 0 && line.wideDisplayCharacterStartingAt(sourceX1 + (-1));
        char[] sourceChars = line.mText;
        if (this == line) {
            sourceChars = Arrays.copyOf(sourceChars, sourceChars.length);
        }
        int latestNonCombiningWidth = 0;
        int i = x1;
        int destinationX2 = destinationX;
        boolean startingFromSecondHalfOfWideChar2 = startingFromSecondHalfOfWideChar;
        int sourceX12 = sourceX1;
        while (i < x2) {
            char sourceChar = sourceChars[i];
            if (Character.isHighSurrogate(sourceChar)) {
                i++;
                codePoint = Character.toCodePoint(sourceChar, sourceChars[i]);
            } else {
                codePoint = sourceChar;
            }
            if (startingFromSecondHalfOfWideChar2) {
                codePoint = 32;
                startingFromSecondHalfOfWideChar2 = false;
            }
            int w = WcWidth.width(codePoint);
            if (w > 0) {
                destinationX2 += latestNonCombiningWidth;
                sourceX12 += latestNonCombiningWidth;
                latestNonCombiningWidth = w;
            }
            setChar(destinationX2, codePoint, line.getStyle(sourceX12));
            i++;
            x1 = x1;
        }
    }

    public int getSpaceUsed() {
        return this.mSpaceUsed;
    }

    public int findStartOfColumn(int column) {
        int newCharIndex;
        int newCharIndex2;
        if (column == this.mColumns) {
            return getSpaceUsed();
        }
        int currentColumn = 0;
        int currentCharIndex = 0;
        while (true) {
            int newCharIndex3 = currentCharIndex;
            int newCharIndex4 = newCharIndex3 + 1;
            char c = this.mText[newCharIndex3];
            boolean isHigh = Character.isHighSurrogate(c);
            if (isHigh) {
                newCharIndex = newCharIndex4 + 1;
                newCharIndex2 = Character.toCodePoint(c, this.mText[newCharIndex4]);
            } else {
                newCharIndex = newCharIndex4;
                newCharIndex2 = c;
            }
            int wcwidth = WcWidth.width(newCharIndex2);
            if (wcwidth > 0) {
                currentColumn += wcwidth;
                if (currentColumn == column) {
                    while (newCharIndex < this.mSpaceUsed) {
                        if (Character.isHighSurrogate(this.mText[newCharIndex])) {
                            char[] cArr = this.mText;
                            if (WcWidth.width(Character.toCodePoint(cArr[newCharIndex], cArr[newCharIndex + 1])) > 0) {
                                break;
                            }
                            newCharIndex += 2;
                        } else {
                            if (WcWidth.width(this.mText[newCharIndex]) > 0) {
                                break;
                            }
                            newCharIndex++;
                        }
                    }
                    return newCharIndex;
                }
                if (currentColumn > column) {
                    return currentCharIndex;
                }
            }
            currentCharIndex = newCharIndex;
        }
    }

    private boolean wideDisplayCharacterStartingAt(int column) {
        int codePoint;
        int currentCharIndex = 0;
        int currentColumn = 0;
        while (currentCharIndex < this.mSpaceUsed) {
            int currentCharIndex2 = currentCharIndex + 1;
            char c = this.mText[currentCharIndex];
            if (Character.isHighSurrogate(c)) {
                codePoint = Character.toCodePoint(c, this.mText[currentCharIndex2]);
                currentCharIndex2++;
            } else {
                codePoint = c;
            }
            int wcwidth = WcWidth.width(codePoint);
            if (wcwidth > 0) {
                if (currentColumn == column && wcwidth == 2) {
                    return true;
                }
                currentColumn += wcwidth;
                if (currentColumn > column) {
                    return false;
                }
            }
            currentCharIndex = currentCharIndex2;
        }
        return false;
    }

    public void clear(long style) {
        Arrays.fill(this.mText, ' ');
        Arrays.fill(this.mStyle, style);
        this.mSpaceUsed = (short) this.mColumns;
        this.mHasNonOneWidthOrSurrogateChars = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setChar(int r19, int r20, long r21) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.terminal.TerminalRow.setChar(int, int, long):void");
    }

    boolean isBlank() {
        int charLen = getSpaceUsed();
        for (int charIndex = 0; charIndex < charLen; charIndex++) {
            if (this.mText[charIndex] != ' ') {
                return false;
            }
        }
        return true;
    }

    public final long getStyle(int column) {
        return this.mStyle[column];
    }
}
