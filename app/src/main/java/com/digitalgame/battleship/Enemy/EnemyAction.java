package com.digitalgame.battleship.Enemy;

import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.Utils.GameState;
import com.digitalgame.battleship.Utils.Util;

public class EnemyAction {
    public static int[] getRandom() {
        MapState EnemyMap = GameState.getInstance().getEnemyMap();
        int attackLine;
        int attackColumn;

        while (true) {
            int line = Util.getRandomValue(MapState.MAX_LENGTH);
            int column = Util.getRandomValue(MapState.MAX_LENGTH);

            if (EnemyMap.getTouched(line, column)) {
                attackLine = line;
                attackColumn = column;
                EnemyMap.setTouched(line, column);
                break;
            }
        }

        GameState.getInstance().setEnemyMap(EnemyMap);
        return new int[]{attackLine, attackColumn};
    }
}