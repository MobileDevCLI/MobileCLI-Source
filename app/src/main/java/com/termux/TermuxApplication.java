package com.termux;

import android.app.Application;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TermuxApplication.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0002J\b\u0010\u0005\u001a\u00020\u0004H\u0016J\b\u0010\u0006\u001a\u00020\u0004H\u0016J\b\u0010\u0007\u001a\u00020\u0004H\u0016J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0002¨\u0006\r"}, d2 = {"Lcom/termux/TermuxApplication;", "Landroid/app/Application;", "()V", "initializeDirectories", "", "onCreate", "onLowMemory", "onTerminate", "onTrimMemory", "level", "", "setupCrashHandler", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class TermuxApplication extends Application {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "TermuxApplication";
    private static volatile TermuxApplication instance;

    /* compiled from: TermuxApplication.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lcom/termux/TermuxApplication$Companion;", "", "()V", "TAG", "", "instance", "Lcom/termux/TermuxApplication;", "getInstance", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TermuxApplication getInstance() {
            return TermuxApplication.instance;
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
        Log.i(TAG, "MobileCLI Application starting");
        setupCrashHandler();
        initializeDirectories();
        Log.i(TAG, "MobileCLI Application initialized");
    }

    private final void setupCrashHandler() {
        final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: com.termux.TermuxApplication$$ExternalSyntheticLambda0
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                TermuxApplication.setupCrashHandler$lambda$0(this.f$0, defaultHandler, thread, th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupCrashHandler$lambda$0(TermuxApplication this$0, Thread.UncaughtExceptionHandler $defaultHandler, Thread thread, Throwable throwable) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Log.e(TAG, "Uncaught exception in thread " + thread.getName(), throwable);
        try {
            File crashFile = new File(this$0.getFilesDir(), "crash.log");
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date());
            FilesKt.appendText$default(crashFile, "=== Crash at " + timestamp + " ===\n", null, 2, null);
            FilesKt.appendText$default(crashFile, "Thread: " + thread.getName() + "\n", null, 2, null);
            FilesKt.appendText$default(crashFile, "Exception: " + throwable.getMessage() + "\n", null, 2, null);
            Intrinsics.checkNotNull(throwable);
            FilesKt.appendText$default(crashFile, ExceptionsKt.stackTraceToString(throwable), null, 2, null);
            FilesKt.appendText$default(crashFile, "\n\n", null, 2, null);
        } catch (Exception e) {
        }
        if ($defaultHandler != null) {
            $defaultHandler.uncaughtException(thread, throwable);
        }
    }

    private final void initializeDirectories() {
        try {
            File files = getFilesDir();
            File usr = new File(files, "usr");
            File home = new File(files, "home");
            File termuxDir = new File(home, ".termux");
            File bootDir = new File(termuxDir, "boot");
            usr.mkdirs();
            home.mkdirs();
            termuxDir.mkdirs();
            bootDir.mkdirs();
            File propertiesFile = new File(termuxDir, "termux.properties");
            if (!propertiesFile.exists()) {
                FilesKt.writeText$default(propertiesFile, "\n# MobileCLI Terminal Properties\n# Documentation: https://wiki.termux.com/wiki/Terminal_Settings\n\n### Keyboard Settings ###\n\n# Extra keys configuration (JSON format)\n# Default keys: ESC, CTRL, ALT, TAB, -, /, |, HOME, UP, END, PGUP\n# extra-keys = [['ESC','/','-','HOME','UP','END','PGUP'],['TAB','CTRL','ALT','LEFT','DOWN','RIGHT','PGDN']]\n\n# Back key behavior: \"back\" (default) or \"escape\"\n# back-key = back\n\n### Appearance ###\n\n# Use fullscreen mode\n# fullscreen = false\n\n# Hide soft keyboard on startup\n# hide-soft-keyboard-on-startup = false\n\n### Terminal Settings ###\n\n# Terminal transcript rows (scrollback buffer)\n# terminal-transcript-rows = 2000\n\n# Cursor blink rate in ms (0 = no blink)\n# terminal-cursor-blink-rate = 500\n\n# Cursor style: \"block\", \"underline\", or \"bar\"\n# terminal-cursor-style = block\n\n### Bell Settings ###\n\n# Bell character behavior: \"vibrate\", \"beep\", \"ignore\"\n# bell-character = vibrate\n\n### URL/External App Settings ###\n\n# Allow external apps to open URLs and files\n# This must be true for Claude Code OAuth to work!\nallow-external-apps = true\n\n### Session Settings ###\n\n# Default working directory\n# default-working-directory = /data/data/com.termux/files/home\n\n", null, 2, null);
                Log.i(TAG, "Created default termux.properties");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize directories", e);
        }
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        instance = null;
        Log.i(TAG, "MobileCLI Application terminated");
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onLowMemory() {
        super.onLowMemory();
        Log.w(TAG, "Low memory warning");
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level >= 60) {
            Log.w(TAG, "Trim memory level: " + level);
        }
    }
}
