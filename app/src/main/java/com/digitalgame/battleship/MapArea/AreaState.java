package com.digitalgame.battleship.MapArea;

public interface AreaState {
    boolean EXIST_YES = true;
    boolean EXIST_NO = false;
    boolean TOUCH_YET = true;
    boolean TOUCH_ALREADY = false;

    /**
     * get R.id of buttons
     */
    int getWidgetId();

    /**
     * set R.id of buttons
     */
    void setWidgetId(int id);

    void setExist();

    boolean getExist();

    void setTouch();

    boolean isTouch();
}