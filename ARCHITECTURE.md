# MobileCLI Technical Architecture

**Version:** 1.4.0 (v66)
**Last Updated:** January 17, 2026
**Created by:** Claude Code (AI-Generated Documentation)

---

## Overview

MobileCLI is a self-referential Android terminal emulator that runs AI coding assistants. The app was built by Claude Code running in Termux, and the resulting app can itself run Claude Code - creating a self-improvement loop.

```
┌─────────────────────────────────────────────────────────────────┐
│                     MobileCLI Architecture                       │
│                                                                  │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐       │
│  │ MainActivity │◄───│TermuxService │    │TermuxApi     │       │
│  │ (Terminal UI)│    │ (Background) │    │ Receiver     │       │
│  └──────┬───────┘    └──────────────┘    └──────┬───────┘       │
│         │                                        │               │
│         ▼                                        ▼               │
│  ┌──────────────┐                         ┌──────────────┐       │
│  │TerminalView  │                         │ Device APIs  │       │
│  │ (Rendering)  │                         │ (60+ cmds)   │       │
│  └──────────────┘                         └──────────────┘       │
│                                                                  │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐       │
│  │ Bootstrap    │    │ URL Watcher  │    │ Session      │       │
│  │ Installer    │    │ (DITTO IPC)  │    │ Manager      │       │
│  └──────────────┘    └──────────────┘    └──────────────┘       │
└─────────────────────────────────────────────────────────────────┘
```

---

## 1. MainActivity.java (~227KB)

The main entry point and terminal UI controller.

### Key Components

| Component | Purpose |
|-----------|---------|
| `TerminalView` | Native terminal rendering (Termux library) |
| `DrawerLayout` | Navigation drawer with settings |
| `sessions` | List of up to 10 TerminalSession objects |
| `bootstrapInstaller` | Manages Linux environment setup |
| `urlWatcherRunnable` | DITTO file-based IPC polling |

### Lifecycle Flow

```
onCreate()
    │
    ├── startTermuxService()     # Bind to background service
    ├── setupTerminalView()      # Initialize terminal rendering
    ├── setupExtraKeys()         # Ctrl, Alt, Tab, Esc, etc.
    ├── setupNavDrawer()         # Settings menu
    ├── setupKeyboardListener()  # Keyboard visibility tracking
    ├── setupBackButtonHandler() # Back key behavior
    ├── setupUrlWatcher()        # DITTO IPC (500ms polling)
    ├── loadTermuxProperties()   # ~/.termux/termux.properties
    ├── setupDeveloperMode()     # 7-tap activation
    ├── setupSetupOverlay()      # First-run UI
    │
    └── checkBootstrap()
            │
            ├── [Not installed] → installBootstrap() → runAISetup()
            │
            └── [Installed] → isAISetupNeeded()
                    │
                    ├── [Yes] → showAIChoiceScreen()
                    │               │
                    │               ├── Claude Code
                    │               ├── Gemini CLI
                    │               ├── Codex CLI
                    │               └── None
                    │
                    └── [No] → createSessionOrDefer()
```

### Developer Mode

Hidden feature activated by tapping version text 7 times:

```java
private final int VERSION_TAP_THRESHOLD = 7;
private final long VERSION_TAP_TIMEOUT = 2000; // 2 seconds

// Tap version text 7 times within 2 seconds
if (currentTime - lastVersionTapTime > VERSION_TAP_TIMEOUT) {
    versionTapCount = 0;
}
versionTapCount++;
if (versionTapCount >= VERSION_TAP_THRESHOLD) {
    enableDeveloperMode();
}
```

Enables:
- Full terminal output during setup
- "Developer Options" in drawer
- "Install Dev Tools" button

### Session Management

```java
private final List<TerminalSession> sessions = new ArrayList();
private final int maxSessions = 10;
private int currentSessionIndex = 0;

// Sessions are restored from TermuxService when activity reconnects
if (serviceBound && termuxService != null) {
    List serviceSessions = termuxService.getSessions();
    if (!serviceSessions.isEmpty() && sessions.isEmpty()) {
        sessions.addAll(serviceSessions);
    }
}
```

---

