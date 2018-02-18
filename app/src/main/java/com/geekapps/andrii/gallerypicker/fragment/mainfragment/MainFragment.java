package com.geekapps.andrii.gallerypicker.fragment.mainfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivity;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.fragment.albumimagefragment.AlbumImageFragment;
import com.geekapps.andrii.gallerypicker.fragment.albumvideofragment.AlbumVideoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    @BindView(R.id.image_video_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.image_video_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.back_btn)
    ImageView mBackBtn;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    private ViewPagerAdapter mAdapter;
    private GalleryActivityController mController;

    public MainFragment() {
    }

    public static MainFragment newInstance(int mode) {
        Bundle args = new Bundle();
        args.putInt(GalleryActivity.MODE_PREF, mode);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mController = (GalleryActivityController) activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        mToolbarTitle.setText(R.string.gallery);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            setupViewPager();
    }

    private void setupViewPager() {
        int mode = getArguments().getInt(GalleryActivity.MODE_PREF);
        mAdapter = new ViewPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(AlbumImageFragment.newInstance(), getString(R.string.image));
        mAdapter.addFragment(AlbumVideoFragment.newInstance(), getString(R.string.video));
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mode);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.back_btn)
    public void onBackClicked() {
        mController.goBack();
    }

    private static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
