package com.geekapps.andrii.gallerypicker.fragment.albumimagefragment;

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

public class AlbumImageFragment extends Fragment implements AlbumImageFragmentContract.View {

    public static final String TAG = AlbumImageFragment.class.getSimpleName();
    private static final int SPAN_COUNT = 2;

    @BindView(R.id.image_recycler_view)
    PlaceholderRecyclerView mImageRecyclerView;

    private AlbumRecyclerAdapter mAdapter;
    private GalleryActivityController mController;

    public AlbumImageFragment() {
    }

    public static AlbumImageFragment newInstance() {
        Bundle args = new Bundle();
        AlbumImageFragment fragment = new AlbumImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mController = (GalleryActivityController) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlbumImageFragmentContract.Presenter presenter =
                new AlbumImageFragmentPresenter(this, getContext());
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        } else {
            initRecycler();
            presenter.getGalleryImageAlbums(getLoaderManager());
        }
    }

    private void initRecycler() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);
        mAdapter = new AlbumRecyclerAdapter(mController);
        mImageRecyclerView.setAdapter(mAdapter);
        mImageRecyclerView.setEmptyView(getLayoutInflater().inflate(R.layout.layout_empty_placeholder, null));
        mImageRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onGetImageAlbumsSuccess(List<AlbumModel> albums) {
        mAdapter.addAll(albums);
    }

    @Override
    public void onGetImageAlbumsFail() {

    }
}
