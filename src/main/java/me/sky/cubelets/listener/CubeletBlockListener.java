package me.sky.cubelets.listener;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.menus.CubeletMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CubeletBlockListener implements Listener {
    private final ICubeletsPlugin plugin;

    public CubeletBlockListener(ICubeletsPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void blockInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        if (plugin.getCubeletLocationManager().getLocations().containsKey(event.getClickedBlock())) {
            CubeletLocation cubeletLocation = plugin.getCubeletLocationManager().getLocations().get(event.getClickedBlock());
            if (cubeletLocation.isOpening()) {
                return;
            }
            new CubeletMenu(event.getPlayer(), cubeletLocation, plugin);
        }
    }
}
