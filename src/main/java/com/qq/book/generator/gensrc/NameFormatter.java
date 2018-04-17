package com.qq.book.generator.gensrc;

import org.apache.commons.lang.StringUtils;

public class NameFormatter {

    public static String toJava(String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            throw new NullPointerException("");
        }

        StringBuilder result = new StringBuilder();
        char[] cs = name.toCharArray();
        result.append(Character.toLowerCase(cs[0]));
        result.append(name.substring(1));
        return result.toString();
    }

    public static String toDB(String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            throw new NullPointerException("");
        }

        StringBuilder result = new StringBuilder();
        char[] cs = name.toCharArray();
        result.append(Character.toLowerCase(cs[0]));
        for (int i = 1; i < cs.length; i++) {
            if (Character.isUpperCase(cs[i])) {
                result.append("_");
                result.append(Character.toLowerCase(cs[i]));
            } else {
                result.append(cs[i]);
            }
        }
        return result.toString();
    }
}
