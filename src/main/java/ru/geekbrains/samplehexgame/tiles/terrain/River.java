package ru.geekbrains.samplehexgame.tiles.terrain;

import ru.geekbrains.hexcore.core.model.tiles.Terrain;

import java.awt.*;

public class River extends Terrain {
    {
        passable = false;
    }

    public River(int s, int q, int r) {
        super(s, q, r);
    }
}
