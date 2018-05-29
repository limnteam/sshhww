package com.sshhww;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sshhww.adapter.FileInfoAdapter;
import com.sshhww.dialog.EditDialog;
import com.sshhww.domain.FileInfo;
import com.sshhww.event.AddFileInfoEvent;
import com.sshhww.event.DeleteFileInfoEvent;
import com.sshhww.event.DownFileEvent;
import com.sshhww.event.EditFileInfoEvent;
import com.sshhww.event.SaveFileToLocalEvent;
import com.sshhww.event.ShowEditFileInfoEvent;
import com.sshhww.utils.DialogUtil;
import com.sshhww.utils.FileUtil;
import com.sshhww.utils.GsonUtil;
import com.sshhww.utils.InitConfig;
import com.sshhww.utils.LogUtil;
import com.sshhww.utils.ShellUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, DownloadListener {
    AlertDialog alertDialog;
    ProgressDialog progressDialog;
    private ListView listView;
    private List<FileInfo> fileInfos;
    FileInfoAdapter fileInfoAdapter;
    private EditDialog editDialog;
    private int DOWNFILE_WHAT = 100;
    private FileInfo currentFileInfo;
    private DownloadQueue queue;

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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        listView = findViewById(R.id.lv);
        String configStr = FileUtil.readLocalConfig(this, FileUtil.LOCAL_CONFIG);
        if (TextUtils.isEmpty(configStr)) {
            fileInfos = InitConfig.initFileInfo();
        }
        else {
            fileInfos = GsonUtil.jsonToList(configStr, FileInfo.class);
        }
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //删除/修改
                alertDialog = DialogUtil.longClickDialog(MainActivity.this, "请选择操作", position);
                return false;
            }
        });
        queue = NoHttp.newDownloadQueue();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 检查文件是否存在
     *
     * @param view
     */
    public void checkFileIsExist(View view) {
        checkFile();
    }


    public void checkFile() {
        if (fileInfos == null) return;
        List<String> stringList = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            stringList.add(fileInfo.getFileName());
        }
        try {
            stringList = FileUtil.checkFilesIsInTmp(stringList);
            for (FileInfo fileInfo : fileInfos) {
                if (stringList.contains(fileInfo.getFileName())) {
                    fileInfo.setExist(false);
                }
                else {
                    fileInfo.setExist(true);
                    //计算md5数值
//                    fileInfo.setMd5Str(ShellUtil.md5Sum(fileInfo.getFileName()));
                    fileInfo.setMd5Str(FileUtil.calcFileMd5(fileInfo.getFileName()));
                }
            }
            fileInfoAdapter = new FileInfoAdapter(this, fileInfos);
            listView.setAdapter(fileInfoAdapter);
        } catch (Exception e) {
            String errorStr = e.getMessage() == null ? "未知错误" : e.getMessage();
            alertDialog = DialogUtil.showDialog(this, "错误", errorStr);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.dismissDialog(alertDialog);
        DialogUtil.dismissDialog(progressDialog);
        DialogUtil.dismissDialog(editDialog);
    }


    @Override
    public void onDownloadError(int what, Exception exception) {
        if (what == DOWNFILE_WHAT && currentFileInfo != null) {
            alertDialog = DialogUtil.showDialog(this, "提示", currentFileInfo.getFileName() + " 下载失败");
        }
    }

    @Override
    public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
        if (what == DOWNFILE_WHAT && currentFileInfo != null) {
            progressDialog = DialogUtil.showProgressDialog(this, currentFileInfo.getFileName() + " 下载中...");
        }
    }

    @Override
    public void onProgress(int what, int progress, long fileCount, long speed) {
        progressDialog.setProgress(progress);
    }

    @Override
    public void onFinish(int what, String filePath) {
        if (what == DOWNFILE_WHAT && currentFileInfo != null) {
            DialogUtil.dismissDialog(progressDialog);
            String fileName = currentFileInfo.getFileName();
            LogUtil.i("替换文件:" + fileName);
            FileUtil.moveFileToTmp(fileName);
            //修改权限
            if (!ShellUtil.changeFilePower(fileName)) {
                Toast.makeText(this, fileName + "修改权限失败！", Toast.LENGTH_SHORT).show();
            }
            //修改文件拥有者
            if (!ShellUtil.changeFileOwner(fileName)) {
                Toast.makeText(this, fileName + "修改文件拥有者失败！", Toast.LENGTH_SHORT).show();
            }
            //修改用户组
            if (!ShellUtil.changeFileGroup(fileName)) {
                Toast.makeText(this, fileName + "修改文件拥有者组失败！", Toast.LENGTH_SHORT).show();
            }
            // 更新
            checkFile();
            Toast.makeText(this, fileName + "已更新！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCancel(int what) {

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSaveFileToLocalEvent(SaveFileToLocalEvent saveFileToLocalEvent) {
        try {
            FileUtil.editConfigToLocal(this, saveFileToLocalEvent.getFileStr(), FileUtil.LOCAL_CONFIG);
        } catch (Exception e) {
            LogUtil.w("保存失败！");
        }
    }


    public String filterFileInfos() {
        if (this.fileInfos == null) return null;
        List<FileInfo> newFileInfos = new ArrayList<>();
        for (FileInfo fileInfo : this.fileInfos) {
            FileInfo newFileInfo = (FileInfo) fileInfo.clone();
            newFileInfos.add(newFileInfo);
        }
        return GsonUtil.toJson(newFileInfos);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddFileInfoEvent(AddFileInfoEvent addFileInfoEvent) {
        //内存
        fileInfos.add(addFileInfoEvent.getFileInfo());
        // 本地文件
        SaveFileToLocalEvent saveFileToLocalEvent = new SaveFileToLocalEvent(filterFileInfos());
        EventBus.getDefault().post(saveFileToLocalEvent);
        // 更新
        checkFile();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEditFileInfoEvent(EditFileInfoEvent editFileInfoEvent) {
        //内存
        fileInfos.remove(editFileInfoEvent.getPosition());
        fileInfos.add(editFileInfoEvent.getPosition(), editFileInfoEvent.getFileInfo());
        //本地文件
        SaveFileToLocalEvent saveFileToLocalEvent = new SaveFileToLocalEvent(filterFileInfos());
        EventBus.getDefault().post(saveFileToLocalEvent);
        // 更新
        checkFile();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeleteFileInfoEvent(DeleteFileInfoEvent deleteFileInfoEvent) {
        if (fileInfoAdapter != null) {
            fileInfoAdapter.delete(deleteFileInfoEvent.getPosition());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowEditFileInfoEvent(ShowEditFileInfoEvent showEditFileInfoEvent) {
        if (fileInfos != null && fileInfos.size() > showEditFileInfoEvent.getPosition()) {
            FileInfo fileInfo = fileInfos.get(showEditFileInfoEvent.getPosition());
            editDialog = new EditDialog(this, fileInfo);
            editDialog.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownFileEvent(DownFileEvent downFileEvent) {
        this.currentFileInfo = downFileEvent.getFileInfo();
        DownloadRequest request = new DownloadRequest(this.currentFileInfo.getFileRemoteUrl(),
                RequestMethod.GET, getFilesDir().getAbsolutePath(), this.currentFileInfo.getFileName(),
                false, true);
        queue.add(DOWNFILE_WHAT, request, this);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: //添加
                editDialog = new EditDialog(this, null);
                editDialog.show();
                break;
            case R.id.action_setting: // 获取应用信息列表
                Intent intent = new Intent(this, AppInfoListActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }
}
