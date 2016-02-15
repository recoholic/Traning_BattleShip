package com.digitalgame.battleship.Map;

public class MapStateImpl implements MapState {
    public AreaState[][] mArea = new AreaState[MAX_LENGTH][MAX_LENGTH];

    public MapStateImpl() {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                mArea[i][j] = new AreaStateImpl();
            }
        }
    }

    @Override
    public void setWidgetId(int line, int column, int widgetId) {
        mArea[line][column].setWidgetId(widgetId);
    }

    @Override
    public void setWidgetId(int[][] widgetId) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                setWidgetId(i, j, widgetId[i][j]);
            }
        }
    }

    @Override
    public void setHitArea(int widgetId) {
        int[] point = getLineColumn(widgetId);
        setHitArea(point[0], point[1]);
    }

    @Override
    public void setHitArea(int line, int column) {
        mArea[line][column].setExist();
    }

    @Override
    public boolean isExist(int widgetId) {
        int[] point = getLineColumn(widgetId);
        return isExist(point[0], point[1]);
    }

    @Override
    public boolean isExist(int line, int column) {
        return mArea[line][column].getExist();
    }

    @Override
    public void setTouched(int line, int column) {
        mArea[line][column].setTouch();
    }

    @Override
    public boolean getTouched(int line, int column) {
        return mArea[line][column].isTouch();
    }

    @Override
    public int[] getLineColumn(int widgetId) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                if (mArea[i][j].getWidgetId() == widgetId) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean checkDuplicate(MapState mapState) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                if (mArea[i][j].getExist() && mapState.isExist(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void addMap(MapState mapState) {
        for (int i = 0; i < MAX_LENGTH; i++) {
            for (int j = 0; j < MAX_LENGTH; j++) {
                if(mapState.isExist(i, j)) {
                    mArea[i][j].setExist();
                }
            }
        }
    }
}
