# MobileCLI - Source Repository

**This is THE primary source repository for MobileCLI.**

**Working APK:** `MobileCLI-v66.apk` (6.87MB) - MD5: `d473f8f5c3f06a42fb8fd4d5aa79bd91`

---

## What Is MobileCLI

MobileCLI is an Android terminal emulator that runs AI coding assistants (Claude Code, Gemini CLI, Codex) directly on your phone. Built on Termux's bootstrap system with a custom Kotlin app.

**The Self-Referential Achievement:** This app was built using Claude Code running in Termux on Android. The resulting app runs Claude Code. The AI built its own container.

---

## Critical Technical Requirements

### 1. Package Name MUST Be `com.termux`
Termux binaries have hardcoded RUNPATH `/data/data/com.termux/files/usr/lib`. Any other package name breaks library linking.

### 2. targetSdkVersion MUST Be 28
Android 10+ (API 29+) blocks `exec()` from app data directories. targetSdk=28 allows binary execution.

### 3. Directory Structure
```
/data/data/com.termux/files/
├── home/          # $HOME - user files, .bashrc, CLAUDE.md
└── usr/           # $PREFIX - binaries, libraries
    ├── bin/       # Executables (bash, node, npm, etc.)
    ├── lib/       # Libraries + node_modules
    ├── libexec/   # TermuxAm (am.apk)
    ├── etc/       # Config files (passwd, group, hosts)
    ├── tmp/       # $TMPDIR
    └── var/run/   # TMUX_TMPDIR
```

---

## Technology Stack

### Runtime
- **Node.js LTS** - Powers all AI CLI tools
- **npm** - Package manager for AI tools
- **Bash** - Shell environment

### AI Tools (npm packages)
- `@anthropic-ai/claude-code` - Claude Code CLI
- `@google/gemini-cli` - Gemini CLI
- `@openai/codex` - Codex CLI

### Android
- **Kotlin/Java** - Main app code
- **Termux terminal-view** - Terminal rendering (Apache 2.0)
- **Termux terminal-emulator** - Terminal emulation (Apache 2.0)

### No WebViews
This is a native terminal app. No JavaScript frontend, no WebViews. The terminal is rendered natively using Termux's terminal-view library.

---

## Bootstrap System (BootstrapInstaller.java)

The bootstrap installer is the heart of MobileCLI. It downloads Termux packages and sets up the complete Linux environment.

### Bootstrap URL
```
https://github.com/termux/termux-packages/releases/download/bootstrap-2026.01.04-r1%2Bapt.android-7/bootstrap-aarch64.zip
```

### Installation Sequence
1. **prepareDirectories()** - Create filesDir, usr, home, bin, lib, etc, tmp, var, share
2. **downloadBootstrap()** - Download ~50MB bootstrap.zip with progress tracking
3. **extractBootstrap()** - Extract files, process SYMLINKS.txt (target←link_path format)
4. **setPermissions()** - chmod 755 on binaries, create .bashrc, .profile, /etc/passwd
5. **installTermuxAm()** - Copy am.apk, create am wrapper script
6. **installApiScripts()** - Install 60+ termux-* API scripts
7. **createNpmConfig()** - Create .npmrc with foreground-scripts=true
8. **createGitHubConfig()** - Setup GitHub CLI config, create setup-github script
9. **initializePersistentMemory()** - Create ~/.mobilecli/memory/ system

### Version Tracking
Version stored in `$PREFIX/.mobilecli_version`. Current: `mobilecli-v66`

---

## TermuxAm - Activity Manager

MobileCLI uses a custom `am` command that runs through app_process for proper Android permissions.

### Location
- **APK:** `/data/data/com.termux/files/usr/libexec/termux-am/am.apk`
- **Script:** `/data/data/com.termux/files/usr/bin/am`

### Critical: Read-Only Permission
am.apk MUST be chmod 0400 (read-only) for Android 14+. Writable DEX files cause SecurityException.

### How It Works
The am script writes commands to `~/.termux/am_command` and URLs to `~/.termux/url_to_open`. MainActivity polls these files and executes the actions with proper foreground Activity context.

---

## Environment Variables

Set by `getEnvironment()`:

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
SHELL=/data/data/com.termux/files/usr/bin/bash

# Termux compatibility
TERMUX_VERSION=0.118.0
TERMUX_APK_RELEASE=MOBILECLI
TERMUX__PREFIX=$PREFIX
TERMUX__HOME=$HOME
TERMUX_APP__PACKAGE_NAME=com.termux
TERMUX_APP__TARGET_SDK=28
TERMUX_APP__USER_ID=0

