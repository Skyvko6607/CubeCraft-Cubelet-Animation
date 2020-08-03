package me.sky.cubelets.database;

import me.sky.cubelets.ICubeletsPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLTableCreate extends SQLScheduler<String> {
    @Override
    public void run(String table, Connection connection, ICubeletsPlugin plugin) {
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS bd.`" + table + "` (`UUID` TEXT DEFAULT NULL, `Cubelets` ARRAY[int])").execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