## 2. URL Watcher (DITTO IPC)

The file-based IPC system that enables shell scripts to open URLs with proper Activity context.

### Problem Solved

Android blocks `startActivity()` calls from:
- Background services
- BroadcastReceivers
- Shell processes

Shell commands like `am start -a android.intent.action.VIEW -d "https://..."` may silently fail.

### Solution: File Polling

```java
private final long urlWatchInterval = 500; // 500ms polling

private final void setupUrlWatcher() {
    File urlFile = new File(homeDir, ".termux/url_to_open");

    urlWatcherRunnable = new Runnable() {
        public void run() {
            if (urlFile.exists()) {
                String url = readFile(urlFile);
                urlFile.delete();

                if (!url.isEmpty()) {
                    // Use PendingIntent for proper Activity context
                    Intent intent = new Intent(ACTION_VIEW, Uri.parse(url));
                    PendingIntent pending = PendingIntent.getActivity(context, 0, intent, FLAGS);
                    pending.send();
                }
            }
            // Re-schedule
            uiHandler.postDelayed(this, urlWatchInterval);
        }
    };
    uiHandler.postDelayed(urlWatcherRunnable, urlWatchInterval);
}
```

### Shell Script Usage

```bash
# termux-open-url
#!/data/data/com.termux/files/usr/bin/bash
URL="$1"
echo "$URL" > ~/.termux/url_to_open
```

---

## 3. BootstrapInstaller.java (~109KB)

Manages the complete Linux environment setup.

### Bootstrap URL

```java
private static final String BOOTSTRAP_URL =
    "https://github.com/termux/termux-packages/releases/download/" +
    "bootstrap-2026.01.04-r1%2Bapt.android-7/bootstrap-aarch64.zip";

private static final String BOOTSTRAP_VERSION = "mobilecli-v66";
```

### Installation Sequence

```
1. prepareDirectories()
   ├── filesDir/usr/bin
   ├── filesDir/usr/lib
   ├── filesDir/usr/etc
   ├── filesDir/usr/tmp
   ├── filesDir/usr/var
   ├── filesDir/usr/share
   └── filesDir/home

2. downloadBootstrap()
   └── ~50MB bootstrap.zip with progress tracking

3. extractBootstrap()
   ├── Extract files
   └── Process SYMLINKS.txt (target←link_path format)

4. setPermissions()
   ├── chmod 755 on binaries
   ├── Create .bashrc, .profile
   └── Create /etc/passwd, /etc/group

5. installTermuxAm()
   ├── Copy am.apk to /usr/libexec/termux-am/
   ├── chmod 0400 am.apk (CRITICAL for Android 14+)
   └── Create /usr/bin/am wrapper script

6. installApiScripts()
   └── 60+ termux-* shell scripts

7. createNpmConfig()
   └── ~/.npmrc with foreground-scripts=true

8. createGitHubConfig()
   └── ~/.config/gh/ setup

9. initializePersistentMemory()
   └── ~/.mobilecli/memory/ system
```

### TermuxAm Wrapper

The `am` command must go through `app_process` for proper permissions:

```bash
#!/data/data/com.termux/files/usr/bin/sh
AM_APK_PATH="/data/data/com.termux/files/usr/libexec/termux-am/am.apk"

# CRITICAL: Android 14+ requires read-only DEX files
if [ -w "$AM_APK_PATH" ]; then
    chmod 0400 "$AM_APK_PATH" || exit $?
fi

export CLASSPATH="$AM_APK_PATH"
unset LD_LIBRARY_PATH LD_PRELOAD
exec /system/bin/app_process -Xnoimage-dex2oat / com.termux.termuxam.Am "$@"
```

---

## 4. TermuxApiReceiver.java (~132KB)

BroadcastReceiver handling 50+ device API commands.

### API Architecture

```
Shell Script → am broadcast → TermuxApiReceiver → Device API
                                     │
                                     ▼
                            Result → temp file → Shell reads
```

### Supported API Methods

