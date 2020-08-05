package me.sky.cubelets.listener;

import me.sky.cubelets.ICubeletsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

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

    @EventHandler
    public void entityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getScoreboardTags().contains("CubeletEntity")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getScoreboardTags().contains("CubeletEntity")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityInteract(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked().getScoreboardTags().contains("CubeletEntity")) {
            event.setCancelled(true);
        }
    }
}
