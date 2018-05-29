package com.sshhww.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sshhww.R;
import com.sshhww.domain.FileInfo;
import com.sshhww.event.AddFileInfoEvent;
import com.sshhww.event.EditFileInfoEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class EditDialog extends Dialog implements View.OnClickListener {
    private EditText fileName;
    private EditText remoteUrl;
    private Button btnOk;
    private Button btnCancel;
    private TextView tvTitle;
    private FileInfo fileInfo;
    private int position;

    public void setPosition(int position) {
        this.position = position;
    }

    public EditDialog(@NonNull Context context, FileInfo fileInfo) {
        this(context, R.style.myDialogTheme, fileInfo);
    }

    public EditDialog(@NonNull Context context, int themeResId, FileInfo fileInfo) {
        super(context, themeResId);
        this.fileInfo = fileInfo;
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_edit);
        fileName = findViewById(R.id.et_fileName);
        remoteUrl = findViewById(R.id.et_remote_url);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        tvTitle = findViewById(R.id.tv_title);
        if (fileInfo == null) {
            tvTitle.setText("添加");
        }
        else {
            tvTitle.setText("修改");
            fileName.setText(fileInfo.getFileName());
            remoteUrl.setText(fileInfo.getFileRemoteUrl());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String fileNameStr = fileName.getText().toString().trim();
                String remoteUrlStr = remoteUrl.getText().toString().trim();
                if (TextUtils.isEmpty(fileNameStr)) {
                    Toast.makeText(getContext(), "请输入文件名", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(remoteUrlStr)) {
                    Toast.makeText(getContext(), "请输入下载地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fileInfo == null) {
                    fileInfo = new FileInfo();
                    fileInfo.setFileName(fileNameStr);
                    fileInfo.setFileRemoteUrl(remoteUrlStr);
                    AddFileInfoEvent addFileInfoEvent = new AddFileInfoEvent(fileInfo);
                    EventBus.getDefault().post(addFileInfoEvent);
                    Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    fileInfo.setFileName(fileNameStr);
                    fileInfo.setFileRemoteUrl(remoteUrlStr);
                    EditFileInfoEvent editFileInfoEvent = new EditFileInfoEvent(fileInfo, this.position);
                    EventBus.getDefault().post(editFileInfoEvent);
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                }
                dismiss();

                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        if (window != null) {
            //这句设置我们dialog在底部
            WindowManager.LayoutParams lp = window.getAttributes();
            //这句就是设置dialog横向满屏了。
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
    }
}
