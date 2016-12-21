package com.dubsmash.assignment.gallery.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dubsmash.assignment.gallery.R;
import com.dubsmash.assignment.gallery.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoCardAdapter extends RecyclerView.Adapter<VideoCardAdapter.VideoCardViewHolder> {

    private Context mContext;
    private List<Video> videos = new ArrayList<>();

    public VideoCardAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void updateVideos(List<Video> videos) {
        // If videos are empty then set incoming videos to adapter videos
        if (this.videos == null || this.videos.isEmpty()) {
            this.videos = videos;
            notifyDataSetChanged();
            return;
        }

        // If adapter posts already exist and check for possible update
        // new Videos could come if user try to Swipe refresh
        if (videos != null && !videos.isEmpty() && this.videos.size() != videos.size()) {
            this.videos = videos;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public VideoCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_card, parent, false);

        return new VideoCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoCardViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.creationDate.setText("12");
        holder.title.setText("Test");
        holder.duration.setText("12");

        // Glide ?
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(video.videoUri, MediaStore.Video.Thumbnails.MINI_KIND);
        holder.thumbnail_image.setImageBitmap(thumb);

        holder.thumbnail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the video

            }
        });

        // loading video cover using Glide library ????
    }

    public class VideoCardViewHolder extends RecyclerView.ViewHolder {
        public TextView title, creationDate, duration;
        public ImageView thumbnail_image;

        public VideoCardViewHolder(View view) {
            super(view);
            creationDate = (TextView) view.findViewById(R.id.created_date);
            title = (TextView) view.findViewById(R.id.video_title);
            duration = (TextView) view.findViewById(R.id.duration);
            thumbnail_image = (ImageView) view.findViewById(R.id.thumbnail_image);
        }
    }
}