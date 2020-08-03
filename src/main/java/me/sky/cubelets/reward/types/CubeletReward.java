package me.sky.cubelets.reward.types;

import me.sky.cubelets.ICubeletsPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class CubeletReward extends me.sky.cubelets.reward.CubeletReward {
    private final String cubelet;

    public CubeletReward(ConfigurationSection section, ICubeletsPlugin plugin) {
        super(section, plugin);
        this.cubelet = section.getString("Cubelet");
    }

    @Override
    public void giveReward(Player player) {
        //TODO Continue code
    }
}
