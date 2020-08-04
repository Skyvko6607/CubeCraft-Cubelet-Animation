package me.sky.cubelets.cubelet.animation.handler;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CubeletAnimation {
    private final Cubelet cubelet;
    private final Player player;
    private final CubeletLocation cubeletLocation;
    private final ICubeletsPlugin plugin;
    private final List<CubeletAnimationPart> animationParts = new ArrayList<>();

    private boolean finished = false;
    private int animIndex = 0;

    public CubeletAnimation(Cubelet cubelet, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        this.cubelet = cubelet;
        this.player = player;
        this.cubeletLocation = cubeletLocation;
        this.plugin = plugin;
    }

    public void addAnimation(CubeletAnimationPart animationPart) {
        this.animationParts.add(animationPart);
    }

    public void playNext() {
        if (animIndex >= animationParts.size()) {
            animationParts.forEach(CubeletAnimationPart::remove);
            finish();
            finished = true;
            return;
        }
        animationParts.get(animIndex).start();
        animIndex++;
    }

    public Cubelet getCubelet() {
        return cubelet;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public CubeletLocation getCubeletLocation() {
        return cubeletLocation;
    }

    public ICubeletsPlugin getPlugin() {
        return plugin;
    }

    public List<CubeletAnimationPart> getAnimationParts() {
        return animationParts;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void finish();
}
