package com.sshhww.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.sshhww.R;
import com.sshhww.event.DeleteFileInfoEvent;
import com.sshhww.event.ShowEditFileInfoEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class DialogUtil {
    public static AlertDialog showDialog(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.show();
    }

    public static AlertDialog longClickDialog(Context context, String title, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton(R.string.dialog_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShowEditFileInfoEvent showEditFileInfoEvent = new ShowEditFileInfoEvent(position);
                EventBus.getDefault().post(showEditFileInfoEvent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeleteFileInfoEvent deleteFileInfoEvent = new DeleteFileInfoEvent(position);
                EventBus.getDefault().post(deleteFileInfoEvent);
                dialog.dismiss();
            }
        });

        builder.setNeutralButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.show();
    }


    public static ProgressDialog showProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
        progressDialog.setTitle("提示");
        progressDialog.setMessage(msg);
        progressDialog.setMax(100);
        progressDialog.show();
        return progressDialog;
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }
}
