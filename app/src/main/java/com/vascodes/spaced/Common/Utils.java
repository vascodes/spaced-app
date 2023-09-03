package com.vascodes.spaced.Common;

public class Utils {
    public static boolean isStringEmpty(String str){
        str = str.trim();
        return str.isEmpty() || str.equals("");
    }
}