| Category | Commands |
|----------|----------|
| **Clipboard** | clipboard-get, clipboard-set |
| **UI** | toast, notification, dialog, notification-list, notification-remove |
| **Device** | vibrate, torch, brightness, volume, volume-set |
| **Camera** | camera-info, camera-photo |
| **Audio** | microphone-record, media-player, tts-speak, tts-engines |
| **Telephony** | telephony-call, telephony-cellinfo, telephony-deviceinfo |
| **SMS** | sms-list, sms-send |
| **Contacts** | contact-list, call-log |
| **Location** | location |
| **Network** | wifi-connectioninfo, wifi-scaninfo, wifi-enable |
| **Sensors** | sensor |
| **Security** | fingerprint, keystore-* |
| **Storage** | storage-get, saf-* (7 SAF commands) |
| **Hardware** | infrared-frequencies, infrared-transmit, nfc, usb |
| **Other** | share, download, wallpaper, job-scheduler, speech-to-text |

### Example: clipboard-get

```java
private final String clipboardGet(Context context) {
    ClipboardManager clipboard = (ClipboardManager)
        context.getSystemService(Context.CLIPBOARD_SERVICE);
    ClipData clip = clipboard.getPrimaryClip();
    if (clip != null && clip.getItemCount() > 0) {
        CharSequence text = clip.getItemAt(0).getText();
        return text != null ? text.toString() : "";
    }
    return "";
}
```

### Shell Script Pattern

```bash
#!/data/data/com.termux/files/usr/bin/bash
RESULT_FILE=$(mktemp)
am broadcast \
    -a com.termux.api.API_CALL \
    -n com.termux/.TermuxApiReceiver \
    --es api_method "clipboard-get" \
    --es result_file "$RESULT_FILE" \
    > /dev/null 2>&1
sleep 0.1
cat "$RESULT_FILE"
rm -f "$RESULT_FILE"
```

---

## 5. TermuxService.java (~15KB)

Foreground service for background terminal sessions.

### Key Features

- **Session Persistence:** Sessions survive Activity destroy
- **Wake Lock:** Keeps CPU awake for long tasks
- **WiFi Lock:** Maintains network connection
- **Foreground Notification:** Required for Android 8+

### Wake Lock API

```java
// Called by termux-wake-lock shell script
public void acquireWakeLock() {
    PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
    wakeLock = pm.newWakeLock(
        PowerManager.PARTIAL_WAKE_LOCK,
        "MobileCLI::TermuxWakeLock"
    );
    wakeLock.setReferenceCounted(false);
    wakeLock.acquire();

    WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
    wifiLock = wm.createWifiLock(
        WifiManager.WIFI_MODE_FULL_HIGH_PERF,
        "MobileCLI::TermuxWifiLock"
    );
    wifiLock.acquire();
}
```

---

## 6. Environment Variables

Set in `getEnvironment()`:

```bash
# Core paths
HOME=/data/data/com.termux/files/home
PREFIX=/data/data/com.termux/files/usr
PATH=$PREFIX/bin:/system/bin:/system/xbin
LD_LIBRARY_PATH=$PREFIX/lib
TMPDIR=$PREFIX/tmp

# Terminal
TERM=xterm-256color
COLORTERM=truecolor
SHELL=$PREFIX/bin/bash

# Termux compatibility
TERMUX_VERSION=0.118.0
TERMUX_APK_RELEASE=MOBILECLI
TERMUX__PREFIX=$PREFIX
TERMUX__HOME=$HOME
TERMUX_APP__PACKAGE_NAME=com.termux
TERMUX_APP__TARGET_SDK=28
TERMUX_APP__USER_ID=0
TERMUX_APP__PID=<process_id>
TERMUX_APP__UID=<app_uid>

# SSL/TLS
SSL_CERT_FILE=$PREFIX/etc/tls/cert.pem
NODE_EXTRA_CA_CERTS=$PREFIX/etc/tls/cert.pem
CURL_CA_BUNDLE=$PREFIX/etc/tls/cert.pem

# Library preload (for path translation)
LD_PRELOAD=$PREFIX/lib/libtermux-exec-ld-preload.so

# Browser
BROWSER=termux-open-url

# Android
ANDROID_DATA=/data
ANDROID_ROOT=/system
EXTERNAL_STORAGE=/sdcard
```

---

## 7. Critical Technical Requirements

