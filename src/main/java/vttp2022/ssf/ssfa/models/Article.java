package vttp2022.ssf.ssfa.models;

import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Article {
    
    private String id;
    private int published_on;
    private String title;
    private String url;
    private String imageurl;
    private String body;
    private String tags;
    private String categories;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getPublished_on() {
        return published_on;
    }
    public void setPublished_on(int published_on) {
        this.published_on = published_on;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }

    public static Article createNew(String jsonStr) {
        StringReader strReader = new StringReader(jsonStr);
        JsonReader reader = Json.createReader(strReader);
        return createNew(reader.readObject());
    }

    public static Article createNew(JsonObject jo) {
        Article a = new Article();
        a.setId(jo.getString("id"));
        a.setPublished_on(jo.getInt("published_on"));
        a.setTitle(jo.getString("title"));
        a.setUrl(jo.getString("url"));
        a.setImageurl(jo.getString("imageurl"));
        a.setBody(jo.getString("body"));
        a.setTags(jo.getString("tags"));
        a.setCategories(jo.getString("categories"));
        return a;
    }

    public static Article create(JsonObject jo) {
        Article a = new Article();
        a.setId(jo.getString("id"));
        a.setPublished_on(jo.getInt("published_on"));
        a.setTitle(jo.getString("title"));
        a.setUrl(jo.getString("url"));
        a.setImageurl(jo.getJsonObject("source_info").getString("img"));
        a.setBody(jo.getString("body"));
        a.setTags(jo.getString("tags"));
        a.setCategories(jo.getString("categories"));
        return a;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", id)
            .add("title", title)
            .add("body", body)
            .add("published_on", published_on)
            .add("url", url)
            .add("imageurl", imageurl)
            .add("tags", tags)
            .add("categories", categories)
            .build();
    }
    
}
