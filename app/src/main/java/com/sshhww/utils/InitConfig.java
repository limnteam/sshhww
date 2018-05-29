package com.sshhww.utils;

import com.sshhww.domain.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class InitConfig {
    public static List<FileInfo> initFileInfo() {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo();
        fileInfo1.setFileName("sshhwwstart.sh");
        fileInfo1.setFileRemoteUrl("http://120.26.205.248:8080/apk/sshhww/sshhwwstart.sh");
        fileInfos.add(fileInfo1);
        FileInfo fileInfo2 = new FileInfo();
        fileInfo2.setFileName("sshhwwstrap.jar");
        fileInfo2.setFileRemoteUrl("http://120.26.205.248:8080/apk/sshhww/sshhwwstrap.jar");
        fileInfos.add(fileInfo2);
        return fileInfos;
    }
}
