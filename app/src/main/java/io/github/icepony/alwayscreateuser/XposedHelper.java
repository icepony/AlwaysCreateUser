package io.github.icepony.alwayscreateuser;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedHelper {
    public static final String TAG = "AlwaysCreateUser";
    public static XSharedPreferences prefs = new XSharedPreferences(BuildConfig.APPLICATION_ID, BuildConfig.APPLICATION_ID + "_preferences");

    public static Field findField(Class<?> clazz, String fieldName) {
        try {
            Field field = XposedHelpers.findField(clazz, fieldName);
            log("Successfully found field on class " + clazz.getName() + ": " + fieldName);
            return field;
        } catch (Throwable e) {
            logError("Error finding field on class " + clazz.getName() + ": " + fieldName, e);
        }
        return null;
    }

    public static XC_MethodHook.Unhook hookMethod(Member hookMethod, XC_MethodHook callback) {
        try {
            XC_MethodHook.Unhook unhook = XposedBridge.hookMethod(hookMethod, callback);
            log("Successfully added hook for " + hookMethod);
            return unhook;
        } catch (Throwable e) {
            logError("Error hook method: " + hookMethod, e);
        }
        return null;
    }

    public static XC_MethodHook.Unhook findAndHookMethod(String className, ClassLoader classLoader, String methodName, Object... parameterTypesAndCallback) {
        try {
            XC_MethodHook.Unhook andHookMethod = XposedHelpers.findAndHookMethod(className, classLoader, methodName, parameterTypesAndCallback);
            log("Successfully added hook for " + className + "#" + methodName);
            return andHookMethod;
        } catch (Throwable e) {
            logError("Error hook method: " + className + "#" + methodName, e);
        }
        return null;
    }

    public static Set<XC_MethodHook.Unhook> hookAllMethods(Class<?> hookClass, String methodName, XC_MethodHook callback) {
        try {
            Set<XC_MethodHook.Unhook> unhooks = XposedBridge.hookAllMethods(hookClass, methodName, callback);
            if (unhooks.isEmpty()) {
                log("No hooks found for " + hookClass.getName() + "#" + methodName);
                return null;
            }
            log("Successfully added hook for " + hookClass.getName() + "#" + methodName + " with " + unhooks.size() + " hooks.");
            return unhooks;
        } catch (Throwable e) {
            logError("Error hook method: " + hookClass.getName() + "#" + methodName, e);
        }
        return null;
    }

    public static Class<?> findClass(String className, ClassLoader classLoader) {
        try {
            Class<?> aClass = XposedHelpers.findClass(className, classLoader);
            log("Successfully found class: " + className);
            return aClass;
        } catch (Throwable e) {
            logError("Error finding class: " + className, e);
        }
        return null;
    }

    public static void logError(String message, Throwable t) {
        XposedBridge.log("E/" + TAG + ": " + message + "\n" + Log.getStackTraceString(t));
    }

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            XposedBridge.log("D/" + TAG + ": " + message);
        }
    }
}
