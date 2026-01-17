package com.termux.terminal;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.location.LocationRequestCompat;
import androidx.core.text.HtmlCompat;
import androidx.core.view.PointerIconCompat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;
import kotlin.jvm.internal.ByteCompanionObject;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* loaded from: classes.dex */
public final class TerminalEmulator {
    private static final int DECSET_BIT_APPLICATION_CURSOR_KEYS = 1;
    private static final int DECSET_BIT_APPLICATION_KEYPAD = 32;
    private static final int DECSET_BIT_AUTOWRAP = 8;
    private static final int DECSET_BIT_BRACKETED_PASTE_MODE = 1024;
    private static final int DECSET_BIT_CURSOR_ENABLED = 16;
    private static final int DECSET_BIT_LEFTRIGHT_MARGIN_MODE = 2048;
    private static final int DECSET_BIT_MOUSE_PROTOCOL_SGR = 512;
    private static final int DECSET_BIT_MOUSE_TRACKING_BUTTON_EVENT = 128;
    private static final int DECSET_BIT_MOUSE_TRACKING_PRESS_RELEASE = 64;
    private static final int DECSET_BIT_ORIGIN_MODE = 4;
    private static final int DECSET_BIT_RECTANGULAR_CHANGEATTRIBUTE = 4096;
    private static final int DECSET_BIT_REVERSE_VIDEO = 2;
    private static final int DECSET_BIT_SEND_FOCUS_EVENTS = 256;
    public static final int DEFAULT_TERMINAL_CURSOR_STYLE = 0;
    public static final int DEFAULT_TERMINAL_TRANSCRIPT_ROWS = 2000;
    private static final int ESC = 1;
    private static final int ESC_CSI = 6;
    private static final int ESC_CSI_ARGS_ASTERIX = 16;
    private static final int ESC_CSI_ARGS_SPACE = 15;
    private static final int ESC_CSI_BIGGERTHAN = 12;
    private static final int ESC_CSI_DOLLAR = 8;
    private static final int ESC_CSI_DOUBLE_QUOTE = 17;
    private static final int ESC_CSI_EXCLAMATION = 19;
    private static final int ESC_CSI_QUESTIONMARK = 7;
    private static final int ESC_CSI_QUESTIONMARK_ARG_DOLLAR = 14;
    private static final int ESC_CSI_SINGLE_QUOTE = 18;
    private static final int ESC_NONE = 0;
    private static final int ESC_OSC = 10;
    private static final int ESC_OSC_ESC = 11;
    private static final int ESC_P = 13;
    private static final int ESC_PERCENT = 9;
    private static final int ESC_POUND = 2;
    private static final int ESC_SELECT_LEFT_PAREN = 3;
    private static final int ESC_SELECT_RIGHT_PAREN = 4;
    private static final boolean LOG_ESCAPE_SEQUENCES = false;
    private static final String LOG_TAG = "TerminalEmulator";
    private static final int MAX_ESCAPE_PARAMETERS = 16;
    private static final int MAX_OSC_STRING_LENGTH = 8192;
    public static final int MOUSE_LEFT_BUTTON = 0;
    public static final int MOUSE_LEFT_BUTTON_MOVED = 32;
    public static final int MOUSE_WHEELDOWN_BUTTON = 65;
    public static final int MOUSE_WHEELUP_BUTTON = 64;
    public static final Integer[] TERMINAL_CURSOR_STYLES_LIST = {0, 1, 2};
    public static final int TERMINAL_CURSOR_STYLE_BAR = 2;
    public static final int TERMINAL_CURSOR_STYLE_BLOCK = 0;
    public static final int TERMINAL_CURSOR_STYLE_UNDERLINE = 1;
    public static final int TERMINAL_TRANSCRIPT_ROWS_MAX = 50000;
    public static final int TERMINAL_TRANSCRIPT_ROWS_MIN = 100;
    public static final int UNICODE_REPLACEMENT_CHAR = 65533;
    private boolean mAboutToAutoWrap;
    final TerminalBuffer mAltBuffer;
    private int mArgIndex;
    int mBackColor;
    private int mBottomMargin;
    TerminalSessionClient mClient;
    public int mColumns;
    private boolean mContinueSequence;
    private int mCurrentDecSetFlags;
    private boolean mCursorBlinkState;
    private boolean mCursorBlinkingEnabled;
    private int mCursorCol;
    private int mCursorRow;
    private int mEffect;
    private int mEscapeState;
    int mForeColor;
    private boolean mInsertMode;
    private int mLeftMargin;
    private final TerminalBuffer mMainBuffer;
    private int mRightMargin;
    public int mRows;
    private int mSavedDecSetFlags;
    private TerminalBuffer mScreen;
    private final TerminalOutput mSession;
    private boolean[] mTabStop;
    private String mTitle;
    private int mTopMargin;
    private boolean mUseLineDrawingG0;
    private boolean mUseLineDrawingG1;
    private byte mUtf8Index;
    private byte mUtf8ToFollow;
    private final Stack<String> mTitleStack = new Stack<>();
    private int mCursorStyle = 0;
    private final int[] mArgs = new int[16];
    private final StringBuilder mOSCOrDeviceControlArgs = new StringBuilder();
    private final SavedScreenState mSavedStateMain = new SavedScreenState();
    private final SavedScreenState mSavedStateAlt = new SavedScreenState();
    private boolean mUseLineDrawingUsesG0 = true;
    private int mScrollCounter = 0;
    private final byte[] mUtf8InputBuffer = new byte[4];
    private int mLastEmittedCodePoint = -1;
    public final TerminalColors mColors = new TerminalColors();

    private boolean isDecsetInternalBitSet(int bit) {
        return (this.mCurrentDecSetFlags & bit) != 0;
    }

    private void setDecsetinternalBit(int internalBit, boolean set) {
        if (set) {
            if (internalBit == 64) {
                setDecsetinternalBit(128, false);
            } else if (internalBit == 128) {
                setDecsetinternalBit(64, false);
            }
        }
        if (set) {
            this.mCurrentDecSetFlags |= internalBit;
        } else {
            this.mCurrentDecSetFlags &= ~internalBit;
        }
    }

    static int mapDecSetBitToInternalBit(int decsetBit) {
        switch (decsetBit) {
            case 1:
                return 1;
            case 5:
                return 2;
            case 6:
                return 4;
            case 7:
                return 8;
            case 25:
                return 16;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                return 32;
            case 69:
                return 2048;
            case 1000:
                return 64;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                return 128;
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                return 256;
            case PointerIconCompat.TYPE_CELL /* 1006 */:
                return 512;
            case 2004:
                return 1024;
            default:
                return -1;
        }
    }

    public TerminalEmulator(TerminalOutput session, int columns, int rows, Integer transcriptRows, TerminalSessionClient client) {
        this.mSession = session;
        TerminalBuffer terminalBuffer = new TerminalBuffer(columns, getTerminalTranscriptRows(transcriptRows), rows);
        this.mMainBuffer = terminalBuffer;
        this.mScreen = terminalBuffer;
        this.mAltBuffer = new TerminalBuffer(columns, rows, rows);
        this.mClient = client;
        this.mRows = rows;
        this.mColumns = columns;
        this.mTabStop = new boolean[columns];
        reset();
    }

    public void updateTerminalSessionClient(TerminalSessionClient client) {
        this.mClient = client;
        setCursorStyle();
        setCursorBlinkState(true);
    }

    public TerminalBuffer getScreen() {
        return this.mScreen;
    }

    public boolean isAlternateBufferActive() {
        return this.mScreen == this.mAltBuffer;
    }

    private int getTerminalTranscriptRows(Integer transcriptRows) {
        if (transcriptRows == null || transcriptRows.intValue() < 100 || transcriptRows.intValue() > 50000) {
            return 2000;
        }
        return transcriptRows.intValue();
    }

    public void sendMouseEvent(int mouseButton, int column, int row, boolean pressed) {
        if (column < 1) {
            column = 1;
        }
        if (column > this.mColumns) {
            column = this.mColumns;
        }
        if (row < 1) {
            row = 1;
        }
        if (row > this.mRows) {
            row = this.mRows;
        }
        if (mouseButton != 32 || isDecsetInternalBitSet(128)) {
            if (isDecsetInternalBitSet(512)) {
                this.mSession.write(String.format("\u001b[<%d;%d;%d" + (pressed ? 'M' : 'm'), Integer.valueOf(mouseButton), Integer.valueOf(column), Integer.valueOf(row)));
                return;
            }
            int mouseButton2 = pressed ? mouseButton : 3;
            boolean out_of_bounds = column > 223 || row > 223;
            if (!out_of_bounds) {
                byte[] data = {27, 91, 77, (byte) (mouseButton2 + 32), (byte) (column + 32), (byte) (row + 32)};
                this.mSession.write(data, 0, data.length);
            }
        }
    }

