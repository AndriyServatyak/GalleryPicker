package com.geekapps.andrii.gallerypicker.fragment.medialistfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.model.eventbus.OnRefreshEvent;
import com.geekapps.andrii.gallerypicker.recycler.adapter.MediaListRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.util.Utils;
import com.geekapps.andrii.gallerypicker.view.PlaceholderRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MediaListFragment extends Fragment {

    public static final String TAG = MediaListFragment.class.getSimpleName();
    private static final String MEDIA_MODELS_PREF = "MEDIA_MODELS_PREF";
    private static final int SPAN_COUNT = 3;

    @BindView(R.id.back_btn)
    ImageView mBackIv;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitleTv;
    @BindView(R.id.selected_counter_tv)
    TextView mSelectedCounterTv;
    @BindView(R.id.media_files_recycler_view)
    PlaceholderRecyclerView mMediaFilesRecyclerView;
    @BindView(R.id.confirm_chosen_media_files_fab)
    FloatingActionButton mConfirmFab;

    private GalleryActivityController mController;
    private MediaListRecyclerAdapter mAdapter;

    public MediaListFragment() {
    }

    public static MediaListFragment newInstance(AlbumModel albumModel) {
        Bundle args = new Bundle();
        args.putSerializable(MEDIA_MODELS_PREF, albumModel);
        MediaListFragment fragment = new MediaListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mController = (GalleryActivityController) activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_media_list, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tryGetArguments();
        EventBus.getDefault().register(this);
    }

    private void tryGetArguments() {
        if (getArguments() != null) {
            AlbumModel albumModel = (AlbumModel) getArguments().getSerializable(MEDIA_MODELS_PREF);
            initRecycler(albumModel);
            initViews(albumModel);
        }
    }

    private void initViews(AlbumModel albumModel) {
        mToolbarTitleTv.setText(albumModel.getAlbumName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(OnRefreshEvent event) {
        List<MediaModel> selectedItems = event.getSelectedItems();
        if (Utils.isEmpty(selectedItems)) {
            mConfirmFab.hide();
            mSelectedCounterTv.setVisibility(View.GONE);
        } else {
            mConfirmFab.show();
            mSelectedCounterTv.setVisibility(View.VISIBLE);
            mSelectedCounterTv.setText(String.valueOf(selectedItems.size()));
        }
    }

    private void initRecycler(AlbumModel albumModel) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mAdapter = new MediaListRecyclerAdapter(mController);
        mMediaFilesRecyclerView.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty_placeholder, null));
        mMediaFilesRecyclerView.setLayoutManager(layoutManager);
        mMediaFilesRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(albumModel.getMediaModels());
    }

    @OnClick(R.id.back_btn)
    public void onBackClicked() {
        mController.goBack();
    }

    @OnClick(R.id.confirm_chosen_media_files_fab)
    public void onConfirmClicked() {
        mController.setResult(mAdapter.getSelectedItems());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
