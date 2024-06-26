package ru.geekbrains.samplehexgame.utils;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Static utility methods class for graphics
 */
public class HexagonCalc {

    public static Polygon getHexagon(Point centerPoint, int size) {
        Polygon polygon = new Polygon();
        for (double angle = Math.PI / 6; angle <= 2 * Math.PI; angle += Math.PI / 3) {
            polygon.addPoint((int) (centerPoint.x + size * cos(angle)), (int) (centerPoint.y + size * sin(angle)));
        }
        return polygon;
    }
    
}
