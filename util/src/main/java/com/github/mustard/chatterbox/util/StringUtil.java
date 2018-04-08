package com.github.mustard.chatterbox.util;

public class StringUtil {

    public static String trimToEmpty(String input) {
        return (input == null) ? "" : input.trim();
    }

}
