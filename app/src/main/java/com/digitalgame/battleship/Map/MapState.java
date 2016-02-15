package com.digitalgame.battleship.Map;

public interface MapState {
    int MAX_LENGTH = 5;

    void setWidgetId(int widgetId[][]);

    void setWidgetId(int line, int column, int widgetId);

    void setHitArea(int widgetId);

    void setHitArea(int line, int column);

    boolean isExist(int widgetId);

    boolean isExist(int line, int column);

    void setTouched(int line, int column);

    boolean getTouched(int line, int column);

    int[] getLineColumn(int widgetId);

    boolean checkDuplicate(MapState mapState);

    void addMap(MapState mapState);
}