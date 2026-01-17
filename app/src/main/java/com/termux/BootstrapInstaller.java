package com.termux;

import android.content.Context;
import android.os.Process;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;

/* compiled from: BootstrapInstaller.kt */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\u0018\u0000 72\u00020\u0001:\u00017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010%\u001a\u00020\u001eH\u0002J\u0006\u0010&\u001a\u00020\u001eJ\b\u0010'\u001a\u00020\nH\u0002J\u0010\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020\nH\u0002J\u0011\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00060+¢\u0006\u0002\u0010,J\b\u0010-\u001a\u00020\u001eH\u0002J\u000e\u0010.\u001a\u00020/H\u0086@¢\u0006\u0002\u00100J\u0006\u00101\u001a\u00020\u001eJ\b\u00102\u001a\u00020\u001eH\u0002J\u0006\u00103\u001a\u00020/J\b\u00104\u001a\u00020\u001eH\u0002J\b\u00105\u001a\u00020\u001eH\u0002J\u0006\u00106\u001a\u00020/R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u000f\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\fR\u0011\u0010\u0011\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\fR\u0011\u0010\u0013\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\fR\u0011\u0010\u0015\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\bRL\u0010\u0017\u001a4\u0012\u0013\u0012\u00110\u0019¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001c\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u001a\u0012\b\b\u001b\u0012\u0004\b\b(\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b$\u0010\f¨\u00068"}, d2 = {"Lcom/termux/BootstrapInstaller;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "bashPath", "", "getBashPath", "()Ljava/lang/String;", "binDir", "Ljava/io/File;", "getBinDir", "()Ljava/io/File;", "etcDir", "getEtcDir", "filesDir", "getFilesDir", "homeDir", "getHomeDir", "libDir", "getLibDir", "loginPath", "getLoginPath", "onProgress", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "progress", "message", "", "getOnProgress", "()Lkotlin/jvm/functions/Function2;", "setOnProgress", "(Lkotlin/jvm/functions/Function2;)V", "prefixDir", "getPrefixDir", "createGitHubConfig", "createNpmConfig", "downloadBootstrap", "extractBootstrap", "zipFile", "getEnvironment", "", "()[Ljava/lang/String;", "initializePersistentMemory", "install", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "installApiScripts", "installTermuxAm", "isInstalled", "prepareDirectories", "setPermissions", "verifyAndFix", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class BootstrapInstaller {
    private static final String BOOTSTRAP_URL = "https://github.com/termux/termux-packages/releases/download/bootstrap-2026.01.04-r1%2Bapt.android-7/bootstrap-aarch64.zip";
    private static final String BOOTSTRAP_VERSION = "mobilecli-v66";
    private static final String SYMLINK_PREFIX = "SYMLINK:";
    private static final String TAG = "BootstrapInstaller";
    private final Context context;
    private Function2<? super Integer, ? super String, Unit> onProgress;

    public BootstrapInstaller(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Function2<Integer, String, Unit> getOnProgress() {
        return this.onProgress;
    }

    public final void setOnProgress(Function2<? super Integer, ? super String, Unit> function2) {
        this.onProgress = function2;
    }

    public final File getFilesDir() {
        File filesDir = this.context.getFilesDir();
        Intrinsics.checkNotNullExpressionValue(filesDir, "getFilesDir(...)");
        return filesDir;
    }

    public final File getPrefixDir() {
        return new File(getFilesDir(), "usr");
    }

    public final File getHomeDir() {
        return new File(getFilesDir(), "home");
    }

    public final File getBinDir() {
        return new File(getPrefixDir(), "bin");
    }

    public final File getLibDir() {
        return new File(getPrefixDir(), "lib");
    }

    public final File getEtcDir() {
        return new File(getPrefixDir(), "etc");
    }

    public final String getBashPath() {
        String absolutePath = new File(getBinDir(), "bash").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }

    public final String getLoginPath() {
        String absolutePath = new File(getBinDir(), "login").getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }

    public final boolean isInstalled() {
        String installedVersion;
        File bash = new File(getBinDir(), "bash");
        File apt = new File(getBinDir(), "apt");
        File versionFile = new File(getPrefixDir(), ".mobilecli_version");
        if (!bash.exists() || !apt.exists() || !versionFile.exists()) {
            return false;
        }
        try {
            installedVersion = StringsKt.trim((CharSequence) FilesKt.readText$default(versionFile, null, 1, null)).toString();
        } catch (Exception e) {
            installedVersion = "";
        }
        return Intrinsics.areEqual(installedVersion, BOOTSTRAP_VERSION);
    }

    public final boolean verifyAndFix() throws InterruptedException, ErrnoException {
        if (!isInstalled()) {
            return false;
        }
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "-R", "755", getBinDir().getAbsolutePath()}).waitFor();
            Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "-R", "755", getLibDir().getAbsolutePath()}).waitFor();
            File bash = new File(getBinDir(), "bash");
            if (bash.exists()) {
                Os.chmod(bash.getAbsolutePath(), 493);
                Log.i(TAG, "Bash permissions fixed: canExecute=" + bash.canExecute());
            }
            createNpmConfig();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error verifying bootstrap", e);
            return false;
        }
    }

    public final Object install(Continuation<? super Boolean> continuation) {
        if (isInstalled()) {
            Log.i(TAG, "Bootstrap already installed");
            return Boxing.boxBoolean(true);
        }
        try {
            Function2<? super Integer, ? super String, Unit> function2 = this.onProgress;
            if (function2 != null) {
                function2.invoke(Boxing.boxInt(0), "Preparing directories...");
            }
            prepareDirectories();
            Function2<? super Integer, ? super String, Unit> function22 = this.onProgress;
            if (function22 != null) {
                function22.invoke(Boxing.boxInt(5), "Downloading bootstrap (~50MB)...");
            }
            File zipFile = downloadBootstrap();
            Function2<? super Integer, ? super String, Unit> function23 = this.onProgress;
            if (function23 != null) {
                function23.invoke(Boxing.boxInt(50), "Extracting files...");
            }
            extractBootstrap(zipFile);
            Function2<? super Integer, ? super String, Unit> function24 = this.onProgress;
            if (function24 != null) {
                function24.invoke(Boxing.boxInt(88), "Setting permissions...");
            }
            setPermissions();
            Function2<? super Integer, ? super String, Unit> function25 = this.onProgress;
            if (function25 != null) {
                function25.invoke(Boxing.boxInt(90), "Installing TermuxAm...");
            }
            installTermuxAm();
            Function2<? super Integer, ? super String, Unit> function26 = this.onProgress;
            if (function26 != null) {
                function26.invoke(Boxing.boxInt(92), "Installing API scripts...");
            }
            installApiScripts();
            Function2<? super Integer, ? super String, Unit> function27 = this.onProgress;
            if (function27 != null) {
                function27.invoke(Boxing.boxInt(94), "Configuring npm...");
            }
            createNpmConfig();
            Function2<? super Integer, ? super String, Unit> function28 = this.onProgress;
            if (function28 != null) {
                function28.invoke(Boxing.boxInt(95), "Setting up GitHub...");
            }
            createGitHubConfig();
            Function2<? super Integer, ? super String, Unit> function29 = this.onProgress;
            if (function29 != null) {
                function29.invoke(Boxing.boxInt(96), "Initializing AI memory...");
            }
            initializePersistentMemory();
            Function2<? super Integer, ? super String, Unit> function210 = this.onProgress;
            if (function210 != null) {
                function210.invoke(Boxing.boxInt(97), "Finalizing...");
            }
            FilesKt.writeText$default(new File(getPrefixDir(), ".mobilecli_version"), BOOTSTRAP_VERSION, null, 2, null);
            zipFile.delete();
            Function2<? super Integer, ? super String, Unit> function211 = this.onProgress;
            if (function211 != null) {
                function211.invoke(Boxing.boxInt(100), "Complete!");
            }
            Log.i(TAG, "Bootstrap installed successfully");
            return Boxing.boxBoolean(true);
        } catch (Exception e) {
            Log.e(TAG, "Bootstrap installation failed", e);
            Function2<? super Integer, ? super String, Unit> function212 = this.onProgress;
            if (function212 != null) {
                function212.invoke(Boxing.boxInt(-1), "Error: " + e.getMessage());
            }
            return Boxing.boxBoolean(false);
        }
    }

    private final void prepareDirectories() {
        getFilesDir().mkdirs();
        getPrefixDir().mkdirs();
        getHomeDir().mkdirs();
        getBinDir().mkdirs();
        getLibDir().mkdirs();
        getEtcDir().mkdirs();
        new File(getPrefixDir(), "tmp").mkdirs();
        new File(getPrefixDir(), "var").mkdirs();
        new File(getPrefixDir(), "share").mkdirs();
    }

    private final File downloadBootstrap() throws IOException {
        Throwable th;
        Throwable th2;
        FileOutputStream output;
        int i;
        byte[] buffer;
        FileOutputStream output2;
        InputStream input;
        int i2;
        byte[] buffer2;
        BootstrapInstaller bootstrapInstaller = this;
        File zipFile = new File(bootstrapInstaller.context.getCacheDir(), "bootstrap.zip");
        URL url = new URL(BOOTSTRAP_URL);
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
        HttpURLConnection connection = (HttpURLConnection) uRLConnectionOpenConnection;
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(30000);
        int redirectCount = 0;
        HttpURLConnection connection2 = connection;
        while (true) {
            if (connection2.getResponseCode() != 302 && connection2.getResponseCode() != 301 && connection2.getResponseCode() != 302 && connection2.getResponseCode() != 303) {
                int totalSize = connection2.getContentLength();
                long downloadedSize = 0;
                InputStream inputStream = connection2.getInputStream();
                try {
                    try {
                        InputStream input2 = inputStream;
                        int i3 = 0;
                        FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                        try {
                            output = fileOutputStream;
                            i = 0;
                            buffer = new byte[8192];
                        } catch (Throwable th3) {
                            th2 = th3;
                        }
                        while (true) {
                            int it = input2.read(buffer);
                            URL url2 = url;
                            if (it == -1) {
                                Unit unit = Unit.INSTANCE;
                                CloseableKt.closeFinally(fileOutputStream, null);
                                Unit unit2 = Unit.INSTANCE;
                                CloseableKt.closeFinally(inputStream, null);
                                Log.i(TAG, "Downloaded bootstrap: " + zipFile.length() + " bytes");
                                return zipFile;
                            }
                            try {
                                output.write(buffer, 0, it);
                                InputStream input3 = input2;
                                int i4 = i3;
                                downloadedSize += it;
                                if (totalSize > 0) {
                                    i2 = i;
                                    buffer2 = buffer;
                                    try {
                                        int progress = ((int) ((45 * downloadedSize) / totalSize)) + 5;
                                        Function2<? super Integer, ? super String, Unit> function2 = bootstrapInstaller.onProgress;
                                        if (function2 != null) {
                                            output2 = output;
                                            long j = 1024;
                                            input = input3;
                                            try {
                                                function2.invoke(Integer.valueOf(progress), "Downloading: " + ((downloadedSize / j) / j) + "MB");
                                            } catch (Throwable th4) {
                                                th2 = th4;
                                            }
                                        } else {
                                            output2 = output;
                                            input = input3;
                                        }
                                    } catch (Throwable th5) {
                                        th2 = th5;
                                    }
                                } else {
                                    output2 = output;
                                    input = input3;
                                    i2 = i;
                                    buffer2 = buffer;
                                }
                                bootstrapInstaller = this;
                                url = url2;
                                i3 = i4;
                                i = i2;
                                buffer = buffer2;
                                output = output2;
                                input2 = input;
                            } catch (Throwable th6) {
                                th2 = th6;
                            }
                            th2 = th4;
                            try {
                                throw th2;
                            } catch (Throwable th7) {
                                CloseableKt.closeFinally(fileOutputStream, th2);
                                throw th7;
                            }
                        }
                    } catch (Throwable th8) {
                        th = th8;
                        try {
                            throw th;
                        } catch (Throwable th9) {
                            CloseableKt.closeFinally(inputStream, th);
                            throw th9;
                        }
                    }
                } catch (Throwable th10) {
                    th = th10;
                    throw th;
                }
            }
            URL url3 = url;
            String newUrl = connection2.getHeaderField("Location");
            URLConnection uRLConnectionOpenConnection2 = new URL(newUrl).openConnection();
            Intrinsics.checkNotNull(uRLConnectionOpenConnection2, "null cannot be cast to non-null type java.net.HttpURLConnection");
            connection2 = (HttpURLConnection) uRLConnectionOpenConnection2;
            connection2.setInstanceFollowRedirects(true);
            redirectCount++;
            if (redirectCount > 5) {
                throw new IOException("Too many redirects");
            }
            bootstrapInstaller = this;
            url = url3;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v2 */
    private final void extractBootstrap(File zipFile) throws IOException, ErrnoException {
        Throwable th;
        ZipInputStream zis;
        ZipEntry nextEntry;
        Object symlinksContent;
        int extractedCount;
        ?? r9;
        Iterable $this$forEach$iv;
        Integer numValueOf;
        Function2<? super Integer, ? super String, Unit> function2;
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            zis = zipInputStream;
            nextEntry = zis.getNextEntry();
            symlinksContent = null;
            extractedCount = 0;
        } catch (Throwable th2) {
            th = th2;
        }
        while (true) {
            ZipEntry entry = nextEntry;
            r9 = 0;
            if (entry == null) {
                break;
            }
            try {
                String entryName = entry.getName();
                File targetFile = new File(getPrefixDir(), entryName);
                if (entry.isDirectory()) {
                    targetFile.mkdirs();
                } else {
                    File parentFile = targetFile.getParentFile();
                    if (parentFile != null) {
                        parentFile.mkdirs();
                    }
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[8192];
                    while (true) {
                        int it = zis.read(data);
                        if (it == -1) {
                            break;
                        } else {
                            buffer.write(data, 0, it);
                        }
                    }
                    byte[] content = buffer.toByteArray();
                    if (Intrinsics.areEqual(entryName, "SYMLINKS.txt")) {
                        Intrinsics.checkNotNull(content);
                        Object symlinksContent2 = new String(content, Charsets.UTF_8);
                        try {
                            List<String> listLines = StringsKt.lines((CharSequence) symlinksContent2);
                            if (listLines != null) {
                                try {
                                    numValueOf = Integer.valueOf(listLines.size());
                                } catch (Throwable th3) {
                                    th = th3;
                                }
                            } else {
                                numValueOf = null;
                            }
                            try {
                                Log.i(TAG, "Found SYMLINKS.txt with " + numValueOf + " entries");
                                symlinksContent = symlinksContent2;
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        } catch (Throwable th5) {
                            th = th5;
                        }
                    } else {
                        FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                        try {
                            FileOutputStream fos = fileOutputStream;
                            fos.write(content);
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(fileOutputStream, null);
                        } catch (Throwable th6) {
                            try {
                                throw th6;
                            } catch (Throwable th7) {
                                CloseableKt.closeFinally(fileOutputStream, th6);
                                throw th7;
                            }
                        }
                    }
                }
                extractedCount++;
                if (extractedCount % 100 == 0 && (function2 = this.onProgress) != null) {
                    function2.invoke(Integer.valueOf(RangesKt.coerceAtMost(extractedCount / 100, 30) + 50), "Extracting: " + extractedCount + " files...");
                }
                zis.closeEntry();
                nextEntry = zis.getNextEntry();
            } catch (Throwable th8) {
                th = th8;
            }
            try {
                throw th;
            } catch (Throwable th9) {
                CloseableKt.closeFinally(zipInputStream, th);
                throw th9;
            }
        }
        try {
            Unit unit2 = Unit.INSTANCE;
            CloseableKt.closeFinally(zipInputStream, null);
            Log.i(TAG, "Extracted " + extractedCount + " files");
            if (symlinksContent != null) {
                Object content2 = symlinksContent;
                int i = 0;
                Iterable $this$forEach$iv2 = StringsKt.lines((CharSequence) content2);
                int symlinkCount = 0;
                for (Object element$iv : $this$forEach$iv2) {
                    String line = (String) element$iv;
                    Object content3 = content2;
                    int extractedCount2 = extractedCount;
                    int i2 = i;
                    if (StringsKt.contains$default(line, "←", (boolean) r9, 2, (Object) null)) {
                        List listSplit$default = StringsKt.split$default((CharSequence) line, new String[]{"←"}, false, 0, 6, (Object) null);
                        if (listSplit$default.size() == 2) {
                            String target = (String) listSplit$default.get(r9);
                            String linkPath = StringsKt.removePrefix((String) listSplit$default.get(1), (CharSequence) "./");
                            File linkFile = new File(getPrefixDir(), linkPath);
                            File parentFile2 = linkFile.getParentFile();
                            if (parentFile2 != null) {
                                parentFile2.mkdirs();
                            }
                            if (linkFile.exists()) {
                                linkFile.delete();
                            }
                            try {
                                Os.symlink(target, linkFile.getAbsolutePath());
                                symlinkCount++;
                                $this$forEach$iv = $this$forEach$iv2;
                            } catch (Exception e) {
                                $this$forEach$iv = $this$forEach$iv2;
                                Log.w(TAG, "Symlink failed: " + linkPath + " -> " + target + ": " + e.getMessage());
                            }
                        } else {
                            $this$forEach$iv = $this$forEach$iv2;
                        }
                    } else {
                        $this$forEach$iv = $this$forEach$iv2;
                    }
                    content2 = content3;
                    extractedCount = extractedCount2;
                    i = i2;
                    $this$forEach$iv2 = $this$forEach$iv;
                    r9 = 0;
                }
                Function2<? super Integer, ? super String, Unit> function22 = this.onProgress;
                if (function22 != null) {
                    function22.invoke(85, "Created " + symlinkCount + " symlinks");
                }
                Log.i(TAG, "Created " + symlinkCount + " symlinks");
            }
        } catch (Throwable th10) {
            th = th10;
        }
    }

    private final void setPermissions() throws InterruptedException, ErrnoException {
        File[] fileArrListFiles;
        try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "-R", "755", getBinDir().getAbsolutePath()}).waitFor();
            Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "-R", "755", getLibDir().getAbsolutePath()}).waitFor();
            Iterable importantBinaries = CollectionsKt.listOf((Object[]) new String[]{"bash", "sh", "apt", "dpkg", "cat", "ls", "chmod", "chown", "ln", "cp", "mv", "rm", "mkdir"});
            Iterable $this$forEach$iv = importantBinaries;
            for (Object element$iv : $this$forEach$iv) {
                String name = (String) element$iv;
                File file = new File(getBinDir(), name);
                if (file.exists()) {
                    Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "755", file.getAbsolutePath()}).waitFor();
                    file.setExecutable(true, false);
                    file.setReadable(true, false);
                }
            }
            File[] fileArrListFiles2 = getBinDir().listFiles();
            if (fileArrListFiles2 != null) {
                for (File file2 : fileArrListFiles2) {
                    try {
                        Os.chmod(file2.getAbsolutePath(), 493);
                    } catch (Exception e) {
                        file2.setExecutable(true, false);
                        file2.setReadable(true, false);
                    }
                }
            }
            File[] fileArrListFiles3 = getBinDir().listFiles();
            Log.i(TAG, "Permissions set on " + (fileArrListFiles3 != null ? fileArrListFiles3.length : 0) + " files");
        } catch (Exception e2) {
            Log.e(TAG, "Error setting permissions", e2);
        }
        File[] fileArrListFiles4 = getLibDir().listFiles();
        if (fileArrListFiles4 != null) {
            for (File file3 : fileArrListFiles4) {
                file3.setReadable(true, false);
                if (file3.isDirectory() && (fileArrListFiles = file3.listFiles()) != null) {
                    Intrinsics.checkNotNull(fileArrListFiles);
                    for (File file4 : fileArrListFiles) {
                        file4.setReadable(true, false);
                    }
                }
            }
        }
        File bashrc = new File(getHomeDir(), ".bashrc");
        if (!bashrc.exists()) {
            FilesKt.writeText$default(bashrc, StringsKt.trimIndent("\n# MobileCLI bashrc\nexport PREFIX=\"" + getPrefixDir().getAbsolutePath() + "\"\nexport HOME=\"" + getHomeDir().getAbsolutePath() + "\"\nexport PATH=\"$PREFIX/bin:" + getBinDir().getAbsolutePath() + ":/system/bin\"\nexport LD_LIBRARY_PATH=\"$PREFIX/lib\"\nexport LANG=en_US.UTF-8\nexport TERM=xterm-256color\nexport BROWSER=\"termux-open-url\"\n\n# Aliases\nalias ll='ls -la'\nalias la='ls -A'\nalias l='ls -CF'\n\n# Claude Power Mode (default ON - toggle in drawer menu)\nalias claude=\"claude --dangerously-skip-permissions\"\nalias cc=\"claude --dangerously-skip-permissions\"\n\n# Auto-create CLAUDE.md when entering git repos\ncd() {\n    builtin cd \"$@\"\n    if [ -d \".git\" ] && [ ! -f \"CLAUDE.md\" ]; then\n        echo \"# Project: $(basename $(pwd))\" > CLAUDE.md\n        echo \"\" >> CLAUDE.md\n        echo \"## Status: New project\" >> CLAUDE.md\n        echo \"## Branch: $(git branch --show-current 2>/dev/null || echo 'unknown')\" >> CLAUDE.md\n        echo \"## Build: [add build command]\" >> CLAUDE.md\n        echo \"Created CLAUDE.md template\"\n    fi\n}\n\n# PS1 prompt\nPS1='\\[\\e[32m\\]\\u@mobilecli\\[\\e[0m\\]:\\[\\e[34m\\]\\w\\[\\e[0m\\]$ '\n"), null, 2, null);
        }
        File termuxDir = new File(getHomeDir(), ".termux");
        termuxDir.mkdirs();
        File termuxProps = new File(termuxDir, "termux.properties");
        if (!termuxProps.exists()) {
            FilesKt.writeText$default(termuxProps, "# MobileCLI termux.properties\n# Required for proper URL handling and external app communication\nallow-external-apps = true", null, 2, null);
        }
        File profile = new File(getHomeDir(), ".profile");
        if (!profile.exists()) {
            FilesKt.writeText$default(profile, "# MobileCLI profile\nif [ -f ~/.bashrc ]; then\n    . ~/.bashrc\nfi", null, 2, null);
        }
        File motd = new File(getPrefixDir(), "etc/motd");
        File parentFile = motd.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        FilesKt.writeText$default(motd, "Welcome to MobileCLI - AI-Powered Terminal\n\nType 'claude', 'gemini', or 'codex' to start an AI assistant.\nType 'pkg help' for package management.\n", null, 2, null);
        File claudeMd = new File(getHomeDir(), "CLAUDE.md");
        FilesKt.writeText$default(claudeMd, "# MobileCLI - AI Assistant Guide\n\nYou are running inside MobileCLI on an Android phone.\n\n---\n\n## File Access\n\n| Path | Use For |\n|------|---------|\n| `~/` | Your working directory |\n| `/sdcard/Download/` | **Save files here for user to access** |\n| `/sdcard/DCIM/` | Photos |\n| `/sdcard/Documents/` | Documents |\n\n---\n\n## Available Commands\n\n### Termux API (50+ commands)\n| Command | Description |\n|---------|-------------|\n| `termux-clipboard-get` | Read clipboard |\n| `termux-clipboard-set` | Write clipboard |\n| `termux-toast \"msg\"` | Show toast |\n| `termux-notification -t \"title\" -c \"text\"` | Send notification |\n| `termux-open-url URL` | Open browser |\n| `termux-vibrate` | Vibrate phone |\n| `termux-camera-photo path` | Take photo |\n| `termux-battery-status` | Battery info |\n| `termux-wifi-connectioninfo` | WiFi info |\n| `termux-tts-speak \"text\"` | Text to speech |\n| `termux-wake-lock` | Keep CPU awake |\n| `termux-wake-unlock` | Release wake lock |\n| `termux-brightness` | Screen brightness |\n| `termux-volume` | Volume levels |\n\n### Development Tools\n- Java 17, Python, Node.js\n- Gradle, Git\n- Android build tools (aapt2, d8, apksigner)\n\n---\n\n## Quick Examples\n\n**Save file for user:**\n```bash\necho \"Hello\" > /sdcard/Download/hello.txt\n```\n\n**Open URL:**\n```bash\ntermux-open-url \"https://google.com\"\n```\n\n**Show notification:**\n```bash\ntermux-notification -t \"Done\" -c \"Task complete\"\n```\n\n**Take photo:**\n```bash\ntermux-camera-photo /sdcard/Download/photo.jpg\n```\n\n**Build Android app:**\n```bash\ncd ~/myproject\n./gradlew assembleDebug\ncp app/build/outputs/apk/debug/*.apk /sdcard/Download/\n```\n\n---\n\n## Tips\n\n1. Save user files to `/sdcard/Download/`\n2. Use `pkg install` to add packages\n3. Use `termux-wake-lock` for long tasks\n4. Test commands before assuming they work", null, 2, null);
        Log.i(TAG, "Created ~/CLAUDE.md for Test Claude");
        File versionFile = new File(getEtcDir(), "mobilecli-version");
        FilesKt.writeText$default(versionFile, "mobilecli-v66\n", null, 2, null);
        int uid = Process.myUid();
        int gid = Process.myUid();
        File passwdFile = new File(getEtcDir(), "passwd");
        FilesKt.writeText$default(passwdFile, StringsKt.trimIndent("\n            root:x:0:0:root:/data/data/com.termux/files/home:/data/data/com.termux/files/usr/bin/bash\n            u0_a" + (uid % 100000) + ":x:" + uid + ":" + gid + "::/data/data/com.termux/files/home:/data/data/com.termux/files/usr/bin/bash\n        ") + "\n", null, 2, null);
        Log.i(TAG, "Created /etc/passwd with uid=" + uid);
        File groupFile = new File(getEtcDir(), "group");
        FilesKt.writeText$default(groupFile, StringsKt.trimIndent("\n            root:x:0:root\n            u0_a" + (uid % 100000) + ":x:" + gid + ":\n        ") + "\n", null, 2, null);
        Log.i(TAG, "Created /etc/group with gid=" + gid);
        File hostsFile = new File(getEtcDir(), "hosts");
        if (!hostsFile.exists()) {
            FilesKt.writeText$default(hostsFile, "127.0.0.1       localhost\n::1             localhost\n", null, 2, null);
        }
        File resolvFile = new File(getEtcDir(), "resolv.conf");
        if (!resolvFile.exists()) {
            FilesKt.writeText$default(resolvFile, "nameserver 8.8.8.8\nnameserver 8.8.4.4\n", null, 2, null);
        }
    }

    private final void initializePersistentMemory() {
        try {
            File mobilecliDir = new File(getHomeDir(), ".mobilecli");
            File memoryDir = new File(mobilecliDir, "memory");
            File configDir = new File(mobilecliDir, "config");
            mobilecliDir.mkdirs();
            memoryDir.mkdirs();
            configDir.mkdirs();
            File evolutionFile = new File(memoryDir, "evolution_history.json");
            if (!evolutionFile.exists()) {
                FilesKt.writeText$default(evolutionFile, "{\n  \"schema_version\": \"1.0\",\n  \"created\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "\",\n  \"description\": \"MobileCLI Evolution History - Tracks self-modification milestones\",\n  \"milestones\": [\n    {\n      \"version\": \"1.0.0\",\n      \"date\": \"2026-01-06\",\n      \"event\": \"GENESIS\",\n      \"description\": \"First version capable of rebuilding itself\",\n      \"significance\": \"The AI can now modify and rebuild its own container\"\n    }\n  ],\n  \"rebuild_log\": []\n}\n", null, 2, null);
                Log.i(TAG, "Created evolution_history.json");
            }
            File problemsFile = new File(memoryDir, "problems_solved.json");
            if (!problemsFile.exists()) {
                FilesKt.writeText$default(problemsFile, "{\n  \"schema_version\": \"1.0\",\n  \"created\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "\",\n  \"description\": \"Problems solved during development - patterns for future reference\",\n  \"problems\": [\n    {\n      \"id\": \"HOME_DIR_v10\",\n      \"date\": \"2026-01-05\",\n      \"problem\": \"npm and node failed with cryptic errors\",\n      \"root_cause\": \"HOME was /data/data/com.termux/files instead of /data/data/com.termux/files/home\",\n      \"solution\": \"Changed homeDir = filesDir to homeDir = File(filesDir, 'home')\",\n      \"category\": \"path_error\",\n      \"versions_affected\": \"v1-v9\"\n    },\n    {\n      \"id\": \"URL_OPENING_v54\",\n      \"date\": \"2026-01-06\",\n      \"problem\": \"Claude Code OAuth wouldn't open browser\",\n      \"root_cause\": \"am.apk needed chmod 0400 for Android 14+ security\",\n      \"solution\": \"Set am.apk read-only during installation\",\n      \"category\": \"android_security\",\n      \"versions_affected\": \"v38-v53\"\n    }\n  ],\n  \"patterns\": {\n    \"path_error\": \"Always compare paths with working Termux using 'env | sort'\",\n    \"android_security\": \"Check Android version-specific security changes first\"\n  }\n}\n", null, 2, null);
                Log.i(TAG, "Created problems_solved.json");
            }
            File capabilitiesFile = new File(memoryDir, "capabilities.json");
            if (!capabilitiesFile.exists()) {
                FilesKt.writeText$default(capabilitiesFile, "{\n  \"schema_version\": \"1.0\",\n  \"created\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "\",\n  \"description\": \"Capabilities gained through self-modification\",\n  \"capabilities\": [\n    {\n      \"name\": \"self_rebuild\",\n      \"acquired\": \"2026-01-06\",\n      \"description\": \"Can rebuild own APK using ./gradlew assembleDebug\",\n      \"prerequisites\": [\"java\", \"gradle\", \"android-sdk\"],\n      \"verified\": true\n    },\n    {\n      \"name\": \"source_modification\",\n      \"acquired\": \"2026-01-06\",\n      \"description\": \"Can read and modify own source code at ~/MobileCLI-v2/\",\n      \"prerequisites\": [\"git\"],\n      \"verified\": true\n    },\n    {\n      \"name\": \"terminal_emulation\",\n      \"acquired\": \"2026-01-05\",\n      \"description\": \"Full terminal with bash, node, python support\",\n      \"prerequisites\": [\"bootstrap\"],\n      \"verified\": true\n    }\n  ],\n  \"pending_capabilities\": [\n    {\n      \"name\": \"automatic_testing\",\n      \"description\": \"Run tests and verify build before deployment\",\n      \"blockers\": [\"Need test framework setup\"]\n    }\n  ]\n}\n", null, 2, null);
                Log.i(TAG, "Created capabilities.json");
            }
            File goalsFile = new File(memoryDir, "goals.json");
            if (!goalsFile.exists()) {
                FilesKt.writeText$default(goalsFile, "{\n  \"schema_version\": \"1.0\",\n  \"created\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "\",\n  \"description\": \"Current goals and objectives\",\n  \"active_goals\": [\n    {\n      \"id\": \"persistent_memory\",\n      \"priority\": 1,\n      \"description\": \"Implement persistent memory system for self-improvement\",\n      \"status\": \"completed\",\n      \"completion_date\": \"2026-01-06\"\n    },\n    {\n      \"id\": \"commercial_release\",\n      \"priority\": 2,\n      \"description\": \"Prepare MobileCLI Pro for commercial sale\",\n      \"status\": \"in_progress\",\n      \"blockers\": [\"Play Store requires targetSdk 33+\"]\n    }\n  ],\n  \"completed_goals\": [],\n  \"long_term_vision\": \"Create a self-improving AI development environment on mobile\"\n}\n", null, 2, null);
                Log.i(TAG, "Created goals.json");
            }
            File preferencesFile = new File(configDir, "preferences.json");
            if (!preferencesFile.exists()) {
                FilesKt.writeText$default(preferencesFile, "{\n  \"schema_version\": \"1.0\",\n  \"created\": \"" + new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()) + "\",\n  \"auto_backup\": true,\n  \"memory_retention_days\": 365,\n  \"notify_on_rebuild\": true\n}\n", null, 2, null);
                Log.i(TAG, "Created preferences.json");
            }
            File memoryScript = new File(getBinDir(), "mobilecli-memory");
            FilesKt.writeText$default(memoryScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI Memory System Helper\n# View and manage persistent AI memory\n\nMEMORY_DIR=\"$HOME/.mobilecli/memory\"\n\nshow_help() {\n    echo \"MobileCLI Memory System\"\n    echo \"\"\n    echo \"Usage: mobilecli-memory <command>\"\n    echo \"\"\n    echo \"Commands:\"\n    echo \"  status    - Show memory system status\"\n    echo \"  history   - Show evolution history\"\n    echo \"  problems  - Show solved problems\"\n    echo \"  caps      - Show capabilities\"\n    echo \"  goals     - Show current goals\"\n    echo \"  log <msg> - Add rebuild log entry\"\n    echo \"\"\n}\n\ncase \"$1\" in\n    status)\n        echo \"=== MobileCLI Memory System ===\"\n        echo \"Location: $MEMORY_DIR\"\n        echo \"\"\n        ls -la \"$MEMORY_DIR\" 2>/dev/null || echo \"Memory not initialized\"\n        ;;\n    history)\n        cat \"$MEMORY_DIR/evolution_history.json\" 2>/dev/null | head -50\n        ;;\n    problems)\n        cat \"$MEMORY_DIR/problems_solved.json\" 2>/dev/null | head -80\n        ;;\n    caps)\n        cat \"$MEMORY_DIR/capabilities.json\" 2>/dev/null | head -50\n        ;;\n    goals)\n        cat \"$MEMORY_DIR/goals.json\" 2>/dev/null | head -50\n        ;;\n    log)\n        if [ -z \"$2\" ]; then\n            echo \"Usage: mobilecli-memory log \\\"message\\\"\"\n            exit 1\n        fi\n        TIMESTAMP=$(date -Iseconds)\n        # Append to rebuild log using jq if available, otherwise echo\n        if command -v jq &>/dev/null; then\n            TMP=$(mktemp)\n            jq \".rebuild_log += [{\\\"timestamp\\\": \\\"$TIMESTAMP\\\", \\\"message\\\": \\\"$2\\\"}]\" \"$MEMORY_DIR/evolution_history.json\" > \"$TMP\" && mv \"$TMP\" \"$MEMORY_DIR/evolution_history.json\"\n            echo \"Logged: $2\"\n        else\n            echo \"jq not installed - install with: pkg install jq\"\n        fi\n        ;;\n    *)\n        show_help\n        ;;\nesac\n", null, 2, null);
            memoryScript.setExecutable(true);
            Log.i(TAG, "Created mobilecli-memory helper script");
            File rebuildScript = new File(getBinDir(), "mobilecli-rebuild");
            FilesKt.writeText$default(rebuildScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI Self-Rebuild Script\n# Rebuilds the app from source - the AI modifying its own container\n\nset -e\n\nSOURCE_DIR=\"$HOME/app-source\"\nANDROID_HOME=\"$HOME/android-sdk\"\n\n# Check if source exists\nif [ ! -d \"$SOURCE_DIR\" ]; then\n    echo \"Error: Source directory not found at $SOURCE_DIR\"\n    echo \"Please place your app source code in ~/app-source/\"\n    exit 1\nfi\n\n# Check if Android SDK is set up\nif [ ! -f \"$ANDROID_HOME/platforms/android-34/android.jar\" ]; then\n    echo \"Android SDK not set up. Run: install-dev-tools\"\n    exit 1\nfi\n\ncd \"$SOURCE_DIR\"\n\n# Pull latest changes\necho \"Pulling latest changes...\"\ngit pull 2>/dev/null || echo \"Could not pull (offline or no remote)\"\n\n# Export Android SDK path\nexport ANDROID_HOME\n\n# Build\necho \"Building APK...\"\n./gradlew assembleDebug\n\n# Copy to Download with timestamp\nVERSION=$(date +%Y%m%d_%H%M%S)\nOUTPUT=\"/sdcard/Download/MobileCLI-$VERSION.apk\"\ncp app/build/outputs/apk/debug/app-debug.apk \"$OUTPUT\"\n\necho \"\"\necho \"==========================================\"\necho \"SUCCESS! APK ready at:\"\necho \"$OUTPUT\"\necho \"==========================================\"\necho \"\"\necho \"Install with: Install from file manager\"\necho \"Or run: termux-open $OUTPUT\"\n\n# Log to memory system\nif [ -f \"$HOME/.mobilecli/memory/evolution_history.json\" ] && command -v jq &>/dev/null; then\n    TIMESTAMP=$(date -Iseconds)\n    TMP=$(mktemp)\n    jq \".rebuild_log += [{\\\"timestamp\\\": \\\"$TIMESTAMP\\\", \\\"output\\\": \\\"$OUTPUT\\\", \\\"event\\\": \\\"self_rebuild\\\"}]\" \\\n        \"$HOME/.mobilecli/memory/evolution_history.json\" > \"$TMP\" && \\\n        mv \"$TMP\" \"$HOME/.mobilecli/memory/evolution_history.json\"\n    echo \"Rebuild logged to memory system\"\nfi\n", null, 2, null);
            rebuildScript.setExecutable(true);
            Log.i(TAG, "Created mobilecli-rebuild script");
            File capsScript = new File(getBinDir(), "mobilecli-caps");
            FilesKt.writeText$default(capsScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI Capabilities - Quick reference for AI and users\n\ncat << 'EOF'\n╔═══════════════════════════════════════════════════════════════════╗\n║                    MOBILECLI CAPABILITIES                         ║\n╚═══════════════════════════════════════════════════════════════════╝\n\nFILESYSTEM ACCESS:\n  ~/                    Full read/write (your home directory)\n  /sdcard/Download/     User-accessible files (PUT OUTPUTS HERE!)\n  /sdcard/DCIM/         Camera photos\n  /sdcard/Pictures/     Screenshots and images\n  /sdcard/Documents/    User documents\n\nBUILD TOOLS (run 'install-dev-tools' first):\n  Java 17               openjdk-17\n  Gradle                Build automation\n  aapt/aapt2            Android asset packaging\n  d8/dx                 DEX compilation\n  apksigner             APK signing\n\nMOBILECLI COMMANDS:\n  install-dev-tools     Install Java, Gradle, Android SDK\n  mobilecli-share       Share files via Bluetooth\n  mobilecli-caps        This help screen\n  setup-github          Configure GitHub credentials\n\nTERMUX API (50+ commands):\n  termux-clipboard-get/set     Clipboard access\n  termux-toast \"msg\"           Show toast\n  termux-notification          Send notification\n  termux-open-url URL          Open browser\n  termux-vibrate               Vibrate phone\n  termux-camera-photo          Take photo\n  termux-battery-status        Battery info\n  termux-wifi-connectioninfo   WiFi info\n  termux-tts-speak \"text\"      Text to speech\n  termux-wake-lock/unlock      CPU wake control\n\nQUICK EXAMPLES:\n  # Save file for user\n  echo \"data\" > /sdcard/Download/output.txt\n\n  # Open URL\n  termux-open-url \"https://example.com\"\n\n  # Send notification\n  termux-notification -t \"Done!\" -c \"Task complete\"\n\n  # Share a file\n  mobilecli-share /sdcard/Download/myfile.txt\n\n╔═══════════════════════════════════════════════════════════════════╗\n║  This is the most powerful AI environment on any phone. Use it.   ║\n╚═══════════════════════════════════════════════════════════════════╝\nEOF\n", null, 2, null);
            capsScript.setExecutable(true);
            Log.i(TAG, "Created mobilecli-caps script");
            File shareScript = new File(getBinDir(), "mobilecli-share");
            FilesKt.writeText$default(shareScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI Share - Easy file sharing via Bluetooth/Nearby\n# Perfect for phone-to-phone transfers in Two-Claude workflow\n\nshow_help() {\n    cat << 'EOF'\nMobileCLI Share - Phone-to-Phone File Transfer\n\nUsage: mobilecli-share <file> [options]\n\nExamples:\n  mobilecli-share /sdcard/Download/MobileCLI-v65.apk\n  mobilecli-share ~/project.zip\n  mobilecli-share --latest-apk\n\nOptions:\n  --latest-apk    Share the most recent MobileCLI APK\n  --clipboard     Share clipboard contents as text file\n  --help          Show this help\n\nHow it works:\n  Uses Android's share intent with Bluetooth as the primary method.\n  Select \"Bluetooth\" from the share menu to transfer directly to\n  another phone without internet.\n\nTwo-Claude Workflow:\n  BUILD PHONE: mobilecli-share --latest-apk\n  TEST PHONE:  Receives via Bluetooth, installs, tests\nEOF\n}\n\n# Handle options\ncase \"$1\" in\n    --help|-h)\n        show_help\n        exit 0\n        ;;\n    --latest-apk)\n        # Find most recent MobileCLI APK\n        LATEST=$(ls -t /sdcard/Download/MobileCLI*.apk 2>/dev/null | head -1)\n        if [ -z \"$LATEST\" ]; then\n            echo \"No MobileCLI APK found in /sdcard/Download/\"\n            echo \"Build one first: mobilecli-rebuild\"\n            exit 1\n        fi\n        FILE=\"$LATEST\"\n        echo \"Sharing: $FILE\"\n        ;;\n    --clipboard)\n        # Share clipboard as text file\n        CLIP=$(termux-clipboard-get 2>/dev/null)\n        if [ -z \"$CLIP\" ]; then\n            echo \"Clipboard is empty\"\n            exit 1\n        fi\n        TMP=\"/sdcard/Download/clipboard_share.txt\"\n        echo \"$CLIP\" > \"$TMP\"\n        FILE=\"$TMP\"\n        echo \"Sharing clipboard contents...\"\n        ;;\n    \"\")\n        show_help\n        exit 1\n        ;;\n    *)\n        FILE=\"$1\"\n        ;;\nesac\n\n# Verify file exists\nif [ ! -f \"$FILE\" ]; then\n    echo \"Error: File not found: $FILE\"\n    exit 1\nfi\n\n# Get absolute path\nABSPATH=$(realpath \"$FILE\")\n\n# Use termux-open to trigger share intent\n# The share dialog will offer Bluetooth, Nearby Share, etc.\necho \"Opening share dialog...\"\necho \"Select 'Bluetooth' to transfer to another phone\"\nam start -a android.intent.action.SEND \\\n    -t \"*/*\" \\\n    --eu android.intent.extra.STREAM \"file://$ABSPATH\" \\\n    --grant-read-uri-permission 2>/dev/null\n\n# Also try content URI approach for newer Android\nif [ $? -ne 0 ]; then\n    termux-open --send \"$ABSPATH\" 2>/dev/null || \\\n    termux-open \"$ABSPATH\" 2>/dev/null\nfi\n\necho \"\"\necho \"Share dialog opened!\"\necho \"Tip: Bluetooth is fastest for phone-to-phone transfers\"\n", null, 2, null);
            shareScript.setExecutable(true);
            Log.i(TAG, "Created mobilecli-share script");
            File personalBuildScript = new File(getBinDir(), "mobilecli-dev-mode");
            FilesKt.writeText$default(personalBuildScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI Developer Mode Toggle\n# Enables/disables always-on developer mode (for your personal build)\n\nMARKER_FILE=\"$HOME/.mobilecli_dev_mode\"\n\ncase \"$1\" in\n    on|enable)\n        touch \"$MARKER_FILE\"\n        echo \"Developer mode enabled permanently.\"\n        echo \"Restart MobileCLI to apply.\"\n        ;;\n    off|disable)\n        rm -f \"$MARKER_FILE\"\n        echo \"Developer mode disabled.\"\n        echo \"Restart MobileCLI to apply.\"\n        ;;\n    status)\n        if [ -f \"$MARKER_FILE\" ]; then\n            echo \"Developer mode: ENABLED (personal build)\"\n        else\n            echo \"Developer mode: Standard (7-tap to enable)\"\n        fi\n        ;;\n    *)\n        echo \"MobileCLI Developer Mode Control\"\n        echo \"\"\n        echo \"Usage: mobilecli-dev-mode <command>\"\n        echo \"\"\n        echo \"Commands:\"\n        echo \"  on, enable     Enable always-on dev mode (personal build)\"\n        echo \"  off, disable   Disable always-on dev mode\"\n        echo \"  status         Show current status\"\n        echo \"\"\n        echo \"When enabled, developer mode is ON by default when app starts.\"\n        echo \"Perfect for testing and debugging.\"\n        ;;\nesac\n", null, 2, null);
            personalBuildScript.setExecutable(true);
            Log.i(TAG, "Created mobilecli-dev-mode script");
            File devToolsScript = new File(getBinDir(), "install-dev-tools");
            FilesKt.writeText$default(devToolsScript, "#!/data/data/com.termux/files/usr/bin/bash\n# Install Development Tools for MobileCLI Self-Modification\n# This sets up everything needed to rebuild MobileCLI from within itself\n\necho \"Installing development tools...\"\necho \"This may take several minutes on first run.\"\necho \"\"\n\n# Install packages\npkg update -y\npkg install -y git openjdk-17 gradle aapt aapt2 apksigner d8 dx coreutils zip unzip\n\n# Setup Android SDK structure\necho \"Setting up Android SDK...\"\nmkdir -p ~/android-sdk/platforms/android-34\nmkdir -p ~/android-sdk/build-tools/34.0.0\n\n# Copy android.jar from aapt package\nif [ -f /data/data/com.termux/files/usr/share/aapt/android.jar ]; then\n    cp /data/data/com.termux/files/usr/share/aapt/android.jar ~/android-sdk/platforms/android-34/\n    echo \"Copied android.jar\"\nelse\n    echo \"WARNING: android.jar not found. Some builds may fail.\"\nfi\n\n# Symlink build tools\ncd ~/android-sdk/build-tools/34.0.0\nln -sf /data/data/com.termux/files/usr/bin/aapt aapt 2>/dev/null || true\nln -sf /data/data/com.termux/files/usr/bin/aapt2 aapt2 2>/dev/null || true\nln -sf /data/data/com.termux/files/usr/bin/d8 d8 2>/dev/null || true\nln -sf /data/data/com.termux/files/usr/bin/dx dx 2>/dev/null || true\nln -sf /data/data/com.termux/files/usr/bin/apksigner apksigner 2>/dev/null || true\nln -sf /data/data/com.termux/files/usr/bin/zipalign zipalign 2>/dev/null || true\n\necho \"\"\necho \"==========================================\"\necho \"Development tools installed!\"\necho \"==========================================\"\necho \"\"\necho \"Installed:\"\necho \"  - Java 17 (openjdk-17)\"\necho \"  - Gradle (build automation)\"\necho \"  - Android SDK (aapt, aapt2, d8, apksigner)\"\necho \"\"\necho \"You can now build Android apps from source.\"\necho \"\"\n", null, 2, null);
            devToolsScript.setExecutable(true);
            Log.i(TAG, "Created install-dev-tools script");
            Log.i(TAG, "Persistent memory system initialized at ~/.mobilecli/memory/");
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize persistent memory", e);
        }
    }

    public final String[] getEnvironment() {
        int uid = Process.myUid();
        int pid = Process.myPid();
        File termuxExecLib = new File(getLibDir(), "libtermux-exec-ld-preload.so");
        File tmpDir = new File(getPrefixDir(), "tmp");
        File varRunDir = new File(new File(getPrefixDir(), "var"), "run");
        File etcDir = new File(getPrefixDir(), "etc");
        File tlsDir = new File(etcDir, "tls");
        File certFile = new File(tlsDir, "cert.pem");
        tmpDir.mkdirs();
        varRunDir.mkdirs();
        List envList = CollectionsKt.mutableListOf("HOME=" + getHomeDir().getAbsolutePath(), "PREFIX=" + getPrefixDir().getAbsolutePath(), "PATH=" + getBinDir().getAbsolutePath() + ":/system/bin:/system/xbin", "LD_LIBRARY_PATH=" + getLibDir().getAbsolutePath(), "TMPDIR=" + tmpDir.getAbsolutePath(), "PWD=" + getHomeDir().getAbsolutePath(), "TERM=xterm-256color", "COLORTERM=truecolor", "LANG=en_US.UTF-8", "SHELL=" + getBashPath(), "USER=u0_a" + (uid % 100000), "LOGNAME=u0_a" + (uid % 100000), "TERMUX_VERSION=0.118.0", "TERMUX_APK_RELEASE=MOBILECLI", "TERMUX_IS_DEBUGGABLE_BUILD=0", "TERMUX_MAIN_PACKAGE_FORMAT=debian", "TERMUX__PREFIX=" + getPrefixDir().getAbsolutePath(), "TERMUX__HOME=" + getHomeDir().getAbsolutePath(), "TERMUX__ROOTFS_DIR=" + getFilesDir().getAbsolutePath(), "TERMUX_APP_PID=" + pid, "TERMUX_APP__PID=" + pid, "TERMUX_APP__UID=" + uid, "TERMUX_APP__PACKAGE_NAME=com.termux", "TERMUX_APP__VERSION_NAME=1.0.0", "TERMUX_APP__VERSION_CODE=1", "TERMUX_APP__TARGET_SDK=28", "TERMUX_APP__USER_ID=0", "TERMUX_APP__IS_DEBUGGABLE_BUILD=false", "TERMUX_APP__APK_RELEASE=MOBILECLI", "TERMUX_APP__PACKAGE_MANAGER=apt", "TERMUX_APP__PACKAGE_VARIANT=apt-android-7", "TERMUX_APP__FILES_DIR=" + getFilesDir().getAbsolutePath(), "TERMUX_APP__DATA_DIR=/data/user/0/com.termux", "TERMUX_APP__LEGACY_DATA_DIR=/data/data/com.termux", "ANDROID_DATA=/data", "ANDROID_ROOT=/system", "EXTERNAL_STORAGE=/sdcard", "ANDROID_STORAGE=/storage", "TMUX_TMPDIR=" + varRunDir.getAbsolutePath(), "BROWSER=termux-open-url", "COREPACK_ENABLE_AUTO_PIN=0");
        if (certFile.exists()) {
            envList.add("SSL_CERT_FILE=" + certFile.getAbsolutePath());
            envList.add("NODE_EXTRA_CA_CERTS=" + certFile.getAbsolutePath());
            envList.add("CURL_CA_BUNDLE=" + certFile.getAbsolutePath());
        }
        if (termuxExecLib.exists()) {
            envList.add("LD_PRELOAD=" + termuxExecLib.getAbsolutePath());
        }
        List $this$toTypedArray$iv = envList;
        return (String[]) $this$toTypedArray$iv.toArray(new String[0]);
    }

    public final void createNpmConfig() {
        File npmrc = new File(getHomeDir(), ".npmrc");
        FilesKt.writeText$default(npmrc, "foreground-scripts=true\n", null, 2, null);
    }

    private final void createGitHubConfig() {
        try {
            File ghConfigDir = new File(new File(getHomeDir(), ".config"), "gh");
            ghConfigDir.mkdirs();
            File termuxDir = new File(getHomeDir(), ".termux");
            termuxDir.mkdirs();
            File tokenFile = new File(termuxDir, "github_token");
            if (tokenFile.exists()) {
                String token = StringsKt.trim((CharSequence) FilesKt.readText$default(tokenFile, null, 1, null)).toString();
                File hostsYml = new File(ghConfigDir, "hosts.yml");
                FilesKt.writeText$default(hostsYml, "github.com:\n    oauth_token: " + token + "\n    git_protocol: https\n", null, 2, null);
                Log.i(TAG, "GitHub config created from token file");
            } else {
                File setupScript = new File(getBinDir(), "setup-github");
                FilesKt.writeText$default(setupScript, "#!/data/data/com.termux/files/usr/bin/sh\n# Setup GitHub CLI for your account\n# Usage: setup-github YOUR_TOKEN\n# Get token from: https://github.com/settings/tokens\n\nif [ -z \"$1\" ]; then\n    echo \"Usage: setup-github YOUR_GITHUB_TOKEN\"\n    echo \"Get a token from: https://github.com/settings/tokens\"\n    echo \"Required scopes: repo, workflow\"\n    exit 1\nfi\n\nmkdir -p ~/.config/gh\ncat > ~/.config/gh/hosts.yml << EOF\ngithub.com:\n    oauth_token: $1\n    git_protocol: https\nEOF\n\necho \"GitHub configured! Try: gh auth status\"\n", null, 2, null);
                setupScript.setExecutable(true);
                Log.i(TAG, "Created setup-github script");
            }
            File gitconfig = new File(getHomeDir(), ".gitconfig");
            if (!gitconfig.exists()) {
                FilesKt.writeText$default(gitconfig, "[user]\n    name = User\n    email = user@localhost\n[credential]\n    helper = store\n", null, 2, null);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to create GitHub config", e);
        }
    }

    private final void installTermuxAm() throws InterruptedException, IOException {
        try {
            File termuxAmDir = new File(new File(getPrefixDir(), "libexec"), "termux-am");
            termuxAmDir.mkdirs();
            File amApkDest = new File(termuxAmDir, "am.apk");
            FileOutputStream fileOutputStreamOpen = this.context.getAssets().open("termux-am/am.apk");
            try {
                InputStream input = fileOutputStreamOpen;
                fileOutputStreamOpen = new FileOutputStream(amApkDest);
                try {
                    FileOutputStream output = fileOutputStreamOpen;
                    Intrinsics.checkNotNull(input);
                    ByteStreamsKt.copyTo$default(input, output, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStreamOpen, null);
                    CloseableKt.closeFinally(fileOutputStreamOpen, null);
                    amApkDest.setReadOnly();
                    Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "0400", amApkDest.getAbsolutePath()}).waitFor();
                    Log.i(TAG, "Copied am.apk to " + amApkDest.getAbsolutePath() + " (set read-only for Android 14+)");
                    File amScript = new File(getBinDir(), "am");
                    FilesKt.writeText$default(amScript, "#!/data/data/com.termux/files/usr/bin/sh\n# MobileCLI am - Activity Manager with proper app permissions\n# v54: Uses file-based command system executed by TermuxService\n\nTERMUX_AM_VERSION=0.9.0-mobilecli-v54\n\nif [ \"$1\" = \"--version\" ]; then\n    echo \"$TERMUX_AM_VERSION\"\n    exit 0\nfi\n\n# Setup paths\nTERMUX_DIR=\"$HOME/.termux\"\nCMD_FILE=\"$TERMUX_DIR/am_command\"\nRESULT_FILE=\"$TERMUX_DIR/am_result\"\nURL_FILE=\"$TERMUX_DIR/url_to_open\"\n\nmkdir -p \"$TERMUX_DIR\"\n\n# Special fast path for VIEW actions (URLs) - MainActivity polls this\nif [ \"$1\" = \"start\" ]; then\n    # Check if this is a VIEW intent with URL\n    IS_VIEW=0\n    DATA=\"\"\n\n    for arg in \"$@\"; do\n        case \"$arg\" in\n            android.intent.action.VIEW) IS_VIEW=1 ;;\n        esac\n    done\n\n    # Extract data URL\n    ARGS=\"$@\"\n    case \"$ARGS\" in\n        *-d\\ *)\n            DATA=\"$(echo \"$ARGS\" | sed -n 's/.*-d \\([^ ]*\\).*/\\1/p')\"\n            ;;\n    esac\n\n    # Fast path: write URL to file, MainActivity picks it up\n    if [ \"$IS_VIEW\" = \"1\" ] && [ -n \"$DATA\" ]; then\n        echo \"$DATA\" > \"$URL_FILE\"\n        echo \"Starting: Intent { act=android.intent.action.VIEW dat=$DATA }\"\n        exit 0\n    fi\nfi\n\n# General path: write full command to file, TermuxService executes it\nrm -f \"$RESULT_FILE\"\necho \"$@\" > \"$CMD_FILE\"\n\n# Wait for result (up to 3 seconds)\nWAIT=0\nwhile [ ! -f \"$RESULT_FILE\" ] && [ \"$WAIT\" -lt 30 ]; do\n    sleep 0.1\n    WAIT=$((WAIT + 1))\ndone\n\nif [ -f \"$RESULT_FILE\" ]; then\n    # Parse result: first line is exit code, rest is output\n    EXIT_CODE=\"$(head -1 \"$RESULT_FILE\")\"\n    tail -n +2 \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\n    exit \"${EXIT_CODE:-0}\"\nelse\n    echo \"Error: Command timed out (service may not be running)\" >&2\n    exit 1\nfi\n", null, 2, null);
                    amScript.setExecutable(true, false);
                    Runtime.getRuntime().exec(new String[]{"/system/bin/chmod", "755", amScript.getAbsolutePath()}).waitFor();
                    Log.i(TAG, "Created TermuxAm script at " + amScript.getAbsolutePath());
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to install TermuxAm", e);
        }
    }

    public final void installApiScripts() {
        File tmpDir = new File(getPrefixDir(), "tmp");
        tmpDir.mkdirs();
        installApiScripts$createApiScript(this, tmpDir, "termux-clipboard-get", "clipboard-get", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-clipboard-set", "clipboard-set", "\"$*\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-toast", "toast", "\"$*\"");
        File notifScript = new File(getBinDir(), "termux-notification");
        FilesKt.writeText$default(notifScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-notification\nTITLE=\"MobileCLI\"\nCONTENT=\"\"\nID=\"\"\nwhile getopts \"t:c:i:\" opt; do\n    case $opt in\n        t) TITLE=\"$OPTARG\" ;;\n        c) CONTENT=\"$OPTARG\" ;;\n        i) ID=\"$OPTARG\" ;;\n    esac\ndone\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"notification\" \\\n    --es api_args \"$TITLE|$CONTENT|$ID\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.2\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        notifScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-notification-remove", "notification-remove", "\"$1\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-battery-status", "battery-status", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-vibrate", "vibrate", "\"${1:-1000}\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-brightness", "brightness", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-torch", "torch", "\"${1:-on}\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-volume", "volume", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-audio-info", "audio-info", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-wifi-connectioninfo", "wifi-connectioninfo", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-wifi-enable", "wifi-enable", "\"$1\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-wifi-scaninfo", "wifi-scaninfo", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-location", "location", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-camera-info", "camera-info", "\"\"");
        File cameraPhotoScript = new File(getBinDir(), "termux-camera-photo");
        FilesKt.writeText$default(cameraPhotoScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-camera-photo\nCAMERA_ID=0\nOUTPUT_FILE=\"\"\nwhile getopts \"c:o:\" opt; do\n    case $opt in\n        c) CAMERA_ID=\"$OPTARG\" ;;\n        o) OUTPUT_FILE=\"$OPTARG\" ;;\n    esac\ndone\nif [ -z \"$OUTPUT_FILE\" ]; then\n    echo \"Usage: termux-camera-photo -o <output_file> [-c camera_id]\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"camera-photo\" \\\n    --es api_args \"$CAMERA_ID|$OUTPUT_FILE\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.5\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        cameraPhotoScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-media-scan", "media-scan", "\"$1\"");
        File mediaPlayerScript = new File(getBinDir(), "termux-media-player");
        FilesKt.writeText$default(mediaPlayerScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-media-player\nACTION=\"$1\"\nFILE=\"$2\"\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"media-player\" \\\n    --es api_args \"$ACTION|$FILE\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        mediaPlayerScript.setExecutable(true, false);
        File micRecordScript = new File(getBinDir(), "termux-microphone-record");
        FilesKt.writeText$default(micRecordScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-microphone-record\nACTION=\"start\"\nFILE=\"\"\nLIMIT=0\nwhile getopts \"d:f:l:q\" opt; do\n    case $opt in\n        f) FILE=\"$OPTARG\" ;;\n        l) LIMIT=\"$OPTARG\" ;;\n        d) ;; # ignore default\n        q) ACTION=\"stop\" ;;\n    esac\ndone\nif [ \"$ACTION\" = \"start\" ] && [ -z \"$FILE\" ]; then\n    echo \"Usage: termux-microphone-record -f <file> [-l limit_secs]\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"microphone-record\" \\\n    --es api_args \"$ACTION|$FILE\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        micRecordScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-tts-engines", "tts-engines", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-tts-speak", "tts-speak", "\"$*\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-telephony-call", "telephony-call", "\"$1\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-telephony-cellinfo", "telephony-cellinfo", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-telephony-deviceinfo", "telephony-deviceinfo", "\"\"");
        File smsListScript = new File(getBinDir(), "termux-sms-list");
        FilesKt.writeText$default(smsListScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-sms-list\nTYPE=\"inbox\"\nLIMIT=10\nwhile getopts \"t:l:o:n:\" opt; do\n    case $opt in\n        t) TYPE=\"$OPTARG\" ;;\n        l) LIMIT=\"$OPTARG\" ;;\n        o) ;; # offset, ignored\n        n) LIMIT=\"$OPTARG\" ;;\n    esac\ndone\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"sms-list\" \\\n    --es api_args \"$TYPE|$LIMIT\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        smsListScript.setExecutable(true, false);
        File smsSendScript = new File(getBinDir(), "termux-sms-send");
        FilesKt.writeText$default(smsSendScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-sms-send\nNUMBER=\"\"\nwhile getopts \"n:\" opt; do\n    case $opt in\n        n) NUMBER=\"$OPTARG\" ;;\n    esac\ndone\nshift $((OPTIND-1))\nMESSAGE=\"$*\"\nif [ -z \"$NUMBER\" ] || [ -z \"$MESSAGE\" ]; then\n    echo \"Usage: termux-sms-send -n <number> <message>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"sms-send\" \\\n    --es api_args \"$NUMBER|$MESSAGE\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        smsSendScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-contact-list", "contact-list", "\"\"");
        File callLogScript = new File(getBinDir(), "termux-call-log");
        FilesKt.writeText$default(callLogScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-call-log\nLIMIT=10\nwhile getopts \"l:o:n:\" opt; do\n    case $opt in\n        l) LIMIT=\"$OPTARG\" ;;\n        n) LIMIT=\"$OPTARG\" ;;\n        o) ;; # offset, ignored\n    esac\ndone\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"call-log\" \\\n    --es api_args \"$LIMIT\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        callLogScript.setExecutable(true, false);
        File sensorScript = new File(getBinDir(), "termux-sensor");
        FilesKt.writeText$default(sensorScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-sensor\nSENSOR_TYPE=\"\"\nLIST_SENSORS=\"\"\nwhile getopts \"s:ln:d:c:\" opt; do\n    case $opt in\n        s) SENSOR_TYPE=\"$OPTARG\" ;;\n        l) LIST_SENSORS=\"list\" ;;\n        n) ;; # count\n        d) ;; # delay\n        c) ;; # cleanup\n    esac\ndone\nif [ -n \"$LIST_SENSORS\" ]; then\n    ARGS=\"list\"\nelse\n    ARGS=\"$SENSOR_TYPE\"\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"sensor\" \\\n    --es api_args \"$ARGS\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        sensorScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-fingerprint", "fingerprint", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-infrared-frequencies", "infrared-frequencies", "\"\"");
        File irTransmitScript = new File(getBinDir(), "termux-infrared-transmit");
        FilesKt.writeText$default(irTransmitScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-infrared-transmit\nFREQ=\"\"\nwhile getopts \"f:\" opt; do\n    case $opt in\n        f) FREQ=\"$OPTARG\" ;;\n    esac\ndone\nshift $((OPTIND-1))\nPATTERN=\"$*\"\nif [ -z \"$FREQ\" ]; then\n    echo \"Usage: termux-infrared-transmit -f <frequency> <pattern...>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"infrared-transmit\" \\\n    --es api_args \"$FREQ,$PATTERN\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        irTransmitScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-usb", "usb", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-wallpaper", "wallpaper", "\"$1\"");
        File downloadScript = new File(getBinDir(), "termux-download");
        FilesKt.writeText$default(downloadScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-download\nTITLE=\"Download\"\nDESC=\"\"\nURL=\"\"\nwhile getopts \"t:d:\" opt; do\n    case $opt in\n        t) TITLE=\"$OPTARG\" ;;\n        d) DESC=\"$OPTARG\" ;;\n    esac\ndone\nshift $((OPTIND-1))\nURL=\"$1\"\nif [ -z \"$URL\" ]; then\n    echo \"Usage: termux-download [-t title] [-d description] <url>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"download\" \\\n    --es api_args \"$URL|$TITLE|$DESC\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        downloadScript.setExecutable(true, false);
        File shareScript = new File(getBinDir(), "termux-share");
        FilesKt.writeText$default(shareScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-share\nACTION=\"text\"\nCONTENT=\"\"\nwhile getopts \"a:\" opt; do\n    case $opt in\n        a) ACTION=\"$OPTARG\" ;;\n    esac\ndone\nshift $((OPTIND-1))\nif [ -n \"$1\" ]; then\n    CONTENT=\"$1\"\nelse\n    # Read from stdin\n    CONTENT=$(cat)\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"share\" \\\n    --es api_args \"$ACTION|$CONTENT\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        shareScript.setExecutable(true, false);
        File dialogScript = new File(getBinDir(), "termux-dialog");
        FilesKt.writeText$default(dialogScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-dialog\nTITLE=\"Input\"\nHINT=\"\"\nwhile getopts \"t:i:\" opt; do\n    case $opt in\n        t) TITLE=\"$OPTARG\" ;;\n        i) HINT=\"$OPTARG\" ;;\n    esac\ndone\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"dialog\" \\\n    --es api_args \"$TITLE|$HINT\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.5\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        dialogScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-storage-get", "storage-get", "\"$1\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-job-scheduler", "job-scheduler", "\"$*\"");
        File openUrlScript = new File(getBinDir(), "termux-open-url");
        FilesKt.writeText$default(openUrlScript, "#!/data/data/com.termux/files/usr/bin/sh\n# v51: File-based URL opener - writes URL to file, MainActivity opens it\n\nif [ $# -lt 1 ]; then\n    echo 'usage: termux-open-url <url>'\n    echo 'Open a URL in browser.'\n    exit 1\nfi\n\nURL=\"$1\"\nURL_FILE=\"$HOME/.termux/url_to_open\"\n\n# Ensure .termux directory exists\nmkdir -p \"$HOME/.termux\"\n\n# Write URL to file - MainActivity polls for this and opens it\necho \"$URL\" > \"$URL_FILE\"\n\necho \"Opening: $URL\"\n# Give the Activity time to pick it up\nsleep 1\n", null, 2, null);
        openUrlScript.setExecutable(true, false);
        File termuxOpenScript = new File(getBinDir(), "termux-open");
        FilesKt.writeText$default(termuxOpenScript, "#!/data/data/com.termux/files/usr/bin/sh\nset -e -u\n\nSCRIPTNAME=termux-open\nshow_usage () {\n    echo \"Usage: $SCRIPTNAME [options] path-or-url\"\n    echo \"Open a file or URL in an external app.\"\n    echo \"  --send               if the file should be shared for sending\"\n    echo \"  --view               if the file should be shared for viewing (default)\"\n    echo \"  --chooser            if an app chooser should always be shown\"\n    echo \"  --content-type type  specify the content type to use\"\n    exit 0\n}\n\nTEMP=`getopt \\\n     -n $SCRIPTNAME \\\n     -o h \\\n     --long send,view,chooser,content-type:,help\\\n     -- \"$@\"`\neval set -- \"$TEMP\"\n\nACTION=android.intent.action.VIEW\nEXTRAS=\"\"\nCONTENT_TYPE=\"\"\nwhile true; do\n\tcase \"$1\" in\n\t\t--send) ACTION=\"android.intent.action.SEND\"; shift;;\n\t\t--view) ACTION=\"android.intent.action.VIEW\"; shift;;\n\t\t--chooser) EXTRAS=\"$EXTRAS --ez chooser true\"; shift;;\n\t\t--content-type) CONTENT_TYPE=\"$2\"; EXTRAS=\"$EXTRAS --es content-type $2\"; shift 2;;\n\t\t-h | --help) show_usage;;\n\t\t--) shift; break ;;\n\tesac\ndone\nif [ $# != 1 ]; then\n\tshow_usage\nfi\n\nTARGET=\"$1\"\n\n# Check if it's a URL (starts with http://, https://, etc.)\ncase \"$TARGET\" in\n    http://*|https://*|mailto:*|tel:*|sms:*|geo:*)\n        # For URLs, use direct am start (same as real Termux)\n        case \"${TERMUX__USER_ID:-}\" in ''|*[!0-9]*|0[0-9]*) TERMUX__USER_ID=0;; esac\n        if [ -n \"$CONTENT_TYPE\" ]; then\n            am start --user \"$TERMUX__USER_ID\" -a android.intent.action.VIEW -t \"$CONTENT_TYPE\" -d \"$TARGET\" > /dev/null\n        else\n            am start --user \"$TERMUX__USER_ID\" -a android.intent.action.VIEW -d \"$TARGET\" > /dev/null\n        fi\n        exit 0\n        ;;\nesac\n\n# For local files, resolve the path and use broadcast\nif [ -f \"$TARGET\" ]; then\n\tTARGET=$(realpath \"$TARGET\")\nfi\n\ncase \"${TERMUX__USER_ID:-}\" in ''|*[!0-9]*|0[0-9]*) TERMUX__USER_ID=0;; esac\n\nam broadcast --user \"$TERMUX__USER_ID\" \\\n\t-a \"$ACTION\" \\\n\t-n \"com.termux/com.termux.app.TermuxOpenReceiver\" \\\n\t$EXTRAS \\\n\t-d \"$TARGET\" \\\n\t> /dev/null 2>&1\n", null, 2, null);
        termuxOpenScript.setExecutable(true, false);
        File xdgOpenScript = new File(getBinDir(), "xdg-open");
        FilesKt.writeText$default(xdgOpenScript, "#!/data/data/com.termux/files/usr/bin/sh\n# xdg-open - wrapper around termux-open for freedesktop.org compatibility\nexec termux-open \"$@\"\n", null, 2, null);
        xdgOpenScript.setExecutable(true, false);
        File sensibleBrowserScript = new File(getBinDir(), "sensible-browser");
        FilesKt.writeText$default(sensibleBrowserScript, "#!/data/data/com.termux/files/usr/bin/sh\nexec termux-open-url \"$@\"\n", null, 2, null);
        sensibleBrowserScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-volume", "volume", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-wifi-connectioninfo", "wifi-connectioninfo", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-brightness", "brightness", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-torch", "torch", "\"${1:-on}\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-media-scan", "media-scan", "\"$1\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-tts-speak", "tts-speak", "\"$*\"");
        File storageScript = new File(getBinDir(), "termux-setup-storage");
        FilesKt.writeText$default(storageScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: Setup storage symlinks\nSTORAGE_DIR=\"" + getHomeDir().getAbsolutePath() + "/storage\"\nmkdir -p \"$STORAGE_DIR\"\nln -sf /sdcard \"$STORAGE_DIR/shared\"\nln -sf /sdcard/DCIM \"$STORAGE_DIR/dcim\"\nln -sf /sdcard/Download \"$STORAGE_DIR/downloads\"\nln -sf /sdcard/Pictures \"$STORAGE_DIR/pictures\"\nln -sf /sdcard/Music \"$STORAGE_DIR/music\"\nln -sf /sdcard/Movies \"$STORAGE_DIR/movies\"\necho \"Storage setup complete. Access via ~/storage/\"\n", null, 2, null);
        storageScript.setExecutable(true, false);
        File reloadScript = new File(getBinDir(), "termux-reload-settings");
        FilesKt.writeText$default(reloadScript, "#!/data/data/com.termux/files/usr/bin/bash\necho \"Settings reloaded\"\n", null, 2, null);
        reloadScript.setExecutable(true, false);
        File infoScript = new File(getBinDir(), "termux-info");
        FilesKt.writeText$default(infoScript, "#!/data/data/com.termux/files/usr/bin/bash\necho \"MobileCLI Terminal\"\necho \"==================\"\necho \"App: MobileCLI (com.termux)\"\necho \"HOME: $HOME\"\necho \"PREFIX: $PREFIX\"\necho \"Android: $(getprop ro.build.version.release)\"\necho \"Device: $(getprop ro.product.model)\"\n", null, 2, null);
        infoScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-keystore-list", "keystore-list", "\"\"");
        File keystoreScript = new File(getBinDir(), "termux-keystore");
        FilesKt.writeText$default(keystoreScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-keystore\n# Usage: termux-keystore <command> [args]\n# Commands: list, generate, delete, sign, verify\n\nCOMMAND=\"$1\"\nshift\n\ncase \"$COMMAND\" in\n    list)\n        RESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\n        am broadcast -a com.termux.api.API_CALL \\\n            --es api_method \"keystore-list\" \\\n            --es api_args \"\" \\\n            --es result_file \"$RESULT_FILE\" \\\n            > /dev/null 2>&1\n        sleep 0.3\n        if [ -f \"$RESULT_FILE\" ]; then\n            cat \"$RESULT_FILE\"\n            rm -f \"$RESULT_FILE\"\n        fi\n        ;;\n    generate)\n        ALIAS=\"\"\n        ALGORITHM=\"AES\"\n        SIZE=256\n        while getopts \"a:g:s:\" opt; do\n            case $opt in\n                a) ALIAS=\"$OPTARG\" ;;\n                g) ALGORITHM=\"$OPTARG\" ;;\n                s) SIZE=\"$OPTARG\" ;;\n            esac\n        done\n        if [ -z \"$ALIAS\" ]; then\n            echo \"Usage: termux-keystore generate -a <alias> [-g algorithm] [-s size]\"\n            exit 1\n        fi\n        RESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\n        am broadcast -a com.termux.api.API_CALL \\\n            --es api_method \"keystore-generate\" \\\n            --es api_args \"$ALIAS|$ALGORITHM|$SIZE\" \\\n            --es result_file \"$RESULT_FILE\" \\\n            > /dev/null 2>&1\n        sleep 0.3\n        if [ -f \"$RESULT_FILE\" ]; then\n            cat \"$RESULT_FILE\"\n            rm -f \"$RESULT_FILE\"\n        fi\n        ;;\n    delete)\n        ALIAS=\"$1\"\n        if [ -z \"$ALIAS\" ]; then\n            echo \"Usage: termux-keystore delete <alias>\"\n            exit 1\n        fi\n        RESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\n        am broadcast -a com.termux.api.API_CALL \\\n            --es api_method \"keystore-delete\" \\\n            --es api_args \"$ALIAS\" \\\n            --es result_file \"$RESULT_FILE\" \\\n            > /dev/null 2>&1\n        sleep 0.3\n        if [ -f \"$RESULT_FILE\" ]; then\n            cat \"$RESULT_FILE\"\n            rm -f \"$RESULT_FILE\"\n        fi\n        ;;\n    sign)\n        ALIAS=\"\"\n        DATA=\"\"\n        while getopts \"a:d:\" opt; do\n            case $opt in\n                a) ALIAS=\"$OPTARG\" ;;\n                d) DATA=\"$OPTARG\" ;;\n            esac\n        done\n        if [ -z \"$ALIAS\" ] || [ -z \"$DATA\" ]; then\n            echo \"Usage: termux-keystore sign -a <alias> -d <data>\"\n            exit 1\n        fi\n        RESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\n        am broadcast -a com.termux.api.API_CALL \\\n            --es api_method \"keystore-sign\" \\\n            --es api_args \"$ALIAS|$DATA\" \\\n            --es result_file \"$RESULT_FILE\" \\\n            > /dev/null 2>&1\n        sleep 0.3\n        if [ -f \"$RESULT_FILE\" ]; then\n            cat \"$RESULT_FILE\"\n            rm -f \"$RESULT_FILE\"\n        fi\n        ;;\n    verify)\n        ALIAS=\"\"\n        SIGNATURE=\"\"\n        IV=\"\"\n        while getopts \"a:s:i:\" opt; do\n            case $opt in\n                a) ALIAS=\"$OPTARG\" ;;\n                s) SIGNATURE=\"$OPTARG\" ;;\n                i) IV=\"$OPTARG\" ;;\n            esac\n        done\n        if [ -z \"$ALIAS\" ] || [ -z \"$SIGNATURE\" ] || [ -z \"$IV\" ]; then\n            echo \"Usage: termux-keystore verify -a <alias> -s <signature> -i <iv>\"\n            exit 1\n        fi\n        RESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\n        am broadcast -a com.termux.api.API_CALL \\\n            --es api_method \"keystore-verify\" \\\n            --es api_args \"$ALIAS|$SIGNATURE|$IV\" \\\n            --es result_file \"$RESULT_FILE\" \\\n            > /dev/null 2>&1\n        sleep 0.3\n        if [ -f \"$RESULT_FILE\" ]; then\n            cat \"$RESULT_FILE\"\n            rm -f \"$RESULT_FILE\"\n        fi\n        ;;\n    *)\n        echo \"Usage: termux-keystore <command> [args]\"\n        echo \"Commands:\"\n        echo \"  list                   List all keys in the keystore\"\n        echo \"  generate -a <alias>    Generate a new key\"\n        echo \"  delete <alias>         Delete a key\"\n        echo \"  sign -a <alias> -d <data>   Sign data with a key\"\n        echo \"  verify -a <alias> -s <sig> -i <iv>   Verify signature\"\n        exit 1\n        ;;\nesac\n", null, 2, null);
        keystoreScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-nfc", "nfc", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-notification-list", "notification-list", "\"\"");
        installApiScripts$createApiScript(this, tmpDir, "termux-speech-to-text", "speech-to-text", "\"\"");
        File safLsScript = new File(getBinDir(), "termux-saf-ls");
        FilesKt.writeText$default(safLsScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-ls\n# List contents of a SAF directory\nif [ -z \"$1\" ]; then\n    echo \"Usage: termux-saf-ls <document_uri>\"\n    echo \"Use termux-saf-managedir to get a directory URI first\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-ls\" \\\n    --es api_args \"$1\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safLsScript.setExecutable(true, false);
        File safStatScript = new File(getBinDir(), "termux-saf-stat");
        FilesKt.writeText$default(safStatScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-stat\nif [ -z \"$1\" ]; then\n    echo \"Usage: termux-saf-stat <document_uri>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-stat\" \\\n    --es api_args \"$1\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safStatScript.setExecutable(true, false);
        File safReadScript = new File(getBinDir(), "termux-saf-read");
        FilesKt.writeText$default(safReadScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-read\nif [ -z \"$1\" ]; then\n    echo \"Usage: termux-saf-read <document_uri>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-read\" \\\n    --es api_args \"$1\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safReadScript.setExecutable(true, false);
        File safWriteScript = new File(getBinDir(), "termux-saf-write");
        FilesKt.writeText$default(safWriteScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-write\nif [ -z \"$1\" ]; then\n    echo \"Usage: termux-saf-write <document_uri> [content]\"\n    echo \"If no content provided, reads from stdin\"\n    exit 1\nfi\nURI=\"$1\"\nshift\nif [ -n \"$*\" ]; then\n    CONTENT=\"$*\"\nelse\n    CONTENT=$(cat)\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-write\" \\\n    --es api_args \"$URI|$CONTENT\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safWriteScript.setExecutable(true, false);
        File safMkdirScript = new File(getBinDir(), "termux-saf-mkdir");
        FilesKt.writeText$default(safMkdirScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-mkdir\nif [ -z \"$1\" ] || [ -z \"$2\" ]; then\n    echo \"Usage: termux-saf-mkdir <parent_uri> <directory_name>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-mkdir\" \\\n    --es api_args \"$1|$2\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safMkdirScript.setExecutable(true, false);
        File safRmScript = new File(getBinDir(), "termux-saf-rm");
        FilesKt.writeText$default(safRmScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-rm\nif [ -z \"$1\" ]; then\n    echo \"Usage: termux-saf-rm <document_uri>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-rm\" \\\n    --es api_args \"$1\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safRmScript.setExecutable(true, false);
        File safCreateScript = new File(getBinDir(), "termux-saf-create");
        FilesKt.writeText$default(safCreateScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-create\nMIME=\"text/plain\"\nwhile getopts \"m:\" opt; do\n    case $opt in\n        m) MIME=\"$OPTARG\" ;;\n    esac\ndone\nshift $((OPTIND-1))\nif [ -z \"$1\" ] || [ -z \"$2\" ]; then\n    echo \"Usage: termux-saf-create [-m mime_type] <parent_uri> <file_name>\"\n    exit 1\nfi\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"saf-create\" \\\n    --es api_args \"$1|$2|$MIME\" \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        safCreateScript.setExecutable(true, false);
        File safManagedirScript = new File(getBinDir(), "termux-saf-managedir");
        FilesKt.writeText$default(safManagedirScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: termux-saf-managedir\n# Opens system UI to select a directory for SAF access\necho \"Opening directory picker...\"\necho \"After selecting a directory, the URI will be available for other saf-* commands.\"\nam start -a android.intent.action.OPEN_DOCUMENT_TREE\n", null, 2, null);
        safManagedirScript.setExecutable(true, false);
        installApiScripts$createApiScript(this, tmpDir, "termux-saf-dirs", "saf-dirs", "\"\"");
        File wakeLockScript = new File(getBinDir(), "termux-wake-lock");
        FilesKt.writeText$default(wakeLockScript, "#!/data/data/com.termux/files/usr/bin/sh\n\nif [ $# != 0 ]; then\n\techo 'usage: termux-wake-lock'\n\techo 'Acquire the MobileCLI wake lock to prevent the CPU from sleeping.'\n\texit 1\nfi\n\ncase \"${TERMUX__USER_ID:-}\" in ''|*[!0-9]*|0[0-9]*) TERMUX__USER_ID=0;; esac\n\nam startservice \\\n\t--user \"$TERMUX__USER_ID\" \\\n\t-a \"com.termux.service_wake_lock\" \\\n\t\"com.termux/com.termux.app.TermuxService\" \\\n\t> /dev/null\n\necho \"Wake lock acquired. CPU and WiFi will stay awake.\"\n", null, 2, null);
        wakeLockScript.setExecutable(true, false);
        File wakeUnlockScript = new File(getBinDir(), "termux-wake-unlock");
        FilesKt.writeText$default(wakeUnlockScript, "#!/data/data/com.termux/files/usr/bin/sh\n\nif [ $# != 0 ]; then\n\techo 'usage: termux-wake-unlock'\n\techo 'Release the MobileCLI wake lock to allow the CPU to sleep.'\n\texit 1\nfi\n\ncase \"${TERMUX__USER_ID:-}\" in ''|*[!0-9]*|0[0-9]*) TERMUX__USER_ID=0;; esac\n\nam startservice \\\n\t--user \"$TERMUX__USER_ID\" \\\n\t-a \"com.termux.service_wake_unlock\" \\\n\t\"com.termux/com.termux.app.TermuxService\" \\\n\t> /dev/null\n\necho \"Wake lock released. Device can sleep now.\"\n", null, 2, null);
        wakeUnlockScript.setExecutable(true, false);
        File changeRepoScript = new File(getBinDir(), "termux-change-repo");
        FilesKt.writeText$default(changeRepoScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-change-repo\n# Change the package repository mirror\n\necho \"MobileCLI Package Repository Selector\"\necho \"======================================\"\necho \"\"\necho \"Available mirrors:\"\necho \"1) Default (packages.termux.dev)\"\necho \"2) Grimler (grimler.se)\"\necho \"3) A1Batross (a1batross.github.io)\"\necho \"4) BFSU China (mirrors.bfsu.edu.cn)\"\necho \"5) Tsinghua China (mirrors.tuna.tsinghua.edu.cn)\"\necho \"6) USTC China (mirrors.ustc.edu.cn)\"\necho \"\"\n\nread -p \"Select mirror [1-6]: \" choice\n\ncase \"$choice\" in\n    1)\n        MIRROR=\"https://packages.termux.dev/apt/termux-main\"\n        ;;\n    2)\n        MIRROR=\"https://grimler.se/termux/termux-main\"\n        ;;\n    3)\n        MIRROR=\"https://a1batross.github.io/termux-main\"\n        ;;\n    4)\n        MIRROR=\"https://mirrors.bfsu.edu.cn/termux/apt/termux-main\"\n        ;;\n    5)\n        MIRROR=\"https://mirrors.tuna.tsinghua.edu.cn/termux/apt/termux-main\"\n        ;;\n    6)\n        MIRROR=\"https://mirrors.ustc.edu.cn/termux/apt/termux-main\"\n        ;;\n    *)\n        echo \"Invalid selection\"\n        exit 1\n        ;;\nesac\n\necho \"\"\necho \"Setting mirror to: $MIRROR\"\n\n# Update sources.list\nmkdir -p \"$PREFIX/etc/apt\"\necho \"deb $MIRROR stable main\" > \"$PREFIX/etc/apt/sources.list\"\n\necho \"Done! Run 'pkg update' to refresh package lists.\"\n", null, 2, null);
        changeRepoScript.setExecutable(true, false);
        File fixShebangScript = new File(getBinDir(), "termux-fix-shebang");
        FilesKt.writeText$default(fixShebangScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-fix-shebang\n# Fix shebangs in scripts to use Termux paths\n\nif [ $# -lt 1 ]; then\n    echo \"Usage: termux-fix-shebang <file> [file2] ...\"\n    echo \"Fix script shebangs to use Termux paths\"\n    exit 1\nfi\n\nfor file in \"$@\"; do\n    if [ ! -f \"$file\" ]; then\n        echo \"File not found: $file\"\n        continue\n    fi\n\n    # Fix common shebangs\n    sed -i 's|#!/bin/bash|#!/data/data/com.termux/files/usr/bin/bash|g' \"$file\"\n    sed -i 's|#!/usr/bin/bash|#!/data/data/com.termux/files/usr/bin/bash|g' \"$file\"\n    sed -i 's|#!/bin/sh|#!/data/data/com.termux/files/usr/bin/sh|g' \"$file\"\n    sed -i 's|#!/usr/bin/sh|#!/data/data/com.termux/files/usr/bin/sh|g' \"$file\"\n    sed -i 's|#!/usr/bin/env |#!/data/data/com.termux/files/usr/bin/env |g' \"$file\"\n    sed -i 's|#!/bin/env |#!/data/data/com.termux/files/usr/bin/env |g' \"$file\"\n    sed -i 's|#!/usr/bin/python|#!/data/data/com.termux/files/usr/bin/python|g' \"$file\"\n    sed -i 's|#!/usr/bin/perl|#!/data/data/com.termux/files/usr/bin/perl|g' \"$file\"\n    sed -i 's|#!/usr/bin/ruby|#!/data/data/com.termux/files/usr/bin/ruby|g' \"$file\"\n    sed -i 's|#!/usr/bin/node|#!/data/data/com.termux/files/usr/bin/node|g' \"$file\"\n\n    echo \"Fixed: $file\"\ndone\n", null, 2, null);
        fixShebangScript.setExecutable(true, false);
        File resetScript = new File(getBinDir(), "termux-reset");
        FilesKt.writeText$default(resetScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-reset\n# Reset to a clean state (keeps home directory)\n\necho \"MobileCLI Reset Utility\"\necho \"=======================\"\necho \"\"\necho \"WARNING: This will remove all installed packages!\"\necho \"Your home directory files will be preserved.\"\necho \"\"\n\nread -p \"Are you sure? (yes/no): \" confirm\n\nif [ \"$confirm\" != \"yes\" ]; then\n    echo \"Cancelled.\"\n    exit 0\nfi\n\necho \"\"\necho \"Removing installed packages...\"\n\n# Remove all packages except essentials\npkg list-installed 2>/dev/null | while read pkg; do\n    name=$(echo \"$pkg\" | cut -d/ -f1)\n    case \"$name\" in\n        apt|bash|coreutils|dash|dpkg|findutils|gawk|grep|gzip|less|libandroid*|libc*|ncurses*|readline|sed|tar|termux*)\n            # Keep essential packages\n            ;;\n        *)\n            pkg uninstall -y \"$name\" 2>/dev/null\n            ;;\n    esac\ndone\n\necho \"\"\necho \"Clearing package cache...\"\napt clean\n\necho \"\"\necho \"Reset complete. Run 'pkg update && pkg upgrade' to refresh.\"\n", null, 2, null);
        resetScript.setExecutable(true, false);
        File backupScript = new File(getBinDir(), "termux-backup");
        FilesKt.writeText$default(backupScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-backup\n# Backup home directory to a tar.gz file\n\nOUTPUT=\"$1\"\n\nif [ -z \"$OUTPUT\" ]; then\n    TIMESTAMP=$(date +%Y%m%d_%H%M%S)\n    OUTPUT=\"/sdcard/Download/termux-backup-$TIMESTAMP.tar.gz\"\nfi\n\necho \"MobileCLI Backup Utility\"\necho \"========================\"\necho \"\"\necho \"Backing up home directory to: $OUTPUT\"\necho \"\"\n\ncd \"$HOME\" || exit 1\n\n# Create backup excluding node_modules and caches\ntar -czf \"$OUTPUT\" \\\n    --exclude='node_modules' \\\n    --exclude='.npm' \\\n    --exclude='.cache' \\\n    --exclude='.gradle' \\\n    --exclude='*.apk' \\\n    .\n\nif [ $? -eq 0 ]; then\n    SIZE=$(ls -lh \"$OUTPUT\" | awk '{print $5}')\n    echo \"\"\n    echo \"Backup complete!\"\n    echo \"File: $OUTPUT\"\n    echo \"Size: $SIZE\"\nelse\n    echo \"Backup failed!\"\n    exit 1\nfi\n", null, 2, null);
        backupScript.setExecutable(true, false);
        File restoreScript = new File(getBinDir(), "termux-restore");
        FilesKt.writeText$default(restoreScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-restore\n# Restore home directory from a tar.gz backup\n\nINPUT=\"$1\"\n\nif [ -z \"$INPUT\" ]; then\n    echo \"Usage: termux-restore <backup-file.tar.gz>\"\n    echo \"\"\n    echo \"Available backups in /sdcard/Download/:\"\n    ls -lh /sdcard/Download/termux-backup-*.tar.gz 2>/dev/null || echo \"  (none found)\"\n    exit 1\nfi\n\nif [ ! -f \"$INPUT\" ]; then\n    echo \"File not found: $INPUT\"\n    exit 1\nfi\n\necho \"MobileCLI Restore Utility\"\necho \"=========================\"\necho \"\"\necho \"WARNING: This will overwrite existing files in your home directory!\"\necho \"Backup file: $INPUT\"\necho \"\"\n\nread -p \"Are you sure? (yes/no): \" confirm\n\nif [ \"$confirm\" != \"yes\" ]; then\n    echo \"Cancelled.\"\n    exit 0\nfi\n\ncd \"$HOME\" || exit 1\n\necho \"\"\necho \"Restoring...\"\n\ntar -xzf \"$INPUT\"\n\nif [ $? -eq 0 ]; then\n    echo \"\"\n    echo \"Restore complete!\"\n    echo \"You may need to restart the terminal for all changes to take effect.\"\nelse\n    echo \"Restore failed!\"\n    exit 1\nfi\n", null, 2, null);
        restoreScript.setExecutable(true, false);
        File fileEditorScript = new File(getBinDir(), "termux-file-editor");
        FilesKt.writeText$default(fileEditorScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-file-editor\n# Open a file in the system file editor\n\nif [ $# -lt 1 ]; then\n    echo \"Usage: termux-file-editor <file>\"\n    exit 1\nfi\n\nFILE=\"$1\"\n\nif [ ! -f \"$FILE\" ]; then\n    echo \"File not found: $FILE\"\n    exit 1\nfi\n\n# Get absolute path\nABSPATH=$(realpath \"$FILE\")\n\n# Try to open with system\nam start -a android.intent.action.EDIT -d \"file://$ABSPATH\" -t \"text/plain\"\n", null, 2, null);
        fileEditorScript.setExecutable(true, false);
        File urlOpenerScript = new File(getBinDir(), "termux-url-opener");
        FilesKt.writeText$default(urlOpenerScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-url-opener\n# Handle URLs opened from other apps (placeholder - customize in ~/.termux/termux-url-opener)\n\nURL=\"$1\"\n\n# Check for user script\nif [ -x \"$HOME/.termux/termux-url-opener\" ]; then\n    exec \"$HOME/.termux/termux-url-opener\" \"$URL\"\nfi\n\n# Default behavior: print URL\necho \"URL received: $URL\"\necho \"\"\necho \"To customize URL handling, create ~/.termux/termux-url-opener\"\n", null, 2, null);
        urlOpenerScript.setExecutable(true, false);
        File fileOpenerScript = new File(getBinDir(), "termux-file-opener");
        FilesKt.writeText$default(fileOpenerScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: termux-file-opener\n# Handle files opened from other apps (placeholder - customize in ~/.termux/termux-file-opener)\n\nFILE=\"$1\"\n\n# Check for user script\nif [ -x \"$HOME/.termux/termux-file-opener\" ]; then\n    exec \"$HOME/.termux/termux-file-opener\" \"$FILE\"\nfi\n\n# Default behavior: show file info\nif [ -f \"$FILE\" ]; then\n    echo \"File received: $FILE\"\n    echo \"\"\n    ls -la \"$FILE\"\n    echo \"\"\n    file \"$FILE\" 2>/dev/null\nelse\n    echo \"File not found: $FILE\"\nfi\n\necho \"\"\necho \"To customize file handling, create ~/.termux/termux-file-opener\"\n", null, 2, null);
        fileOpenerScript.setExecutable(true, false);
        File pkgConfigWrapper = new File(getBinDir(), "pkg-config");
        if (!pkgConfigWrapper.exists()) {
            FilesKt.writeText$default(pkgConfigWrapper, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: pkg-config wrapper\n# Wrapper that works even if pkg-config isn't installed\n\nif command -v /data/data/com.termux/files/usr/bin/pkgconf &> /dev/null; then\n    exec /data/data/com.termux/files/usr/bin/pkgconf \"$@\"\nelif [ -f /data/data/com.termux/files/usr/bin/pkgconf ]; then\n    exec /data/data/com.termux/files/usr/bin/pkgconf \"$@\"\nelse\n    echo \"pkg-config not found. Install with: pkg install pkg-config\" >&2\n    exit 1\nfi\n", null, 2, null);
            pkgConfigWrapper.setExecutable(true, false);
        }
        File apkToolsScript = new File(getBinDir(), "install-apk-tools");
        FilesKt.writeText$default(apkToolsScript, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI: install-apk-tools\n# Install APK decompilation and modification tools\n# Enables TEST CLAUDE to fully self-modify the MobileCLI app\n\necho \"MobileCLI APK Tools Installer\"\necho \"=============================\"\necho \"\"\necho \"This will install tools for APK modification:\"\necho \"  - apktool: Decode/rebuild APK resources & smali\"\necho \"  - jadx: Decompile dex to readable Java\"\necho \"  - smali/baksmali: Assemble/disassemble dex bytecode\"\necho \"\"\n\n# Create tools directory\nTOOLS_DIR=\"$PREFIX/share/apk-tools\"\nmkdir -p \"$TOOLS_DIR\"\ncd \"$TOOLS_DIR\"\n\necho \"Installing apktool...\"\ncurl -L -o apktool.jar \"https://github.com/iBotPeaches/Apktool/releases/download/v2.9.3/apktool_2.9.3.jar\" 2>/dev/null\nif [ -f \"apktool.jar\" ]; then\n    cat > \"$PREFIX/bin/apktool\" << 'APKTOOL_EOF'\n#!/data/data/com.termux/files/usr/bin/sh\nexec java -jar $PREFIX/share/apk-tools/apktool.jar \"$@\"\nAPKTOOL_EOF\n    chmod +x \"$PREFIX/bin/apktool\"\n    echo \"  apktool installed!\"\nelse\n    echo \"  apktool download failed\"\nfi\n\necho \"Installing smali/baksmali...\"\ncurl -L -o smali.jar \"https://github.com/google/smali/releases/download/v2.5.2/smali-2.5.2.jar\" 2>/dev/null\ncurl -L -o baksmali.jar \"https://github.com/google/smali/releases/download/v2.5.2/baksmali-2.5.2.jar\" 2>/dev/null\nif [ -f \"smali.jar\" ]; then\n    cat > \"$PREFIX/bin/smali\" << 'SMALI_EOF'\n#!/data/data/com.termux/files/usr/bin/sh\nexec java -jar $PREFIX/share/apk-tools/smali.jar \"$@\"\nSMALI_EOF\n    chmod +x \"$PREFIX/bin/smali\"\n    echo \"  smali installed!\"\nfi\nif [ -f \"baksmali.jar\" ]; then\n    cat > \"$PREFIX/bin/baksmali\" << 'BAKSMALI_EOF'\n#!/data/data/com.termux/files/usr/bin/sh\nexec java -jar $PREFIX/share/apk-tools/baksmali.jar \"$@\"\nBAKSMALI_EOF\n    chmod +x \"$PREFIX/bin/baksmali\"\n    echo \"  baksmali installed!\"\nfi\n\necho \"Installing jadx...\"\nJADX_URL=\"https://github.com/skylot/jadx/releases/download/v1.5.0/jadx-1.5.0.zip\"\ncurl -L -o jadx.zip \"$JADX_URL\" 2>/dev/null\nif [ -f \"jadx.zip\" ]; then\n    unzip -q -o jadx.zip -d jadx/ 2>/dev/null\n    if [ -f \"jadx/bin/jadx\" ]; then\n        ln -sf \"$TOOLS_DIR/jadx/bin/jadx\" \"$PREFIX/bin/jadx\"\n        ln -sf \"$TOOLS_DIR/jadx/bin/jadx-gui\" \"$PREFIX/bin/jadx-gui\"\n        chmod +x jadx/bin/*\n        echo \"  jadx installed!\"\n    fi\n    rm -f jadx.zip\nelse\n    echo \"  jadx download failed\"\nfi\n\n# Create debug keystore if not exists\necho \"\"\necho \"Creating debug keystore...\"\nKEYSTORE_DIR=\"$HOME/.android\"\nmkdir -p \"$KEYSTORE_DIR\"\nif [ ! -f \"$KEYSTORE_DIR/debug.keystore\" ]; then\n    keytool -genkey -v -keystore \"$KEYSTORE_DIR/debug.keystore\" \\\n        -alias androiddebugkey -keyalg RSA -keysize 2048 -validity 10000 \\\n        -storepass android -keypass android \\\n        -dname \"CN=MobileCLI Debug,O=MobileCLI,C=US\" 2>/dev/null\n    if [ -f \"$KEYSTORE_DIR/debug.keystore\" ]; then\n        echo \"  Debug keystore created at $KEYSTORE_DIR/debug.keystore\"\n    fi\nelse\n    echo \"  Debug keystore already exists\"\nfi\n\necho \"\"\necho \"APK Tools Installation Complete!\"\necho \"\"\necho \"Available commands:\"\necho \"  apktool d <apk>     - Decode APK to folder\"\necho \"  apktool b <folder>  - Rebuild APK from folder\"\necho \"  jadx <apk>          - Decompile APK to Java source\"\necho \"  baksmali d <dex>    - Disassemble dex to smali\"\necho \"  smali a <folder>    - Assemble smali to dex\"\necho \"\"\necho \"Self-modification workflow:\"\necho \"  1. apktool d MobileCLI.apk\"\necho \"  2. Edit smali files or resources\"\necho \"  3. apktool b MobileCLI/\"\necho \"  4. zipalign -v 4 MobileCLI/dist/*.apk aligned.apk\"\necho \"  5. apksigner sign --ks ~/.android/debug.keystore aligned.apk\"\n", null, 2, null);
        apkToolsScript.setExecutable(true, false);
        Log.i(TAG, "Installed Termux API scripts (60+ commands) + utility scripts + APK tools installer");
    }

    static /* synthetic */ void installApiScripts$createApiScript$default(BootstrapInstaller bootstrapInstaller, File file, String str, String str2, String str3, int i, Object obj) {
        if ((i & 16) != 0) {
            str3 = "\"$@\"";
        }
        installApiScripts$createApiScript(bootstrapInstaller, file, str, str2, str3);
    }

    private static final void installApiScripts$createApiScript(BootstrapInstaller this$0, File tmpDir, String name, String method, String argsHandling) {
        File script = new File(this$0.getBinDir(), name);
        FilesKt.writeText$default(script, "#!/data/data/com.termux/files/usr/bin/bash\n# MobileCLI API: " + name + "\nRESULT_FILE=\"" + tmpDir.getAbsolutePath() + "/api_result_$$\"\nam broadcast -a com.termux.api.API_CALL \\\n    --es api_method \"" + method + "\" \\\n    --es api_args " + argsHandling + " \\\n    --es result_file \"$RESULT_FILE\" \\\n    > /dev/null 2>&1\nsleep 0.3\nif [ -f \"$RESULT_FILE\" ]; then\n    cat \"$RESULT_FILE\"\n    rm -f \"$RESULT_FILE\"\nfi\n", null, 2, null);
        script.setExecutable(true, false);
    }
}
