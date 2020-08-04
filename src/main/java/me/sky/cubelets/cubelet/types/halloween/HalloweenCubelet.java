package me.sky.cubelets.cubelet.types.halloween;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.halloween.HalloweenCubeletAnimation;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.cubelet.objects.CubeletRarity;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class HalloweenCubelet extends Cubelet {
    public HalloweenCubelet(ICubeletsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getId() {
        return "halloween";
    }

    @Override
    public String getHeadTexture() {
        return "a6cc486c2be1cb9dfcb2e53dd9a3e9a883bfadb27cb956f1896d602b4067";
    }

    @Override
    public CubeletRarity getRarity() {
        return CubeletRarity.RARE;
    }

    @Override
    public int[] getLootPoints() {
        return new int[]{50, 200};
    }

    @Override
    public long getExpiration() {
        return TimeUnit.DAYS.toMillis(13);
    }

    @Override
    public void start(Player player, CubeletLocation cubeletLocation) {
        super.start(player, cubeletLocation);
        new HalloweenCubeletAnimation(this, player, cubeletLocation, getPlugin()).playNext();
    }
}
