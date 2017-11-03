package com.t1_network.core.app;

/**
 * Created by David on 15/10/20.
 */
public class Build {

    public final static boolean isDev = false;

    public final static String HOST = isDev ? "http://dev.t1-network.com/" : "http://api.tyijian.com/";

    private static String dbPath = null;//数据库路径

    public static String SP_PATH = "taiyi";

}
