package com.jay.android.video.gallery.gallery.repositiry;

import com.jay.android.video.gallery.gallery.model.Video;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Date;
import java.util.List;

import static com.jay.android.video.gallery.gallery.GalleryApp.PAGE_LIMIT;

public final class VideoRepo {
    private VideoRepo() {}
    public static Date lastRetrievedDate = null;

    public static List<Video> getNextVideos() {
        Select<Video> select = Select.from(Video.class);

        if (lastRetrievedDate != null) {
            select.where(Condition.prop("creation_Time").lt(lastRetrievedDate.getTime()));
        }

        List<Video> videos = select.orderBy("creation_Time desc")
                                    .limit(PAGE_LIMIT)
                                    .list();
        if (videos != null && !videos.isEmpty()) {
            lastRetrievedDate = videos.get(videos.size() - 1).creationTime;
        }
        return videos;
    }
}