    public void resize(int columns, int rows) {
        int i = this.mRows;
        if (i == rows && this.mColumns == columns) {
            return;
        }
        if (columns < 2 || rows < 2) {
            throw new IllegalArgumentException("rows=" + rows + ", columns=" + columns);
        }
        if (i != rows) {
            this.mRows = rows;
            this.mTopMargin = 0;
            this.mBottomMargin = rows;
        }
        if (this.mColumns != columns) {
            int oldColumns = this.mColumns;
            this.mColumns = columns;
            boolean[] oldTabStop = this.mTabStop;
            this.mTabStop = new boolean[columns];
            setDefaultTabStops();
            int toTransfer = Math.min(oldColumns, columns);
            System.arraycopy(oldTabStop, 0, this.mTabStop, 0, toTransfer);
            this.mLeftMargin = 0;
            this.mRightMargin = this.mColumns;
        }
        resizeScreen();
    }

    private void resizeScreen() {
        int[] cursor = {this.mCursorCol, this.mCursorRow};
        int newTotalRows = this.mScreen == this.mAltBuffer ? this.mRows : this.mMainBuffer.mTotalRows;
        this.mScreen.resize(this.mColumns, this.mRows, newTotalRows, cursor, getStyle(), isAlternateBufferActive());
        this.mCursorCol = cursor[0];
        this.mCursorRow = cursor[1];
    }

    public int getCursorRow() {
        return this.mCursorRow;
    }

    public int getCursorCol() {
        return this.mCursorCol;
    }

    public int getCursorStyle() {
        return this.mCursorStyle;
    }

    public void setCursorStyle() {
        Integer cursorStyle = null;
        TerminalSessionClient terminalSessionClient = this.mClient;
        if (terminalSessionClient != null) {
            cursorStyle = terminalSessionClient.getTerminalCursorStyle();
        }
        if (cursorStyle == null || !Arrays.asList(TERMINAL_CURSOR_STYLES_LIST).contains(cursorStyle)) {
            this.mCursorStyle = 0;
        } else {
            this.mCursorStyle = cursorStyle.intValue();
        }
    }

    public boolean isReverseVideo() {
        return isDecsetInternalBitSet(2);
    }

    public boolean isCursorEnabled() {
        return isDecsetInternalBitSet(16);
    }

    public boolean shouldCursorBeVisible() {
        if (!isCursorEnabled()) {
            return false;
        }
        if (this.mCursorBlinkingEnabled) {
            return this.mCursorBlinkState;
        }
        return true;
    }

    public void setCursorBlinkingEnabled(boolean cursorBlinkingEnabled) {
        this.mCursorBlinkingEnabled = cursorBlinkingEnabled;
    }

    public void setCursorBlinkState(boolean cursorBlinkState) {
        this.mCursorBlinkState = cursorBlinkState;
    }

    public boolean isKeypadApplicationMode() {
        return isDecsetInternalBitSet(32);
    }

    public boolean isCursorKeysApplicationMode() {
        return isDecsetInternalBitSet(1);
    }

    public boolean isMouseTrackingActive() {
        return isDecsetInternalBitSet(64) || isDecsetInternalBitSet(128);
    }

    private void setDefaultTabStops() {
        int i = 0;
        while (i < this.mColumns) {
            this.mTabStop[i] = (i & 7) == 0 && i != 0;
            i++;
        }
    }

    public void append(byte[] buffer, int length) throws NumberFormatException {
        for (int i = 0; i < length; i++) {
            processByte(buffer[i]);
        }
    }

