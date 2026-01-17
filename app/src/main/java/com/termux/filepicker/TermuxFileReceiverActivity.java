package com.termux.filepicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: TermuxFileReceiverActivity.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u0012\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\u0012\u0010\u0012\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0004H\u0002¨\u0006\u0016"}, d2 = {"Lcom/termux/filepicker/TermuxFileReceiverActivity;", "Landroid/app/Activity;", "()V", "getFilenameFromUri", "", "uri", "Landroid/net/Uri;", "handleIntent", "", "intent", "Landroid/content/Intent;", "handleSend", "handleView", "launchTerminal", "message", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onNewIntent", "saveFile", "saveText", "text", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TermuxFileReceiverActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws FileNotFoundException {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Intrinsics.checkNotNullExpressionValue(intent, "getIntent(...)");
        handleIntent(intent);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) throws FileNotFoundException {
        super.onNewIntent(intent);
        if (intent != null) {
            handleIntent(intent);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    private final void handleIntent(Intent intent) throws FileNotFoundException {
        String action = intent.getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1173264947:
                    if (action.equals("android.intent.action.SEND")) {
                        handleSend(intent);
                        return;
                    }
                    break;
                case -1173171990:
                    if (action.equals("android.intent.action.VIEW")) {
                        handleView(intent);
                        return;
                    }
                    break;
            }
        }
        Toast.makeText(this, "Unknown action: " + intent.getAction(), 0).show();
        finish();
    }

    private final void handleSend(Intent intent) throws FileNotFoundException {
        Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.STREAM");
        if (uri != null) {
            saveFile(uri);
            return;
        }
        String text = intent.getStringExtra("android.intent.extra.TEXT");
        if (text != null) {
            saveText(text);
        } else {
            Toast.makeText(this, "No content to receive", 0).show();
            finish();
        }
    }

    private final void handleView(Intent intent) throws FileNotFoundException {
        Uri uri = intent.getData();
        if (uri != null) {
            saveFile(uri);
        } else {
            Toast.makeText(this, "No file to view", 0).show();
            finish();
        }
    }

    private final void saveFile(Uri uri) throws FileNotFoundException {
        File file;
        String ext;
        try {
            String filename = getFilenameFromUri(uri);
            File downloadsDir = new File(new File(getFilesDir(), "home"), "downloads");
            downloadsDir.mkdirs();
            int counter = 1;
            file = new File(downloadsDir, filename);
            while (file.exists()) {
                String name = StringsKt.substringBeforeLast$default(filename, '.', (String) null, 2, (Object) null);
                if (StringsKt.contains$default((CharSequence) filename, '.', false, 2, (Object) null)) {
                    ext = "." + StringsKt.substringAfterLast$default(filename, '.', (String) null, 2, (Object) null);
                } else {
                    ext = "";
                }
                file = new File(downloadsDir, name + "_" + counter + ext);
                counter++;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            InputStream inputStreamOpenInputStream = getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                FileOutputStream fileOutputStream = inputStreamOpenInputStream;
                try {
                    InputStream input = fileOutputStream;
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        FileOutputStream output = fileOutputStream;
                        long jCopyTo$default = ByteStreamsKt.copyTo$default(input, output, 0, 2, null);
                        CloseableKt.closeFinally(fileOutputStream, null);
                        Long.valueOf(jCopyTo$default);
                        CloseableKt.closeFinally(fileOutputStream, null);
                    } finally {
                    }
                } finally {
                }
            }
            Toast.makeText(this, "Saved to ~/downloads/" + file.getName(), 1).show();
            launchTerminal("File saved: ~/downloads/" + file.getName());
        } catch (Exception e2) {
            e = e2;
            Toast.makeText(this, "Failed to save file: " + e.getMessage(), 1).show();
            finish();
        }
        finish();
    }

    private final void saveText(String text) {
        try {
            File downloadsDir = new File(new File(getFilesDir(), "home"), "downloads");
            downloadsDir.mkdirs();
            long timestamp = System.currentTimeMillis();
            File destFile = new File(downloadsDir, "shared_text_" + timestamp + ".txt");
            FilesKt.writeText$default(destFile, text, null, 2, null);
            Toast.makeText(this, "Saved to ~/downloads/" + destFile.getName(), 1).show();
            launchTerminal("Text saved: ~/downloads/" + destFile.getName());
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save text: " + e.getMessage(), 1).show();
        }
        finish();
    }

    private final String getFilenameFromUri(Uri uri) throws IOException {
        Cursor cursorQuery = getContentResolver().query(uri, null, null, null, null);
        if (cursorQuery != null) {
            Cursor cursor = cursorQuery;
            try {
                Cursor cursor2 = cursor;
                int nameIndex = cursor2.getColumnIndex("_display_name");
                if (nameIndex >= 0 && cursor2.moveToFirst()) {
                    String name = cursor2.getString(nameIndex);
                    String str = name;
                    if (!(str == null || str.length() == 0)) {
                        Intrinsics.checkNotNull(name);
                        CloseableKt.closeFinally(cursor, null);
                        return name;
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(cursor, null);
            } finally {
            }
        }
        String lastSegment = uri.getLastPathSegment();
        String str2 = lastSegment;
        if (str2 == null || str2.length() == 0) {
            return "received_file_" + System.currentTimeMillis();
        }
        return lastSegment;
    }

    private final void launchTerminal(String message) {
        try {
            Intent intent = new Intent(this, Class.forName("com.termux.MainActivity"));
            intent.setFlags(335544320);
            intent.putExtra("notification_message", message);
            startActivity(intent);
        } catch (Exception e) {
        }
    }
}
