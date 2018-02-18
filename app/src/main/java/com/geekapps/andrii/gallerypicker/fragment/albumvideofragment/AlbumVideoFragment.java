package com.geekapps.andrii.gallerypicker.fragment.albumvideofragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.recycler.adapter.AlbumRecyclerAdapter;
import com.geekapps.andrii.gallerypicker.view.PlaceholderRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumVideoFragment extends Fragment implements AlbumVideoContract.View {

    public static final String TAG = AlbumVideoFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.video_album_recycler_view)
    PlaceholderRecyclerView mAlbumVideoRecycler;

    private AlbumRecyclerAdapter mAdapter;
    private GalleryActivityController mController;

    public AlbumVideoFragment() {
    }

    public static AlbumVideoFragment newInstance() {
        Bundle args = new Bundle();
        AlbumVideoFragment fragment = new AlbumVideoFragment();
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
        View root = inflater.inflate(R.layout.fragment_album_video, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlbumVideoContract.Presenter presenter = new AlbumVideoPresenter(this, getContext());
        initRecycler();
        presenter.getVideos(getLoaderManager());
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mAdapter = new AlbumRecyclerAdapter(mController);
        mAlbumVideoRecycler.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty_placeholder, null));
        mAlbumVideoRecycler.setLayoutManager(layoutManager);
        mAlbumVideoRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onGetVideosSuccess(List<AlbumModel> albumVideoModels) {
        mAdapter.addAll(albumVideoModels);
    }

    @Override
    public void onGetVideosFail() {

    }
}
