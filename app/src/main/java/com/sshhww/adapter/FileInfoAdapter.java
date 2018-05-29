package com.sshhww.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sshhww.R;
import com.sshhww.domain.FileInfo;
import com.sshhww.event.DownFileEvent;
import com.sshhww.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public class FileInfoAdapter extends MyAdapter<FileInfo> {
    public FileInfoAdapter(Context context, List<FileInfo> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_file_info, null);
            viewHolder.tvFileName = convertView.findViewById(R.id.tv_file_name);
            viewHolder.tvMd5Str = convertView.findViewById(R.id.tv_md5_str);
            viewHolder.btnUpdate = convertView.findViewById(R.id.btn_update);
            viewHolder.rlLayout = convertView.findViewById(R.id.rl_layout);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final FileInfo fileInfo = dataList.get(position);
        viewHolder.tvFileName.setText(fileInfo.getFileName());
        viewHolder.tvMd5Str.setText(fileInfo.getMd5Str());
        if (fileInfo.getExist() == null) {
            viewHolder.rlLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
            viewHolder.btnUpdate.setVisibility(View.GONE);
        }
        else {
            viewHolder.btnUpdate.setVisibility(View.VISIBLE);
            if (fileInfo.getExist()) {
                viewHolder.rlLayout.setBackgroundColor(context.getResources().getColor(R.color.green));
                viewHolder.btnUpdate.setText("更新");
            }
            else {
                viewHolder.rlLayout.setBackgroundColor(context.getResources().getColor(R.color.red));
                viewHolder.btnUpdate.setText("下载");
            }
        }
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("下载：" + fileInfo.getFileRemoteUrl());
                DownFileEvent downFileEvent = new DownFileEvent(fileInfo);
                EventBus.getDefault().post(downFileEvent);

            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvFileName;
        TextView tvMd5Str;
        Button btnUpdate;
        RelativeLayout rlLayout;
    }
}
