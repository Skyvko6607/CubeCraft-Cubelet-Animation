package me.sky.cubelets.cubelet.types.summer;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.summer.SummerCubeletAnimation;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.cubelet.objects.CubeletRarity;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class SummerCubelet extends Cubelet {
    public SummerCubelet(ICubeletsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getId() {
        return "summer";
    }

    @Override
    public String getHeadTexture() {
        return "5a5ab05ea254c32e3c48f3fdcf9fd9d77d3cba04e6b5ec2e68b3cbdcfac3fd";
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
        new SummerCubeletAnimation(this, player, cubeletLocation, getPlugin()).playNext();
    }
}
