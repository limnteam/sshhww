package com.sshhww.event;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class SaveFileToLocalEvent {
    private String fileStr;
    public SaveFileToLocalEvent(String fileStr){
        this.fileStr = fileStr;
    }

    public String getFileStr() {
        return fileStr;
    }
}
