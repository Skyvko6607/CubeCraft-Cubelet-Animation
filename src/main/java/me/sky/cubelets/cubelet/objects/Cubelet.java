package me.sky.cubelets.cubelet.objects;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.ItemUtils;
import me.sky.cubelets.utils.MinecraftUtils;
import me.sky.cubelets.utils.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Cubelet {
    private final ICubeletsPlugin plugin;
    private final Config config;

    public Cubelet(ICubeletsPlugin plugin) {
        this.plugin = plugin;
        this.config = new Config("plugins/" + getPlugin().getName() + "/cubelets", getId() + ".yml", getPlugin());
        this.config.create();
        if (!config.exists()) {
            config.setDefault("cubelet.yml");
            config.getConfig().options().copyDefaults(true);
            config.saveConfig();
            config.reloadConfig();
        }
    }

    public abstract String getId();

    public abstract String getHeadTexture();

    public abstract CubeletRarity getRarity();

    public abstract int[] getLootPoints();

    public abstract long getExpiration();

    public void start(Player player, CubeletLocation cubeletLocation) {
        cubeletLocation.setOpening(true);
    }

    public void finish(Player player, CubeletLocation cubeletLocation) {
        cubeletLocation.setOpening(false);
    }

    public String getName() {
        return MinecraftUtils.translate(getConfig().getConfig().getString("Name"));
    }

    public List<String> getDescription() {
        return MinecraftUtils.translate(getConfig().getConfig().getStringList("Description"));
    }

    public int getRandomLootPoint() {
        return getPlugin().getRandom().nextInt(getLootPoints()[0] + getLootPoints()[1]) - getLootPoints()[0];
    }

    public ItemStack getHeadItem() {
        return ItemUtils.constructHeadTexture(getHeadTexture(), "", new ArrayList<>());
    }

    public Config getConfig() {
        return config;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }
}
