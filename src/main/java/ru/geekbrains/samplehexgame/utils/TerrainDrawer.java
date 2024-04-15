package ru.geekbrains.samplehexgame.utils;

import java.awt.*;

public class TerrainDrawer {

     public static void draw(Graphics2D g2, Color color, int size, Point centerPoint) {
        Polygon polygon = HexagonCalc.getHexagon(centerPoint, size);
        g2.setColor(color);
        g2.fillPolygon(polygon);
     }

}
