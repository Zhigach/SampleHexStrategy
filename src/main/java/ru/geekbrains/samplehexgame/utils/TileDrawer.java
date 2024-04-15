package ru.geekbrains.samplehexgame.utils;


import ru.geekbrains.hexcore.core.model.tiles.Tile;

import java.awt.*;

import static java.lang.Math.sqrt;

public class TileDrawer {

    public static void draw(Graphics2D g2, Tile tile, int size, Point centerPoint) {
        g2.drawString(
                String.format(String.valueOf(
                        tile.getHex())), (int) (centerPoint.x - sqrt(3) * size / 2), centerPoint.y);
    }

}
