package com.termux.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;

/* compiled from: BootReceiver.kt */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\r"}, d2 = {"Lcom/termux/boot/BootReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "executeScript", "", "context", "Landroid/content/Context;", "script", "Ljava/io/File;", "onReceive", "intent", "Landroid/content/Intent;", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes6.dex */
public final class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    /* JADX WARN: Removed duplicated region for block: B:26:0x0091  */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onReceive(android.content.Context r18, android.content.Intent r19) throws java.lang.InterruptedException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 259
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.boot.BootReceiver.onReceive(android.content.Context, android.content.Intent):void");
    }

    private final void executeScript(Context context, File script) throws InterruptedException, IOException {
        try {
            Log.i(TAG, "Executing boot script: " + script.getName());
            File homeDir = new File(context.getFilesDir(), "home");
            File prefixDir = new File(context.getFilesDir(), "usr");
            String bashPath = new File(prefixDir, "bin/bash").getAbsolutePath();
            String[] env = {"HOME=" + homeDir.getAbsolutePath(), "PREFIX=" + prefixDir.getAbsolutePath(), "PATH=" + prefixDir.getAbsolutePath() + "/bin:/system/bin:/system/xbin", "LD_LIBRARY_PATH=" + prefixDir.getAbsolutePath() + "/lib", "TMPDIR=" + prefixDir.getAbsolutePath() + "/tmp", "TERM=xterm-256color", "SHELL=" + bashPath};
            Process process = Runtime.getRuntime().exec(new String[]{bashPath, script.getAbsolutePath()}, env, homeDir);
            boolean completed = process.waitFor(10L, TimeUnit.SECONDS);
            if (completed) {
                int exitCode = process.exitValue();
                Log.i(TAG, "Boot script " + script.getName() + " completed with exit code " + exitCode);
            } else {
                Log.w(TAG, "Boot script " + script.getName() + " timed out, destroying...");
                process.destroyForcibly();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error executing boot script " + script.getName(), e);
        }
    }
}
