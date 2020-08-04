package me.sky.cubelets.data;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.Manager;
import me.sky.cubelets.database.SQLLoadPlayerData;
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
        datas.put(uuid, null);
        try {
            new SQLLoadPlayerData() {
                @Override
                public void finish(PlayerData data) {
                    if (data == null) {
                        data = new PlayerData(uuid, new ArrayList<>());
                        datas.put(uuid, data);
                        save(data);
                    } else {
                        datas.put(uuid, data);
                    }
                }
            }.execute(uuid, getPlugin());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
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
