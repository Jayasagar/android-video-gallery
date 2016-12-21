package com.dubsmash.assignment.gallery.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dubsmash.assignment.gallery.R;
import com.dubsmash.assignment.gallery.activity.adapter.VideoCardAdapter;
import com.dubsmash.assignment.gallery.model.Video;
import com.orm.query.Select;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class VideoGalleryActivity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1000;
    private RecyclerView mRecyclerView;
    private Uri videoStoragePathUri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Start the camera to record video
                dispatchTakeVideoIntent();
            }
        });

        // Load all the Videos
        // TODO: Pagination support
        List<Video> videos = Select.from(Video.class).list();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        VideoCardAdapter videoCardAdapter = new VideoCardAdapter(getApplicationContext());
        videoCardAdapter.updateVideos(videos);

        mRecyclerView.setAdapter(videoCardAdapter);
        mRecyclerView.setLayoutManager(llm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + new Date
                ().getTime() + ".mp4");

        videoStoragePathUri = Uri.fromFile(file);
        //videoStoragePathUri = getInternalFileUri();

        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoStoragePathUri);

        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //Uri videoUri = intent.getData();
            // Duration
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            //use one of overloaded setDataSource() functions to set your data source
            retriever.setDataSource(file.getAbsolutePath());
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeInMillisec = Long.parseLong(time );
            String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String creationDate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);

            //InputStream is = getContentResolver().openInputStream(videoUri);
            //Bitmap bitmap = BitmapFactory.decodeStream(is);
            //is.close();

            Video video = new Video();
            video.name = title;
            video.duration = (int)timeInMillisec/1000;
            // TODO : convert to date
            //video.creationTime = creationDate;
            video.videoUri = videoStoragePathUri.getPath();
            //video.thumbnailUri = thumb.

            Video.save(video);
        }
    }

    public Uri getInternalFileUri() {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(getApplicationContext().getFilesDir() + File.separator +
                "VID_"+ timeStamp + ".mp4");

        return Uri.fromFile(mediaFile);
    }
}
