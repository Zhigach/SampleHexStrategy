package ru.geekbrains.samplehexgame.tiles.units;


import ru.geekbrains.hexcore.core.model.attack.Attack;
import ru.geekbrains.hexcore.core.model.attack.AttackType;
import ru.geekbrains.hexcore.core.model.game.Player;
import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Unit;
import ru.geekbrains.hexcore.utils.DiceSet;
import ru.geekbrains.hexcore.utils.Dices;


import java.util.Set;

public class RangedInfantry extends Unit {
    private static final int HP = 5;
    private static final int MOVEMENT = 2;
    private static Attack BASIC_ATTACK = new Attack(AttackType.PHYSICAL, new DiceSet(Set.of(new Dices(2, 3))), 2);

    public RangedInfantry(Player owner, Hex hex) {
        super(owner, HP, MOVEMENT, BASIC_ATTACK, hex);
    }
}
