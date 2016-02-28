package com.digitalgame.battleship.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.digitalgame.battleship.Enemy.EnemyState;
import com.digitalgame.battleship.MapArea.MapState;
import com.digitalgame.battleship.MapArea.MapStateImpl;

public class DeliverServiceImpl extends Service implements DeliverService{
    private static MapState mPlayerMap;
    private static MapState mEnemyMap;

    public static void setPlayerMap(MapState Map, boolean isPlayer) {
        if (isPlayer) {
            mPlayerMap = Map;
        } else {
            mEnemyMap = Map;
        }
    }

    public static MapState getPlayerMap(boolean isPlayer) {
        if (isPlayer) {
            return mPlayerMap;
        } else {
            return mEnemyMap;
        }
    }

    public static void setEnemyMode(String mode) {
        EnemyState enemyState = new EnemyState();
        mEnemyMap = enemyState.MakeState(mode);
    }

    public static void initDeliver() {
        mPlayerMap = new MapStateImpl();
        mEnemyMap = new MapStateImpl();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initDeliver();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
