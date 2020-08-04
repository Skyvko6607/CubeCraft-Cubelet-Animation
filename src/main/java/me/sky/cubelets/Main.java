package me.sky.cubelets;

import com.google.gson.Gson;
import me.sky.cubelets.commands.CubeCommand;
import me.sky.cubelets.commands.list.CubeletCommand;
import me.sky.cubelets.cubelet.CubeletsManager;
import me.sky.cubelets.listener.CubeletBlockListener;
import me.sky.cubelets.listener.CubeletEntityListener;
import me.sky.cubelets.location.CubeletLocationManager;
import me.sky.cubelets.utils.config.PluginConfig;
import me.sky.cubelets.utils.menu.IMenuHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Main extends JavaPlugin implements ICubeletsPlugin {

    private CubeletsManager cubeletsManager;
    private CubeletLocationManager cubeletLocationManager;
    private PluginConfig settings, messages;
    private Random random;
    private Gson gson;

    @Override
    public void onEnable() {
        this.random = new Random();
        this.gson = new Gson();
        new IMenuHandler(this);
        this.settings = new PluginConfig("config.yml", this);
        this.messages = new PluginConfig("messages.yml", this);
        this.cubeletsManager = new CubeletsManager(this);
        this.cubeletLocationManager = new CubeletLocationManager(this);
        registerCommands(new CubeletCommand("cubelet", this));
        new CubeletBlockListener(this);
        new CubeletEntityListener(this);
    }

    private void registerCommands(CubeCommand... commands) {
        for (CubeCommand cmd : commands) {
            getCommand(cmd.getCommand()).setExecutor(cmd);
        }
    }

    @Override
    public CubeletsManager getCubeletManager() {
        return cubeletsManager;
    }

    @Override
    public CubeletLocationManager getCubeletLocationManager() {
        return cubeletLocationManager;
    }

    @Override
    public PluginConfig getMessages() {
        return messages;
    }

    @Override
    public PluginConfig getSettings() {
        return settings;
    }

    @Override
    public Random getRandom() {
        return random;
    }

    @Override
    public Gson getGson() {
        return gson;
    }
}
