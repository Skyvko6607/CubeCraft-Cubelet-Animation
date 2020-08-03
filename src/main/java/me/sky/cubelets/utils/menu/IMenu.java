package me.sky.cubelets.utils.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface IMenu extends InventoryHolder {
    void click(Player player, int slot, ItemStack item, ClickType clickType, Cancellable event);
}
