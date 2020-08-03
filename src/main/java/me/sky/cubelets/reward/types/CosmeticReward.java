package me.sky.cubelets.reward.types;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.reward.CubeletReward;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class CosmeticReward extends CubeletReward {
    private final String cosmetic;

    public CosmeticReward(ConfigurationSection section, ICubeletsPlugin plugin) {
        super(section, plugin);
        this.cosmetic = section.getString("Cosmetic");
    }

    /**
     * Empty code because it requires a cosmetic plugin for it to work.
     */
    @Override
    public void giveReward(Player player) {
        //TODO Add cosmetic to player
    }
}
