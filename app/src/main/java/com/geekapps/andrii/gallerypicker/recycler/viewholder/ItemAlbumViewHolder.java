package com.geekapps.andrii.gallerypicker.recycler.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekapps.andrii.gallerypicker.R;
import com.geekapps.andrii.gallerypicker.activity.gallery.GalleryActivityController;
import com.geekapps.andrii.gallerypicker.model.AlbumModel;
import com.geekapps.andrii.gallerypicker.model.MediaModel;
import com.geekapps.andrii.gallerypicker.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAlbumViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.album_preview_iv)
    ImageView mAlbumPreviewIv;
    @BindView(R.id.album_title_tv)
    TextView mAlbumTitleTv;
    @BindView(R.id.image_number_tv)
    TextView mImageNumberTv;

    private View mRootView;
    private GalleryActivityController mController;

    public ItemAlbumViewHolder(View itemView, GalleryActivityController controller) {
        super(itemView);
        mRootView = itemView;
        mController = controller;
        ButterKnife.bind(this, itemView);
    }

    public void bind(final AlbumModel albumModel) {
        List<MediaModel> baseMediaModels = albumModel.getMediaModels();
        if (!Utils.isEmpty(baseMediaModels)) {
            Utils.loadImage(baseMediaModels.get(0), mAlbumPreviewIv);
        }
        mAlbumTitleTv.setText(albumModel.getAlbumName());
        mImageNumberTv.setText(String.valueOf(baseMediaModels.size()));
        mRootView.setOnClickListener(view -> mController.goToMediaFragment(albumModel));
    }
}
