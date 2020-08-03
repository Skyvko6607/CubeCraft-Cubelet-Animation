package me.sky.cubelets;

import com.google.gson.Gson;
import me.sky.cubelets.cubelet.CubeletsManager;
import me.sky.cubelets.database.Database;
import me.sky.cubelets.utils.config.PluginConfig;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public interface ICubeletsPlugin extends Plugin {
    CubeletsManager getCubeletManager();
    PluginConfig getMessages();
    PluginConfig getSettings();
    PluginConfig getDatabaseSettings();
    Database getDatabase();
    Random getRandom();
    Gson getGson();
}
