package com.digitalgame.battleship.Map;

public interface AreaState {
    boolean EXIST_YES = true;
    boolean EXIST_NO = false;
    boolean TOUCH_YET = true;
    boolean TOUCH_ALREADY = false;

    int getWidgetId();

    void setWidgetId(int id);

    void setExist();

    boolean getExist();

    void setTouch();

    boolean isTouch();
}