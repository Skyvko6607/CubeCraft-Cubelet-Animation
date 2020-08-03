package me.sky.cubelets.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private final List<CubeletData> data = new ArrayList<>();

    public PlayerData(UUID uuid, List<CubeletData> data) {
        this.uuid = uuid;
        this.data.addAll(data);
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<CubeletData> getData() {
        return data;
    }
}
