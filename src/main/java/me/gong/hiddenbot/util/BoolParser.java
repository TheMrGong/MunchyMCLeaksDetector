package me.gong.hiddenbot.util;

import java.util.stream.Stream;

public final class BoolParser {

    private static final String[] ENABLE = new String[]{"enable", "true", "begin", "start", "1"},
            DISABLE = new String[]{"disable", "false", "stop", "end", "0"};

    public static Boolean parseString(String s) {
        if(Stream.of(ENABLE).anyMatch(b -> b.equalsIgnoreCase(s)))
            return true;
        if(Stream.of(DISABLE).anyMatch(b -> b.equalsIgnoreCase(s))) return false;
        return null;
    }
}
