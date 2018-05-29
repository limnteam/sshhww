package com.sshhww.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class FileUtil {
    public static String LOCAL_CONFIG = "config.aa";
    public static String LOCAL_FILTER = "filter.aa";

    public static void checkFilesIsExist(String dirPath, List<String> fileNames) {
        if (dirPath == null) throw new IllegalArgumentException("dirPath 为 null");
        LogUtil.i("dirPath:" + dirPath);
        if (fileNames == null) throw new IllegalArgumentException("fileNames 为 null");
        LogUtil.i("fileNames:" + fileNames.toString());
        File dirFile = new File(dirPath);
        if (!dirFile.isDirectory()) throw new IllegalArgumentException(dirPath + "不是文件夹");
        LogUtil.i("" + dirFile.getAbsolutePath());
        File[] sonFile = dirFile.listFiles();
        if (sonFile == null) throw new RuntimeException("查询的文件不存在");
        for (File file : sonFile) {
            if (file == null || file.isDirectory()) continue;
            String fileName = file.getName();
            LogUtil.i("fileName:" + fileName);
            if (fileNames.contains(fileName)) {
                fileNames.remove(fileName);
            }
        }
        if (fileNames.size() != 0)
            throw new RuntimeException(dirPath + "下缺少" + fileNames.toString());
    }


    public static List<String> checkFilesIsInTmp(List<String> checkFiles) {
        List<String> fileNames = ShellUtil.getDirFiles(ShellUtil.DIRECTIONPATH);
        if (checkFiles == null) throw new IllegalArgumentException("checkFiles 为 null");
        if (fileNames == null) throw new RuntimeException("查询的文件不存在");
        for (String fileName : fileNames) {
            if (checkFiles.contains(fileName)) {
                checkFiles.remove(fileName);
            }
        }
        return checkFiles;
    }

    public static void listFile(String dirPath) {
        if (dirPath == null) return;
        LogUtil.i("当前文件夹：" + dirPath);
        File file = new File(dirPath);
        if (file.exists() && file.isDirectory()) {
            String[] fileNames = file.list();
            if (fileNames == null) return;
            for (String fileName : fileNames) {
                LogUtil.i("fileName:" + fileName);
            }
        }
        else {
            LogUtil.w(dirPath + "不存在或不是文件夹");
        }
    }

    public static void moveFileToTmp(String fileName) {
        ShellUtil.moveFile("/data/data/com.sshhww/files", ShellUtil.DIRECTIONPATH + "/", fileName);
    }


    public static void editConfigToLocal(Context context, String content, String localFileName) throws Exception {
        LogUtil.i(content + "");
        String localFilePath = context.getFilesDir().getAbsolutePath();
        File file = new File(localFilePath + "/" + localFileName);
        if (file.exists()) {
            file.createNewFile();
        }
        final FileOutputStream fos = new FileOutputStream(file, false);
        byte[] buffer;
        try {
            buffer = content.getBytes();
            fos.write(buffer);
            fos.flush();
        } catch (IOException ioe) {
            LogUtil.w(ioe.getMessage());
        } finally {
            fos.close();
            buffer = null;
        }
    }

    public static String readLocalConfig(Context context, String localFileName) {
        StringBuffer sDest = new StringBuffer();
        try {
            String localFilePath = context.getFilesDir().getAbsolutePath();
            File file = new File(localFilePath + "/" + localFileName);
            if (!file.exists()) {
                return null;
            }
            FileInputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                String data;
                while ((data = br.readLine()) != null) {
                    sDest.append(data.trim() + "\n");
                }
            } catch (IOException ioex) {
                LogUtil.w(ioex.getMessage());
            } finally {
                is.close();
                is = null;
                br.close();
                br = null;
            }
        } catch (Exception ex) {
            LogUtil.w(ex.getMessage());
        }
        return sDest.toString().trim();
    }


    public static String calcFileMd5(String fileName) {
        File file = new File(ShellUtil.DIRECTIONPATH + "/" + fileName);
        if (!file.exists()) {
            return null;
        }
        else {
            try {
                return getMd5ByFile(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }


    private static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

}
