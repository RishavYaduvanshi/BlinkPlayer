package com.rishav.blynkplayer;



public class VideoFiles {
    private String dataAdded;

    private String duration;

    private String filename;

    private String id;

    private String path;

    private String size;

    private String title;

    public VideoFiles(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
        this.id = paramString1;
        this.path = paramString2;
        this.title = paramString3;
        this.filename = paramString4;
        this.size = paramString5;
        this.dataAdded = paramString6;
        this.duration = paramString7;
    }

    public String getDataAdded() {
        return this.dataAdded;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public String getSize() {
        return this.size;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDataAdded(String paramString) {
        this.dataAdded = paramString;
    }

    public void setDuration(String paramString) {
        this.duration = paramString;
    }

    public void setFilename(String paramString) {
        this.filename = paramString;
    }

    public void setId(String paramString) {
        this.id = paramString;
    }

    public void setPath(String paramString) {
        this.path = paramString;
    }

    public void setSize(String paramString) {
        this.size = paramString;
    }

    public void setTitle(String paramString) {
        this.title = paramString;
    }
}

