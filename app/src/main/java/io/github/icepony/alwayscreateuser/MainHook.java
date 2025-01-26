package io.github.icepony.alwayscreateuser;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {
    public static final String TAG = "AlwaysCreateUser";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("android")) return;
        XposedBridge.log("D/" + TAG + " handleLoadPackage");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            XposedHelpers.findAndHookMethod(
                    "com.android.server.pm.UserManagerService",
                    lpparam.classLoader,
                    "isCreationOverrideEnabled",
                    new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(true);
                        }
                    }
            );
        }
    }
}