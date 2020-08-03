package me.sky.cubelets.data;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;

public class CubeletData {
    private final String id;
    private final long expiration;

    public CubeletData(Cubelet cubelet) {
        this.id = cubelet.getId();
        this.expiration = System.currentTimeMillis() + cubelet.getExpiration();
    }

    public CubeletData(String id, long expiration) {
        this.id = id;
        this.expiration = expiration;
    }

    public String getId() {
        return id;
    }

    public long getExpiration() {
        return expiration;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= getExpiration();
    }

    public String toString(ICubeletsPlugin plugin) {
        return plugin.getGson().toJson(this);
    }
}
