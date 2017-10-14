package com.stickercamera.app.utility;

import org.json.JSONObject;

/**
 * Created by Nikith_Shetty on 10/09/2017.
 */

public class FbFeed {
    private String message;
    private String create_time;
    private String story;
    private JSONObject from;
    private String full_picture;
    private String type;
    private String description;
    private String picture;
    private String id;
    private String fromName;
    private String likesCount;

    public String getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(String likesCount) {
        this.likesCount = likesCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getFromName() {
        return fromName;
    }

    private void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromId() {
        return fromId;
    }

    private void setFromId(String fromId) {
        this.fromId = fromId;
    }

    private String fromId;

    public JSONObject getFrom() {
        return from;
    }

    public void setFrom(JSONObject from) {
        this.from = from;
    }

    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_picture) {
        this.full_picture = full_picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static FbFeed fromJSONObj (JSONObject resObj) {
        FbFeed feed = new FbFeed();
        try {
            if (resObj.has("description")){
                feed.setDescription(resObj.getString("description"));
            } else {
                feed.setDescription("");
            }
            if (resObj.has("full_picture")){
                feed.setFull_picture(resObj.getString("full_picture"));
            } else {
                feed.setFull_picture("");
            }
            if (resObj.has("type")){
                feed.setType(resObj.getString("type"));
            } else {
                feed.setType("");
            }
            if (resObj.has("picture")){
                feed.setPicture(resObj.getString("picture"));
            } else {
                feed.setPicture("");
            }
            if (resObj.has("id")){
                feed.setId(resObj.getString("id"));
            } else {
                feed.setId("");
            }
            if (resObj.has("from")){
                feed.setFrom(resObj.getJSONObject("from"));
                if (feed.from.has("name")){
                    feed.setFromName(resObj.getJSONObject("from").getString("name"));
                } else {
                    feed.setFromName("");
                }
                if (feed.from.has("id")){
                    feed.setFromId(resObj.getJSONObject("from").getString("id"));
                } else {
                    feed.setFromId("");
                }
            } else {
                feed.setFrom(null);
            }
            if (resObj.has("create_time")){
                String time = resObj.getString("create_time");
                feed.setCreate_time(time);
//                feed.setCreate_time(SimpleDateFormat.getDateInstance().parse(time).toString());
            } else {
                feed.setCreate_time("");
            }
            if (resObj.has("message")){
                feed.setMessage(resObj.getString("message"));
            } else {
                feed.setMessage("");
            }
            if (resObj.has("story")){
                feed.setStory(resObj.getString("story"));
            } else {
                feed.setStory("");
            }
            if (resObj.has("likes")){
                JSONObject likes = resObj.getJSONObject("likes");
                JSONObject summary = likes.getJSONObject("summary");
                if (summary.has("total_count"))
                    feed.setLikesCount(summary.getString("total_count"));
            } else {
                feed.setLikesCount("");
            }
            return feed;
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(){
        return "{ description : " + getDescription() + "\n" +
                " id : " + getId() + "\n" +
                " full_picture : " + getFull_picture() + "\n" +
                " picture : " + getPicture() + "\n" +
                " from : { name : " + getFromName() + "\n" +
                "          id : " + getFromId() + "\n" +
                "       } " + "\n" +
                " message : " + getMessage() + "\n" +
                " created_time : " + getCreate_time() + "\n" +
                " story : " + getStory() + "\n" +
                "}";
    }
}
