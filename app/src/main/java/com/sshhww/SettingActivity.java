package com.sshhww;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sshhww.adapter.FilterAdapter;
import com.sshhww.dialog.AddDialog;
import com.sshhww.event.AddFilterEvent;
import com.sshhww.event.DeleteFilterEvent;
import com.sshhww.utils.DialogUtil;
import com.sshhww.utils.FileUtil;
import com.sshhww.utils.GsonUtil;
import com.sshhww.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private ListView lvFilter;
    private List<String> packageNames;
    private FilterAdapter filterAdapter;
    private AddDialog addDialog;

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvFilter = findViewById(R.id.lv_filter);
        String filterNameList = FileUtil.readLocalConfig(this, FileUtil.LOCAL_FILTER);
        LogUtil.i("filterNameList:"+filterNameList);
        if (filterNameList == null) {
            packageNames = new ArrayList<>();
        }
        else {
            packageNames = GsonUtil.jsonToList(filterNameList, String.class);
        }
        filterAdapter = new FilterAdapter(this, packageNames);
        lvFilter.setAdapter(filterAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteFilterEvent(DeleteFilterEvent deleteFilterEvent) {
        if (packageNames != null && packageNames.size() > deleteFilterEvent.getPosition()) {
            filterAdapter.delete(deleteFilterEvent.getPosition());
            try {
                FileUtil.editConfigToLocal(this, GsonUtil.toJson(packageNames), FileUtil.LOCAL_FILTER);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "本地保存失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddFilterEvent(AddFilterEvent addFilterEvent) {
        if (filterAdapter != null) {
            filterAdapter.add(addFilterEvent.getPackageName());
            this.packageNames = this.filterAdapter.getDataList();
            try {
                FileUtil.editConfigToLocal(this, GsonUtil.toJson(packageNames), FileUtil.LOCAL_FILTER);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "本地保存失败！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 添加筛选的包名
     *
     * @param view
     */
    public void addFilterPackage(View view) {
        addDialog = new AddDialog(this);
        addDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.dismissDialog(addDialog);
    }
}
