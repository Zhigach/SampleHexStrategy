package ru.geekbrains.samplehexgame.tiles.terrain;


import ru.geekbrains.hexcore.core.model.tiles.Terrain;

import java.awt.*;

public class Hill extends Terrain {
    {
        passable = true;
        blockLOS = true;
    }

    public Hill(int s, int q, int r) {
        super(s, q, r);
    }
}
