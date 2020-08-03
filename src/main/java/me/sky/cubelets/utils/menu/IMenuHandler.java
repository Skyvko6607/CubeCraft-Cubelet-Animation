package me.sky.cubelets.utils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class IMenuHandler implements Listener {
    public IMenuHandler(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        }
        if (!(event.getView().getTopInventory().getHolder() instanceof IMenu)) {
            return;
        }
        if (!event.getClickedInventory().equals(event.getView().getTopInventory())) {
            return;
        }
        event.setCancelled(true);
        ((IMenu) event.getClickedInventory().getHolder()).click((Player) event.getWhoClicked(), event.getSlot(), event.getCurrentItem(), event.getClick(), event);
    }
}