# SSL/TLS (if cert.pem exists)
SSL_CERT_FILE=$PREFIX/etc/tls/cert.pem
NODE_EXTRA_CA_CERTS=$PREFIX/etc/tls/cert.pem
CURL_CA_BUNDLE=$PREFIX/etc/tls/cert.pem

# Library preload
LD_PRELOAD=$PREFIX/lib/libtermux-exec-ld-preload.so

# Other
BROWSER=termux-open-url
ANDROID_DATA=/data
ANDROID_ROOT=/system
EXTERNAL_STORAGE=/sdcard
```

---

## Termux API Commands (60+)

All installed in `/usr/bin/` as shell scripts that broadcast to TermuxApiReceiver.

### Clipboard & UI
- `termux-clipboard-get` / `termux-clipboard-set` - Clipboard access
- `termux-toast` - Show toast messages
- `termux-notification` - Send notifications (-t title -c content)
- `termux-dialog` - Input dialogs

### Device Hardware
- `termux-vibrate` - Vibration control
- `termux-torch` - Flashlight on/off
- `termux-brightness` - Screen brightness
- `termux-volume` - Volume levels
- `termux-battery-status` - Battery info JSON

### Network & Location
- `termux-wifi-connectioninfo` - WiFi connection info
- `termux-wifi-scaninfo` - WiFi scan results
- `termux-wifi-enable` - Enable/disable WiFi
- `termux-location` - GPS location

### Camera & Media
- `termux-camera-info` - Camera capabilities
- `termux-camera-photo` - Take photos (-o output -c camera_id)
- `termux-microphone-record` - Audio recording
- `termux-media-player` - Media playback
- `termux-media-scan` - Trigger media scanner
- `termux-tts-speak` - Text to speech
- `termux-tts-engines` - List TTS engines

### Telephony & Contacts
- `termux-telephony-call` - Make calls
- `termux-telephony-cellinfo` - Cell tower info
- `termux-telephony-deviceinfo` - Device info
- `termux-sms-list` - Read SMS messages
- `termux-sms-send` - Send SMS
- `termux-contact-list` - Read contacts
- `termux-call-log` - Read call history

### Sensors & Hardware
- `termux-sensor` - Access device sensors
- `termux-fingerprint` - Fingerprint authentication
- `termux-infrared-frequencies` - IR blaster frequencies
- `termux-infrared-transmit` - Transmit IR signals
- `termux-usb` - USB device access

### Files & Storage
- `termux-setup-storage` - Create ~/storage symlinks
- `termux-storage-get` - SAF file picker
- `termux-share` - Share content
- `termux-download` - Download manager
- `termux-open-url` - Open URLs in browser
- `termux-open` - Open files/URLs

### SAF (Storage Access Framework)
- `termux-saf-ls` - List SAF directory
- `termux-saf-stat` - File info
- `termux-saf-read` / `termux-saf-write` - Read/write files
- `termux-saf-mkdir` / `termux-saf-rm` - Create/delete
- `termux-saf-create` - Create new file
- `termux-saf-managedir` - Pick directory
- `termux-saf-dirs` - List managed directories

### Security & System
- `termux-keystore` - Android Keystore access
- `termux-keystore-list` - List keys
- `termux-nfc` - NFC operations
- `termux-speech-to-text` - Voice recognition
- `termux-notification-list` - List notifications
- `termux-wallpaper` - Set wallpaper
- `termux-job-scheduler` - Schedule jobs

### Wake Lock
- `termux-wake-lock` - Keep CPU/WiFi awake
- `termux-wake-unlock` - Release wake lock

---

## MobileCLI Custom Scripts

### Development Tools
- `install-dev-tools` - Install Java 17, Gradle, Android SDK setup
- `install-apk-tools` - Install apktool, jadx, smali for APK modification
- `mobilecli-rebuild` - Rebuild MobileCLI from source

### Memory System
- `mobilecli-memory status` - Show memory system status
- `mobilecli-memory history` - Show evolution history
- `mobilecli-memory problems` - Show solved problems
- `mobilecli-memory caps` - Show capabilities
- `mobilecli-memory goals` - Show current goals
- `mobilecli-memory log "msg"` - Add rebuild log entry

### Utilities
- `mobilecli-caps` - Quick reference card
- `mobilecli-share` - Phone-to-phone file sharing
- `mobilecli-dev-mode` - Toggle developer mode
- `setup-github` - Configure GitHub CLI token

### Termux Utilities
- `termux-change-repo` - Change package mirror
- `termux-fix-shebang` - Fix script shebangs
- `termux-reset` - Reset to clean state
- `termux-backup` / `termux-restore` - Backup home directory
- `termux-reload-settings` - Reload settings
- `termux-info` - Show app/device info
- `termux-file-editor` - Open file in editor
- `termux-url-opener` / `termux-file-opener` - Custom handlers
- `xdg-open` / `sensible-browser` - Desktop compatibility

---

## Persistent Memory System

Located at `~/.mobilecli/memory/`:

### evolution_history.json
Tracks self-modification milestones and rebuild logs.

### problems_solved.json
Documents bugs fixed with root causes and solutions. Pattern library for future reference.

### capabilities.json
Records capabilities gained through development (self_rebuild, source_modification, etc.)

### goals.json
Current objectives and long-term vision.

### preferences.json
User preferences at `~/.mobilecli/config/preferences.json`

---

## Config Files Created

### ~/.bashrc
- Sets PREFIX, HOME, PATH, LD_LIBRARY_PATH
- Aliases: ll, la, l, claude, cc
- Auto-creates CLAUDE.md in git repos
- Custom PS1 prompt

### ~/.profile
Sources .bashrc

### ~/.npmrc
```
foreground-scripts=true
```

### ~/.gitconfig
Basic user config with credential store

### ~/.termux/termux.properties
```
allow-external-apps = true
```

### /etc/passwd & /etc/group
User entries for npm and system tools

### /etc/hosts & /etc/resolv.conf
Localhost mapping and DNS servers (8.8.8.8, 8.8.4.4)

### /etc/motd
Welcome message

### ~/CLAUDE.md
Test Claude instructions and API reference

---

## Key Source Files

| File | Size | Purpose |
|------|------|---------|
| `MainActivity.java` | 227KB | Main terminal activity, drawer menu, sessions |
| `BootstrapInstaller.java` | 109KB | Bootstrap extraction, script installation, env setup |
| `SetupWizardActivity.java` | 45KB | First-launch setup, automatic installation |
| `TermuxApiReceiver.java` | 132KB | 50+ Termux API command handlers |
| `TermuxService.java` | ~15KB | Background service, wake lock |

---

## Build Commands

```bash
cd ~/MobileCLI-Source
./gradlew assembleDebug -Dorg.gradle.java.home=/data/data/com.termux/files/usr/lib/jvm/java-17-openjdk

