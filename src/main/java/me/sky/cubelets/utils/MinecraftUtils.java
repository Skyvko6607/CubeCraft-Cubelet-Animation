package me.sky.cubelets.utils;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.List;
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

    public static List<String> translate(List<String> message) {
        message.replaceAll(MinecraftUtils::translate);
        return message;
    }

    public static String roundToNDigits(double value) {
        return new DecimalFormat("#.#").format(value);
    }

    public static Location readConfigLocation(String loc) {
        String[] s = loc.split(";");
        World world = Bukkit.getWorld(s[0]);
        double x = Double.parseDouble(s[1]), y = Double.parseDouble(s[2]), z = Double.parseDouble(s[3]);
        float yaw = 0, pitch = 0;
        if (s.length == 6) {
            yaw = Float.parseFloat(s[4]);
            yaw = Float.parseFloat(s[5]);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String centerText(String text) {
        int maxWidth = 80, spaces = (int) Math.round((maxWidth - 1.4 * ChatColor.stripColor(text).length()) / 2);
        return StringUtils.repeat(" ", spaces) + text;
    }

    public static void scheduleLater(ITaskSchedule taskSchedule, int delay, Plugin plugin) {
        new BukkitRunnable() {
            @Override
            public void run() {
                taskSchedule.run();
            }
        }.runTaskLater(plugin, delay);
    }

    public static void lookAtLocation(Entity source, Location target) {
        Location changed = getLookAtLocation(source, target);
        source.setRotation(changed.getYaw(), changed.getPitch());
    }

    public static Location getLookAtLocation(Entity source, Location target) {
        Vector direction = source.getLocation().toVector().subtract(target.toVector()).normalize();
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();

        Location changed = source.getLocation().clone();
        changed.setYaw(180 - (float) Math.toDegrees(Math.atan2(x, z)));
        changed.setPitch(90 - (float) Math.toDegrees(Math.acos(y)));
        return changed;
    }
}
