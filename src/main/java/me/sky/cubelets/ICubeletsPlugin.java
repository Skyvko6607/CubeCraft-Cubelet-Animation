package me.sky.cubelets;

import com.google.gson.Gson;
import me.sky.cubelets.cubelet.CubeletsManager;
import me.sky.cubelets.location.CubeletLocationManager;
import me.sky.cubelets.utils.config.PluginConfig;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public interface ICubeletsPlugin extends Plugin {
    CubeletsManager getCubeletManager();
    CubeletLocationManager getCubeletLocationManager();
    PluginConfig getMessages();
    PluginConfig getSettings();
    Random getRandom();
    Gson getGson();
}
