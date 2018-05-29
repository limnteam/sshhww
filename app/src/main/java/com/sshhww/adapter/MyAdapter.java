package com.sshhww.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author： xiongdejin
 * date: 2018/4/19
 * describe:
 */
public abstract class MyAdapter<T> extends BaseAdapter {
    protected List<T> dataList;
    protected Context context;

    public MyAdapter(Context context, List<T> dataList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        this.dataList = dataList;
        this.context = context;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void delete(int position) {
        if (position < dataList.size()) {
            dataList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void add(T t) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.add(t);
        notifyDataSetChanged();
    }
}
