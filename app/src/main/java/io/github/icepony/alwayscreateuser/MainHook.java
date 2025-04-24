package io.github.icepony.alwayscreateuser;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook extends XposedHelper implements IXposedHookLoadPackage {

    boolean isModuleEnabled;
    private Class<?> userManagerServiceClass;

    private void reloadPreferences() {
        prefs.reload();
        isModuleEnabled = prefs.getBoolean("enable_module", true);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("android")) return;
        log("Handling Android package");
        userManagerServiceClass = findClass("com.android.server.pm.UserManagerService", lpparam.classLoader);
        if (userManagerServiceClass == null)
            return;

        hookUserManager();
    }

    private void hookUserManager() {
        checkAndHook("isCreationOverrideEnabled", true);
        checkAndHook("canAddMoreProfilesToUser", true);
        checkAndHook("canAddMoreManagedProfiles", true);
        checkAndHook("isUserLimitReached", false);
        checkAndHook("isUserLimitReachedLocked", false);
    }

    private void checkAndHook(String methodName, Object result) {
        hookAllMethods(userManagerServiceClass, methodName, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                reloadPreferences();
                if (!isModuleEnabled) {
                    return;
                }

                if (prefs.getBoolean("hook_" + methodName, true)) {
                    log("Bypassing " + methodName);
                    param.setResult(result);
                }
            }
        });
    }

}
