package com.geekapps.andrii.gallerypicker.picker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.filepicker.FilePickerActivity;
import com.geekapps.andrii.gallerypicker.activity.fullscreen.ImageFullScreenActivity;
import com.geekapps.andrii.gallerypicker.activity.fullscreen.VideoFullScreenActivity;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivity;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnShowImageEvent;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnShowVideoEvent;
import com.geekapps.andrii.gallerypicker.recycler.adapter.SelectedFileListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.recycler.adapter.SelectedMediaListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.PermissionHelper;
import com.geekapps.andrii.gallerypicker.view.PlaceholderRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class PickerDialogFragment extends BottomSheetDialogFragment {

    public static final String TAG = PickerDialogFragment.class.getSimpleName();
    private static final int MEDIA_REQUEST_CODE = 99;
    private static final int FILE_REQUEST_CODE = 98;
    private static final int SPAN_COUNT = 3;
    private static final int REQUEST_IMAGE_CODE = 56;
    private static final int REQUEST_VIDEO_CODE = 57;
    private static final int REQUEST_FILE_CODE = 58;

    @BindView(R.id.image_video_picked_values_recycler)
    PlaceholderRecyclerView mImageVideoRecycler;
    @BindView(R.id.files_picked_recycler)
    PlaceholderRecyclerView mFileRecycler;
    @BindView(R.id.chose_image_tv)
    TextView mChoseImageTv;
    @BindView(R.id.chose_video_tv)
    TextView mChoseVideoTv;
    @BindView(R.id.chose_file_tv)
    TextView mChoseFileTv;

    private SelectedMediaListRecyclerAdapter mMediaFilesAdapter;
    private SelectedFileListRecyclerAdapter mFileAdapter;

    public static PickerDialogFragment newInstance() {
        Bundle args = new Bundle();
        PickerDialogFragment fragment = new PickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_fragment_picker, container);
        ButterKnife.bind(this, root);
        EventBus.getDefault().register(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMediaFilesRecycler();
        initFilesRecycler();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenImageEvent(OnShowImageEvent event) {
        ImageFullScreenActivity.start(getContext(), event.getMediaModels(), event.getSelectedPosition());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenVideoEvent(OnShowVideoEvent event) {
        VideoFullScreenActivity.start(getContext(), event.getSelectedItem().getMediaPath());
    }

    private void initFilesRecycler() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mFileAdapter = new SelectedFileListRecyclerAdapter(getContext());
        mFileRecycler.setLayoutManager(layoutManager);
        mFileRecycler.setAdapter(mFileAdapter);
    }

    private void initMediaFilesRecycler() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mMediaFilesAdapter = new SelectedMediaListRecyclerAdapter();
        mImageVideoRecycler.setAdapter(mMediaFilesAdapter);
        mImageVideoRecycler.setLayoutManager(layoutManager);
    }

    @OnClick(R.id.chose_image_tv)
    public void onChoseImageClicked() {
        if (PermissionHelper.isReadStoragePermissionGranted(getContext(), REQUEST_IMAGE_CODE)) {
            Intent intent = new Intent(getContext(), GalleryActivity.class);
            intent.putExtra(GalleryActivity.MODE_PREF, GalleryActivity.IMAGE_MODE);
            startActivityForResult(intent, MEDIA_REQUEST_CODE);
        }
    }

    @OnClick(R.id.chose_video_tv)
    public void onChoseVideoClicked() {
        if (PermissionHelper.isReadStoragePermissionGranted(getContext(), REQUEST_VIDEO_CODE)) {
            Intent intent = new Intent(getContext(), GalleryActivity.class);
            intent.putExtra(GalleryActivity.MODE_PREF, GalleryActivity.VIDEO_MODE);
            startActivityForResult(intent, MEDIA_REQUEST_CODE);
        }
    }

    @OnClick(R.id.chose_file_tv)
    public void onChoseFileClicked() {
        if (PermissionHelper.isReadStoragePermissionGranted(getContext(), REQUEST_FILE_CODE)) {
            Intent intent = new Intent(getContext(), FilePickerActivity.class);
            startActivityForResult(intent, FILE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_IMAGE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onChoseImageClicked();
                }
                break;
            case REQUEST_VIDEO_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onChoseVideoClicked();
                }
                break;
            case REQUEST_FILE_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onChoseFileClicked();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == MEDIA_REQUEST_CODE) {
            if (PickerMediaDataHolder.hasData()) {
                List<MediaModel> mediaModels = PickerMediaDataHolder.getData();
                mMediaFilesAdapter.add(mediaModels);
            }
            return;
        }

        if (resultCode == RESULT_OK && requestCode == FILE_REQUEST_CODE) {
            if (PickerFileDataHolder.hasData()) {
                mFileAdapter.addFile(PickerFileDataHolder.getData());
            }
        }
    }

    public enum PickerMediaDataHolder {
        INSTANCE;

        private List<MediaModel> mObjectList;

        public static boolean hasData() {
            return INSTANCE.mObjectList != null;
        }

        public static List<MediaModel> getData() {
            final List<MediaModel> retList = INSTANCE.mObjectList;
            INSTANCE.mObjectList = null;
            return retList;
        }

        public static void setData(final List<MediaModel> objectList) {
            INSTANCE.mObjectList = objectList;
        }
    }

    public enum PickerFileDataHolder {
        INSTANCE;

        private List<File> mObjectList;

        public static boolean hasData() {
            return INSTANCE.mObjectList != null;
        }

        public static List<File> getData() {
            final List<File> retList = INSTANCE.mObjectList;
            INSTANCE.mObjectList = null;
            return retList;
        }

        public static void setData(final List<File> objectList) {
            INSTANCE.mObjectList = objectList;
        }
    }
}
