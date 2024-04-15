package ru.geekbrains.samplehexgame.utils;

import ru.geekbrains.hexcore.core.model.tiles.Terrain;
import ru.geekbrains.samplehexgame.tiles.terrain.Forest;
import ru.geekbrains.samplehexgame.tiles.terrain.Hill;
import ru.geekbrains.samplehexgame.tiles.terrain.River;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TerrainColorMapper {

    private static final Map<Class<? extends Terrain>, Color> colorMap = new HashMap<>();

    static {
        colorMap.put(Forest.class, Color.GREEN);
        colorMap.put(River.class, Color.BLUE);
        colorMap.put(Hill.class, Color.GRAY);
    }

    public static Color getColorFor(Terrain terrain) {
        return colorMap.getOrDefault(terrain.getClass(), Color.WHITE);
    }


}
