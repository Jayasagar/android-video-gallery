package com.dubsmash.assignment.gallery.model;

import com.orm.SugarRecord;

import java.util.Date;

public class Video extends SugarRecord {
    public String id;
    public String name;
    public String thumbnailUri;
    public String videoUri;
    public int duration;
    public Date creationTime;
}
