package com.termux;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: SetupWizardActivity.kt */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\r\u0018\u0000 )2\u00020\u0001:\u0001)B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0082@¢\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\u0012\u0010\u0016\u001a\u00020\u000b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J-\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000f2\u000e\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\r0\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016¢\u0006\u0002\u0010\u001eJ\b\u0010\u001f\u001a\u00020\u000bH\u0002J\b\u0010 \u001a\u00020\u000bH\u0002J\u000e\u0010!\u001a\u00020\u000bH\u0082@¢\u0006\u0002\u0010\"J\b\u0010#\u001a\u00020\u000bH\u0002J\b\u0010$\u001a\u00020\u000bH\u0002J \u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000f2\u0006\u0010'\u001a\u00020\r2\u0006\u0010(\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/termux/SetupWizardActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "bootstrapInstaller", "Lcom/termux/BootstrapInstaller;", "progressBar", "Landroid/widget/ProgressBar;", "progressText", "Landroid/widget/TextView;", "statusText", "installPackage", "", "command", "", "targetProgress", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "requestPermissions", "requestRegularPermissions", "setupAndroidSdk", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startInstallation", "startMainActivity", "updateUI", "progress", NotificationCompat.CATEGORY_STATUS, "detail", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class SetupWizardActivity extends AppCompatActivity {
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 1002;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private BootstrapInstaller bootstrapInstaller;
    private ProgressBar progressBar;
    private TextView progressText;
    private TextView statusText;

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String[] ALL_PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CAMERA", "android.permission.RECORD_AUDIO", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.BODY_SENSORS"};

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity", f = "SetupWizardActivity.kt", i = {0, 0}, l = {231, 247}, m = "installPackage", n = {"this", "targetProgress"}, s = {"L$0", "I$0"})
    /* renamed from: com.termux.SetupWizardActivity$installPackage$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int I$0;
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SetupWizardActivity.this.installPackage(null, 0, this);
        }
    }

    public static final /* synthetic */ void access$startInstallation(SetupWizardActivity $this) {
        $this.startInstallation();
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\rR\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/termux/SetupWizardActivity$Companion;", "", "()V", "ALL_PERMISSIONS", "", "", "[Ljava/lang/String;", "OVERLAY_PERMISSION_REQUEST_CODE", "", "PERMISSION_REQUEST_CODE", "isSetupComplete", "", "context", "Landroid/content/Context;", "markSetupComplete", "", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isSetupComplete(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            SharedPreferences prefs = context.getSharedPreferences("mobilecli", 0);
            return prefs.getBoolean("setup_complete", false);
        }

        public final void markSetupComplete(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            SharedPreferences prefs = context.getSharedPreferences("mobilecli", 0);
            prefs.edit().putBoolean("setup_complete", true).apply();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (INSTANCE.isSetupComplete(this)) {
            startMainActivity();
            return;
        }
        setContentView(R.layout.activity_setup_wizard);
        this.bootstrapInstaller = new BootstrapInstaller(this);
        View viewFindViewById = findViewById(R.id.setup_progress);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.progressBar = (ProgressBar) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.status_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.statusText = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.progress_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.progressText = (TextView) viewFindViewById3;
        requestPermissions();
    }

    private final void requestPermissions() {
        if (!Settings.canDrawOverlays(this)) {
            new AlertDialog.Builder(this).setTitle("Permission Required").setMessage("MobileCLI needs permission to open browser links.\n\nThis is required for AI authentication.").setPositiveButton("Grant", new DialogInterface.OnClickListener() { // from class: com.termux.SetupWizardActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SetupWizardActivity.requestPermissions$lambda$0(this.f$0, dialogInterface, i);
                }
            }).setNegativeButton("Skip", new DialogInterface.OnClickListener() { // from class: com.termux.SetupWizardActivity$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    SetupWizardActivity.requestPermissions$lambda$1(this.f$0, dialogInterface, i);
                }
            }).setCancelable(false).show();
        } else {
            requestRegularPermissions();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestPermissions$lambda$0(SetupWizardActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + this$0.getPackageName())), 1002);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void requestPermissions$lambda$1(SetupWizardActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.requestRegularPermissions();
    }

    private final void requestRegularPermissions() {
        String[] strArr = ALL_PERMISSIONS;
        Collection destination$iv$iv = new ArrayList();
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str = strArr[i];
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                destination$iv$iv.add(str);
            }
            i++;
        }
        List needed = CollectionsKt.toMutableList(destination$iv$iv);
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            needed.add("android.permission.POST_NOTIFICATIONS");
        }
        if (!needed.isEmpty()) {
            List $this$toTypedArray$iv = needed;
            ActivityCompat.requestPermissions(this, (String[]) $this$toTypedArray$iv.toArray(new String[0]), 1001);
        } else {
            startInstallation();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002) {
            requestRegularPermissions();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            startInstallation();
        }
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity$startInstallation$1", f = "SetupWizardActivity.kt", i = {}, l = {169, 175, 179, 182, 185, 188, 191, 193, 197, 201, 204, 208, 213, 218}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.SetupWizardActivity$startInstallation$1, reason: invalid class name and case insensitive filesystem */
    static final class C00411 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C00411(Continuation<? super C00411> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SetupWizardActivity.this.new C00411(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00411) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:40:0x00db A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00f7 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0113 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:49:0x012f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:52:0x014b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0163 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:58:0x017a A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0197 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:64:0x01b4 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:67:0x01d1 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:70:0x01ea A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0210 A[RETURN] */
        /* JADX WARN: Type inference failed for: r1v0, types: [com.termux.SetupWizardActivity$startInstallation$1, int] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 598
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.termux.SetupWizardActivity.C00411.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: SetupWizardActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.SetupWizardActivity$startInstallation$1$2", f = "SetupWizardActivity.kt", i = {}, l = {170}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.SetupWizardActivity$startInstallation$1$2, reason: invalid class name */
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
            int label;
            final /* synthetic */ SetupWizardActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(SetupWizardActivity setupWizardActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = setupWizardActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object $result) throws Throwable {
                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure($result);
                        BootstrapInstaller bootstrapInstaller = this.this$0.bootstrapInstaller;
                        if (bootstrapInstaller == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                            bootstrapInstaller = null;
                        }
                        this.label = 1;
                        Object objInstall = bootstrapInstaller.install(this);
                        return objInstall == coroutine_suspended ? coroutine_suspended : objInstall;
                    case 1:
                        ResultKt.throwOnFailure($result);
                        return $result;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: SetupWizardActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.SetupWizardActivity$startInstallation$1$3", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.SetupWizardActivity$startInstallation$1$3, reason: invalid class name */
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ SetupWizardActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(SetupWizardActivity setupWizardActivity, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = setupWizardActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        this.this$0.startMainActivity();
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: SetupWizardActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.SetupWizardActivity$startInstallation$1$4", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.SetupWizardActivity$startInstallation$1$4, reason: invalid class name */
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ SetupWizardActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(SetupWizardActivity setupWizardActivity, Exception exc, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.this$0 = setupWizardActivity;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass4) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        TextView textView = this.this$0.statusText;
                        TextView textView2 = null;
                        if (textView == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("statusText");
                            textView = null;
                        }
                        textView.setText("Setup error: " + this.$e.getMessage());
                        TextView textView3 = this.this$0.progressText;
                        if (textView3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("progressText");
                        } else {
                            textView2 = textView3;
                        }
                        textView2.setText("Tap to retry");
                        View viewFindViewById = this.this$0.findViewById(R.id.loading_section);
                        final SetupWizardActivity setupWizardActivity = this.this$0;
                        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.termux.SetupWizardActivity$startInstallation$1$4$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SetupWizardActivity.access$startInstallation(setupWizardActivity);
                            }
                        });
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startInstallation() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C00411(null), 3, null);
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity$installPackage$2", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.SetupWizardActivity$installPackage$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
        final /* synthetic */ String $command;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(String str, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.$command = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SetupWizardActivity.this.new AnonymousClass2(this.$command, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
            return invoke2(coroutineScope, (Continuation<Object>) continuation);
        }

        /* renamed from: invoke, reason: avoid collision after fix types in other method */
        public final Object invoke2(CoroutineScope coroutineScope, Continuation<Object> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    try {
                        Runtime runtime = Runtime.getRuntime();
                        String[] strArr = new String[3];
                        BootstrapInstaller bootstrapInstaller = SetupWizardActivity.this.bootstrapInstaller;
                        BootstrapInstaller bootstrapInstaller2 = null;
                        if (bootstrapInstaller == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                            bootstrapInstaller = null;
                        }
                        strArr[0] = new File(bootstrapInstaller.getBinDir(), "bash").getAbsolutePath();
                        strArr[1] = "-c";
                        strArr[2] = this.$command;
                        BootstrapInstaller bootstrapInstaller3 = SetupWizardActivity.this.bootstrapInstaller;
                        if (bootstrapInstaller3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                            bootstrapInstaller3 = null;
                        }
                        String[] environment = bootstrapInstaller3.getEnvironment();
                        BootstrapInstaller bootstrapInstaller4 = SetupWizardActivity.this.bootstrapInstaller;
                        if (bootstrapInstaller4 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                        } else {
                            bootstrapInstaller2 = bootstrapInstaller4;
                        }
                        Process process = runtime.exec(strArr, environment, bootstrapInstaller2.getHomeDir());
                        return Boxing.boxInt(process.waitFor());
                    } catch (Exception e) {
                        return Unit.INSTANCE;
                    }
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0075 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object installPackage(java.lang.String r8, int r9, kotlin.coroutines.Continuation<? super kotlin.Unit> r10) throws java.lang.Throwable {
        /*
            r7 = this;
            boolean r0 = r10 instanceof com.termux.SetupWizardActivity.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r10
            com.termux.SetupWizardActivity$installPackage$1 r0 = (com.termux.SetupWizardActivity.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L19
        L14:
            com.termux.SetupWizardActivity$installPackage$1 r0 = new com.termux.SetupWizardActivity$installPackage$1
            r0.<init>(r10)
        L19:
            r10 = r0
            java.lang.Object r0 = r10.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r10.label
            r3 = 0
            switch(r2) {
                case 0: goto L3c;
                case 1: goto L32;
                case 2: goto L2e;
                default: goto L26;
            }
        L26:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L2e:
            kotlin.ResultKt.throwOnFailure(r0)
            goto L76
        L32:
            int r8 = r10.I$0
            java.lang.Object r9 = r10.L$0
            com.termux.SetupWizardActivity r9 = (com.termux.SetupWizardActivity) r9
            kotlin.ResultKt.throwOnFailure(r0)
            goto L5d
        L3c:
            kotlin.ResultKt.throwOnFailure(r0)
            r2 = r7
            kotlinx.coroutines.CoroutineDispatcher r4 = kotlinx.coroutines.Dispatchers.getIO()
            kotlin.coroutines.CoroutineContext r4 = (kotlin.coroutines.CoroutineContext) r4
            com.termux.SetupWizardActivity$installPackage$2 r5 = new com.termux.SetupWizardActivity$installPackage$2
            r5.<init>(r8, r3)
            kotlin.jvm.functions.Function2 r5 = (kotlin.jvm.functions.Function2) r5
            r10.L$0 = r2
            r10.I$0 = r9
            r6 = 1
            r10.label = r6
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r4, r5, r10)
            if (r8 != r1) goto L5b
            return r1
        L5b:
            r8 = r9
            r9 = r2
        L5d:
            kotlinx.coroutines.MainCoroutineDispatcher r2 = kotlinx.coroutines.Dispatchers.getMain()
            kotlin.coroutines.CoroutineContext r2 = (kotlin.coroutines.CoroutineContext) r2
            com.termux.SetupWizardActivity$installPackage$3 r4 = new com.termux.SetupWizardActivity$installPackage$3
            r4.<init>(r8, r3)
            kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
            r10.L$0 = r3
            r3 = 2
            r10.label = r3
            java.lang.Object r8 = kotlinx.coroutines.BuildersKt.withContext(r2, r4, r10)
            if (r8 != r1) goto L76
            return r1
        L76:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.SetupWizardActivity.installPackage(java.lang.String, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity$installPackage$3", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.SetupWizardActivity$installPackage$3, reason: invalid class name */
    static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $targetProgress;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass3(int i, Continuation<? super AnonymousClass3> continuation) {
            super(2, continuation);
            this.$targetProgress = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SetupWizardActivity.this.new AnonymousClass3(this.$targetProgress, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ProgressBar progressBar = SetupWizardActivity.this.progressBar;
                    if (progressBar == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("progressBar");
                        progressBar = null;
                    }
                    progressBar.setProgress(this.$targetProgress);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity$setupAndroidSdk$2", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.SetupWizardActivity$setupAndroidSdk$2, reason: invalid class name and case insensitive filesystem */
    static final class C00402 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C00402(Continuation<? super C00402> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SetupWizardActivity.this.new C00402(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00402) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            char c;
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    try {
                        BootstrapInstaller bootstrapInstaller = SetupWizardActivity.this.bootstrapInstaller;
                        if (bootstrapInstaller == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                            bootstrapInstaller = null;
                        }
                        File sdkDir = new File(bootstrapInstaller.getHomeDir(), "android-sdk");
                        File platformsDir = new File(sdkDir, "platforms/android-34");
                        File buildToolsDir = new File(sdkDir, "build-tools/34.0.0");
                        platformsDir.mkdirs();
                        buildToolsDir.mkdirs();
                        File androidJar = new File("/data/data/com.termux/files/usr/share/aapt/android.jar");
                        if (androidJar.exists()) {
                            FilesKt.copyTo$default(androidJar, new File(platformsDir, "android.jar"), true, 0, 4, null);
                        }
                        File binDir = new File("/data/data/com.termux/files/usr/bin");
                        char c2 = 3;
                        Iterable $this$forEach$iv = CollectionsKt.listOf((Object[]) new String[]{"aapt", "aapt2", "d8", "apksigner", "zipalign"});
                        for (Object element$iv : $this$forEach$iv) {
                            String tool = (String) element$iv;
                            File source = new File(binDir, tool);
                            File dest = new File(buildToolsDir, tool);
                            if (!source.exists() || dest.exists()) {
                                c = c2;
                            } else {
                                c = 3;
                                Runtime.getRuntime().exec(new String[]{"ln", "-sf", source.getAbsolutePath(), dest.getAbsolutePath()}).waitFor();
                            }
                            c2 = c;
                        }
                    } catch (Exception e) {
                    }
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Object setupAndroidSdk(Continuation<? super Unit> continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new C00402(null), continuation);
        return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
    }

    /* compiled from: SetupWizardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.SetupWizardActivity$updateUI$1", f = "SetupWizardActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.SetupWizardActivity$updateUI$1, reason: invalid class name and case insensitive filesystem */
    static final class C00421 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $detail;
        final /* synthetic */ int $progress;
        final /* synthetic */ String $status;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00421(int i, String str, String str2, Continuation<? super C00421> continuation) {
            super(2, continuation);
            this.$progress = i;
            this.$status = str;
            this.$detail = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return SetupWizardActivity.this.new C00421(this.$progress, this.$status, this.$detail, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00421) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    ProgressBar progressBar = SetupWizardActivity.this.progressBar;
                    TextView textView = null;
                    if (progressBar == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("progressBar");
                        progressBar = null;
                    }
                    progressBar.setProgress(this.$progress);
                    TextView textView2 = SetupWizardActivity.this.statusText;
                    if (textView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("statusText");
                        textView2 = null;
                    }
                    textView2.setText(this.$status);
                    TextView textView3 = SetupWizardActivity.this.progressText;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("progressText");
                    } else {
                        textView = textView3;
                    }
                    textView.setText(this.$detail);
                    return Unit.INSTANCE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUI(int progress, String status, String detail) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getMain(), null, new C00421(progress, status, detail, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startMainActivity() {
        Intent $this$startMainActivity_u24lambda_u243 = new Intent(this, (Class<?>) MainActivity.class);
        $this$startMainActivity_u24lambda_u243.setFlags(268468224);
        startActivity($this$startMainActivity_u24lambda_u243);
        finish();
    }
}
