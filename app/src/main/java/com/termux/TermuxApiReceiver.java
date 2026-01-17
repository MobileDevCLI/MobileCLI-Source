package com.termux;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.ConsumerIrManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.security.keystore.KeyGenParameterSpec;
import android.service.notification.StatusBarNotification;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.telephony.CellInfo;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Size;
import android.widget.Toast;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.os.EnvironmentCompat;
import com.termux.terminal.KeyHandler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import kotlinx.coroutines.DebugKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TermuxApiReceiver.kt */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b'\u0018\u0000 V2\u00020\u0001:\u0001VB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0013\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u0019\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010\u001c\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u0018\u0010 \u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010!\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\"\u001a\u00020\nH\u0002J\u0018\u0010#\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010$\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010%\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010&\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010'\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010(\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010)\u001a\u00020\nH\u0002J\u0018\u0010*\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010+\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010,\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010-\u001a\u00020.2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010/\u001a\u000200H\u0016J\u0018\u00101\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00102\u001a\u00020\nH\u0002J\u0018\u00103\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u00104\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u00105\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u00106\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u00107\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u00108\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u00109\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010:\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010;\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010<\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010=\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010>\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010?\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010@\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010A\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010B\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010C\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010D\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0018\u0010E\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010F\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010G\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010H\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010I\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010J\u001a\u00020\nH\u0002J\u0010\u0010K\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010L\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010M\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010N\u001a\u00020\nH\u0002J\u0010\u0010O\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010P\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\nH\u0002J\u0010\u0010Q\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010R\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0018\u0010S\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\nH\u0002J\u0010\u0010T\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010U\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006W"}, d2 = {"Lcom/termux/TermuxApiReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "mediaPlayer", "Landroid/media/MediaPlayer;", "mediaRecorder", "Landroid/media/MediaRecorder;", "tts", "Landroid/speech/tts/TextToSpeech;", "audioInfo", "", "context", "Landroid/content/Context;", "batteryStatus", "callLog", "args", "cameraInfo", "cameraPhoto", "clipboardGet", "clipboardSet", "text", "contactList", "download", "fingerprintAuth", "getBrightness", "getLocation", "getVolume", "infraredFrequencies", "infraredTransmit", "intToIp", "ip", "", "jobScheduler", "keystoreDelete", "alias", "keystoreGenerate", "keystoreList", "keystoreSign", "keystoreVerify", "mediaPlayerControl", "mediaScan", "path", "microphoneRecord", "nfcInfo", "notificationList", "onReceive", "", "intent", "Landroid/content/Intent;", "openUrl", TermuxUrlHandlerActivity.EXTRA_URL, "removeNotification", "safCreate", "safDirs", "safList", "safManageDir", "safMkdir", "safRead", "safRemove", "safStat", "safWrite", "sensorInfo", "setBrightness", "setVolume", "setWallpaper", "share", "showDialog", "showNotification", "showToast", "smsList", "smsSend", "speechToText", "storageGet", "telephonyCall", "number", "telephonyCellInfo", "telephonyDeviceInfo", "toggleTorch", "state", "ttsEngines", "ttsSpeak", "usbInfo", "vibrate", "wifiEnable", "wifiInfo", "wifiScanInfo", "Companion", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class TermuxApiReceiver extends BroadcastReceiver {
    public static final String ACTION_API_CALL = "com.termux.api.API_CALL";
    private static final String CHANNEL_ID = "termux_api_notifications";
    public static final String EXTRA_API_ARGS = "api_args";
    public static final String EXTRA_API_METHOD = "api_method";
    public static final String EXTRA_RESULT_FILE = "result_file";
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private TextToSpeech tts;

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String method;
        String result = "\"}";
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(intent, "intent");
        if (Intrinsics.areEqual(intent.getAction(), ACTION_API_CALL) && (method = intent.getStringExtra(EXTRA_API_METHOD)) != null) {
            String args = intent.getStringExtra(EXTRA_API_ARGS);
            if (args == null) {
                args = "";
            }
            String resultFile = intent.getStringExtra(EXTRA_RESULT_FILE);
            try {
                switch (method.hashCode()) {
                    case -2025364086:
                        if (!method.equals("camera-photo")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = cameraPhoto(context, args);
                            break;
                        }
                    case -1618336704:
                        if (!method.equals("notification-list")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = notificationList(context);
                            break;
                        }
                    case -1553728721:
                        if (!method.equals("volume-set")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = setVolume(context, args);
                            break;
                        }
                    case -1499311977:
                        if (!method.equals("tts-engines")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = ttsEngines(context);
                            break;
                        }
                    case -1375934236:
                        if (!method.equals("fingerprint")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = fingerprintAuth(context);
                            break;
                        }
                    case -1347900182:
                        if (!method.equals("media-player")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = mediaPlayerControl(context, args);
                            break;
                        }
                    case -1332085432:
                        if (!method.equals("dialog")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = showDialog(context, args);
                            break;
                        }
                    case -1198151108:
                        if (!method.equals("infrared-transmit")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = infraredTransmit(context, args);
                            break;
                        }
                    case -909948004:
                        if (!method.equals("saf-ls")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safList(context, args);
                            break;
                        }
                    case -909947824:
                        if (!method.equals("saf-rm")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safRemove(context, args);
                            break;
                        }
                    case -905948230:
                        if (!method.equals("sensor")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = sensorInfo(context, args);
                            break;
                        }
                    case -810883302:
                        if (!method.equals("volume")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = getVolume(context);
                            break;
                        }
                    case -656517339:
                        if (!method.equals("telephony-cellinfo")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = telephonyCellInfo(context);
                            break;
                        }
                    case -526741244:
                        if (!method.equals("storage-get")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = storageGet(context, args);
                            break;
                        }
                    case -505795732:
                        if (!method.equals("open-url")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = openUrl(context, args);
                            break;
                        }
                    case -275510362:
                        if (!method.equals("notification-remove")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = removeNotification(context, args);
                            break;
                        }
                    case -173788331:
                        if (!method.equals("call-log")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = callLog(context, args);
                            break;
                        }
                    case -149388142:
                        if (!method.equals("sms-list")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = smsList(context, args);
                            break;
                        }
                    case -149183620:
                        if (!method.equals("sms-send")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = smsSend(context, args);
                            break;
                        }
                    case -102984124:
                        if (!method.equals("wifi-connectioninfo")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = wifiInfo(context);
                            break;
                        }
                    case -14125397:
                        if (!method.equals("job-scheduler")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = jobScheduler(context, args);
                            break;
                        }
                    case 108971:
                        if (!method.equals("nfc")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = nfcInfo(context);
                            break;
                        }
                    case 116100:
                        if (!method.equals("usb")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = usbInfo(context);
                            break;
                        }
                    case 109400031:
                        if (!method.equals("share")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = share(context, args);
                            break;
                        }
                    case 110532135:
                        if (!method.equals("toast")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = showToast(context, args);
                            break;
                        }
                    case 110547964:
                        if (!method.equals("torch")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = toggleTorch(context, args);
                            break;
                        }
                    case 230499999:
                        if (!method.equals("clipboard-get")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = clipboardGet(context);
                            break;
                        }
                    case 230511531:
                        if (!method.equals("clipboard-set")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = clipboardSet(context, args);
                            break;
                        }
                    case 289856494:
                        if (!method.equals("infrared-frequencies")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = infraredFrequencies(context);
                            break;
                        }
                    case 370913083:
                        if (!method.equals("wifi-enable")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = wifiEnable(context, args);
                            break;
                        }
                    case 451310959:
                        if (!method.equals("vibrate")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = vibrate(context, args);
                            break;
                        }
                    case 570341107:
                        if (!method.equals("telephony-call")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = telephonyCall(context, args);
                            break;
                        }
                    case 595233003:
                        if (!method.equals("notification")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = showNotification(context, args);
                            break;
                        }
                    case 648162385:
                        if (!method.equals("brightness")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = getBrightness(context);
                            break;
                        }
                    case 688618068:
                        if (!method.equals("microphone-record")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = microphoneRecord(context, args);
                            break;
                        }
                    case 922181971:
                        if (!method.equals("saf-managedir")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safManageDir(context, args);
                            break;
                        }
                    case 956930304:
                        if (!method.equals("keystore-generate")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = keystoreGenerate(context, args);
                            break;
                        }
                    case 1050243062:
                        if (!method.equals("keystore-delete")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = keystoreDelete(context, args);
                            break;
                        }
                    case 1182472020:
                        if (!method.equals("speech-to-text")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = speechToText(context);
                            break;
                        }
                    case 1231503915:
                        if (!method.equals("contact-list")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = contactList(context);
                            break;
                        }
                    case 1250020497:
                        if (!method.equals("saf-create")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safCreate(context, args);
                            break;
                        }
                    case 1310831017:
                        if (!method.equals("keystore-list")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = keystoreList(context);
                            break;
                        }
                    case 1311039176:
                        if (!method.equals("keystore-sign")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = keystoreSign(context, args);
                            break;
                        }
                    case 1427818632:
                        if (!method.equals("download")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = download(context, args);
                            break;
                        }
                    case 1472877432:
                        if (!method.equals("tts-speak")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = ttsSpeak(context, args);
                            break;
                        }
                    case 1474694658:
                        if (!method.equals("wallpaper")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = setWallpaper(context, args);
                            break;
                        }
                    case 1502863429:
                        if (!method.equals("audio-info")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = audioInfo(context);
                            break;
                        }
                    case 1565749956:
                        if (!method.equals("keystore-verify")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = keystoreVerify(context, args);
                            break;
                        }
                    case 1573369850:
                        if (!method.equals("saf-mkdir")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safMkdir(context, args);
                            break;
                        }
                    case 1582818730:
                        if (!method.equals("saf-write")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safWrite(context, args);
                            break;
                        }
                    case 1658065842:
                        if (!method.equals("battery-status")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = batteryStatus(context);
                            break;
                        }
                    case 1713052251:
                        if (!method.equals("saf-dirs")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safDirs(context);
                            break;
                        }
                    case 1713464939:
                        if (!method.equals("saf-read")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safRead(context, args);
                            break;
                        }
                    case 1713509161:
                        if (!method.equals("saf-stat")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = safStat(context, args);
                            break;
                        }
                    case 1728094307:
                        if (!method.equals("wifi-scaninfo")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = wifiScanInfo(context);
                            break;
                        }
                    case 1893648070:
                        if (!method.equals("media-scan")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = mediaScan(context, args);
                            break;
                        }
                    case 1901043637:
                        if (!method.equals("location")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = getLocation(context);
                            break;
                        }
                    case 1951211737:
                        if (!method.equals("telephony-deviceinfo")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = telephonyDeviceInfo(context);
                            break;
                        }
                    case 1983368422:
                        if (!method.equals("brightness-set")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = setBrightness(context, args);
                            break;
                        }
                    case 2012672598:
                        if (!method.equals("camera-info")) {
                            result = "{\"error\":\"Unknown API method: " + method + "\"}";
                            break;
                        } else {
                            result = cameraInfo(context);
                            break;
                        }
                    default:
                        result = "{\"error\":\"Unknown API method: " + method + "\"}";
                        break;
                }
            } catch (Exception e) {
                String message = e.getMessage();
                result = "{\"error\":\"" + (message != null ? StringsKt.replace$default(message, "\"", "'", false, 4, (Object) null) : null) + result;
            }
            if (resultFile != null) {
                try {
                    FilesKt.writeText$default(new File(resultFile), result, null, 2, null);
                } catch (Exception e2) {
                }
            }
        }
    }

    private final String clipboardGet(Context context) {
        ClipData.Item itemAt;
        CharSequence text;
        String string;
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboard = (ClipboardManager) systemService;
        ClipData primaryClip = clipboard.getPrimaryClip();
        return (primaryClip == null || (itemAt = primaryClip.getItemAt(0)) == null || (text = itemAt.getText()) == null || (string = text.toString()) == null) ? "" : string;
    }

    private final String clipboardSet(Context context, String text) {
        Object systemService = context.getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ClipboardManager clipboard = (ClipboardManager) systemService;
        clipboard.setPrimaryClip(ClipData.newPlainText("termux-api", text));
        return "";
    }

    private final String showToast(final Context context, final String text) {
        new Handler(context.getMainLooper()).post(new Runnable() { // from class: com.termux.TermuxApiReceiver$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                TermuxApiReceiver.showToast$lambda$1(context, text);
            }
        });
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showToast$lambda$1(Context context, String text) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(text, "$text");
        Toast.makeText(context, text, 1).show();
    }

    private final String showNotification(Context context, String args) {
        Integer intOrNull;
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String title = (String) CollectionsKt.getOrNull(parts, 0);
        if (title == null) {
            title = "MobileCLI";
        }
        String content = (String) CollectionsKt.getOrNull(parts, 1);
        if (content == null) {
            content = "";
        }
        String str = (String) CollectionsKt.getOrNull(parts, 2);
        int id = (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) ? (int) System.currentTimeMillis() : intOrNull.intValue();
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Termux API", 3);
            notificationManager.createNotificationChannel(channel);
        }
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID).setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle(title).setContentText(content).setPriority(0).setAutoCancel(true).build();
        Intrinsics.checkNotNullExpressionValue(notification, "build(...)");
        notificationManager.notify(id, notification);
        return "";
    }

    private final String removeNotification(Context context, String args) {
        Integer intOrNull = StringsKt.toIntOrNull(args);
        if (intOrNull == null) {
            return "{\"error\":\"Invalid notification ID\"}";
        }
        int id = intOrNull.intValue();
        Object systemService = context.getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        NotificationManager notificationManager = (NotificationManager) systemService;
        notificationManager.cancel(id);
        return "";
    }

    private final String batteryStatus(Context context) throws JSONException {
        String statusStr;
        Object systemService = context.getSystemService("batterymanager");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.BatteryManager");
        BatteryManager batteryManager = (BatteryManager) systemService;
        int level = batteryManager.getIntProperty(4);
        int status = batteryManager.getIntProperty(6);
        boolean plugged = batteryManager.isCharging();
        batteryManager.getIntProperty(2);
        switch (status) {
            case 2:
                statusStr = "CHARGING";
                break;
            case 3:
                statusStr = "DISCHARGING";
                break;
            case 4:
                statusStr = "NOT_CHARGING";
                break;
            case 5:
                statusStr = "FULL";
                break;
            default:
                statusStr = "UNKNOWN";
                break;
        }
        JSONObject $this$batteryStatus_u24lambda_u242 = new JSONObject();
        $this$batteryStatus_u24lambda_u242.put("percentage", level);
        $this$batteryStatus_u24lambda_u242.put(NotificationCompat.CATEGORY_STATUS, statusStr);
        $this$batteryStatus_u24lambda_u242.put("plugged", plugged ? "PLUGGED_AC" : "UNPLUGGED");
        $this$batteryStatus_u24lambda_u242.put("health", "GOOD");
        String string = $this$batteryStatus_u24lambda_u242.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final String vibrate(Context context, String args) {
        Vibrator vibrator;
        Integer intOrNull;
        Long longOrNull;
        List parts = StringsKt.split$default((CharSequence) args, new String[]{","}, false, 0, 6, (Object) null);
        boolean z = false;
        String str = (String) CollectionsKt.getOrNull(parts, 0);
        long duration = (str == null || (longOrNull = StringsKt.toLongOrNull(str)) == null) ? 1000L : longOrNull.longValue();
        String str2 = (String) CollectionsKt.getOrNull(parts, 1);
        int force = (str2 == null || (intOrNull = StringsKt.toIntOrNull(str2)) == null) ? -1 : intOrNull.intValue();
        if (Build.VERSION.SDK_INT >= 31) {
            Object systemService = context.getSystemService("vibrator_manager");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.os.VibratorManager");
            vibrator = ((VibratorManager) systemService).getDefaultVibrator();
        } else {
            Object systemService2 = context.getSystemService("vibrator");
            Intrinsics.checkNotNull(systemService2, "null cannot be cast to non-null type android.os.Vibrator");
            vibrator = (Vibrator) systemService2;
        }
        Intrinsics.checkNotNull(vibrator);
        if (Build.VERSION.SDK_INT >= 26) {
            if (1 <= force && force < 256) {
                z = true;
            }
            int amplitude = z ? force : -1;
            vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude));
            return "";
        }
        vibrator.vibrate(duration);
        return "";
    }

    private final String getBrightness(Context context) throws Settings.SettingNotFoundException {
        try {
            int brightness = Settings.System.getInt(context.getContentResolver(), "screen_brightness");
            String string = new JSONObject().put("brightness", brightness).toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String setBrightness(Context context, String args) {
        Integer intOrNull = StringsKt.toIntOrNull(args);
        if (intOrNull == null) {
            return "{\"error\":\"Invalid brightness value\"}";
        }
        int brightness = RangesKt.coerceIn(intOrNull.intValue(), 0, 255);
        try {
            Settings.System.putInt(context.getContentResolver(), "screen_brightness", brightness);
            return "{\"brightness\":" + brightness + "}";
        } catch (Exception e) {
            return "{\"error\":\"Cannot set brightness: " + e.getMessage() + "\"}";
        }
    }

    private final String toggleTorch(Context context, String state) throws CameraAccessException {
        try {
            Object systemService = context.getSystemService("camera");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.camera2.CameraManager");
            CameraManager cameraManager = (CameraManager) systemService;
            String cameraId = cameraManager.getCameraIdList()[0];
            boolean turnOn = Intrinsics.areEqual(state, DebugKt.DEBUG_PROPERTY_VALUE_ON) || Intrinsics.areEqual(state, "1") || Intrinsics.areEqual(state, "true");
            cameraManager.setTorchMode(cameraId, turnOn);
            return "";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String getVolume(Context context) throws JSONException {
        Object systemService = context.getSystemService("audio");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        AudioManager audioManager = (AudioManager) systemService;
        JSONObject $this$getVolume_u24lambda_u249 = new JSONObject();
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u243 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u243.put("volume", audioManager.getStreamVolume(0));
        $this$getVolume_u24lambda_u249_u24lambda_u243.put("max_volume", audioManager.getStreamMaxVolume(0));
        Unit unit = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put(NotificationCompat.CATEGORY_CALL, $this$getVolume_u24lambda_u249_u24lambda_u243);
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u244 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u244.put("volume", audioManager.getStreamVolume(1));
        $this$getVolume_u24lambda_u249_u24lambda_u244.put("max_volume", audioManager.getStreamMaxVolume(1));
        Unit unit2 = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put("system", $this$getVolume_u24lambda_u249_u24lambda_u244);
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u245 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u245.put("volume", audioManager.getStreamVolume(2));
        $this$getVolume_u24lambda_u249_u24lambda_u245.put("max_volume", audioManager.getStreamMaxVolume(2));
        Unit unit3 = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put("ring", $this$getVolume_u24lambda_u249_u24lambda_u245);
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u246 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u246.put("volume", audioManager.getStreamVolume(3));
        $this$getVolume_u24lambda_u249_u24lambda_u246.put("max_volume", audioManager.getStreamMaxVolume(3));
        Unit unit4 = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put("music", $this$getVolume_u24lambda_u249_u24lambda_u246);
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u247 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u247.put("volume", audioManager.getStreamVolume(4));
        $this$getVolume_u24lambda_u249_u24lambda_u247.put("max_volume", audioManager.getStreamMaxVolume(4));
        Unit unit5 = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put(NotificationCompat.CATEGORY_ALARM, $this$getVolume_u24lambda_u249_u24lambda_u247);
        JSONObject $this$getVolume_u24lambda_u249_u24lambda_u248 = new JSONObject();
        $this$getVolume_u24lambda_u249_u24lambda_u248.put("volume", audioManager.getStreamVolume(5));
        $this$getVolume_u24lambda_u249_u24lambda_u248.put("max_volume", audioManager.getStreamMaxVolume(5));
        Unit unit6 = Unit.INSTANCE;
        $this$getVolume_u24lambda_u249.put("notification", $this$getVolume_u24lambda_u249_u24lambda_u248);
        String string = $this$getVolume_u24lambda_u249.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x008d, code lost:
    
        if (r6.equals("system") == false) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String setVolume(android.content.Context r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = r10
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            java.lang.String r1 = ","
            java.lang.String[] r1 = new java.lang.String[]{r1}
            r2 = 0
            r3 = 0
            r4 = 6
            r5 = 0
            java.util.List r0 = kotlin.text.StringsKt.split$default(r0, r1, r2, r3, r4, r5)
            r1 = 0
            java.lang.Object r2 = kotlin.collections.CollectionsKt.getOrNull(r0, r1)
            java.lang.String r2 = (java.lang.String) r2
            if (r2 != 0) goto L1d
            java.lang.String r1 = "{\"error\":\"No stream specified\"}"
            return r1
        L1d:
            r3 = 1
            java.lang.Object r4 = kotlin.collections.CollectionsKt.getOrNull(r0, r3)
            java.lang.String r4 = (java.lang.String) r4
            if (r4 == 0) goto Lb2
            java.lang.Integer r4 = kotlin.text.StringsKt.toIntOrNull(r4)
            if (r4 == 0) goto Lb2
            int r4 = r4.intValue()
            java.lang.String r5 = "audio"
            java.lang.Object r5 = r9.getSystemService(r5)
            java.lang.String r6 = "null cannot be cast to non-null type android.media.AudioManager"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5, r6)
            android.media.AudioManager r5 = (android.media.AudioManager) r5
            java.util.Locale r6 = java.util.Locale.ROOT
            java.lang.String r6 = r2.toLowerCase(r6)
            java.lang.String r7 = "toLowerCase(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r7)
            int r7 = r6.hashCode()
            switch(r7) {
                case -887328209: goto L87;
                case 3045982: goto L7c;
                case 3500592: goto L71;
                case 92895825: goto L66;
                case 104263205: goto L5b;
                case 595233003: goto L50;
                default: goto L4f;
            }
        L4f:
            goto L98
        L50:
            java.lang.String r3 = "notification"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L59
            goto L4f
        L59:
            r3 = 5
            goto L91
        L5b:
            java.lang.String r3 = "music"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L64
            goto L4f
        L64:
            r3 = 3
            goto L91
        L66:
            java.lang.String r3 = "alarm"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L6f
            goto L4f
        L6f:
            r3 = 4
            goto L91
        L71:
            java.lang.String r3 = "ring"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L7a
            goto L4f
        L7a:
            r3 = 2
            goto L91
        L7c:
            java.lang.String r3 = "call"
            boolean r3 = r6.equals(r3)
            if (r3 != 0) goto L85
            goto L4f
        L85:
            r3 = r1
            goto L91
        L87:
            java.lang.String r7 = "system"
            boolean r6 = r6.equals(r7)
            if (r6 != 0) goto L90
            goto L4f
        L90:
        L91:
            r5.setStreamVolume(r3, r4, r1)
            java.lang.String r1 = ""
            return r1
        L98:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "{\"error\":\"Unknown stream: "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r3 = "\"}"
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            return r1
        Lb2:
            java.lang.String r1 = "{\"error\":\"No volume specified\"}"
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.TermuxApiReceiver.setVolume(android.content.Context, java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String wifiInfo(android.content.Context r14) throws org.json.JSONException {
        /*
            r13 = this;
            android.content.Context r0 = r14.getApplicationContext()     // Catch: java.lang.Exception -> La2
            java.lang.String r1 = "wifi"
            java.lang.Object r0 = r0.getSystemService(r1)     // Catch: java.lang.Exception -> La2
            java.lang.String r1 = "null cannot be cast to non-null type android.net.wifi.WifiManager"
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0, r1)     // Catch: java.lang.Exception -> La2
            android.net.wifi.WifiManager r0 = (android.net.wifi.WifiManager) r0     // Catch: java.lang.Exception -> La2
            android.net.wifi.WifiInfo r1 = r0.getConnectionInfo()     // Catch: java.lang.Exception -> La2
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: java.lang.Exception -> La2
            r2.<init>()     // Catch: java.lang.Exception -> La2
            r3 = r2
            r4 = 0
            java.lang.String r5 = "ssid"
            java.lang.String r6 = r1.getSSID()     // Catch: java.lang.Exception -> La2
            java.lang.String r12 = ""
            if (r6 == 0) goto L37
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r7 = "\""
            java.lang.String r8 = ""
            r9 = 0
            r10 = 4
            r11 = 0
            java.lang.String r6 = kotlin.text.StringsKt.replace$default(r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Exception -> La2
            if (r6 != 0) goto L38
        L37:
            r6 = r12
        L38:
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "bssid"
            java.lang.String r6 = r1.getBSSID()     // Catch: java.lang.Exception -> La2
            if (r6 != 0) goto L44
            goto L48
        L44:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)     // Catch: java.lang.Exception -> La2
            r12 = r6
        L48:
            r3.put(r5, r12)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "rssi"
            int r6 = r1.getRssi()     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "link_speed"
            int r6 = r1.getLinkSpeed()     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "link_speed_units"
            java.lang.String r6 = "Mbps"
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "frequency"
            int r6 = r1.getFrequency()     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "frequency_units"
            java.lang.String r6 = "MHz"
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "ip"
            int r6 = r1.getIpAddress()     // Catch: java.lang.Exception -> La2
            java.lang.String r6 = r13.intToIp(r6)     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "network_id"
            int r6 = r1.getNetworkId()     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r5 = "supplicant_state"
            android.net.wifi.SupplicantState r6 = r1.getSupplicantState()     // Catch: java.lang.Exception -> La2
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> La2
            r3.put(r5, r6)     // Catch: java.lang.Exception -> La2
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Exception -> La2
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)     // Catch: java.lang.Exception -> La2
            goto Lc0
        La2:
            r0 = move-exception
            java.lang.String r1 = r0.getMessage()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "{\"error\":\""
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r2 = "\"}"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r1.toString()
        Lc0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.TermuxApiReceiver.wifiInfo(android.content.Context):java.lang.String");
    }

    private final String intToIp(int ip) {
        return (ip & 255) + "." + ((ip >> 8) & 255) + "." + ((ip >> 16) & 255) + "." + ((ip >> 24) & 255);
    }

    private final String wifiEnable(Context context, String args) {
        try {
            Object systemService = context.getApplicationContext().getSystemService("wifi");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
            WifiManager wifiManager = (WifiManager) systemService;
            boolean enable = Intrinsics.areEqual(args, "true") || Intrinsics.areEqual(args, DebugKt.DEBUG_PROPERTY_VALUE_ON) || Intrinsics.areEqual(args, "1");
            wifiManager.setWifiEnabled(enable);
            return "{\"wifi_enabled\":" + enable + "}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String wifiScanInfo(Context context) throws JSONException {
        try {
            Object systemService = context.getApplicationContext().getSystemService("wifi");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.net.wifi.WifiManager");
            WifiManager wifiManager = (WifiManager) systemService;
            Iterable results = wifiManager.getScanResults();
            JSONArray arr = new JSONArray();
            Intrinsics.checkNotNull(results);
            Iterable $this$forEach$iv = results;
            for (Object element$iv : $this$forEach$iv) {
                ScanResult result = (ScanResult) element$iv;
                JSONObject $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411 = new JSONObject();
                $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411.put("ssid", result.SSID);
                $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411.put("bssid", result.BSSID);
                $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411.put("rssi", result.level);
                $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411.put("frequency", result.frequency);
                $this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411.put("capabilities", result.capabilities);
                arr.put($this$wifiScanInfo_u24lambda_u2412_u24lambda_u2411);
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String getLocation(Context context) throws JSONException {
        String string;
        if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            return "{\"error\":\"Location permission not granted\"}";
        }
        try {
            Object systemService = context.getSystemService("location");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.location.LocationManager");
            LocationManager locationManager = (LocationManager) systemService;
            Location location = locationManager.getLastKnownLocation("gps");
            if (location == null) {
                location = locationManager.getLastKnownLocation("network");
            }
            if (location != null) {
                JSONObject $this$getLocation_u24lambda_u2413 = new JSONObject();
                $this$getLocation_u24lambda_u2413.put("latitude", location.getLatitude());
                $this$getLocation_u24lambda_u2413.put("longitude", location.getLongitude());
                $this$getLocation_u24lambda_u2413.put("altitude", location.getAltitude());
                $this$getLocation_u24lambda_u2413.put("accuracy", Float.valueOf(location.getAccuracy()));
                $this$getLocation_u24lambda_u2413.put("bearing", Float.valueOf(location.getBearing()));
                $this$getLocation_u24lambda_u2413.put("speed", Float.valueOf(location.getSpeed()));
                $this$getLocation_u24lambda_u2413.put("provider", location.getProvider());
                string = $this$getLocation_u24lambda_u2413.toString();
            } else {
                string = "{\"error\":\"No location available\"}";
            }
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String cameraInfo(Context context) throws JSONException, CameraAccessException {
        String facingStr;
        Object[] $this$forEach$iv;
        int $i$f$forEach;
        int i;
        try {
            Object systemService = context.getSystemService("camera");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.camera2.CameraManager");
            CameraManager cameraManager = (CameraManager) systemService;
            JSONArray arr = new JSONArray();
            Object[] cameraIdList = cameraManager.getCameraIdList();
            Intrinsics.checkNotNullExpressionValue(cameraIdList, "getCameraIdList(...)");
            Object[] $this$forEach$iv2 = cameraIdList;
            int $i$f$forEach2 = 0;
            int length = $this$forEach$iv2.length;
            int i2 = 0;
            while (i2 < length) {
                Object element$iv = $this$forEach$iv2[i2];
                String cameraId = (String) element$iv;
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
                Intrinsics.checkNotNullExpressionValue(characteristics, "getCameraCharacteristics(...)");
                Integer facing = (Integer) characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing.intValue() == 0) {
                    facingStr = "front";
                } else {
                    facingStr = (facing != null && facing.intValue() == 1) ? "back" : "external";
                }
                JSONObject $this$cameraInfo_u24lambda_u2418_u24lambda_u2417 = new JSONObject();
                CameraManager cameraManager2 = cameraManager;
                $this$cameraInfo_u24lambda_u2418_u24lambda_u2417.put("id", cameraId);
                $this$cameraInfo_u24lambda_u2418_u24lambda_u2417.put("facing", facingStr);
                Size it = (Size) characteristics.get(CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE);
                if (it != null) {
                    $this$forEach$iv = $this$forEach$iv2;
                    JSONArray jSONArray = new JSONArray();
                    $i$f$forEach = $i$f$forEach2;
                    JSONObject $this$cameraInfo_u24lambda_u2418_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414 = new JSONObject();
                    i = length;
                    $this$cameraInfo_u24lambda_u2418_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414.put("width", it.getWidth());
                    $this$cameraInfo_u24lambda_u2418_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414.put("height", it.getHeight());
                    Unit unit = Unit.INSTANCE;
                    $this$cameraInfo_u24lambda_u2418_u24lambda_u2417.put("jpeg_output_sizes", jSONArray.put($this$cameraInfo_u24lambda_u2418_u24lambda_u2417_u24lambda_u2415_u24lambda_u2414));
                } else {
                    $this$forEach$iv = $this$forEach$iv2;
                    $i$f$forEach = $i$f$forEach2;
                    i = length;
                }
                Boolean it2 = (Boolean) characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (it2 != null) {
                    Intrinsics.checkNotNull(it2);
                    $this$cameraInfo_u24lambda_u2418_u24lambda_u2417.put("flash_available", it2.booleanValue());
                }
                arr.put($this$cameraInfo_u24lambda_u2418_u24lambda_u2417);
                i2++;
                cameraManager = cameraManager2;
                $this$forEach$iv2 = $this$forEach$iv;
                $i$f$forEach2 = $i$f$forEach;
                length = i;
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String cameraPhoto(Context context, String args) {
        return "{\"error\":\"Use 'am start -a android.media.action.IMAGE_CAPTURE' for camera\"}";
    }

    private final String mediaScan(Context context, String path) {
        String str = "\"}";
        try {
            File file = new File(path);
            if (!file.exists()) {
                str = "{\"error\":\"File not found: " + path + "\"}";
            } else {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(file));
                context.sendBroadcast(intent);
                str = "";
            }
            return str;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + str;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final String mediaPlayerControl(Context context, String args) throws IllegalStateException, JSONException, IOException, SecurityException, IllegalArgumentException {
        String string;
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String action = (String) CollectionsKt.getOrNull(parts, 0);
        if (action == null) {
            return "{\"error\":\"No action specified\"}";
        }
        String file = (String) CollectionsKt.getOrNull(parts, 1);
        if (file == null) {
            file = "";
        }
        try {
            switch (action.hashCode()) {
                case -934426579:
                    if (!action.equals("resume")) {
                        break;
                    } else {
                        MediaPlayer mediaPlayer = this.mediaPlayer;
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }
                        return "{\"status\":\"playing\"}";
                    }
                case 3237038:
                    if (!action.equals("info")) {
                        break;
                    } else {
                        MediaPlayer mp = this.mediaPlayer;
                        if (mp != null) {
                            JSONObject $this$mediaPlayerControl_u24lambda_u2420 = new JSONObject();
                            $this$mediaPlayerControl_u24lambda_u2420.put("playing", mp.isPlaying());
                            $this$mediaPlayerControl_u24lambda_u2420.put("position", mp.getCurrentPosition());
                            $this$mediaPlayerControl_u24lambda_u2420.put(TypedValues.TransitionType.S_DURATION, mp.getDuration());
                            string = $this$mediaPlayerControl_u24lambda_u2420.toString();
                        } else {
                            string = "{\"status\":\"no_media\"}";
                        }
                        Intrinsics.checkNotNull(string);
                        return string;
                    }
                case 3443508:
                    if (!action.equals("play")) {
                        break;
                    } else {
                        MediaPlayer mediaPlayer2 = this.mediaPlayer;
                        if (mediaPlayer2 != null) {
                            mediaPlayer2.release();
                        }
                        MediaPlayer $this$mediaPlayerControl_u24lambda_u2419 = new MediaPlayer();
                        $this$mediaPlayerControl_u24lambda_u2419.setDataSource(file);
                        $this$mediaPlayerControl_u24lambda_u2419.prepare();
                        $this$mediaPlayerControl_u24lambda_u2419.start();
                        this.mediaPlayer = $this$mediaPlayerControl_u24lambda_u2419;
                        return "{\"status\":\"playing\",\"file\":\"" + file + "\"}";
                    }
                case 3540994:
                    if (!action.equals("stop")) {
                        break;
                    } else {
                        MediaPlayer mediaPlayer3 = this.mediaPlayer;
                        if (mediaPlayer3 != null) {
                            mediaPlayer3.stop();
                        }
                        MediaPlayer mediaPlayer4 = this.mediaPlayer;
                        if (mediaPlayer4 != null) {
                            mediaPlayer4.release();
                        }
                        this.mediaPlayer = null;
                        return "{\"status\":\"stopped\"}";
                    }
                case 106440182:
                    if (!action.equals("pause")) {
                        break;
                    } else {
                        MediaPlayer mediaPlayer5 = this.mediaPlayer;
                        if (mediaPlayer5 != null) {
                            mediaPlayer5.pause();
                        }
                        return "{\"status\":\"paused\"}";
                    }
            }
            return "{\"error\":\"Unknown action: " + action + "\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String microphoneRecord(Context context, String args) throws IllegalStateException, IOException {
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String action = (String) CollectionsKt.getOrNull(parts, 0);
        if (action == null) {
            return "{\"error\":\"No action specified\"}";
        }
        String file = (String) CollectionsKt.getOrNull(parts, 1);
        if (file == null) {
            file = "";
        }
        try {
            if (Intrinsics.areEqual(action, "start")) {
                if (ContextCompat.checkSelfPermission(context, "android.permission.RECORD_AUDIO") != 0) {
                    return "{\"error\":\"Recording permission not granted\"}";
                }
                MediaRecorder mediaRecorder = this.mediaRecorder;
                if (mediaRecorder != null) {
                    mediaRecorder.release();
                }
                MediaRecorder $this$microphoneRecord_u24lambda_u2421 = new MediaRecorder();
                $this$microphoneRecord_u24lambda_u2421.setAudioSource(1);
                $this$microphoneRecord_u24lambda_u2421.setOutputFormat(2);
                $this$microphoneRecord_u24lambda_u2421.setAudioEncoder(3);
                $this$microphoneRecord_u24lambda_u2421.setOutputFile(file);
                $this$microphoneRecord_u24lambda_u2421.prepare();
                $this$microphoneRecord_u24lambda_u2421.start();
                this.mediaRecorder = $this$microphoneRecord_u24lambda_u2421;
                return "{\"status\":\"recording\",\"file\":\"" + file + "\"}";
            }
            if (!Intrinsics.areEqual(action, "stop")) {
                return "{\"error\":\"Unknown action: " + action + "\"}";
            }
            MediaRecorder mediaRecorder2 = this.mediaRecorder;
            if (mediaRecorder2 != null) {
                mediaRecorder2.stop();
            }
            MediaRecorder mediaRecorder3 = this.mediaRecorder;
            if (mediaRecorder3 != null) {
                mediaRecorder3.release();
            }
            this.mediaRecorder = null;
            return "{\"status\":\"stopped\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String audioInfo(Context context) throws JSONException {
        String str;
        Object systemService = context.getSystemService("audio");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
        AudioManager audioManager = (AudioManager) systemService;
        JSONObject $this$audioInfo_u24lambda_u2422 = new JSONObject();
        switch (audioManager.getRingerMode()) {
            case 0:
                str = "SILENT";
                break;
            case 1:
                str = "VIBRATE";
                break;
            default:
                str = "NORMAL";
                break;
        }
        $this$audioInfo_u24lambda_u2422.put("ringer_mode", str);
        $this$audioInfo_u24lambda_u2422.put("music_active", audioManager.isMusicActive());
        $this$audioInfo_u24lambda_u2422.put("speaker_on", audioManager.isSpeakerphoneOn());
        $this$audioInfo_u24lambda_u2422.put("bluetooth_sco_on", audioManager.isBluetoothScoOn());
        String string = $this$audioInfo_u24lambda_u2422.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final String ttsEngines(Context context) throws JSONException {
        try {
            TextToSpeech tts = new TextToSpeech(context, null);
            Iterable engines = tts.getEngines();
            JSONArray arr = new JSONArray();
            Intrinsics.checkNotNull(engines);
            Iterable $this$forEach$iv = engines;
            for (Object element$iv : $this$forEach$iv) {
                TextToSpeech.EngineInfo engine = (TextToSpeech.EngineInfo) element$iv;
                JSONObject $this$ttsEngines_u24lambda_u2424_u24lambda_u2423 = new JSONObject();
                $this$ttsEngines_u24lambda_u2424_u24lambda_u2423.put("name", engine.name);
                $this$ttsEngines_u24lambda_u2424_u24lambda_u2423.put("label", engine.label);
                arr.put($this$ttsEngines_u24lambda_u2424_u24lambda_u2423);
            }
            tts.shutdown();
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String ttsSpeak(Context context, String text) throws InterruptedException {
        try {
            TextToSpeech tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() { // from class: com.termux.TermuxApiReceiver$$ExternalSyntheticLambda0
                @Override // android.speech.tts.TextToSpeech.OnInitListener
                public final void onInit(int i) {
                    TermuxApiReceiver.ttsSpeak$lambda$25(i);
                }
            });
            Thread.sleep(500L);
            tts.speak(text, 0, null, "termux-tts");
            return "{\"status\":\"speaking\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void ttsSpeak$lambda$25(int status) {
    }

    private final String telephonyCall(Context context, String number) {
        try {
            Intent intent = new Intent("android.intent.action.CALL");
            intent.setData(Uri.parse("tel:" + number));
            intent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            context.startActivity(intent);
            return "{\"status\":\"calling\",\"number\":\"" + number + "\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String telephonyCellInfo(Context context) throws JSONException {
        if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE") != 0) {
            return "{\"error\":\"Phone permission not granted\"}";
        }
        try {
            Object systemService = context.getSystemService("phone");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager");
            TelephonyManager telephonyManager = (TelephonyManager) systemService;
            Iterable cellInfoList = telephonyManager.getAllCellInfo();
            JSONArray arr = new JSONArray();
            if (cellInfoList != null) {
                Iterable $this$forEach$iv = cellInfoList;
                for (Object element$iv : $this$forEach$iv) {
                    CellInfo cellInfo = (CellInfo) element$iv;
                    JSONObject $this$telephonyCellInfo_u24lambda_u2427_u24lambda_u2426 = new JSONObject();
                    $this$telephonyCellInfo_u24lambda_u2427_u24lambda_u2426.put("type", cellInfo.getClass().getSimpleName());
                    $this$telephonyCellInfo_u24lambda_u2427_u24lambda_u2426.put("registered", cellInfo.isRegistered());
                    arr.put($this$telephonyCellInfo_u24lambda_u2427_u24lambda_u2426);
                }
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String telephonyDeviceInfo(Context context) throws JSONException {
        String str;
        String str2;
        try {
            Object systemService = context.getSystemService("phone");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager");
            TelephonyManager telephonyManager = (TelephonyManager) systemService;
            JSONObject $this$telephonyDeviceInfo_u24lambda_u2428 = new JSONObject();
            String deviceSoftwareVersion = telephonyManager.getDeviceSoftwareVersion();
            if (deviceSoftwareVersion == null) {
                deviceSoftwareVersion = EnvironmentCompat.MEDIA_UNKNOWN;
            }
            $this$telephonyDeviceInfo_u24lambda_u2428.put("device_software_version", deviceSoftwareVersion);
            $this$telephonyDeviceInfo_u24lambda_u2428.put("network_country_iso", telephonyManager.getNetworkCountryIso());
            $this$telephonyDeviceInfo_u24lambda_u2428.put("network_operator", telephonyManager.getNetworkOperator());
            $this$telephonyDeviceInfo_u24lambda_u2428.put("network_operator_name", telephonyManager.getNetworkOperatorName());
            $this$telephonyDeviceInfo_u24lambda_u2428.put("network_type", telephonyManager.getNetworkType());
            switch (telephonyManager.getPhoneType()) {
                case 1:
                    str = "GSM";
                    break;
                case 2:
                    str = "CDMA";
                    break;
                case 3:
                    str = "SIP";
                    break;
                default:
                    str = "NONE";
                    break;
            }
            $this$telephonyDeviceInfo_u24lambda_u2428.put("phone_type", str);
            $this$telephonyDeviceInfo_u24lambda_u2428.put("sim_country_iso", telephonyManager.getSimCountryIso());
            $this$telephonyDeviceInfo_u24lambda_u2428.put("sim_operator", telephonyManager.getSimOperator());
            $this$telephonyDeviceInfo_u24lambda_u2428.put("sim_operator_name", telephonyManager.getSimOperatorName());
            switch (telephonyManager.getSimState()) {
                case 1:
                    str2 = "ABSENT";
                    break;
                case 2:
                    str2 = "PIN_REQUIRED";
                    break;
                case 3:
                    str2 = "PUK_REQUIRED";
                    break;
                case 4:
                    str2 = "NETWORK_LOCKED";
                    break;
                case 5:
                    str2 = "READY";
                    break;
                default:
                    str2 = "UNKNOWN";
                    break;
            }
            $this$telephonyDeviceInfo_u24lambda_u2428.put("sim_state", str2);
            String string = $this$telephonyDeviceInfo_u24lambda_u2428.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final java.lang.String smsList(android.content.Context r23, java.lang.String r24) {
        /*
            Method dump skipped, instructions count: 366
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.termux.TermuxApiReceiver.smsList(android.content.Context, java.lang.String):java.lang.String");
    }

    private final String smsSend(Context context, String args) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.SEND_SMS") != 0) {
            return "{\"error\":\"SMS permission not granted\"}";
        }
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 2, 2, (Object) null);
        String number = (String) CollectionsKt.getOrNull(parts, 0);
        if (number == null) {
            return "{\"error\":\"No number specified\"}";
        }
        String message = (String) CollectionsKt.getOrNull(parts, 1);
        if (message == null) {
            return "{\"error\":\"No message specified\"}";
        }
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            return "{\"status\":\"sent\",\"to\":\"" + number + "\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String contactList(Context context) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.READ_CONTACTS") != 0) {
            return "{\"error\":\"Contacts permission not granted\"}";
        }
        try {
            JSONArray arr = new JSONArray();
            Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"display_name", "data1"}, null, null, "display_name ASC");
            if (cursor != null) {
                Cursor cursor2 = cursor;
                try {
                    Cursor it = cursor2;
                    while (it.moveToNext()) {
                        JSONObject $this$contactList_u24lambda_u2432_u24lambda_u2431 = new JSONObject();
                        $this$contactList_u24lambda_u2432_u24lambda_u2431.put("name", it.getString(0));
                        $this$contactList_u24lambda_u2432_u24lambda_u2431.put("number", it.getString(1));
                        arr.put($this$contactList_u24lambda_u2432_u24lambda_u2431);
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor2, null);
                } finally {
                }
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String callLog(Context context, String args) {
        JSONArray arr;
        Cursor cursor;
        Throwable th;
        String str;
        String str2 = "number";
        String str3 = "name";
        if (ContextCompat.checkSelfPermission(context, "android.permission.READ_CALL_LOG") != 0) {
            return "{\"error\":\"Call log permission not granted\"}";
        }
        Integer intOrNull = StringsKt.toIntOrNull(args);
        int limit = intOrNull != null ? intOrNull.intValue() : 10;
        try {
            arr = new JSONArray();
            cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, "date DESC");
        } catch (Exception e) {
            e = e;
        }
        try {
            if (cursor != null) {
                Cursor cursor2 = cursor;
                try {
                    Cursor it = cursor2;
                    int count = 0;
                    while (it.moveToNext() && count < limit) {
                        int type = it.getInt(it.getColumnIndexOrThrow("type"));
                        JSONObject $this$callLog_u24lambda_u2434_u24lambda_u2433 = new JSONObject();
                        int limit2 = limit;
                        try {
                            String string = it.getString(it.getColumnIndexOrThrow(str3));
                            if (string == null) {
                                string = "";
                            } else {
                                Intrinsics.checkNotNull(string);
                            }
                            $this$callLog_u24lambda_u2434_u24lambda_u2433.put(str3, string);
                            $this$callLog_u24lambda_u2434_u24lambda_u2433.put(str2, it.getString(it.getColumnIndexOrThrow(str2)));
                            switch (type) {
                                case 1:
                                    str = "INCOMING";
                                    break;
                                case 2:
                                    str = "OUTGOING";
                                    break;
                                case 3:
                                    str = "MISSED";
                                    break;
                                case 4:
                                default:
                                    str = "UNKNOWN";
                                    break;
                                case 5:
                                    str = "REJECTED";
                                    break;
                            }
                            $this$callLog_u24lambda_u2434_u24lambda_u2433.put("type", str);
                            $this$callLog_u24lambda_u2434_u24lambda_u2433.put("date", it.getLong(it.getColumnIndexOrThrow("date")));
                            $this$callLog_u24lambda_u2434_u24lambda_u2433.put(TypedValues.TransitionType.S_DURATION, it.getLong(it.getColumnIndexOrThrow(TypedValues.TransitionType.S_DURATION)));
                            arr.put($this$callLog_u24lambda_u2434_u24lambda_u2433);
                            count++;
                            limit = limit2;
                            str2 = str2;
                            str3 = str3;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(cursor2, th);
                                throw th3;
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor2, null);
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            String string2 = arr.toString();
            Intrinsics.checkNotNull(string2);
            return string2;
        } catch (Exception e2) {
            e = e2;
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String sensorInfo(Context context, String args) throws JSONException {
        String string;
        Object systemService = context.getSystemService("sensor");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.SensorManager");
        SensorManager sensorManager = (SensorManager) systemService;
        if ((args.length() == 0) || Intrinsics.areEqual(args, "list")) {
            Iterable sensors = sensorManager.getSensorList(-1);
            JSONArray arr = new JSONArray();
            Intrinsics.checkNotNull(sensors);
            Iterable $this$forEach$iv = sensors;
            for (Object element$iv : $this$forEach$iv) {
                Sensor sensor = (Sensor) element$iv;
                JSONObject $this$sensorInfo_u24lambda_u2436_u24lambda_u2435 = new JSONObject();
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("name", sensor.getName());
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("type", sensor.getType());
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("vendor", sensor.getVendor());
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("version", sensor.getVersion());
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("resolution", Float.valueOf(sensor.getResolution()));
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("max_range", Float.valueOf(sensor.getMaximumRange()));
                $this$sensorInfo_u24lambda_u2436_u24lambda_u2435.put("power", Float.valueOf(sensor.getPower()));
                arr.put($this$sensorInfo_u24lambda_u2436_u24lambda_u2435);
                sensorManager = sensorManager;
            }
            String string2 = arr.toString();
            Intrinsics.checkNotNull(string2);
            return string2;
        }
        Integer intOrNull = StringsKt.toIntOrNull(args);
        int sensorType = intOrNull != null ? intOrNull.intValue() : 1;
        Sensor sensor2 = sensorManager.getDefaultSensor(sensorType);
        if (sensor2 != null) {
            JSONObject $this$sensorInfo_u24lambda_u2437 = new JSONObject();
            $this$sensorInfo_u24lambda_u2437.put("name", sensor2.getName());
            $this$sensorInfo_u24lambda_u2437.put("type", sensor2.getType());
            $this$sensorInfo_u24lambda_u2437.put("vendor", sensor2.getVendor());
            string = $this$sensorInfo_u24lambda_u2437.toString();
        } else {
            string = "{\"error\":\"Sensor not found\"}";
        }
        Intrinsics.checkNotNull(string);
        return string;
    }

    private final String fingerprintAuth(Context context) {
        try {
            Object systemService = context.getSystemService("fingerprint");
            FingerprintManager fingerprintManager = systemService instanceof FingerprintManager ? (FingerprintManager) systemService : null;
            if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                return "{\"error\":\"NO_HARDWARE\"}";
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                return "{\"error\":\"NONE_ENROLLED\"}";
            }
            return "{\"auth_result\":\"AUTH_AVAILABLE\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private final String infraredFrequencies(Context context) throws JSONException {
        try {
            Object systemService = context.getSystemService("consumer_ir");
            ConsumerIrManager irManager = systemService instanceof ConsumerIrManager ? (ConsumerIrManager) systemService : null;
            if (irManager != null && irManager.hasIrEmitter()) {
                ConsumerIrManager.CarrierFrequencyRange[] ranges = irManager.getCarrierFrequencies();
                JSONArray arr = new JSONArray();
                Intrinsics.checkNotNull(ranges);
                for (ConsumerIrManager.CarrierFrequencyRange carrierFrequencyRange : ranges) {
                    JSONObject $this$infraredFrequencies_u24lambda_u2439_u24lambda_u2438 = new JSONObject();
                    $this$infraredFrequencies_u24lambda_u2439_u24lambda_u2438.put("min", carrierFrequencyRange.getMinFrequency());
                    $this$infraredFrequencies_u24lambda_u2439_u24lambda_u2438.put("max", carrierFrequencyRange.getMaxFrequency());
                    arr.put($this$infraredFrequencies_u24lambda_u2439_u24lambda_u2438);
                }
                String string = arr.toString();
                Intrinsics.checkNotNull(string);
                return string;
            }
            return "{\"error\":\"No IR emitter available\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private final String infraredTransmit(Context context, String args) {
        Integer intOrNull;
        try {
            Object systemService = context.getSystemService("consumer_ir");
            ConsumerIrManager irManager = systemService instanceof ConsumerIrManager ? (ConsumerIrManager) systemService : null;
            if (irManager != null && irManager.hasIrEmitter()) {
                List parts = StringsKt.split$default((CharSequence) args, new String[]{","}, false, 0, 6, (Object) null);
                String str = (String) CollectionsKt.getOrNull(parts, 0);
                if (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) {
                    return "{\"error\":\"No frequency specified\"}";
                }
                int frequency = intOrNull.intValue();
                Iterable $this$mapNotNull$iv = CollectionsKt.drop(parts, 1);
                Collection destination$iv$iv = new ArrayList();
                for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
                    String it = (String) element$iv$iv$iv;
                    Integer intOrNull2 = StringsKt.toIntOrNull(it);
                    if (intOrNull2 != null) {
                        destination$iv$iv.add(intOrNull2);
                    }
                }
                int[] pattern = CollectionsKt.toIntArray((List) destination$iv$iv);
                if (pattern.length == 0) {
                    return "{\"error\":\"No pattern specified\"}";
                }
                irManager.transmit(frequency, pattern);
                return "{\"status\":\"transmitted\"}";
            }
            return "{\"error\":\"No IR emitter available\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private final String usbInfo(Context context) throws JSONException {
        try {
            Object systemService = context.getSystemService("usb");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.hardware.usb.UsbManager");
            UsbManager usbManager = (UsbManager) systemService;
            Map devices = usbManager.getDeviceList();
            JSONArray arr = new JSONArray();
            Intrinsics.checkNotNull(devices);
            Map $this$forEach$iv = devices;
            for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
                String name = element$iv.getKey();
                UsbDevice device = element$iv.getValue();
                JSONObject $this$usbInfo_u24lambda_u2442_u24lambda_u2441 = new JSONObject();
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("device_name", name);
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("vendor_id", device.getVendorId());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("product_id", device.getProductId());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("device_class", device.getDeviceClass());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("device_subclass", device.getDeviceSubclass());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("device_protocol", device.getDeviceProtocol());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("manufacturer_name", device.getManufacturerName());
                $this$usbInfo_u24lambda_u2442_u24lambda_u2441.put("product_name", device.getProductName());
                arr.put($this$usbInfo_u24lambda_u2442_u24lambda_u2441);
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String setWallpaper(Context context, String args) throws IOException {
        try {
            File file = new File(args);
            if (!file.exists()) {
                return "{\"error\":\"File not found: " + args + "\"}";
            }
            Bitmap bitmap = BitmapFactory.decodeFile(args);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setBitmap(bitmap);
            return "{\"status\":\"wallpaper_set\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String download(Context context, String args) {
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String url = (String) CollectionsKt.getOrNull(parts, 0);
        if (url == null) {
            return "{\"error\":\"No URL specified\"}";
        }
        String title = (String) CollectionsKt.getOrNull(parts, 1);
        if (title == null) {
            title = "Download";
        }
        String description = (String) CollectionsKt.getOrNull(parts, 2);
        if (description == null) {
            description = "";
        }
        try {
            DownloadManager.Request $this$download_u24lambda_u2443 = new DownloadManager.Request(Uri.parse(url));
            $this$download_u24lambda_u2443.setTitle(title);
            $this$download_u24lambda_u2443.setDescription(description);
            $this$download_u24lambda_u2443.setNotificationVisibility(1);
            $this$download_u24lambda_u2443.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Uri.parse(url).getLastPathSegment());
            Object systemService = context.getSystemService("download");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.DownloadManager");
            DownloadManager downloadManager = (DownloadManager) systemService;
            long downloadId = downloadManager.enqueue($this$download_u24lambda_u2443);
            return "{\"download_id\":" + downloadId + "}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String share(Context context, String args) {
        Intent $this$share_u24lambda_u2444;
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 2, 2, (Object) null);
        String action = (String) CollectionsKt.getOrNull(parts, 0);
        if (action == null) {
            action = "text";
        }
        String content = (String) CollectionsKt.getOrNull(parts, 1);
        if (content == null) {
            content = "";
        }
        try {
            if (Intrinsics.areEqual(action, "text")) {
                $this$share_u24lambda_u2444 = new Intent("android.intent.action.SEND");
                $this$share_u24lambda_u2444.setType("text/plain");
                $this$share_u24lambda_u2444.putExtra("android.intent.extra.TEXT", content);
            } else {
                if (!Intrinsics.areEqual(action, "file")) {
                    return "{\"error\":\"Unknown share type: " + action + "\"}";
                }
                $this$share_u24lambda_u2444 = new Intent("android.intent.action.SEND");
                File file = new File(content);
                Uri uri = Uri.fromFile(file);
                $this$share_u24lambda_u2444.setType("*/*");
                $this$share_u24lambda_u2444.putExtra("android.intent.extra.STREAM", uri);
                $this$share_u24lambda_u2444.addFlags(1);
            }
            $this$share_u24lambda_u2444.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            context.startActivity(Intent.createChooser($this$share_u24lambda_u2444, "Share via").addFlags(KeyHandler.KEYMOD_NUM_LOCK));
            return "{\"status\":\"shared\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String showDialog(final Context context, final String args) {
        new Handler(context.getMainLooper()).post(new Runnable() { // from class: com.termux.TermuxApiReceiver$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                TermuxApiReceiver.showDialog$lambda$46(context, args);
            }
        });
        return "{\"input\":\"" + args + "\"}";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDialog$lambda$46(Context context, String args) {
        Intrinsics.checkNotNullParameter(context, "$context");
        Intrinsics.checkNotNullParameter(args, "$args");
        Toast.makeText(context, "Dialog: " + args, 1).show();
    }

    private final String storageGet(Context context, String args) {
        return "{\"error\":\"Use 'termux-setup-storage' to access storage\"}";
    }

    private final String jobScheduler(Context context, String args) {
        return "{\"error\":\"Job scheduler not implemented - use cron or at command\"}";
    }

    private final String openUrl(Context context, String url) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            intent.addFlags(KeyHandler.KEYMOD_NUM_LOCK);
            context.startActivity(intent);
            return "";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String keystoreList(Context context) throws JSONException, NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Enumeration aliases = keyStore.aliases();
            JSONArray arr = new JSONArray();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                JSONObject $this$keystoreList_u24lambda_u2447 = new JSONObject();
                $this$keystoreList_u24lambda_u2447.put("alias", alias);
                $this$keystoreList_u24lambda_u2447.put("is_key_entry", keyStore.isKeyEntry(alias));
                $this$keystoreList_u24lambda_u2447.put("is_certificate_entry", keyStore.isCertificateEntry(alias));
                Certificate cert = keyStore.getCertificate(alias);
                if (cert != null) {
                    $this$keystoreList_u24lambda_u2447.put("type", cert.getType());
                }
                arr.put($this$keystoreList_u24lambda_u2447);
            }
            String alias2 = arr.toString();
            Intrinsics.checkNotNull(alias2);
            return alias2;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String keystoreGenerate(Context context, String args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Integer intOrNull;
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String alias = (String) CollectionsKt.getOrNull(parts, 0);
        if (alias == null) {
            return "{\"error\":\"No alias specified\"}";
        }
        String algorithm = (String) CollectionsKt.getOrNull(parts, 1);
        if (algorithm == null) {
            algorithm = "AES";
        }
        String str = (String) CollectionsKt.getOrNull(parts, 2);
        int keySize = (str == null || (intOrNull = StringsKt.toIntOrNull(str)) == null) ? 256 : intOrNull.intValue();
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            KeyGenParameterSpec parameterSpec = new KeyGenParameterSpec.Builder(alias, 15).setBlockModes("CBC", "GCM").setEncryptionPaddings("PKCS7Padding").setKeySize(keySize).setUserAuthenticationRequired(false).build();
            Intrinsics.checkNotNullExpressionValue(parameterSpec, "build(...)");
            keyGenerator.init(parameterSpec);
            keyGenerator.generateKey();
            return "{\"status\":\"generated\",\"alias\":\"" + alias + "\",\"algorithm\":\"" + algorithm + "\",\"key_size\":" + keySize + "}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String keystoreDelete(Context context, String alias) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        String str = "\"}";
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!keyStore.containsAlias(alias)) {
                str = "{\"error\":\"Alias not found: " + alias + "\"}";
            } else {
                keyStore.deleteEntry(alias);
                str = "{\"status\":\"deleted\",\"alias\":\"" + alias + "\"}";
            }
            return str;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + str;
        }
    }

    private final String keystoreSign(Context context, String args) throws BadPaddingException, JSONException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, InvalidKeyException, KeyStoreException, CertificateException {
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 2, 2, (Object) null);
        String alias = (String) CollectionsKt.getOrNull(parts, 0);
        if (alias == null) {
            return "{\"error\":\"No alias specified\"}";
        }
        String data = (String) CollectionsKt.getOrNull(parts, 1);
        if (data == null) {
            return "{\"error\":\"No data to sign\"}";
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Key key = keyStore.getKey(alias, null);
            if (key == null) {
                return "{\"error\":\"Key not found\"}";
            }
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, key);
            byte[] iv = cipher.getIV();
            byte[] bytes = data.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            byte[] encrypted = cipher.doFinal(bytes);
            JSONObject $this$keystoreSign_u24lambda_u2448 = new JSONObject();
            $this$keystoreSign_u24lambda_u2448.put("signature", Base64.encodeToString(encrypted, 2));
            $this$keystoreSign_u24lambda_u2448.put("iv", Base64.encodeToString(iv, 2));
            $this$keystoreSign_u24lambda_u2448.put("alias", alias);
            String string = $this$keystoreSign_u24lambda_u2448.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    private final String keystoreVerify(Context context, String args) throws BadPaddingException, JSONException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, InvalidKeyException, KeyStoreException, CertificateException, InvalidAlgorithmParameterException {
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String alias = (String) CollectionsKt.getOrNull(parts, 0);
        if (alias == null) {
            return "{\"error\":\"No alias specified\"}";
        }
        String signatureB64 = (String) CollectionsKt.getOrNull(parts, 1);
        if (signatureB64 == null) {
            return "{\"error\":\"No signature\"}";
        }
        String ivB64 = (String) CollectionsKt.getOrNull(parts, 2);
        if (ivB64 == null) {
            return "{\"error\":\"No IV\"}";
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Key key = keyStore.getKey(alias, null);
            if (key == null) {
                return "{\"error\":\"Key not found\"}";
            }
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = Base64.decode(ivB64, 2);
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(2, key, spec);
            byte[] signature = Base64.decode(signatureB64, 2);
            byte[] decrypted = cipher.doFinal(signature);
            JSONObject $this$keystoreVerify_u24lambda_u2449 = new JSONObject();
            $this$keystoreVerify_u24lambda_u2449.put("valid", true);
            Intrinsics.checkNotNull(decrypted);
            try {
                $this$keystoreVerify_u24lambda_u2449.put("data", new String(decrypted, Charsets.UTF_8));
                String string = $this$keystoreVerify_u24lambda_u2449.toString();
                Intrinsics.checkNotNull(string);
                return string;
            } catch (Exception e) {
                e = e;
                return "{\"valid\":false,\"error\":\"" + e.getMessage() + "\"}";
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    private final String nfcInfo(Context context) throws JSONException {
        try {
            Object systemService = context.getSystemService("nfc");
            NfcManager nfcManager = systemService instanceof NfcManager ? (NfcManager) systemService : null;
            NfcAdapter nfcAdapter = nfcManager != null ? nfcManager.getDefaultAdapter() : null;
            if (nfcAdapter == null) {
                return "{\"error\":\"NFC not available\"}";
            }
            JSONObject $this$nfcInfo_u24lambda_u2450 = new JSONObject();
            $this$nfcInfo_u24lambda_u2450.put("enabled", nfcAdapter.isEnabled());
            $this$nfcInfo_u24lambda_u2450.put("available", true);
            $this$nfcInfo_u24lambda_u2450.put("note", "Use Intent.ACTION_NDEF_DISCOVERED in activity to read tags");
            String string = $this$nfcInfo_u24lambda_u2450.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String notificationList(Context context) throws JSONException {
        String string;
        StatusBarNotification[] activeNotifications;
        String string2;
        String string3;
        try {
            Object systemService = context.getSystemService("notification");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
            NotificationManager notificationManager = (NotificationManager) systemService;
            JSONArray arr = new JSONArray();
            StatusBarNotification[] activeNotifications2 = notificationManager.getActiveNotifications();
            Intrinsics.checkNotNull(activeNotifications2);
            int length = activeNotifications2.length;
            int i = 0;
            while (i < length) {
                StatusBarNotification statusBarNotification = activeNotifications2[i];
                JSONObject $this$notificationList_u24lambda_u2453_u24lambda_u2452 = new JSONObject();
                $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("id", statusBarNotification.getId());
                $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("package", statusBarNotification.getPackageName());
                NotificationManager notificationManager2 = notificationManager;
                $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("post_time", statusBarNotification.getPostTime());
                $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("is_ongoing", statusBarNotification.isOngoing());
                Notification notification = statusBarNotification.getNotification();
                String str = notification.category;
                if (str == null) {
                    str = "";
                } else {
                    Intrinsics.checkNotNull(str);
                }
                $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("category", str);
                Bundle extras = notification.extras;
                if (extras != null) {
                    Intrinsics.checkNotNull(extras);
                    activeNotifications = activeNotifications2;
                    CharSequence charSequence = extras.getCharSequence(NotificationCompat.EXTRA_TITLE);
                    if (charSequence == null || (string2 = charSequence.toString()) == null) {
                        string2 = "";
                    }
                    $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("title", string2);
                    CharSequence charSequence2 = extras.getCharSequence(NotificationCompat.EXTRA_TEXT);
                    if (charSequence2 == null || (string3 = charSequence2.toString()) == null) {
                        string3 = "";
                    }
                    $this$notificationList_u24lambda_u2453_u24lambda_u2452.put("text", string3);
                } else {
                    activeNotifications = activeNotifications2;
                }
                arr.put($this$notificationList_u24lambda_u2453_u24lambda_u2452);
                i++;
                notificationManager = notificationManager2;
                activeNotifications2 = activeNotifications;
            }
            if (arr.length() == 0) {
                JSONObject $this$notificationList_u24lambda_u2454 = new JSONObject();
                $this$notificationList_u24lambda_u2454.put("notifications", arr);
                $this$notificationList_u24lambda_u2454.put("note", "Only shows this app's notifications. For all notifications, grant NotificationListener permission in Settings.");
                string = $this$notificationList_u24lambda_u2454.toString();
            } else {
                string = arr.toString();
            }
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String speechToText(Context context) throws JSONException {
        try {
            boolean available = SpeechRecognizer.isRecognitionAvailable(context);
            if (!available) {
                return "{\"error\":\"Speech recognition not available\"}";
            }
            JSONObject $this$speechToText_u24lambda_u2455 = new JSONObject();
            $this$speechToText_u24lambda_u2455.put("available", true);
            $this$speechToText_u24lambda_u2455.put("note", "Speech recognition requires Activity context. Use Intent.ACTION_RECOGNIZE_SPEECH");
            $this$speechToText_u24lambda_u2455.put("example", "am start -a android.speech.action.RECOGNIZE_SPEECH");
            String string = $this$speechToText_u24lambda_u2455.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safList(Context context, String args) {
        Throwable th;
        try {
            int i = 1;
            int i2 = 0;
            if (args.length() == 0) {
                return "{\"error\":\"No document URI specified. Use termux-saf-managedir first.\"}";
            }
            Uri uri = Uri.parse(args);
            JSONArray arr = new JSONArray();
            Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri));
            int i3 = 2;
            Cursor cursor = context.getContentResolver().query(childrenUri, new String[]{"document_id", "_display_name", "mime_type", "_size", "last_modified"}, null, null, null);
            if (cursor != null) {
                Cursor cursor2 = cursor;
                try {
                    Cursor it = cursor2;
                    while (it.moveToNext()) {
                        JSONObject $this$safList_u24lambda_u2457_u24lambda_u2456 = new JSONObject();
                        $this$safList_u24lambda_u2457_u24lambda_u2456.put("id", it.getString(i2));
                        $this$safList_u24lambda_u2457_u24lambda_u2456.put("name", it.getString(i));
                        $this$safList_u24lambda_u2457_u24lambda_u2456.put("mime_type", it.getString(i3));
                        $this$safList_u24lambda_u2457_u24lambda_u2456.put("size", it.getLong(3));
                        Cursor cursor3 = cursor;
                        try {
                            $this$safList_u24lambda_u2457_u24lambda_u2456.put("last_modified", it.getLong(4));
                            $this$safList_u24lambda_u2457_u24lambda_u2456.put("is_directory", Intrinsics.areEqual(it.getString(2), "vnd.android.document/directory"));
                            arr.put($this$safList_u24lambda_u2457_u24lambda_u2456);
                            i3 = 2;
                            cursor = cursor3;
                            i = 1;
                            i2 = 0;
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(cursor2, th);
                                throw th3;
                            }
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor2, null);
                } catch (Throwable th4) {
                    th = th4;
                }
            }
            String string = arr.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safStat(Context context, String args) {
        String string;
        try {
            if (args.length() == 0) {
                return "{\"error\":\"No document URI specified\"}";
            }
            Uri uri = Uri.parse(args);
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"document_id", "_display_name", "mime_type", "_size", "last_modified", "flags"}, null, null, null);
            if (cursor != null) {
                Cursor cursor2 = cursor;
                try {
                    Cursor it = cursor2;
                    if (it.moveToFirst()) {
                        JSONObject $this$safStat_u24lambda_u2459_u24lambda_u2458 = new JSONObject();
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("id", it.getString(0));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("name", it.getString(1));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("mime_type", it.getString(2));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("size", it.getLong(3));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("last_modified", it.getLong(4));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("flags", it.getInt(5));
                        $this$safStat_u24lambda_u2459_u24lambda_u2458.put("is_directory", Intrinsics.areEqual(it.getString(2), "vnd.android.document/directory"));
                        string = $this$safStat_u24lambda_u2459_u24lambda_u2458.toString();
                    } else {
                        string = "{\"error\":\"Document not found\"}";
                    }
                    CloseableKt.closeFinally(cursor2, null);
                    if (string != null) {
                        return string;
                    }
                } finally {
                }
            }
            return "{\"error\":\"Could not query document\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safRead(Context context, String args) throws FileNotFoundException {
        try {
            if (args.length() == 0) {
                return "{\"error\":\"No document URI specified\"}";
            }
            Uri uri = Uri.parse(args);
            InputStream inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream != null) {
                InputStream inputStream = inputStreamOpenInputStream;
                try {
                    InputStream inputStream2 = inputStream;
                    Reader inputStreamReader = new InputStreamReader(inputStream2, Charsets.UTF_8);
                    String text = TextStreamsKt.readText(inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192));
                    CloseableKt.closeFinally(inputStream, null);
                    if (text != null) {
                        return text;
                    }
                } finally {
                }
            }
            return "{\"error\":\"Could not open document\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safWrite(Context context, String args) throws FileNotFoundException {
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 2, 2, (Object) null);
        String uriStr = (String) CollectionsKt.getOrNull(parts, 0);
        if (uriStr == null) {
            return "{\"error\":\"No document URI specified\"}";
        }
        String content = (String) CollectionsKt.getOrNull(parts, 1);
        if (content == null) {
            return "{\"error\":\"No content specified\"}";
        }
        try {
            Uri uri = Uri.parse(uriStr);
            OutputStream outputStreamOpenOutputStream = context.getContentResolver().openOutputStream(uri, "wt");
            if (outputStreamOpenOutputStream != null) {
                OutputStream outputStream = outputStreamOpenOutputStream;
                try {
                    OutputStream outputStream2 = outputStream;
                    byte[] bytes = content.getBytes(Charsets.UTF_8);
                    Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                    outputStream2.write(bytes);
                    String str = "{\"status\":\"written\",\"bytes\":" + content.length() + "}";
                    CloseableKt.closeFinally(outputStream, null);
                    if (str != null) {
                        return str;
                    }
                } finally {
                }
            }
            return "{\"error\":\"Could not open document for writing\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safMkdir(Context context, String args) throws FileNotFoundException {
        String str = "\"}";
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String parentUri = (String) CollectionsKt.getOrNull(parts, 0);
        if (parentUri == null) {
            return "{\"error\":\"No parent URI specified\"}";
        }
        String dirName = (String) CollectionsKt.getOrNull(parts, 1);
        if (dirName == null) {
            return "{\"error\":\"No directory name specified\"}";
        }
        try {
            Uri parent = Uri.parse(parentUri);
            String parentDocId = DocumentsContract.getTreeDocumentId(parent);
            Uri parentDocUri = DocumentsContract.buildDocumentUriUsingTree(parent, parentDocId);
            Uri newDir = DocumentsContract.createDocument(context.getContentResolver(), parentDocUri, "vnd.android.document/directory", dirName);
            if (newDir != null) {
                str = "{\"status\":\"created\",\"uri\":\"" + newDir + "\"}";
                return str;
            }
            return "{\"error\":\"Could not create directory\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + str;
        }
    }

    private final String safRemove(Context context, String args) throws FileNotFoundException {
        try {
            if (args.length() == 0) {
                return "{\"error\":\"No document URI specified\"}";
            }
            Uri uri = Uri.parse(args);
            boolean deleted = DocumentsContract.deleteDocument(context.getContentResolver(), uri);
            if (deleted) {
                return "{\"status\":\"deleted\"}";
            }
            return "{\"error\":\"Could not delete document\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safCreate(Context context, String args) throws FileNotFoundException {
        String str = "\"}";
        List parts = StringsKt.split$default((CharSequence) args, new String[]{"|"}, false, 0, 6, (Object) null);
        String parentUri = (String) CollectionsKt.getOrNull(parts, 0);
        if (parentUri == null) {
            return "{\"error\":\"No parent URI specified\"}";
        }
        String fileName = (String) CollectionsKt.getOrNull(parts, 1);
        if (fileName == null) {
            return "{\"error\":\"No file name specified\"}";
        }
        String mimeType = (String) CollectionsKt.getOrNull(parts, 2);
        if (mimeType == null) {
            mimeType = "text/plain";
        }
        try {
            Uri parent = Uri.parse(parentUri);
            String parentDocId = DocumentsContract.getTreeDocumentId(parent);
            Uri parentDocUri = DocumentsContract.buildDocumentUriUsingTree(parent, parentDocId);
            Uri newFile = DocumentsContract.createDocument(context.getContentResolver(), parentDocUri, mimeType, fileName);
            if (newFile != null) {
                str = "{\"status\":\"created\",\"uri\":\"" + newFile + "\"}";
                return str;
            }
            return "{\"error\":\"Could not create file\"}";
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + str;
        }
    }

    private final String safManageDir(Context context, String args) throws JSONException {
        try {
            JSONObject $this$safManageDir_u24lambda_u2462 = new JSONObject();
            $this$safManageDir_u24lambda_u2462.put("note", "SAF directory management requires user interaction");
            $this$safManageDir_u24lambda_u2462.put("to_select_directory", "am start -a android.intent.action.OPEN_DOCUMENT_TREE");
            $this$safManageDir_u24lambda_u2462.put("usage", "After selecting a directory, the URI will be returned. Use that URI with other saf-* commands.");
            String string = $this$safManageDir_u24lambda_u2462.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private final String safDirs(Context context) throws JSONException {
        try {
            JSONObject $this$safDirs_u24lambda_u2463 = new JSONObject();
            $this$safDirs_u24lambda_u2463.put("note", "SAF directories are user-selected. Use termux-saf-managedir to select a directory.");
            $this$safDirs_u24lambda_u2463.put("primary_storage", "/sdcard");
            $this$safDirs_u24lambda_u2463.put("downloads", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
            $this$safDirs_u24lambda_u2463.put("dcim", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
            $this$safDirs_u24lambda_u2463.put("documents", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
            String string = $this$safDirs_u24lambda_u2463.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }
}
