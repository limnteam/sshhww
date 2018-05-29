package com.sshhww.utils;

import com.jaredrummler.android.shell.CommandResult;
import com.jaredrummler.android.shell.Shell;

import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class ShellUtil {
    public static String DIRECTIONPATH = "/data/local/temp";

    public static List<String> getDirFiles(String dirPath) {
        CommandResult result = Shell.SU.run("cd " + dirPath, "ls");
        if (!result.isSuccessful()) {
            LogUtil.i(result.getStderr());
            return null;
        }
        else {
            String strResult = result.getStdout();
            LogUtil.i(strResult);
            return result.stdout;
        }
    }

    public static boolean moveFile(String oldPath, String newPath, String fileName) {
        CommandResult result = Shell.SU.run("mv " + oldPath + "/" + fileName + " " + newPath);
        return result.isSuccessful();
    }

    public static String md5Sum(String fileName) {
        CommandResult result = Shell.SU.run("md5sum " + fileName);
        if (result.isSuccessful()) {
            return result.getStdout().split("\\s+")[0];
        }
        else {
            LogUtil.w(result.getStderr());
            return "未知";
        }
    }

    /**
     * 修改文件权限
     *
     * @param fileName
     */
    public static boolean changeFilePower(String fileName) {
        CommandResult result = Shell.SU.run("chmod 777 " + DIRECTIONPATH + "/" + fileName);
        return result.isSuccessful();
    }

    /**
     * 修改文件拥有者
     *
     * @param fileName
     * @return
     */
    public static boolean changeFileOwner(String fileName) {
        CommandResult result = Shell.SU.run("chown shell " + DIRECTIONPATH + "/" + fileName);
        return result.isSuccessful();
    }

    public static boolean changeFileGroup(String fileName) {
        CommandResult result = Shell.SU.run("chgrp shell " + DIRECTIONPATH + "/" + fileName);
        return result.isSuccessful();
    }
}
