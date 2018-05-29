package com.sshhww.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class GsonUtil {
    private static volatile Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    public static String toJson(Object object) {
        try {
            return getGson().toJson(object);
        } catch (Exception e) {
            LogUtil.w(e.getMessage());
        }
        return null;
    }

    public static <T> List<T> jsonToList(String content, Class<T> cls) {
        List<T> list = new ArrayList<>();
        if (!TextUtils.isEmpty(content)) {
            try {
                JsonArray arry = new JsonParser().parse(content).getAsJsonArray();
                for (JsonElement jsonElement : arry) {
                    list.add(getGson().fromJson(jsonElement, cls));
                }
            } catch (Exception e) {
                LogUtil.w(e.getMessage());
            }
        }
        return list;
    }

    public static <T> T jsonToObject(String content, Class<T> cls) {
        if (TextUtils.isEmpty(content)) return null;
        try {
            return getGson().fromJson(content, cls);
        } catch (Exception e) {
            LogUtil.w(e.getMessage());
        }
        return null;
    }
}
