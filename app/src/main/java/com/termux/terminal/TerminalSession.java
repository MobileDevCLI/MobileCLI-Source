package com.termux.terminal;

import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* loaded from: classes.dex */
public final class TerminalSession extends TerminalOutput {
    private static final String LOG_TAG = "TerminalSession";
    private static final int MSG_NEW_INPUT = 1;
    private static final int MSG_PROCESS_EXITED = 4;
    private final String[] mArgs;
    TerminalSessionClient mClient;
    private final String mCwd;
    TerminalEmulator mEmulator;
    private final String[] mEnv;
    public String mSessionName;
    int mShellExitStatus;
    private final String mShellPath;
    int mShellPid;
    private int mTerminalFileDescriptor;
    private final Integer mTranscriptRows;
    public final String mHandle = UUID.randomUUID().toString();
    final ByteQueue mProcessToTerminalIOQueue = new ByteQueue(4096);
    final ByteQueue mTerminalToProcessIOQueue = new ByteQueue(4096);
    private final byte[] mUtf8InputBuffer = new byte[5];
    final Handler mMainThreadHandler = new MainThreadHandler();

    public TerminalSession(String shellPath, String cwd, String[] args, String[] env, Integer transcriptRows, TerminalSessionClient client) {
        this.mShellPath = shellPath;
        this.mCwd = cwd;
        this.mArgs = args;
        this.mEnv = env;
        this.mTranscriptRows = transcriptRows;
        this.mClient = client;
    }

