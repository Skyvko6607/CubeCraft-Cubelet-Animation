package me.sky.cubelets.cubelet.objects;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.reward.CubeletReward;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Cubelet {
    private final ICubeletsPlugin plugin;

    public Cubelet(ICubeletsPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract List<String> getLore();
    public abstract String getHeadTexture();
    public abstract CubeletRarity getRarity();
    public abstract int getLootPoints();
    public abstract long getExpiration();
    public abstract List<CubeletReward> getRewards();
    public abstract void giveReward(Player player);

    public void start(Player player, CubeletLocation cubeletLocation) {
        cubeletLocation.setOpening(true);
    }

    public void finish(Player player, CubeletLocation cubeletLocation) {
        cubeletLocation.setOpening(false);
        giveReward(player);
    }

    public CubeletReward getRandomReward() {
        return getRewards().get(getPlugin().getRandom().nextInt(getRewards().size()));
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }
}
