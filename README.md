# MobileCLI v66 - Working Version

This is the decompiled source from the **working v66 APK** that is installed and functioning.

## Working APK

The file `MobileCLI-v66.apk` is the exact working APK (6.87MB).
MD5: `d473f8f5c3f06a42fb8fd4d5aa79bd91`

## Source Code

The `app/src/main/java/com/termux/` directory contains the decompiled Java source from the APK.

**Note:** This is decompiled code. The original was Kotlin with coroutines, which don't decompile cleanly.
Some methods may show "Method dump skipped" in jadx output.

## Key Files

- `MainActivity.java` - Main terminal activity (227KB)
- `BootstrapInstaller.java` - Bootstrap + scripts installer (109KB)
- `SetupWizardActivity.java` - First-launch setup (45KB)
- `TermuxApiReceiver.java` - API commands handler (132KB)
- `TermuxService.java` - Background service (in app/ subdirectory)

## Features (v66)

- Automatic setup - installs everything on first launch
- Claude Code, Gemini CLI, Codex pre-installed
- Drawer menu with wake lock, power mode, sessions
- Welcome animation after setup
- Full Termux API support

## To Install

Just install `MobileCLI-v66.apk` on your Android device.

## To Build (Experimental)

The decompiled Java may need fixes to compile. The working APK is provided for reference.

```bash
./gradlew assembleDebug
```
