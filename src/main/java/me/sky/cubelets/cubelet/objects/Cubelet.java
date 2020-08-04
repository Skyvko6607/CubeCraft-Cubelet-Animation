package me.sky.cubelets.cubelet.objects;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.reward.CubeletReward;
import me.sky.cubelets.reward.types.BoxReward;
import me.sky.cubelets.reward.types.CosmeticReward;
import me.sky.cubelets.reward.types.ItemReward;
import me.sky.cubelets.reward.types.PermissionReward;
import me.sky.cubelets.utils.ItemUtils;
import me.sky.cubelets.utils.MinecraftUtils;
import me.sky.cubelets.utils.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Cubelet {
    private final ICubeletsPlugin plugin;
    private final Config config;
    private final List<CubeletReward> rewards = new ArrayList<>();

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
        config.getConfig().getConfigurationSection("Rewards").getKeys(false).forEach(s -> {
            ConfigurationSection sec = config.getConfig().getConfigurationSection("Rewards." + s);
            CubeletReward.RewardType type = CubeletReward.RewardType.valueOf(sec.getString("Type"));
            CubeletReward reward = null;
            if (type == CubeletReward.RewardType.ITEM) {
                reward = new ItemReward(sec, plugin);
            } else if (type == CubeletReward.RewardType.COSMETIC) {
                reward = new CosmeticReward(sec, plugin);
            } else if (type == CubeletReward.RewardType.PERMISSION) {
                reward = new PermissionReward(sec, plugin);
            } else if (type == CubeletReward.RewardType.CUBELET) {
                reward = new BoxReward(sec, plugin);
            }
            if (reward != null) {
                rewards.add(reward);
            }
        });
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
        giveRandomReward(player);
    }

    public void giveRandomReward(Player player) {
        giveReward(player, getRandomReward());
    }

    public void giveReward(Player player, CubeletReward reward) {
        reward.giveReward(player);
    }

    public String getName() {
        return MinecraftUtils.translate(getConfig().getConfig().getString("Name"));
    }

    public List<String> getDescription() {
        return MinecraftUtils.translate(getConfig().getConfig().getStringList("Description"));
    }

    public CubeletReward getRandomReward() {
        return getRewards().get(getPlugin().getRandom().nextInt(getRewards().size()));
    }

    public int getRandomLootPoint() {
        return getPlugin().getRandom().nextInt(getLootPoints()[0] + getLootPoints()[1]) - getLootPoints()[0];
    }

    public ItemStack getHeadItem() {
        return ItemUtils.constructHeadTexture(getHeadTexture(), "", new ArrayList<>());
    }

    public List<CubeletReward> getRewards() {
        return rewards;
    }

    public Config getConfig() {
        return config;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }
}
