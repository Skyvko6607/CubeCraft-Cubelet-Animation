package me.sky.cubelets.cubelet.animation.halloween.objects.entity;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;

public class VexStatue extends EntityVex {

    private final Location loc;

    public VexStatue(Location loc) {
        super(EntityTypes.VEX, ((CraftWorld) loc.getWorld()).getHandle());
        this.loc = loc;
        setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        setInvulnerable(true);
        setSilent(true);
        setNoGravity(true);
        addScoreboardTag("CubeletEntity");
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F, 1.0F));
    }

    @Override
    public void tick() {
        super.tick();
        if (!getBukkitEntity().getLocation().toVector().equals(loc.toVector())) {
            setPosition(loc.getX(), loc.getY(), loc.getZ());
        }
    }
}
