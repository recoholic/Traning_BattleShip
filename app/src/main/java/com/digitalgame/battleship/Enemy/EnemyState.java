package com.digitalgame.battleship.Enemy;

import com.digitalgame.battleship.Utils.BattleShipConstants;
import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.MapArea.MapStateImpl;
import com.digitalgame.battleship.Utils.Util;

public class EnemyState {

    private static final int MAX_SERIES = 3;
    private static final int MAX_SELECT_AREA = 6;

    public EnemyState() {
    }

    public MapState makeState(String Command) {
        switch (Command) {
            case BattleShipConstants.TUTORIAL:
                return Tutorial();
            case BattleShipConstants.SIMPLE:
                return Simple();
            case BattleShipConstants.RANDOM:
                return Random();
        }

        return null;
    }

    public MapState Tutorial() {
        MapState mapState = new MapStateImpl();
        for (int i = 0; i < MapState.MAX_LENGTH; i++) {
            mapState.setHitArea(i, i);
        }

        return mapState;
    }

    public MapState Simple() {
        MapState mapState = new MapStateImpl();
        for (int attack = MAX_SERIES; attack > 0; attack--) {
            while(true) {
                int row;
                int column;
                boolean isAddRow = Util.getRandomBoolean();
                if (isAddRow) {
                    row = Util.getRandomValue(MapState.MAX_LENGTH - attack);
                    column = Util.getRandomValue(MapState.MAX_LENGTH);
                }  else {
                    row = Util.getRandomValue(MapState.MAX_LENGTH);
                    column = Util.getRandomValue(MapState.MAX_LENGTH - attack);
                }

                MapState map = new MapStateImpl();
                for (int i = 0; i < attack; i++) {
                    if (isAddRow) {
                        map.setHitArea(row + i, column);
                    } else {
                        map.setHitArea(row, column + i);
                    }
                }
                if (mapState.checkDuplicate(map)) {
                    mapState.addMap(map);
                    break;
                }
            }
        }

        return mapState;
    }

    public MapState Random() {
        MapState mapState = new MapStateImpl();
        for (int count = 0; count < MAX_SELECT_AREA; ) {
            int row = Util.getRandomValue(MapState.MAX_LENGTH);
            int column = Util.getRandomValue(MapState.MAX_LENGTH);

            if (!mapState.isExist(row, column)) {
                mapState.setHitArea(row, column);
                count++;
            }
        }

        return mapState;
    }
}