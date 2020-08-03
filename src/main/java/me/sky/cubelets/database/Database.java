package me.sky.cubelets.database;

import me.sky.cubelets.ICubeletsPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final ICubeletsPlugin plugin;
    private Connection connection;

    public Database(ICubeletsPlugin plugin) {
        this.plugin = plugin;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }
        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("com.mysql.jdbc.Driver");
            String ip = plugin.getDatabaseSettings().getConfig().getString("IP"),
                    port = plugin.getDatabaseSettings().getConfig().getString("Port"),
                    database = plugin.getDatabaseSettings().getConfig().getString("Database"),
                    user = plugin.getDatabaseSettings().getConfig().getString("Username"),
                    pass = plugin.getDatabaseSettings().getConfig().getString("Password");
            String connectionString = String.format("jdbc:mysql://%s:%s/%s", ip, port, database);
            connection = DriverManager.getConnection(connectionString, user, pass);
            return connection;
        }
    }
}
