package me.sky.cubelets.cubelet.animation.halloween.objects.entity;

import me.sky.cubelets.utils.MinecraftUtils;
import net.minecraft.server.v1_16_R1.EntityEvoker;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;

public class EvocationEntity extends EntityEvoker {
    private final Location loc;
    private final Location center;

    public EvocationEntity(Location loc, Location center) {
        super(EntityTypes.EVOKER, ((CraftWorld) loc.getWorld()).getHandle());
        this.loc = loc;
        this.center = center;
        setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        setInvulnerable(true);
        setSilent(true);
        collides = false;
        addScoreboardTag("CubeletEntity");
    }

    @Override
    protected void initPathfinder() {

    }

    @Override
    public void tick() {
        super.tick();
        MinecraftUtils.lookAtLocation(getBukkitEntity(), center);
    }

    public void playAnimation(Spell spell) {
        setSpell(spell);
    }

}
