package me.sky.cubelets.database;

import me.sky.cubelets.ICubeletsPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLScheduler<T> {
    public abstract void run(T obj, Connection connection, ICubeletsPlugin plugin);

    public void execute(T obj, ICubeletsPlugin plugin) throws SQLException, ClassNotFoundException {
        Connection connection = plugin.getDatabase().openConnection();
        new BukkitRunnable() {
            @Override
            public void run() {
                SQLScheduler.this.run(obj, connection, plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
}
