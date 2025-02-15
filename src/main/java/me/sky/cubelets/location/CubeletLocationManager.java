package me.sky.cubelets.location;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.Manager;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class CubeletLocationManager extends Manager<ICubeletsPlugin> {

    private final Map<Block, CubeletLocation> locations = new HashMap<>();

    public CubeletLocationManager(ICubeletsPlugin plugin) {
        super(plugin);
        plugin.getSettings().getConfig().getConfigurationSection("OpeningLocations").getKeys(false).forEach(s -> {
            CubeletLocation cubeletLocation = new CubeletLocation(s, plugin.getSettings().getConfig().getString("OpeningLocations." + s), plugin);
            locations.put(cubeletLocation.getLocation().getBlock(), cubeletLocation);
        });
    }

    public Map<Block, CubeletLocation> getLocations() {
        return locations;
    }
}
