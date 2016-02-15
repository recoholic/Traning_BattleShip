package com.digitalgame.battleship;

import com.digitalgame.battleship.Service.DeliverServiceImpl;

public class GameState {
    private static final boolean CHOOSE_YET = false;
    private static final boolean CHOOSE_ALREADY = true;
    private static final boolean DELIVER_YET = false;
    private static final boolean DELIVER_ALREADY = true;
    private static final boolean GAME_NOW = false;
    private static final boolean GAME_END = true;
    private static final boolean PLAYER_WIN = true;
    private static final boolean PLAYER_LOSE = false;

    private static final GameState STATE = new GameState();

    private boolean mSetAlready;
    private boolean mDeliverAlready;
    private boolean mIsGameEnd;
    private boolean mPlayerWin;

    private GameState() {
    }

    public static GameState getState() {
        return STATE;
    }

    public void initGameState() {
        DeliverServiceImpl.initDeliver();
        mSetAlready = CHOOSE_YET;
        mDeliverAlready = DELIVER_YET;
        mIsGameEnd = GAME_NOW;
        mPlayerWin = PLAYER_LOSE;
    }

    public void setPlayerState() {
        mSetAlready     = CHOOSE_ALREADY;
        mDeliverAlready = DELIVER_ALREADY;
    }

    public boolean isChooseAlready() {
        return mSetAlready;
    }

    public boolean isDeliverAlready() {
        return mDeliverAlready;
    }

    public boolean getGameNow() {
        return mIsGameEnd;
    }

    public void setGameEnd() {
        mIsGameEnd = GAME_END;
    }

    public boolean isPlayerWin() {
        return mPlayerWin;
    }

    public void setPlayerWin() {
        this.mPlayerWin = PLAYER_WIN;
    }
}