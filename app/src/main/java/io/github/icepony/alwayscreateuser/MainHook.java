package io.github.icepony.alwayscreateuser;

import android.os.Build;
import android.util.Log;

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
        log("Handling Android package");
        hook(lpparam);
    }

    private void hook(XC_LoadPackage.LoadPackageParam lpparam) {
        // Method name, result, min SDK, max SDK
        hookMethod(lpparam, "isCreationOverrideEnabled", true, Build.VERSION_CODES.UPSIDE_DOWN_CAKE, Integer.MAX_VALUE);
        hookMethod(lpparam, "canAddMoreProfilesToUser", true, Build.VERSION_CODES.R, Build.VERSION_CODES.TIRAMISU);
        hookMethod(lpparam, "canAddMoreManagedProfiles", true, Build.VERSION_CODES.M, Build.VERSION_CODES.Q);
        hookMethod(lpparam, "isUserLimitReached", false, Build.VERSION_CODES.N, Build.VERSION_CODES.TIRAMISU);
        hookMethod(lpparam, "isUserLimitReachedLocked", false, Build.VERSION_CODES.JELLY_BEAN_MR1, Build.VERSION_CODES.M);
    }

    private void hookMethod(XC_LoadPackage.LoadPackageParam lpparam, String methodName, Object result, int minSdk, int maxSdk) {
        if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk) {
            log("Skipping " + methodName + " for SDK " + Build.VERSION.SDK_INT);
            return;
        }

        try {
            Class<?> clazz = XposedHelpers.findClass("com.android.server.pm.UserManagerService", lpparam.classLoader);
            XposedBridge.hookAllMethods(clazz, methodName, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) {
                    log("Bypassing " + methodName);
                    param.setResult(result);
                }
            });
            log("Successfully hooked " + methodName);
        } catch (Throwable t) {
            logError("Failed to hook " + methodName, t);
        }
    }

    private void log(String message) {
        XposedBridge.log("D/" + TAG + ": " + message);
    }

    private void logError(String message, Throwable t) {
        XposedBridge.log("E/" + TAG + ": " + message + "\n" + Log.getStackTraceString(t));
    }
}