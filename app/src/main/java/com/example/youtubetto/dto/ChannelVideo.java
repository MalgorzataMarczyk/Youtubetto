package com.example.youtubetto.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class ChannelVideo implements IYoutubeObject{

    private String videoName;
    private String videolId;

    public ChannelVideo(JSONObject object) throws JSONException{
        videolId = object.getJSONObject("id").getString("videoId");
        videoName = object.getJSONObject("snippet").getString("title");
    }

    @Override
    public String getID() {
        return videolId;
    }

    @Override
    public String getTitle() {
        return videoName;
    }
}
