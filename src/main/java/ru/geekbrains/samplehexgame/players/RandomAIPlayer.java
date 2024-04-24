package ru.geekbrains.samplehexgame.players;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.model.game.Player;
import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Tile;
import ru.geekbrains.hexcore.core.model.tiles.Unit;


import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

@Setter
@Getter
@Slf4j
public class RandomAIPlayer extends Player {
    Color color;

    public RandomAIPlayer(@NonNull String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "RandomAIPlayer{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void takeTurn() {
        for (Unit unit : units) {

            unit.restoreMovementPoint();
            Set<Hex> movementReachableHexes = unit.getReachableHexes(unit.getMovementPoints());
            Set<Unit> reachableByAttackEnemies = new HashSet<>();
            Map<Unit, Tile> hexAttackableUnitMap = new HashMap<>();

            // Find enemies that can be attacked
            Set<Hex> hexesUnderAttack = new HashSet<>();
            for (Hex hex : movementReachableHexes) {
                Set<Hex> attackableHexes = hex.getHexesInRange(unit.getAttack().getRange());
                for (Hex attackableHex : attackableHexes) {
                    if ( hexesUnderAttack.contains(attackableHex) ) {
                        continue;
                    }
                    else {
                        hexesUnderAttack.add(attackableHex);
                        Set<Unit> enemies = battlefield.getUnitsByCoordinate(attackableHex)
                                .stream()
                                .filter(u -> !u.getOwner().equals(this) && battlefield.getTerrainByCoordinate(hex).hasLOS(u))
                                .collect(Collectors.toSet());
                        enemies.forEach(e -> hexAttackableUnitMap.put(e, battlefield.getTerrainByCoordinate(hex)));
                    }
                }
            }

            // Pick the closest enemy and go to the
            Unit attackCandidate = hexAttackableUnitMap.keySet().stream()
                    .min(Comparator.comparingInt( e -> e.getHex().findDistance(hexAttackableUnitMap.get(e).getHex()))).orElse(null);


            if (attackCandidate != null) {
                if (unit.getHex().findDistance(attackCandidate.getHex()) > unit.getAttack().getRange()) {
                    gameEngine.moveUnit(unit, unit.getPathTo(hexAttackableUnitMap.get(attackCandidate)));
                }
                gameEngine.attack(unit, attackCandidate);
            } else {
                Player enemy = gameEngine.getPlayers()
                        .stream()
                        .filter(p -> !p.equals(this))
                        .findFirst().get();
                if (enemy.getUnits().isEmpty()) {
                    return;
                } else {
                    attackCandidate = enemy.getUnits().get(0);
                    gameEngine.moveUnit(unit, unit.getPathTo(attackCandidate));
                }
            }
        }
    }
}
