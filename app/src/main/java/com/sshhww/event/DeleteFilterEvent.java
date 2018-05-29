package com.sshhww.event;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class DeleteFilterEvent {
    private int position;

    public DeleteFilterEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
