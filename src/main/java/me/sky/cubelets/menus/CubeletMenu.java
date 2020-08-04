package me.sky.cubelets.menus;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.menu.IMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class CubeletMenu implements IMenu {
    private final ICubeletsPlugin plugin;
    private final CubeletLocation cubeletLocation;
    private final Inventory inv;
    private final Map<Integer, Cubelet> cubeletMap = new HashMap<>();

    public CubeletMenu(Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        this.plugin = plugin;
        this.cubeletLocation = cubeletLocation;
        this.inv = Bukkit.createInventory(this, 18, "§lCubelets");
        for (Cubelet cubelet : plugin.getCubeletManager().getCubelets()) {
            cubeletMap.put(inv.firstEmpty(), cubelet);
            inv.setItem(inv.firstEmpty(), getCubeletIcon(cubelet));
        }
        inv.setItem(11, new ItemStack(Material.CRAFTING_TABLE));
        inv.setItem(13, new ItemStack(Material.BOOK));
        inv.setItem(15, new ItemStack(Material.CLOCK));
        player.openInventory(inv);
    }

    private ItemStack getCubeletIcon(Cubelet cubelet) {
        ItemStack item = cubelet.getHeadItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(cubelet.getName());
        meta.setLore(cubelet.getDescription());
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void click(Player player, int slot, ItemStack item, ClickType clickType, Cancellable event) {
        if (!cubeletMap.containsKey(slot)) {
            return;
        }
        cubeletMap.get(slot).start(player, cubeletLocation);
        player.closeInventory();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }
}
