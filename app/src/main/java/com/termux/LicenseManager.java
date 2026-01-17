package com.termux;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.json.JSONObject;

/* compiled from: LicenseManager.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bJ\b\u0010\f\u001a\u0004\u0018\u00010\u000bJ\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0086@¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0015\u001a\u00020\u0012H\u0086@¢\u0006\u0002\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/termux/LicenseManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "clearLicense", "", "getDeviceId", "", "getLicenseKey", "getUserTier", "hasProAccess", "", "isLicenseValid", "registerDevice", "Lcom/termux/LicenseManager$LicenseResult;", "licenseKey", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyLicense", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "LicenseResult", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class LicenseManager {
    private static final String API_BASE = "https://mwxlguqukyfberyhtkmg.supabase.co/rest/v1";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_EXPIRES_AT = "expires_at";
    private static final String KEY_LAST_VERIFIED = "last_verified";
    private static final String KEY_LICENSE_KEY = "license_key";
    private static final int KEY_OFFLINE_GRACE_DAYS = 7;
    private static final String KEY_USER_TIER = "user_tier";
    private static final String PREFS_NAME = "mobilecli_license";
    private static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im13eGxndXF1a3lmYmVyeWh0a21nIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njc0OTg5ODgsImV4cCI6MjA4MzA3NDk4OH0.VdpU9WzYpTyLeVX9RaXKBP3dNNNf0t9YkQfVf7x_TA8";
    private final Context context;
    private final SharedPreferences prefs;

    public LicenseManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, 0);
    }

    public final String getDeviceId() {
        String deviceId;
        String stored = this.prefs.getString(KEY_DEVICE_ID, null);
        if (stored != null) {
            return stored;
        }
        try {
            deviceId = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
            if (deviceId == null) {
                deviceId = UUID.randomUUID().toString();
                Intrinsics.checkNotNullExpressionValue(deviceId, "toString(...)");
            }
        } catch (Exception e) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNull(string);
            deviceId = string;
        }
        this.prefs.edit().putString(KEY_DEVICE_ID, deviceId).apply();
        return deviceId;
    }

    public final String getLicenseKey() {
        return this.prefs.getString(KEY_LICENSE_KEY, null);
    }

    public final String getUserTier() {
        String string = this.prefs.getString(KEY_USER_TIER, "free");
        return string == null ? "free" : string;
    }

    public final boolean isLicenseValid() {
        if (getLicenseKey() == null) {
            return true;
        }
        long expiresAt = this.prefs.getLong(KEY_EXPIRES_AT, 0L);
        long lastVerified = this.prefs.getLong(KEY_LAST_VERIFIED, 0L);
        if (lastVerified == 0) {
            return false;
        }
        long daysSinceVerified = (System.currentTimeMillis() - lastVerified) / 86400000;
        if (daysSinceVerified > 7) {
            return false;
        }
        return expiresAt <= 0 || System.currentTimeMillis() <= expiresAt;
    }

    public final boolean hasProAccess() {
        String tier = getUserTier();
        return Intrinsics.areEqual(tier, "pro") || Intrinsics.areEqual(tier, "team");
    }

    /* compiled from: LicenseManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/termux/LicenseManager$LicenseResult;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.LicenseManager$verifyLicense$2", f = "LicenseManager.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.LicenseManager$verifyLicense$2, reason: invalid class name and case insensitive filesystem */
    static final class C00342 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super LicenseResult>, Object> {
        int label;

        C00342(Continuation<? super C00342> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return LicenseManager.this.new C00342(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super LicenseResult> continuation) {
            return ((C00342) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    String licenseKey = LicenseManager.this.getLicenseKey();
                    if (licenseKey == null) {
                        return new LicenseResult(true, "free", "Free tier - upgrade for Pro features");
                    }
                    try {
                        String deviceId = LicenseManager.this.getDeviceId();
                        URL url = new URL("https://mwxlguqukyfberyhtkmg.supabase.co/rest/v1/rpc/verify_license");
                        URLConnection uRLConnectionOpenConnection = url.openConnection();
                        Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                        HttpURLConnection connection = (HttpURLConnection) uRLConnectionOpenConnection;
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("apikey", LicenseManager.SUPABASE_ANON_KEY);
                        connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im13eGxndXF1a3lmYmVyeWh0a21nIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njc0OTg5ODgsImV4cCI6MjA4MzA3NDk4OH0.VdpU9WzYpTyLeVX9RaXKBP3dNNNf0t9YkQfVf7x_TA8");
                        connection.setDoOutput(true);
                        JSONObject $this$invokeSuspend_u24lambda_u240 = new JSONObject();
                        $this$invokeSuspend_u24lambda_u240.put("p_license_key", licenseKey);
                        $this$invokeSuspend_u24lambda_u240.put("p_device_id", deviceId);
                        OutputStream outputStream = connection.getOutputStream();
                        try {
                            OutputStream os = outputStream;
                            String string = $this$invokeSuspend_u24lambda_u240.toString();
                            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                            byte[] bytes = string.getBytes(Charsets.UTF_8);
                            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                            os.write(bytes);
                            Unit unit = Unit.INSTANCE;
                            CloseableKt.closeFinally(outputStream, null);
                            int responseCode = connection.getResponseCode();
                            if (responseCode == 200) {
                                InputStream inputStream = connection.getInputStream();
                                Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
                                Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
                                String response = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                                JSONObject json = new JSONObject(response);
                                boolean valid = json.optBoolean("valid", false);
                                String tier = json.optString("tier", "free");
                                String expiresAt = json.optString(LicenseManager.KEY_EXPIRES_AT, "");
                                if (valid) {
                                    SharedPreferences.Editor $this$invokeSuspend_u24lambda_u242 = LicenseManager.this.prefs.edit();
                                    $this$invokeSuspend_u24lambda_u242.putString(LicenseManager.KEY_USER_TIER, tier);
                                    $this$invokeSuspend_u24lambda_u242.putLong(LicenseManager.KEY_LAST_VERIFIED, System.currentTimeMillis());
                                    Intrinsics.checkNotNull(expiresAt);
                                    if (expiresAt.length() > 0) {
                                        try {
                                            Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(expiresAt);
                                            long expMillis = date != null ? date.getTime() : 0L;
                                            $this$invokeSuspend_u24lambda_u242.putLong(LicenseManager.KEY_EXPIRES_AT, expMillis);
                                        } catch (Exception e) {
                                        }
                                    }
                                    $this$invokeSuspend_u24lambda_u242.apply();
                                    Intrinsics.checkNotNull(tier);
                                    return new LicenseResult(true, tier, "License verified");
                                }
                                String error = json.optString("error", "Invalid license");
                                Intrinsics.checkNotNull(error);
                                return new LicenseResult(false, "free", error);
                            }
                            if (LicenseManager.this.isLicenseValid()) {
                                return new LicenseResult(true, LicenseManager.this.getUserTier(), "Offline mode - using cached license");
                            }
                            return new LicenseResult(false, "free", "Could not verify license");
                        } finally {
                        }
                    } catch (Exception e2) {
                        if (LicenseManager.this.isLicenseValid()) {
                            return new LicenseResult(true, LicenseManager.this.getUserTier(), "Offline mode - using cached license");
                        }
                        return new LicenseResult(false, "free", "Network error: " + e2.getMessage());
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    public final Object verifyLicense(Continuation<? super LicenseResult> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new C00342(null), continuation);
    }

    /* compiled from: LicenseManager.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "Lcom/termux/LicenseManager$LicenseResult;", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.LicenseManager$registerDevice$2", f = "LicenseManager.kt", i = {}, l = {225}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.LicenseManager$registerDevice$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super LicenseResult>, Object> {
        final /* synthetic */ String $licenseKey;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$licenseKey = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return LicenseManager.this.new AnonymousClass2(this.$licenseKey, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super LicenseResult> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object $result) throws Throwable {
            Exception e;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure($result);
                    try {
                        LicenseManager.this.getDeviceId();
                        String str = Build.MODEL;
                        LicenseManager.this.prefs.edit().putString(LicenseManager.KEY_LICENSE_KEY, this.$licenseKey).apply();
                        this.label = 1;
                        Object objVerifyLicense = LicenseManager.this.verifyLicense(this);
                        if (objVerifyLicense == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                        return objVerifyLicense;
                    } catch (Exception e2) {
                        e = e2;
                        break;
                    }
                case 1:
                    try {
                        ResultKt.throwOnFailure($result);
                        return $result;
                    } catch (Exception e3) {
                        e = e3;
                        break;
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return new LicenseResult(false, "free", "Registration failed: " + e.getMessage());
        }
    }

    public final Object registerDevice(String licenseKey, Continuation<? super LicenseResult> continuation) {
        return BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass2(licenseKey, null), continuation);
    }

    public final void clearLicense() {
        this.prefs.edit().clear().apply();
    }

    /* compiled from: LicenseManager.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lcom/termux/LicenseManager$LicenseResult;", "", "valid", "", "tier", "", "message", "(ZLjava/lang/String;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getTier", "getValid", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class LicenseResult {
        private final String message;
        private final String tier;
        private final boolean valid;

        public static /* synthetic */ LicenseResult copy$default(LicenseResult licenseResult, boolean z, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = licenseResult.valid;
            }
            if ((i & 2) != 0) {
                str = licenseResult.tier;
            }
            if ((i & 4) != 0) {
                str2 = licenseResult.message;
            }
            return licenseResult.copy(z, str, str2);
        }

        /* renamed from: component1, reason: from getter */
        public final boolean getValid() {
            return this.valid;
        }

        /* renamed from: component2, reason: from getter */
        public final String getTier() {
            return this.tier;
        }

        /* renamed from: component3, reason: from getter */
        public final String getMessage() {
            return this.message;
        }

        public final LicenseResult copy(boolean valid, String tier, String message) {
            Intrinsics.checkNotNullParameter(tier, "tier");
            Intrinsics.checkNotNullParameter(message, "message");
            return new LicenseResult(valid, tier, message);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LicenseResult)) {
                return false;
            }
            LicenseResult licenseResult = (LicenseResult) other;
            return this.valid == licenseResult.valid && Intrinsics.areEqual(this.tier, licenseResult.tier) && Intrinsics.areEqual(this.message, licenseResult.message);
        }

        public int hashCode() {
            return (((Boolean.hashCode(this.valid) * 31) + this.tier.hashCode()) * 31) + this.message.hashCode();
        }

        public String toString() {
            return "LicenseResult(valid=" + this.valid + ", tier=" + this.tier + ", message=" + this.message + ")";
        }

        public LicenseResult(boolean valid, String tier, String message) {
            Intrinsics.checkNotNullParameter(tier, "tier");
            Intrinsics.checkNotNullParameter(message, "message");
            this.valid = valid;
            this.tier = tier;
            this.message = message;
        }

        public final boolean getValid() {
            return this.valid;
        }

        public final String getTier() {
            return this.tier;
        }

        public final String getMessage() {
            return this.message;
        }
    }
}
