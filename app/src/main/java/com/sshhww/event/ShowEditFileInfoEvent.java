package com.sshhww.event;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class ShowEditFileInfoEvent {
    private int position;

    public ShowEditFileInfoEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
