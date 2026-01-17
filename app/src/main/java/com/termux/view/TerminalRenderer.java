package com.termux.view;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import androidx.core.view.ViewCompat;
import com.termux.terminal.TextStyle;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* loaded from: classes.dex */
public final class TerminalRenderer {
    private final float[] asciiMeasures;
    private final int mFontAscent;
    final int mFontLineSpacing;
    final int mFontLineSpacingAndAscent;
    final float mFontWidth;
    private final Paint mTextPaint;
    final int mTextSize;
    final Typeface mTypeface;

    public TerminalRenderer(int textSize, Typeface typeface) {
        Paint paint = new Paint();
        this.mTextPaint = paint;
        this.asciiMeasures = new float[WorkQueueKt.MASK];
        this.mTextSize = textSize;
        this.mTypeface = typeface;
        paint.setTypeface(typeface);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        int iCeil = (int) Math.ceil(paint.getFontSpacing());
        this.mFontLineSpacing = iCeil;
        int iCeil2 = (int) Math.ceil(paint.ascent());
        this.mFontAscent = iCeil2;
        this.mFontLineSpacingAndAscent = iCeil + iCeil2;
        this.mFontWidth = paint.measureText("X");
        StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < this.asciiMeasures.length; i++) {
            sb.setCharAt(0, (char) i);
            this.asciiMeasures[i] = this.mTextPaint.measureText(sb, 0, 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:114:0x0210 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void render(com.termux.terminal.TerminalEmulator r61, android.graphics.Canvas r62, int r63, int r64, int r65, int r66, int r67) {
        /*
            Method dump skipped, instructions count: 680
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.view.TerminalRenderer.render(com.termux.terminal.TerminalEmulator, android.graphics.Canvas, int, int, int, int, int):void");
    }

    private void drawTextRun(Canvas canvas, char[] text, int[] palette, float y, int startColumn, int runWidthColumns, int startCharIndex, int runWidthChars, float mes, int cursor, int cursorStyle, long textStyle, boolean reverseVideo) {
        int foreColor;
        int backColor;
        boolean savedMatrix;
        float left;
        int foreColor2;
        float right;
        int i;
        int foreColor3;
        float cursorHeight;
        int foreColor4 = TextStyle.decodeForeColor(textStyle);
        int effect = TextStyle.decodeEffect(textStyle);
        int backColor2 = TextStyle.decodeBackColor(textStyle);
        boolean bold = (effect & 9) != 0;
        boolean underline = (effect & 4) != 0;
        boolean italic = (effect & 2) != 0;
        boolean strikeThrough = (effect & 64) != 0;
        boolean dim = (effect & 256) != 0;
        if ((foreColor4 & ViewCompat.MEASURED_STATE_MASK) != -16777216) {
            if (bold && foreColor4 >= 0 && foreColor4 < 8) {
                foreColor4 += 8;
            }
            foreColor4 = palette[foreColor4];
        }
        if ((backColor2 & ViewCompat.MEASURED_STATE_MASK) != -16777216) {
            backColor2 = palette[backColor2];
        }
        boolean reverseVideoHere = reverseVideo ^ ((effect & 16) != 0);
        if (!reverseVideoHere) {
            foreColor = foreColor4;
            backColor = backColor2;
        } else {
            int tmp = foreColor4;
            foreColor = backColor2;
            backColor = tmp;
        }
        float f = this.mFontWidth;
        float left2 = startColumn * f;
        float right2 = (runWidthColumns * f) + left2;
        float mes2 = mes / f;
        if (Math.abs(mes2 - runWidthColumns) <= 0.01d) {
            savedMatrix = false;
            left = left2;
        } else {
            canvas.save();
            canvas.scale(runWidthColumns / mes2, 1.0f);
            right2 *= mes2 / runWidthColumns;
            left = left2 * (mes2 / runWidthColumns);
            savedMatrix = true;
        }
        if (backColor != palette[257]) {
            this.mTextPaint.setColor(backColor);
            Paint paint = this.mTextPaint;
            foreColor2 = foreColor;
            right = right2;
            i = ViewCompat.MEASURED_STATE_MASK;
            canvas.drawRect(left, (y - this.mFontLineSpacingAndAscent) + this.mFontAscent, right2, y, paint);
        } else {
            foreColor2 = foreColor;
            right = right2;
            i = ViewCompat.MEASURED_STATE_MASK;
        }
        if (cursor != 0) {
            this.mTextPaint.setColor(cursor);
            float cursorHeight2 = this.mFontLineSpacingAndAscent - this.mFontAscent;
            if (cursorStyle == 1) {
                cursorHeight = (float) (cursorHeight2 / 4.0d);
            } else if (cursorStyle == 2) {
                cursorHeight = cursorHeight2;
                right = (float) (right - (((right - left) * 3.0f) / 4.0d));
            } else {
                cursorHeight = cursorHeight2;
            }
            canvas.drawRect(left, y - cursorHeight, right, y, this.mTextPaint);
        }
        if ((effect & 32) == 0) {
            if (!dim) {
                foreColor3 = foreColor2;
            } else {
                int red = (foreColor2 >> 16) & 255;
                int green = (foreColor2 >> 8) & 255;
                int blue = foreColor2 & 255;
                int red2 = (red * 2) / 3;
                int red3 = green * 2;
                int green2 = red3 / 3;
                int green3 = blue * 2;
                int blue2 = green3 / 3;
                int blue3 = red2 << 16;
                foreColor3 = blue3 + i + (green2 << 8) + blue2;
            }
            this.mTextPaint.setFakeBoldText(bold);
            this.mTextPaint.setUnderlineText(underline);
            this.mTextPaint.setTextSkewX(italic ? -0.35f : 0.0f);
            this.mTextPaint.setStrikeThruText(strikeThrough);
            this.mTextPaint.setColor(foreColor3);
            canvas.drawText(text, startCharIndex, runWidthChars, left, y - this.mFontLineSpacingAndAscent, this.mTextPaint);
        }
        if (savedMatrix) {
            canvas.restore();
        }
    }

    public float getFontWidth() {
        return this.mFontWidth;
    }

    public int getFontLineSpacing() {
        return this.mFontLineSpacing;
    }
}
