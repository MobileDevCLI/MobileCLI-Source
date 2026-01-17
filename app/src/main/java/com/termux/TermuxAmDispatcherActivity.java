package com.termux;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.termux.terminal.KeyHandler;
import kotlin.Metadata;

/* compiled from: TermuxAmDispatcherActivity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0014¨\u0006\n"}, d2 = {"Lcom/termux/TermuxAmDispatcherActivity;", "Landroid/app/Activity;", "()V", "buildIntentFromExtras", "Landroid/content/Intent;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class TermuxAmDispatcherActivity extends Activity {
    private static final int REQUEST_CODE = 12345;
    private static final String TAG = "TermuxAmDispatcher";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws PendingIntent.CanceledException {
        super.onCreate(savedInstanceState);
        try {
            Intent dispatchIntent = buildIntentFromExtras();
            if (dispatchIntent == null) {
                Log.e(TAG, "Failed to build intent from extras");
            } else {
                Log.i(TAG, "Dispatching via PendingIntent: action=" + dispatchIntent.getAction() + ", data=" + dispatchIntent.getData());
                dispatchIntent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, dispatchIntent, 201326592);
                pendingIntent.send();
                Log.i(TAG, "PendingIntent sent successfully");
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to dispatch intent", e);
        }
        finish();
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final android.content.Intent buildIntentFromExtras() {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.TermuxAmDispatcherActivity.buildIntentFromExtras():android.content.Intent");
    }
}
