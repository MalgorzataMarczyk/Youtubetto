package com.example.youtubetto.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ChannelVideo implements IYoutubeObject{

    private String videoName;
    private String videolId;
    private String photoUrl;
    private String publishTime;


    public ChannelVideo(JSONObject object) throws JSONException{
        videolId = object.getJSONObject("id").getString("videoId");
        videoName = object.getJSONObject("snippet").getString("title");
        photoUrl = object.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
        publishTime = object.getJSONObject("snippet").getString("publishTime");
    }

    @Override
    public String getID() {
        return videolId;
    }

    @Override
    public String getTitle() {
        return videoName;
    }

    @Override
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public String getPublishTime() {
        return publishTime;
    }
}
