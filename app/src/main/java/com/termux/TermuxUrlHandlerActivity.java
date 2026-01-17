package com.termux;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.termux.terminal.KeyHandler;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: TermuxUrlHandlerActivity.kt */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014J\u0012\u0010\n\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\f"}, d2 = {"Lcom/termux/TermuxUrlHandlerActivity;", "Landroid/app/Activity;", "()V", "handleIntent", "", "intent", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class TermuxUrlHandlerActivity extends Activity {
    public static final String EXTRA_CONTENT_TYPE = "content_type";
    public static final String EXTRA_URL = "url";
    private static final String TAG = "TermuxUrlHandler";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TermuxUrlHandlerActivity started!");
        Log.d(TAG, "Intent: " + getIntent());
        Log.d(TAG, "Intent extras: " + getIntent().getExtras());
        try {
            Intent intent = getIntent();
            Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
            handleIntent(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error handling URL intent", e);
            Toast.makeText(this, "Error opening URL: " + e.getMessage(), 1).show();
        }
        finish();
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            try {
                handleIntent(intent);
                Unit unit = Unit.INSTANCE;
            } catch (Exception e) {
                Integer.valueOf(Log.e(TAG, "Error handling URL intent", e));
            }
        }
        finish();
    }

    private final void handleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_URL);
        Log.d(TAG, "URL from EXTRA_URL: " + ((Object) stringExtra));
        if (stringExtra == null) {
            stringExtra = intent.getDataString();
            Log.d(TAG, "URL from dataString: " + ((Object) stringExtra));
        }
        if (stringExtra == null) {
            stringExtra = intent.getStringExtra("android.intent.extra.TEXT");
            Log.d(TAG, "URL from EXTRA_TEXT: " + ((Object) stringExtra));
        }
        String str = stringExtra;
        if (str == null || StringsKt.isBlank(str)) {
            Log.w(TAG, "No URL provided in intent");
            Toast.makeText(this, "No URL provided", 0).show();
            return;
        }
        Log.d(TAG, "Opening URL: " + ((Object) stringExtra));
        Toast.makeText(this, "Opening: " + ((Object) stringExtra), 0).show();
        Intent viewIntent = new Intent("android.intent.action.VIEW");
        viewIntent.setData(Uri.parse(stringExtra));
        viewIntent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
        String contentType = intent.getStringExtra(EXTRA_CONTENT_TYPE);
        if (contentType != null) {
            viewIntent.setDataAndType(Uri.parse(stringExtra), contentType);
        }
        try {
            Intent chooser = Intent.createChooser(viewIntent, "Open URL with");
            chooser.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            startActivity(chooser);
            Log.d(TAG, "Successfully opened chooser for URL: " + ((Object) stringExtra));
        } catch (Exception e) {
            Log.e(TAG, "Failed to open chooser for URL: " + ((Object) stringExtra), e);
            try {
                startActivity(viewIntent);
                Log.d(TAG, "Opened directly (fallback)");
            } catch (Exception e2) {
                Log.e(TAG, "Failed to open URL even directly", e2);
                Toast.makeText(this, "Cannot open URL: " + e2.getMessage(), 1).show();
            }
        }
    }
}
