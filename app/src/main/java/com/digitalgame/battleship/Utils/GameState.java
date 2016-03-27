package com.digitalgame.battleship.Utils;

import com.digitalgame.battleship.MapArea.MapState;

public class GameState {

    private static GameState ourInstance = new GameState();
    private MapState playerMap;
    private MapState enemyMap;

    private GameState() {
    }

    public static GameState getInstance() {
        return ourInstance;
    }

    public MapState getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(MapState playerMap) {
        this.playerMap = playerMap;
    }

    public MapState getEnemyMap() {
        return enemyMap;
    }

    public void setEnemyMap(MapState enemyMap) {
        this.enemyMap = enemyMap;
    }
}
