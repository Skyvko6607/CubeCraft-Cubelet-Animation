package me.sky.cubelets.location;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Location;

public class CubeletLocation {
    private final String id;
    private final ICubeletsPlugin plugin;
    private final Location location;

    private boolean opening = false;

    public CubeletLocation(String id, Location location, ICubeletsPlugin plugin) {
        this.id = id;
        this.plugin = plugin;
        this.location = location;
    }

    public CubeletLocation(String id, String loc, ICubeletsPlugin plugin) {
        this(id, MinecraftUtils.readConfigLocation(loc), plugin);
    }

    public void setOpening(boolean opening) {
        this.opening = opening;
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }

    public boolean isOpening() {
        return opening;
    }
}
