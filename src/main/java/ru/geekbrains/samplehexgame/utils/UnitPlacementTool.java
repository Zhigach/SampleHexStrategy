package ru.geekbrains.samplehexgame.utils;

import ru.geekbrains.hexcore.core.model.game.Player;
import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Unit;
import ru.geekbrains.hexcore.core.service.Battlefield;

import java.lang.reflect.InvocationTargetException;


public class UnitPlacementTool {
    public static void placeUnits(Battlefield battlefield, int row, int quantity, Class<? extends Unit> unitClass, Player owner) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (quantity >= battlefield.getHorizontalSize()) {
            throw new IllegalArgumentException("Illegal quantity");
        }
        int r = 0;
        if (row > 0) {
            r = Battlefield.top + row;
        } else {
            r = Battlefield.bottom + row;
        }
        int delta = (battlefield.getHorizontalSize() - quantity) / 2;
        for (int q = Battlefield.left + delta; q <= Battlefield.right - delta; q++) {
            Hex hex = new Hex(-q - r, q, r);
            Unit unit = unitClass.getConstructor(Player.class, Hex.class).newInstance(owner, hex);
            battlefield.updateView();
        }

    }

}
