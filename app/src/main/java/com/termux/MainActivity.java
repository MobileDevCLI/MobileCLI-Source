package com.termux;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.system.ErrnoException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.PointerIconCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.termux.MainActivity;
import com.termux.app.TermuxService;
import com.termux.terminal.KeyHandler;
import com.termux.terminal.TerminalBuffer;
import com.termux.terminal.TerminalEmulator;
import com.termux.terminal.TerminalSession;
import com.termux.terminal.TerminalSessionClient;
import com.termux.view.TerminalView;
import com.termux.view.TerminalViewClient;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

/* compiled from: MainActivity.kt */
@Metadata(d1 = {"\u0000ñ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0019\n\u0002\u0010\f\n\u0002\b2*\u0001<\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010Z\u001a\u00020[H\u0002J\u0010\u0010\\\u001a\u00020[2\u0006\u0010]\u001a\u00020\u0017H\u0002J\b\u0010^\u001a\u00020[H\u0002J\b\u0010_\u001a\u00020[H\u0002J\b\u0010`\u001a\u00020[H\u0002J\u0010\u0010a\u001a\u00020[2\u0006\u0010b\u001a\u00020\u0017H\u0016J\u0010\u0010c\u001a\u00020[2\u0006\u0010d\u001a\u00020\u0006H\u0002J\b\u0010e\u001a\u00020?H\u0002J\b\u0010f\u001a\u00020[H\u0002J\b\u0010g\u001a\u00020[H\u0002J\b\u0010h\u001a\u00020[H\u0002J\b\u0010i\u001a\u00020[H\u0002J\r\u0010j\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010kJ\b\u0010l\u001a\u00020[H\u0002J\u0010\u0010m\u001a\u00020[2\u0006\u0010n\u001a\u00020\u0006H\u0002J\b\u0010o\u001a\u00020[H\u0002J\u0010\u0010p\u001a\u00020[2\u0006\u0010q\u001a\u00020\u0006H\u0002J\u0010\u0010r\u001a\u00020\u00172\u0006\u0010q\u001a\u00020\u0006H\u0002J\b\u0010s\u001a\u00020\u0017H\u0002J\b\u0010t\u001a\u00020\u0017H\u0002J\b\u0010u\u001a\u00020\u0017H\u0016J\b\u0010v\u001a\u00020[H\u0002J\u0010\u0010w\u001a\u00020[2\u0006\u0010x\u001a\u00020\u0006H\u0002J\b\u0010y\u001a\u00020[H\u0002J\u001c\u0010z\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010}\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u0006H\u0016J\u001c\u0010~\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u0006H\u0016J%\u0010\u007f\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\u0011\u0010\u0080\u0001\u001a\f\u0018\u00010\u0081\u0001j\u0005\u0018\u0001`\u0082\u0001H\u0016J0\u0010\u0083\u0001\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u00062\u0011\u0010\u0080\u0001\u001a\f\u0018\u00010\u0081\u0001j\u0005\u0018\u0001`\u0082\u0001H\u0016J\u001d\u0010\u0084\u0001\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u0006H\u0016J\u001d\u0010\u0085\u0001\u001a\u00020[2\b\u0010{\u001a\u0004\u0018\u00010\u00062\b\u0010|\u001a\u0004\u0018\u00010\u0006H\u0016J\u0011\u0010\u0086\u0001\u001a\u00020[2\u0006\u0010q\u001a\u00020\u0006H\u0002J\u0013\u0010\u0087\u0001\u001a\u00020[2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J%\u0010\u0088\u0001\u001a\u00020\u00172\u0007\u0010\u0089\u0001\u001a\u00020\u000f2\u0007\u0010\u008a\u0001\u001a\u00020\u00172\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0013\u0010\u008b\u0001\u001a\u00020[2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0013\u0010\u008c\u0001\u001a\u00020[2\b\u0010\u008d\u0001\u001a\u00030\u008e\u0001H\u0016J\u001d\u0010\u008f\u0001\u001a\u00020[2\b\u0010>\u001a\u0004\u0018\u00010?2\b\u0010d\u001a\u0004\u0018\u00010\u0006H\u0016J\u0015\u0010\u0090\u0001\u001a\u00020[2\n\u0010\u0091\u0001\u001a\u0005\u0018\u00010\u0092\u0001H\u0014J\t\u0010\u0093\u0001\u001a\u00020[H\u0014J\t\u0010\u0094\u0001\u001a\u00020[H\u0016J(\u0010\u0095\u0001\u001a\u00020\u00172\u0007\u0010\u0096\u0001\u001a\u00020\u000f2\n\u0010\u0080\u0001\u001a\u0005\u0018\u00010\u0097\u00012\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u001e\u0010\u0098\u0001\u001a\u00020\u00172\u0007\u0010\u0096\u0001\u001a\u00020\u000f2\n\u0010\u0080\u0001\u001a\u0005\u0018\u00010\u0097\u0001H\u0016J\u0015\u0010\u0099\u0001\u001a\u00020\u00172\n\u0010\u009a\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0016J\u0013\u0010\u009c\u0001\u001a\u00020[2\b\u0010>\u001a\u0004\u0018\u00010?H\u0016J\u0014\u0010\u009d\u0001\u001a\u00030\u009e\u00012\b\u0010\u009f\u0001\u001a\u00030\u009e\u0001H\u0016J\u0014\u0010 \u0001\u001a\u00020[2\t\u0010¡\u0001\u001a\u0004\u0018\u00010?H\u0016J\u0015\u0010¢\u0001\u001a\u00020[2\n\u0010\u0080\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0016J\t\u0010£\u0001\u001a\u00020[H\u0014J\u0012\u0010¤\u0001\u001a\u00020[2\u0007\u0010¥\u0001\u001a\u00020\u0017H\u0016J\u0014\u0010¦\u0001\u001a\u00020[2\t\u0010§\u0001\u001a\u0004\u0018\u00010?H\u0016J\u0014\u0010¨\u0001\u001a\u00020[2\t\u0010§\u0001\u001a\u0004\u0018\u00010?H\u0016J\t\u0010©\u0001\u001a\u00020[H\u0002J\t\u0010ª\u0001\u001a\u00020\u0017H\u0016J\t\u0010«\u0001\u001a\u00020\u0017H\u0016J\t\u0010¬\u0001\u001a\u00020\u0017H\u0016J\t\u0010\u00ad\u0001\u001a\u00020\u0017H\u0016J\u0012\u0010®\u0001\u001a\u00020[2\u0007\u0010¯\u0001\u001a\u00020\u000fH\u0002J\t\u0010°\u0001\u001a\u00020[H\u0002J\t\u0010±\u0001\u001a\u00020[H\u0002J\t\u0010²\u0001\u001a\u00020[H\u0002J\t\u0010³\u0001\u001a\u00020[H\u0002J\t\u0010´\u0001\u001a\u00020[H\u0002J\t\u0010µ\u0001\u001a\u00020[H\u0002J\u0013\u0010¶\u0001\u001a\u00020[2\b\u0010·\u0001\u001a\u00030¸\u0001H\u0002J\u0012\u0010¹\u0001\u001a\u00020[2\u0007\u0010\u0096\u0001\u001a\u00020\u000fH\u0002J\u0012\u0010º\u0001\u001a\u00020[2\u0007\u0010»\u0001\u001a\u00020\u0006H\u0002J\t\u0010¼\u0001\u001a\u00020[H\u0002J\t\u0010½\u0001\u001a\u00020[H\u0002J\t\u0010¾\u0001\u001a\u00020[H\u0002J\t\u0010¿\u0001\u001a\u00020[H\u0002J\t\u0010À\u0001\u001a\u00020[H\u0002J\t\u0010Á\u0001\u001a\u00020[H\u0002J\t\u0010Â\u0001\u001a\u00020[H\u0002J\t\u0010Ã\u0001\u001a\u00020[H\u0002J\t\u0010Ä\u0001\u001a\u00020[H\u0002J\t\u0010Å\u0001\u001a\u00020\u0017H\u0016J\t\u0010Æ\u0001\u001a\u00020\u0017H\u0016J\t\u0010Ç\u0001\u001a\u00020\u0017H\u0016J\t\u0010È\u0001\u001a\u00020[H\u0002J\t\u0010É\u0001\u001a\u00020[H\u0002J\t\u0010Ê\u0001\u001a\u00020[H\u0002J\u0015\u0010Ë\u0001\u001a\u00020[2\n\u0010\u009a\u0001\u001a\u0005\u0018\u00010\u009b\u0001H\u0002J\u0011\u0010Ì\u0001\u001a\u00020[2\u0006\u0010|\u001a\u00020\u0006H\u0002J\u001b\u0010Í\u0001\u001a\u00020[2\u0007\u0010Î\u0001\u001a\u00020\u00062\u0007\u0010Ï\u0001\u001a\u00020\u0006H\u0002J\t\u0010Ð\u0001\u001a\u00020[H\u0002J\t\u0010Ñ\u0001\u001a\u00020[H\u0002J\t\u0010Ò\u0001\u001a\u00020[H\u0002J\u0011\u0010Ó\u0001\u001a\u00020[2\u0006\u0010q\u001a\u00020\u0006H\u0002J\u0012\u0010Ô\u0001\u001a\u00020[2\u0007\u0010¯\u0001\u001a\u00020\u000fH\u0002J\u0012\u0010Õ\u0001\u001a\u00020[2\u0007\u0010¯\u0001\u001a\u00020\u000fH\u0002J\t\u0010Ö\u0001\u001a\u00020[H\u0002J\t\u0010×\u0001\u001a\u00020[H\u0002J\u0011\u0010Ø\u0001\u001a\u00020[2\u0006\u0010|\u001a\u00020\u0006H\u0002J\t\u0010Ù\u0001\u001a\u00020[H\u0002J\t\u0010Ú\u0001\u001a\u00020[H\u0002J\u0011\u0010Û\u0001\u001a\u00020[2\u0006\u0010x\u001a\u00020\u0006H\u0002J\t\u0010Ü\u0001\u001a\u00020[H\u0002J\u0012\u0010Ý\u0001\u001a\u00020[2\u0007\u0010¯\u0001\u001a\u00020\u000fH\u0002J\t\u0010Þ\u0001\u001a\u00020[H\u0002J\t\u0010ß\u0001\u001a\u00020[H\u0002J\t\u0010à\u0001\u001a\u00020[H\u0002J\t\u0010á\u0001\u001a\u00020[H\u0002J\t\u0010â\u0001\u001a\u00020[H\u0002J\t\u0010ã\u0001\u001a\u00020[H\u0002J\t\u0010ä\u0001\u001a\u00020[H\u0002J\t\u0010å\u0001\u001a\u00020[H\u0002J\u001b\u0010æ\u0001\u001a\u00020[2\u0007\u0010ç\u0001\u001a\u00020\u000f2\u0007\u0010è\u0001\u001a\u00020\u0006H\u0002J\t\u0010é\u0001\u001a\u00020[H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010#\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u000fX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00100\u001a\u0004\u0018\u000101X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00102\u001a\u0004\u0018\u000103X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00104\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u00105\u001a\u0002068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b7\u00108R\u0010\u00109\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010;\u001a\u00020<X\u0082\u0004¢\u0006\u0004\n\u0002\u0010=R\u0016\u0010>\u001a\u0004\u0018\u00010?8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020?0CX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010EX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010F\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010G\u001a\u0004\u0018\u000101X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010H\u001a\u0004\u0018\u00010IX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010J\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010K\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020MX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010N\u001a\u0004\u0018\u00010OX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010P\u001a\u0004\u0018\u00010QX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020SX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0011X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0011X\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010V\u001a\u0004\u0018\u00010/X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010X\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010Y\u001a\u0004\u0018\u00010\u0019X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006ê\u0001"}, d2 = {"Lcom/termux/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/termux/view/TerminalViewClient;", "Lcom/termux/terminal/TerminalSessionClient;", "()V", "AI_CLAUDE", "", "AI_CODEX", "AI_GEMINI", "AI_NONE", "KEY_CLAUDE_POWER", "KEY_DEV_MODE", "KEY_WAKE_LOCK", "PREFS_NAME", "VERSION_TAP_THRESHOLD", "", "VERSION_TAP_TIMEOUT", "", "aiChoiceScreen", "Landroid/widget/ScrollView;", "bootstrapInstaller", "Lcom/termux/BootstrapInstaller;", "bootstrapReadyPendingSession", "", "claudePowerToggle", "Landroid/widget/TextView;", "currentSessionIndex", "currentTextSize", "devModeToggle", "devOptionsDivider", "Landroid/view/View;", "devOptionsHeader", "developerModeEnabled", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "installDevToolsItem", "isAltPressed", "isClaudePowerEnabled", "isCtrlPressed", "isKeyboardVisible", "isWakeLockEnabled", "keyboardHeightThreshold", "lastVersionTapTime", "maxSessions", "maxTextSize", "minTextSize", "pendingTerminalUpdate", "Ljava/lang/Runnable;", "progressBar", "Landroid/widget/ProgressBar;", "progressDialog", "Landroid/app/AlertDialog;", "progressText", "propertiesFile", "Ljava/io/File;", "getPropertiesFile", "()Ljava/io/File;", "selectedAI", "serviceBound", "serviceConnection", "com/termux/MainActivity$serviceConnection$1", "Lcom/termux/MainActivity$serviceConnection$1;", "session", "Lcom/termux/terminal/TerminalSession;", "getSession", "()Lcom/termux/terminal/TerminalSession;", "sessions", "", "setupOverlay", "Landroid/widget/FrameLayout;", "setupPercentage", "setupProgress", "setupProgressScreen", "Landroid/widget/LinearLayout;", "setupStatus", "setupStep", "terminalView", "Lcom/termux/view/TerminalView;", "termuxProperties", "Ljava/util/Properties;", "termuxService", "Lcom/termux/app/TermuxService;", "uiHandler", "Landroid/os/Handler;", "updateDebounceMs", "urlWatchInterval", "urlWatcherRunnable", "versionTapCount", "versionText", "wakeLockToggle", "addNewSession", "", "applyClaudePowerMode", "enabled", "applyTermuxProperties", "checkAndOfferAISetup", "checkBootstrap", "copyModeChanged", "copyMode", "copyToClipboard", "text", "createNewTerminalSession", "createSession", "createSessionOrDefer", "createSystemPrompt", "enableDeveloperMode", "getTerminalCursorStyle", "()Ljava/lang/Integer;", "hideSetupOverlay", "installAI", "tool", "installBootstrap", "installSelectedAI", "ai", "isAIInstalled", "isAISetupNeeded", "isClaudeInstalled", "isTerminalViewSelected", "killCurrentSession", "launchAIAfterAnimation", "launchCmd", "loadTermuxProperties", "logDebug", "tag", "message", "logError", "logInfo", "logStackTrace", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "logStackTraceWithMessage", "logVerbose", "logWarn", "onAISelected", "onBell", "onCodePoint", "codePoint", "ctrlDown", "onColorsChanged", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCopyTextToClipboard", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onEmulatorSet", "onKeyDown", "keyCode", "Landroid/view/KeyEvent;", "onKeyUp", "onLongPress", NotificationCompat.CATEGORY_EVENT, "Landroid/view/MotionEvent;", "onPasteTextFromClipboard", "onScale", "", "scale", "onSessionFinished", "finishedSession", "onSingleTapUp", "onStop", "onTerminalCursorStateChange", "state", "onTextChanged", "changedSession", "onTitleChanged", "pasteFromClipboard", "readAltKey", "readControlKey", "readFnKey", "readShiftKey", "removeSession", "index", "restoreTranscript", "runAISetup", "runInstallDevTools", "saveDeveloperModeState", "saveTranscript", "scheduleTerminalUpdate", "sendChar", "c", "", "sendKey", "sendSpecialKey", "sequence", "setupAICardClickHandlers", "setupBackButtonHandler", "setupDeveloperMode", "setupExtraKeys", "setupKeyboardListener", "setupNavDrawer", "setupPowerModeToggles", "setupSetupOverlay", "setupUrlWatcher", "shouldBackButtonBeMappedToEscape", "shouldEnforceCharBasedInput", "shouldUseCtrlSpaceWorkaround", "showAIChoiceScreen", "showAIChooserDialog", "showAboutDialog", "showContextMenu", "showError", "showErrorSmart", "technicalError", "friendlyMessage", "showHelpDialog", "showMoreOptions", "showProgressDialog", "showProgressScreenForAI", "showSessionOptions", "showSessionOptionsInDrawer", "showSessionsDialog", "showSettingsDialog", "showSetupDialog", "showSetupOverlay", "showTextSizeDialog", "showWelcomeAnimation", "startTermuxService", "switchToSession", "toggleClaudePowerMode", "toggleDeveloperMode", "toggleWakeLock", "updateDeveloperModeUI", "updateDrawerSessionsList", "updateModifierButtons", "updatePowerModeUI", "updateSessionTabs", "updateSetupProgress", "progress", NotificationCompat.CATEGORY_STATUS, "updateTerminalSize", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class MainActivity extends AppCompatActivity implements TerminalViewClient, TerminalSessionClient {
    private ScrollView aiChoiceScreen;
    private BootstrapInstaller bootstrapInstaller;
    private boolean bootstrapReadyPendingSession;
    private TextView claudePowerToggle;
    private int currentSessionIndex;
    private TextView devModeToggle;
    private View devOptionsDivider;
    private TextView devOptionsHeader;
    private boolean developerModeEnabled;
    private DrawerLayout drawerLayout;
    private TextView installDevToolsItem;
    private boolean isAltPressed;
    private boolean isClaudePowerEnabled;
    private boolean isCtrlPressed;
    private boolean isKeyboardVisible;
    private boolean isWakeLockEnabled;
    private int keyboardHeightThreshold;
    private long lastVersionTapTime;
    private Runnable pendingTerminalUpdate;
    private ProgressBar progressBar;
    private AlertDialog progressDialog;
    private TextView progressText;
    private String selectedAI;
    private boolean serviceBound;
    private FrameLayout setupOverlay;
    private TextView setupPercentage;
    private ProgressBar setupProgress;
    private LinearLayout setupProgressScreen;
    private TextView setupStatus;
    private TextView setupStep;
    private TerminalView terminalView;
    private Properties termuxProperties;
    private TermuxService termuxService;
    private Runnable urlWatcherRunnable;
    private int versionTapCount;
    private TextView versionText;
    private TextView wakeLockToggle;
    private final List<TerminalSession> sessions = new ArrayList();
    private final int maxSessions = 10;
    private final MainActivity$serviceConnection$1 serviceConnection = new MainActivity$serviceConnection$1(this);
    private int currentTextSize = 28;
    private final int minTextSize = 14;
    private final int maxTextSize = 56;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private final long updateDebounceMs = 16;
    private final long urlWatchInterval = 500;
    private final int VERSION_TAP_THRESHOLD = 7;
    private final long VERSION_TAP_TIMEOUT = 2000;
    private final String PREFS_NAME = "MobileCLIPrefs";
    private final String KEY_DEV_MODE = "developer_mode_enabled";
    private final String KEY_WAKE_LOCK = "wake_lock_enabled";
    private final String KEY_CLAUDE_POWER = "claude_power_mode";
    private final String AI_CLAUDE = "claude";
    private final String AI_GEMINI = "gemini";
    private final String AI_CODEX = "codex";
    private final String AI_NONE = "none";

    public static final /* synthetic */ void access$hideSetupOverlay(MainActivity $this) {
        $this.hideSetupOverlay();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TerminalSession getSession() {
        return (TerminalSession) CollectionsKt.getOrNull(this.sessions, this.currentSessionIndex);
    }

    private final File getPropertiesFile() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        return new File(new File(bootstrapInstaller.getHomeDir(), ".termux"), "termux.properties");
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws InterruptedException, ErrnoException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTermuxService();
        View viewFindViewById = findViewById(R.id.terminal_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        TerminalView terminalView = (TerminalView) viewFindViewById;
        this.terminalView = terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.setTerminalViewClient(this);
        TerminalView terminalView3 = this.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView3 = null;
        }
        terminalView3.setTextSize(this.currentTextSize);
        TerminalView terminalView4 = this.terminalView;
        if (terminalView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView4;
        }
        terminalView2.setAlpha(0.0f);
        View viewFindViewById2 = findViewById(R.id.drawer_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.drawerLayout = (DrawerLayout) viewFindViewById2;
        this.bootstrapInstaller = new BootstrapInstaller(this);
        setupExtraKeys();
        updateModifierButtons();
        setupNavDrawer();
        setupKeyboardListener();
        setupBackButtonHandler();
        setupUrlWatcher();
        loadTermuxProperties();
        setupDeveloperMode();
        setupSetupOverlay();
        checkBootstrap();
    }

    private final void setupKeyboardListener() {
        final View rootView = getWindow().getDecorView().getRootView();
        this.keyboardHeightThreshold = getResources().getDisplayMetrics().heightPixels / 4;
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda54
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                MainActivity.setupKeyboardListener$lambda$0(rootView, this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupKeyboardListener$lambda$0(View $rootView, MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Rect rect = new Rect();
        $rootView.getWindowVisibleDisplayFrame(rect);
        int screenHeight = $rootView.getHeight();
        int keyboardHeight = screenHeight - rect.bottom;
        boolean wasVisible = this$0.isKeyboardVisible;
        boolean z = keyboardHeight > this$0.keyboardHeightThreshold;
        this$0.isKeyboardVisible = z;
        if (wasVisible != z) {
            this$0.scheduleTerminalUpdate();
        }
    }

    private final void setupBackButtonHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback() { // from class: com.termux.MainActivity.setupBackButtonHandler.1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                DrawerLayout drawerLayout = null;
                TerminalView terminalView = null;
                if (!MainActivity.this.isKeyboardVisible) {
                    DrawerLayout drawerLayout2 = MainActivity.this.drawerLayout;
                    if (drawerLayout2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
                        drawerLayout2 = null;
                    }
                    if (drawerLayout2.isDrawerOpen(GravityCompat.START)) {
                        DrawerLayout drawerLayout3 = MainActivity.this.drawerLayout;
                        if (drawerLayout3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
                        } else {
                            drawerLayout = drawerLayout3;
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return;
                    }
                    MainActivity.this.moveTaskToBack(true);
                    return;
                }
                Object systemService = MainActivity.this.getSystemService("input_method");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
                InputMethodManager inputMethodManager = (InputMethodManager) systemService;
                TerminalView terminalView2 = MainActivity.this.terminalView;
                if (terminalView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                } else {
                    terminalView = terminalView2;
                }
                inputMethodManager.hideSoftInputFromWindow(terminalView.getWindowToken(), 0);
            }
        });
    }

    private final void setupDeveloperMode() {
        SharedPreferences prefs = getSharedPreferences(this.PREFS_NAME, 0);
        this.developerModeEnabled = prefs.getBoolean(this.KEY_DEV_MODE, false);
        this.devOptionsDivider = findViewById(R.id.dev_options_divider);
        this.devOptionsHeader = (TextView) findViewById(R.id.nav_dev_options_header);
        this.devModeToggle = (TextView) findViewById(R.id.nav_dev_mode);
        this.installDevToolsItem = (TextView) findViewById(R.id.nav_install_dev_tools);
        TextView textView = (TextView) findViewById(R.id.nav_version);
        this.versionText = textView;
        if (textView != null) {
            textView.setText("Version 1.4.0");
        }
        TextView textView2 = this.versionText;
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupDeveloperMode$lambda$1(this.f$0, view);
                }
            });
        }
        TextView textView3 = this.devModeToggle;
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupDeveloperMode$lambda$2(this.f$0, view);
                }
            });
        }
        TextView textView4 = this.installDevToolsItem;
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupDeveloperMode$lambda$3(this.f$0, view);
                }
            });
        }
        updateDeveloperModeUI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDeveloperMode$lambda$1(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        long currentTime = System.currentTimeMillis();
        if (currentTime - this$0.lastVersionTapTime > this$0.VERSION_TAP_TIMEOUT) {
            this$0.versionTapCount = 0;
        }
        int i = this$0.versionTapCount + 1;
        this$0.versionTapCount = i;
        this$0.lastVersionTapTime = currentTime;
        if (this$0.developerModeEnabled) {
            Toast.makeText(this$0, "Developer mode is already enabled", 0).show();
            return;
        }
        int i2 = this$0.VERSION_TAP_THRESHOLD;
        if (i >= i2) {
            this$0.enableDeveloperMode();
            this$0.versionTapCount = 0;
        } else if (i >= 4) {
            int remaining = i2 - i;
            Toast.makeText(this$0, "You are " + remaining + " steps away from developer mode", 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDeveloperMode$lambda$2(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.toggleDeveloperMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupDeveloperMode$lambda$3(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.runInstallDevTools();
    }

    private final void enableDeveloperMode() {
        this.developerModeEnabled = true;
        saveDeveloperModeState();
        updateDeveloperModeUI();
        Toast.makeText(this, "Developer mode enabled!", 1).show();
    }

    private final void toggleDeveloperMode() {
        this.developerModeEnabled = !this.developerModeEnabled;
        saveDeveloperModeState();
        updateDeveloperModeUI();
        String status = this.developerModeEnabled ? "enabled" : "disabled";
        Toast.makeText(this, "Developer mode " + status, 0).show();
    }

    private final void saveDeveloperModeState() {
        SharedPreferences prefs = getSharedPreferences(this.PREFS_NAME, 0);
        prefs.edit().putBoolean(this.KEY_DEV_MODE, this.developerModeEnabled).apply();
    }

    private final void updateDeveloperModeUI() {
        int visibility = this.developerModeEnabled ? 0 : 8;
        View view = this.devOptionsDivider;
        if (view != null) {
            view.setVisibility(visibility);
        }
        TextView textView = this.devOptionsHeader;
        if (textView != null) {
            textView.setVisibility(visibility);
        }
        TextView textView2 = this.devModeToggle;
        if (textView2 != null) {
            textView2.setVisibility(visibility);
        }
        TextView textView3 = this.installDevToolsItem;
        if (textView3 != null) {
            textView3.setVisibility(visibility);
        }
        TextView textView4 = this.devModeToggle;
        if (textView4 == null) {
            return;
        }
        textView4.setText(this.developerModeEnabled ? "Developer Mode: ON" : "Developer Mode: OFF");
    }

    private final void runInstallDevTools() {
        TerminalSession session = getSession();
        if (session != null) {
            byte[] bytes = "install-dev-tools\n".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            session.write(bytes, 0, "install-dev-tools\n".length());
        }
        Toast.makeText(this, "Running install-dev-tools...", 0).show();
    }

    private final void setupSetupOverlay() {
        this.setupOverlay = (FrameLayout) findViewById(R.id.setup_overlay);
        this.setupProgressScreen = (LinearLayout) findViewById(R.id.setup_progress_screen);
        this.aiChoiceScreen = (ScrollView) findViewById(R.id.ai_choice_screen);
        this.setupStatus = (TextView) findViewById(R.id.setup_status);
        this.setupProgress = (ProgressBar) findViewById(R.id.setup_progress);
        this.setupPercentage = (TextView) findViewById(R.id.setup_percentage);
        this.setupStep = (TextView) findViewById(R.id.setup_step);
        setupAICardClickHandlers();
    }

    private final void setupAICardClickHandlers() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ai_card_claude);
        if (linearLayout != null) {
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda37
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupAICardClickHandlers$lambda$4(this.f$0, view);
                }
            });
        }
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.ai_card_gemini);
        if (linearLayout2 != null) {
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda38
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupAICardClickHandlers$lambda$5(this.f$0, view);
                }
            });
        }
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.ai_card_codex);
        if (linearLayout3 != null) {
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda39
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupAICardClickHandlers$lambda$6(this.f$0, view);
                }
            });
        }
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.ai_card_none);
        if (linearLayout4 != null) {
            linearLayout4.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda40
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupAICardClickHandlers$lambda$7(this.f$0, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAICardClickHandlers$lambda$4(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onAISelected(this$0.AI_CLAUDE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAICardClickHandlers$lambda$5(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onAISelected(this$0.AI_GEMINI);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAICardClickHandlers$lambda$6(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onAISelected(this$0.AI_CODEX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAICardClickHandlers$lambda$7(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onAISelected(this$0.AI_NONE);
    }

    private final void onAISelected(String ai) {
        this.selectedAI = ai;
        SharedPreferences prefs = getSharedPreferences("mobilecli", 0);
        prefs.edit().putString("selected_ai", ai).putBoolean("ai_setup_complete", true).apply();
        if (Intrinsics.areEqual(ai, this.AI_NONE)) {
            hideSetupOverlay();
            createSessionOrDefer();
            Toast.makeText(this, "Terminal ready! Install AI tools anytime from settings.", 1).show();
        } else {
            showProgressScreenForAI(ai);
            installSelectedAI(ai);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showAIChoiceScreen() {
        runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.showAIChoiceScreen$lambda$8(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAIChoiceScreen$lambda$8(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.setupProgressScreen;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        ScrollView scrollView = this$0.aiChoiceScreen;
        if (scrollView == null) {
            return;
        }
        scrollView.setVisibility(0);
    }

    private final void showProgressScreenForAI(final String ai) {
        runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.showProgressScreenForAI$lambda$9(this.f$0, ai);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showProgressScreenForAI$lambda$9(MainActivity this$0, String ai) {
        String aiName;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ai, "$ai");
        ScrollView scrollView = this$0.aiChoiceScreen;
        if (scrollView != null) {
            scrollView.setVisibility(8);
        }
        LinearLayout linearLayout = this$0.setupProgressScreen;
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        if (Intrinsics.areEqual(ai, this$0.AI_CLAUDE)) {
            aiName = "Claude Code";
        } else if (Intrinsics.areEqual(ai, this$0.AI_GEMINI)) {
            aiName = "Gemini CLI";
        } else {
            aiName = Intrinsics.areEqual(ai, this$0.AI_CODEX) ? "Codex CLI" : "AI Tools";
        }
        this$0.updateSetupProgress(0, "Installing " + aiName + "...");
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.MainActivity$installSelectedAI$1", f = "MainActivity.kt", i = {}, l = {493, 500, TypedValues.PositionType.TYPE_POSITION_TYPE, 517, 523}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.MainActivity$installSelectedAI$1, reason: invalid class name and case insensitive filesystem */
    static final class C00351 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $ai;
        int label;
        final /* synthetic */ MainActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00351(String str, MainActivity mainActivity, Continuation<? super C00351> continuation) {
            super(2, continuation);
            this.$ai = str;
            this.this$0 = mainActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C00351(this.$ai, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00351) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x0060 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x006f  */
        /* JADX WARN: Removed duplicated region for block: B:25:0x0072 A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:8:0x001a, B:9:0x001f, B:40:0x00bc, B:10:0x0025, B:22:0x0061, B:32:0x0092, B:37:0x00a0, B:42:0x00bf, B:25:0x0072, B:28:0x0081, B:11:0x002a, B:19:0x0052, B:16:0x0037), top: B:53:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x009d  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00a0 A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:8:0x001a, B:9:0x001f, B:40:0x00bc, B:10:0x0025, B:22:0x0061, B:32:0x0092, B:37:0x00a0, B:42:0x00bf, B:25:0x0072, B:28:0x0081, B:11:0x002a, B:19:0x0052, B:16:0x0037), top: B:53:0x0008 }] */
        /* JADX WARN: Removed duplicated region for block: B:42:0x00bf A[Catch: Exception -> 0x002f, TRY_LEAVE, TryCatch #0 {Exception -> 0x002f, blocks: (B:8:0x001a, B:9:0x001f, B:40:0x00bc, B:10:0x0025, B:22:0x0061, B:32:0x0092, B:37:0x00a0, B:42:0x00bf, B:25:0x0072, B:28:0x0081, B:11:0x002a, B:19:0x0052, B:16:0x0037), top: B:53:0x0008 }] */
        /* JADX WARN: Type inference failed for: r1v0, types: [com.termux.MainActivity$installSelectedAI$1, int] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r9) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 298
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.termux.MainActivity.C00351.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$installSelectedAI$1$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$installSelectedAI$1$1, reason: invalid class name and collision with other inner class name */
        static final class C00071 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00071(MainActivity mainActivity, Continuation<? super C00071> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00071(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00071) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        if (this.this$0.sessions.isEmpty()) {
                            this.this$0.createSessionOrDefer();
                        }
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$installSelectedAI$1$2", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$installSelectedAI$1$2, reason: invalid class name */
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(MainActivity mainActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        this.this$0.hideSetupOverlay();
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$installSelectedAI$1$3", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$installSelectedAI$1$3, reason: invalid class name */
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $launchCmd;
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(MainActivity mainActivity, String str, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
                this.$launchCmd = str;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, this.$launchCmd, continuation);
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
                        this.this$0.showWelcomeAnimation(this.$launchCmd);
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$installSelectedAI$1$4", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$installSelectedAI$1$4, reason: invalid class name */
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(MainActivity mainActivity, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass4(this.this$0, continuation);
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
                        this.this$0.hideSetupOverlay();
                        this.this$0.createSessionOrDefer();
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    private final void installSelectedAI(String ai) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C00351(ai, this, null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showWelcomeAnimation(final String launchCmd) {
        ScrollView scrollView = this.aiChoiceScreen;
        if (scrollView != null) {
            scrollView.setVisibility(8);
        }
        LinearLayout linearLayout = this.setupProgressScreen;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        final FrameLayout welcomeOverlay = (FrameLayout) findViewById(R.id.welcome_overlay);
        TextView welcomeIcon = (TextView) findViewById(R.id.welcome_icon);
        TextView welcomeText1 = (TextView) findViewById(R.id.welcome_text1);
        TextView welcomeText2 = (TextView) findViewById(R.id.welcome_text2);
        TextView welcomeSubtext = (TextView) findViewById(R.id.welcome_subtext);
        if (welcomeOverlay == null) {
            launchAIAfterAnimation(launchCmd);
            return;
        }
        welcomeOverlay.setVisibility(0);
        if (welcomeIcon != null) {
            welcomeIcon.setAlpha(0.0f);
            welcomeIcon.setScaleX(0.8f);
            welcomeIcon.setScaleY(0.8f);
            welcomeIcon.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(800L).setStartDelay(200L).start();
        }
        if (welcomeText1 != null) {
            welcomeText1.setAlpha(0.0f);
            welcomeText1.animate().alpha(1.0f).setDuration(600L).setStartDelay(600L).start();
        }
        if (welcomeText2 != null) {
            welcomeText2.setAlpha(0.0f);
            welcomeText2.animate().alpha(1.0f).setDuration(600L).setStartDelay(900L).start();
        }
        if (welcomeSubtext != null) {
            welcomeSubtext.setAlpha(0.0f);
            welcomeSubtext.animate().alpha(1.0f).setDuration(500L).setStartDelay(1400L).start();
        }
        welcomeOverlay.postDelayed(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda55
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.showWelcomeAnimation$lambda$14(welcomeOverlay, this, launchCmd);
            }
        }, 5000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showWelcomeAnimation$lambda$14(FrameLayout $welcomeOverlay, MainActivity this$0, String launchCmd) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(launchCmd, "$launchCmd");
        $welcomeOverlay.setVisibility(8);
        this$0.launchAIAfterAnimation(launchCmd);
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.MainActivity$launchAIAfterAnimation$1", f = "MainActivity.kt", i = {}, l = {590, 599, 601}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.MainActivity$launchAIAfterAnimation$1, reason: invalid class name and case insensitive filesystem */
    static final class C00361 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $launchCmd;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00361(String str, Continuation<? super C00361> continuation) {
            super(2, continuation);
            this.$launchCmd = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivity.this.new C00361(this.$launchCmd, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00361) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$launchAIAfterAnimation$1$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$launchAIAfterAnimation$1$1, reason: invalid class name and collision with other inner class name */
        static final class C00081 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00081(MainActivity mainActivity, Continuation<? super C00081> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00081(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00081) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        this.this$0.hideSetupOverlay();
                        TerminalSession session = this.this$0.getSession();
                        if (session == null) {
                            return null;
                        }
                        byte[] bytes = "clear\n".getBytes(Charsets.UTF_8);
                        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                        session.write(bytes, 0, "clear\n".length());
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x004f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:17:0x006d A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:18:0x006e  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) throws java.lang.Throwable {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                r2 = 0
                switch(r1) {
                    case 0: goto L21;
                    case 1: goto L1c;
                    case 2: goto L17;
                    case 3: goto L12;
                    default: goto La;
                }
            La:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L12:
                r0 = r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L6f
            L17:
                r1 = r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L50
            L1c:
                r1 = r7
                kotlin.ResultKt.throwOnFailure(r8)
                goto L41
            L21:
                kotlin.ResultKt.throwOnFailure(r8)
                r1 = r7
                kotlinx.coroutines.MainCoroutineDispatcher r3 = kotlinx.coroutines.Dispatchers.getMain()
                kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
                com.termux.MainActivity$launchAIAfterAnimation$1$1 r4 = new com.termux.MainActivity$launchAIAfterAnimation$1$1
                com.termux.MainActivity r5 = com.termux.MainActivity.this
                r4.<init>(r5, r2)
                kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
                r5 = r1
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r6 = 1
                r1.label = r6
                java.lang.Object r3 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r5)
                if (r3 != r0) goto L41
                return r0
            L41:
                r3 = r1
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                r4 = 2
                r1.label = r4
                r4 = 300(0x12c, double:1.48E-321)
                java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r4, r3)
                if (r3 != r0) goto L50
                return r0
            L50:
                kotlinx.coroutines.MainCoroutineDispatcher r3 = kotlinx.coroutines.Dispatchers.getMain()
                kotlin.coroutines.CoroutineContext r3 = (kotlin.coroutines.CoroutineContext) r3
                com.termux.MainActivity$launchAIAfterAnimation$1$2 r4 = new com.termux.MainActivity$launchAIAfterAnimation$1$2
                java.lang.String r5 = r1.$launchCmd
                com.termux.MainActivity r6 = com.termux.MainActivity.this
                r4.<init>(r5, r6, r2)
                kotlin.jvm.functions.Function2 r4 = (kotlin.jvm.functions.Function2) r4
                r2 = r1
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r5 = 3
                r1.label = r5
                java.lang.Object r2 = kotlinx.coroutines.BuildersKt.withContext(r3, r4, r2)
                if (r2 != r0) goto L6e
                return r0
            L6e:
                r0 = r1
            L6f:
                kotlin.Unit r1 = kotlin.Unit.INSTANCE
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.termux.MainActivity.C00361.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$launchAIAfterAnimation$1$2", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$launchAIAfterAnimation$1$2, reason: invalid class name */
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $launchCmd;
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(String str, MainActivity mainActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$launchCmd = str;
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$launchCmd, this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        if (this.$launchCmd.length() > 0) {
                            String cmd = this.$launchCmd + "\n";
                            TerminalSession session = this.this$0.getSession();
                            if (session != null) {
                                byte[] bytes = cmd.getBytes(Charsets.UTF_8);
                                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                                session.write(bytes, 0, cmd.length());
                            }
                        }
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    private final void launchAIAfterAnimation(String launchCmd) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new C00361(launchCmd, null), 3, null);
    }

    private final void showErrorSmart(String technicalError, String friendlyMessage) {
        if (this.developerModeEnabled) {
            showError(technicalError);
        } else {
            Toast.makeText(this, friendlyMessage, 1).show();
        }
    }

    private final void showSetupOverlay() {
        if (!this.developerModeEnabled) {
            runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.showSetupOverlay$lambda$15(this.f$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSetupOverlay$lambda$15(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FrameLayout frameLayout = this$0.setupOverlay;
        if (frameLayout != null) {
            frameLayout.setVisibility(0);
        }
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideSetupOverlay() {
        runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.hideSetupOverlay$lambda$16(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void hideSetupOverlay$lambda$16(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FrameLayout frameLayout = this$0.setupOverlay;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSetupProgress(final int progress, final String status) {
        runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.updateSetupProgress$lambda$17(this.f$0, progress, status);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateSetupProgress$lambda$17(MainActivity this$0, int $progress, String status) {
        String step;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(status, "$status");
        ProgressBar progressBar = this$0.setupProgress;
        if (progressBar != null) {
            progressBar.setProgress($progress);
        }
        TextView textView = this$0.setupPercentage;
        if (textView != null) {
            textView.setText($progress + "%");
        }
        TextView textView2 = this$0.setupStatus;
        if (textView2 != null) {
            textView2.setText(status);
        }
        if ($progress < 20) {
            step = "Preparing environment...";
        } else if ($progress < 40) {
            step = "Extracting packages...";
        } else if ($progress < 60) {
            step = "Setting up shell...";
        } else if ($progress < 80) {
            step = "Configuring AI tools...";
        } else {
            step = $progress < 95 ? "Almost ready..." : "Complete!";
        }
        TextView textView3 = this$0.setupStep;
        if (textView3 == null) {
            return;
        }
        textView3.setText(step);
    }

    private final void setupUrlWatcher() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        File urlFile = new File(new File(bootstrapInstaller.getHomeDir(), ".termux"), "url_to_open");
        RunnableC00391 runnableC00391 = new RunnableC00391(urlFile, this);
        this.urlWatcherRunnable = runnableC00391;
        Handler handler = this.uiHandler;
        Intrinsics.checkNotNull(runnableC00391);
        handler.postDelayed(runnableC00391, this.urlWatchInterval);
        Log.i("MainActivity", "URL watcher started, checking every " + this.urlWatchInterval + "ms");
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, d2 = {"com/termux/MainActivity$setupUrlWatcher$1", "Ljava/lang/Runnable;", "run", "", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* renamed from: com.termux.MainActivity$setupUrlWatcher$1, reason: invalid class name and case insensitive filesystem */
    public static final class RunnableC00391 implements Runnable {
        final /* synthetic */ File $urlFile;
        final /* synthetic */ MainActivity this$0;

        RunnableC00391(File $urlFile, MainActivity $receiver) {
            this.$urlFile = $urlFile;
            this.this$0 = $receiver;
        }

        @Override // java.lang.Runnable
        public void run() throws PendingIntent.CanceledException {
            try {
                if (this.$urlFile.exists()) {
                    boolean z = true;
                    final String url = StringsKt.trim((CharSequence) FilesKt.readText$default(this.$urlFile, null, 1, null)).toString();
                    this.$urlFile.delete();
                    if (url.length() <= 0) {
                        z = false;
                    }
                    if (z) {
                        Log.i("MainActivity", "URL watcher: Opening URL: " + url);
                        try {
                            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                            intent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(this.this$0, 0, intent, 201326592);
                            pendingIntent.send();
                            Log.i("MainActivity", "URL watcher: PendingIntent sent successfully");
                        } catch (Exception e) {
                            Log.e("MainActivity", "URL watcher: Failed to open URL", e);
                            final MainActivity mainActivity = this.this$0;
                            mainActivity.runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$setupUrlWatcher$1$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MainActivity.RunnableC00391.run$lambda$0(mainActivity, url);
                                }
                            });
                        }
                    }
                }
            } catch (Exception e2) {
                Log.e("MainActivity", "URL watcher error", e2);
            }
            this.this$0.uiHandler.postDelayed(this, this.this$0.urlWatchInterval);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void run$lambda$0(MainActivity this$0, String url) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(url, "$url");
            Toast.makeText(this$0, "Failed to open: " + url, 0).show();
        }
    }

    private final void loadTermuxProperties() {
        this.termuxProperties = new Properties();
        try {
            if (getPropertiesFile().exists()) {
                FileInputStream fileInputStream = new FileInputStream(getPropertiesFile());
                try {
                    FileInputStream stream = fileInputStream;
                    Properties properties = this.termuxProperties;
                    if (properties != null) {
                        properties.load(stream);
                        Unit unit = Unit.INSTANCE;
                    }
                    CloseableKt.closeFinally(fileInputStream, null);
                    applyTermuxProperties();
                } finally {
                }
            }
        } catch (Exception e) {
            Log.w("MobileCLI", "Failed to load termux.properties: " + e.getMessage());
        }
    }

    private final void applyTermuxProperties() {
        Properties props = this.termuxProperties;
        if (props != null) {
            props.getProperty("bell-character", "vibrate");
            String useFullscreen = props.getProperty("fullscreen", "false");
            if (Intrinsics.areEqual(useFullscreen, "true")) {
                getWindow().getDecorView().setSystemUiVisibility(4102);
            }
            props.getProperty("terminal-cursor-style", "block");
            props.getProperty("back-key", "back");
        }
    }

    private final void scheduleTerminalUpdate() {
        Runnable it = this.pendingTerminalUpdate;
        if (it != null) {
            this.uiHandler.removeCallbacks(it);
        }
        Runnable runnable = new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.scheduleTerminalUpdate$lambda$21(this.f$0);
            }
        };
        this.pendingTerminalUpdate = runnable;
        Handler handler = this.uiHandler;
        Intrinsics.checkNotNull(runnable);
        handler.postDelayed(runnable, this.updateDebounceMs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void scheduleTerminalUpdate$lambda$21(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    private final void startTermuxService() {
        Intent serviceIntent = new Intent(this, (Class<?>) TermuxService.class);
        startService(serviceIntent);
        bindService(serviceIntent, this.serviceConnection, 1);
    }

    private final void checkBootstrap() throws InterruptedException, ErrnoException {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        BootstrapInstaller bootstrapInstaller2 = null;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        if (bootstrapInstaller.isInstalled()) {
            BootstrapInstaller bootstrapInstaller3 = this.bootstrapInstaller;
            if (bootstrapInstaller3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            } else {
                bootstrapInstaller2 = bootstrapInstaller3;
            }
            bootstrapInstaller2.verifyAndFix();
            if (isAISetupNeeded()) {
                if (this.developerModeEnabled) {
                    showSetupDialog("Preparing AI environment...");
                } else {
                    showSetupOverlay();
                    updateSetupProgress(70, "Configuring AI tools...");
                }
                runAISetup();
                return;
            }
            createSessionOrDefer();
            return;
        }
        if (this.developerModeEnabled) {
            showProgressDialog();
        } else {
            showSetupOverlay();
            updateSetupProgress(0, "Setting up your environment...");
        }
        installBootstrap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createSessionOrDefer() {
        TermuxService termuxService;
        List serviceSessions;
        if (this.serviceBound && (termuxService = this.termuxService) != null) {
            if (termuxService == null || (serviceSessions = termuxService.getSessions()) == null) {
                serviceSessions = CollectionsKt.emptyList();
            }
            if ((true ^ serviceSessions.isEmpty()) && this.sessions.isEmpty()) {
                Log.i("MainActivity", "Creating session: restoring from service instead");
                this.sessions.addAll(serviceSessions);
                this.currentSessionIndex = 0;
                TerminalView terminalView = this.terminalView;
                TerminalView terminalView2 = null;
                if (terminalView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView = null;
                }
                terminalView.attachSession(this.sessions.get(0));
                updateSessionTabs();
                TerminalView terminalView3 = this.terminalView;
                if (terminalView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                } else {
                    terminalView2 = terminalView3;
                }
                terminalView2.postDelayed(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.createSessionOrDefer$lambda$22(this.f$0);
                    }
                }, 500L);
                return;
            }
            if (this.sessions.isEmpty()) {
                createSession();
                return;
            }
            return;
        }
        Log.i("MainActivity", "Deferring session creation until service connects");
        this.bootstrapReadyPendingSession = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSessionOrDefer$lambda$22(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.animate().alpha(1.0f).setDuration(300L).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAISetupNeeded() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        File setupMarker = new File(bootstrapInstaller.getHomeDir(), ".mobilecli/.setup_complete");
        return !setupMarker.exists();
    }

    private final boolean isClaudeInstalled() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        BootstrapInstaller bootstrapInstaller2 = null;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        File claudeBin = new File(bootstrapInstaller.getBinDir(), "claude");
        BootstrapInstaller bootstrapInstaller3 = this.bootstrapInstaller;
        if (bootstrapInstaller3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
        } else {
            bootstrapInstaller2 = bootstrapInstaller3;
        }
        File nodeBin = new File(bootstrapInstaller2.getBinDir(), "node");
        return claudeBin.exists() && nodeBin.exists();
    }

    private final void checkAndOfferAISetup() {
        SharedPreferences prefs = getSharedPreferences("mobilecli", 0);
        boolean hasSetup = prefs.getBoolean("ai_setup_complete", false);
        if (!hasSetup) {
            TerminalView terminalView = this.terminalView;
            if (terminalView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                terminalView = null;
            }
            terminalView.postDelayed(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda44
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.checkAndOfferAISetup$lambda$23(this.f$0);
                }
            }, 1000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkAndOfferAISetup$lambda$23(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showAIChooserDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showAIChooserDialog() {
        final SharedPreferences prefs = getSharedPreferences("mobilecli", 0);
        String[] options = {"Claude Code (Recommended)", "Google Gemini CLI", "OpenAI Codex CLI", "All AI Tools", "None - Basic Terminal"};
        new AlertDialog.Builder(this).setTitle("Welcome to MobileCLI!").setIcon(android.R.drawable.ic_dialog_info).setSingleChoiceItems(options, 0, (DialogInterface.OnClickListener) null).setPositiveButton("Install", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda78
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showAIChooserDialog$lambda$24(prefs, this, dialogInterface, i);
            }
        }).setNegativeButton("Skip", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showAIChooserDialog$lambda$25(prefs, this, dialogInterface, i);
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAIChooserDialog$lambda$24(SharedPreferences $prefs, MainActivity this$0, DialogInterface dialog, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(dialog, "null cannot be cast to non-null type android.app.AlertDialog");
        int selected = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
        $prefs.edit().putBoolean("ai_setup_complete", true).apply();
        switch (selected) {
            case 0:
                this$0.installAI("claude");
                break;
            case 1:
                this$0.installAI("gemini");
                break;
            case 2:
                this$0.installAI("codex");
                break;
            case 3:
                this$0.installAI("all");
                break;
            case 4:
                Toast.makeText(this$0, "Basic terminal ready!", 0).show();
                break;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showAIChooserDialog$lambda$25(SharedPreferences $prefs, MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        $prefs.edit().putBoolean("ai_setup_complete", true).apply();
        Toast.makeText(this$0, "You can install AI tools later from Settings", 1).show();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void installAI(java.lang.String r7) {
        /*
            r6 = this;
            int r0 = r7.hashCode()
            switch(r0) {
                case -1357935714: goto L50;
                case -1249537483: goto L38;
                case 96673: goto L20;
                case 94834731: goto L8;
                default: goto L7;
            }
        L7:
            goto L68
        L8:
            java.lang.String r0 = "codex"
            boolean r0 = r7.equals(r0)
            if (r0 != 0) goto L11
            goto L7
        L11:
            kotlin.Triple r0 = new kotlin.Triple
            java.lang.String r1 = "Install Codex CLI"
            java.lang.String r2 = "pkg update -y && pkg upgrade -y && pkg install nodejs-lts -y && npm install -g @openai/codex && echo '\\n✓ Done! Type: codex'\n"
            java.lang.String r3 = "1. pkg update && pkg upgrade\n2. pkg install nodejs-lts\n3. npm install -g @openai/codex\n4. codex"
            r0.<init>(r1, r2, r3)
            goto L6f
        L20:
            java.lang.String r0 = "all"
            boolean r0 = r7.equals(r0)
            if (r0 != 0) goto L29
            goto L7
        L29:
            kotlin.Triple r0 = new kotlin.Triple
            java.lang.String r1 = "Install All AI Tools"
            java.lang.String r2 = "pkg update -y && pkg upgrade -y && pkg install nodejs-lts -y && npm install -g @anthropic-ai/claude-code && npm install -g @google/gemini-cli && npm install -g @openai/codex && echo '\\n✓ All AI tools installed!'\n"
            java.lang.String r3 = "1. pkg update && pkg upgrade\n2. pkg install nodejs-lts\n3. npm install -g @anthropic-ai/claude-code\n4. npm install -g @google/gemini-cli\n5. npm install -g @openai/codex"
            r0.<init>(r1, r2, r3)
            goto L6f
        L38:
            java.lang.String r0 = "gemini"
            boolean r0 = r7.equals(r0)
            if (r0 != 0) goto L41
            goto L7
        L41:
            kotlin.Triple r0 = new kotlin.Triple
            java.lang.String r1 = "Install Gemini CLI"
            java.lang.String r2 = "pkg update -y && pkg upgrade -y && pkg install nodejs-lts -y && npm install -g @google/gemini-cli && echo '\\n✓ Done! Type: gemini'\n"
            java.lang.String r3 = "1. pkg update && pkg upgrade\n2. pkg install nodejs-lts\n3. npm install -g @google/gemini-cli\n4. gemini"
            r0.<init>(r1, r2, r3)
            goto L6f
        L50:
            java.lang.String r0 = "claude"
            boolean r0 = r7.equals(r0)
            if (r0 != 0) goto L59
            goto L7
        L59:
            kotlin.Triple r0 = new kotlin.Triple
            java.lang.String r1 = "Install Claude Code"
            java.lang.String r2 = "pkg update -y && pkg upgrade -y && pkg install nodejs-lts -y && npm install -g @anthropic-ai/claude-code && echo '\\n✓ Done! Type: claude'\n"
            java.lang.String r3 = "1. pkg update && pkg upgrade\n2. pkg install nodejs-lts\n3. npm install -g @anthropic-ai/claude-code\n4. claude"
            r0.<init>(r1, r2, r3)
            goto L6f
        L68:
            kotlin.Triple r0 = new kotlin.Triple
            java.lang.String r1 = ""
            r0.<init>(r1, r1, r1)
        L6f:
            java.lang.Object r1 = r0.component1()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r0.component2()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r0 = r0.component3()
            java.lang.String r0 = (java.lang.String) r0
            r3 = r1
            java.lang.CharSequence r3 = (java.lang.CharSequence) r3
            int r3 = r3.length()
            if (r3 <= 0) goto L8c
            r3 = 1
            goto L8d
        L8c:
            r3 = 0
        L8d:
            if (r3 == 0) goto Lcc
            android.app.AlertDialog$Builder r3 = new android.app.AlertDialog$Builder
            r4 = r6
            android.content.Context r4 = (android.content.Context) r4
            r3.<init>(r4)
            r4 = r1
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            android.app.AlertDialog$Builder r3 = r3.setTitle(r4)
            java.lang.String r4 = "Choose installation method:"
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            android.app.AlertDialog$Builder r3 = r3.setMessage(r4)
            java.lang.String r4 = "Quick Install"
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            com.termux.MainActivity$$ExternalSyntheticLambda0 r5 = new com.termux.MainActivity$$ExternalSyntheticLambda0
            r5.<init>()
            android.app.AlertDialog$Builder r3 = r3.setPositiveButton(r4, r5)
            java.lang.String r4 = "Manual"
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            com.termux.MainActivity$$ExternalSyntheticLambda11 r5 = new com.termux.MainActivity$$ExternalSyntheticLambda11
            r5.<init>()
            android.app.AlertDialog$Builder r3 = r3.setNeutralButton(r4, r5)
            java.lang.String r4 = "Cancel"
            java.lang.CharSequence r4 = (java.lang.CharSequence) r4
            r5 = 0
            android.app.AlertDialog$Builder r3 = r3.setNegativeButton(r4, r5)
            r3.show()
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.MainActivity.installAI(java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installAI$lambda$26(MainActivity this$0, String autoCmd, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(autoCmd, "$autoCmd");
        TerminalSession session = this$0.getSession();
        if (session != null) {
            byte[] bytes = autoCmd.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] bytes2 = autoCmd.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
            session.write(bytes, 0, bytes2.length);
        }
        Toast.makeText(this$0, "Installing... please wait", 1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installAI$lambda$28(final MainActivity this$0, String manualSteps, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(manualSteps, "$manualSteps");
        new AlertDialog.Builder(this$0).setTitle("Manual Installation").setMessage("Run these commands:\n\n" + manualSteps + "\n\nPress Y when prompted.").setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda31
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface2, int i2) {
                MainActivity.installAI$lambda$28$lambda$27(this.f$0, dialogInterface2, i2);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void installAI$lambda$28$lambda$27(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalView terminalView = this$0.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.requestFocus();
        Object systemService = this$0.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        InputMethodManager imm = (InputMethodManager) systemService;
        TerminalView terminalView3 = this$0.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        imm.showSoftInput(terminalView2, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showSetupDialog(String message) {
        View view = getLayoutInflater().inflate(R.layout.dialog_progress, (ViewGroup) null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        TextView textView = (TextView) view.findViewById(R.id.progress_text);
        this.progressText = textView;
        if (textView != null) {
            textView.setText(message);
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle("MobileCLI").setView(view).setCancelable(false).create();
        this.progressDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.MainActivity$runAISetup$1", f = "MainActivity.kt", i = {0, 1}, l = {983, 993, PointerIconCompat.TYPE_WAIT, PointerIconCompat.TYPE_CELL, 1058}, m = "invokeSuspend", n = {"configDir", "configDir"}, s = {"L$0", "L$0"})
    /* renamed from: com.termux.MainActivity$runAISetup$1, reason: invalid class name and case insensitive filesystem */
    static final class C00371 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        Object L$0;
        int label;

        C00371(Continuation<? super C00371> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivity.this.new C00371(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C00371) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x009b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00cd A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00e9 A[RETURN] */
        /* JADX WARN: Type inference failed for: r1v0, types: [com.termux.MainActivity$runAISetup$1, int] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r11) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 286
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.termux.MainActivity.C00371.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$runAISetup$1$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$runAISetup$1$1, reason: invalid class name and collision with other inner class name */
        static final class C00091 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00091(MainActivity mainActivity, Continuation<? super C00091> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C00091(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C00091) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        TextView textView = this.this$0.progressText;
                        if (textView != null) {
                            textView.setText("Setting up AI configuration...");
                        }
                        ProgressBar progressBar = this.this$0.progressBar;
                        if (progressBar != null) {
                            progressBar.setProgress(20);
                        }
                        if (!this.this$0.developerModeEnabled) {
                            this.this$0.updateSetupProgress(80, "Configuring AI assistant...");
                        }
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$runAISetup$1$2", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$runAISetup$1$2, reason: invalid class name */
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(MainActivity mainActivity, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        TextView textView = this.this$0.progressText;
                        if (textView != null) {
                            textView.setText("Configuration complete!");
                        }
                        ProgressBar progressBar = this.this$0.progressBar;
                        if (progressBar != null) {
                            progressBar.setProgress(100);
                        }
                        if (!this.this$0.developerModeEnabled) {
                            this.this$0.updateSetupProgress(95, "Almost ready...");
                        }
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$runAISetup$1$3", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$runAISetup$1$3, reason: invalid class name */
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Object>, Object> {
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(MainActivity mainActivity, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(CoroutineScope coroutineScope, Continuation<? super Object> continuation) {
                return invoke2(coroutineScope, (Continuation<Object>) continuation);
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Object invoke2(CoroutineScope coroutineScope, Continuation<Object> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                final String launchCmd;
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        AlertDialog alertDialog = this.this$0.progressDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        SharedPreferences prefs = this.this$0.getSharedPreferences("mobilecli", 0);
                        boolean hasChosenAI = prefs.getBoolean("ai_setup_complete", false);
                        if (hasChosenAI) {
                            this.this$0.createSessionOrDefer();
                            TerminalView terminalView = null;
                            String chosenAI = prefs.getString("selected_ai", null);
                            if (chosenAI == null || Intrinsics.areEqual(chosenAI, this.this$0.AI_NONE)) {
                                this.this$0.hideSetupOverlay();
                                return Unit.INSTANCE;
                            }
                            if (Intrinsics.areEqual(chosenAI, this.this$0.AI_CLAUDE)) {
                                launchCmd = "claude";
                            } else if (Intrinsics.areEqual(chosenAI, this.this$0.AI_GEMINI)) {
                                launchCmd = "gemini";
                            } else {
                                launchCmd = Intrinsics.areEqual(chosenAI, this.this$0.AI_CODEX) ? "codex" : null;
                            }
                            if (launchCmd == null || !this.this$0.isAIInstalled(chosenAI)) {
                                this.this$0.hideSetupOverlay();
                                return Unit.INSTANCE;
                            }
                            TerminalView terminalView2 = this.this$0.terminalView;
                            if (terminalView2 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                            } else {
                                terminalView = terminalView2;
                            }
                            final MainActivity mainActivity = this.this$0;
                            return Boxing.boxBoolean(terminalView.postDelayed(new Runnable() { // from class: com.termux.MainActivity$runAISetup$1$3$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    MainActivity.C00371.AnonymousClass3.invokeSuspend$lambda$1(launchCmd, mainActivity);
                                }
                            }, 1500L));
                        }
                        if (!this.this$0.developerModeEnabled) {
                            this.this$0.showAIChoiceScreen();
                            return Unit.INSTANCE;
                        }
                        this.this$0.hideSetupOverlay();
                        this.this$0.createSessionOrDefer();
                        this.this$0.showAIChooserDialog();
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void invokeSuspend$lambda$1(String $launchCmd, final MainActivity this$0) {
                String cmd = "clear && " + $launchCmd + "\n";
                TerminalSession session = this$0.getSession();
                if (session != null) {
                    byte[] bytes = cmd.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    session.write(bytes, 0, cmd.length());
                }
                TerminalView terminalView = this$0.terminalView;
                if (terminalView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView = null;
                }
                terminalView.postDelayed(new Runnable() { // from class: com.termux.MainActivity$runAISetup$1$3$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.access$hideSetupOverlay(this$0);
                    }
                }, 500L);
            }
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$runAISetup$1$4", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$runAISetup$1$4, reason: invalid class name */
        static final class AnonymousClass4 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass4(MainActivity mainActivity, Exception exc, Continuation<? super AnonymousClass4> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
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
                        AlertDialog alertDialog = this.this$0.progressDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        this.this$0.hideSetupOverlay();
                        this.this$0.showError("AI setup failed: " + this.$e.getMessage());
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void runAISetup() {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C00371(null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAIInstalled(String ai) {
        String binName;
        if (Intrinsics.areEqual(ai, this.AI_CLAUDE)) {
            binName = "claude";
        } else if (Intrinsics.areEqual(ai, this.AI_GEMINI)) {
            binName = "gemini";
        } else {
            if (!Intrinsics.areEqual(ai, this.AI_CODEX)) {
                return false;
            }
            binName = "codex";
        }
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        return new File(bootstrapInstaller.getBinDir(), binName).exists();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createSystemPrompt() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        File homeDir = bootstrapInstaller.getHomeDir();
        File configDir = new File(homeDir, ".mobilecli");
        configDir.mkdirs();
        FilesKt.writeText$default(new File(configDir, "SYSTEM_PROMPT.md"), "# MobileCLI AI Environment - System Knowledge\n\n## YOU ARE RUNNING IN MOBILECLI - A MOBILE AI DEVELOPMENT ENVIRONMENT\n\nThis is a Termux-compatible environment on Android. You have full terminal access.\n\n## CRITICAL PATHS\n\n### Phone Storage\n- Screenshots: `/sdcard/DCIM/Screenshots/`\n- Camera photos: `/sdcard/DCIM/Camera/`\n- Downloads: `/sdcard/Download/`\n- Documents: `/sdcard/Documents/`\n\n### Environment\n- HOME: `$HOME` = `/data/data/com.termux/files/home`\n- PREFIX: `$PREFIX` = `/data/data/com.termux/files/usr`\n- Binaries: `/data/data/com.termux/files/usr/bin`\n\n## HOW TO VIEW IMAGES/SCREENSHOTS\n\nUse the Read tool to view any image:\n```\nRead /sdcard/DCIM/Screenshots/Screenshot_*.jpg\n```\n\nList recent screenshots:\n```bash\nls -lt /sdcard/DCIM/Screenshots/ | head -10\n```\n\n## PACKAGE MANAGEMENT\n\nInstall packages:\n```bash\npkg install <package>\npkg update && pkg upgrade\n```\n\nCommon packages: nodejs, python, git, openssh, curl, wget\n\n## BUILDING ANDROID APPS\n\nAndroid SDK location: `~/android-sdk`\n\nBuild APK:\n```bash\ncd <project>\n./gradlew assembleDebug\n```\n\nCopy to Downloads for installation:\n```bash\ncp app/build/outputs/apk/debug/*.apk /sdcard/Download/\n```\n\n## GITHUB SETUP\n\n```bash\npkg install git gh\ngh auth login\ngit config --global user.name \"Your Name\"\ngit config --global user.email \"your@email.com\"\n```\n\n## VERCEL DEPLOYMENT\n\n```bash\nnpm install -g vercel\nvercel login\nvercel --prod\n```\n\n## AI CLI TOOLS\n\n- Claude Code: `claude`\n- Gemini: `gemini`\n- Codex: `codex`\n\n## BEST PRACTICES\n\n1. Always check screenshots when user mentions them\n2. Build and test frequently\n3. Copy APKs to /sdcard/Download/ for easy installation\n4. Use pkg for system packages, npm for Node packages\n5. The user is on mobile - keep responses concise\n\n## REMEMBER\n\nYou are inside MobileCLI, a premium mobile development environment.\nThe user expects things to \"just work\" - be proactive and helpful.", null, 2, null);
        File claudeMd = new File(homeDir, "CLAUDE.md");
        if (!claudeMd.exists()) {
            FilesKt.writeText$default(claudeMd, "# MobileCLI AI Environment - System Knowledge\n\n## YOU ARE RUNNING IN MOBILECLI - A MOBILE AI DEVELOPMENT ENVIRONMENT\n\nThis is a Termux-compatible environment on Android. You have full terminal access.\n\n## CRITICAL PATHS\n\n### Phone Storage\n- Screenshots: `/sdcard/DCIM/Screenshots/`\n- Camera photos: `/sdcard/DCIM/Camera/`\n- Downloads: `/sdcard/Download/`\n- Documents: `/sdcard/Documents/`\n\n### Environment\n- HOME: `$HOME` = `/data/data/com.termux/files/home`\n- PREFIX: `$PREFIX` = `/data/data/com.termux/files/usr`\n- Binaries: `/data/data/com.termux/files/usr/bin`\n\n## HOW TO VIEW IMAGES/SCREENSHOTS\n\nUse the Read tool to view any image:\n```\nRead /sdcard/DCIM/Screenshots/Screenshot_*.jpg\n```\n\nList recent screenshots:\n```bash\nls -lt /sdcard/DCIM/Screenshots/ | head -10\n```\n\n## PACKAGE MANAGEMENT\n\nInstall packages:\n```bash\npkg install <package>\npkg update && pkg upgrade\n```\n\nCommon packages: nodejs, python, git, openssh, curl, wget\n\n## BUILDING ANDROID APPS\n\nAndroid SDK location: `~/android-sdk`\n\nBuild APK:\n```bash\ncd <project>\n./gradlew assembleDebug\n```\n\nCopy to Downloads for installation:\n```bash\ncp app/build/outputs/apk/debug/*.apk /sdcard/Download/\n```\n\n## GITHUB SETUP\n\n```bash\npkg install git gh\ngh auth login\ngit config --global user.name \"Your Name\"\ngit config --global user.email \"your@email.com\"\n```\n\n## VERCEL DEPLOYMENT\n\n```bash\nnpm install -g vercel\nvercel login\nvercel --prod\n```\n\n## AI CLI TOOLS\n\n- Claude Code: `claude`\n- Gemini: `gemini`\n- Codex: `codex`\n\n## BEST PRACTICES\n\n1. Always check screenshots when user mentions them\n2. Build and test frequently\n3. Copy APKs to /sdcard/Download/ for easy installation\n4. Use pkg for system packages, npm for Node packages\n5. The user is on mobile - keep responses concise\n\n## REMEMBER\n\nYou are inside MobileCLI, a premium mobile development environment.\nThe user expects things to \"just work\" - be proactive and helpful.", null, 2, null);
        }
    }

    private final void showProgressDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_progress, (ViewGroup) null);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        this.progressText = (TextView) view.findViewById(R.id.progress_text);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle("Setting up MobileCLI").setView(view).setCancelable(false).create();
        this.progressDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0002\b\u0006"}, d2 = {"<anonymous>", "", "progress", "", "message", "", "invoke"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* renamed from: com.termux.MainActivity$installBootstrap$1, reason: invalid class name */
    static final class AnonymousClass1 extends Lambda implements Function2<Integer, String, Unit> {
        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
            invoke(num.intValue(), str);
            return Unit.INSTANCE;
        }

        public final void invoke(final int progress, final String message) {
            Intrinsics.checkNotNullParameter(message, "message");
            final MainActivity mainActivity = MainActivity.this;
            mainActivity.runOnUiThread(new Runnable() { // from class: com.termux.MainActivity$installBootstrap$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.AnonymousClass1.invoke$lambda$0(progress, mainActivity, message);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invoke$lambda$0(int $progress, MainActivity this$0, String message) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(message, "$message");
            if ($progress >= 0) {
                ProgressBar progressBar = this$0.progressBar;
                if (progressBar != null) {
                    progressBar.setProgress($progress);
                }
                TextView textView = this$0.progressText;
                if (textView != null) {
                    textView.setText(message);
                }
                if (!this$0.developerModeEnabled) {
                    this$0.updateSetupProgress($progress, message);
                    return;
                }
                return;
            }
            AlertDialog alertDialog = this$0.progressDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            this$0.hideSetupOverlay();
            this$0.showError(message);
        }
    }

    private final void installBootstrap() {
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        bootstrapInstaller.setOnProgress(new AnonymousClass1());
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new AnonymousClass2(null), 2, null);
    }

    /* compiled from: MainActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "com.termux.MainActivity$installBootstrap$2", f = "MainActivity.kt", i = {}, l = {1224, 1225}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: com.termux.MainActivity$installBootstrap$2, reason: invalid class name */
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0054  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0066 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0067  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r10) throws java.lang.Throwable {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                r2 = 0
                r3 = 1
                switch(r1) {
                    case 0: goto L1f;
                    case 1: goto L18;
                    case 2: goto L13;
                    default: goto Lb;
                }
            Lb:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L13:
                r0 = r9
                kotlin.ResultKt.throwOnFailure(r10)
                goto L69
            L18:
                r1 = r9
                kotlin.ResultKt.throwOnFailure(r10)
                r4 = r1
                r1 = r10
                goto L41
            L1f:
                kotlin.ResultKt.throwOnFailure(r10)
                r1 = r9
                com.termux.MainActivity r4 = com.termux.MainActivity.this
                com.termux.BootstrapInstaller r4 = com.termux.MainActivity.access$getBootstrapInstaller$p(r4)
                if (r4 != 0) goto L31
                java.lang.String r4 = "bootstrapInstaller"
                kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r4)
                r4 = r2
            L31:
                r5 = r1
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r1.label = r3
                java.lang.Object r4 = r4.install(r5)
                if (r4 != r0) goto L3d
                return r0
            L3d:
                r8 = r1
                r1 = r10
                r10 = r4
                r4 = r8
            L41:
                java.lang.Boolean r10 = (java.lang.Boolean) r10
                boolean r10 = r10.booleanValue()
                kotlinx.coroutines.MainCoroutineDispatcher r5 = kotlinx.coroutines.Dispatchers.getMain()
                kotlin.coroutines.CoroutineContext r5 = (kotlin.coroutines.CoroutineContext) r5
                com.termux.MainActivity$installBootstrap$2$1 r6 = new com.termux.MainActivity$installBootstrap$2$1
                com.termux.MainActivity r7 = com.termux.MainActivity.this
                if (r10 == 0) goto L54
                goto L55
            L54:
                r3 = 0
            L55:
                r6.<init>(r7, r3, r2)
                kotlin.jvm.functions.Function2 r6 = (kotlin.jvm.functions.Function2) r6
                r10 = r4
                kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
                r2 = 2
                r4.label = r2
                java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r5, r6, r10)
                if (r10 != r0) goto L67
                return r0
            L67:
                r10 = r1
                r0 = r4
            L69:
                kotlin.Unit r1 = kotlin.Unit.INSTANCE
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.termux.MainActivity.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* compiled from: MainActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
        @DebugMetadata(c = "com.termux.MainActivity$installBootstrap$2$1", f = "MainActivity.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
        /* renamed from: com.termux.MainActivity$installBootstrap$2$1, reason: invalid class name */
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ boolean $success;
            int label;
            final /* synthetic */ MainActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(MainActivity mainActivity, boolean z, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = mainActivity;
                this.$success = z;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, this.$success, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                switch (this.label) {
                    case 0:
                        ResultKt.throwOnFailure(obj);
                        AlertDialog alertDialog = this.this$0.progressDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        if (this.$success) {
                            if (this.this$0.isAISetupNeeded()) {
                                if (this.this$0.developerModeEnabled) {
                                    this.this$0.showSetupDialog("Preparing AI environment...");
                                } else {
                                    this.this$0.updateSetupProgress(70, "Configuring AI tools...");
                                }
                                this.this$0.runAISetup();
                            } else {
                                this.this$0.hideSetupOverlay();
                                this.this$0.createSessionOrDefer();
                            }
                        } else {
                            this.this$0.hideSetupOverlay();
                            this.this$0.showError("Bootstrap installation failed");
                        }
                        return Unit.INSTANCE;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showError(String message) {
        new AlertDialog.Builder(this).setTitle("Error").setMessage(message).setPositiveButton("Retry", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda35
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws InterruptedException, ErrnoException {
                MainActivity.showError$lambda$29(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton("Exit", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda36
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showError$lambda$30(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showError$lambda$29(MainActivity this$0, DialogInterface dialogInterface, int i) throws InterruptedException, ErrnoException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.checkBootstrap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showError$lambda$30(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    private final void setupExtraKeys() {
        ((Button) findViewById(R.id.btn_esc)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$31(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_ctrl)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$32(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_alt)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda20
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$33(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_tab)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$34(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_home)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$35(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_up)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$36(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_end)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$37(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_pgup)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda26
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$38(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_dash)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda27
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$39(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_slash)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$40(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_backslash)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$41(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_pipe)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$42(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_left)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$43(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_down)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$44(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_right)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$45(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_pgdn)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda12
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$46(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_tilde)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$47(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_underscore)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$48(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_colon)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$49(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_quote)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$50(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_more)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupExtraKeys$lambda$51(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_more)).setOnLongClickListener(new View.OnLongClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda19
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return MainActivity.setupExtraKeys$lambda$52(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$31(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendKey(27);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$32(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isCtrlPressed = !this$0.isCtrlPressed;
        this$0.updateModifierButtons();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$33(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isAltPressed = !this$0.isAltPressed;
        this$0.updateModifierButtons();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$34(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendKey(9);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$35(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[H");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$36(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[A");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$37(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[F");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$38(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[5~");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$39(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('-');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$40(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('/');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$41(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('\\');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$42(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('|');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$43(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[D");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$44(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[B");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$45(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[C");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$46(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendSpecialKey("\u001b[6~");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$47(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('~');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$48(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar('_');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$49(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar(':');
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$50(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.sendChar(Typography.quote);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupExtraKeys$lambda$51(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showMoreOptions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean setupExtraKeys$lambda$52(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showContextMenu(null);
        return true;
    }

    private final void updateModifierButtons() {
        ((Button) findViewById(R.id.btn_ctrl)).setAlpha(this.isCtrlPressed ? 1.0f : 0.5f);
        ((Button) findViewById(R.id.btn_alt)).setAlpha(this.isAltPressed ? 1.0f : 0.5f);
    }

    private final void setupNavDrawer() {
        ((TextView) findViewById(R.id.nav_new_session)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda59
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$53(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_settings)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda60
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$54(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_keyboard)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda61
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$55(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_text_size)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda62
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$56(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_install_ai)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda63
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$57(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_help)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda64
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$58(this.f$0, view);
            }
        });
        ((TextView) findViewById(R.id.nav_about)).setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda65
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MainActivity.setupNavDrawer$lambda$59(this.f$0, view);
            }
        });
        setupPowerModeToggles();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$53(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        int size = this$0.sessions.size();
        int i = this$0.maxSessions;
        if (size < i) {
            this$0.addNewSession();
            Toast.makeText(this$0, "Session " + this$0.sessions.size() + " created", 0).show();
            return;
        }
        Toast.makeText(this$0, "Maximum " + i + " sessions reached", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$54(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.showSettingsDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$55(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        Object systemService = this$0.getSystemService("input_method");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
        InputMethodManager imm = (InputMethodManager) systemService;
        imm.toggleSoftInput(0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$56(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.showTextSizeDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$57(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.showAIChooserDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$58(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.showHelpDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupNavDrawer$lambda$59(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
        this$0.showAboutDialog();
    }

    private final void setupPowerModeToggles() {
        SharedPreferences prefs = getSharedPreferences(this.PREFS_NAME, 0);
        this.isWakeLockEnabled = prefs.getBoolean(this.KEY_WAKE_LOCK, false);
        this.isClaudePowerEnabled = prefs.getBoolean(this.KEY_CLAUDE_POWER, true);
        this.wakeLockToggle = (TextView) findViewById(R.id.nav_wake_lock);
        this.claudePowerToggle = (TextView) findViewById(R.id.nav_claude_power);
        updatePowerModeUI();
        TextView textView = this.wakeLockToggle;
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda42
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupPowerModeToggles$lambda$60(this.f$0, view);
                }
            });
        }
        TextView textView2 = this.claudePowerToggle;
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda43
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.setupPowerModeToggles$lambda$61(this.f$0, view);
                }
            });
        }
        if (this.isClaudePowerEnabled) {
            applyClaudePowerMode(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupPowerModeToggles$lambda$60(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.toggleWakeLock();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupPowerModeToggles$lambda$61(MainActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.toggleClaudePowerMode();
    }

    private final void toggleWakeLock() {
        String action;
        boolean z = !this.isWakeLockEnabled;
        this.isWakeLockEnabled = z;
        if (z) {
            action = TermuxService.ACTION_WAKE_LOCK;
        } else {
            action = TermuxService.ACTION_WAKE_UNLOCK;
        }
        Intent intent = new Intent(this, (Class<?>) TermuxService.class);
        intent.setAction(action);
        startService(intent);
        getSharedPreferences(this.PREFS_NAME, 0).edit().putBoolean(this.KEY_WAKE_LOCK, this.isWakeLockEnabled).apply();
        updatePowerModeUI();
        String status = this.isWakeLockEnabled ? "ON - Screen can turn off" : "OFF";
        Toast.makeText(this, "Wake Lock: " + status, 0).show();
    }

    private final void toggleClaudePowerMode() {
        boolean z = !this.isClaudePowerEnabled;
        this.isClaudePowerEnabled = z;
        applyClaudePowerMode(z);
        getSharedPreferences(this.PREFS_NAME, 0).edit().putBoolean(this.KEY_CLAUDE_POWER, this.isClaudePowerEnabled).apply();
        updatePowerModeUI();
        String status = this.isClaudePowerEnabled ? "ON - No permission prompts" : "OFF";
        Toast.makeText(this, "Claude Power Mode: " + status, 0).show();
    }

    private final void applyClaudePowerMode(boolean enabled) {
        try {
            BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
            if (bootstrapInstaller == null) {
                Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
                bootstrapInstaller = null;
            }
            File bashrcFile = new File(bootstrapInstaller.getHomeDir(), ".bashrc");
            String content = bashrcFile.exists() ? FilesKt.readText$default(bashrcFile, null, 1, null) : "";
            Iterable $this$filterNot$iv = StringsKt.lines(content);
            Collection destination$iv$iv = new ArrayList();
            for (Object element$iv$iv : $this$filterNot$iv) {
                String it = (String) element$iv$iv;
                String content2 = content;
                Iterable $this$filterNot$iv2 = $this$filterNot$iv;
                if (!StringsKt.contains$default((CharSequence) it, (CharSequence) "--dangerously-skip-permissions", false, 2, (Object) null)) {
                    destination$iv$iv.add(element$iv$iv);
                }
                content = content2;
                $this$filterNot$iv = $this$filterNot$iv2;
            }
            String content3 = CollectionsKt.joinToString$default((List) destination$iv$iv, "\n", null, null, 0, null, null, 62, null);
            if (enabled) {
                content3 = StringsKt.trimEnd((CharSequence) content3).toString() + "\n\n# Claude Power Mode\nalias claude=\"claude --dangerously-skip-permissions\"\nalias cc=\"claude --dangerously-skip-permissions\"\n";
            }
            FilesKt.writeText$default(bashrcFile, content3, null, 2, null);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to update bashrc for Claude Power Mode", e);
        }
    }

    private final void updatePowerModeUI() {
        TextView textView = this.wakeLockToggle;
        if (textView != null) {
            textView.setText(this.isWakeLockEnabled ? "⚡ Wake Lock: ON" : "⚡ Wake Lock: OFF");
        }
        TextView textView2 = this.wakeLockToggle;
        if (textView2 != null) {
            textView2.setTextColor(this.isWakeLockEnabled ? -16711800 : -1);
        }
        TextView textView3 = this.claudePowerToggle;
        if (textView3 != null) {
            textView3.setText(this.isClaudePowerEnabled ? "🚀 Claude Power Mode: ON" : "🚀 Claude Power Mode: OFF");
        }
        TextView textView4 = this.claudePowerToggle;
        if (textView4 != null) {
            textView4.setTextColor(this.isClaudePowerEnabled ? -16711800 : -1);
        }
    }

    private final void addNewSession() {
        TerminalSession newSession = createNewTerminalSession();
        this.sessions.add(newSession);
        this.currentSessionIndex = this.sessions.size() - 1;
        TerminalView terminalView = this.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.attachSession(newSession);
        TerminalView terminalView3 = this.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda66
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.addNewSession$lambda$64(this.f$0);
            }
        });
        updateSessionTabs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addNewSession$lambda$64(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.requestFocus();
    }

    private final void switchToSession(int index) {
        if (index >= 0 && index < this.sessions.size() && index != this.currentSessionIndex) {
            this.currentSessionIndex = index;
            TerminalView terminalView = this.terminalView;
            TerminalView terminalView2 = null;
            if (terminalView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                terminalView = null;
            }
            terminalView.attachSession(this.sessions.get(index));
            TerminalView terminalView3 = this.terminalView;
            if (terminalView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            } else {
                terminalView2 = terminalView3;
            }
            terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() {
                    MainActivity.switchToSession$lambda$65(this.f$0);
                }
            });
            updateSessionTabs();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void switchToSession$lambda$65(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    private final void killCurrentSession() throws ErrnoException {
        TerminalSession sessionToKill;
        if (this.sessions.isEmpty() || (sessionToKill = getSession()) == null) {
            return;
        }
        byte[] bytes = "\r\n[Process completed - press Enter to close]\r\n".getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        sessionToKill.write(bytes, 0, "\r\n[Process completed - press Enter to close]\r\n".length());
        sessionToKill.finishIfRunning();
    }

    private final void removeSession(int index) {
        if (this.sessions.size() <= 1) {
            this.sessions.clear();
            this.currentSessionIndex = 0;
            addNewSession();
            return;
        }
        this.sessions.remove(index);
        if (this.currentSessionIndex >= this.sessions.size()) {
            this.currentSessionIndex = this.sessions.size() - 1;
        }
        TerminalView terminalView = this.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.attachSession(this.sessions.get(this.currentSessionIndex));
        TerminalView terminalView3 = this.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda50
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.removeSession$lambda$66(this.f$0);
            }
        });
        updateSessionTabs();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeSession$lambda$66(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSessionTabs() {
        HorizontalScrollView tabsContainer = (HorizontalScrollView) findViewById(R.id.session_tabs_container);
        LinearLayout tabsLayout = (LinearLayout) findViewById(R.id.session_tabs);
        if (tabsContainer != null && tabsLayout != null) {
            tabsLayout.removeAllViews();
            if (this.sessions.size() > 1) {
                tabsContainer.setVisibility(0);
                Iterable $this$forEachIndexed$iv = this.sessions;
                final int index = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    int index$iv = index + 1;
                    if (index < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    Button $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469 = new Button(this);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setText(String.valueOf(index + 1));
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setTextSize(12.0f);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setTextColor(index == this.currentSessionIndex ? -16711936 : -1);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setBackgroundColor(index == this.currentSessionIndex ? -13421773 : -15066598);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setPadding(24, 8, 24, 8);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setMinimumWidth(0);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setMinWidth(0);
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda32
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            MainActivity.updateSessionTabs$lambda$70$lambda$69$lambda$67(this.f$0, index, view);
                        }
                    });
                    $this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda34
                        @Override // android.view.View.OnLongClickListener
                        public final boolean onLongClick(View view) {
                            return MainActivity.updateSessionTabs$lambda$70$lambda$69$lambda$68(this.f$0, index, view);
                        }
                    });
                    tabsLayout.addView($this$updateSessionTabs_u24lambda_u2470_u24lambda_u2469);
                    index = index$iv;
                }
            } else {
                tabsContainer.setVisibility(8);
            }
        }
        updateDrawerSessionsList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateSessionTabs$lambda$70$lambda$69$lambda$67(MainActivity this$0, int $index, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.switchToSession($index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean updateSessionTabs$lambda$70$lambda$69$lambda$68(MainActivity this$0, int $index, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showSessionOptions($index);
        return true;
    }

    private final void updateDrawerSessionsList() {
        LinearLayout sessionsList = (LinearLayout) findViewById(R.id.sessions_list);
        if (sessionsList == null) {
            return;
        }
        sessionsList.removeAllViews();
        Iterable $this$forEachIndexed$iv = this.sessions;
        final int index = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            int index$iv = index + 1;
            if (index < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            TextView $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473 = new TextView(this);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setText((index == this.currentSessionIndex ? new StringBuilder().append("● Session ").append(index + 1).append(" (active)") : new StringBuilder().append("○ Session ").append(index + 1)).toString());
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setTextSize(16.0f);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setTextColor(index == this.currentSessionIndex ? -11751600 : -1);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setPadding(24, 24, 24, 24);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setBackgroundColor(index == this.currentSessionIndex ? -14013910 : 0);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setClickable(true);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setFocusable(true);
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setOnClickListener(new View.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda48
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    MainActivity.updateDrawerSessionsList$lambda$74$lambda$73$lambda$71(this.f$0, index, view);
                }
            });
            $this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda49
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return MainActivity.updateDrawerSessionsList$lambda$74$lambda$73$lambda$72(this.f$0, index, view);
                }
            });
            sessionsList.addView($this$updateDrawerSessionsList_u24lambda_u2474_u24lambda_u2473);
            index = index$iv;
        }
        if (this.sessions.isEmpty()) {
            TextView $this$updateDrawerSessionsList_u24lambda_u2475 = new TextView(this);
            $this$updateDrawerSessionsList_u24lambda_u2475.setText("No sessions");
            $this$updateDrawerSessionsList_u24lambda_u2475.setTextSize(14.0f);
            $this$updateDrawerSessionsList_u24lambda_u2475.setTextColor(-10066330);
            $this$updateDrawerSessionsList_u24lambda_u2475.setPadding(24, 16, 24, 16);
            sessionsList.addView($this$updateDrawerSessionsList_u24lambda_u2475);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateDrawerSessionsList$lambda$74$lambda$73$lambda$71(MainActivity this$0, int $index, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.switchToSession($index);
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.closeDrawers();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean updateDrawerSessionsList$lambda$74$lambda$73$lambda$72(MainActivity this$0, int $index, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showSessionOptionsInDrawer($index);
        return true;
    }

    private final void showSessionOptionsInDrawer(final int index) {
        String[] options = {"Switch to session", "Kill session", "Rename session"};
        new AlertDialog.Builder(this).setTitle("Session " + (index + 1)).setItems(options, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda52
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws ErrnoException {
                MainActivity.showSessionOptionsInDrawer$lambda$76(this.f$0, index, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSessionOptionsInDrawer$lambda$76(MainActivity this$0, int $index, DialogInterface dialogInterface, int which) throws ErrnoException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = null;
        switch (which) {
            case 0:
                this$0.switchToSession($index);
                DrawerLayout drawerLayout2 = this$0.drawerLayout;
                if (drawerLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
                } else {
                    drawerLayout = drawerLayout2;
                }
                drawerLayout.closeDrawers();
                break;
            case 1:
                if ($index == this$0.currentSessionIndex) {
                    this$0.killCurrentSession();
                } else {
                    this$0.sessions.get($index).finishIfRunning();
                    this$0.removeSession($index);
                }
                DrawerLayout drawerLayout3 = this$0.drawerLayout;
                if (drawerLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
                } else {
                    drawerLayout = drawerLayout3;
                }
                drawerLayout.closeDrawers();
                break;
            case 2:
                Toast.makeText(this$0, "Session names coming soon", 0).show();
                break;
        }
    }

    private final void showSessionOptions(final int index) {
        String[] options = {"Switch to session", "Kill session"};
        new AlertDialog.Builder(this).setTitle("Session " + (index + 1)).setItems(options, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda33
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws ErrnoException {
                MainActivity.showSessionOptions$lambda$77(this.f$0, index, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSessionOptions$lambda$77(MainActivity this$0, int $index, DialogInterface dialogInterface, int which) throws ErrnoException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        switch (which) {
            case 0:
                this$0.switchToSession($index);
                break;
            case 1:
                if ($index == this$0.currentSessionIndex) {
                    this$0.killCurrentSession();
                    break;
                } else {
                    this$0.sessions.get($index).finishIfRunning();
                    this$0.removeSession($index);
                    break;
                }
        }
    }

    private final void showSettingsDialog() {
        String[] options = {"Text Size", "Reset Terminal", "Kill Session", "New Session", "Sessions (" + this.sessions.size() + "/" + this.maxSessions + ")", "Clear Session History"};
        new AlertDialog.Builder(this).setTitle("Settings").setItems(options, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda58
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws ErrnoException {
                MainActivity.showSettingsDialog$lambda$79(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSettingsDialog$lambda$79(MainActivity this$0, DialogInterface dialogInterface, int which) throws ErrnoException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        switch (which) {
            case 0:
                this$0.showTextSizeDialog();
                break;
            case 1:
                TerminalSession s = this$0.getSession();
                if (s != null) {
                    s.reset();
                    TerminalView terminalView = this$0.terminalView;
                    if (terminalView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                        terminalView = null;
                    }
                    terminalView.onScreenUpdated();
                    Toast.makeText(this$0, "Terminal reset", 0).show();
                    break;
                }
                break;
            case 2:
                this$0.killCurrentSession();
                break;
            case 3:
                int size = this$0.sessions.size();
                int i = this$0.maxSessions;
                if (size >= i) {
                    Toast.makeText(this$0, "Maximum " + i + " sessions reached", 0).show();
                    break;
                } else {
                    this$0.addNewSession();
                    Toast.makeText(this$0, "Session " + this$0.sessions.size() + " created", 0).show();
                    break;
                }
            case 4:
                this$0.showSessionsDialog();
                break;
            case 5:
                SharedPreferences prefs = this$0.getSharedPreferences("mobilecli", 0);
                prefs.edit().remove("last_transcript").apply();
                Toast.makeText(this$0, "Session history cleared", 0).show();
                break;
        }
    }

    private final void showSessionsDialog() {
        if (this.sessions.isEmpty()) {
            Toast.makeText(this, "No sessions", 0).show();
            return;
        }
        Iterable $this$mapIndexed$iv = this.sessions;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$mapIndexed$iv, 10));
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexed$iv) {
            int index$iv$iv2 = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String marker = index$iv$iv == this.currentSessionIndex ? " (current)" : "";
            destination$iv$iv.add("Session " + (index$iv$iv + 1) + marker);
            index$iv$iv = index$iv$iv2;
        }
        Collection thisCollection$iv = (List) destination$iv$iv;
        String[] sessionNames = (String[]) thisCollection$iv.toArray(new String[0]);
        new AlertDialog.Builder(this).setTitle("Sessions").setItems(sessionNames, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda67
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showSessionsDialog$lambda$81(this.f$0, dialogInterface, i);
            }
        }).setNeutralButton("Kill Current", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda68
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws ErrnoException {
                MainActivity.showSessionsDialog$lambda$82(this.f$0, dialogInterface, i);
            }
        }).setPositiveButton("New", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda69
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showSessionsDialog$lambda$83(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton("Close", (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSessionsDialog$lambda$81(MainActivity this$0, DialogInterface dialogInterface, int which) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.switchToSession(which);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSessionsDialog$lambda$82(MainActivity this$0, DialogInterface dialogInterface, int i) throws ErrnoException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.killCurrentSession();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSessionsDialog$lambda$83(MainActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.sessions.size() < this$0.maxSessions) {
            this$0.addNewSession();
        }
    }

    private final void showHelpDialog() {
        new AlertDialog.Builder(this).setTitle("Help").setMessage("Getting Started:\n• Type commands and press Enter\n• Swipe from left for menu\n• Long-press for copy/paste\n• Pinch to zoom text\n\nUseful Commands:\n• pkg install <package>\n• claude - Start Claude AI\n• ls, cd, cat - File navigation\n• git, npm, node - Dev tools\n\nExtra Keys:\n• CTRL/ALT - Modifier keys (tap to toggle)\n• ESC - Escape key\n• TAB - Tab completion\n• Arrows - Navigation").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    private final void showAboutDialog() {
        new AlertDialog.Builder(this).setTitle("MobileCLI").setMessage("Mobile AI Development Environment\n\nVersion 1.4.0\n\nA full terminal with AI coding assistants.\nClaude, Gemini, and Codex at your fingertips.").setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    private final void sendKey(int keyCode) {
        TerminalSession session = getSession();
        if (session != null) {
            session.write(new byte[]{(byte) keyCode}, 0, 1);
        }
    }

    private final void sendSpecialKey(String sequence) {
        TerminalSession session = getSession();
        if (session != null) {
            byte[] bytes = sequence.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            session.write(bytes, 0, sequence.length());
        }
    }

    private final void sendChar(char c) {
        int code = c;
        if (this.isCtrlPressed && Character.isLetter(c)) {
            code = Character.toUpperCase(c) - '@';
            this.isCtrlPressed = false;
            updateModifierButtons();
        }
        TerminalSession session = getSession();
        if (session != null) {
            session.write(new byte[]{(byte) code}, 0, 1);
        }
    }

    private final TerminalSession createNewTerminalSession() {
        String shell;
        String[] args;
        BootstrapInstaller bootstrapInstaller = this.bootstrapInstaller;
        BootstrapInstaller bootstrapInstaller2 = null;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        String home = bootstrapInstaller.getHomeDir().getAbsolutePath();
        BootstrapInstaller bootstrapInstaller3 = this.bootstrapInstaller;
        if (bootstrapInstaller3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller3 = null;
        }
        bootstrapInstaller3.getHomeDir().mkdirs();
        BootstrapInstaller bootstrapInstaller4 = this.bootstrapInstaller;
        if (bootstrapInstaller4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller4 = null;
        }
        File loginFile = new File(bootstrapInstaller4.getBinDir(), "login");
        BootstrapInstaller bootstrapInstaller5 = this.bootstrapInstaller;
        if (bootstrapInstaller5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller5 = null;
        }
        File bashFile = new File(bootstrapInstaller5.getBashPath());
        BootstrapInstaller bootstrapInstaller6 = this.bootstrapInstaller;
        if (bootstrapInstaller6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller6 = null;
        }
        File shFile = new File(bootstrapInstaller6.getBinDir(), "sh");
        if (loginFile.exists() && loginFile.canExecute()) {
            String shell2 = loginFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(shell2, "getAbsolutePath(...)");
            String[] args2 = new String[0];
            shell = shell2;
            args = args2;
        } else if (bashFile.exists() && bashFile.canExecute()) {
            String shell3 = bashFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(shell3, "getAbsolutePath(...)");
            String[] args3 = {"--login"};
            shell = shell3;
            args = args3;
        } else if (shFile.exists() && shFile.canExecute()) {
            String shell4 = shFile.getAbsolutePath();
            Intrinsics.checkNotNullExpressionValue(shell4, "getAbsolutePath(...)");
            String[] args4 = new String[0];
            shell = shell4;
            args = args4;
        } else {
            String[] args5 = new String[0];
            shell = "/system/bin/sh";
            args = args5;
        }
        BootstrapInstaller bootstrapInstaller7 = this.bootstrapInstaller;
        if (bootstrapInstaller7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
        } else {
            bootstrapInstaller2 = bootstrapInstaller7;
        }
        String[] env = bootstrapInstaller2.getEnvironment();
        try {
            return new TerminalSession(shell, home, args, env, 4000, this);
        } catch (Exception e) {
            Log.e("MobileCLI", "Failed to create session", e);
            return new TerminalSession("/system/bin/sh", home, new String[0], env, 4000, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void createSession() {
        TerminalSession newSession = createNewTerminalSession();
        this.sessions.clear();
        this.sessions.add(newSession);
        this.currentSessionIndex = 0;
        TerminalView terminalView = this.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.attachSession(newSession);
        TerminalView terminalView3 = this.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.createSession$lambda$85(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSession$lambda$85(final MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.requestFocus();
        this$0.updateSessionTabs();
        TerminalView terminalView3 = this$0.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        terminalView2.postDelayed(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.createSession$lambda$85$lambda$84(this.f$0);
            }
        }, 500L);
        this$0.checkAndOfferAISetup();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createSession$lambda$85$lambda$84(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.animate().alpha(1.0f).setDuration(300L).start();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        saveTranscript();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (this.serviceBound) {
            TermuxService service = this.termuxService;
            if (service != null) {
                Iterable $this$forEach$iv = this.sessions;
                for (Object element$iv : $this$forEach$iv) {
                    TerminalSession session = (TerminalSession) element$iv;
                    service.addSession(session);
                }
            }
            unbindService(this.serviceConnection);
            this.serviceBound = false;
        }
        Log.i("MainActivity", "Activity destroyed, sessions preserved in service");
    }

    private final void saveTranscript() {
        TerminalEmulator emulator;
        TerminalBuffer screen;
        try {
            TerminalSession session = getSession();
            String transcript = (session == null || (emulator = session.getEmulator()) == null || (screen = emulator.getScreen()) == null) ? null : screen.getTranscriptText();
            if (transcript == null) {
                return;
            }
            if (transcript.length() > 0) {
                SharedPreferences prefs = getSharedPreferences("mobilecli", 0);
                prefs.edit().putString("last_transcript", transcript).apply();
            }
        } catch (Exception e) {
        }
    }

    private final void restoreTranscript() {
        try {
            final SharedPreferences prefs = getSharedPreferences("mobilecli", 0);
            final String saved = prefs.getString("last_transcript", null);
            String str = saved;
            if (!(str == null || str.length() == 0) && saved.length() > 100) {
                new AlertDialog.Builder(this).setTitle("Previous Session").setMessage("Restore transcript from previous session?").setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda56
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.restoreTranscript$lambda$88(this.f$0, saved, dialogInterface, i);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda57
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.restoreTranscript$lambda$89(prefs, dialogInterface, i);
                    }
                }).show();
            }
        } catch (Exception e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void restoreTranscript$lambda$88(MainActivity this$0, String $saved, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalSession session = this$0.getSession();
        if (session != null) {
            byte[] bytes = "\n# Previous session transcript available\n# Use 'cat ~/.mobilecli/last_session.txt' to view\n".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            session.write(bytes, 0, 80);
        }
        BootstrapInstaller bootstrapInstaller = this$0.bootstrapInstaller;
        if (bootstrapInstaller == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller = null;
        }
        new File(bootstrapInstaller.getHomeDir(), ".mobilecli").mkdirs();
        BootstrapInstaller bootstrapInstaller2 = this$0.bootstrapInstaller;
        if (bootstrapInstaller2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bootstrapInstaller");
            bootstrapInstaller2 = null;
        }
        FilesKt.writeText$default(new File(bootstrapInstaller2.getHomeDir(), ".mobilecli/last_session.txt"), $saved, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void restoreTranscript$lambda$89(SharedPreferences $prefs, DialogInterface dialogInterface, int i) {
        $prefs.edit().remove("last_transcript").apply();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        TerminalView terminalView = this.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.onConfigurationChanged$lambda$90(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onConfigurationChanged$lambda$90(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.invalidate();
    }

    private final void updateTerminalSize() {
        TerminalView terminalView = this.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda75
            @Override // java.lang.Runnable
            public final void run() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
                MainActivity.updateTerminalSize$lambda$91(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateTerminalSize$lambda$91(MainActivity this$0) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        TerminalView terminalView = this$0.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        if (terminalView.getWidth() > 0) {
            TerminalView terminalView3 = this$0.terminalView;
            if (terminalView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                terminalView3 = null;
            }
            if (terminalView3.getHeight() > 0 && this$0.getSession() != null) {
                int fontWidthPx = 0;
                int fontHeightPx = 0;
                try {
                    TerminalView terminalView4 = this$0.terminalView;
                    if (terminalView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                        terminalView4 = null;
                    }
                    Field rendererField = terminalView4.getClass().getDeclaredField("mRenderer");
                    rendererField.setAccessible(true);
                    TerminalView terminalView5 = this$0.terminalView;
                    if (terminalView5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                        terminalView5 = null;
                    }
                    Object renderer = rendererField.get(terminalView5);
                    if (renderer != null) {
                        Field fontWidthField = renderer.getClass().getDeclaredField("mFontWidth");
                        fontWidthField.setAccessible(true);
                        Object obj = fontWidthField.get(renderer);
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Number");
                        fontWidthPx = ((Number) obj).intValue();
                        Field fontHeightField = renderer.getClass().getDeclaredField("mFontLineSpacing");
                        fontHeightField.setAccessible(true);
                        Object obj2 = fontHeightField.get(renderer);
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Number");
                        fontHeightPx = ((Number) obj2).intValue();
                    }
                } catch (Exception e) {
                    Log.w("MobileCLI", "Could not get font metrics: " + e.getMessage());
                }
                if (fontWidthPx <= 0 || fontHeightPx <= 0) {
                    float density = this$0.getResources().getDisplayMetrics().density;
                    fontHeightPx = RangesKt.coerceAtLeast((int) (this$0.currentTextSize * density * 1.2f), 1);
                    fontWidthPx = RangesKt.coerceAtLeast((int) (this$0.currentTextSize * density * 0.6f), 1);
                }
                TerminalView terminalView6 = this$0.terminalView;
                if (terminalView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView6 = null;
                }
                int newColumns = RangesKt.coerceIn(terminalView6.getWidth() / fontWidthPx, 20, 500);
                TerminalView terminalView7 = this$0.terminalView;
                if (terminalView7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView7 = null;
                }
                int newRows = RangesKt.coerceIn(terminalView7.getHeight() / fontHeightPx, 5, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                TerminalSession session = this$0.getSession();
                if (session != null) {
                    session.updateSize(newColumns, newRows);
                }
                TerminalView terminalView8 = this$0.terminalView;
                if (terminalView8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView8 = null;
                }
                int width = terminalView8.getWidth();
                TerminalView terminalView9 = this$0.terminalView;
                if (terminalView9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                } else {
                    terminalView2 = terminalView9;
                }
                Log.i("MobileCLI", "Terminal: " + newColumns + "x" + newRows + ", font: " + fontWidthPx + "x" + fontHeightPx + ", view: " + width + "x" + terminalView2.getHeight());
            }
        }
    }

    @Override // com.termux.view.TerminalViewClient
    public float onScale(float scale) {
        if (scale < 0.9f || scale > 1.1f) {
            int delta = scale > 1.0f ? 2 : -2;
            int newSize = RangesKt.coerceIn(this.currentTextSize + delta, this.minTextSize, this.maxTextSize);
            if (newSize != this.currentTextSize) {
                this.currentTextSize = newSize;
                TerminalView terminalView = this.terminalView;
                TerminalView terminalView2 = null;
                if (terminalView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    terminalView = null;
                }
                terminalView.setTextSize(this.currentTextSize);
                TerminalView terminalView3 = this.terminalView;
                if (terminalView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                } else {
                    terminalView2 = terminalView3;
                }
                terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda45
                    @Override // java.lang.Runnable
                    public final void run() {
                        MainActivity.onScale$lambda$92(this.f$0);
                    }
                });
            }
        }
        return 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onScale$lambda$92(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    @Override // com.termux.view.TerminalViewClient
    public void onSingleTapUp(MotionEvent e) {
        TerminalView terminalView = this.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.requestFocus();
        if (!this.isKeyboardVisible) {
            Object systemService = getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            InputMethodManager imm = (InputMethodManager) systemService;
            TerminalView terminalView3 = this.terminalView;
            if (terminalView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            } else {
                terminalView2 = terminalView3;
            }
            imm.showSoftInput(terminalView2, 1);
        }
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean onLongPress(MotionEvent event) {
        return false;
    }

    private final void showContextMenu(MotionEvent event) {
        String[] options = {"Copy", "Paste", "Select All", "More..."};
        new AlertDialog.Builder(this).setItems(options, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda47
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showContextMenu$lambda$93(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showContextMenu$lambda$93(MainActivity this$0, DialogInterface dialogInterface, int which) {
        TerminalEmulator emulator;
        TerminalBuffer screen;
        TerminalEmulator emulator2;
        TerminalBuffer screen2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean z = true;
        String text = null;
        switch (which) {
            case 0:
                TerminalSession session = this$0.getSession();
                if (session != null && (emulator = session.getEmulator()) != null && (screen = emulator.getScreen()) != null) {
                    text = screen.getTranscriptText();
                }
                String str = text;
                if (str != null && str.length() != 0) {
                    z = false;
                }
                if (!z) {
                    this$0.copyToClipboard(text);
                    Toast.makeText(this$0, "Copied", 0).show();
                    break;
                } else {
                    Toast.makeText(this$0, "Nothing to copy", 0).show();
                    break;
                }
            case 1:
                this$0.pasteFromClipboard();
                break;
            case 2:
                TerminalSession session2 = this$0.getSession();
                if (session2 != null && (emulator2 = session2.getEmulator()) != null && (screen2 = emulator2.getScreen()) != null) {
                    text = screen2.getTranscriptText();
                }
                String str2 = text;
                if (str2 != null && str2.length() != 0) {
                    z = false;
                }
                if (!z) {
                    this$0.copyToClipboard(text);
                    Toast.makeText(this$0, "All text copied", 0).show();
                    break;
                }
                break;
            case 3:
                this$0.showMoreOptions();
                break;
        }
    }

    private final void showMoreOptions() {
        String[] options = {"Copy All", "Paste", "New session", "Kill session", "Reset terminal", "Change text size", "Toggle keyboard", "About"};
        new AlertDialog.Builder(this).setTitle("More Options").setItems(options, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda76
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) throws ErrnoException {
                MainActivity.showMoreOptions$lambda$95(this.f$0, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showMoreOptions$lambda$95(MainActivity this$0, DialogInterface dialogInterface, int i) throws ErrnoException {
        TerminalEmulator emulator;
        TerminalBuffer screen;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String transcriptText = null;
        TerminalView terminalView = null;
        transcriptText = null;
        transcriptText = null;
        switch (i) {
            case 0:
                TerminalSession session = this$0.getSession();
                if (session != null && (emulator = session.getEmulator()) != null && (screen = emulator.getScreen()) != null) {
                    transcriptText = screen.getTranscriptText();
                }
                String str = transcriptText;
                if (!(str == null || str.length() == 0)) {
                    this$0.copyToClipboard(transcriptText);
                    Toast.makeText(this$0, "All text copied to clipboard", 0).show();
                    break;
                } else {
                    Toast.makeText(this$0, "Nothing to copy", 0).show();
                    break;
                }
            case 1:
                this$0.pasteFromClipboard();
                Toast.makeText(this$0, "Pasted", 0).show();
                break;
            case 2:
                int size = this$0.sessions.size();
                int i2 = this$0.maxSessions;
                if (size < i2) {
                    this$0.addNewSession();
                    Toast.makeText(this$0, "Session " + this$0.sessions.size() + " created", 0).show();
                    break;
                } else {
                    Toast.makeText(this$0, "Maximum " + i2 + " sessions reached", 0).show();
                    break;
                }
            case 3:
                this$0.killCurrentSession();
                break;
            case 4:
                TerminalSession session2 = this$0.getSession();
                if (session2 != null) {
                    session2.reset();
                    TerminalView terminalView2 = this$0.terminalView;
                    if (terminalView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("terminalView");
                    } else {
                        terminalView = terminalView2;
                    }
                    terminalView.onScreenUpdated();
                    Toast.makeText(this$0, "Terminal reset", 0).show();
                    break;
                }
                break;
            case 5:
                this$0.showTextSizeDialog();
                break;
            case 6:
                Object systemService = this$0.getSystemService("input_method");
                Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
                ((InputMethodManager) systemService).toggleSoftInput(0, 0);
                break;
            case 7:
                this$0.showAboutDialog();
                break;
        }
    }

    private final void showTextSizeDialog() {
        String[] sizes = {"Small (14)", "Medium (20)", "Large (28)", "X-Large (36)", "XX-Large (48)"};
        final int[] sizeValues = {14, 20, 28, 36, 48};
        new AlertDialog.Builder(this).setTitle("Text Size").setItems(sizes, new DialogInterface.OnClickListener() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda77
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.showTextSizeDialog$lambda$97(this.f$0, sizeValues, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTextSizeDialog$lambda$97(final MainActivity this$0, int[] sizeValues, DialogInterface dialogInterface, int which) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(sizeValues, "$sizeValues");
        this$0.currentTextSize = sizeValues[which];
        TerminalView terminalView = this$0.terminalView;
        TerminalView terminalView2 = null;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.setTextSize(this$0.currentTextSize);
        TerminalView terminalView3 = this$0.terminalView;
        if (terminalView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
        } else {
            terminalView2 = terminalView3;
        }
        terminalView2.post(new Runnable() { // from class: com.termux.MainActivity$$ExternalSyntheticLambda74
            @Override // java.lang.Runnable
            public final void run() {
                MainActivity.showTextSizeDialog$lambda$97$lambda$96(this.f$0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showTextSizeDialog$lambda$97$lambda$96(MainActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateTerminalSize();
        TerminalView terminalView = this$0.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    private final void copyToClipboard(String text) {
        Object systemService = getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboard = (ClipboardManager) systemService;
        clipboard.setPrimaryClip(ClipData.newPlainText("MobileCLI", text));
    }

    private final void pasteFromClipboard() {
        ClipData.Item itemAt;
        CharSequence text;
        Object systemService = getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboard = (ClipboardManager) systemService;
        ClipData primaryClip = clipboard.getPrimaryClip();
        if (primaryClip != null && (itemAt = primaryClip.getItemAt(0)) != null && (text = itemAt.getText()) != null) {
            String textStr = text.toString();
            TerminalSession session = getSession();
            if (session != null) {
                byte[] bytes = textStr.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                byte[] bytes2 = textStr.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
                session.write(bytes, 0, bytes2.length);
            }
        }
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean shouldBackButtonBeMappedToEscape() {
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean shouldEnforceCharBasedInput() {
        return true;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean shouldUseCtrlSpaceWorkaround() {
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean isTerminalViewSelected() {
        return true;
    }

    @Override // com.termux.view.TerminalViewClient
    public void copyModeChanged(boolean copyMode) {
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean onKeyDown(int keyCode, KeyEvent e, TerminalSession session) {
        return false;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback, com.termux.view.TerminalViewClient
    public boolean onKeyUp(int keyCode, KeyEvent e) {
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    /* renamed from: readControlKey, reason: from getter */
    public boolean getIsCtrlPressed() {
        return this.isCtrlPressed;
    }

    @Override // com.termux.view.TerminalViewClient
    /* renamed from: readAltKey, reason: from getter */
    public boolean getIsAltPressed() {
        return this.isAltPressed;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean readShiftKey() {
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean readFnKey() {
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    public boolean onCodePoint(int codePoint, boolean ctrlDown, TerminalSession session) {
        boolean wasCtrl = this.isCtrlPressed;
        boolean wasAlt = this.isAltPressed;
        this.isCtrlPressed = false;
        this.isAltPressed = false;
        if (wasCtrl || wasAlt) {
            updateModifierButtons();
        }
        return false;
    }

    @Override // com.termux.view.TerminalViewClient
    public void onEmulatorSet() {
        TerminalView terminalView = this.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.setTerminalCursorBlinkerRate(500);
        updateTerminalSize();
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logError(String tag, String message) {
        Log.e(tag == null ? "Terminal" : tag, message == null ? "" : message);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logWarn(String tag, String message) {
        Log.w(tag == null ? "Terminal" : tag, message == null ? "" : message);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logInfo(String tag, String message) {
        Log.i(tag == null ? "Terminal" : tag, message == null ? "" : message);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logDebug(String tag, String message) {
        Log.d(tag == null ? "Terminal" : tag, message == null ? "" : message);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logVerbose(String tag, String message) {
        Log.v(tag == null ? "Terminal" : tag, message == null ? "" : message);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logStackTraceWithMessage(String tag, String message, Exception e) {
        Log.e(tag == null ? "Terminal" : tag, message, e);
    }

    @Override // com.termux.view.TerminalViewClient, com.termux.terminal.TerminalSessionClient
    public void logStackTrace(String tag, Exception e) {
        Log.e(tag == null ? "Terminal" : tag, "Error", e);
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onTextChanged(TerminalSession changedSession) {
        TerminalView terminalView = this.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onTitleChanged(TerminalSession changedSession) {
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onSessionFinished(TerminalSession finishedSession) {
        int index = CollectionsKt.indexOf((List<? extends TerminalSession>) this.sessions, finishedSession);
        if (index >= 0) {
            removeSession(index);
            Toast.makeText(this, "Session closed", 0).show();
        } else if (this.sessions.isEmpty()) {
            addNewSession();
        }
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onCopyTextToClipboard(TerminalSession session, String text) {
        if (text != null) {
            copyToClipboard(text);
        }
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onPasteTextFromClipboard(TerminalSession session) {
        pasteFromClipboard();
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onBell(TerminalSession session) {
        try {
            Object systemService = getSystemService("vibrator");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.Vibrator");
            Vibrator vibrator = (Vibrator) systemService;
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(50L, -1));
            } else {
                vibrator.vibrate(50L);
            }
        } catch (Exception e) {
        }
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onColorsChanged(TerminalSession session) {
        TerminalView terminalView = this.terminalView;
        if (terminalView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("terminalView");
            terminalView = null;
        }
        terminalView.onScreenUpdated();
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public void onTerminalCursorStateChange(boolean state) {
    }

    @Override // com.termux.terminal.TerminalSessionClient
    public Integer getTerminalCursorStyle() {
        return 0;
    }
}
