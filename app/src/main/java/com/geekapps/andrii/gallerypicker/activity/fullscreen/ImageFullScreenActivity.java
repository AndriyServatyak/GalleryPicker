package com.geekapps.andrii.gallerypicker.activity.fullscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFullScreenActivity extends AppCompatActivity {

    private static final String SELECTED_POSITION_PREF = "SELECTED_POSITION_PREF";

    @BindView(R.id.photo_view_pager)
    ViewPager mPhotoViewPager;

    public static void start(Context context, List<MediaModel> mediaModels, int selectedItemPosition) {
        Intent starter = new Intent(context, ImageFullScreenActivity.class);
        DataHolder.setData(mediaModels);
        starter.putExtra(SELECTED_POSITION_PREF, selectedItemPosition);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent() != null && DataHolder.hasData()) {
            List<MediaModel> mediaModels = DataHolder.getData();
            int selectedPosition = getIntent().getIntExtra(SELECTED_POSITION_PREF, 0);
            setupViewPager(mediaModels, selectedPosition);
        }
    }

    private void setupViewPager(List<MediaModel> mediaModels, int selectedPosition) {
        PhotoPagerAdapter photoPagerAdapter = new PhotoPagerAdapter(this);
        photoPagerAdapter.setPhotos(mediaModels);
        mPhotoViewPager.setAdapter(photoPagerAdapter);
        mPhotoViewPager.setCurrentItem(selectedPosition);
    }

    private enum DataHolder {
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

    private static class PhotoPagerAdapter extends PagerAdapter {

        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private List<MediaModel> mPhotos;

        PhotoPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPhotos = new ArrayList<>();
        }

        void setPhotos(List<MediaModel> photos) {
            mPhotos.clear();
            mPhotos.addAll(photos);
        }

        @Override
        public int getCount() {
            return mPhotos.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.item_photo_pager, container, false);
            ImageView imageView = itemView.findViewById(R.id.photo_pager_iv);
            Utils.loadImage(mPhotos.get(position), imageView);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container,
                                int position,
                                @NonNull Object object) {
            container.removeView((ImageView) object);
        }
    }
}
