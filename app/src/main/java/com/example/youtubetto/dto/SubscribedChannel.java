package com.example.youtubetto.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class SubscribedChannel implements IYoutubeObject {
    private String channelName;
    private String channelId;

    public SubscribedChannel(JSONObject object) throws JSONException {
        JSONObject channelObj = object.getJSONObject("snippet");
        channelId = channelObj.getJSONObject("resourceId").getString("channelId");
        channelName = channelObj.getString("title");
    }

    @Override
    public String getID() {
        return channelId;
    }

    @Override
    public String getTitle() {
        return channelName;
    }

    @Override
    public String getPhotoUrl() { return null; }

    @Override
    public String getPublishTime() { return null; }
}
