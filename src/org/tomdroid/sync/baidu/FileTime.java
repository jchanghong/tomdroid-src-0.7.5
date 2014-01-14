package org.tomdroid.sync.baidu;

import android.util.Log;

import java.util.*;

/**
 * Created by changhong on 14-1-14.
 */
public class FileTime {
    public static String tag = "AAAAAAAAAA";


    public static void ll(String info) {
        Log.i(tag, info);
    }

    public static List<Map<String, Object>> getReplacelocal() {
        ll("getre local");

        replacelocal.clear();
        HashMap<String, Object> map;
        for (String file : allfilelUUL) {
            if (localsUUL.contains(file) && remotlUUL.contains(file) && newer(getremolname(file), getlocalname(file))) {
                map = new HashMap<String, Object>();
                map.put(LOCAL, getlocalname(file));
                map.put(REMOT, getremolname(file));
                replacelocal.add(map);
            }
        }
        return replacelocal;

    }

    public static List<Map<String, Object>> getReplaceremot() {
        ll("getre remo");
        replaceremot.clear();

        HashMap<String, Object> map;
        for (String file : allfilelUUL) {
            if (localsUUL.contains(file) && remotlUUL.contains(file) && newer(getlocalname(file), getremolname(file))) {
                map = new HashMap<String, Object>();
                map.put(LOCAL, getlocalname(file));
                map.put(REMOT, getremolname(file));
                replaceremot.add(map);
            }
        }
        return replaceremot;
    }

    private static String getlocalname(String uul) {
        int i = localsUUL.indexOf(uul);
        return
                locals.get(i);
    }

    private static String getremolname(String uul) {
        int i = remotlUUL.indexOf(uul);
        return remotlUUL.get(i);
    }

    public static List<String> getPushable() {
        ll("getpushable");

        pushable.clear();
        for (String e : localsUUL) {
            if (!remotlUUL.contains(e)) {
                pushable.add(uulToName(e));
            }
        }
        return pushable;


    }

    public static List<String> getPullable() {
        ll("getPullable();");
        pullable.clear();
        for (String e : remotlUUL) {
            if (!localsUUL.contains(e)) {
                pullable.add(uulToName(e));
            }
        }
        return pullable;


    }

    private static boolean newer(String filelocal, String locaremot) {

        return nametoLong(filelocal) > nametoLong(locaremot);
    }

    private static long nametoLong(String name) {
        int i = name.lastIndexOf("_");
        int j = name.lastIndexOf(".");

        return Long.valueOf(name.substring(i + 1, j));

    }

    private static String nameToUUL(String name) {
        String res;
        int index = name.lastIndexOf("_");
        res = name.substring(0, index);
        res = res + name.substring(name.lastIndexOf("."), name.length());
        return res;
    }

    private static String uulToName(String uul) {
        String res;
        int index = uul.lastIndexOf(".");
        res = uul.substring(0, index);
        uul = res + "_" + String.valueOf(System.currentTimeMillis()) + ".note";
        return uul;
    }

    public static void init() {

        localsUUL.clear();
        remotlUUL.clear();
        for (String lo : locals) {
            locals.add(nameToUUL(lo));
        }
        for (String re : remotl) {
            remotlUUL.add(nameToUUL(re));
        }
        allfilelUUL.addAll(localsUUL);
        allfilelUUL.addAll(remotlUUL);
    }

    public static final String LOCAL = "local";
    public static final String REMOT = "remot";
    private static List<Map<String, Object>> replacelocal = new ArrayList<Map<String, Object>>();
    private static List<Map<String, Object>> replaceremot = new ArrayList<Map<String, Object>>();
    private static List<String> pushable = new ArrayList<String>();
    private static List<String> pullable = new ArrayList<String>();
    private static List<String> locals = FileUtil.localfilestring;
    private static List<String> remotl = FileUtil.remotefilestrin;
    private static List<String> localsUUL = new ArrayList<String>();
    private static List<String> remotlUUL = new ArrayList<String>();
    private static Set<String> allfilelUUL = new HashSet<String>();


}
