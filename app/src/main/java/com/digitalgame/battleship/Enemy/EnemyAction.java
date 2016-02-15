package com.digitalgame.battleship.Enemy;

import com.digitalgame.battleship.Service.DeliverServiceImpl;
import com.digitalgame.battleship.Map.MapState;
import com.digitalgame.battleship.Util;

public class EnemyAction {
    public static int[] getRandom() {
        MapState EnemyMap = DeliverServiceImpl.getPlayerMap(DeliverServiceImpl.IS_ENEMY);
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

        DeliverServiceImpl.setPlayerMap(EnemyMap, DeliverServiceImpl.IS_ENEMY);
        return new int[]{attackLine, attackColumn};
    }
}