package com.digitalgame.battleship.MapArea;


public class AreaStateImpl implements AreaState {
    private int mWidgetId;
    private boolean mExist;
    private boolean mTouch;

    public AreaStateImpl() {
        mExist = EXIST_NO;
        mTouch = TOUCH_YET;
    }

    @Override
    public int getWidgetId() {
        return mWidgetId;
    }

    @Override
    public void setWidgetId(int widgetId) {
        this.mWidgetId = widgetId;
    }

    @Override
    public void setExist() {
        mExist = EXIST_YES;
    }

    @Override
    public boolean getExist() {
        return mExist;
    }

    @Override
    public void setTouch() {
        mTouch = TOUCH_ALREADY;
    }

    @Override
    public boolean isTouch() {
        return mTouch;
    }
}