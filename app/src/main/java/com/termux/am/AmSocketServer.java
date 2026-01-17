package com.termux.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Credentials;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.Uri;
import android.os.Process;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.termux.terminal.KeyHandler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: AmSocketServer.kt */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 *2\u00020\u0001:\u0002)*B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0016\u0010\u0013\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0016\u0010\u0016\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0016\u0010\u0017\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0016\u0010\u0018\u001a\u00020\u00102\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0016\u0010\u001c\u001a\u00020\u001d2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u0015H\u0002J\u0012\u0010\u001e\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u001f\u001a\u00020 H\u0002J \u0010!\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u0012H\u0002J\u0018\u0010$\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020\u0010H\u0002J\u0006\u0010&\u001a\u00020'J\u0006\u0010(\u001a\u00020\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lcom/termux/am/AmSocketServer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "executor", "Ljava/util/concurrent/ExecutorService;", "isRunning", "Ljava/util/concurrent/atomic/AtomicBoolean;", "myUid", "", "serverSocket", "Landroid/net/LocalServerSocket;", "acceptLoop", "", "executeAmCommand", "Lcom/termux/am/AmSocketServer$AmResult;", "command", "", "executeBroadcast", "args", "", "executeForceStop", "executeStart", "executeStartService", "handleClient", "client", "Landroid/net/LocalSocket;", "parseIntent", "Landroid/content/Intent;", "readCommand", "input", "Ljava/io/InputStream;", "sendError", "exitCode", "message", "sendResponse", "result", "start", "", "stop", "AmResult", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes4.dex */
public final class AmSocketServer {
    private static final String SOCKET_ADDRESS_PREFIX = "\u0000";
    public static final String SOCKET_DIR = "/data/data/com.termux/files/apps/com.termux/termux-am";
    public static final String SOCKET_NAME = "am.sock";
    public static final String SOCKET_PATH = "/data/data/com.termux/files/apps/com.termux/termux-am/am.sock";
    private static final String TAG = "AmSocketServer";
    private final Context context;
    private ExecutorService executor;
    private final AtomicBoolean isRunning;
    private final int myUid;
    private LocalServerSocket serverSocket;

