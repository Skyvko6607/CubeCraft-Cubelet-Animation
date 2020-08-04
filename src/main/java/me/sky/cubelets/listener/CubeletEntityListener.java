package me.sky.cubelets.listener;

import me.sky.cubelets.ICubeletsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class CubeletEntityListener implements Listener {
    private final ICubeletsPlugin plugin;

    public CubeletEntityListener(ICubeletsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity.getScoreboardTags().contains("CubeletEntity")) {
                entity.remove();
            }
        }));
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        if (event.getEntity().getScoreboardTags().contains("CubeletEntity")) {
            event.setCancelled(true);
        }
    }
}
