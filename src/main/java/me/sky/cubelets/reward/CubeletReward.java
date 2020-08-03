package me.sky.cubelets.reward;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CubeletReward {

    private final ICubeletsPlugin plugin;
    private final String name;
    private final ItemStack item;

    public CubeletReward(ConfigurationSection section, ICubeletsPlugin plugin) {
        this.plugin = plugin;
        this.name = MinecraftUtils.translate(section.getString("Name"));
        if (section.isSet("Icon")) {
            ConfigurationSection itemSection = section.getConfigurationSection("Icon");
            this.item = new ItemStack(Material.valueOf(itemSection.getString("Type")), itemSection.getInt("Amount"));
            ItemMeta meta = item.getItemMeta();
            if (itemSection.isSet("Name")) {
                meta.setDisplayName(MinecraftUtils.translate(itemSection.getString("Name")));
            } else {
                meta.setDisplayName(name);
            }
            if (itemSection.isSet("Lore")) {
                meta.setLore(MinecraftUtils.translate(itemSection.getStringList("Lore")));
            }
            if (itemSection.isSet("ItemFlags")) {
                itemSection.getStringList("ItemFlags").forEach(s -> meta.addItemFlags(ItemFlag.valueOf(s)));
            }
            if (itemSection.isSet("Enchantments")) {
                itemSection.getStringList("Enchantments").forEach(s -> {
                    Enchantment enchantment = Enchantment.getByKey(new NamespacedKey(plugin, s.split(";")[0]));
                    if (enchantment != null) {
                        meta.addEnchant(enchantment, Integer.parseInt(s.split(";")[1]), true);
                    }
                });
            }
            if (itemSection.isSet("Unbreakable")) {
                meta.setUnbreakable(itemSection.getBoolean("Unbreakable"));
            }
            item.setItemMeta(meta);
        } else {
            this.item = new ItemStack(Material.WHITE_TERRACOTTA);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
    }

    public String getDisplayName() {
        return name;
    }

    public ItemStack getIcon() {
        return item;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }

    public abstract void giveReward(Player player);
}
