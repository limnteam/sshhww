package com.sshhww.event;

import com.sshhww.domain.FileInfo;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class DownFileEvent {
    private FileInfo fileInfo;

    public DownFileEvent(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }
}
