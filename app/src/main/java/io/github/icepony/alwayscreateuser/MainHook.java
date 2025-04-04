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

    public static void log(String message) {
//        if (BuildConfig.DEBUG)
        XposedBridge.log("D/" + MainHook.TAG + ": " + message);
    }

    public static void logError(String message, Throwable t) {
        XposedBridge.log("E/" + MainHook.TAG + ": " + message + "\n" + Log.getStackTraceString(t));
    }

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            return XposedHelpers.findClass(className, classLoader);
        } catch (Throwable e) {
            logError("Cannot find Class: " + className, e);
        }
        return null;
    }

    public static void hookAllMethods(String className, ClassLoader classLoader, String methodName, XC_MethodHook callback) {
        try {
            Class<?> clazz = findClass(className, classLoader);
            XposedBridge.hookAllMethods(clazz, methodName, callback);
        } catch (Throwable e) {
            logError("Cannot hook method: " + methodName, e);
        }
    }

    private void hookMethod(XC_LoadPackage.LoadPackageParam lpparam, String methodName, Object result, int minSdk, int maxSdk) {
        if (Build.VERSION.SDK_INT < minSdk || Build.VERSION.SDK_INT > maxSdk) {
            log("Skipping " + methodName + " for SDK " + Build.VERSION.SDK_INT);
            return;
        }

        hookAllMethods("com.android.server.pm.UserManagerService", lpparam.classLoader, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                log("Bypassing " + methodName);
                param.setResult(result);
            }
        });
        log("Successfully hooked " + methodName);
    }
}
