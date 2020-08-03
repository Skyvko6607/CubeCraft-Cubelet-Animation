package me.sky.cubelets;

import me.sky.cubelets.utils.config.InstancedConfig;
import org.bukkit.plugin.Plugin;

public interface ICubeletsPlugin extends Plugin {
    InstancedConfig getMessages();
    InstancedConfig getSettings();
}
