package ru.geekbrains.samplehexgame.tiles.terrain;


import ru.geekbrains.hexcore.core.model.tiles.Terrain;

import java.awt.*;

public class Forest extends Terrain {
    {
        passable = true;
        blockLOS = true;
    }

    public Forest(int s, int q, int r) {
        super(s, q, r);
    }
}
