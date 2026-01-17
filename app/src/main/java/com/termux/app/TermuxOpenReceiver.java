package com.termux.app;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.termux.terminal.KeyHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: TermuxOpenReceiver.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J(\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0002J(\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\bH\u0002¨\u0006\u0012"}, d2 = {"Lcom/termux/app/TermuxOpenReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "openFile", "action", "", "uri", "Landroid/net/Uri;", "originalIntent", "openUrl", "Companion", "ContentProvider", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes5.dex */
public final class TermuxOpenReceiver extends BroadcastReceiver {
    private static final String TAG = "TermuxOpenReceiver";

    /* compiled from: TermuxOpenReceiver.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J1\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001c\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\bH\u0016JO\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0017\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\bH\u0016¢\u0006\u0002\u0010\u0019J;\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0010\u0010\t\u001a\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\nH\u0016¢\u0006\u0002\u0010\u001b¨\u0006\u001d"}, d2 = {"Lcom/termux/app/TermuxOpenReceiver$ContentProvider;", "Landroid/content/ContentProvider;", "()V", "delete", "", "uri", "Landroid/net/Uri;", "selection", "", "selectionArgs", "", "(Landroid/net/Uri;Ljava/lang/String;[Ljava/lang/String;)I", "getType", "insert", "values", "Landroid/content/ContentValues;", "onCreate", "", "openFile", "Landroid/os/ParcelFileDescriptor;", "mode", "query", "Landroid/database/Cursor;", "projection", "sortOrder", "(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "update", "(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class ContentProvider extends android.content.ContentProvider {
        private static final String TAG = "TermuxFilesProvider";

        @Override // android.content.ContentProvider
        public boolean onCreate() {
            return true;
        }

        @Override // android.content.ContentProvider
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return null;
        }

        @Override // android.content.ContentProvider
        public String getType(Uri uri) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            String path = uri.getPath();
            if (path == null) {
                return "application/octet-stream";
            }
            String extension = StringsKt.substringAfterLast(path, '.', "");
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            return mimeTypeFromExtension == null ? "application/octet-stream" : mimeTypeFromExtension;
        }

        @Override // android.content.ContentProvider
        public Uri insert(Uri uri, ContentValues values) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return null;
        }

        @Override // android.content.ContentProvider
        public int delete(Uri uri, String selection, String[] selectionArgs) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return 0;
        }

        @Override // android.content.ContentProvider
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
            Intrinsics.checkNotNullParameter(uri, "uri");
            return 0;
        }

        @Override // android.content.ContentProvider
        public ParcelFileDescriptor openFile(Uri uri, String mode) throws IOException {
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(mode, "mode");
            String path = uri.getPath();
            if (path == null) {
                throw new FileNotFoundException("No path in URI: " + uri);
            }
            Context context = getContext();
            File filesDir = context != null ? context.getFilesDir() : null;
            if (filesDir == null) {
                throw new FileNotFoundException("No context");
            }
            File homeDir = new File(filesDir, "home");
            File prefixDir = new File(filesDir, "usr");
            File file = new File(path);
            String canonicalPath = file.getCanonicalPath();
            Intrinsics.checkNotNull(canonicalPath);
            String canonicalPath2 = homeDir.getCanonicalPath();
            Intrinsics.checkNotNullExpressionValue(canonicalPath2, "getCanonicalPath(...)");
            if (!StringsKt.startsWith$default(canonicalPath, canonicalPath2, false, 2, (Object) null)) {
                String canonicalPath3 = prefixDir.getCanonicalPath();
                Intrinsics.checkNotNullExpressionValue(canonicalPath3, "getCanonicalPath(...)");
                if (!StringsKt.startsWith$default(canonicalPath, canonicalPath3, false, 2, (Object) null)) {
                    throw new SecurityException("Access denied to path: " + path);
                }
            }
            if (!file.exists()) {
                throw new FileNotFoundException("File not found: " + path);
            }
            int accessMode = StringsKt.contains$default((CharSequence) mode, (CharSequence) "w", false, 2, (Object) null) ? 805306368 : KeyHandler.KEYMOD_NUM_LOCK;
            ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, accessMode);
            Intrinsics.checkNotNullExpressionValue(parcelFileDescriptorOpen, "open(...)");
            return parcelFileDescriptorOpen;
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        Uri uri = intent.getData();
        if (uri == null) {
            Log.e(TAG, "No URI provided in intent");
            return;
        }
        Log.i(TAG, "Received open request for: " + uri);
        Log.i(TAG, "Action: " + intent.getAction());
        Log.i(TAG, "Scheme: " + uri.getScheme());
        String scheme = uri.getScheme();
        if (scheme == null) {
            scheme = "";
        }
        if (!Intrinsics.areEqual(scheme, "file")) {
            String action = intent.getAction();
            openUrl(context, action != null ? action : "android.intent.action.VIEW", uri, intent);
        } else {
            String action2 = intent.getAction();
            openFile(context, action2 != null ? action2 : "android.intent.action.VIEW", uri, intent);
        }
    }

    private final void openUrl(Context context, String action, Uri uri, Intent originalIntent) {
        try {
            Intent openIntent = new Intent(action, uri);
            openIntent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            if (originalIntent.getBooleanExtra("chooser", false)) {
                Intent chooser = Intent.createChooser(openIntent, "Open with");
                chooser.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
                context.startActivity(chooser);
            } else {
                context.startActivity(openIntent);
                Log.i(TAG, "Successfully started activity for URL: " + uri);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to open URL: " + uri, e);
        }
    }

    private final void openFile(Context context, String action, Uri uri, Intent originalIntent) {
        try {
            String contentType = originalIntent.getStringExtra("content-type");
            Intent openIntent = new Intent(action);
            if (contentType != null) {
                openIntent.setDataAndType(uri, contentType);
            } else {
                openIntent.setData(uri);
            }
            openIntent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            openIntent.addFlags(1);
            if (originalIntent.getBooleanExtra("chooser", false)) {
                Intent chooser = Intent.createChooser(openIntent, "Open with");
                chooser.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
                context.startActivity(chooser);
            } else {
                context.startActivity(openIntent);
            }
            Log.i(TAG, "Successfully started activity for file: " + uri);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open file: " + uri, e);
            try {
                Intent $this$openFile_u24lambda_u242 = new Intent("android.intent.action.VIEW", uri);
                $this$openFile_u24lambda_u242.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
                context.startActivity($this$openFile_u24lambda_u242);
                Log.i(TAG, "Fallback succeeded for: " + uri);
            } catch (Exception e2) {
                Log.e(TAG, "Fallback also failed: " + uri, e2);
            }
        }
    }
}
