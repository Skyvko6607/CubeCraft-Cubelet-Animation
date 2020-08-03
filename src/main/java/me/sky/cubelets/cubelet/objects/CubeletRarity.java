package me.sky.cubelets.cubelet.objects;

import me.sky.cubelets.ICubeletsPlugin;
import org.apache.commons.lang.StringUtils;

public enum CubeletRarity {
    COMMON,
    UNCOMMON,
    RARE,
    EPIC,
    LEGENDARY;

    public String getName(ICubeletsPlugin plugin) {
        return plugin.getMessages().getMessage(StringUtils.capitalize(name()));
    }
}
