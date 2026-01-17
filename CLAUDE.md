# MobileCLI - Source Repository

**This is THE primary source repository for MobileCLI.**

## What Is MobileCLI

MobileCLI is an Android terminal emulator that runs AI coding assistants (Claude Code, Gemini CLI, Codex) directly on your phone. Built on Termux's bootstrap system with a custom Kotlin app.

**Working APK:** `MobileCLI-v66.apk` (6.87MB) - MD5: `d473f8f5c3f06a42fb8fd4d5aa79bd91`

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
    ├── etc/       # Config files
    └── tmp/       # $TMPDIR
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
- **Kotlin** - Main app code
- **Termux terminal-view** - Terminal rendering (Apache 2.0)
- **Termux terminal-emulator** - Terminal emulation (Apache 2.0)

### No WebViews
This is a native terminal app. No JavaScript frontend, no WebViews. The terminal is rendered natively using Termux's terminal-view library.

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

MobileCLI requests these permissions for full functionality:

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

## Termux API Commands

MobileCLI includes 50+ built-in Termux API commands:

**Clipboard:** `termux-clipboard-get`, `termux-clipboard-set`
**UI:** `termux-toast`, `termux-notification`, `termux-dialog`
**Hardware:** `termux-vibrate`, `termux-torch`, `termux-battery-status`
**Media:** `termux-camera-photo`, `termux-microphone-record`, `termux-tts-speak`
**System:** `termux-volume`, `termux-brightness`, `termux-wifi-connectioninfo`
**Files:** `termux-open-url`, `termux-open`, `termux-share`
**Background:** `termux-wake-lock`, `termux-wake-unlock`

---

## The Self-Referential Achievement

MobileCLI was built using Claude Code running in Termux on Android. The resulting app runs Claude Code. **The AI built its own container.**

```
Claude Code (Termux) → Built MobileCLI → MobileCLI runs Claude Code → ∞
```

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
