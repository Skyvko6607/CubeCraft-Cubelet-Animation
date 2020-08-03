package me.sky.cubelets.utils;

import net.md_5.bungee.api.ChatColor;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinecraftUtils {
    public static String translate(String message) {
        final Pattern pattern = Pattern.compile("#<(.{6})>", Pattern.DOTALL);
        final Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            message = message.replace("#<" + matcher.group(1) + '>', ChatColor.of('#' + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String roundToNDigits(double value) {
        return new DecimalFormat("#.#").format(value);
    }
}
