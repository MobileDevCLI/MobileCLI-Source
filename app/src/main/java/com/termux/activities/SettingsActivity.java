package com.termux.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: SettingsActivity.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000e\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0010H\u0002J\u0010\u0010\u0018\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u0019\u001a\u00020\bH\u0002J\u0012\u0010\u001a\u001a\u00020\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0014J\b\u0010\u001d\u001a\u00020\bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/termux/activities/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "properties", "Ljava/util/Properties;", "propertiesFile", "Ljava/io/File;", "addBackKeySetting", "", "container", "Landroid/widget/LinearLayout;", "addBellSetting", "addCursorBlinkSetting", "addCursorStyleSetting", "addDescription", "text", "", "addExternalAppsSetting", "addFullscreenSetting", "addHideKeyboardSetting", "addSaveButton", "addScrollbackSetting", "addSectionHeader", "title", "addTextSizeSetting", "loadProperties", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "saveProperties", "app_devDebug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes7.dex */
public final class SettingsActivity extends AppCompatActivity {
    private Properties properties;
    private File propertiesFile;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(-15066598);
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(1);
        container.setPadding(32, 32, 32, 32);
        scrollView.addView(container);
        setContentView(scrollView);
        setTitle("MobileCLI Settings");
        File termuxDir = new File(new File(getFilesDir(), "home"), ".termux");
        termuxDir.mkdirs();
        this.propertiesFile = new File(termuxDir, "termux.properties");
        loadProperties();
        addSectionHeader(container, "Appearance");
        addFullscreenSetting(container);
        addTextSizeSetting(container);
        addSectionHeader(container, "Keyboard");
        addBackKeySetting(container);
        addHideKeyboardSetting(container);
        addSectionHeader(container, "Terminal");
        addCursorStyleSetting(container);
        addCursorBlinkSetting(container);
        addScrollbackSetting(container);
        addSectionHeader(container, "Bell");
        addBellSetting(container);
        addSectionHeader(container, "External Apps");
        addExternalAppsSetting(container);
        addSaveButton(container);
    }

    private final void loadProperties() {
        this.properties = new Properties();
        try {
            File file = this.propertiesFile;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("propertiesFile");
                file = null;
            }
            if (file.exists()) {
                File file2 = this.propertiesFile;
                if (file2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("propertiesFile");
                    file2 = null;
                }
                FileInputStream fileInputStream = new FileInputStream(file2);
                try {
                    FileInputStream it = fileInputStream;
                    Properties properties = this.properties;
                    if (properties == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("properties");
                        properties = null;
                    }
                    properties.load(it);
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, null);
                } finally {
                }
            }
        } catch (Exception e) {
        }
    }

    private final void saveProperties() {
        try {
            File file = this.propertiesFile;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("propertiesFile");
                file = null;
            }
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            File file2 = this.propertiesFile;
            if (file2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("propertiesFile");
                file2 = null;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                FileOutputStream stream = fileOutputStream;
                Properties properties = this.properties;
                if (properties == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("properties");
                    properties = null;
                }
                properties.store(stream, "MobileCLI Settings");
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                Toast.makeText(this, "Settings saved. Restart app to apply.", 1).show();
            } finally {
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save settings: " + e.getMessage(), 0).show();
        }
    }

    private final void addSectionHeader(LinearLayout container, String title) {
        TextView $this$addSectionHeader_u24lambda_u244 = new TextView(this);
        $this$addSectionHeader_u24lambda_u244.setText(title);
        $this$addSectionHeader_u24lambda_u244.setTextSize(18.0f);
        $this$addSectionHeader_u24lambda_u244.setTextColor(-11751600);
        $this$addSectionHeader_u24lambda_u244.setPadding(0, 32, 0, 16);
        container.addView($this$addSectionHeader_u24lambda_u244);
    }

    private final void addFullscreenSetting(LinearLayout container) {
        Switch $this$addFullscreenSetting_u24lambda_u246 = new Switch(this);
        $this$addFullscreenSetting_u24lambda_u246.setText("Fullscreen Mode");
        $this$addFullscreenSetting_u24lambda_u246.setTextColor(-1);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addFullscreenSetting_u24lambda_u246.setChecked(Intrinsics.areEqual(properties.getProperty("fullscreen", "false"), "true"));
        $this$addFullscreenSetting_u24lambda_u246.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda7
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.addFullscreenSetting$lambda$6$lambda$5(this.f$0, compoundButton, z);
            }
        });
        container.addView($this$addFullscreenSetting_u24lambda_u246);
        addDescription(container, "Hide status bar and navigation");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addFullscreenSetting$lambda$6$lambda$5(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Properties properties = this$0.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        properties.setProperty("fullscreen", isChecked ? "true" : "false");
    }

    private final void addTextSizeSetting(LinearLayout container) {
        final TextView $this$addTextSizeSetting_u24lambda_u247 = new TextView(this);
        $this$addTextSizeSetting_u24lambda_u247.setText("Default Text Size");
        $this$addTextSizeSetting_u24lambda_u247.setTextColor(-1);
        container.addView($this$addTextSizeSetting_u24lambda_u247);
        SeekBar $this$addTextSizeSetting_u24lambda_u248 = new SeekBar(this);
        $this$addTextSizeSetting_u24lambda_u248.setMax(42);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        String property = properties.getProperty("default-text-size", "28");
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        $this$addTextSizeSetting_u24lambda_u248.setProgress((StringsKt.toIntOrNull(property) != null ? r4.intValue() : 28) - 14);
        $this$addTextSizeSetting_u24lambda_u248.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.termux.activities.SettingsActivity$addTextSizeSetting$seekBar$1$1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                $this$addTextSizeSetting_u24lambda_u247.setText("Default Text Size: " + (progress + 14));
                Properties properties2 = this.properties;
                if (properties2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("properties");
                    properties2 = null;
                }
                properties2.setProperty("default-text-size", String.valueOf(progress + 14));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar sb) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar sb) {
            }
        });
        container.addView($this$addTextSizeSetting_u24lambda_u248);
    }

    private final void addBackKeySetting(LinearLayout container) {
        Switch $this$addBackKeySetting_u24lambda_u2410 = new Switch(this);
        $this$addBackKeySetting_u24lambda_u2410.setText("Back Key = Escape");
        $this$addBackKeySetting_u24lambda_u2410.setTextColor(-1);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addBackKeySetting_u24lambda_u2410.setChecked(Intrinsics.areEqual(properties.getProperty("back-key", "back"), "escape"));
        $this$addBackKeySetting_u24lambda_u2410.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda4
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.addBackKeySetting$lambda$10$lambda$9(this.f$0, compoundButton, z);
            }
        });
        container.addView($this$addBackKeySetting_u24lambda_u2410);
        addDescription(container, "Use back button as escape key");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addBackKeySetting$lambda$10$lambda$9(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Properties properties = this$0.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        properties.setProperty("back-key", isChecked ? "escape" : "back");
    }

    private final void addHideKeyboardSetting(LinearLayout container) {
        Switch $this$addHideKeyboardSetting_u24lambda_u2412 = new Switch(this);
        $this$addHideKeyboardSetting_u24lambda_u2412.setText("Hide Keyboard on Startup");
        $this$addHideKeyboardSetting_u24lambda_u2412.setTextColor(-1);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addHideKeyboardSetting_u24lambda_u2412.setChecked(Intrinsics.areEqual(properties.getProperty("hide-soft-keyboard-on-startup", "false"), "true"));
        $this$addHideKeyboardSetting_u24lambda_u2412.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda6
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.addHideKeyboardSetting$lambda$12$lambda$11(this.f$0, compoundButton, z);
            }
        });
        container.addView($this$addHideKeyboardSetting_u24lambda_u2412);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addHideKeyboardSetting$lambda$12$lambda$11(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Properties properties = this$0.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        properties.setProperty("hide-soft-keyboard-on-startup", isChecked ? "true" : "false");
    }

    private final void addCursorStyleSetting(LinearLayout container) {
        final TextView $this$addCursorStyleSetting_u24lambda_u2414 = new TextView(this);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addCursorStyleSetting_u24lambda_u2414.setText("Cursor Style: " + properties.getProperty("terminal-cursor-style", "block"));
        $this$addCursorStyleSetting_u24lambda_u2414.setTextColor(-1);
        $this$addCursorStyleSetting_u24lambda_u2414.setOnClickListener(new View.OnClickListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.addCursorStyleSetting$lambda$14$lambda$13(this.f$0, $this$addCursorStyleSetting_u24lambda_u2414, view);
            }
        });
        container.addView($this$addCursorStyleSetting_u24lambda_u2414);
        addDescription(container, "Tap to cycle: block, underline, bar");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addCursorStyleSetting$lambda$14$lambda$13(SettingsActivity this$0, TextView this_apply, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        String[] styles = {"block", "underline", "bar"};
        Properties properties = this$0.properties;
        Properties properties2 = null;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        String current = properties.getProperty("terminal-cursor-style", "block");
        int currentIndex = ArraysKt.indexOf(styles, current);
        int nextIndex = (currentIndex + 1) % styles.length;
        Properties properties3 = this$0.properties;
        if (properties3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
        } else {
            properties2 = properties3;
        }
        properties2.setProperty("terminal-cursor-style", styles[nextIndex]);
        this_apply.setText("Cursor Style: " + styles[nextIndex]);
    }

    private final void addCursorBlinkSetting(LinearLayout container) {
        Switch $this$addCursorBlinkSetting_u24lambda_u2416 = new Switch(this);
        $this$addCursorBlinkSetting_u24lambda_u2416.setText("Cursor Blink");
        $this$addCursorBlinkSetting_u24lambda_u2416.setTextColor(-1);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        String property = properties.getProperty("terminal-cursor-blink-rate", "500");
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        Integer intOrNull = StringsKt.toIntOrNull(property);
        $this$addCursorBlinkSetting_u24lambda_u2416.setChecked((intOrNull != null ? intOrNull.intValue() : 500) > 0);
        $this$addCursorBlinkSetting_u24lambda_u2416.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda5
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.addCursorBlinkSetting$lambda$16$lambda$15(this.f$0, compoundButton, z);
            }
        });
        container.addView($this$addCursorBlinkSetting_u24lambda_u2416);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addCursorBlinkSetting$lambda$16$lambda$15(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Properties properties = this$0.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        properties.setProperty("terminal-cursor-blink-rate", isChecked ? "500" : "0");
    }

    private final void addScrollbackSetting(LinearLayout container) {
        final TextView $this$addScrollbackSetting_u24lambda_u2417 = new TextView(this);
        Properties properties = this.properties;
        Properties properties2 = null;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addScrollbackSetting_u24lambda_u2417.setText("Scrollback Buffer: " + properties.getProperty("terminal-transcript-rows", "2000") + " lines");
        $this$addScrollbackSetting_u24lambda_u2417.setTextColor(-1);
        container.addView($this$addScrollbackSetting_u24lambda_u2417);
        SeekBar $this$addScrollbackSetting_u24lambda_u2418 = new SeekBar(this);
        int i = 8;
        $this$addScrollbackSetting_u24lambda_u2418.setMax(8);
        Properties properties3 = this.properties;
        if (properties3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
        } else {
            properties2 = properties3;
        }
        String property = properties2.getProperty("terminal-transcript-rows", "2000");
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        Integer intOrNull = StringsKt.toIntOrNull(property);
        int rows = intOrNull != null ? intOrNull.intValue() : 2000;
        if (rows <= 500) {
            i = 0;
        } else if (rows <= 1000) {
            i = 1;
        } else if (rows <= 2000) {
            i = 2;
        } else if (rows <= 4000) {
            i = 3;
        } else if (rows <= 8000) {
            i = 4;
        } else if (rows <= 16000) {
            i = 5;
        } else if (rows <= 32000) {
            i = 6;
        } else if (rows <= 64000) {
            i = 7;
        }
        $this$addScrollbackSetting_u24lambda_u2418.setProgress(i);
        $this$addScrollbackSetting_u24lambda_u2418.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.termux.activities.SettingsActivity$addScrollbackSetting$seekBar$1$1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                List values = CollectionsKt.listOf((Object[]) new Integer[]{500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 100000});
                int value = ((Number) values.get(progress)).intValue();
                $this$addScrollbackSetting_u24lambda_u2417.setText("Scrollback Buffer: " + value + " lines");
                Properties properties4 = this.properties;
                if (properties4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("properties");
                    properties4 = null;
                }
                properties4.setProperty("terminal-transcript-rows", String.valueOf(value));
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar sb) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar sb) {
            }
        });
        container.addView($this$addScrollbackSetting_u24lambda_u2418);
    }

    private final void addBellSetting(LinearLayout container) {
        final TextView $this$addBellSetting_u24lambda_u2420 = new TextView(this);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addBellSetting_u24lambda_u2420.setText("Bell: " + properties.getProperty("bell-character", "vibrate"));
        $this$addBellSetting_u24lambda_u2420.setTextColor(-1);
        $this$addBellSetting_u24lambda_u2420.setOnClickListener(new View.OnClickListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.addBellSetting$lambda$20$lambda$19(this.f$0, $this$addBellSetting_u24lambda_u2420, view);
            }
        });
        container.addView($this$addBellSetting_u24lambda_u2420);
        addDescription(container, "Tap to cycle: vibrate, beep, ignore");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addBellSetting$lambda$20$lambda$19(SettingsActivity this$0, TextView this_apply, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        String[] options = {"vibrate", "beep", "ignore"};
        Properties properties = this$0.properties;
        Properties properties2 = null;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        String current = properties.getProperty("bell-character", "vibrate");
        int currentIndex = ArraysKt.indexOf(options, current);
        int nextIndex = (currentIndex + 1) % options.length;
        Properties properties3 = this$0.properties;
        if (properties3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
        } else {
            properties2 = properties3;
        }
        properties2.setProperty("bell-character", options[nextIndex]);
        this_apply.setText("Bell: " + options[nextIndex]);
    }

    private final void addExternalAppsSetting(LinearLayout container) {
        Switch $this$addExternalAppsSetting_u24lambda_u2422 = new Switch(this);
        $this$addExternalAppsSetting_u24lambda_u2422.setText("Allow External Apps");
        $this$addExternalAppsSetting_u24lambda_u2422.setTextColor(-1);
        Properties properties = this.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        $this$addExternalAppsSetting_u24lambda_u2422.setChecked(Intrinsics.areEqual(properties.getProperty("allow-external-apps", "true"), "true"));
        $this$addExternalAppsSetting_u24lambda_u2422.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SettingsActivity.addExternalAppsSetting$lambda$22$lambda$21(this.f$0, compoundButton, z);
            }
        });
        container.addView($this$addExternalAppsSetting_u24lambda_u2422);
        addDescription(container, "Required for Claude Code OAuth and URL opening");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addExternalAppsSetting$lambda$22$lambda$21(SettingsActivity this$0, CompoundButton compoundButton, boolean isChecked) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Properties properties = this$0.properties;
        if (properties == null) {
            Intrinsics.throwUninitializedPropertyAccessException("properties");
            properties = null;
        }
        properties.setProperty("allow-external-apps", isChecked ? "true" : "false");
    }

    private final void addDescription(LinearLayout container, String text) {
        TextView $this$addDescription_u24lambda_u2423 = new TextView(this);
        $this$addDescription_u24lambda_u2423.setText(text);
        $this$addDescription_u24lambda_u2423.setTextSize(12.0f);
        $this$addDescription_u24lambda_u2423.setTextColor(-7829368);
        $this$addDescription_u24lambda_u2423.setPadding(0, 0, 0, 16);
        container.addView($this$addDescription_u24lambda_u2423);
    }

    private final void addSaveButton(LinearLayout container) {
        Button $this$addSaveButton_u24lambda_u2425 = new Button(this);
        $this$addSaveButton_u24lambda_u2425.setText("Save Settings");
        $this$addSaveButton_u24lambda_u2425.setBackgroundColor(-11751600);
        $this$addSaveButton_u24lambda_u2425.setTextColor(-1);
        $this$addSaveButton_u24lambda_u2425.setPadding(32, 16, 32, 16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.topMargin = 48;
        $this$addSaveButton_u24lambda_u2425.setLayoutParams(params);
        $this$addSaveButton_u24lambda_u2425.setOnClickListener(new View.OnClickListener() { // from class: com.termux.activities.SettingsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SettingsActivity.addSaveButton$lambda$25$lambda$24(this.f$0, view);
            }
        });
        container.addView($this$addSaveButton_u24lambda_u2425);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void addSaveButton$lambda$25$lambda$24(SettingsActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveProperties();
    }
}
