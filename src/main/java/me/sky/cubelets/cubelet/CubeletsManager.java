package me.sky.cubelets.cubelet;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.Manager;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.cubelet.types.summer.SummerCubelet;

import java.util.ArrayList;
import java.util.List;

public class CubeletsManager extends Manager<ICubeletsPlugin> {

    private final List<Cubelet> cubelets = new ArrayList<>();

    public CubeletsManager(ICubeletsPlugin plugin) {
        super(plugin);
        cubelets.add(new SummerCubelet(plugin));
    }

    public Cubelet getCubeletById(String id) {
        for (Cubelet cubelet : cubelets) {
            if (cubelet.getId().equalsIgnoreCase(id)) {
                return cubelet;
            }
        }
        return null;
    }

    public List<Cubelet> getCubelets() {
        return cubelets;
    }
}
