package com.sshhww.utils;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.sshhww.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class AppUtil {

    public static List<AppInfo> getAppPackageInfo(Context context) {
        if (context == null) return null;
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        if (packages != null) {
            List<AppInfo> appPackageInfos = new ArrayList<>();
            for (int i = 0; i < packages.size(); i++) {
                AppInfo appPackageInfo = new AppInfo();
                PackageInfo packageInfo = packages.get(i);
                appPackageInfo.setAppName(packageInfo.applicationInfo.loadLabel(
                        context.getPackageManager()).toString());
                appPackageInfo.setPackageName(packageInfo.packageName);
                appPackageInfo.setVersionName(packageInfo.versionName);
                appPackageInfo.setVersionCode(packageInfo.versionCode);
                appPackageInfos.add(appPackageInfo);
            }
            return appPackageInfos;
        }
        else {
            return null;
        }
    }
}
