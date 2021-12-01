package com.abubakar.i180449_i180564;

import android.app.Application;

public class Id extends Application {
    private static String id;
    private static String path;
    private static String ip;

    public static String getId() {
        return id;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Id.ip = ip;
    }

    public static void setId(String id) {
        Id.id = id;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Id.path = path;
    }
}