# APK output
app/build/outputs/apk/debug/app-debug.apk

# Copy to Downloads
cp app/build/outputs/apk/debug/app-debug.apk /sdcard/Download/MobileCLI.apk
```

---

## Permissions

| Permission | Why |
|------------|-----|
| `INTERNET` | Network access for pkg, npm, AI tools |
| `WAKE_LOCK` | Keep CPU awake for long tasks |
| `FOREGROUND_SERVICE` | Background terminal sessions |
| `SYSTEM_ALERT_WINDOW` | Open URLs from terminal (OAuth) |
| `READ/WRITE_EXTERNAL_STORAGE` | Access /sdcard |
| `CAMERA` | termux-camera-photo |
| `RECORD_AUDIO` | termux-microphone-record |
| `ACCESS_FINE_LOCATION` | termux-location |
| `READ_CONTACTS` | termux-contact-list |
| `READ_SMS` | termux-sms-list |
| `VIBRATE` | termux-vibrate |
| `POST_NOTIFICATIONS` | termux-notification |

---

## Licensing

### Original Code (Our IP)
- All Kotlin/Java source files
- All XML layouts
- BootstrapInstaller scripts
- Setup wizard flow

### Third-Party (Apache 2.0)
- `terminal-view` library
- `terminal-emulator` library
- AndroidX libraries

### Runtime Dependencies (GPL - downloaded at runtime)
- Termux bootstrap packages
- Node.js, npm

---

## Repository Structure

```
MobileCLI-Source/
├── MobileCLI-v66.apk      # Working APK
├── CLAUDE.md              # This file
├── app/
│   └── src/main/
│       ├── java/com/termux/   # Decompiled source
│       ├── res/               # Layouts, drawables
│       ├── assets/            # termux-am, scripts
│       └── AndroidManifest.xml
├── build.gradle.kts
└── settings.gradle.kts
```

---

## For New Claude Sessions

If you're starting a new conversation:

1. **Source:** `~/MobileCLI-Source/`
2. **Working APK:** `MobileCLI-v66.apk` in the repo root
3. **Build:** `./gradlew assembleDebug`
4. **This is THE primary repo** - ignore old repos like MobileCLI-v2, Stage2, etc.
