/*
* Copyright (C) 2015 Andy Ke <dictfb@gmail.com>
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.android.mb.wash.view;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.android.mb.wash.R;
import com.android.mb.wash.utils.ToastHelper;

public class PlayVideoActivity extends AppCompatActivity {

    private VideoView mVideoNet;
    private ProgressBar mProgressBar;
    private ImageView mIvPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        initNetVideo();
    }

    //播放网络视频
    private void initNetVideo() {
        mVideoNet = findViewById(R.id.video_net);
        mProgressBar = findViewById(R.id.progressBar);
        mIvPlay = findViewById(R.id.iv_play);
        String videoUrl = getIntent().getStringExtra("videoUrl");
        //设置有进度条可以拖动快进
        MediaController localMediaController = new MediaController(this);
        mVideoNet.setMediaController(localMediaController);
        mVideoNet.setVideoPath(videoUrl);
        mVideoNet.start();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mVideoNet.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //视频加载完成,准备好播放视频的回调
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mVideoNet.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //视频播放完成后的回调
            }
        });
        mVideoNet.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                //异常回调
                ToastHelper.showLongToast("视频加载异常");
                return false;//如果方法处理了错误，则为true；否则为false。返回false或根本没有OnErrorListener，将导致调用OnCompletionListener。
            }
        });
    }
}
