package com.tomato.pocketsend.pocketsend_backend.dto;

public class FileUploadResponse {
    private long id;
    private String filename;
    private String downloadURL;

    public FileUploadResponse(long id, String filename, String downloadURL) {
        this.id = id;
        this.filename = filename;
        this.downloadURL = downloadURL;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
