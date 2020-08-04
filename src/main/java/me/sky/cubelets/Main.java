package me.sky.cubelets;

import com.google.gson.Gson;
import me.sky.cubelets.commands.CubeCommand;
import me.sky.cubelets.commands.list.CubeletCommand;
import me.sky.cubelets.cubelet.CubeletsManager;
import me.sky.cubelets.database.Database;
import me.sky.cubelets.database.SQLTableCreate;
import me.sky.cubelets.utils.config.PluginConfig;
import me.sky.cubelets.utils.menu.IMenuHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Random;

public class Main extends JavaPlugin implements ICubeletsPlugin {

    private CubeletsManager cubeletsManager;
    private PluginConfig settings, messages, databaseSettings;
    private Random random;
    private Gson gson;
    private Database database;

    @Override
    public void onEnable() {
        this.random = new Random();
        this.gson = new Gson();
        new IMenuHandler(this);
        this.settings = new PluginConfig("config.yml", this);
        this.messages = new PluginConfig("messages.yml", this);
        this.databaseSettings = new PluginConfig("database-settings.yml", this);
        this.database = new Database(this);
        runDatabaseCheck();
        this.cubeletsManager = new CubeletsManager(this);
        registerCommands(new CubeletCommand("cubelet", this));

    }

    private void registerCommands(CubeCommand... commands) {
        for (CubeCommand cmd : commands) {
            getCommand(cmd.getCommand()).setExecutor(cmd);
        }
    }

    private void runDatabaseCheck() {
        try {
            new SQLTableCreate().execute("player_data", this);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public CubeletsManager getCubeletManager() {
        return cubeletsManager;
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
    public PluginConfig getDatabaseSettings() {
        return databaseSettings;
    }

    @Override
    public Database getDatabase() {
        return database;
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
