package com.geekapps.andrii.gallerypicker.activity.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.fullscreen.ImageFullScreenActivity;
import com.geekapps.andrii.gallerypicker.activity.fullscreen.VideoFullScreenActivity;
import com.geekapps.andrii.gallerypicker.fragment.mainfragment.MainFragment;
import com.geekapps.andrii.gallerypicker.fragment.medialistfragment.MediaListFragment;
import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.picker.PickerDialogFragment;

import java.util.List;

import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity implements GalleryActivityController {

    public static final int IMAGE_MODE = 0;
    public static final int VIDEO_MODE = 1;
    public static final String MODE_PREF = "IMAGE_MODE_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        showImageVideoFragment();
    }

    private void showImageVideoFragment() {
        int mode = getIntent().getIntExtra(MODE_PREF, 0);
        MainFragment imageVideoFragment = MainFragment.newInstance(mode);
        replaceFragment(imageVideoFragment, false, MainFragment.TAG);
    }

    @Override
    public void goToMediaFragment(AlbumModel albumModel) {
        addFragment(MediaListFragment.newInstance(albumModel), true, MediaListFragment.TAG);
    }

    @Override
    public void goToPhotoFullScreen(List<MediaModel> mediaModels, int selectedPosition) {
        ImageFullScreenActivity.start(this, mediaModels, selectedPosition);
    }

    @Override
    public void goToVideoPlayer(MediaModel mediaModel) {
        VideoFullScreenActivity.start(this, mediaModel.getMediaPath());
    }

    @Override
    public void setResult(List<MediaModel> selectedValues) {
        PickerDialogFragment.PickerMediaDataHolder.setData(selectedValues);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    private void replaceFragment(Fragment fragment, boolean isAddToBackstack, String tag) {
        Fragment foundFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (foundFragment != null && foundFragment.isVisible()) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag);
        if (isAddToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    private void addFragment(Fragment fragment, boolean isAddToBackstack, String tag) {
        Fragment foundFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (foundFragment != null && foundFragment.isVisible()) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        if (isAddToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

}
