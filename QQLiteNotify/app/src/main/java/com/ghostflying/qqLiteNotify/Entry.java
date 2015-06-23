package com.ghostflying.qqLiteNotify;

import android.app.Notification;
import android.support.v4.app.NotificationCompat;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Ghost on 6/23/2015.
 */
public class Entry implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!"com.tencent.qqlite".equals(loadPackageParam.packageName)){
            return;
        }

        try{
            XposedHelpers.findAndHookMethod("android.app.Notification.Builder", loadPackageParam.classLoader, "build", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Notification.Builder builder = (Notification.Builder)param.thisObject;
                    builder.setOngoing(false);
                }
            });
        }
        catch (Throwable t){
            XposedBridge.log(t);
        }

        // not use support package
//        try{
//            XposedHelpers.findAndHookMethod("android.support.v4.app.NotificationCompat.Builder", loadPackageParam.classLoader, "build", new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    NotificationCompat.Builder builder = (NotificationCompat.Builder)param.thisObject;
//                    builder.setOngoing(false);
//                }
//            });
//        }
//        catch (Throwable t){
//            XposedBridge.log(t);
//        }
    }
}
