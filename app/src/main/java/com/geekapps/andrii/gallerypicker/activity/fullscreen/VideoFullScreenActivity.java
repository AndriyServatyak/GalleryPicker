package com.geekapps.andrii.gallerypicker.activity.fullscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.geekapps.andrii.gallerypicker.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFullScreenActivity extends AppCompatActivity {

    private static final String VIDEO_PATH_PREF = "VIDEO_PATH_PREF";

    @BindView(R.id.player_view)
    SimpleExoPlayerView mSimpleExoPlayerView;

    private SimpleExoPlayer mPlayer;

    private DataSource.Factory mMediaDataSourceFactory;
    private DefaultTrackSelector mTrackSelector;
    private boolean mIsShouldAutoPlay;
    private BandwidthMeter mBandwidthMeter;

    private ImageView mHideControllerButton;

    private String mVideoPath;


    public static void start(Context context, String videoPath) {
        Intent starter = new Intent(context, VideoFullScreenActivity.class);
        starter.putExtra(VIDEO_PATH_PREF, videoPath);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_full_screen);
        ButterKnife.bind(this);
        mIsShouldAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) mBandwidthMeter);
        mHideControllerButton = findViewById(R.id.exo_controller);
    }

    private void tryGetVideoPath() {
        if (getIntent() != null) {
            mVideoPath = getIntent().getStringExtra(VIDEO_PATH_PREF);
            initializePlayer();
        }
    }

    private void initializePlayer() {

        mSimpleExoPlayerView.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mPlayer = ExoPlayerFactory.newSimpleInstance(this, mTrackSelector);
        mSimpleExoPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(mIsShouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mVideoPath),
                mMediaDataSourceFactory, extractorsFactory, null, null);

        mPlayer.prepare(mediaSource);

        mHideControllerButton.setOnClickListener(v -> mSimpleExoPlayerView.hideController());
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mIsShouldAutoPlay = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
            mTrackSelector = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            tryGetVideoPath();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {
            tryGetVideoPath();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
