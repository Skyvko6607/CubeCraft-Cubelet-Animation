package me.sky.cubelets.cubelet.animation.handler;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class CubeletAnimationPart {
    private final CubeletAnimation cubeletAnimation;
    private final Player player;
    private final CubeletLocation cubeletLocation;
    private final ICubeletsPlugin plugin;
    private boolean finished = false;

    public CubeletAnimationPart(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        this.cubeletAnimation = animation;
        this.player = player;
        this.cubeletLocation = cubeletLocation;
        this.plugin = plugin;
    }

    public abstract void update();
    public abstract void remove();
    public abstract int getUpdateTime();

    public void start() {
        if (getUpdateTime() > 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (cubeletAnimation.isFinished()) {
                        this.cancel();
                        return;
                    }
                    update();
                }
            }.runTaskTimer(plugin, 0, getUpdateTime());
        }
    }

    public void finish() {
        if (finished) {
            return;
        }
        finished = true;
        cubeletAnimation.playNext();
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getPlayer() {
        return player;
    }

    public CubeletLocation getCubeletLocation() {
        return cubeletLocation;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }

    public CubeletAnimation getCubeletAnimation() {
        return cubeletAnimation;
    }
}
