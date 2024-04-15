package ru.geekbrains.samplehexgame.utils;

import ru.geekbrains.hexcore.core.model.tiles.Unit;

import java.awt.*;

public class UnitDrawer {


    public static void draw(Graphics2D g2, Unit unit, int size, Point centerPoint) {
        String shortName = unit.getClass().getSimpleName().chars().filter(Character::isUpperCase).map(c -> ((char) c))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        g2.drawString(String.format(shortName + "(%s/%s)", unit.getCurrentHealth(), unit.getMaxHealth()),
                centerPoint.x, centerPoint.y);
    }

}