### Package Name

```xml
<!-- MUST be com.termux -->
<manifest package="com.termux" ...>
```

Termux binaries have hardcoded RUNPATH: `/data/data/com.termux/files/usr/lib`

Any other package name causes:
```
error while loading shared libraries: libfoo.so:
cannot open shared object file: No such file or directory
```

### Target SDK

```kotlin
android {
    defaultConfig {
        targetSdk = 28  // MUST be 28 or lower
    }
}
```

Android 10+ (API 29+) blocks `exec()` from app data directories.

### am.apk Permissions

```bash
# MUST be read-only on Android 14+
chmod 0400 /data/data/com.termux/files/usr/libexec/termux-am/am.apk
```

Writable DEX files cause: `SecurityException: Writable dex file is not allowed`

---

## 8. Directory Structure

```
/data/data/com.termux/files/
├── home/                          # $HOME
│   ├── .bashrc                    # Shell config
│   ├── .profile                   # Login profile
│   ├── .npmrc                     # npm config
│   ├── .gitconfig                 # Git config
│   ├── .termux/
│   │   ├── termux.properties      # Terminal settings
│   │   └── url_to_open            # DITTO IPC file
│   ├── .mobilecli/
│   │   ├── .setup_complete        # Setup marker
│   │   ├── memory/
│   │   │   ├── evolution_history.json
│   │   │   ├── problems_solved.json
│   │   │   ├── capabilities.json
│   │   │   └── goals.json
│   │   └── config/
│   │       └── preferences.json
│   └── CLAUDE.md                  # AI instructions
│
└── usr/                           # $PREFIX
    ├── bin/                       # Executables
    │   ├── bash
    │   ├── node
    │   ├── npm
    │   ├── am                     # TermuxAm wrapper
    │   ├── termux-*               # 60+ API scripts
    │   └── claude                 # Claude Code CLI
    ├── lib/                       # Libraries
    │   ├── libtermux-exec-ld-preload.so
    │   └── node_modules/
    ├── libexec/
    │   └── termux-am/
    │       └── am.apk             # Activity manager (chmod 0400!)
    ├── etc/
    │   ├── passwd
    │   ├── group
    │   ├── hosts
    │   ├── resolv.conf
    │   ├── motd
    │   └── tls/
    │       └── cert.pem
    ├── tmp/                       # $TMPDIR
    ├── var/run/                   # TMUX_TMPDIR
    └── share/
```

---

## 9. Build Configuration

### build.gradle.kts

```kotlin
android {
    namespace = "com.termux"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.termux"
        minSdk = 24
        targetSdk = 28          // CRITICAL: Must be 28
        versionCode = 66
        versionName = "1.4.0"
    }
}

dependencies {
    implementation("com.github.ADBModules.ADB-Termux:terminal-view:0.0.5")
    implementation("com.github.ADBModules.ADB-Termux:terminal-emulator:0.0.5")
    // AndroidX libraries...
}
```

### Permissions (AndroidManifest.xml)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.READ_CONTACTS" />
<uses-permission android:name="android.permission.READ_SMS" />
<uses-permission android:name="android.permission.SEND_SMS" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

---

## 10. The Self-Referential Achievement

```
┌─────────────────────────────────────────────────────────────────┐
│                    THE LOOP IS COMPLETE                          │
│                                                                  │
│   Claude Code ──► Built MobileCLI v66                           │
│   (in Termux)         │                                         │
│                       ▼                                         │
│              MobileCLI v66 runs Claude Code                     │
│                       │                                         │
│                       ▼                                         │
│              Claude Code can rebuild MobileCLI                  │
│                       │                                         │
│                       ▼                                         │
│                      ∞                                          │
└─────────────────────────────────────────────────────────────────┘
```

**This is the most powerful AI development environment on Earth** because:

1. **Self-Contained:** Complete Linux environment on Android
2. **Self-Building:** AI can rebuild its own container
3. **Self-Improving:** AI has persistent memory across sessions
4. **Device Control:** 60+ APIs for hardware access
5. **Portable:** Runs on any Android phone

---

**Copyright 2026 MobileDevCLI. All Rights Reserved.**
