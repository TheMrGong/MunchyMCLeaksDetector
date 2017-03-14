package me.gong.hiddenbot.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.lang.reflect.Field;

public class SessionUtils {

    private static Field sessionField;

    static {
        for (Field field : Minecraft.class.getDeclaredFields()) {
            if(field.getType().equals(Session.class)) {
                sessionField = field;
                sessionField.setAccessible(true);
            }
        }
    }

    public static Session getSession() {
        try {
            return (Session) sessionField.get(Minecraft.getMinecraft());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
