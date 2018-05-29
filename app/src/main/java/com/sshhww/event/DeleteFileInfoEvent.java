package com.sshhww.event;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class DeleteFileInfoEvent {
    private int position;
    public DeleteFileInfoEvent(int position){
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
