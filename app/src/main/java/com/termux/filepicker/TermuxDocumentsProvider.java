package com.termux.filepicker;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;
import android.webkit.MimeTypeMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: TermuxDocumentsProvider.kt */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\t\u0018\u0000 (2\u00020\u0001:\u0001(B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002J&\u0010\r\u001a\u00020\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000bH\u0016J\u0012\u0010\u0011\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0010\u0010\u0013\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u001c\u0010\u0015\u001a\u00020\u00162\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J&\u0010\u0018\u001a\u00020\u00192\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0016J3\u0010\u001d\u001a\u00020\u001e2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\"J)\u0010#\u001a\u00020\u001e2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010 H\u0016¢\u0006\u0002\u0010$J\u001f\u0010%\u001a\u00020\u001e2\u0010\u0010\u001f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u000b\u0018\u00010 H\u0016¢\u0006\u0002\u0010&J\u001c\u0010'\u001a\u00020\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000bH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lcom/termux/filepicker/TermuxDocumentsProvider;", "Landroid/provider/DocumentsProvider;", "()V", "homeDir", "Ljava/io/File;", "prefixDir", "addFileRow", "", "cursor", "Landroid/database/MatrixCursor;", "documentId", "", "file", "createDocument", "parentDocumentId", "mimeType", "displayName", "deleteDocument", "getDocumentIdForFile", "getFileForDocumentId", "getMimeType", "isChildDocument", "", "onCreate", "openDocument", "Landroid/os/ParcelFileDescriptor;", "mode", "signal", "Landroid/os/CancellationSignal;", "queryChildDocuments", "Landroid/database/Cursor;", "projection", "", "sortOrder", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;", "queryDocument", "(Ljava/lang/String;[Ljava/lang/String;)Landroid/database/Cursor;", "queryRoots", "([Ljava/lang/String;)Landroid/database/Cursor;", "renameDocument", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class TermuxDocumentsProvider extends DocumentsProvider {
    private static final String ROOT_ID_HOME = "home";
    private static final String ROOT_ID_PREFIX = "prefix";
    private static final String TAG = "TermuxDocumentsProvider";
    private File homeDir;
    private File prefixDir;
    private static final String[] DEFAULT_ROOT_PROJECTION = {"root_id", "mime_types", "flags", "icon", "title", "summary", "document_id"};
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = {"document_id", "mime_type", "_display_name", "last_modified", "flags", "_size"};

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        Context context = getContext();
        File filesDir = context != null ? context.getFilesDir() : null;
        if (filesDir == null) {
            return false;
        }
        this.homeDir = new File(filesDir, ROOT_ID_HOME);
        this.prefixDir = new File(filesDir, "usr");
        return true;
    }

    @Override // android.provider.DocumentsProvider
    public Cursor queryRoots(String[] projection) {
        MatrixCursor result = new MatrixCursor(projection == null ? DEFAULT_ROOT_PROJECTION : projection);
        MatrixCursor.RowBuilder $this$queryRoots_u24lambda_u240 = result.newRow();
        $this$queryRoots_u24lambda_u240.add("root_id", ROOT_ID_HOME);
        $this$queryRoots_u24lambda_u240.add("mime_types", "*/*");
        $this$queryRoots_u24lambda_u240.add("flags", 19);
        $this$queryRoots_u24lambda_u240.add("icon", Integer.valueOf(R.drawable.ic_menu_myplaces));
        $this$queryRoots_u24lambda_u240.add("title", "MobileCLI Home");
        $this$queryRoots_u24lambda_u240.add("summary", "~/");
        $this$queryRoots_u24lambda_u240.add("document_id", ROOT_ID_HOME);
        MatrixCursor.RowBuilder $this$queryRoots_u24lambda_u241 = result.newRow();
        $this$queryRoots_u24lambda_u241.add("root_id", ROOT_ID_PREFIX);
        $this$queryRoots_u24lambda_u241.add("mime_types", "*/*");
        $this$queryRoots_u24lambda_u241.add("flags", 18);
        $this$queryRoots_u24lambda_u241.add("icon", Integer.valueOf(R.drawable.ic_menu_compass));
        $this$queryRoots_u24lambda_u241.add("title", "MobileCLI Prefix");
        $this$queryRoots_u24lambda_u241.add("summary", "$PREFIX");
        $this$queryRoots_u24lambda_u241.add("document_id", ROOT_ID_PREFIX);
        return result;
    }

    @Override // android.provider.DocumentsProvider
    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(projection == null ? DEFAULT_DOCUMENT_PROJECTION : projection);
        if (documentId == null) {
            return result;
        }
        File file = getFileForDocumentId(documentId);
        addFileRow(result, documentId, file);
        return result;
    }

    @Override // android.provider.DocumentsProvider
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        MatrixCursor result = new MatrixCursor(projection == null ? DEFAULT_DOCUMENT_PROJECTION : projection);
        if (parentDocumentId == null) {
            return result;
        }
        File parent = getFileForDocumentId(parentDocumentId);
        File[] fileArrListFiles = parent.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                Intrinsics.checkNotNull(file);
                String docId = getDocumentIdForFile(file);
                addFileRow(result, docId, file);
            }
        }
        return result;
    }

    @Override // android.provider.DocumentsProvider
    public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
        if (documentId == null) {
            throw new FileNotFoundException();
        }
        File file = getFileForDocumentId(documentId);
        int accessMode = ParcelFileDescriptor.parseMode(mode == null ? "r" : mode);
        ParcelFileDescriptor parcelFileDescriptorOpen = ParcelFileDescriptor.open(file, accessMode);
        Intrinsics.checkNotNullExpressionValue(parcelFileDescriptorOpen, "open(...)");
        return parcelFileDescriptorOpen;
    }

    @Override // android.provider.DocumentsProvider
    public String createDocument(String parentDocumentId, String mimeType, String displayName) throws IOException {
        if (parentDocumentId == null) {
            throw new FileNotFoundException();
        }
        File parent = getFileForDocumentId(parentDocumentId);
        File newFile = new File(parent, displayName == null ? "new_file" : displayName);
        if (Intrinsics.areEqual(mimeType, "vnd.android.document/directory")) {
            newFile.mkdirs();
        } else {
            newFile.createNewFile();
        }
        return getDocumentIdForFile(newFile);
    }

    @Override // android.provider.DocumentsProvider
    public void deleteDocument(String documentId) throws FileNotFoundException {
        if (documentId == null) {
            throw new FileNotFoundException();
        }
        File file = getFileForDocumentId(documentId);
        if (file.isDirectory()) {
            FilesKt.deleteRecursively(file);
        } else {
            file.delete();
        }
    }

    @Override // android.provider.DocumentsProvider
    public String renameDocument(String documentId, String displayName) throws FileNotFoundException {
        if (documentId == null) {
            throw new FileNotFoundException();
        }
        File file = getFileForDocumentId(documentId);
        File newFile = new File(file.getParentFile(), displayName == null ? file.getName() : displayName);
        file.renameTo(newFile);
        return getDocumentIdForFile(newFile);
    }

    @Override // android.provider.DocumentsProvider
    public boolean isChildDocument(String parentDocumentId, String documentId) throws FileNotFoundException {
        if (parentDocumentId == null) {
            return false;
        }
        File parent = getFileForDocumentId(parentDocumentId);
        if (documentId == null) {
            return false;
        }
        File child = getFileForDocumentId(documentId);
        String absolutePath = child.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        String absolutePath2 = parent.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
        return StringsKt.startsWith$default(absolutePath, absolutePath2, false, 2, (Object) null);
    }

    private final File getFileForDocumentId(String documentId) throws FileNotFoundException {
        File file = null;
        if (Intrinsics.areEqual(documentId, ROOT_ID_HOME)) {
            File file2 = this.homeDir;
            if (file2 != null) {
                return file2;
            }
            Intrinsics.throwUninitializedPropertyAccessException("homeDir");
            return null;
        }
        if (Intrinsics.areEqual(documentId, ROOT_ID_PREFIX)) {
            File file3 = this.prefixDir;
            if (file3 != null) {
                return file3;
            }
            Intrinsics.throwUninitializedPropertyAccessException("prefixDir");
            return null;
        }
        if (StringsKt.startsWith$default(documentId, "home/", false, 2, (Object) null)) {
            File file4 = this.homeDir;
            if (file4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("homeDir");
            } else {
                file = file4;
            }
            return new File(file, StringsKt.removePrefix(documentId, (CharSequence) "home/"));
        }
        if (StringsKt.startsWith$default(documentId, "prefix/", false, 2, (Object) null)) {
            File file5 = this.prefixDir;
            if (file5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("prefixDir");
            } else {
                file = file5;
            }
            return new File(file, StringsKt.removePrefix(documentId, (CharSequence) "prefix/"));
        }
        throw new FileNotFoundException("Unknown document ID: " + documentId);
    }

    private final String getDocumentIdForFile(File file) {
        String absolutePath = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        File file2 = this.homeDir;
        File file3 = null;
        if (file2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("homeDir");
            file2 = null;
        }
        String absolutePath2 = file2.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath2, "getAbsolutePath(...)");
        if (StringsKt.startsWith$default(absolutePath, absolutePath2, false, 2, (Object) null)) {
            String absolutePath3 = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath3, "getAbsolutePath(...)");
            File file4 = this.homeDir;
            if (file4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("homeDir");
            } else {
                file3 = file4;
            }
            String absolutePath4 = file3.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath4, "getAbsolutePath(...)");
            String relativePath = StringsKt.removePrefix(absolutePath3, (CharSequence) absolutePath4);
            return relativePath.length() == 0 ? ROOT_ID_HOME : ROOT_ID_HOME + relativePath;
        }
        String absolutePath5 = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath5, "getAbsolutePath(...)");
        File file5 = this.prefixDir;
        if (file5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("prefixDir");
            file5 = null;
        }
        String absolutePath6 = file5.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath6, "getAbsolutePath(...)");
        if (StringsKt.startsWith$default(absolutePath5, absolutePath6, false, 2, (Object) null)) {
            String absolutePath7 = file.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath7, "getAbsolutePath(...)");
            File file6 = this.prefixDir;
            if (file6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("prefixDir");
            } else {
                file3 = file6;
            }
            String absolutePath8 = file3.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(absolutePath8, "getAbsolutePath(...)");
            String relativePath2 = StringsKt.removePrefix(absolutePath7, (CharSequence) absolutePath8);
            return relativePath2.length() == 0 ? ROOT_ID_PREFIX : ROOT_ID_PREFIX + relativePath2;
        }
        String absolutePath9 = file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath9, "getAbsolutePath(...)");
        return absolutePath9;
    }

    private final void addFileRow(MatrixCursor cursor, String documentId, File file) {
        String mimeType;
        if (file.isDirectory()) {
            mimeType = "vnd.android.document/directory";
        } else {
            mimeType = getMimeType(file);
        }
        int flags = 0;
        if (file.isDirectory()) {
            flags = 0 | 8;
        }
        if (file.canWrite()) {
            flags = flags | 2 | 4 | 64;
        }
        MatrixCursor.RowBuilder $this$addFileRow_u24lambda_u243 = cursor.newRow();
        $this$addFileRow_u24lambda_u243.add("document_id", documentId);
        $this$addFileRow_u24lambda_u243.add("mime_type", mimeType);
        $this$addFileRow_u24lambda_u243.add("_display_name", file.getName());
        $this$addFileRow_u24lambda_u243.add("last_modified", Long.valueOf(file.lastModified()));
        $this$addFileRow_u24lambda_u243.add("flags", Integer.valueOf(flags));
        $this$addFileRow_u24lambda_u243.add("_size", Long.valueOf(file.length()));
    }

    private final String getMimeType(File file) {
        String extension = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(extension, "toLowerCase(...)");
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        return mimeType == null ? "application/octet-stream" : mimeType;
    }
}
