package com.jay.android.video.gallery.gallery.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;

import com.jay.android.video.gallery.gallery.utils.DateUtils;
import com.jay.android.video.gallery.gallery.utils.MediaUtils;
import com.jay.android.video.gallery.gallery.R;
import com.jay.android.video.gallery.gallery.activity.adapter.EndlessRecyclerViewScrollListener;
import com.jay.android.video.gallery.gallery.activity.adapter.VideoCardAdapter;
import com.jay.android.video.gallery.gallery.model.Video;
import com.jay.android.video.gallery.gallery.repositiry.VideoRepo;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VideoGalleryActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 1000;
    private static final int REQUEST_PERMISSIONS = 1001;
    private RecyclerView mRecyclerView;
    private Uri videoStoragePathUri;
    private VideoCardAdapter videoCardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.record_video);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {recordVideo();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        // Register endless scrollbar
        registerScroll(linearLayoutManager);

        // Set Adapter
        videoCardAdapter = new VideoCardAdapter(getApplicationContext());
        // Load first page of videos
        List<Video> videos = VideoRepo.getNextVideos();
        videoCardAdapter.updateVideos(videos);
        mRecyclerView.setAdapter(videoCardAdapter);

        // request for permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    private void registerScroll(LinearLayoutManager linearLayoutManager) {
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                List<Video> nextVideos = VideoRepo.getNextVideos();
                videoCardAdapter.updateVideos(nextVideos);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {

            // Extract video information
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(videoStoragePathUri.getPath());
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            String creationDate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);

            Video video = new Video();
            // TODO: Ask user to name the video
            video.name = "Random Video";
            video.duration = (int)TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(time));
            video.creationTime = DateUtils.formatMediaDate(creationDate);

            // Copy Video to internal storage
            String internalStorageVideoPath = MediaUtils.saveToInternalStorage(this, videoStoragePathUri);

            video.uri = internalStorageVideoPath;

            Video.save(video);
            videoCardAdapter.addVideo(video);
        }
    }

    private void recordVideo() {
        // file:///storage/emulated/0/Movies/camera/Video_20161221_140831.mp4
        videoStoragePathUri = MediaUtils.getOutputMediaFileUri();

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            //
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String type = mime.getMimeTypeFromExtension("mp4");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                // content://com.jay.android.video.gallery.gallery.provider/external_storage_root/Movies/camera/Video_20161221_140831.mp4
                Uri contentUri = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", new File(videoStoragePathUri.getPath()));
                //intent.setDataAndType(contentUri, type);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoStoragePathUri);
            }
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }

    }
}
