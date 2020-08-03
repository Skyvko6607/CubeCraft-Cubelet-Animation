package me.sky.cubelets.data;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.Manager;
import me.sky.cubelets.database.SQLSavePlayerData;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class DataManager extends Manager<ICubeletsPlugin> {
    private final Map<UUID, PlayerData> datas = new HashMap<>();

    public DataManager(ICubeletsPlugin plugin) {
        super(plugin);
    }

    public Map<UUID, PlayerData> getDatas() {
        return datas;
    }

    public void load(UUID uuid) {

    }

    public void save(PlayerData data) {
        try {
            new SQLSavePlayerData().execute(data, getPlugin());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            getPlugin().getLogger().log(Level.SEVERE, "Failed to save player data for player UUID -> " + data.getUuid().toString());
        }
    }
}
