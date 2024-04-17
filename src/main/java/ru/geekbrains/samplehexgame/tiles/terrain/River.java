package ru.geekbrains.samplehexgame.tiles.terrain;

import ru.geekbrains.hexcore.core.model.tiles.Terrain;

public class River extends Terrain {
    {
        passable = false;
    }

    public River(int s, int q, int r) {
        super(s, q, r);
    }
}
