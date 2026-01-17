package com.termux;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.termux.app.TermuxService;
import com.termux.terminal.TerminalSession;
import com.termux.view.TerminalView;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016J\u0012\u0010\b\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016¨\u0006\t"}, d2 = {"com/termux/MainActivity$serviceConnection$1", "Landroid/content/ServiceConnection;", "onServiceConnected", "", "name", "Landroid/content/ComponentName;", "binder", "Landroid/os/IBinder;", "onServiceDisconnected", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class MainActivity$serviceConnection$1 implements ServiceConnection {
    final /* synthetic */ MainActivity this$0;

    MainActivity$serviceConnection$1(MainActivity $receiver) {
        this.this$0 = $receiver;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Intrinsics.checkNotNull(binder, "null cannot be cast to non-null type com.termux.app.TermuxService.LocalBinder");
        TermuxService.LocalBinder localBinder = (TermuxService.LocalBinder) binder;
        this.this$0.termuxService = localBinder.getThis$0();
        TermuxService termuxService = this.this$0.termuxService;
        if (termuxService != null) {
            termuxService.setSessionClient(this.this$0);
        }
        this.this$0.serviceBound = true;
        TermuxService service = this.this$0.termuxService;
        if (service != null) {
            final MainActivity mainActivity = this.this$0;
            List serviceSessions = service.getSessions();
            if (!(true ^ serviceSessions.isEmpty())) {
                if (mainActivity.bootstrapReadyPendingSession) {
                    Log.i("MainActivity", "Service connected, no sessions to restore, creating new session");
                    mainActivity.createSession();
                }
            } else {
                Log.i("MainActivity", "Restoring " + serviceSessions.size() + " sessions from service");
                mainActivity.sessions.clear();
                mainActivity.sessions.addAll(serviceSessions);
                mainActivity.currentSessionIndex = 0;
                TerminalView terminalView = mainActivity.terminalView;
                TerminalView terminalView2 = null;
                if (terminalView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView = null;
                }
                terminalView.attachSession((TerminalSession) mainActivity.sessions.get(0));
                mainActivity.updateSessionTabs();
                TerminalView terminalView3 = mainActivity.terminalView;
                if (terminalView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                } else {
                    terminalView2 = terminalView3;
                }
                terminalView2.postDelayed(new Runnable() { // from class: com.termux.MainActivity$serviceConnection$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity$serviceConnection$1.onServiceConnected$lambda$2$lambda$0(mainActivity);
                    }
                }, 500L);
                mainActivity.runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$serviceConnection$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity$serviceConnection$1.onServiceConnected$lambda$2$lambda$1(mainActivity);
                    }
                });
            }
        }
        this.this$0.bootstrapReadyPendingSession = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onServiceConnected$lambda$2$lambda$0(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.animate().alpha(1.0f).setDuration(300L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onServiceConnected$lambda$2$lambda$1(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0, "Restored " + this$0.sessions.size() + " session(s)", 0).show();
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
        this.this$0.termuxService = null;
        this.this$0.serviceBound = false;
    }
}
