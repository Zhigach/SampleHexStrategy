package ru.geekbrains.samplehexgame;

import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.RectangleMapInitializer;
import ru.geekbrains.hexcore.core.GameEngine;
import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.samplehexgame.players.RandomAIPlayer;
import ru.geekbrains.samplehexgame.tiles.terrain.Forest;
import ru.geekbrains.samplehexgame.tiles.terrain.River;
import ru.geekbrains.samplehexgame.tiles.units.SampleInfantry;
import ru.geekbrains.samplehexgame.utils.UnitPlacementTool;
import ru.geekbrains.samplehexgame.viewer.SwingViewer;

import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Battlefield battlefield = Battlefield.getInstance();
        initBattlefield(battlefield);

        RandomAIPlayer p1 = new RandomAIPlayer("FirstPlayer");
        p1.setColor(Color.RED);
        RandomAIPlayer p2 = new RandomAIPlayer("SecondPlayer");
        p2.setColor(Color.BLUE);

        GameEngine gameEngine = new GameEngine(List.of(p1, p2), battlefield);


        Tile forest1 = new Forest(0, 0, 0);
        Tile forest2 = new Forest(-2, 0, 2);

        Tile river1 = new River(0, 1, -1);
        Tile river2 = new River(1, -1, 0);
        Tile river3 = new River(1, 0, -1);
        Tile river4 = new River(-1, 1, 0);

        //Unit lineInfantry = new SampleInfantry(p1, new Hex(0, 0, 0));
        //Unit dummyTarget = new SampleInfantry(p2, new Hex(0, -2, 2));

        try {
            UnitPlacementTool.placeUnits(battlefield, 2,4, SampleInfantry.class, p1);
            UnitPlacementTool.placeUnits(battlefield, -2,4, SampleInfantry.class, p2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        gameEngine.start();

    }

    private static void initBattlefield(Battlefield battlefield) {
        Battlefield.setDimensions(-4, 4, -6, 6); // C&C field
        Battlefield.setMapInitializer(new RectangleMapInitializer());
        SwingViewer battlefieldDrawer = new SwingViewer(battlefield);
        battlefieldDrawer.setGUI();
        battlefield.initializeMap();
    }
}