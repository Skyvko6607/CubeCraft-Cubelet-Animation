package me.sky.cubelets.reward.types;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.reward.CubeletReward;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ItemReward extends CubeletReward {
    public ItemReward(ConfigurationSection section, ICubeletsPlugin plugin) {
        super(section, plugin);
    }

    @Override
    public void giveReward(Player player) {
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(getIcon());
        } else {
            player.getWorld().dropItem(player.getLocation(), getIcon());
        }
    }
}
