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
import com.sshhww.event.AddFilterEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class AddDialog extends Dialog implements View.OnClickListener {
    private EditText fileName;
    private Button btnOk;
    private Button btnCancel;
    private TextView tvTitle;


    public AddDialog(@NonNull Context context) {
        this(context, R.style.myDialogTheme);
    }

    public AddDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_add);
        fileName = findViewById(R.id.et_package);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("添加");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                String fileNameStr = fileName.getText().toString().trim();
                if (TextUtils.isEmpty(fileNameStr)) {
                    Toast.makeText(getContext(), "请输入包名", Toast.LENGTH_SHORT).show();
                    return;
                }
                AddFilterEvent addFilterEvent = new AddFilterEvent(fileNameStr);
                EventBus.getDefault().post(addFilterEvent);
                Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();

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
