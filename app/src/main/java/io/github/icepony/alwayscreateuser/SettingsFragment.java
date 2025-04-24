package io.github.icepony.alwayscreateuser;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.View;

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private SwitchPreference mEnableModuleSwitch;
    private PreferenceCategory mSettingsPreferenceCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.prefs);

        mEnableModuleSwitch = (SwitchPreference) findPreference("enable_module");
        mSettingsPreferenceCategory = (PreferenceCategory) findPreference("settings");

        updatePreferenceEnabledStates(mEnableModuleSwitch.isChecked());

        mEnableModuleSwitch.setOnPreferenceChangeListener(this);
    }

    private void updatePreferenceEnabledStates(boolean moduleEnabled) {
        if (!BuildConfig.DEBUG) {
            if (mSettingsPreferenceCategory != null) {
                mSettingsPreferenceCategory.setEnabled(moduleEnabled);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (!(newValue instanceof Boolean)) {
            return false;
        }
        boolean isEnabled = (Boolean) newValue;
        String key = preference.getKey();

        switch (key) {
            case "enable_module":
                updatePreferenceEnabledStates(isEnabled);
                return true;

            default:
                return true;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Adjust the top distance to avoid conflict with the ActionBar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setOnApplyWindowInsetsListener((v, insets) -> {
                view.setPadding(insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getStableInsetBottom());

                return insets.consumeSystemWindowInsets();
            });
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mEnableModuleSwitch != null) {
            updatePreferenceEnabledStates(mEnableModuleSwitch.isChecked());
        }
    }
}