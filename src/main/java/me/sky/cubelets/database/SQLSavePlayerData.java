package me.sky.cubelets.database;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.data.CubeletData;
import me.sky.cubelets.data.PlayerData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLSavePlayerData extends SQLScheduler<PlayerData> {
    @Override
    public void run(PlayerData data, Connection connection, ICubeletsPlugin plugin) {
        try {
            String table = plugin.getDatabaseSettings().getConfig().getString("PlayerData-Table");
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM `%s` WHERE UUID=?", table));
            statement.setString(1, data.getUuid().toString());
            ResultSet result = statement.executeQuery();
            if (result != null && result.next()) {
                updateData(data, connection, table);
            } else {
                createData(data, connection, table);
            }
            statement.close();
            if (result != null) {
                result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createData(PlayerData data, Connection connection, String table) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(String.format("INSERT INTO `%s` (`UUID`, `Cubeletes`) VALUES (?, ?)", table));
        statement.setString(1, data.getUuid().toString());
        statement.setArray(2, connection.createArrayOf("TINYTEXT", convertCubeletesData(data.getData())));
        statement.executeUpdate();
    }

    private void updateData(PlayerData data, Connection connection, String table) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(String.format("UPDATE `%s` SET Cubeletes=? WHERE UUID=?", table));
        statement.setArray(1, connection.createArrayOf("TINYTEXT", convertCubeletesData(data.getData())));
        statement.setString(2, data.getUuid().toString());
        statement.executeUpdate();
    }

    private String[] convertCubeletesData(List<CubeletData> data) {
        String[] converted = new String[data.size()];
        for (int i = 0; i < data.size(); i++) {
            converted[i] = data.get(i).toString();
        }
        return converted;
    }
}
