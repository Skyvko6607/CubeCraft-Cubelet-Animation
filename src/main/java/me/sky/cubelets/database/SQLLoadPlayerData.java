package me.sky.cubelets.database;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.data.CubeletData;
import me.sky.cubelets.data.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class SQLLoadPlayerData extends SQLScheduler<UUID> {
    public void run(UUID uuid, Connection connection, ICubeletsPlugin plugin) {
        try {
            String table = plugin.getDatabaseSettings().getConfig().getString("PlayerData-Table");
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM `%s` WHERE UUID=?", table));
            statement.setString(1, uuid.toString());
            ResultSet result = statement.executeQuery();
            if (result != null && result.next()) {
                finish(new PlayerData(uuid, convertArray((String[]) result.getArray("Cubelets").getArray(), plugin)));
            } else {
                finish(null);
            }
            statement.close();
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public abstract void finish(PlayerData data);

    private List<CubeletData> convertArray(String[] data, ICubeletsPlugin plugin) {
        List<CubeletData> cubeletData = new ArrayList<>();
        for (String s : data) {
            cubeletData.add(plugin.getGson().fromJson(s, CubeletData.class));
        }
        return cubeletData;
    }
}
