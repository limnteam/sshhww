package com.sshhww.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sshhww.R;
import com.sshhww.domain.AppInfo;

import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class AppInfoAdapter extends MyAdapter<AppInfo> {
    private List<String> stringList;
    public AppInfoAdapter(Context context, List<AppInfo> dataList) {
        super(context, dataList);
        this.stringList = stringList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_app_info, null);
            viewHolder = new ViewHolder();
            viewHolder.tvAppName = convertView.findViewById(R.id.tv_app_name);
            viewHolder.tvVersionName = convertView.findViewById(R.id.tv_version_name);
            viewHolder.tvVersionCode = convertView.findViewById(R.id.tv_version_code);
            viewHolder.tvPackage = convertView.findViewById(R.id.tv_package);
            viewHolder.llLayout = convertView.findViewById(R.id.ll_layout);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppInfo appInfo = dataList.get(position);

        if (appInfo.getSelect()) {
            viewHolder.llLayout.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else {
            viewHolder.llLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        viewHolder.tvAppName.setText(appInfo.getAppName());
        viewHolder.tvPackage.setText(appInfo.getPackageName());
        viewHolder.tvVersionCode.setText(String.valueOf(appInfo.getVersionCode()));
        viewHolder.tvVersionName.setText(appInfo.getVersionName());
        return convertView;
    }

    public class ViewHolder {
        TextView tvAppName;
        TextView tvVersionName;
        TextView tvVersionCode;
        TextView tvPackage;
        LinearLayout llLayout;
    }
}
