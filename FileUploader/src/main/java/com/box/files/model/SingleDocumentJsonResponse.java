package com.box.files.model;

import java.time.LocalDateTime;

public class SingleDocumentJsonResponse {

    String filename;

    String hash;

    LocalDateTime lastUpload;

    public SingleDocumentJsonResponse() {
    }

    public SingleDocumentJsonResponse(String filename, String hash, LocalDateTime lastUpload) {
        this.filename = filename;
        this.hash = hash;
        this.lastUpload = lastUpload;
    }

    public String getFilename() { return filename; }

    public void setFilename(String filename) { this.filename = filename; }

    public String getHash() { return hash; }

    public void setHash(String hash) { this.hash = hash; }

    public LocalDateTime getLastUpload() { return lastUpload; }

    public void setLastUpload(LocalDateTime lastUpload) { this.lastUpload = lastUpload; }

}
