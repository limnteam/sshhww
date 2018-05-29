package com.sshhww.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sshhww.R;
import com.sshhww.event.DeleteFilterEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/20
 * describe:
 */
public class FilterAdapter extends MyAdapter<String> {
    public FilterAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_filter,null);
            viewHolder = new ViewHolder();
            viewHolder.tvPackage = convertView.findViewById(R.id.tv_package);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvPackage.setText(dataList.get(position));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFilterEvent deleteFilterEvent = new DeleteFilterEvent(position);
                EventBus.getDefault().post(deleteFilterEvent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvPackage;
        Button btnDelete;
    }
}
