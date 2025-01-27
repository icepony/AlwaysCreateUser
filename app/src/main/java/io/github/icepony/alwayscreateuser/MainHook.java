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
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("android")) return;
        XposedBridge.log("D/" + TAG + " handleLoadPackage");

        hook(lpparam);
    }

    private void hook_isCreationOverrideEnabled(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                "com.android.server.pm.UserManagerService",
                lpparam.classLoader,
                "isCreationOverrideEnabled",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        XposedBridge.log("D/" + TAG + " isCreationOverrideEnabled call");
                        param.setResult(true);
                    }
                }
        );
    }

    private void hook_canAddMoreProfilesToUser(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                "com.android.server.pm.UserManagerService",
                lpparam.classLoader,
                "canAddMoreProfilesToUser",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        XposedBridge.log("D/" + TAG + " canAddMoreProfilesToUser call");
                        param.setResult(true);
                    }
                }
        );
    }

    private void hook_canAddMoreManagedProfiles(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                "com.android.server.pm.UserManagerService",
                lpparam.classLoader,
                "canAddMoreManagedProfiles",
                int.class,
                boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        XposedBridge.log("D/" + TAG + " canAddMoreManagedProfiles call");
                        param.setResult(true);
                    }
                }
        );
    }

    private void hook_isUserLimitReached(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                "com.android.server.pm.UserManagerService",
                lpparam.classLoader,
                "isUserLimitReached",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        XposedBridge.log("D/" + TAG + " isUserLimitReached call");
                        param.setResult(false);
                    }
                }
        );
    }

    private void hook_isUserLimitReachedLocked(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookMethod(
                "com.android.server.pm.UserManagerService",
                lpparam.classLoader,
                "isUserLimitReachedLocked",
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) {
                        XposedBridge.log("D/" + TAG + " isUserLimitReachedLocked call");
                        param.setResult(false);
                    }
                }
        );
    }

    private void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            hook_isCreationOverrideEnabled(lpparam);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) { // Android 11 - 13
            hook_canAddMoreProfilesToUser(lpparam);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) { // Android 6 - 10
            hook_canAddMoreManagedProfiles(lpparam);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) { // Android 7 - 13
            hook_isUserLimitReached(lpparam);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) { // Android 4.2 - 6
            hook_isUserLimitReachedLocked(lpparam);
        }
    }
}
