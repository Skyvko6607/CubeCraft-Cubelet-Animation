package me.sky.cubelets.cubelet.animation.summer.objects;

import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.EntityEvoker;
import net.minecraft.server.v1_16_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EvocationEntity extends EntityEvoker {
    private final Location center;

    public EvocationEntity(Location loc, Location center) {
        super(EntityTypes.EVOKER, ((CraftWorld) loc.getWorld()).getHandle());
        this.center = center;
        setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        setInvulnerable(true);
        setCustomName(new ChatComponentText("§0§l§kCUBECRAFT"));
        setCustomNameVisible(true);
        setSilent(true);
        addScoreboardTag("CubeletEntity");
    }

    @Override
    protected void initPathfinder() {

    }

    @Override
    public void tick() {
        super.tick();
        lookAtLocation(getBukkitEntity(), center);
    }

    public void playAnimation(Spell spell) {
        setSpell(spell);
    }

    private void lookAtLocation(Entity source, Location target) {
        Vector direction = source.getLocation().toVector().subtract(target.toVector()).normalize();
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();

        Location changed = source.getLocation().clone();
        changed.setYaw(180 - (float) Math.toDegrees(Math.atan2(x, z)));
        changed.setPitch(90 - (float) Math.toDegrees(Math.acos(y)));
        source.setRotation(changed.getYaw(), changed.getPitch());
    }

}
