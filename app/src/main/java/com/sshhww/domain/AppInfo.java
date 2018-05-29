package com.sshhww.domain;

import android.support.annotation.NonNull;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class AppInfo implements Comparable<AppInfo> {
    private Boolean isSelect;
    /**
     * app应用名称
     */
    private String appName;
    /**
     * 应用包名称
     */
    private String packageName;
    /**
     * 外部版本号
     */
    private String versionName;
    /**
     * 内部版本号
     */
    private Integer versionCode;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public Boolean getSelect() {
        if (isSelect == null) return false;
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }

    @Override
    public int compareTo(@NonNull AppInfo o) {
        if (isSelect != null && isSelect) return -1;
        if(o.getSelect() != null && o.getSelect()) return -1;
        // null || false
        return 1;
    }
}