    public void updateTerminalSessionClient(TerminalSessionClient client) {
        this.mClient = client;
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator != null) {
            terminalEmulator.updateTerminalSessionClient(client);
        }
    }

    public void updateSize(int columns, int rows) {
        if (this.mEmulator == null) {
            initializeEmulator(columns, rows);
        } else {
            JNI.setPtyWindowSize(this.mTerminalFileDescriptor, rows, columns);
            this.mEmulator.resize(columns, rows);
        }
    }

    public String getTitle() {
        TerminalEmulator terminalEmulator = this.mEmulator;
        if (terminalEmulator == null) {
            return null;
        }
        return terminalEmulator.getTitle();
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.termux.terminal.TerminalSession$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.termux.terminal.TerminalSession$2] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.termux.terminal.TerminalSession$3] */
    public void initializeEmulator(int columns, int rows) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        this.mEmulator = new TerminalEmulator(this, columns, rows, this.mTranscriptRows, this.mClient);
        int[] processId = new int[1];
        int iCreateSubprocess = JNI.createSubprocess(this.mShellPath, this.mCwd, this.mArgs, this.mEnv, processId, rows, columns);
        this.mTerminalFileDescriptor = iCreateSubprocess;
        this.mShellPid = processId[0];
        final FileDescriptor terminalFileDescriptorWrapped = wrapFileDescriptor(iCreateSubprocess, this.mClient);
        new Thread("TermSessionInputReader[pid=" + this.mShellPid + "]") { // from class: com.termux.terminal.TerminalSession.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws IOException {
                try {
                    InputStream termIn = new FileInputStream(terminalFileDescriptorWrapped);
                    try {
                        byte[] buffer = new byte[4096];
                        while (true) {
                            int read = termIn.read(buffer);
                            if (read != -1) {
                                if (TerminalSession.this.mProcessToTerminalIOQueue.write(buffer, 0, read)) {
                                    TerminalSession.this.mMainThreadHandler.sendEmptyMessage(1);
                                } else {
                                    termIn.close();
                                    return;
                                }
                            } else {
                                termIn.close();
                                return;
                            }
                        }
                    } finally {
                    }
                } catch (Exception e) {
                }
            }
        }.start();
        new Thread("TermSessionOutputWriter[pid=" + this.mShellPid + "]") { // from class: com.termux.terminal.TerminalSession.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws IOException {
                byte[] buffer = new byte[4096];
                try {
                    FileOutputStream termOut = new FileOutputStream(terminalFileDescriptorWrapped);
                    while (true) {
                        try {
                            int bytesToWrite = TerminalSession.this.mTerminalToProcessIOQueue.read(buffer, true);
                            if (bytesToWrite != -1) {
                                termOut.write(buffer, 0, bytesToWrite);
                            } else {
                                termOut.close();
                                return;
                            }
                        } finally {
                        }
                    }
                } catch (IOException e) {
                }
            }
        }.start();
        new Thread("TermSessionWaiter[pid=" + this.mShellPid + "]") { // from class: com.termux.terminal.TerminalSession.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int processExitCode = JNI.waitFor(TerminalSession.this.mShellPid);
                TerminalSession.this.mMainThreadHandler.sendMessage(TerminalSession.this.mMainThreadHandler.obtainMessage(4, Integer.valueOf(processExitCode)));
            }
        }.start();
    }

    @Override // com.termux.terminal.TerminalOutput
    public void write(byte[] data, int offset, int count) {
        if (this.mShellPid > 0) {
            this.mTerminalToProcessIOQueue.write(data, offset, count);
        }
    }

    public void writeCodePoint(boolean prependEscape, int codePoint) {
        int bufferPosition;
        if (codePoint > 1114111 || (codePoint >= 55296 && codePoint <= 57343)) {
            throw new IllegalArgumentException("Invalid code point: " + codePoint);
        }
        int bufferPosition2 = 0;
        if (prependEscape) {
            int bufferPosition3 = 0 + 1;
            this.mUtf8InputBuffer[0] = 27;
            bufferPosition2 = bufferPosition3;
        }
        if (codePoint <= 127) {
            bufferPosition = bufferPosition2 + 1;
            this.mUtf8InputBuffer[bufferPosition2] = (byte) codePoint;
        } else if (codePoint <= 2047) {
            byte[] bArr = this.mUtf8InputBuffer;
            int bufferPosition4 = bufferPosition2 + 1;
            bArr[bufferPosition2] = (byte) ((codePoint >> 6) | 192);
            bArr[bufferPosition4] = (byte) ((codePoint & 63) | 128);
            bufferPosition = bufferPosition4 + 1;
        } else if (codePoint <= 65535) {
            byte[] bArr2 = this.mUtf8InputBuffer;
            int bufferPosition5 = bufferPosition2 + 1;
            bArr2[bufferPosition2] = (byte) ((codePoint >> 12) | 224);
            int bufferPosition6 = bufferPosition5 + 1;
            bArr2[bufferPosition5] = (byte) (((codePoint >> 6) & 63) | 128);
            bufferPosition = bufferPosition6 + 1;
            bArr2[bufferPosition6] = (byte) ((codePoint & 63) | 128);
        } else {
            byte[] bArr3 = this.mUtf8InputBuffer;
            int bufferPosition7 = bufferPosition2 + 1;
            bArr3[bufferPosition2] = (byte) ((codePoint >> 18) | 240);
            int bufferPosition8 = bufferPosition7 + 1;
            bArr3[bufferPosition7] = (byte) (((codePoint >> 12) & 63) | 128);
            int bufferPosition9 = bufferPosition8 + 1;
            bArr3[bufferPosition8] = (byte) (((codePoint >> 6) & 63) | 128);
            bArr3[bufferPosition9] = (byte) ((codePoint & 63) | 128);
            bufferPosition = bufferPosition9 + 1;
        }
        write(this.mUtf8InputBuffer, 0, bufferPosition);
    }

    public TerminalEmulator getEmulator() {
        return this.mEmulator;
    }

    protected void notifyScreenUpdate() {
        this.mClient.onTextChanged(this);
    }

    public void reset() {
        this.mEmulator.reset();
        notifyScreenUpdate();
    }

    public void finishIfRunning() throws ErrnoException {
        if (isRunning()) {
            try {
                Os.kill(this.mShellPid, OsConstants.SIGKILL);
            } catch (ErrnoException e) {
                this.mClient.logWarn(LOG_TAG, "Failed sending SIGKILL: " + e.getMessage());
            }
        }
    }

    void cleanupResources(int exitStatus) {
        synchronized (this) {
            this.mShellPid = -1;
            this.mShellExitStatus = exitStatus;
        }
        this.mTerminalToProcessIOQueue.close();
        this.mProcessToTerminalIOQueue.close();
        JNI.close(this.mTerminalFileDescriptor);
    }

    @Override // com.termux.terminal.TerminalOutput
    public void titleChanged(String oldTitle, String newTitle) {
        this.mClient.onTitleChanged(this);
    }

    public synchronized boolean isRunning() {
        return this.mShellPid != -1;
    }

    public synchronized int getExitStatus() {
        return this.mShellExitStatus;
    }

    @Override // com.termux.terminal.TerminalOutput
    public void onCopyTextToClipboard(String text) {
        this.mClient.onCopyTextToClipboard(this, text);
    }

    @Override // com.termux.terminal.TerminalOutput
    public void onPasteTextFromClipboard() {
        this.mClient.onPasteTextFromClipboard(this);
    }

    @Override // com.termux.terminal.TerminalOutput
    public void onBell() {
        this.mClient.onBell(this);
    }

    @Override // com.termux.terminal.TerminalOutput
    public void onColorsChanged() {
        this.mClient.onColorsChanged(this);
    }

    public int getPid() {
        return this.mShellPid;
    }

    public String getCwd() throws IOException {
        String cwdSymlink;
        String outputPath;
        String outputPathWithTrailingSlash;
        int i = this.mShellPid;
        if (i < 1) {
            return null;
        }
        try {
            cwdSymlink = String.format("/proc/%s/cwd/", Integer.valueOf(i));
            outputPath = new File(cwdSymlink).getCanonicalPath();
            outputPathWithTrailingSlash = outputPath;
            if (!outputPath.endsWith("/")) {
                outputPathWithTrailingSlash = outputPathWithTrailingSlash + '/';
            }
        } catch (IOException | SecurityException e) {
            this.mClient.logStackTraceWithMessage(LOG_TAG, "Error getting current directory", e);
        }
        if (cwdSymlink.equals(outputPathWithTrailingSlash)) {
            return null;
        }
        return outputPath;
    }

    private static FileDescriptor wrapFileDescriptor(int fileDescriptor, TerminalSessionClient client) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Field descriptorField;
        FileDescriptor result = new FileDescriptor();
        try {
            try {
                descriptorField = FileDescriptor.class.getDeclaredField("descriptor");
            } catch (NoSuchFieldException e) {
                try {
                    descriptorField = FileDescriptor.class.getDeclaredField("fd");
                } catch (NoSuchFieldException e2) {
                    e = e2;
                    client.logStackTraceWithMessage(LOG_TAG, "Error accessing FileDescriptor#descriptor private field", e);
                    System.exit(1);
                    return result;
                }
            }
            descriptorField.setAccessible(true);
            descriptorField.set(result, Integer.valueOf(fileDescriptor));
        } catch (IllegalAccessException e3) {
            e = e3;
            client.logStackTraceWithMessage(LOG_TAG, "Error accessing FileDescriptor#descriptor private field", e);
            System.exit(1);
            return result;
        } catch (IllegalArgumentException e4) {
            e = e4;
            client.logStackTraceWithMessage(LOG_TAG, "Error accessing FileDescriptor#descriptor private field", e);
            System.exit(1);
            return result;
        }
        return result;
    }

    class MainThreadHandler extends Handler {
        final byte[] mReceiveBuffer = new byte[4096];

        MainThreadHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) throws NumberFormatException {
            int bytesRead = TerminalSession.this.mProcessToTerminalIOQueue.read(this.mReceiveBuffer, false);
            if (bytesRead > 0) {
                TerminalSession.this.mEmulator.append(this.mReceiveBuffer, bytesRead);
                TerminalSession.this.notifyScreenUpdate();
            }
            if (msg.what == 4) {
                int exitCode = ((Integer) msg.obj).intValue();
                TerminalSession.this.cleanupResources(exitCode);
                String exitDescription = "\r\n[Process completed";
                if (exitCode > 0) {
                    exitDescription = "\r\n[Process completed (code " + exitCode + ")";
                } else if (exitCode < 0) {
                    exitDescription = "\r\n[Process completed (signal " + (-exitCode) + ")";
                }
                byte[] bytesToWrite = (exitDescription + " - press Enter]").getBytes(StandardCharsets.UTF_8);
                TerminalSession.this.mEmulator.append(bytesToWrite, bytesToWrite.length);
                TerminalSession.this.notifyScreenUpdate();
                TerminalSession.this.mClient.onSessionFinished(TerminalSession.this);
            }
        }
    }
}