    private void processByte(byte byteToProcess) throws NumberFormatException {
        byte b;
        byte b2 = this.mUtf8ToFollow;
        if (b2 > 0) {
            if ((byteToProcess & 192) == 128) {
                byte[] bArr = this.mUtf8InputBuffer;
                byte b3 = this.mUtf8Index;
                byte b4 = (byte) (b3 + 1);
                this.mUtf8Index = b4;
                bArr[b3] = byteToProcess;
                byte b5 = (byte) (b2 - 1);
                this.mUtf8ToFollow = b5;
                if (b5 == 0) {
                    byte firstByteMask = (byte) (b4 == 2 ? 31 : b4 == 3 ? 15 : 7);
                    int codePoint = bArr[0] & firstByteMask;
                    int i = 1;
                    while (true) {
                        b = this.mUtf8Index;
                        if (i >= b) {
                            break;
                        }
                        codePoint = (codePoint << 6) | (this.mUtf8InputBuffer[i] & 63);
                        i++;
                    }
                    if ((codePoint <= 127 && b > 1) || ((codePoint < 2047 && b > 2) || (codePoint < 65535 && b > 3))) {
                        codePoint = UNICODE_REPLACEMENT_CHAR;
                    }
                    this.mUtf8ToFollow = (byte) 0;
                    this.mUtf8Index = (byte) 0;
                    if (codePoint < 128 || codePoint > 159) {
                        switch (Character.getType(codePoint)) {
                            case 0:
                            case 19:
                                codePoint = UNICODE_REPLACEMENT_CHAR;
                                break;
                        }
                        processCodePoint(codePoint);
                        return;
                    }
                    return;
                }
                return;
            }
            this.mUtf8ToFollow = (byte) 0;
            this.mUtf8Index = (byte) 0;
            emitCodePoint(UNICODE_REPLACEMENT_CHAR);
            processByte(byteToProcess);
            return;
        }
        if ((byteToProcess & ByteCompanionObject.MIN_VALUE) == 0) {
            processCodePoint(byteToProcess);
            return;
        }
        if ((byteToProcess & 224) == 192) {
            this.mUtf8ToFollow = (byte) 1;
        } else if ((byteToProcess & 240) == 224) {
            this.mUtf8ToFollow = (byte) 2;
        } else if ((byteToProcess & 248) == 240) {
            this.mUtf8ToFollow = (byte) 3;
        } else {
            processCodePoint(UNICODE_REPLACEMENT_CHAR);
            return;
        }
        byte[] bArr2 = this.mUtf8InputBuffer;
        byte b6 = this.mUtf8Index;
        this.mUtf8Index = (byte) (b6 + 1);
        bArr2[b6] = byteToProcess;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void processCodePoint(int i) throws NumberFormatException {
        boolean z;
        int i2;
        int arg;
        int i3;
        i = 1;
        int i4 = 1;
        switch (i) {
            case 0:
                break;
            case 7:
                if (this.mEscapeState == 10) {
                    doOsc(i);
                    break;
                } else {
                    this.mSession.onBell();
                    break;
                }
            case 8:
                int i5 = this.mLeftMargin;
                int i6 = this.mCursorCol;
                if (i5 != i6) {
                    setCursorCol(i6 - 1);
                    break;
                } else {
                    int i7 = this.mCursorRow - 1;
                    if (i7 >= 0 && this.mScreen.getLineWrap(i7)) {
                        this.mScreen.clearLineWrap(i7);
                        setCursorRowCol(i7, this.mRightMargin - 1);
                        break;
                    }
                }
                break;
            case 9:
                this.mCursorCol = nextTabStop(1);
                break;
            case 10:
            case 11:
            case 12:
                doLinefeed();
                break;
            case 13:
                setCursorCol(this.mLeftMargin);
                break;
            case 14:
                this.mUseLineDrawingUsesG0 = false;
                break;
            case 15:
                this.mUseLineDrawingUsesG0 = true;
                break;
            case 24:
            case 26:
                if (this.mEscapeState != 0) {
                    this.mEscapeState = 0;
                    emitCodePoint(WorkQueueKt.MASK);
                    break;
                }
                break;
            case 27:
                int i8 = this.mEscapeState;
                if (i8 != 13) {
                    if (i8 != 10) {
                        startEscapeSequence();
                        break;
                    } else {
                        doOsc(i);
                        break;
                    }
                }
                break;
            default:
                this.mContinueSequence = false;
                switch (this.mEscapeState) {
                    case 0:
                        if (i >= 32) {
                            emitCodePoint(i);
                            break;
                        }
                        break;
                    case 1:
                        doEsc(i);
                        break;
                    case 2:
                        doEscPound(i);
                        break;
                    case 3:
                        this.mUseLineDrawingG0 = i == 48;
                        break;
                    case 4:
                        this.mUseLineDrawingG1 = i == 48;
                        break;
                    case 5:
                    default:
                        unknownSequence(i);
                        break;
                    case 6:
                        doCsi(i);
                        break;
                    case 7:
                        doCsiQuestionMark(i);
                        break;
                    case 8:
                        boolean zIsDecsetInternalBitSet = isDecsetInternalBitSet(4);
                        int i9 = zIsDecsetInternalBitSet ? this.mTopMargin : 0;
                        int i10 = zIsDecsetInternalBitSet ? this.mBottomMargin : this.mRows;
                        int i11 = zIsDecsetInternalBitSet ? this.mLeftMargin : 0;
                        int i12 = zIsDecsetInternalBitSet ? this.mRightMargin : this.mColumns;
                        switch (i) {
                            case 114:
                            case 116:
                                boolean z2 = i == 116;
                                int iMin = Math.min(getArg(0, 1, true) - 1, i10) + i9;
                                int iMin2 = Math.min(getArg(1, 1, true) - 1, i12) + i11;
                                int iMin3 = Math.min(getArg(2, this.mRows, true) + 1, i10 - 1) + i9;
                                int iMin4 = Math.min(getArg(3, this.mColumns, true) + 1, i12 - 1) + i11;
                                int i13 = this.mArgIndex;
                                if (i13 < 4) {
                                    break;
                                } else {
                                    int[] iArr = this.mArgs;
                                    if (i13 >= iArr.length) {
                                        this.mArgIndex = iArr.length - 1;
                                    }
                                    int i14 = 4;
                                    while (i14 <= this.mArgIndex) {
                                        int i15 = 0;
                                        boolean z3 = true;
                                        switch (getArg(i14, 0, false)) {
                                            case 0:
                                                i15 = 29;
                                                if (!z2) {
                                                    z3 = false;
                                                    break;
                                                }
                                                break;
                                            case 1:
                                                i15 = 1;
                                                break;
                                            case 4:
                                                i15 = 4;
                                                break;
                                            case 5:
                                                i15 = 8;
                                                break;
                                            case 7:
                                                i15 = 16;
                                                break;
                                            case 22:
                                                i15 = 1;
                                                z3 = false;
                                                break;
                                            case 24:
                                                i15 = 4;
                                                z3 = false;
                                                break;
                                            case 25:
                                                i15 = 8;
                                                z3 = false;
                                                break;
                                            case 27:
                                                i15 = 16;
                                                z3 = false;
                                                break;
                                        }
                                        if (!z2 || z3) {
                                            z = zIsDecsetInternalBitSet;
                                            i2 = i12;
                                            this.mScreen.setOrClearEffect(i15, z3, z2, isDecsetInternalBitSet(4096), i11, i2, iMin, iMin2, iMin3, iMin4);
                                        } else {
                                            z = zIsDecsetInternalBitSet;
                                            i2 = i12;
                                        }
                                        i14++;
                                        i12 = i2;
                                        zIsDecsetInternalBitSet = z;
                                    }
                                    break;
                                }
                            case 115:
                            case 117:
                            case 119:
                            case 121:
                            default:
                                unknownSequence(i);
                                break;
                            case 118:
                                int iMin5 = Math.min((getArg(0, 1, true) - 1) + i9, this.mRows);
                                int iMin6 = Math.min((getArg(1, 1, true) - 1) + i11, this.mColumns);
                                int iMin7 = Math.min(Math.max(getArg(2, this.mRows, true) + i9, iMin5), this.mRows);
                                int iMin8 = Math.min(Math.max(getArg(3, this.mColumns, true) + i11, iMin6), this.mColumns);
                                int iMin9 = Math.min((getArg(5, 1, true) - 1) + i9, this.mRows);
                                int iMin10 = Math.min((getArg(6, 1, true) - 1) + i11, this.mColumns);
                                this.mScreen.blockCopy(iMin6, iMin5, Math.min(this.mColumns - iMin10, iMin8 - iMin6), Math.min(this.mRows - iMin9, iMin7 - iMin5), iMin10, iMin9);
                                break;
                            case 120:
                            case 122:
                            case 123:
                                Object[] objArr = i != 120;
                                Object[] objArr2 = i == 123;
                                Object[] objArr3 = objArr == true && objArr2 == true;
                                int i16 = 0;
                                if (objArr == true) {
                                    arg = 32;
                                } else {
                                    arg = getArg(0, -1, true);
                                    i16 = 0 + 1;
                                }
                                if ((arg >= 32 && arg <= 126) || (arg >= 160 && arg <= 255)) {
                                    int i17 = i16 + 1;
                                    int iMin11 = Math.min(getArg(i16, 1, true) + i9, i10 + 1);
                                    int i18 = i17 + 1;
                                    int iMin12 = Math.min(getArg(i17, 1, true) + i11, i12 + 1);
                                    int iMin13 = Math.min(getArg(i18, this.mRows, true) + i9, i10);
                                    int iMin14 = Math.min(getArg(i18 + 1, this.mColumns, true) + i11, i12);
                                    long style = getStyle();
                                    int i19 = iMin11 - 1;
                                    while (i19 < iMin13) {
                                        int i20 = iMin13;
                                        int i21 = iMin12 - 1;
                                        while (i21 < iMin14) {
                                            if (objArr2 == true) {
                                                i3 = iMin12;
                                                if ((TextStyle.decodeEffect(this.mScreen.getStyleAt(i19, i21)) & 128) == 0) {
                                                }
                                                i21++;
                                                iMin12 = i3;
                                            } else {
                                                i3 = iMin12;
                                            }
                                            TerminalBuffer terminalBuffer = this.mScreen;
                                            terminalBuffer.setChar(i21, i19, arg, objArr3 != false ? terminalBuffer.getStyleAt(i19, i21) : style);
                                            i21++;
                                            iMin12 = i3;
                                        }
                                        i19++;
                                        iMin13 = i20;
                                    }
                                    break;
                                } else {
                                    break;
                                }
                                break;
                        }
                    case 9:
                        break;
                    case 10:
                        doOsc(i);
                        break;
                    case 11:
                        doOscEsc(i);
                        break;
                    case 12:
                        doCsiBiggerThan(i);
                        break;
                    case 13:
                        doDeviceControl(i);
                        break;
                    case 14:
                        if (i == 112) {
                            int arg0 = getArg0(0);
                            if (arg0 == 47 || arg0 == 1047 || arg0 == 1049) {
                                if (this.mScreen != this.mAltBuffer) {
                                    i4 = 2;
                                }
                            } else {
                                int iMapDecSetBitToInternalBit = mapDecSetBitToInternalBit(arg0);
                                if (iMapDecSetBitToInternalBit != -1) {
                                    if (!isDecsetInternalBitSet(iMapDecSetBitToInternalBit)) {
                                        i4 = 2;
                                    }
                                } else {
                                    this.mClient.logError(LOG_TAG, "Got DECRQM for unrecognized private DEC mode=" + arg0);
                                    i4 = 0;
                                }
                            }
                            this.mSession.write(String.format(Locale.US, "\u001b[?%d;%d$y", Integer.valueOf(arg0), Integer.valueOf(i4)));
                            break;
                        } else {
                            unknownSequence(i);
                            break;
                        }
                        break;
                    case 15:
                        int arg02 = getArg0(0);
                        switch (i) {
                            case 113:
                                switch (arg02) {
                                    case 0:
                                    case 1:
                                    case 2:
                                        this.mCursorStyle = 0;
                                        break;
                                    case 3:
                                    case 4:
                                        this.mCursorStyle = 1;
                                        break;
                                    case 5:
                                    case 6:
                                        this.mCursorStyle = 2;
                                        break;
                                }
                            case 114:
                            case 115:
                            default:
                                unknownSequence(i);
                                break;
                            case 116:
                            case 117:
                                break;
                        }
                    case 16:
                        int arg03 = getArg0(0);
                        if (i == 120 && arg03 >= 0 && arg03 <= 2) {
                            setDecsetinternalBit(4096, arg03 == 2);
                            break;
                        } else {
                            unknownSequence(i);
                            break;
                        }
                    case 17:
                        if (i == 113) {
                            int arg04 = getArg0(0);
                            if (arg04 == 0 || arg04 == 2) {
                                this.mEffect &= -129;
                                break;
                            } else if (arg04 == 1) {
                                this.mEffect |= 128;
                                break;
                            } else {
                                unknownSequence(i);
                                break;
                            }
                        } else {
                            unknownSequence(i);
                            break;
                        }
                    case 18:
                        if (i != 125) {
                            if (i == 126) {
                                int i22 = this.mRightMargin - this.mCursorCol;
                                int iMin15 = Math.min(getArg0(1), i22);
                                int i23 = i22 - iMin15;
                                TerminalBuffer terminalBuffer2 = this.mScreen;
                                int i24 = this.mCursorCol;
                                terminalBuffer2.blockCopy(i24 + iMin15, 0, i23, this.mRows, i24, 0);
                                blockClear(this.mCursorRow + i23, 0, iMin15, this.mRows);
                                break;
                            } else {
                                unknownSequence(i);
                                break;
                            }
                        } else {
                            int i25 = this.mRightMargin - this.mCursorCol;
                            int iMin16 = Math.min(getArg0(1), i25);
                            TerminalBuffer terminalBuffer3 = this.mScreen;
                            int i26 = this.mCursorCol;
                            terminalBuffer3.blockCopy(i26, 0, i25 - iMin16, this.mRows, i26 + iMin16, 0);
                            blockClear(this.mCursorCol, 0, iMin16, this.mRows);
                            break;
                        }
                    case 19:
                        if (i == 112) {
                            reset();
                            break;
                        } else {
                            unknownSequence(i);
                            break;
                        }
                }
                if (!this.mContinueSequence) {
                    this.mEscapeState = 0;
                    break;
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void doDeviceControl(int r17) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 560
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.terminal.TerminalEmulator.doDeviceControl(int):void");
    }

    private int nextTabStop(int numTabs) {
        int i = this.mCursorCol;
        while (true) {
            i++;
            if (i < this.mColumns) {
                if (this.mTabStop[i] && numTabs - 1 == 0) {
                    return Math.min(i, this.mRightMargin);
                }
            } else {
                int i2 = this.mRightMargin;
                return i2 - 1;
            }
        }
    }

    private void doCsiQuestionMark(int b) {
        int startCol;
        int startRow;
        int endCol;
        int endRow;
        int row;
        int col;
        switch (b) {
            case 36:
                continueSequence(14);
                break;
            case 74:
            case 75:
                this.mAboutToAutoWrap = false;
                boolean justRow = b == 75;
                switch (getArg0(0)) {
                    case 0:
                        int startCol2 = this.mCursorCol;
                        int startRow2 = this.mCursorRow;
                        int endCol2 = this.mColumns;
                        int endRow2 = justRow ? this.mCursorRow + 1 : this.mRows;
                        startCol = startCol2;
                        startRow = startRow2;
                        endCol = endCol2;
                        endRow = endRow2;
                        break;
                    case 1:
                        int startRow3 = justRow ? this.mCursorRow : 0;
                        int endCol3 = this.mCursorCol + 1;
                        int endRow3 = this.mCursorRow + 1;
                        startCol = 0;
                        startRow = startRow3;
                        endCol = endCol3;
                        endRow = endRow3;
                        break;
                    case 2:
                        int startRow4 = justRow ? this.mCursorRow : 0;
                        int endCol4 = this.mColumns;
                        int endRow4 = justRow ? this.mCursorRow + 1 : this.mRows;
                        startCol = 0;
                        startRow = startRow4;
                        endCol = endCol4;
                        endRow = endRow4;
                        break;
                    default:
                        unknownSequence(b);
                        startCol = -1;
                        startRow = -1;
                        endCol = -1;
                        endRow = -1;
                        break;
                }
                long style = getStyle();
                int row2 = startRow;
                while (row2 < endRow) {
                    int col2 = startCol;
                    while (col2 < endCol) {
                        if ((TextStyle.decodeEffect(this.mScreen.getStyleAt(row2, col2)) & 128) == 0) {
                            row = row2;
                            col = col2;
                            this.mScreen.setChar(col2, row2, 32, style);
                        } else {
                            row = row2;
                            col = col2;
                        }
                        col2 = col + 1;
                        row2 = row;
                    }
                    row2++;
                }
                break;
            case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                int i = this.mArgIndex;
                int[] iArr = this.mArgs;
                if (i >= iArr.length) {
                    this.mArgIndex = iArr.length - 1;
                }
                for (int i2 = 0; i2 <= this.mArgIndex; i2++) {
                    doDecSetOrReset(b == 104, this.mArgs[i2]);
                }
                break;
            case 110:
                switch (getArg0(-1)) {
                    case 6:
                        this.mSession.write(String.format(Locale.US, "\u001b[?%d;%d;1R", Integer.valueOf(this.mCursorRow + 1), Integer.valueOf(this.mCursorCol + 1)));
                        break;
                    default:
                        finishSequence();
                        break;
                }
            case 114:
            case 115:
                int i3 = this.mArgIndex;
                int[] iArr2 = this.mArgs;
                if (i3 >= iArr2.length) {
                    this.mArgIndex = iArr2.length - 1;
                }
                for (int i4 = 0; i4 <= this.mArgIndex; i4++) {
                    int externalBit = this.mArgs[i4];
                    int internalBit = mapDecSetBitToInternalBit(externalBit);
                    if (internalBit == -1) {
                        this.mClient.logWarn(LOG_TAG, "Ignoring request to save/recall decset bit=" + externalBit);
                    } else if (b == 115) {
                        this.mSavedDecSetFlags |= internalBit;
                    } else {
                        doDecSetOrReset((this.mSavedDecSetFlags & internalBit) != 0, externalBit);
                    }
                }
                break;
            default:
                parseArg(b);
                break;
        }
    }

    public void doDecSetOrReset(boolean setting, int externalBit) {
        int internalBit = mapDecSetBitToInternalBit(externalBit);
        if (internalBit != -1) {
            setDecsetinternalBit(internalBit, setting);
        }
        switch (externalBit) {
            case 1:
            case 4:
            case 5:
            case 40:
            case 45:
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
            case 1000:
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
            case PointerIconCompat.TYPE_HAND /* 1002 */:
            case PointerIconCompat.TYPE_HELP /* 1003 */:
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
            case 1005:
            case PointerIconCompat.TYPE_CELL /* 1006 */:
            case PointerIconCompat.TYPE_VERTICAL_DOUBLE_ARROW /* 1015 */:
            case 1034:
            case 2004:
                break;
            case 3:
                this.mTopMargin = 0;
                this.mLeftMargin = 0;
                this.mBottomMargin = this.mRows;
                this.mRightMargin = this.mColumns;
                setDecsetinternalBit(2048, false);
                blockClear(0, 0, this.mColumns, this.mRows);
                setCursorRowCol(0, 0);
                break;
            case 6:
                if (setting) {
                    setCursorPosition(0, 0);
                    break;
                }
                break;
            case 7:
            case 8:
            case 9:
            case 12:
            case 25:
                TerminalSessionClient terminalSessionClient = this.mClient;
                if (terminalSessionClient != null) {
                    terminalSessionClient.onTerminalCursorStateChange(setting);
                    break;
                }
                break;
            case 47:
            case 1047:
            case 1049:
                TerminalBuffer newScreen = setting ? this.mAltBuffer : this.mMainBuffer;
                if (newScreen != this.mScreen) {
                    boolean resized = (newScreen.mColumns == this.mColumns && newScreen.mScreenRows == this.mRows) ? false : true;
                    if (setting) {
                        saveCursor();
                    }
                    this.mScreen = newScreen;
                    if (!setting) {
                        int col = this.mSavedStateMain.mSavedCursorCol;
                        int row = this.mSavedStateMain.mSavedCursorRow;
                        restoreCursor();
                        if (resized) {
                            this.mCursorCol = col;
                            this.mCursorRow = row;
                        }
                    }
                    if (resized) {
                        resizeScreen();
                    }
                    if (newScreen == this.mAltBuffer) {
                        newScreen.blockSet(0, 0, this.mColumns, this.mRows, 32, getStyle());
                        break;
                    }
                }
                break;
            case 69:
                if (!setting) {
                    this.mLeftMargin = 0;
                    this.mRightMargin = this.mColumns;
                    break;
                }
                break;
            case 1048:
                if (setting) {
                    saveCursor();
                    break;
                } else {
                    restoreCursor();
                    break;
                }
            default:
                unknownParameter(externalBit);
                break;
        }
    }

    private void doCsiBiggerThan(int b) {
        switch (b) {
            case 99:
                this.mSession.write("\u001b[>41;320;0c");
                break;
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                this.mClient.logError(LOG_TAG, "(ignored) CSI > MODIFY RESOURCE: " + getArg0(-1) + " to " + getArg1(-1));
                break;
            default:
                parseArg(b);
                break;
        }
    }

    private void startEscapeSequence() {
        this.mEscapeState = 1;
        this.mArgIndex = 0;
        Arrays.fill(this.mArgs, -1);
    }

    private void doLinefeed() {
        int i = this.mCursorRow;
        int i2 = this.mBottomMargin;
        boolean belowScrollingRegion = i >= i2;
        int newCursorRow = i + 1;
        if (belowScrollingRegion) {
            if (i != this.mRows - 1) {
                setCursorRow(newCursorRow);
            }
        } else {
            if (newCursorRow == i2) {
                scrollDownOneLine();
                newCursorRow = this.mBottomMargin - 1;
            }
            setCursorRow(newCursorRow);
        }
    }

    private void continueSequence(int state) {
        this.mEscapeState = state;
        this.mContinueSequence = true;
    }

    private void doEscPound(int b) {
        switch (b) {
            case 56:
                this.mScreen.blockSet(0, 0, this.mColumns, this.mRows, 69, getStyle());
                break;
            default:
                unknownSequence(b);
                break;
        }
    }

    private void doEsc(int b) {
        switch (b) {
            case 35:
                continueSequence(2);
                break;
            case 40:
                continueSequence(3);
                break;
            case 41:
                continueSequence(4);
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
            case 78:
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE /* 54 */:
                int i = this.mCursorCol;
                int i2 = this.mLeftMargin;
                if (i > i2) {
                    this.mCursorCol = i - 1;
                    break;
                } else {
                    int i3 = this.mBottomMargin;
                    int i4 = this.mTopMargin;
                    int rows = i3 - i4;
                    this.mScreen.blockCopy(i2, i4, (this.mRightMargin - i2) - 1, rows, i2 + 1, i4);
                    this.mScreen.blockSet(this.mLeftMargin, this.mTopMargin, 1, rows, 32, TextStyle.encode(this.mForeColor, this.mBackColor, 0));
                    break;
                }
            case ConstraintLayout.LayoutParams.Table.LAYOUT_GONE_MARGIN_BASELINE /* 55 */:
                saveCursor();
                break;
            case 56:
                restoreCursor();
                break;
            case 57:
                int i5 = this.mCursorCol;
                if (i5 < this.mRightMargin - 1) {
                    this.mCursorCol = i5 + 1;
                    break;
                } else {
                    int i6 = this.mBottomMargin;
                    int i7 = this.mTopMargin;
                    int rows2 = i6 - i7;
                    TerminalBuffer terminalBuffer = this.mScreen;
                    int i8 = this.mLeftMargin;
                    terminalBuffer.blockCopy(i8 + 1, i7, (r2 - i8) - 1, rows2, i8, i7);
                    this.mScreen.blockSet(this.mRightMargin - 1, this.mTopMargin, 1, rows2, 32, TextStyle.encode(this.mForeColor, this.mBackColor, 0));
                    break;
                }
            case LockFreeTaskQueueCore.CLOSED_SHIFT /* 61 */:
                setDecsetinternalBit(32, true);
                break;
            case 62:
                setDecsetinternalBit(32, false);
                break;
            case 68:
                doLinefeed();
                break;
            case 69:
                setCursorCol(isDecsetInternalBitSet(4) ? this.mLeftMargin : 0);
                doLinefeed();
                break;
            case 70:
                setCursorRowCol(0, this.mBottomMargin - 1);
                break;
            case 72:
                this.mTabStop[this.mCursorCol] = true;
                break;
            case 77:
                int i9 = this.mCursorRow;
                int i10 = this.mTopMargin;
                if (i9 <= i10) {
                    this.mScreen.blockCopy(0, i10, this.mColumns, this.mBottomMargin - (i10 + 1), 0, i10 + 1);
                    blockClear(0, this.mTopMargin, this.mColumns);
                    break;
                } else {
                    this.mCursorRow = i9 - 1;
                    break;
                }
            case 80:
                this.mOSCOrDeviceControlArgs.setLength(0);
                continueSequence(13);
                break;
            case 91:
                continueSequence(6);
                break;
            case 93:
                this.mOSCOrDeviceControlArgs.setLength(0);
                continueSequence(10);
                break;
            case 99:
                reset();
                this.mMainBuffer.clearTranscript();
                blockClear(0, 0, this.mColumns, this.mRows);
                setCursorPosition(0, 0);
                break;
            default:
                unknownSequence(b);
                break;
        }
    }

    private void saveCursor() {
        SavedScreenState state = this.mScreen == this.mMainBuffer ? this.mSavedStateMain : this.mSavedStateAlt;
        state.mSavedCursorRow = this.mCursorRow;
        state.mSavedCursorCol = this.mCursorCol;
        state.mSavedEffect = this.mEffect;
        state.mSavedForeColor = this.mForeColor;
        state.mSavedBackColor = this.mBackColor;
        state.mSavedDecFlags = this.mCurrentDecSetFlags;
        state.mUseLineDrawingG0 = this.mUseLineDrawingG0;
        state.mUseLineDrawingG1 = this.mUseLineDrawingG1;
        state.mUseLineDrawingUsesG0 = this.mUseLineDrawingUsesG0;
    }

    private void restoreCursor() {
        SavedScreenState state = this.mScreen == this.mMainBuffer ? this.mSavedStateMain : this.mSavedStateAlt;
        setCursorRowCol(state.mSavedCursorRow, state.mSavedCursorCol);
        this.mEffect = state.mSavedEffect;
        this.mForeColor = state.mSavedForeColor;
        this.mBackColor = state.mSavedBackColor;
        this.mCurrentDecSetFlags = (this.mCurrentDecSetFlags & (~12)) | (state.mSavedDecFlags & 12);
        this.mUseLineDrawingG0 = state.mUseLineDrawingG0;
        this.mUseLineDrawingG1 = state.mUseLineDrawingG1;
        this.mUseLineDrawingUsesG0 = state.mUseLineDrawingUsesG0;
    }

    private void doCsi(int b) {
        switch (b) {
            case 32:
                continueSequence(15);
                break;
            case 33:
                continueSequence(19);
                break;
            case 34:
                continueSequence(17);
                break;
            case 36:
                continueSequence(8);
                break;
            case 39:
                continueSequence(18);
                break;
            case 42:
                continueSequence(16);
                break;
            case 62:
                continueSequence(12);
                break;
            case HtmlCompat.FROM_HTML_MODE_COMPACT /* 63 */:
                continueSequence(7);
                break;
            case 64:
                this.mAboutToAutoWrap = false;
                int columnsAfterCursor = this.mColumns - this.mCursorCol;
                int spacesToInsert = Math.min(getArg0(1), columnsAfterCursor);
                int charsToMove = columnsAfterCursor - spacesToInsert;
                TerminalBuffer terminalBuffer = this.mScreen;
                int i = this.mCursorCol;
                int i2 = this.mCursorRow;
                terminalBuffer.blockCopy(i, i2, charsToMove, 1, i + spacesToInsert, i2);
                blockClear(this.mCursorCol, this.mCursorRow, spacesToInsert);
                break;
            case 65:
                setCursorRow(Math.max(0, this.mCursorRow - getArg0(1)));
                break;
            case ConstraintLayout.LayoutParams.Table.LAYOUT_WRAP_BEHAVIOR_IN_PARENT /* 66 */:
                setCursorRow(Math.min(this.mRows - 1, this.mCursorRow + getArg0(1)));
                break;
            case ConstraintLayout.LayoutParams.Table.GUIDELINE_USE_RTL /* 67 */:
            case 97:
                setCursorCol(Math.min(this.mRightMargin - 1, this.mCursorCol + getArg0(1)));
                break;
            case 68:
                setCursorCol(Math.max(this.mLeftMargin, this.mCursorCol - getArg0(1)));
                break;
            case 69:
                setCursorPosition(0, this.mCursorRow + getArg0(1));
                break;
            case 70:
                setCursorPosition(0, this.mCursorRow - getArg0(1));
                break;
            case 71:
                setCursorCol(Math.min(Math.max(1, getArg0(1)), this.mColumns) - 1);
                break;
            case 72:
            case LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY /* 102 */:
                setCursorPosition(getArg1(1) - 1, getArg0(1) - 1);
                break;
            case 73:
                setCursorCol(nextTabStop(getArg0(1)));
                break;
            case 74:
                switch (getArg0(0)) {
                    case 0:
                        int i3 = this.mCursorCol;
                        blockClear(i3, this.mCursorRow, this.mColumns - i3);
                        int i4 = this.mCursorRow;
                        blockClear(0, i4 + 1, this.mColumns, this.mRows - (i4 + 1));
                        break;
                    case 1:
                        blockClear(0, 0, this.mColumns, this.mCursorRow);
                        blockClear(0, this.mCursorRow, this.mCursorCol + 1);
                        break;
                    case 2:
                        blockClear(0, 0, this.mColumns, this.mRows);
                        break;
                    case 3:
                        this.mMainBuffer.clearTranscript();
                        break;
                    default:
                        unknownSequence(b);
                        break;
                }
                this.mAboutToAutoWrap = false;
                break;
            case 75:
                switch (getArg0(0)) {
                    case 0:
                        int i5 = this.mCursorCol;
                        blockClear(i5, this.mCursorRow, this.mColumns - i5);
                        break;
                    case 1:
                        blockClear(0, this.mCursorRow, this.mCursorCol + 1);
                        break;
                    case 2:
                        blockClear(0, this.mCursorRow, this.mColumns);
                        break;
                    default:
                        unknownSequence(b);
                        break;
                }
                this.mAboutToAutoWrap = false;
                break;
            case 76:
                int linesAfterCursor = this.mBottomMargin - this.mCursorRow;
                int linesToInsert = Math.min(getArg0(1), linesAfterCursor);
                TerminalBuffer terminalBuffer2 = this.mScreen;
                int i6 = this.mCursorRow;
                terminalBuffer2.blockCopy(0, i6, this.mColumns, linesAfterCursor - linesToInsert, 0, i6 + linesToInsert);
                blockClear(0, this.mCursorRow, this.mColumns, linesToInsert);
                break;
            case 77:
                this.mAboutToAutoWrap = false;
                int linesAfterCursor2 = this.mBottomMargin - this.mCursorRow;
                int linesToDelete = Math.min(getArg0(1), linesAfterCursor2);
                int linesToMove = linesAfterCursor2 - linesToDelete;
                TerminalBuffer terminalBuffer3 = this.mScreen;
                int i7 = this.mCursorRow;
                terminalBuffer3.blockCopy(0, i7 + linesToDelete, this.mColumns, linesToMove, 0, i7);
                blockClear(0, this.mCursorRow + linesToMove, this.mColumns, linesToDelete);
                break;
            case 80:
                this.mAboutToAutoWrap = false;
                int cellsAfterCursor = this.mColumns - this.mCursorCol;
                int cellsToDelete = Math.min(getArg0(1), cellsAfterCursor);
                int cellsToMove = cellsAfterCursor - cellsToDelete;
                TerminalBuffer terminalBuffer4 = this.mScreen;
                int i8 = this.mCursorCol;
                int i9 = this.mCursorRow;
                terminalBuffer4.blockCopy(i8 + cellsToDelete, i9, cellsToMove, 1, i8, i9);
                blockClear(this.mCursorCol + cellsToMove, this.mCursorRow, cellsToDelete);
                break;
            case 83:
                int linesToScroll = getArg0(1);
                for (int i10 = 0; i10 < linesToScroll; i10++) {
                    scrollDownOneLine();
                }
                break;
            case 84:
                if (this.mArgIndex == 0) {
                    int linesToScrollArg = getArg0(1);
                    int linesBetweenTopAndBottomMargins = this.mBottomMargin - this.mTopMargin;
                    int linesToScroll2 = Math.min(linesBetweenTopAndBottomMargins, linesToScrollArg);
                    TerminalBuffer terminalBuffer5 = this.mScreen;
                    int i11 = this.mTopMargin;
                    terminalBuffer5.blockCopy(0, i11, this.mColumns, linesBetweenTopAndBottomMargins - linesToScroll2, 0, i11 + linesToScroll2);
                    blockClear(0, this.mTopMargin, this.mColumns, linesToScroll2);
                    break;
                } else {
                    unimplementedSequence(b);
                    break;
                }
            case 88:
                this.mAboutToAutoWrap = false;
                this.mScreen.blockSet(this.mCursorCol, this.mCursorRow, Math.min(getArg0(1), this.mColumns - this.mCursorCol), 1, 32, getStyle());
                break;
            case 90:
                int numberOfTabs = getArg0(1);
                int newCol = this.mLeftMargin;
                int i12 = this.mCursorCol - 1;
                while (true) {
                    if (i12 >= 0) {
                        if (this.mTabStop[i12] && numberOfTabs - 1 == 0) {
                            newCol = Math.max(i12, this.mLeftMargin);
                        } else {
                            i12--;
                        }
                    }
                }
                this.mCursorCol = newCol;
                break;
            case 96:
                int numRepeat = getArg0(1);
                setCursorColRespectingOriginMode(numRepeat - 1);
                break;
            case 98:
                if (this.mLastEmittedCodePoint != -1) {
                    int numRepeat2 = getArg0(1);
                    for (int i13 = 0; i13 < numRepeat2; i13++) {
                        emitCodePoint(this.mLastEmittedCodePoint);
                    }
                    break;
                }
                break;
            case 99:
                if (getArg0(0) == 0) {
                    this.mSession.write("\u001b[?64;1;2;6;9;15;18;21;22c");
                    break;
                }
                break;
            case 100:
                setCursorRow(Math.min(Math.max(1, getArg0(1)), this.mRows) - 1);
                break;
            case TypedValues.TYPE_TARGET /* 101 */:
                setCursorPosition(this.mCursorCol, this.mCursorRow + getArg0(1));
                break;
            case 103:
                switch (getArg0(0)) {
                    case 0:
                        this.mTabStop[this.mCursorCol] = false;
                        break;
                    case 3:
                        for (int i14 = 0; i14 < this.mColumns; i14++) {
                            this.mTabStop[i14] = false;
                        }
                        break;
                }
            case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                doSetMode(true);
                break;
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                doSetMode(false);
                break;
            case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                selectGraphicRendition();
                break;
            case 110:
                switch (getArg0(0)) {
                    case 5:
                        byte[] dsr = {27, 91, 48, 110};
                        this.mSession.write(dsr, 0, dsr.length);
                        break;
                    case 6:
                        this.mSession.write(String.format(Locale.US, "\u001b[%d;%dR", Integer.valueOf(this.mCursorRow + 1), Integer.valueOf(this.mCursorCol + 1)));
                        break;
                }
            case 114:
                int iMax = Math.max(0, Math.min(getArg0(1) - 1, this.mRows - 2));
                this.mTopMargin = iMax;
                this.mBottomMargin = Math.max(iMax + 2, Math.min(getArg1(this.mRows), this.mRows));
                setCursorPosition(0, 0);
                break;
            case 115:
                if (isDecsetInternalBitSet(2048)) {
                    int iMin = Math.min(getArg0(1) - 1, this.mColumns - 2);
                    this.mLeftMargin = iMin;
                    this.mRightMargin = Math.max(iMin + 1, Math.min(getArg1(this.mColumns), this.mColumns));
                    setCursorPosition(0, 0);
                    break;
                } else {
                    saveCursor();
                    break;
                }
            case 116:
                switch (getArg0(0)) {
                    case 11:
                        this.mSession.write("\u001b[1t");
                        break;
                    case 13:
                        this.mSession.write("\u001b[3;0;0t");
                        break;
                    case 14:
                        this.mSession.write(String.format(Locale.US, "\u001b[4;%d;%dt", Integer.valueOf(this.mRows * 12), Integer.valueOf(this.mColumns * 12)));
                        break;
                    case 18:
                        this.mSession.write(String.format(Locale.US, "\u001b[8;%d;%dt", Integer.valueOf(this.mRows), Integer.valueOf(this.mColumns)));
                        break;
                    case 19:
                        this.mSession.write(String.format(Locale.US, "\u001b[9;%d;%dt", Integer.valueOf(this.mRows), Integer.valueOf(this.mColumns)));
                        break;
                    case 20:
                        this.mSession.write("\u001b]LIconLabel\u001b\\");
                        break;
                    case 21:
                        this.mSession.write("\u001b]l\u001b\\");
                        break;
                    case 22:
                        this.mTitleStack.push(this.mTitle);
                        if (this.mTitleStack.size() > 20) {
                            this.mTitleStack.remove(0);
                            break;
                        }
                        break;
                    case 23:
                        if (!this.mTitleStack.isEmpty()) {
                            setTitle(this.mTitleStack.pop());
                            break;
                        }
                        break;
                }
            case 117:
                restoreCursor();
                break;
            default:
                parseArg(b);
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void selectGraphicRendition() {
        /*
            Method dump skipped, instructions count: 493
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.terminal.TerminalEmulator.selectGraphicRendition():void");
    }

    private void doOsc(int b) throws NumberFormatException {
        switch (b) {
            case 7:
                doOscSetTextParameters("\u0007");
                break;
            case 27:
                continueSequence(11);
                break;
            default:
                collectOSCArgs(b);
                break;
        }
    }

    private void doOscEsc(int b) throws NumberFormatException {
        switch (b) {
            case 92:
                doOscSetTextParameters("\u001b\\");
                break;
            default:
                collectOSCArgs(27);
                collectOSCArgs(b);
                continueSequence(10);
                break;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0228, code lost:
    
        if (r3 != r4.length()) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x022a, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x022c, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x022e, code lost:
    
        if (r5 == false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0230, code lost:
    
        r6 = ';';
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0233, code lost:
    
        r6 = r4.charAt(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0239, code lost:
    
        if (r6 != ';') goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x023b, code lost:
    
        if (r2 >= 0) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x023d, code lost:
    
        r2 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0242, code lost:
    
        if (r0 < 0) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0246, code lost:
    
        if (r0 <= 255) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0249, code lost:
    
        r22.mColors.tryParseColor(r0, r4.substring(r2, r3));
        r22.mSession.onColorsChanged();
        r0 = -1;
        r2 = -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x025a, code lost:
    
        unknownSequence(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x025d, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0260, code lost:
    
        if (r2 < 0) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0263, code lost:
    
        if (r2 >= 0) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0265, code lost:
    
        if (r6 < '0') goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0267, code lost:
    
        if (r6 > '9') goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0269, code lost:
    
        if (r0 >= 0) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x026b, code lost:
    
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x026e, code lost:
    
        r12 = r0 * 10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0270, code lost:
    
        r0 = r12 + (r6 - '0');
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0274, code lost:
    
        if (r5 == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0279, code lost:
    
        r3 = r3 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x027c, code lost:
    
        unknownSequence(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x027f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0280, code lost:
    
        setTitle(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0043, code lost:
    
        r6 = 255;
        r11 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0046, code lost:
    
        switch(r5) {
            case 0: goto L132;
            case 1: goto L132;
            case 2: goto L132;
            case 4: goto L98;
            case 10: goto L44;
            case 11: goto L44;
            case 12: goto L44;
            case 52: goto L39;
            case 104: goto L21;
            case 110: goto L20;
            case 111: goto L20;
            case 112: goto L20;
            case 119: goto L19;
            default: goto L18;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0049, code lost:
    
        unknownParameter(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0056, code lost:
    
        r22.mColors.reset((r5 - 110) + 256);
        r22.mSession.onColorsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x006c, code lost:
    
        if (r4.isEmpty() == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006e, code lost:
    
        r22.mColors.reset();
        r22.mSession.onColorsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007c, code lost:
    
        r3 = 0;
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0080, code lost:
    
        r0 = r4.length();
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0084, code lost:
    
        if (r3 != r0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0086, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0088, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0089, code lost:
    
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x008a, code lost:
    
        if (r6 != false) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0090, code lost:
    
        if (r4.charAt(r3) != ';') goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0092, code lost:
    
        r0 = java.lang.Integer.parseInt(r4.substring(r2, r3));
        r22.mColors.reset(r0);
        r22.mSession.onColorsChanged();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a4, code lost:
    
        if (r6 == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00aa, code lost:
    
        r3 = r3 + 1;
        r2 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b2, code lost:
    
        r2 = r4.indexOf(";") + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00ba, code lost:
    
        r0 = new java.lang.String(android.util.Base64.decode(r4.substring(r2), 0), java.nio.charset.StandardCharsets.UTF_8);
        r22.mSession.onCopyTextToClipboard(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d3, code lost:
    
        r22.mClient.logError(com.termux.terminal.TerminalEmulator.LOG_TAG, "OSC Manipulate selection, invalid string '" + r4 + "");
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f7, code lost:
    
        r0 = (r5 - 10) + 256;
        r12 = 0;
        r8 = 0;
        r7 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x021a, code lost:
    
        r0 = -1;
        r2 = -1;
        r3 = 0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void doOscSetTextParameters(java.lang.String r23) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 706
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.terminal.TerminalEmulator.doOscSetTextParameters(java.lang.String):void");
    }

    private void blockClear(int sx, int sy, int w) {
        blockClear(sx, sy, w, 1);
    }

    private void blockClear(int sx, int sy, int w, int h) {
        this.mScreen.blockSet(sx, sy, w, h, 32, getStyle());
    }

    private long getStyle() {
        return TextStyle.encode(this.mForeColor, this.mBackColor, this.mEffect);
    }

    private void doSetMode(boolean newValue) {
        int modeBit = getArg0(0);
        switch (modeBit) {
            case 4:
                this.mInsertMode = newValue;
                break;
            case 20:
                unknownParameter(modeBit);
                break;
            case 34:
                break;
            default:
                unknownParameter(modeBit);
                break;
        }
    }

    private void setCursorPosition(int x, int y) {
        boolean originMode = isDecsetInternalBitSet(4);
        int effectiveTopMargin = originMode ? this.mTopMargin : 0;
        int effectiveBottomMargin = originMode ? this.mBottomMargin : this.mRows;
        int effectiveLeftMargin = originMode ? this.mLeftMargin : 0;
        int effectiveRightMargin = originMode ? this.mRightMargin : this.mColumns;
        int newRow = Math.max(effectiveTopMargin, Math.min(effectiveTopMargin + y, effectiveBottomMargin - 1));
        int newCol = Math.max(effectiveLeftMargin, Math.min(effectiveLeftMargin + x, effectiveRightMargin - 1));
        setCursorRowCol(newRow, newCol);
    }

    private void scrollDownOneLine() {
        this.mScrollCounter++;
        int i = this.mLeftMargin;
        if (i == 0 && this.mRightMargin == this.mColumns) {
            this.mScreen.scrollDownOneLine(this.mTopMargin, this.mBottomMargin, getStyle());
            return;
        }
        TerminalBuffer terminalBuffer = this.mScreen;
        int i2 = this.mTopMargin;
        terminalBuffer.blockCopy(i, i2 + 1, this.mRightMargin - i, (this.mBottomMargin - i2) - 1, i, i2);
        TerminalBuffer terminalBuffer2 = this.mScreen;
        int i3 = this.mLeftMargin;
        terminalBuffer2.blockSet(i3, this.mBottomMargin - 1, this.mRightMargin - i3, 1, 32, this.mEffect);
    }

    private void parseArg(int b) {
        int value;
        if (b >= 48 && b <= 57) {
            int i = this.mArgIndex;
            int[] iArr = this.mArgs;
            if (i < iArr.length) {
                int oldValue = iArr[i];
                int thisDigit = b - 48;
                if (oldValue >= 0) {
                    value = (oldValue * 10) + thisDigit;
                } else {
                    value = thisDigit;
                }
                iArr[i] = value;
            }
            continueSequence(this.mEscapeState);
            return;
        }
        if (b == 59) {
            int i2 = this.mArgIndex;
            if (i2 < this.mArgs.length) {
                this.mArgIndex = i2 + 1;
            }
            continueSequence(this.mEscapeState);
            return;
        }
        unknownSequence(b);
    }

    private int getArg0(int defaultValue) {
        return getArg(0, defaultValue, true);
    }

    private int getArg1(int defaultValue) {
        return getArg(1, defaultValue, true);
    }

    private int getArg(int index, int defaultValue, boolean treatZeroAsDefault) {
        int result = this.mArgs[index];
        if (result < 0 || (result == 0 && treatZeroAsDefault)) {
            return defaultValue;
        }
        return result;
    }

    private void collectOSCArgs(int b) {
        if (this.mOSCOrDeviceControlArgs.length() < 8192) {
            this.mOSCOrDeviceControlArgs.appendCodePoint(b);
            continueSequence(this.mEscapeState);
        } else {
            unknownSequence(b);
        }
    }

    private void unimplementedSequence(int b) {
        logError("Unimplemented sequence char '" + ((char) b) + "' (U+" + String.format("%04x", Integer.valueOf(b)) + ")");
        finishSequence();
    }

    private void unknownSequence(int b) {
        logError("Unknown sequence char '" + ((char) b) + "' (numeric value=" + b + ")");
        finishSequence();
    }

    private void unknownParameter(int parameter) {
        logError("Unknown parameter: " + parameter);
        finishSequence();
    }

    private void logError(String errorType) {
    }

    private void finishSequenceAndLogError(String error) {
        finishSequence();
    }

    private void finishSequence() {
        this.mEscapeState = 0;
    }

    private void emitCodePoint(int codePoint) {
        int i;
        int destCol;
        int i2;
        int codePoint2 = codePoint;
        this.mLastEmittedCodePoint = codePoint2;
        if (!this.mUseLineDrawingUsesG0 ? this.mUseLineDrawingG1 : this.mUseLineDrawingG0) {
            switch (codePoint2) {
                case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                    codePoint2 = 9608;
                    break;
                case 95:
                    codePoint2 = 32;
                    break;
                case 96:
                    codePoint2 = 9670;
                    break;
                case 97:
                    codePoint2 = 9618;
                    break;
                case 98:
                    codePoint2 = 9225;
                    break;
                case 99:
                    codePoint2 = 9228;
                    break;
                case 100:
                    codePoint2 = 13;
                    break;
                case TypedValues.TYPE_TARGET /* 101 */:
                    codePoint2 = 9226;
                    break;
                case LocationRequestCompat.QUALITY_BALANCED_POWER_ACCURACY /* 102 */:
                    codePoint2 = 176;
                    break;
                case 103:
                    codePoint2 = 177;
                    break;
                case LocationRequestCompat.QUALITY_LOW_POWER /* 104 */:
                    codePoint2 = 10;
                    break;
                case 105:
                    codePoint2 = 9227;
                    break;
                case 106:
                    codePoint2 = 9496;
                    break;
                case 107:
                    codePoint2 = 9488;
                    break;
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR /* 108 */:
                    codePoint2 = 9484;
                    break;
                case AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY /* 109 */:
                    codePoint2 = 9492;
                    break;
                case 110:
                    codePoint2 = 9532;
                    break;
                case 111:
                    codePoint2 = 9146;
                    break;
                case 112:
                    codePoint2 = 9147;
                    break;
                case 113:
                    codePoint2 = 9472;
                    break;
                case 114:
                    codePoint2 = 9148;
                    break;
                case 115:
                    codePoint2 = 9149;
                    break;
                case 116:
                    codePoint2 = 9500;
                    break;
                case 117:
                    codePoint2 = 9508;
                    break;
                case 118:
                    codePoint2 = 9524;
                    break;
                case 119:
                    codePoint2 = 9516;
                    break;
                case 120:
                    codePoint2 = 9474;
                    break;
                case 121:
                    codePoint2 = 8804;
                    break;
                case 122:
                    codePoint2 = 8805;
                    break;
                case 123:
                    codePoint2 = 960;
                    break;
                case 124:
                    codePoint2 = 8800;
                    break;
                case 125:
                    codePoint2 = 163;
                    break;
                case 126:
                    codePoint2 = 183;
                    break;
            }
        }
        boolean autoWrap = isDecsetInternalBitSet(8);
        int displayWidth = WcWidth.width(codePoint2);
        boolean cursorInLastColumn = this.mCursorCol == this.mRightMargin - 1;
        if (autoWrap) {
            if (cursorInLastColumn && ((this.mAboutToAutoWrap && displayWidth == 1) || displayWidth == 2)) {
                this.mScreen.setLineWrap(this.mCursorRow);
                this.mCursorCol = this.mLeftMargin;
                int i3 = this.mCursorRow;
                if (i3 + 1 < this.mBottomMargin) {
                    this.mCursorRow = i3 + 1;
                } else {
                    scrollDownOneLine();
                }
            }
        } else if (cursorInLastColumn && displayWidth == 2) {
            return;
        }
        if (this.mInsertMode && displayWidth > 0 && (destCol = (i = this.mCursorCol) + displayWidth) < (i2 = this.mRightMargin)) {
            TerminalBuffer terminalBuffer = this.mScreen;
            int i4 = this.mCursorRow;
            terminalBuffer.blockCopy(i, i4, i2 - destCol, 1, destCol, i4);
        }
        int offsetDueToCombiningChar = (displayWidth > 0 || this.mCursorCol <= 0 || this.mAboutToAutoWrap) ? 0 : 1;
        this.mScreen.setChar(this.mCursorCol - offsetDueToCombiningChar, this.mCursorRow, codePoint2, getStyle());
        if (autoWrap && displayWidth > 0) {
            this.mAboutToAutoWrap = this.mCursorCol == this.mRightMargin - displayWidth;
        }
        this.mCursorCol = Math.min(this.mCursorCol + displayWidth, this.mRightMargin - 1);
    }

    private void setCursorRow(int row) {
        this.mCursorRow = row;
        this.mAboutToAutoWrap = false;
    }

    private void setCursorCol(int col) {
        this.mCursorCol = col;
        this.mAboutToAutoWrap = false;
    }

    private void setCursorColRespectingOriginMode(int col) {
        setCursorPosition(col, this.mCursorRow);
    }

    private void setCursorRowCol(int row, int col) {
        this.mCursorRow = Math.max(0, Math.min(row, this.mRows - 1));
        this.mCursorCol = Math.max(0, Math.min(col, this.mColumns - 1));
        this.mAboutToAutoWrap = false;
    }

    public int getScrollCounter() {
        return this.mScrollCounter;
    }

    public void clearScrollCounter() {
        this.mScrollCounter = 0;
    }

    public void reset() {
        setCursorStyle();
        this.mArgIndex = 0;
        this.mContinueSequence = false;
        this.mEscapeState = 0;
        this.mInsertMode = false;
        this.mLeftMargin = 0;
        this.mTopMargin = 0;
        this.mBottomMargin = this.mRows;
        this.mRightMargin = this.mColumns;
        this.mAboutToAutoWrap = false;
        SavedScreenState savedScreenState = this.mSavedStateMain;
        this.mSavedStateAlt.mSavedForeColor = 256;
        savedScreenState.mSavedForeColor = 256;
        this.mForeColor = 256;
        SavedScreenState savedScreenState2 = this.mSavedStateMain;
        this.mSavedStateAlt.mSavedBackColor = 257;
        savedScreenState2.mSavedBackColor = 257;
        this.mBackColor = 257;
        setDefaultTabStops();
        this.mUseLineDrawingG1 = false;
        this.mUseLineDrawingG0 = false;
        this.mUseLineDrawingUsesG0 = true;
        SavedScreenState savedScreenState3 = this.mSavedStateMain;
        savedScreenState3.mSavedDecFlags = 0;
        savedScreenState3.mSavedEffect = 0;
        savedScreenState3.mSavedCursorCol = 0;
        savedScreenState3.mSavedCursorRow = 0;
        SavedScreenState savedScreenState4 = this.mSavedStateAlt;
        savedScreenState4.mSavedDecFlags = 0;
        savedScreenState4.mSavedEffect = 0;
        savedScreenState4.mSavedCursorCol = 0;
        savedScreenState4.mSavedCursorRow = 0;
        this.mCurrentDecSetFlags = 0;
        setDecsetinternalBit(8, true);
        setDecsetinternalBit(16, true);
        SavedScreenState savedScreenState5 = this.mSavedStateMain;
        SavedScreenState savedScreenState6 = this.mSavedStateAlt;
        int i = this.mCurrentDecSetFlags;
        savedScreenState6.mSavedDecFlags = i;
        savedScreenState5.mSavedDecFlags = i;
        this.mSavedDecSetFlags = i;
        this.mUtf8ToFollow = (byte) 0;
        this.mUtf8Index = (byte) 0;
        this.mColors.reset();
        this.mSession.onColorsChanged();
    }

    public String getSelectedText(int x1, int y1, int x2, int y2) {
        return this.mScreen.getSelectedText(x1, y1, x2, y2);
    }

    public String getTitle() {
        return this.mTitle;
    }

    private void setTitle(String newTitle) {
        String oldTitle = this.mTitle;
        this.mTitle = newTitle;
        if (!Objects.equals(oldTitle, newTitle)) {
            this.mSession.titleChanged(oldTitle, newTitle);
        }
    }

    public void paste(String text) {
        String text2 = text.replaceAll("(\u001b|[\u0080-\u009f])", "").replaceAll("\r?\n", "\r");
        boolean bracketed = isDecsetInternalBitSet(1024);
        if (bracketed) {
            this.mSession.write("\u001b[200~");
        }
        this.mSession.write(text2);
        if (bracketed) {
            this.mSession.write("\u001b[201~");
        }
    }

    static final class SavedScreenState {
        int mSavedBackColor;
        int mSavedCursorCol;
        int mSavedCursorRow;
        int mSavedDecFlags;
        int mSavedEffect;
        int mSavedForeColor;
        boolean mUseLineDrawingG0;
        boolean mUseLineDrawingG1;
        boolean mUseLineDrawingUsesG0 = true;

        SavedScreenState() {
        }
    }

    public String toString() {
        return "TerminalEmulator[size=" + this.mScreen.mColumns + "x" + this.mScreen.mScreenRows + ", margins={" + this.mTopMargin + "," + this.mRightMargin + "," + this.mBottomMargin + "," + this.mLeftMargin + "}]";
    }
}