    public AmSocketServer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.isRunning = new AtomicBoolean(false);
        this.myUid = Process.myUid();
    }

    public final boolean start() throws IOException {
        if (this.isRunning.get()) {
            Log.w(TAG, "Server already running");
            return true;
        }
        try {
            File socketDir = new File(SOCKET_DIR);
            if (!socketDir.exists()) {
                socketDir.mkdirs();
                Log.i(TAG, "Created socket directory: /data/data/com.termux/files/apps/com.termux/termux-am");
            }
            File socketFile = new File(SOCKET_PATH);
            if (socketFile.exists()) {
                socketFile.delete();
                Log.i(TAG, "Removed old socket file");
            }
            this.serverSocket = new LocalServerSocket(SOCKET_PATH);
            this.executor = Executors.newCachedThreadPool();
            this.isRunning.set(true);
            ExecutorService executorService = this.executor;
            if (executorService != null) {
                executorService.execute(new Runnable() { // from class: com.termux.am.AmSocketServer$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AmSocketServer.start$lambda$0(this.f$0);
                    }
                });
            }
            Log.i(TAG, "Socket server started at /data/data/com.termux/files/apps/com.termux/termux-am/am.sock");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Failed to start socket server", e);
            stop();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void start$lambda$0(AmSocketServer this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.acceptLoop();
    }

    public final void stop() throws IOException {
        this.isRunning.set(false);
        try {
            LocalServerSocket localServerSocket = this.serverSocket;
            if (localServerSocket != null) {
                localServerSocket.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error closing server socket", e);
        }
        this.serverSocket = null;
        ExecutorService executorService = this.executor;
        if (executorService != null) {
            executorService.shutdownNow();
        }
        this.executor = null;
        try {
            new File(SOCKET_PATH).delete();
        } catch (Exception e2) {
        }
        Log.i(TAG, "Socket server stopped");
    }

    private final void acceptLoop() {
        final LocalSocket client;
        Log.i(TAG, "Accept loop started");
        while (this.isRunning.get()) {
            try {
                LocalServerSocket localServerSocket = this.serverSocket;
                client = localServerSocket != null ? localServerSocket.accept() : null;
            } catch (Exception e) {
                if (this.isRunning.get()) {
                    Log.e(TAG, "Error accepting client", e);
                }
            }
            if (client == null) {
                break;
            }
            ExecutorService executorService = this.executor;
            if (executorService != null) {
                executorService.execute(new Runnable() { // from class: com.termux.am.AmSocketServer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() throws IOException {
                        AmSocketServer.acceptLoop$lambda$1(this.f$0, client);
                    }
                });
            }
        }
        Log.i(TAG, "Accept loop ended");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void acceptLoop$lambda$1(AmSocketServer this$0, LocalSocket client) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(client, "$client");
        this$0.handleClient(client);
    }

    private final void handleClient(LocalSocket client) throws IOException {
        try {
            try {
                Credentials credentials = client.getPeerCredentials();
                int clientUid = credentials.getUid();
                int i = this.myUid;
                if (clientUid != i && clientUid != 0) {
                    Log.w(TAG, "Rejecting client with UID " + clientUid + " (expected " + i + " or 0)");
                    sendError(client, 1, "Permission denied: UID mismatch");
                    try {
                        return;
                    } catch (Exception e) {
                        return;
                    }
                }
                Log.d(TAG, "Accepted client with UID " + clientUid + ", PID " + credentials.getPid());
                InputStream inputStream = client.getInputStream();
                Intrinsics.checkNotNull(inputStream);
                String command = readCommand(inputStream);
                String str = command;
                if (str == null || StringsKt.isBlank(str)) {
                    sendError(client, 1, "Empty command");
                    try {
                        client.close();
                    } catch (Exception e2) {
                    }
                } else {
                    Log.i(TAG, "Received command: " + command);
                    AmResult result = executeAmCommand(command);
                    sendResponse(client, result);
                    try {
                        client.close();
                    } catch (Exception e3) {
                    }
                }
            } finally {
                try {
                    client.close();
                } catch (Exception e4) {
                }
            }
        } catch (Exception e5) {
            Log.e(TAG, "Error handling client", e5);
            try {
                sendError(client, 1, "Internal error: " + e5.getMessage());
            } catch (Exception e6) {
            }
            try {
                client.close();
            } catch (Exception e7) {
            }
        }
    }

    private final String readCommand(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            try {
                int n = input.read(buf);
                if (n <= 0) {
                    break;
                }
                buffer.write(buf, 0, n);
            } catch (Exception e) {
            }
        }
        String string = buffer.toString(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trim((CharSequence) string).toString();
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    private final AmResult executeAmCommand(String command) {
        List args = new Regex("\\s+").split(command, 0);
        if (args.isEmpty()) {
            return new AmResult(1, "", "No command specified");
        }
        String str = args.get(0);
        switch (str.hashCode()) {
            case -1618876223:
                if (str.equals("broadcast")) {
                    return executeBroadcast(CollectionsKt.drop(args, 1));
                }
                break;
            case 88586660:
                if (str.equals("force-stop")) {
                    return executeForceStop(CollectionsKt.drop(args, 1));
                }
                break;
            case 109757538:
                if (str.equals("start")) {
                    return executeStart(CollectionsKt.drop(args, 1));
                }
                break;
            case 185053203:
                if (str.equals("startservice")) {
                    return executeStartService(CollectionsKt.drop(args, 1));
                }
                break;
            case 1737589560:
                if (str.equals("--version")) {
                    return new AmResult(0, "0.9.0-mobilecli", "");
                }
                break;
        }
        return new AmResult(1, "", "Unknown command: " + ((Object) args.get(0)));
    }

    private final AmResult executeStart(List<String> args) {
        try {
            Intent intent = parseIntent(args);
            intent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            this.context.startActivity(intent);
            StringBuilder $this$executeStart_u24lambda_u245 = new StringBuilder();
            $this$executeStart_u24lambda_u245.append("Starting: Intent { ");
            String it = intent.getAction();
            if (it != null) {
                $this$executeStart_u24lambda_u245.append("act=" + it + " ");
            }
            Uri it2 = intent.getData();
            if (it2 != null) {
                $this$executeStart_u24lambda_u245.append("dat=" + it2 + " ");
            }
            ComponentName it3 = intent.getComponent();
            if (it3 != null) {
                $this$executeStart_u24lambda_u245.append("cmp=" + it3 + " ");
            }
            $this$executeStart_u24lambda_u245.append("}");
            String output = $this$executeStart_u24lambda_u245.toString();
            Intrinsics.checkNotNullExpressionValue(output, "toString(...)");
            return new AmResult(0, output, "");
        } catch (Exception e) {
            Log.e(TAG, "Failed to start activity", e);
            return new AmResult(1, "", "Error: " + e.getMessage());
        }
    }

    private final AmResult executeStartService(List<String> args) {
        try {
            Intent intent = parseIntent(args);
            this.context.startService(intent);
            return new AmResult(0, "Starting service: " + intent.getComponent(), "");
        } catch (Exception e) {
            Log.e(TAG, "Failed to start service", e);
            return new AmResult(1, "", "Error: " + e.getMessage());
        }
    }

    private final AmResult executeBroadcast(List<String> args) {
        try {
            Intent intent = parseIntent(args);
            this.context.sendBroadcast(intent);
            return new AmResult(0, "Broadcasting: " + intent.getAction(), "");
        } catch (Exception e) {
            Log.e(TAG, "Failed to send broadcast", e);
            return new AmResult(1, "", "Error: " + e.getMessage());
        }
    }

    private final AmResult executeForceStop(List<String> args) {
        return new AmResult(1, "", "force-stop requires system permissions");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final android.content.Intent parseIntent(java.util.List<java.lang.String> r13) {
        /*
            Method dump skipped, instructions count: 740
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.am.AmSocketServer.parseIntent(java.util.List):android.content.Intent");
    }

    private final void sendResponse(LocalSocket client, AmResult result) throws IOException {
        try {
            OutputStream output = client.getOutputStream();
            byte[] bytes = (result.getExitCode() + SOCKET_ADDRESS_PREFIX).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            output.write(bytes);
            byte[] bytes2 = (result.getStdout() + SOCKET_ADDRESS_PREFIX).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
            output.write(bytes2);
            byte[] bytes3 = (result.getStderr() + SOCKET_ADDRESS_PREFIX).getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
            output.write(bytes3);
            output.flush();
        } catch (Exception e) {
            Log.e(TAG, "Error sending response", e);
        }
    }

    private final void sendError(LocalSocket client, int exitCode, String message) throws IOException {
        sendResponse(client, new AmResult(exitCode, "", message));
    }

    /* compiled from: AmSocketServer.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0016"}, d2 = {"Lcom/termux/am/AmSocketServer$AmResult;", "", "exitCode", "", "stdout", "", "stderr", "(ILjava/lang/String;Ljava/lang/String;)V", "getExitCode", "()I", "getStderr", "()Ljava/lang/String;", "getStdout", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class AmResult {
        private final int exitCode;
        private final String stderr;
        private final String stdout;

        public static /* synthetic */ AmResult copy$default(AmResult amResult, int i, String str, String str2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                i = amResult.exitCode;
            }
            if ((i2 & 2) != 0) {
                str = amResult.stdout;
            }
            if ((i2 & 4) != 0) {
                str2 = amResult.stderr;
            }
            return amResult.copy(i, str, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final int getExitCode() {
            return this.exitCode;
        }

        /* renamed from: component2, reason: from getter */
        public final String getStdout() {
            return this.stdout;
        }

        /* renamed from: component3, reason: from getter */
        public final String getStderr() {
            return this.stderr;
        }

        public final AmResult copy(int exitCode, String stdout, String stderr) {
            Intrinsics.checkNotNullParameter(stdout, "stdout");
            Intrinsics.checkNotNullParameter(stderr, "stderr");
            return new AmResult(exitCode, stdout, stderr);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AmResult)) {
                return false;
            }
            AmResult amResult = (AmResult) other;
            return this.exitCode == amResult.exitCode && Intrinsics.areEqual(this.stdout, amResult.stdout) && Intrinsics.areEqual(this.stderr, amResult.stderr);
        }

        public int hashCode() {
            return (((Integer.hashCode(this.exitCode) * 31) + this.stdout.hashCode()) * 31) + this.stderr.hashCode();
        }

        public String toString() {
            return "AmResult(exitCode=" + this.exitCode + ", stdout=" + this.stdout + ", stderr=" + this.stderr + ")";
        }

        public AmResult(int exitCode, String stdout, String stderr) {
            Intrinsics.checkNotNullParameter(stdout, "stdout");
            Intrinsics.checkNotNullParameter(stderr, "stderr");
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        public final int getExitCode() {
            return this.exitCode;
        }

        public final String getStdout() {
            return this.stdout;
        }

        public final String getStderr() {
            return this.stderr;
        }
    }
}
