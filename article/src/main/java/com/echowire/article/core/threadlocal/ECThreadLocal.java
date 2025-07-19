package com.echowire.article.core.threadlocal;

public class ECThreadLocal {
    public static final ThreadLocal<UserInfo> userInfo = new ThreadLocal<>();

    public static void set(UserInfo info) {
        userInfo.set(info);
    }

    public static void unset() {
        userInfo.remove();
    }

    public static UserInfo get() {
        return userInfo.get();
    }
}
