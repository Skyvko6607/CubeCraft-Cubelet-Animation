package me.sky.cubelets;

import me.sky.cubelets.utils.config.InstancedConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements ICubeletsPlugin {

    private InstancedConfig settings, messages;

    @Override
    public void onEnable() {
        this.settings = new InstancedConfig("config.yml", this);
        this.messages = new InstancedConfig("messages.yml", this);
    }

    @Override
    public InstancedConfig getMessages() {
        return messages;
    }

    @Override
    public InstancedConfig getSettings() {
        return settings;
    }
}
