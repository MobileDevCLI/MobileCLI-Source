package com.termux.terminal;

/* loaded from: classes.dex */
final class ByteQueue {
    private final byte[] mBuffer;
    private int mHead;
    private boolean mOpen = true;
    private int mStoredBytes;

    public ByteQueue(int size) {
        this.mBuffer = new byte[size];
    }

    public synchronized void close() {
        this.mOpen = false;
        notify();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0017, code lost:
    
        if (r10.mOpen != false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x001a, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x001c, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x001d, code lost:
    
        r3 = r10.mBuffer.length;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0020, code lost:
    
        if (r3 != r0) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0022, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0024, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0025, code lost:
    
        r4 = r11.length;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0027, code lost:
    
        if (r4 <= 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0029, code lost:
    
        r6 = r10.mStoredBytes;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x002b, code lost:
    
        if (r6 <= 0) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x002d, code lost:
    
        r6 = java.lang.Math.min(r3 - r10.mHead, r6);
        r7 = java.lang.Math.min(r4, r6);
        java.lang.System.arraycopy(r10.mBuffer, r10.mHead, r11, r5, r7);
        r8 = r10.mHead + r7;
        r10.mHead = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0045, code lost:
    
        if (r8 < r3) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0047, code lost:
    
        r10.mHead = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0049, code lost:
    
        r10.mStoredBytes -= r7;
        r4 = r4 - r7;
        r5 = r5 + r7;
        r2 = r2 + r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0052, code lost:
    
        if (r0 == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0054, code lost:
    
        notify();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0058, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized int read(byte[] r11, boolean r12) {
        /*
            r10 = this;
            monitor-enter(r10)
        L2:
            int r0 = r10.mStoredBytes     // Catch: java.lang.Throwable -> L59
            r1 = 0
            if (r0 != 0) goto L15
            boolean r2 = r10.mOpen     // Catch: java.lang.Throwable -> L59
            if (r2 == 0) goto L15
            if (r12 == 0) goto L13
            r10.wait()     // Catch: java.lang.InterruptedException -> L11 java.lang.Throwable -> L59
            goto L2
        L11:
            r0 = move-exception
            goto L2
        L13:
            monitor-exit(r10)
            return r1
        L15:
            boolean r2 = r10.mOpen     // Catch: java.lang.Throwable -> L59
            if (r2 != 0) goto L1c
            monitor-exit(r10)
            r0 = -1
            return r0
        L1c:
            r2 = 0
            byte[] r3 = r10.mBuffer     // Catch: java.lang.Throwable -> L59
            int r3 = r3.length     // Catch: java.lang.Throwable -> L59
            if (r3 != r0) goto L24
            r0 = 1
            goto L25
        L24:
            r0 = r1
        L25:
            int r4 = r11.length     // Catch: java.lang.Throwable -> L59
            r5 = 0
        L27:
            if (r4 <= 0) goto L52
            int r6 = r10.mStoredBytes     // Catch: java.lang.Throwable -> L59
            if (r6 <= 0) goto L52
            int r7 = r10.mHead     // Catch: java.lang.Throwable -> L59
            int r7 = r3 - r7
            int r6 = java.lang.Math.min(r7, r6)     // Catch: java.lang.Throwable -> L59
            int r7 = java.lang.Math.min(r4, r6)     // Catch: java.lang.Throwable -> L59
            byte[] r8 = r10.mBuffer     // Catch: java.lang.Throwable -> L59
            int r9 = r10.mHead     // Catch: java.lang.Throwable -> L59
            java.lang.System.arraycopy(r8, r9, r11, r5, r7)     // Catch: java.lang.Throwable -> L59
            int r8 = r10.mHead     // Catch: java.lang.Throwable -> L59
            int r8 = r8 + r7
            r10.mHead = r8     // Catch: java.lang.Throwable -> L59
            if (r8 < r3) goto L49
            r10.mHead = r1     // Catch: java.lang.Throwable -> L59
        L49:
            int r8 = r10.mStoredBytes     // Catch: java.lang.Throwable -> L59
            int r8 = r8 - r7
            r10.mStoredBytes = r8     // Catch: java.lang.Throwable -> L59
            int r4 = r4 - r7
            int r5 = r5 + r7
            int r2 = r2 + r7
            goto L27
        L52:
            if (r0 == 0) goto L57
            r10.notify()     // Catch: java.lang.Throwable -> L59
        L57:
            monitor-exit(r10)
            return r2
        L59:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.terminal.ByteQueue.read(byte[], boolean):int");
    }

    public boolean write(byte[] buffer, int offset, int lengthToWrite) {
        int i;
        int oneRun;
        if (lengthToWrite + offset > buffer.length) {
            throw new IllegalArgumentException("length + offset > buffer.length");
        }
        if (lengthToWrite <= 0) {
            throw new IllegalArgumentException("length <= 0");
        }
        int bufferLength = this.mBuffer.length;
        synchronized (this) {
            while (true) {
                boolean wasEmpty = true;
                if (lengthToWrite <= 0) {
                    return true;
                }
                while (true) {
                    try {
                        i = this.mStoredBytes;
                        if (bufferLength != i || !this.mOpen) {
                            break;
                        }
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    } finally {
                    }
                }
                if (!this.mOpen) {
                    return false;
                }
                if (i != 0) {
                    wasEmpty = false;
                }
                int bytesToWriteBeforeWaiting = Math.min(lengthToWrite, bufferLength - i);
                lengthToWrite -= bytesToWriteBeforeWaiting;
                while (bytesToWriteBeforeWaiting > 0) {
                    int i2 = this.mHead;
                    int tail = this.mStoredBytes + i2;
                    if (tail >= bufferLength) {
                        tail -= bufferLength;
                        oneRun = i2 - tail;
                    } else {
                        oneRun = bufferLength - tail;
                    }
                    int bytesToCopy = Math.min(oneRun, bytesToWriteBeforeWaiting);
                    System.arraycopy(buffer, offset, this.mBuffer, tail, bytesToCopy);
                    offset += bytesToCopy;
                    bytesToWriteBeforeWaiting -= bytesToCopy;
                    this.mStoredBytes += bytesToCopy;
                }
                if (wasEmpty) {
                    notify();
                }
            }
        }
    }
}
