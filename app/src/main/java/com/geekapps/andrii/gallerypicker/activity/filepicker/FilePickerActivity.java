package com.geekapps.andrii.gallerypicker.activity.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnDefaultFolderClickedEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnFileSelectEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnFolderClickedEvent;
import com.geekapps.andrii.gallerypicker.picker.PickerDialogFragment;
import com.geekapps.andrii.gallerypicker.recycler.adapter.FileListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.Utils;
import com.geekapps.andrii.gallerypicker.view.PlaceholderRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilePickerActivity extends AppCompatActivity implements FilePickerContract.View {

    @BindView(R.id.back_btn)
    ImageView mBackIv;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitleTv;
    @BindView(R.id.files_recycler_view)
    PlaceholderRecyclerView mFileRecyclerView;
    @BindView(R.id.selected_counter_tv)
    TextView mCounterTv;
    @BindView(R.id.confirm_chosen_files_fab)
    FloatingActionButton mConfirmFab;

    private FilePickerContract.Presenter mPresenter;
    private FileListRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        ButterKnife.bind(this);
        mToolbarTitleTv.setText(R.string.files);
        mPresenter = new FilePickerPresenter(this);
        initRecycler();
        mPresenter.getDirectories(Environment.getExternalStorageDirectory().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new FileListRecyclerAdapter();
        mFileRecyclerView.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty_placeholder, null));
        mFileRecyclerView.setLayoutManager(layoutManager);
        mFileRecyclerView.setAdapter(mAdapter);
        mFileRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFolderClickedEvent(OnFolderClickedEvent event) {
        File file = event.getFile();
        setupTitle(file);
        mPresenter.getDirectories(file.getPath());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDefaultFolderClickedEvent(OnDefaultFolderClickedEvent event) {
        onBackPressed();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFileSelectEvent(OnFileSelectEvent event) {
        List<File> selectedFiles = mAdapter.getSelectedFiles();
        if (Utils.isEmpty(selectedFiles)) {
            mCounterTv.setVisibility(View.GONE);
            mConfirmFab.hide();
        } else {
            mCounterTv.setVisibility(View.VISIBLE);
            mCounterTv.setText(String.valueOf(selectedFiles.size()));
            mConfirmFab.show();
        }
    }

    @OnClick(R.id.confirm_chosen_files_fab)
    public void onConfirmClicked() {
        PickerDialogFragment.PickerFileDataHolder.setData(mAdapter.getSelectedFiles());
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.back_btn)
    public void onBackPress() {
        onBackPressed();
    }

    @Override
    public void onGetDirectoriesSuccess(List<File> files) {
        mAdapter.addAll(files);
    }

    @Override
    public void onGetDirectoriesFail() {

    }

    @Override
    public void onBackPressed() {
        File previousSelectedFolder = mAdapter.getPreviousSelectedFolder();
        if (previousSelectedFolder != null) {
            mPresenter.getDirectories(previousSelectedFolder.getPath());
            mAdapter.removeFolder(previousSelectedFolder);
            setupTitle(previousSelectedFolder);
        } else {
            super.onBackPressed();
        }
    }

    private void setupTitle(File file) {
        if (mAdapter.isLastFolder()) {
            mToolbarTitleTv.setText(R.string.files);
        } else {
            mToolbarTitleTv.setText(file.getName());
        }
    }
}
