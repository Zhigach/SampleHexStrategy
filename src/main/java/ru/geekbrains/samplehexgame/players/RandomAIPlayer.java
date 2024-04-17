package ru.geekbrains.samplehexgame.players;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.model.game.Player;
import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Unit;


import java.awt.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

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
            movementReachableHexes
                    .forEach(h -> h.getHexesInRange(unit.getAttack().getRange())
                            .stream()
                            .forEach(hoi -> battlefield.getUnitsByCoordinate(hoi)
                                    .stream()
                                    .filter(u -> !u.getOwner().equals(this) && battlefield.getTerrainByCoordinate(h).hasLOS(u))
                                    .forEach(reachableByAttackEnemies::add)));
            Unit attackCandidate = reachableByAttackEnemies.stream().min(Comparator.comparingInt(u -> u.getHex().findDistance(unit.getHex()))).orElse(null);

            if (attackCandidate != null) {
                if (unit.getHex().findDistance(attackCandidate.getHex()) > unit.getAttack().getRange()) {
                    gameEngine.moveUnit(unit, unit.getPathTo(attackCandidate));
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
