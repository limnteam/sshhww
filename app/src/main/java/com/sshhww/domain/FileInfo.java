package com.sshhww.domain;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class FileInfo implements Cloneable {
    private String fileName;
    private String fileRemoteUrl;
    private String md5Str;
    private Boolean exist;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRemoteUrl() {
        return fileRemoteUrl;
    }

    public void setFileRemoteUrl(String fileRemoteUrl) {
        this.fileRemoteUrl = fileRemoteUrl;
    }

    public String getMd5Str() {
        return md5Str;
    }

    public void setMd5Str(String md5Str) {
        this.md5Str = md5Str;
    }

    public Boolean getExist() {
        return exist;
    }

    public void setExist(Boolean exist) {
        this.exist = exist;
    }

    public Object clone() {
        FileInfo fileInfo = null;
        try {
            fileInfo = (FileInfo) super.clone();
            fileInfo.setMd5Str(null);
            fileInfo.setExist(null);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return fileInfo;
    }
}
