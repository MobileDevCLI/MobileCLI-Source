package com.termux.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: RunCommandService.kt */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002¢\u0006\u0002\u0010\u0006J3\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0002¢\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\"\u0010\u0013\u001a\u00020\u00142\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0014H\u0016¨\u0006\u0018"}, d2 = {"Lcom/termux/app/RunCommandService;", "Landroid/app/Service;", "()V", "buildEnvironment", "", "", "()[Ljava/lang/String;", "executeCommand", "", "commandPath", "arguments", "workdir", "background", "", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Z)V", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onStartCommand", "", "flags", "startId", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes5.dex */
public final class RunCommandService extends Service {
    public static final String EXTRA_ARGUMENTS = "com.termux.RUN_COMMAND_ARGUMENTS";
    public static final String EXTRA_BACKGROUND = "com.termux.RUN_COMMAND_BACKGROUND";
    public static final String EXTRA_COMMAND_PATH = "com.termux.RUN_COMMAND_PATH";
    public static final String EXTRA_SESSION_ACTION = "com.termux.RUN_COMMAND_SESSION_ACTION";
    public static final String EXTRA_WORKDIR = "com.termux.RUN_COMMAND_WORKDIR";
    public static final int RESULT_CODE_FAILED = 1;
    public static final int RESULT_CODE_OK = 0;
    private static final String TAG = "RunCommandService";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, final int startId) {
        final String workdir;
        if (intent == null) {
            Log.e(TAG, "Received null intent");
            stopSelf(startId);
            return 2;
        }
        String action = intent.getAction();
        if (!Intrinsics.areEqual(action, "com.termux.RUN_COMMAND")) {
            Log.e(TAG, "Unknown action: " + action);
            stopSelf(startId);
            return 2;
        }
        final String commandPath = intent.getStringExtra(EXTRA_COMMAND_PATH);
        String str = commandPath;
        if (str == null || str.length() == 0) {
            Log.e(TAG, "No command path provided");
            stopSelf(startId);
            return 2;
        }
        String[] stringArrayExtra = intent.getStringArrayExtra(EXTRA_ARGUMENTS);
        if (stringArrayExtra == null) {
            stringArrayExtra = new String[0];
        }
        final String[] arguments = stringArrayExtra;
        String stringExtra = intent.getStringExtra(EXTRA_WORKDIR);
        if (stringExtra != null) {
            workdir = stringExtra;
        } else {
            workdir = "/data/data/com.termux/files/home";
        }
        final boolean background = intent.getBooleanExtra(EXTRA_BACKGROUND, false);
        Log.i(TAG, "Executing command: " + commandPath + " with " + arguments.length + " arguments");
        new Thread(new Runnable() { // from class: com.termux.app.RunCommandService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException, IOException {
                RunCommandService.onStartCommand$lambda$0(this.f$0, commandPath, arguments, workdir, background, startId);
            }
        }).start();
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onStartCommand$lambda$0(RunCommandService this$0, String $commandPath, String[] arguments, String workdir, boolean $background, int $startId) throws InterruptedException, IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(arguments, "$arguments");
        Intrinsics.checkNotNullParameter(workdir, "$workdir");
        this$0.executeCommand($commandPath, arguments, workdir, $background);
        this$0.stopSelf($startId);
    }

    private final void executeCommand(String commandPath, String[] arguments, String workdir, boolean background) throws InterruptedException, IOException {
        File workDir;
        try {
            File commandFile = new File(commandPath);
            if (!commandFile.exists()) {
                Log.e(TAG, "Command file does not exist: " + commandPath);
                return;
            }
            if (!commandFile.canExecute()) {
                Log.e(TAG, "Command file is not executable: " + commandPath);
                return;
            }
            try {
                workDir = new File(workdir);
                if (!workDir.exists()) {
                    workDir.mkdirs();
                }
            } catch (Exception e) {
                e = e;
            }
            try {
                String[] cmdArray = (String[]) ArraysKt.plus((Object[]) new String[]{commandPath}, (Object[]) arguments);
                String[] env = buildEnvironment();
                Process process = Runtime.getRuntime().exec(cmdArray, env, workDir);
                if (!background) {
                    int exitCode = process.waitFor();
                    Log.i(TAG, "Command completed with exit code: " + exitCode);
                    InputStream inputStream = process.getInputStream();
                    Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                    Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
                    String output = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                    InputStream errorStream = process.getErrorStream();
                    Intrinsics.checkNotNullExpressionValue(errorStream, "getErrorStream(...)");
                    Reader inputStreamReader2 = new InputStreamReader(errorStream, Charsets.UTF_8);
                    String error = TextStreamsKt.readText(inputStreamReader2 instanceof BufferedReader ? (BufferedReader) inputStreamReader2 : new BufferedReader(inputStreamReader2, 8192));
                    boolean z = true;
                    if (output.length() > 0) {
                        Log.d(TAG, "Output: " + output);
                    }
                    if (error.length() <= 0) {
                        z = false;
                    }
                    if (z) {
                        Log.w(TAG, "Error: " + error);
                        return;
                    }
                    return;
                }
                Log.i(TAG, "Command started in background");
            } catch (Exception e2) {
                e = e2;
                Log.e(TAG, "Failed to execute command", e);
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    private final String[] buildEnvironment() {
        File filesDir = getApplicationContext().getFilesDir();
        File prefixDir = new File(filesDir, "usr");
        File homeDir = new File(filesDir, "home");
        File binDir = new File(prefixDir, "bin");
        File libDir = new File(prefixDir, "lib");
        File tmpDir = new File(prefixDir, "tmp");
        return new String[]{"HOME=" + homeDir.getAbsolutePath(), "PREFIX=" + prefixDir.getAbsolutePath(), "PATH=" + binDir.getAbsolutePath() + ":/system/bin:/system/xbin", "LD_LIBRARY_PATH=" + libDir.getAbsolutePath(), "TMPDIR=" + tmpDir.getAbsolutePath(), "TERM=xterm-256color", "LANG=en_US.UTF-8", "SHELL=" + binDir.getAbsolutePath() + "/bash"};
    }
}
