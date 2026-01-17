package com.termux.terminal;

/* loaded from: classes.dex */
final class JNI {
    public static native void close(int fileDescriptor);

    public static native int createSubprocess(String cmd, String cwd, String[] args, String[] envVars, int[] processId, int rows, int columns);

    public static native void setPtyWindowSize(int fd, int rows, int cols);

    public static native int waitFor(int processId);

    JNI() {
    }

    static {
        System.loadLibrary("termux");
    }
}
