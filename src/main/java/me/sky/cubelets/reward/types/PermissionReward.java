package me.sky.cubelets.reward.types;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.reward.CubeletReward;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionReward extends CubeletReward {
    private final String permission;

    public PermissionReward(ConfigurationSection section, ICubeletsPlugin plugin) {
        super(section, plugin);
        this.permission = section.getString("Permission");
    }

    /**
     * Temporary permission adding.
     * This actually doesn't do much - it disappears after server restart.
     * Requires an existing permission API to make it work.
     */
    @Override
    public void giveReward(Player player) {
        PermissionAttachment attachment = player.addAttachment(getPlugin());
        attachment.setPermission(permission, true);
    }
}
