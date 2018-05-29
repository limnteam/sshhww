package com.sshhww.event;

import com.sshhww.domain.FileInfo;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class EditFileInfoEvent {
    private FileInfo fileInfo;
    private int position;
    public EditFileInfoEvent(FileInfo fileInfo,int position){
        this.fileInfo = fileInfo;
        this.position = position;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public int getPosition() {
        return position;
    }
}
