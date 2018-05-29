package com.sshhww;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sshhww.adapter.AppInfoAdapter;
import com.sshhww.domain.AppInfo;
import com.sshhww.utils.AppUtil;
import com.sshhww.utils.FileUtil;
import com.sshhww.utils.GsonUtil;

import java.util.Collections;
import java.util.List;

public class AppInfoListActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private AppInfoAdapter appInfoAdapter;
    private ListView appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info_list);

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

        toolbar.setOnMenuItemClickListener(this);

        appInfoList = findViewById(R.id.lv_app_info);

        List<AppInfo> appInfos = AppUtil.getAppPackageInfo(this);
        appInfoAdapter = new AppInfoAdapter(this, appInfos);
        appInfoList.setAdapter(appInfoAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> stringList = GsonUtil.jsonToList(FileUtil.readLocalConfig(this, FileUtil.LOCAL_FILTER), String.class);
        if (appInfoAdapter != null && appInfoAdapter.getDataList() != null) {
            for (AppInfo appInfo : appInfoAdapter.getDataList()) {
                appInfo.setSelect(stringList.contains(appInfo.getPackageName()));
            }
            Collections.sort(appInfoAdapter.getDataList());
            appInfoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_info_menu, menu);
        return true;
    }
}
