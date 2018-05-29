package com.sshhww.event;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class AddFilterEvent {
    private String packageName;

    public AddFilterEvent(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }
}
