package me.gong.hiddenbot.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.regex.Pattern;

/*=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~
 =~ Copyright TheMrGong (c) 2015 - 2016. All Rights Reserved.
 =~ Any code contained within this java file is the sole property of TheMrGong.
 =~ You may not distribute, take snippets, reproduce, or claim any code
 =~ as your own. Doing so will result in void with any agreements with you.
 =~ Stop flopping around.
 =~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~=~*/

public enum ChatColor {
    BLACK("BLACK", 0, '0', 0),
    DARK_BLUE("DARK_BLUE", 1, '1', 1),
    DARK_GREEN("DARK_GREEN", 2, '2', 2),
    DARK_AQUA("DARK_AQUA", 3, '3', 3),
    DARK_RED("DARK_RED", 4, '4', 4),
    DARK_PURPLE("DARK_PURPLE", 5, '5', 5),
    GOLD("GOLD", 6, '6', 6),
    GRAY("GRAY", 7, '7', 7),
    DARK_GRAY("DARK_GRAY", 8, '8', 8),
    BLUE("BLUE", 9, '9', 9),
    GREEN("GREEN", 10, 'a', 10),
    AQUA("AQUA", 11, 'b', 11),
    RED("RED", 12, 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 13, 'd', 13),
    YELLOW("YELLOW", 14, 'e', 14),
    WHITE("WHITE", 15, 'f', 15),
    MAGIC("MAGIC", 16, 'k', 16, true),
    BOLD("BOLD", 17, 'l', 17, true),
    STRIKETHROUGH("STRIKETHROUGH", 18, 'm', 18, true),
    UNDERLINE("UNDERLINE", 19, 'n', 19, true),
    ITALIC("ITALIC", 20, 'o', 20, true),
    RESET("RESET", 21, 'r', 21);
    public static final char COLOR_CHAR = '\u00a7';
    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");
    private static final Map<Integer, ChatColor> BY_ID = Maps.newHashMap();
    private static final Map<Character, ChatColor> BY_CHAR = Maps.newHashMap();

    static {
        ChatColor[] var0 = values();

        for (ChatColor color : var0) {
            BY_ID.put(color.intCode, color);
            BY_CHAR.put(color.code, color);
        }
    }

    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;

    private ChatColor(String var1, int var2, char code, int intCode) {
        this(var1, var2, code, intCode, false);
    }

    private ChatColor(String var1, int var2, char code, int intCode, boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[]{'\u00a7', code});
    }

    public static ChatColor getByChar(char code) {
        return (ChatColor) BY_CHAR.get(code);
    }

    public static ChatColor getByChar(String code) {
        Validate.notNull(code, "Code cannot be null", new Object[0]);
        Validate.isTrue(code.length() > 0, "Code must have at least one char", new Object[0]);
        return (ChatColor) BY_CHAR.get(code.charAt(0));
    }

    public static String stripColor(String input) {
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();

        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static String getLastColors(String input) {
        String result = "";
        int length = input.length();

        for (int index = length - 1; index > -1; --index) {
            char section = input.charAt(index);

            if (section == 167 && index < length - 1) {
                char c = input.charAt(index + 1);
                ChatColor color = getByChar(c);

                if (color != null) {
                    result = color.toString() + result;

                    if (color.isColor() || color.equals(RESET)) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public char getChar() {
        return this.code;
    }

    public String toString() {
        return this.toString;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }
}
