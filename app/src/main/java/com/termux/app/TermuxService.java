package com.termux.app;

import android.R;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PowerManager;
import android.system.ErrnoException;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import com.termux.MainActivity;
import com.termux.am.AmSocketServer;
import com.termux.terminal.KeyHandler;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;

/* compiled from: TermuxService.kt */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 N2\u00020\u0001:\u0002NOB\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u001fJ\b\u0010-\u001a\u00020.H\u0002J\b\u0010/\u001a\u00020*H\u0002J\u0016\u00100\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020103H\u0002J\u0010\u00104\u001a\u0002012\u0006\u00105\u001a\u000201H\u0002J\u0016\u00106\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020103H\u0002J\u0016\u00107\u001a\u0002012\f\u00102\u001a\b\u0012\u0004\u0012\u00020103H\u0002J\u0006\u00108\u001a\u000209J\f\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001f03J\u0006\u0010\u0016\u001a\u00020\u0017J\u0012\u0010;\u001a\u00020<2\b\u0010=\u001a\u0004\u0018\u00010>H\u0016J\b\u0010?\u001a\u00020*H\u0016J\b\u0010@\u001a\u00020*H\u0016J\"\u0010A\u001a\u0002092\b\u0010=\u001a\u0004\u0018\u00010>2\u0006\u0010B\u001a\u0002092\u0006\u0010C\u001a\u000209H\u0016J\u0016\u0010D\u001a\u00020>2\f\u00102\u001a\b\u0012\u0004\u0012\u00020103H\u0002J\u0006\u0010E\u001a\u00020*J\u000e\u0010F\u001a\u00020*2\u0006\u0010,\u001a\u00020\u001fJ\u000e\u0010G\u001a\u00020*2\u0006\u0010H\u001a\u00020\u001cJ\b\u0010I\u001a\u00020*H\u0002J\b\u0010J\u001a\u00020*H\u0002J\b\u0010K\u001a\u00020*H\u0002J\b\u0010L\u001a\u00020*H\u0002J\b\u0010M\u001a\u00020*H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00060\u0006R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\f\u001a\u0004\b\u0014\u0010\nR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0018\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\f\u001a\u0004\b\u0019\u0010\nR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010 \u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\f\u001a\u0004\b!\u0010\nR\u0014\u0010#\u001a\b\u0018\u00010$R\u00020%X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010&\u001a\b\u0018\u00010'R\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006P"}, d2 = {"Lcom/termux/app/TermuxService;", "Landroid/app/Service;", "()V", "amSocketServer", "Lcom/termux/am/AmSocketServer;", "binder", "Lcom/termux/app/TermuxService$LocalBinder;", "commandFile", "Ljava/io/File;", "getCommandFile", "()Ljava/io/File;", "commandFile$delegate", "Lkotlin/Lazy;", "commandHandler", "Landroid/os/Handler;", "commandPollInterval", "", "commandWatcherRunnable", "Ljava/lang/Runnable;", "homeDir", "getHomeDir", "homeDir$delegate", "isWakeLockHeld", "", "resultFile", "getResultFile", "resultFile$delegate", "sessionClient", "Lcom/termux/terminal/TerminalSessionClient;", "sessions", "", "Lcom/termux/terminal/TerminalSession;", "termuxDir", "getTermuxDir", "termuxDir$delegate", "wakeLock", "Landroid/os/PowerManager$WakeLock;", "Landroid/os/PowerManager;", "wifiLock", "Landroid/net/wifi/WifiManager$WifiLock;", "Landroid/net/wifi/WifiManager;", "acquireWakeLock", "", "addSession", "session", "buildNotification", "Landroid/app/Notification;", "createNotificationChannel", "executeAmBroadcast", "", "args", "", "executeAmCommand", "command", "executeAmStart", "executeAmStartService", "getSessionCount", "", "getSessions", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "parseIntent", "releaseWakeLock", "removeSession", "setSessionClient", "client", "startAmSocketServer", "startCommandWatcher", "stopAmSocketServer", "stopCommandWatcher", "updateNotification", "Companion", "LocalBinder", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes5.dex */
public final class TermuxService extends Service {
    public static final String ACTION_SERVICE_EXECUTE = "com.termux.service_execute";
    public static final String ACTION_STOP_SERVICE = "com.termux.service_stop";
    public static final String ACTION_WAKE_LOCK = "com.termux.service_wake_lock";
    public static final String ACTION_WAKE_UNLOCK = "com.termux.service_wake_unlock";
    private static final String CHANNEL_ID = "termux_service_channel";
    private static final int NOTIFICATION_ID = 1337;
    private static final String TAG = "TermuxService";
    private AmSocketServer amSocketServer;
    private Runnable commandWatcherRunnable;
    private boolean isWakeLockHeld;
    private TerminalSessionClient sessionClient;
    private PowerManager.WakeLock wakeLock;
    private WifiManager.WifiLock wifiLock;
    private final LocalBinder binder = new LocalBinder();
    private final List<TerminalSession> sessions = new ArrayList();
    private final Handler commandHandler = new Handler(Looper.getMainLooper());
    private final long commandPollInterval = 200;

    /* renamed from: homeDir$delegate, reason: from kotlin metadata */
    private final Lazy homeDir = LazyKt.lazy(new Function0<File>() { // from class: com.termux.app.TermuxService$homeDir$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return new File(this.this$0.getFilesDir(), "home");
        }
    });

    /* renamed from: termuxDir$delegate, reason: from kotlin metadata */
    private final Lazy termuxDir = LazyKt.lazy(new Function0<File>() { // from class: com.termux.app.TermuxService$termuxDir$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return new File(this.this$0.getHomeDir(), ".termux");
        }
    });

    /* renamed from: commandFile$delegate, reason: from kotlin metadata */
    private final Lazy commandFile = LazyKt.lazy(new Function0<File>() { // from class: com.termux.app.TermuxService$commandFile$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return new File(this.this$0.getTermuxDir(), "am_command");
        }
    });

    /* renamed from: resultFile$delegate, reason: from kotlin metadata */
    private final Lazy resultFile = LazyKt.lazy(new Function0<File>() { // from class: com.termux.app.TermuxService$resultFile$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            return new File(this.this$0.getTermuxDir(), "am_result");
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public final File getHomeDir() {
        return (File) this.homeDir.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getTermuxDir() {
        return (File) this.termuxDir.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getCommandFile() {
        return (File) this.commandFile.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final File getResultFile() {
        return (File) this.resultFile.getValue();
    }

    /* compiled from: TermuxService.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lcom/termux/app/TermuxService$LocalBinder;", "Landroid/os/Binder;", "(Lcom/termux/app/TermuxService;)V", NotificationCompat.CATEGORY_SERVICE, "Lcom/termux/app/TermuxService;", "getService", "()Lcom/termux/app/TermuxService;", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public final class LocalBinder extends Binder {
        public LocalBinder() {
        }

        /* renamed from: getService, reason: from getter */
        public final TermuxService getThis$0() {
            return TermuxService.this;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "TermuxService created");
        createNotificationChannel();
        startForeground(NOTIFICATION_ID, buildNotification());
        startAmSocketServer();
        startCommandWatcher();
    }

    private final void startAmSocketServer() {
        try {
            AmSocketServer $this$startAmSocketServer_u24lambda_u240 = new AmSocketServer(this);
            if ($this$startAmSocketServer_u24lambda_u240.start()) {
                Log.i(TAG, "Am socket server started at /data/data/com.termux/files/apps/com.termux/termux-am/am.sock");
            } else {
                Log.e(TAG, "Failed to start Am socket server");
            }
            this.amSocketServer = $this$startAmSocketServer_u24lambda_u240;
        } catch (Exception e) {
            Log.e(TAG, "Error starting Am socket server", e);
        }
    }

    private final void stopAmSocketServer() {
        try {
            AmSocketServer amSocketServer = this.amSocketServer;
            if (amSocketServer != null) {
                amSocketServer.stop();
            }
            this.amSocketServer = null;
            Log.i(TAG, "Am socket server stopped");
        } catch (Exception e) {
            Log.e(TAG, "Error stopping Am socket server", e);
        }
    }

    private final void startCommandWatcher() {
        getTermuxDir().mkdirs();
        Runnable runnable = new Runnable() { // from class: com.termux.app.TermuxService.startCommandWatcher.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (TermuxService.this.getCommandFile().exists()) {
                        boolean z = true;
                        String command = StringsKt.trim((CharSequence) FilesKt.readText$default(TermuxService.this.getCommandFile(), null, 1, null)).toString();
                        TermuxService.this.getCommandFile().delete();
                        if (command.length() <= 0) {
                            z = false;
                        }
                        if (z) {
                            Log.i(TermuxService.TAG, "Received am command: " + command);
                            String result = TermuxService.this.executeAmCommand(command);
                            FilesKt.writeText$default(TermuxService.this.getResultFile(), result, null, 2, null);
                            Log.i(TermuxService.TAG, "Command result written");
                        }
                    }
                } catch (Exception e) {
                    Log.e(TermuxService.TAG, "Error processing command", e);
                    try {
                        FilesKt.writeText$default(TermuxService.this.getResultFile(), "1\nError: " + e.getMessage() + "\n", null, 2, null);
                    } catch (Exception e2) {
                    }
                }
                TermuxService.this.commandHandler.postDelayed(this, TermuxService.this.commandPollInterval);
            }
        };
        this.commandWatcherRunnable = runnable;
        Handler handler = this.commandHandler;
        Intrinsics.checkNotNull(runnable);
        handler.postDelayed(runnable, this.commandPollInterval);
        Log.i(TAG, "Command watcher started");
    }

    private final void stopCommandWatcher() {
        Runnable it = this.commandWatcherRunnable;
        if (it != null) {
            this.commandHandler.removeCallbacks(it);
        }
        this.commandWatcherRunnable = null;
        Log.i(TAG, "Command watcher stopped");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final String executeAmCommand(String command) {
        String strExecuteAmBroadcast;
        List args = new Regex("\\s+").split(command, 0);
        if (args.isEmpty()) {
            return "1\n\nNo command specified";
        }
        try {
            String str = args.get(0);
            switch (str.hashCode()) {
                case -1618876223:
                    if (!str.equals("broadcast")) {
                        strExecuteAmBroadcast = "1\n\nUnknown command: " + ((Object) args.get(0));
                        break;
                    } else {
                        strExecuteAmBroadcast = executeAmBroadcast(CollectionsKt.drop(args, 1));
                        break;
                    }
                case 109757538:
                    if (!str.equals("start")) {
                        strExecuteAmBroadcast = "1\n\nUnknown command: " + ((Object) args.get(0));
                        break;
                    } else {
                        strExecuteAmBroadcast = executeAmStart(CollectionsKt.drop(args, 1));
                        break;
                    }
                case 185053203:
                    if (!str.equals("startservice")) {
                        strExecuteAmBroadcast = "1\n\nUnknown command: " + ((Object) args.get(0));
                        break;
                    } else {
                        strExecuteAmBroadcast = executeAmStartService(CollectionsKt.drop(args, 1));
                        break;
                    }
                case 1737589560:
                    if (!str.equals("--version")) {
                        strExecuteAmBroadcast = "1\n\nUnknown command: " + ((Object) args.get(0));
                        break;
                    } else {
                        strExecuteAmBroadcast = "0\n0.9.0-mobilecli-v54\n";
                        break;
                    }
                default:
                    strExecuteAmBroadcast = "1\n\nUnknown command: " + ((Object) args.get(0));
                    break;
            }
            return strExecuteAmBroadcast;
        } catch (Exception e) {
            return "1\n\nError: " + e.getMessage();
        }
    }

    private final String executeAmStart(List<String> args) throws PendingIntent.CanceledException {
        Intent intent = parseIntent(args);
        intent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 201326592);
        pendingIntent.send();
        StringBuilder $this$executeAmStart_u24lambda_u245 = new StringBuilder();
        $this$executeAmStart_u24lambda_u245.append("Starting: Intent { ");
        String it = intent.getAction();
        if (it != null) {
            $this$executeAmStart_u24lambda_u245.append("act=" + it + " ");
        }
        Uri it2 = intent.getData();
        if (it2 != null) {
            $this$executeAmStart_u24lambda_u245.append("dat=" + it2 + " ");
        }
        ComponentName it3 = intent.getComponent();
        if (it3 != null) {
            $this$executeAmStart_u24lambda_u245.append("cmp=" + it3 + " ");
        }
        $this$executeAmStart_u24lambda_u245.append("}");
        String output = $this$executeAmStart_u24lambda_u245.toString();
        Intrinsics.checkNotNullExpressionValue(output, "toString(...)");
        return "0\n" + output + "\n";
    }

    private final String executeAmStartService(List<String> args) {
        Intent intent = parseIntent(args);
        startService(intent);
        return "0\nStarting service: " + intent.getComponent() + "\n";
    }

    private final String executeAmBroadcast(List<String> args) {
        Intent intent = parseIntent(args);
        sendBroadcast(intent);
        return "0\nBroadcasting: " + intent.getAction() + "\n";
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final android.content.Intent parseIntent(java.util.List<java.lang.String> r13) {
        /*
            Method dump skipped, instructions count: 602
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.app.TermuxService.parseIntent(java.util.List):android.content.Intent");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : null;
        Log.i(TAG, "onStartCommand: action=" + action);
        if (action != null) {
            switch (action.hashCode()) {
                case -1725877538:
                    if (action.equals(ACTION_STOP_SERVICE)) {
                        releaseWakeLock();
                        stopForeground(true);
                        stopSelf();
                        break;
                    }
                    break;
                case -109047837:
                    if (action.equals(ACTION_WAKE_UNLOCK)) {
                        releaseWakeLock();
                        updateNotification();
                        if (this.sessions.isEmpty() && !this.isWakeLockHeld) {
                            stopSelf();
                            break;
                        }
                    }
                    break;
                case 626457:
                    if (action.equals(ACTION_SERVICE_EXECUTE)) {
                        Log.i(TAG, "Execute command requested");
                        break;
                    }
                    break;
                case 2064421258:
                    if (action.equals(ACTION_WAKE_LOCK)) {
                        acquireWakeLock();
                        updateNotification();
                        break;
                    }
                    break;
            }
        }
        return 1;
    }

    @Override // android.app.Service
    public void onDestroy() throws ErrnoException {
        Log.i(TAG, "TermuxService destroyed");
        stopCommandWatcher();
        stopAmSocketServer();
        releaseWakeLock();
        Iterable $this$forEach$iv = this.sessions;
        for (Object element$iv : $this$forEach$iv) {
            TerminalSession it = (TerminalSession) element$iv;
            it.finishIfRunning();
        }
        this.sessions.clear();
        super.onDestroy();
    }

    public final List<TerminalSession> getSessions() {
        return CollectionsKt.toList(this.sessions);
    }

    public final int getSessionCount() {
        return this.sessions.size();
    }

    public final void setSessionClient(TerminalSessionClient client) {
        Intrinsics.checkNotNullParameter(client, "client");
        this.sessionClient = client;
    }

    public final void addSession(TerminalSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.sessions.add(session);
        updateNotification();
        Log.i(TAG, "Session added, total: " + this.sessions.size());
    }

    public final void removeSession(TerminalSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.sessions.remove(session);
        updateNotification();
        Log.i(TAG, "Session removed, remaining: " + this.sessions.size());
        if (this.sessions.isEmpty() && !this.isWakeLockHeld) {
            Log.i(TAG, "No sessions left, stopping service");
            stopForeground(true);
            stopSelf();
        }
    }

    public final void acquireWakeLock() {
        if (this.isWakeLockHeld) {
            Log.i(TAG, "Wake lock already held");
            return;
        }
        try {
            Object systemService = getSystemService("power");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            PowerManager powerManager = (PowerManager) systemService;
            PowerManager.WakeLock $this$acquireWakeLock_u24lambda_u247 = powerManager.newWakeLock(1, "MobileCLI::TermuxWakeLock");
            $this$acquireWakeLock_u24lambda_u247.setReferenceCounted(false);
            $this$acquireWakeLock_u24lambda_u247.acquire();
            this.wakeLock = $this$acquireWakeLock_u24lambda_u247;
            Object systemService2 = getApplicationContext().getSystemService("wifi");
            Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.net.wifi.WifiManager");
            WifiManager wifiManager = (WifiManager) systemService2;
            WifiManager.WifiLock $this$acquireWakeLock_u24lambda_u248 = wifiManager.createWifiLock(3, "MobileCLI::TermuxWifiLock");
            $this$acquireWakeLock_u24lambda_u248.setReferenceCounted(false);
            $this$acquireWakeLock_u24lambda_u248.acquire();
            this.wifiLock = $this$acquireWakeLock_u24lambda_u248;
            this.isWakeLockHeld = true;
            Log.i(TAG, "Wake lock acquired - CPU and WiFi will stay awake");
        } catch (Exception e) {
            Log.e(TAG, "Failed to acquire wake lock", e);
        }
    }

    public final void releaseWakeLock() {
        if (!this.isWakeLockHeld) {
            Log.i(TAG, "No wake lock to release");
            return;
        }
        try {
            PowerManager.WakeLock it = this.wakeLock;
            if (it != null && it.isHeld()) {
                it.release();
            }
            this.wakeLock = null;
            WifiManager.WifiLock it2 = this.wifiLock;
            if (it2 != null && it2.isHeld()) {
                it2.release();
            }
            this.wifiLock = null;
            this.isWakeLockHeld = false;
            Log.i(TAG, "Wake lock released - device can sleep");
        } catch (Exception e) {
            Log.e(TAG, "Failed to release wake lock", e);
        }
    }

    /* renamed from: isWakeLockHeld, reason: from getter */
    public final boolean getIsWakeLockHeld() {
        return this.isWakeLockHeld;
    }

    private final void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Terminal Service", 2);
            channel.setDescription("Keeps terminal sessions running in background");
            channel.setShowBadge(false);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private final Notification buildNotification() {
        String sessionInfo;
        Intent openIntent = new Intent(this, (Class<?>) MainActivity.class);
        openIntent.setFlags(805306368);
        PendingIntent openPendingIntent = PendingIntent.getActivity(this, 0, openIntent, 201326592);
        Intent stopIntent = new Intent(this, (Class<?>) TermuxService.class);
        stopIntent.setAction(ACTION_STOP_SERVICE);
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, 201326592);
        Intent wakeLockIntent = new Intent(this, (Class<?>) TermuxService.class);
        wakeLockIntent.setAction(this.isWakeLockHeld ? ACTION_WAKE_UNLOCK : ACTION_WAKE_LOCK);
        PendingIntent wakeLockPendingIntent = PendingIntent.getService(this, 1, wakeLockIntent, 201326592);
        if (this.sessions.isEmpty()) {
            sessionInfo = "No active sessions";
        } else {
            sessionInfo = this.sessions.size() == 1 ? "1 session" : this.sessions.size() + " sessions";
        }
        String wakeLockInfo = this.isWakeLockHeld ? " • Wake lock held" : "";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.ic_menu_manage).setContentTitle("MobileCLI").setContentText(sessionInfo + wakeLockInfo).setContentIntent(openPendingIntent).setOngoing(true).setPriority(this.isWakeLockHeld ? 1 : -1).addAction(R.drawable.ic_menu_close_clear_cancel, "Exit", stopPendingIntent).addAction(R.drawable.ic_lock_lock, this.isWakeLockHeld ? "Release lock" : "Acquire lock", wakeLockPendingIntent);
        Intrinsics.checkNotNullExpressionValue(builder, "addAction(...)");
        Notification notificationBuild = builder.build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        return notificationBuild;
    }

    private final void updateNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        notificationManager.notify(NOTIFICATION_ID, buildNotification());
    }
}
