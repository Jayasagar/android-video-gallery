package com.jay.android.video.gallery.gallery.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jay.android.video.gallery.gallery.utils.DateUtils;
import com.jay.android.video.gallery.gallery.R;
import com.jay.android.video.gallery.gallery.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoCardAdapter extends RecyclerView.Adapter<VideoCardAdapter.VideoCardViewHolder> {

    private Context context;
    private List<Video> videos = new ArrayList<>();

    public VideoCardAdapter(Context context) {
        this.context = context;
    }

    public void updateVideos(List<Video> videos) {
        // If videos are empty then set incoming videos to adapter videos
        if (this.videos == null || this.videos.isEmpty()) {
            this.videos = videos;
            notifyDataSetChanged();
            return;
        }

        // If adapter posts already exist and check for possible update
        if (videos != null && !videos.isEmpty() && this.videos.size() != videos.size()) {
            this.videos.addAll(videos);
            notifyDataSetChanged();
        }
    }

    public void addVideo(Video video) {
        this.videos.add(0, video);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public VideoCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_card, parent, false);

        return new VideoCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoCardViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.creationDate.setText(DateUtils.dateToString(video.creationTime));
        holder.title.setText(video.name);
        holder.duration.setText(String.valueOf(video.duration) + " Sec");

        // Glide to create Thumbnail and cache image
        Glide.with(context)
                .load(video.uri)
                .asBitmap()
                .thumbnail(0.1f)
                .placeholder(R.drawable.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.thumbnail_image);

        holder.thumbnail_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://" + context.getPackageName()+ "/" + video.uri);

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setDataAndType(uri, "video/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
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