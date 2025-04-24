package io.github.icepony.alwayscreateuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getSharedPreferences(BuildConfig.APPLICATION_ID + "_preferences", Context.MODE_WORLD_READABLE);
        } catch (SecurityException ignored) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.module_not_loaded)
                    .setMessage(R.string.will_not_save)
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, new SettingsFragment())
                    .commit();
        }
    }
}